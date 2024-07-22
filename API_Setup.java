// TranslationApp.java
import com.google.cloud.translate.*;
import com.google.cloud.speech.v1.*;
import com.google.cloud.texttospeech.v1.*;
import javax.swing.*;

public class TranslationApp {
    private static Translate translate;
    private static SpeechClient speechClient;
    private static TextToSpeechClient textToSpeechClient;

    public static void initialize() throws Exception {
        // Initialize translation API
        translate = TranslateOptions.newBuilder().build().getService();
        
        // Initialize speech-to-text API
        speechClient = SpeechClient.create();
        
        // Initialize text-to-speech API
        textToSpeechClient = TextToSpeechClient.create();
    }

    public static void main(String[] args) throws Exception {
        TranslationApp.initialize();
        new TranslationUI();
    }
}
