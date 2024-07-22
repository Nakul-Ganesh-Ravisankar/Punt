// TranslationUI.java
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.google.protobuf.ByteString;

public class TranslationUI {
    private JFrame frame;
    private JTextField inputTextField;
    private JComboBox<String> languageComboBox;
    private JTextArea outputTextArea;
    private JButton translateButton;
    private JButton speakButton;

    public TranslationUI() {
        frame = new JFrame("Translation App");
        inputTextField = new JTextField();
        languageComboBox = new JComboBox<>(new String[]{"en", "es", "fr", "de", "zh"});
        outputTextArea = new JTextArea();
        translateButton = new JButton("Translate");
        speakButton = new JButton("Speak");

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.add(new JLabel("Input Text:"));
        frame.add(inputTextField);
        frame.add(new JLabel("Target Language:"));
        frame.add(languageComboBox);
        frame.add(translateButton);
        frame.add(new JLabel("Translated Text:"));
        frame.add(outputTextArea);
        frame.add(speakButton);

        translateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inputText = inputTextField.getText();
                String targetLanguage = (String) languageComboBox.getSelectedItem();
                try {
                    String translatedText = TranslationService.translateText(inputText, targetLanguage);
                    outputTextArea.setText(translatedText);
                } catch (Exception ex) {
                    outputTextArea.setText("Translation error: " + ex.getMessage());
                }
            }
        });

        speakButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String translatedText = outputTextArea.getText();
                try {
                    ByteString audioBytes = TranslationService.textToSpeech(translatedText, (String) languageComboBox.getSelectedItem());
                    // Code to play audioBytes
                } catch (Exception ex) {
                    outputTextArea.setText("Speech synthesis error: " + ex.getMessage());
                }
            }
        });

        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
