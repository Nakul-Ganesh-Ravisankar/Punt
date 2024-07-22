import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class TransUI {
    private JFrame frame;
    private JTextField inpTextField;
    private JComboBox<String> langComboBox;
    private JTextArea outputTA;
    private JButton translateButton;
    private JButton speakButton;

    public TransUI() {
        frame = new JFrame("Translation App");
        inpTextField = new JTextField();
        langComboBox = new JComboBox<>(new String[]{"en", "es", "fr", "de", "zh"});
        outputTA = new JTextArea();
        translateButton = new JButton("Translate");
        speakButton = new JButton("Speak");

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.add(new JLabel("Input Text:"));
        frame.add(inpTextField);
        frame.add(new JLabel("Target Language:"));
        frame.add(langComboBox);
        frame.add(translateButton);
        frame.add(new JLabel("Translated Text:"));
        frame.add(outputTA);
        frame.add(speakButton);

        translateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inputText = inpTextField.getText();
                String targetLanguage = (String) langComboBox.getSelectedItem();
                try {
                    String translatedText = Translation1.translateText(inputText, targetLanguage);
                    outputTA.setText(translatedText);
                } catch (Exception ex) {
                    outputTA.setText("Translation error: " + ex.getMessage());
                }
            }
        });

        speakButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String translatedText = outputTA.getText();
                try {
                    ByteString audioBytes = Translation1.textToSpeech(translatedText, (String) langComboBox.getSelectedItem());
                    playAudio(audioBytes);
                } catch (Exception ex) {
                    outputTA.setText("Speech synthesis error: " + ex.getMessage());
                }
            }
        });

        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void playAudio(ByteString audioBytes) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        byte[] audioData = audioBytes.toByteArray();
        AudioInputStream audioStream = new AudioInputStream(new ByteArrayInputStream(audioData), getAudioFormat(), audioData.length / getAudioFormat().getFrameSize());

        SourceDataLine audioLine = AudioSystem.getSourceDataLine(getAudioFormat());
        audioLine.open(getAudioFormat());
        audioLine.start();

        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = audioStream.read(buffer, 0, buffer.length)) != -1) {
            audioLine.write(buffer, 0, bytesRead);
        }

        audioLine.drain();
        audioLine.close();
        audioStream.close();
    }

    private AudioFormat getAudioFormat() {
        // Assuming the audio format is LINEAR16 with 16-bit samples and 16000 Hz sampling rate.
        // Modify according to your needs if the TTS API returns a different format.
        return new AudioFormat(16000, 16, 1, true, false);
    }

    public static void main(String[] args) throws Exception {
        APISetup.initialize();
        new TransUI();
    }
}
