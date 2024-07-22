// TranslationService.java
import com.google.cloud.translate.*;
import com.google.cloud.speech.v1.*;
import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;

public class Translation1 {
    private static Translate trans;
    private static SpeechClient sClient;
    private static TextToSpeechClient ttsClient;

    public static void setTranslate(Translate trans) {
        Translation1.trans = trans;
    }

    public static void setSpeechClient(SpeechClient sClient) {
        Translation1.sClient = sClient;
    }

    public static void setTextToSpeechClient(TextToSpeechClient ttsClient) {
        Translation1.ttsClient = ttsClient;
    }

    public static String detectLanguage(String input) {
        Detection detect1 = translate.detect(input);
        return detect1.getLanguage();
    }

    public static String translateText(String input, String targetLanguage) {
        Translation trans1 = translate.translate(input, Translate.TranslateOption.targetLanguage(targetLanguage));
        return trans1.getTranslatedText();
    }

    public static String speechToText(ByteString audioBytes) throws Exception {
        RecognitionAudio audio = RecognitionAudio.newBuilder().setContent(audioBytes).build();
        RecognitionConfig config = RecognitionConfig.newBuilder().setEncoding(RecognitionConfig.AudioEncoding.LINEAR16).setSampleRateHertz(16000).setLanguageCode("en-US").build();
        RecognizeResponse response = sClient.recognize(config, audio);
        return response.getResultsList().get(0).getAlternativesList().get(0).getTranscript();
    }

    public static ByteString textToSpeech(String input, String languageCode) throws Exception {
        SynthesisInput inputText = SynthesisInput.newBuilder().setText(input).build();
        VoiceSelectionParams voice = VoiceSelectionParams.newBuilder().setLanguageCode(languageCode).setSsmlGender(SsmlVoiceGender.NEUTRAL).build();
        AudioConfig audioConfig = AudioConfig.newBuilder().setAudioEncoding(AudioEncoding.LINEAR16).build();
        SynthesizeSpeechResponse response = ttsClient.synthesizeSpeech(inputText, voice, audioConfig);
        return response.getAudioContent();
    }
}
