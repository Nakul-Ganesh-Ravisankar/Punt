// APISetup.java
import com.google.cloud.translate.*;
import com.google.cloud.speech.v1.*;
import com.google.cloud.texttospeech.v1.*;
import javax.swing.*;
import java.io.*;

public class APISetup {
    private static Translate trans;
    private static SpeechClient sClient;
    private static TextToSpeechClient ttsClient;

    public static void initialize() throws Exception {
        //translation API
        trans = TranslateOptions.newBuilder().build().getService();
        
        //speech-to-text API
        sClient = SpeechClient.create();
        
        //text-to-speech API
        tttsClient = TextToSpeechClient.create();
    }

    public static void main(String[] args) throws Exception {
       APISetup.initialize();
        new UI();
    }
}
