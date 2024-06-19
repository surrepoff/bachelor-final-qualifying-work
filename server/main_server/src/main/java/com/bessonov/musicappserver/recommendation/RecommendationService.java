package com.bessonov.musicappserver.recommendation;

import com.bessonov.musicappserver.api.APIService;
import com.bessonov.musicappserver.database.genre.Genre;
import com.bessonov.musicappserver.database.genre.GenreDTO;
import com.bessonov.musicappserver.database.genre.GenreRepository;
import com.bessonov.musicappserver.database.trackAudioFeatureExtractionType.TrackAudioFeatureExtractionType;
import com.bessonov.musicappserver.database.trackAudioFeatureExtractionType.TrackAudioFeatureExtractionTypeRepository;
import com.bessonov.musicappserver.database.userData.UserData;
import com.bessonov.musicappserver.database.userData.UserDataRepository;
import com.bessonov.musicappserver.database.userData.UserDataShortDTO;
import com.bessonov.musicappserver.database.userNeuralNetworkConfiguration.UserNeuralNetworkConfiguration;
import com.bessonov.musicappserver.database.userNeuralNetworkConfiguration.UserNeuralNetworkConfigurationRepository;
import com.bessonov.musicappserver.database.userRating.UserRating;
import com.bessonov.musicappserver.database.userRating.UserRatingDTO;
import com.bessonov.musicappserver.database.userRating.UserRatingRepository;
import com.bessonov.musicappserver.database.userRecommendation.UserRecommendation;
import com.bessonov.musicappserver.database.userRecommendation.UserRecommendationDTO;
import com.bessonov.musicappserver.database.userRecommendation.UserRecommendationRepository;
import com.bessonov.musicappserver.database.userRecommendationGenre.UserRecommendationGenre;
import com.bessonov.musicappserver.database.userRecommendationGenre.UserRecommendationGenreRepository;
import com.bessonov.musicappserver.database.userRecommendationTrack.UserRecommendationTrack;
import com.bessonov.musicappserver.database.userRecommendationTrack.UserRecommendationTrackRepository;
import com.bessonov.musicappserver.playlist.PlaylistEditDTO;
import com.bessonov.musicappserver.playlist.PlaylistInfoDTO;
import com.bessonov.musicappserver.playlist.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class RecommendationService {
    @Autowired
    GenreRepository genreRepository;

    @Autowired
    TrackAudioFeatureExtractionTypeRepository trackAudioFeatureExtractionTypeRepository;

    @Autowired
    UserDataRepository userDataRepository;

    @Autowired
    UserNeuralNetworkConfigurationRepository userNeuralNetworkConfigurationRepository;

    @Autowired
    UserRatingRepository userRatingRepository;

    @Autowired
    UserRecommendationRepository userRecommendationRepository;

    @Autowired
    UserRecommendationGenreRepository userRecommendationGenreRepository;

    @Autowired
    UserRecommendationTrackRepository userRecommendationTrackRepository;

    @Autowired
    APIService apiService;

    @Autowired
    PlaylistService playlistService;

    public RecommendationInfoDTO getByUserRecommendationId(String username, int userRecommendationId) {
        Optional<UserRecommendation> userRecommendation = userRecommendationRepository.findById(userRecommendationId);

        return userRecommendation.map(value -> getUserRecommendationInfoByUserRecommendation(username, value)).orElse(null);
    }

    public List<RecommendationInfoDTO> getByUserRecommendationIdList(String username, List<Integer> userRecommendationIdList) {
        List<RecommendationInfoDTO> recommendationInfoDTOList = new ArrayList<>();

        for (Integer userRecommendationId : userRecommendationIdList) {
            recommendationInfoDTOList.add(getByUserRecommendationId(username, userRecommendationId));
        }

        return recommendationInfoDTOList;
    }

    public List<RecommendationInfoDTO> getAll(String username) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        List<UserRecommendation> userRecommendationList = userRecommendationRepository.findAll(sort);

        List<RecommendationInfoDTO> recommendationInfoDTOList = new ArrayList<>();

        for (UserRecommendation userRecommendation : userRecommendationList) {
            recommendationInfoDTOList.add(getUserRecommendationInfoByUserRecommendation(username, userRecommendation));
        }

        return recommendationInfoDTOList;
    }

    public List<RecommendationInfoDTO> getUserRecommendationList(String username) {
        Optional<UserData> userData = userDataRepository.findByUsername(username);

        if (userData.isEmpty()) {
            return null;
        }

        List<UserRecommendation> userRecommendationList = userRecommendationRepository.findByUserIdOrderByCreationDateAsc(userData.get().getId());

        List<RecommendationInfoDTO> recommendationInfoDTOList = new ArrayList<>();

        for (UserRecommendation userRecommendation : userRecommendationList) {
            recommendationInfoDTOList.add(getByUserRecommendationId(username, userRecommendation.getId()));
        }

        return recommendationInfoDTOList;
    }

    public RecommendationInfoDTO getUserRecommendationInfoByUserRecommendation(String username, UserRecommendation userRecommendation) {
        RecommendationInfoDTO recommendationInfoDTO = new RecommendationInfoDTO();

        recommendationInfoDTO.setRecommendation(new UserRecommendationDTO(userRecommendation));

        Optional<UserData> userData = userDataRepository.findByUsername(username);

        if (userData.isEmpty()) {
            return null;
        }

        Optional<UserData> ownerUserDate = userDataRepository.findById(userRecommendation.getUserId());

        if (ownerUserDate.isEmpty()) {
            return null;
        }

        if (userData.get() != ownerUserDate.get()) {
            return null;
        }

        recommendationInfoDTO.setUser(new UserDataShortDTO(ownerUserDate.get()));

        List<GenreDTO> genreDTOList = new ArrayList<>();
        List<UserRecommendationGenre> userRecommendationGenreList = userRecommendationGenreRepository.findByIdUserRecommendationIdOrderByIdGenreIdAsc(userRecommendation.getId());
        for (UserRecommendationGenre userRecommendationGenre : userRecommendationGenreList) {
            Optional<Genre> genre = genreRepository.findById(userRecommendationGenre.getId().getGenreId());
            genre.ifPresent(value -> genreDTOList.add(new GenreDTO(value)));
        }
        recommendationInfoDTO.setGenre(genreDTOList);

        List<Integer> trackIdList = new ArrayList<>();
        List<UserRecommendationTrack> userRecommendationTrackList = userRecommendationTrackRepository.findByIdUserRecommendationIdOrderByTrackNumberInRecommendationAsc(userRecommendation.getId());
        for (UserRecommendationTrack userRecommendationTrack : userRecommendationTrackList) {
            trackIdList.add(userRecommendationTrack.getId().getTrackId());
        }
        recommendationInfoDTO.setTrackId(trackIdList);

        Optional<UserRating> userRating = userRatingRepository.findById(userRecommendation.getUserRatingId());
        userRating.ifPresent(rating -> recommendationInfoDTO.setRating(new UserRatingDTO(rating)));

        return recommendationInfoDTO;
    }

    public RecommendationInfoDTO rateUserRecommendation(String username, int userRecommendationId, int rateId) {
        Optional<UserRating> userRating = userRatingRepository.findById(rateId);

        if (userRating.isEmpty()) {
            return null;
        }

        Optional<UserData> userData = userDataRepository.findByUsername(username);

        if (userData.isEmpty()) {
            return null;
        }

        Optional<UserRecommendation> userRecommendation = userRecommendationRepository.findById(userRecommendationId);

        if (userRecommendation.isEmpty()) {
            return null;
        }

        userRecommendation.get().setUserRatingId(rateId);
        userRecommendationRepository.save(userRecommendation.get());

        return getUserRecommendationInfoByUserRecommendation(username, userRecommendation.get());
    }

    public RecommendationInfoDTO createUserRecommendation(String username, RecommendationCreateDTO recommendationCreateDTO) {
        Optional<UserData> userData = userDataRepository.findByUsername(username);

        if (userData.isEmpty()) {
            return null;
        }
        recommendationCreateDTO.setUserId(userData.get().getId());

        Optional<TrackAudioFeatureExtractionType> trackAudioFeatureExtractionType =
                trackAudioFeatureExtractionTypeRepository.findById(recommendationCreateDTO.getExtractionTypeId());

        if (trackAudioFeatureExtractionType.isEmpty()) {
            return null;
        }

        if (recommendationCreateDTO.getSize() < 1 || recommendationCreateDTO.getSize() > 20) {
            recommendationCreateDTO.setSize(10);
        }

        if (recommendationCreateDTO.getFamiliarityPercentage() < 0 || recommendationCreateDTO.getFamiliarityPercentage() > 1) {
            recommendationCreateDTO.setFamiliarityPercentage(0.5);
        }

        List<Integer> genreIdList = new ArrayList<>();
        for (Integer genreId : recommendationCreateDTO.getGenreId()) {
            Optional<Genre> genre = genreRepository.findById(genreId);
            if (genre.isPresent()) {
                genreIdList.add(genreId);
            }
        }
        if (genreIdList.isEmpty()) {
            return null;
        }
        recommendationCreateDTO.setGenreId(genreIdList);

        RecommendationResponseDTO recommendationResponseDTO = apiService.sendPostRequestToCreateRecommendation(recommendationCreateDTO);

        return getByUserRecommendationId(username, recommendationResponseDTO.getId());
    }

    public RecommendationInfoDTO deleteUserRecommendation(String username, int userRecommendationId) {
        Optional<UserData> userData = userDataRepository.findByUsername(username);

        if (userData.isEmpty()) {
            return null;
        }

        Optional<UserRecommendation> userRecommendation = userRecommendationRepository.findById(userRecommendationId);

        if (userRecommendation.isEmpty()) {
            return null;
        }

        userRecommendationGenreRepository.deleteByIdUserRecommendationId(userRecommendationId);
        userRecommendationTrackRepository.deleteByIdUserRecommendationId(userRecommendationId);
        userRecommendationRepository.delete(userRecommendation.get());

        return new RecommendationInfoDTO();
    }

    public PlaylistInfoDTO addUserRecommendationAsPlaylist(String username, int userRecommendationId) {
        Optional<UserData> userData = userDataRepository.findByUsername(username);

        if (userData.isEmpty()) {
            return null;
        }

        Optional<UserRecommendation> userRecommendation = userRecommendationRepository.findById(userRecommendationId);

        if (userRecommendation.isEmpty()) {
            return null;
        }

        List<Integer> trackIdList = new ArrayList<>();
        List<UserRecommendationTrack> userRecommendationTrackList = userRecommendationTrackRepository.findByIdUserRecommendationIdOrderByTrackNumberInRecommendationAsc(userRecommendation.get().getId());
        for (UserRecommendationTrack userRecommendationTrack : userRecommendationTrackList) {
            trackIdList.add(userRecommendationTrack.getId().getTrackId());
        }

        PlaylistEditDTO playlistEditDTO = new PlaylistEditDTO("Rec №" + userRecommendationId, trackIdList);

        return playlistService.createPlaylist(username, playlistEditDTO);
    }

    public Boolean getUpdateUserNeuralNetworkStatus(String username, int extractionTypeId) {
        Optional<UserData> userData = userDataRepository.findByUsername(username);

        if (userData.isEmpty()) {
            return null;
        }

        Optional<TrackAudioFeatureExtractionType> trackAudioFeatureExtractionType =
                trackAudioFeatureExtractionTypeRepository.findById(extractionTypeId);

        if (trackAudioFeatureExtractionType.isEmpty()) {
            return null;
        }

        Optional<UserNeuralNetworkConfiguration> userNeuralNetworkConfiguration = userNeuralNetworkConfigurationRepository.findByUserIdAndTrackAudioFeatureExtractionTypeId(userData.get().getId(), extractionTypeId);

        if (userNeuralNetworkConfiguration.isEmpty()) {
            return true;
        }

        ZonedDateTime currentTime = ZonedDateTime.now();
        ZoneOffset offset = (ZoneId.systemDefault()).getRules().getOffset(java.time.Instant.now());

        Instant instant = userNeuralNetworkConfiguration.get().getTrainingDate().toInstant();
        ZonedDateTime previousTime = instant.atZone(ZoneId.systemDefault()).plusSeconds(offset.getTotalSeconds());

        Duration duration = Duration.between(previousTime, currentTime);

        return duration.toMinutes() >= 30;
    }

    public Boolean updateUserNeuralNetwork(String username, int extractionTypeId) {
        Optional<UserData> userData = userDataRepository.findByUsername(username);

        if (userData.isEmpty()) {
            return null;
        }

        Optional<TrackAudioFeatureExtractionType> trackAudioFeatureExtractionType =
                trackAudioFeatureExtractionTypeRepository.findById(extractionTypeId);

        if (trackAudioFeatureExtractionType.isEmpty()) {
            return null;
        }

        Optional<UserNeuralNetworkConfiguration> userNeuralNetworkConfiguration = userNeuralNetworkConfigurationRepository.findByUserIdAndTrackAudioFeatureExtractionTypeId(userData.get().getId(), extractionTypeId);

        if (userNeuralNetworkConfiguration.isPresent()) {
            ZonedDateTime currentTime = ZonedDateTime.now();
            ZoneOffset offset = (ZoneId.systemDefault()).getRules().getOffset(java.time.Instant.now());

            Instant instant = userNeuralNetworkConfiguration.get().getTrainingDate().toInstant();
            ZonedDateTime previousTime = instant.atZone(ZoneId.systemDefault()).plusSeconds(offset.getTotalSeconds());

            Duration duration = Duration.between(previousTime, currentTime);

            if (duration.toMinutes() < 30) {
                return false;
            }
        }

        return apiService.sendGetRequestToTrainNeuralNetworkByUserId(userData.get().getId(), extractionTypeId);
    }
}
