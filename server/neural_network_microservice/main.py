from audio_feature_extractor.AudioDataHandler import *


if __name__ == "__main__":
    audio_file = '../../data/track/Tricky_Diesel-Yup.mp3'
    # audio_file = librosa.ex('trumpet')

    adh = AudioDataHandler()
    adh.extract_audio_features_from_file(audio_file)
