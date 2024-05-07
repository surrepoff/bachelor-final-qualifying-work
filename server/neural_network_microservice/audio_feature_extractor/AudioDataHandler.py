import librosa
import numpy as np
import audioread.ffdec
import eyed3


class AudioDataHandler:
    def __init__(self):
        self.__segment_start_delta = 3
        self.__segment_duration = 3
        self.__sr_value = None

    def __librosa_load(self, audio_file):
        # aro = audioread.ffdec.FFmpegAudioFile(audio_file)
        # y, sr = librosa.load(aro, sr=self.__sr_value)
        y, sr = librosa.load(audio_file, sr=self.__sr_value)
        return y, sr

    def _get_mp3_duration(self, audio_file):
        audiofile = eyed3.load(audio_file)
        return audiofile.info.time_secs

    def _get_audio_duration(self, audio_file):
        y, sr = AudioDataHandler.__librosa_load(self, audio_file)
        return librosa.get_duration(y=y, sr=sr)

    def _extract_audio_features_from_segment(self, audio_file, start_time):
        # aro = audioread.ffdec.FFmpegAudioFile(audio_file)
        # y, sr = librosa.load(aro, sr=self.__sr_value, offset=start_time, duration=self.__segment_duration)
        y, sr = librosa.load(audio_file, sr=self.__sr_value, offset=start_time, duration=self.__segment_duration)

        length = librosa.get_duration(y=y, sr=sr)
        print("Длина сегмента: ", length)
        '''
        # Хроматические признаки
        chroma_stft_mean = np.mean(librosa.feature.chroma_stft(y=y, sr=sr), axis=1)
        chroma_stft_var = np.var(librosa.feature.chroma_stft(y=y, sr=sr), axis=1)
        print(chroma_stft_mean)
        print(chroma_stft_var)

        # Среднеквадратичная амплитуда
        rms_mean = np.mean(librosa.feature.rms(y=y))
        rms_var = np.var(librosa.feature.rms(y=y))
        print(rms_mean)
        print(rms_var)

        # Спектральный центроид
        spectral_centroid_mean = np.mean(librosa.feature.spectral_centroid(y=y, sr=sr))
        spectral_centroid_var = np.var(librosa.feature.spectral_centroid(y=y, sr=sr))
        print(spectral_centroid_mean)
        print(spectral_centroid_var)

        # Ширина полосы спектра
        spectral_bandwidth_mean = np.mean(librosa.feature.spectral_bandwidth(y=y, sr=sr))
        spectral_bandwidth_var = np.var(librosa.feature.spectral_bandwidth(y=y, sr=sr))
        print(spectral_bandwidth_mean)
        print(spectral_bandwidth_var)

        # Частота отсечки
        rolloff_mean = np.mean(librosa.feature.spectral_rolloff(y=y, sr=sr))
        rolloff_var = np.var(librosa.feature.spectral_rolloff(y=y, sr=sr))
        print(rolloff_mean)
        print(rolloff_var)

        # Скорость пересечения нуля
        zero_crossing_rate_mean = np.mean(librosa.feature.zero_crossing_rate(y=y))
        zero_crossing_rate_var = np.var(librosa.feature.zero_crossing_rate(y=y))
        print(zero_crossing_rate_mean)
        print(zero_crossing_rate_var)

        # Гармония
        harmony_mean = np.mean(librosa.effects.harmonic(y=y))
        harmony_var = np.var(librosa.effects.harmonic(y=y))
        print(harmony_mean)
        print(harmony_var)

        # Темп
        tempo, _ = librosa.beat.beat_track(y=y, sr=sr)
        print(tempo)

        # MFCC
        mfccs = librosa.feature.mfcc(y=y, sr=sr, n_mfcc=20)
        mfccs_mean = np.mean(mfccs, axis=1)
        mfccs_var = np.var(mfccs, axis=1)
        print(mfccs_mean)
        print(mfccs_var)
        '''

    def extract_audio_features_from_file(self, audio_file):
        audio_duration_1 = AudioDataHandler._get_mp3_duration(self, audio_file)
        audio_duration_2 = AudioDataHandler._get_audio_duration(self, audio_file)
        print("Длина1 файла: ", audio_duration_1)
        print("Длина2 файла: ", audio_duration_2)

        segment_index = 0
        segment_start_time = 0

        while segment_start_time + self.__segment_duration <= audio_duration_2:
            print("Индекс сегмента: ", segment_index)
            AudioDataHandler._extract_audio_features_from_segment(self, audio_file, segment_start_time)
            segment_start_time += self.__segment_start_delta
            segment_index += 1

        if segment_start_time != audio_duration_2 - self.__segment_duration:
            print("Индекс сегмента: ", segment_index)
            AudioDataHandler._extract_audio_features_from_segment(self, audio_file,
                                                                  audio_duration_2 - self.__segment_duration)
