import com.google.cloud.translate.*;
import com.google.cloud.texttospeech.v1.*;
import com.google.cloud.speech.v1.*;
import com.google.protobu.ByteString;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TranslationApplication {
    private static Translate trans;
    private static SpeechClient speechC;
    private static TextToSpeechClient ttsC;

    public static void initial() throws Exception {
      //Translation API
      trans = TranslateOptions.newBuilder().build().getService();
      //SpeechToText API
      speechC = SpeechClient.create();
      //TextToSpeech API
      ttsC = TextToSpeechClient.create();
    }
}
