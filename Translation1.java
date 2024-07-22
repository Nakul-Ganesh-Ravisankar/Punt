public class TranslationService {
    public static String detectLanguage(String inp) {
        Detection detect1 = trans.detect(inp);
        return detect1.getLanguage();
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
