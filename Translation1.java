// TranslationService.java
import com.google.cloud.translate.*;
import com.google.cloud.speech.v1.*;
import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;

public class TranslationService {
    private static Translate translate;
    private static SpeechClient speechClient;
    private static TextToSpeechClient textToSpeechClient;

    public static void setTranslate(Translate translate) {
        TranslationService.translate = translate;
    }

    public static void setSpeechClient(SpeechClient speechClient) {
        TranslationService.speechClient = speechClient;
    }

    public static void setTextToSpeechClient(TextToSpeechClient textToSpeechClient) {
        TranslationService.textToSpeechClient = textToSpeechClient;
    }

    public static String detectLanguage(String input) {
        Detection detection = translate.detect(input);
        return detection.getLanguage();
    }

    public static String translateText(String input, String targetLanguage) {
        Translation translation = translate.translate(input, Translate.TranslateOption.targetLanguage(targetLanguage));
        return translation.getTranslatedText();
    }

    public static String speechToText(ByteString audioBytes) throws Exception {
        RecognitionAudio audio = RecognitionAudio.newBuilder().setContent(audioBytes).build();
        RecognitionConfig config = RecognitionConfig.newBuilder().setEncoding(RecognitionConfig.AudioEncoding.LINEAR16).setSampleRateHertz(16000).setLanguageCode("en-US").build();
        RecognizeResponse response = speechClient.recognize(config, audio);
        return response.getResultsList().get(0).getAlternativesList().get(0).getTranscript();
    }

    public static ByteString textToSpeech(String input, String languageCode) throws Exception {
        SynthesisInput inputText = SynthesisInput.newBuilder().setText(input).build();
        VoiceSelectionParams voice = VoiceSelectionParams.newBuilder().setLanguageCode(languageCode).setSsmlGender(SsmlVoiceGender.NEUTRAL).build();
        AudioConfig audioConfig = AudioConfig.newBuilder().setAudioEncoding(AudioEncoding.LINEAR16).build();
        SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(inputText, voice, audioConfig);
        return response.getAudioContent();
    }
}
