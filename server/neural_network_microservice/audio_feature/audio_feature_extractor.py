import librosa
import numpy as np
import audioread.ffdec
import platform

from typing import List

from api.repository import TrackRepository, AudioFeatureRepository, SegmentAudioFeatureRepository

AUDIO_PATH = '../../data/track/'


class AudioFeatureExtractor:
    def __init__(self, segment_start_delta, segment_duration):
        self.__segment_start_delta = segment_start_delta
        self.__segment_duration = segment_duration
        self.__sr_value = None

    def __librosa_load(self, audio_file):
        system = platform.system()
        if system == "Darwin":
            aro = audioread.ffdec.FFmpegAudioFile(audio_file)
            y, sr = librosa.load(aro, sr=self.__sr_value)
        else:
            y, sr = librosa.load(audio_file, sr=self.__sr_value)
        return y, sr

    def _get_audio_duration(self, audio_file):
        y, sr = AudioFeatureExtractor.__librosa_load(self, audio_file)
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

        data: List[float] = []

        chroma_stft = librosa.feature.chroma_stft(y=y, sr=sr, n_chroma=12)
        # print("chroma_stft:\n", chroma_stft)
        # print("chroma_stft len: ", len(chroma_stft))

        data.extend(np.mean(chroma_stft, axis=1))
        data.extend(np.std(chroma_stft, axis=1))
        data.extend(np.var(chroma_stft, axis=1))
        data.extend(np.min(chroma_stft, axis=1))
        data.extend(np.max(chroma_stft, axis=1))

        chroma_cqt = librosa.feature.chroma_cqt(y=y, sr=sr, n_chroma=12)
        # print("chroma_cqt:\n", chroma_cqt)
        # print("chroma_cqt len: ", len(chroma_cqt))

        data.extend(np.mean(chroma_cqt, axis=1))
        data.extend(np.std(chroma_cqt, axis=1))
        data.extend(np.var(chroma_cqt, axis=1))
        data.extend(np.min(chroma_cqt, axis=1))
        data.extend(np.max(chroma_cqt, axis=1))

        chroma_cens = librosa.feature.chroma_cens(y=y, sr=sr, n_chroma=12)
        # print("chroma_cens:\n", chroma_cens)
        # print("chroma_cens len: ", len(chroma_cens))

        data.extend(np.mean(chroma_cens, axis=1))
        data.extend(np.std(chroma_cens, axis=1))
        data.extend(np.var(chroma_cens, axis=1))
        data.extend(np.min(chroma_cens, axis=1))
        data.extend(np.max(chroma_cens, axis=1))

        chroma_vqt = librosa.feature.chroma_vqt(y=y, sr=sr, intervals='equal', bins_per_octave=12)
        # print("chroma_vqt:\n", chroma_vqt)
        # print("chroma_vqt len: ", len(chroma_vqt))

        data.extend(np.mean(chroma_vqt, axis=1))
        data.extend(np.std(chroma_vqt, axis=1))
        data.extend(np.var(chroma_vqt, axis=1))
        data.extend(np.min(chroma_vqt, axis=1))
        data.extend(np.max(chroma_vqt, axis=1))

        melspectrogram = librosa.feature.melspectrogram(y=y, sr=sr, n_mels=128)
        # print("melspectrogram:\n", melspectrogram)
        # print("melspectrogram len: ", len(melspectrogram))

        data.extend(np.mean(melspectrogram, axis=1))
        data.extend(np.std(melspectrogram, axis=1))
        data.extend(np.var(melspectrogram, axis=1))
        data.extend(np.min(melspectrogram, axis=1))
        data.extend(np.max(melspectrogram, axis=1))

        mfcc = librosa.feature.mfcc(y=y, sr=sr, n_mfcc=20)
        # print("mfcc:\n", mfcc)
        # print("mfcc len: ", len(mfcc))

        data.extend(np.mean(mfcc, axis=1))
        data.extend(np.std(mfcc, axis=1))
        data.extend(np.var(mfcc, axis=1))
        data.extend(np.min(mfcc, axis=1))
        data.extend(np.max(mfcc, axis=1))

        rms = librosa.feature.rms(y=y)
        # print("rms:\n", rms)
        # print("rms len: ", len(rms))

        data.extend(np.mean(rms, axis=1))
        data.extend(np.std(rms, axis=1))
        data.extend(np.var(rms, axis=1))
        data.extend(np.min(rms, axis=1))
        data.extend(np.max(rms, axis=1))

        spectral_centroid = librosa.feature.spectral_centroid(y=y, sr=sr)
        # print("spectral_centroid:\n", spectral_centroid)
        # print("spectral_centroid len: ", len(spectral_centroid))

        data.extend(np.mean(spectral_centroid, axis=1))
        data.extend(np.std(spectral_centroid, axis=1))
        data.extend(np.var(spectral_centroid, axis=1))
        data.extend(np.min(spectral_centroid, axis=1))
        data.extend(np.max(spectral_centroid, axis=1))

        spectral_bandwidth = librosa.feature.spectral_bandwidth(y=y, sr=sr)
        # print("spectral_bandwidth:\n", spectral_bandwidth)
        # print("spectral_bandwidth len: ", len(spectral_bandwidth))

        data.extend(np.mean(spectral_bandwidth, axis=1))
        data.extend(np.std(spectral_bandwidth, axis=1))
        data.extend(np.var(spectral_bandwidth, axis=1))
        data.extend(np.min(spectral_bandwidth, axis=1))
        data.extend(np.max(spectral_bandwidth, axis=1))

        spectral_contrast = librosa.feature.spectral_contrast(y=y, sr=sr)
        # print("spectral_contrast:\n", spectral_contrast)
        # print("spectral_contrast len: ", len(spectral_contrast))

        data.extend(np.mean(spectral_contrast, axis=1))
        data.extend(np.std(spectral_contrast, axis=1))
        data.extend(np.var(spectral_contrast, axis=1))
        data.extend(np.min(spectral_contrast, axis=1))
        data.extend(np.max(spectral_contrast, axis=1))

        spectral_flatness = librosa.feature.spectral_flatness(y=y)
        # print("spectral_flatness:\n", spectral_flatness)
        # print("spectral_flatness len: ", len(spectral_flatness))

        data.extend(np.mean(spectral_flatness, axis=1))
        data.extend(np.std(spectral_flatness, axis=1))
        data.extend(np.var(spectral_flatness, axis=1))
        data.extend(np.min(spectral_flatness, axis=1))
        data.extend(np.max(spectral_flatness, axis=1))

        spectral_rolloff = librosa.feature.spectral_rolloff(y=y, sr=sr)
        # print("spectral_rolloff:\n", spectral_rolloff)
        # print("spectral_rolloff len: ", len(spectral_rolloff))

        data.extend(np.mean(spectral_rolloff, axis=1))
        data.extend(np.std(spectral_rolloff, axis=1))
        data.extend(np.var(spectral_rolloff, axis=1))
        data.extend(np.min(spectral_rolloff, axis=1))
        data.extend(np.max(spectral_rolloff, axis=1))

        poly_features = librosa.feature.poly_features(y=y, sr=sr)
        # print("poly_features:\n", poly_features)
        # print("poly_features len: ", len(poly_features))

        data.extend(np.mean(poly_features, axis=1))
        data.extend(np.std(poly_features, axis=1))
        data.extend(np.var(poly_features, axis=1))
        data.extend(np.min(poly_features, axis=1))
        data.extend(np.max(poly_features, axis=1))

        tonnetz = librosa.feature.tonnetz(y=y, sr=sr)
        # print("tonnetz:\n", tonnetz)
        # print("tonnetz len: ", len(tonnetz))

        data.extend(np.mean(tonnetz, axis=1))
        data.extend(np.std(tonnetz, axis=1))
        data.extend(np.var(tonnetz, axis=1))
        data.extend(np.min(tonnetz, axis=1))
        data.extend(np.max(tonnetz, axis=1))

        zero_crossing_rate = librosa.feature.zero_crossing_rate(y=y)
        # print("zero_crossing_rate:\n", zero_crossing_rate)
        # print("zero_crossing_rate len: ", len(zero_crossing_rate))

        data.extend(np.mean(zero_crossing_rate, axis=1))
        data.extend(np.std(zero_crossing_rate, axis=1))
        data.extend(np.var(zero_crossing_rate, axis=1))
        data.extend(np.min(zero_crossing_rate, axis=1))
        data.extend(np.max(zero_crossing_rate, axis=1))

        tempo, beats = librosa.beat.beat_track(y=y, sr=sr)
        # print("tempo:", tempo)
        # print("beats:", beats)

        data.append(tempo)
        if len(beats) != 0:
            data.append(np.mean(beats))
            data.append(np.std(beats))
            data.append(np.var(beats))
            data.append(np.min(beats))
            data.append(np.max(beats))
        else:
            data.append(0)
            data.append(0)
            data.append(0)
            data.append(0)
            data.append(0)

        tempogram = librosa.feature.tempogram(y=y, sr=sr)
        # print("tempogram:\n", tempogram)
        # print("tempogram len: ", len(tempogram))

        data.extend(np.mean(tempogram, axis=1))
        data.extend(np.std(tempogram, axis=1))
        data.extend(np.var(tempogram, axis=1))
        data.extend(np.min(tempogram, axis=1))
        data.extend(np.max(tempogram, axis=1))

        print("data length:", len(data))  # expected 3011

        return data

    async def extract_audio_features_from_track(self, track_id, extraction_type_id) -> str:
        audio_filename = await TrackRepository.get_track_filename(track_id)
        if audio_filename is None:
            return "No audio_filename"

        audio_filename = AUDIO_PATH + audio_filename

        audio_feature_id = await AudioFeatureRepository.get_audio_feature_id(track_id,extraction_type_id)

        audio_duration = AudioFeatureExtractor._get_audio_duration(self, audio_filename)
        print("Длина файла: ", audio_duration)

        segment_index = 0
        segment_start_time = 0

        while segment_start_time + self.__segment_duration <= audio_duration:
            print("Индекс сегмента: ", segment_index)
            data = AudioFeatureExtractor._extract_audio_features_from_segment(self, audio_filename, segment_start_time)
            await SegmentAudioFeatureRepository.add_segment_audio_feature(audio_feature_id, segment_index, data)
            segment_start_time += self.__segment_start_delta
            segment_index += 1

        if segment_start_time != audio_duration - self.__segment_duration:
            print("Индекс сегмента: ", segment_index)
            data = AudioFeatureExtractor._extract_audio_features_from_segment(self, audio_filename,
                                                                              audio_duration - self.__segment_duration)
            await SegmentAudioFeatureRepository.add_segment_audio_feature(audio_feature_id, segment_index, data)

        return "success"
