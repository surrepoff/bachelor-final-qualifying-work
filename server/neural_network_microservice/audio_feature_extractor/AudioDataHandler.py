import librosa
import numpy as np
import audioread.ffdec
import eyed3
import platform


class AudioDataHandler:
    def __init__(self):
        self.__segment_start_delta = 3
        self.__segment_duration = 3
        self.__sr_value = None

    def __librosa_load(self, audio_file):
        system = platform.system()
        if system == "Darwin":
            aro = audioread.ffdec.FFmpegAudioFile(audio_file)
            y, sr = librosa.load(aro, sr=self.__sr_value)
        else:
            y, sr = librosa.load(audio_file, sr=self.__sr_value)
        return y, sr

    def _get_mp3_duration(self, audio_file):
        audiofile = eyed3.load(audio_file)
        return audiofile.info.time_secs

    def _get_audio_duration(self, audio_file):
        y, sr = AudioDataHandler.__librosa_load(self, audio_file)
        return librosa.get_duration(y=y, sr=sr)

    def _extract_audio_features_from_segment(self, audio_file, start_time):
        system = platform.system()
        if system == "Darwin":
            aro = audioread.ffdec.FFmpegAudioFile(audio_file)
            y, sr = librosa.load(aro, sr=self.__sr_value, offset=start_time, duration=self.__segment_duration)
        else:
            y, sr = librosa.load(audio_file, sr=self.__sr_value, offset=start_time, duration=self.__segment_duration)

        length = librosa.get_duration(y=y, sr=sr)
        print("Длина сегмента: ", length)

        chroma_stft = librosa.feature.chroma_stft(y=y, sr=sr, n_chroma=12)
        print("chroma_stft:\n", chroma_stft)
        print("chroma_stft len: ", len(chroma_stft))
        print("chroma_stft mean:\n", np.mean(chroma_stft, axis=1))
        print("chroma_stft std:\n", np.std(chroma_stft, axis=1))
        print("chroma_stft var:\n", np.var(chroma_stft, axis=1))
        print("chroma_stft min:\n", np.min(chroma_stft, axis=1))
        print("chroma_stft max:\n", np.max(chroma_stft, axis=1))

        chroma_cqt = librosa.feature.chroma_cqt(y=y, sr=sr, n_chroma=12)
        print("chroma_cqt:\n", chroma_cqt)
        print("chroma_cqt len: ", len(chroma_cqt))
        print("chroma_cqt mean:\n", np.mean(chroma_cqt, axis=1))
        print("chroma_cqt std:\n", np.std(chroma_cqt, axis=1))
        print("chroma_cqt var:\n", np.var(chroma_cqt, axis=1))
        print("chroma_cqt min:\n", np.min(chroma_cqt, axis=1))
        print("chroma_cqt max:\n", np.max(chroma_cqt, axis=1))

        chroma_cens = librosa.feature.chroma_cens(y=y, sr=sr, n_chroma=12)
        print("chroma_cens:\n", chroma_cens)
        print("chroma_cens len: ", len(chroma_cens))
        print("chroma_cens mean:\n", np.mean(chroma_cens, axis=1))
        print("chroma_cens std:\n", np.std(chroma_cens, axis=1))
        print("chroma_cens var:\n", np.var(chroma_cens, axis=1))
        print("chroma_cens min:\n", np.min(chroma_cens, axis=1))
        print("chroma_cens max:\n", np.max(chroma_cens, axis=1))

        chroma_vqt = librosa.feature.chroma_vqt(y=y, sr=sr, intervals='equal', bins_per_octave=12)
        print("chroma_vqt:\n", chroma_vqt)
        print("chroma_vqt len: ", len(chroma_vqt))
        print("chroma_vqt mean:\n", np.mean(chroma_vqt, axis=1))
        print("chroma_vqt std:\n", np.std(chroma_vqt, axis=1))
        print("chroma_vqt var:\n", np.var(chroma_vqt, axis=1))
        print("chroma_vqt min:\n", np.min(chroma_vqt, axis=1))
        print("chroma_vqt max:\n", np.max(chroma_vqt, axis=1))

        melspectrogram = librosa.feature.melspectrogram(y=y, sr=sr)
        print("melspectrogram:\n", melspectrogram)
        print("melspectrogram len: ", len(melspectrogram))
        print("melspectrogram mean:\n", np.mean(melspectrogram, axis=1))
        print("melspectrogram std:\n", np.std(melspectrogram, axis=1))
        print("melspectrogram var:\n", np.var(melspectrogram, axis=1))
        print("melspectrogram min:\n", np.min(melspectrogram, axis=1))
        print("melspectrogram max:\n", np.max(melspectrogram, axis=1))
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
