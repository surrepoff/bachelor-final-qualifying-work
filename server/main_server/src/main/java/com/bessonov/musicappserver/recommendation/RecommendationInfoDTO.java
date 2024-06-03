package com.bessonov.musicappserver.recommendation;

import com.bessonov.musicappserver.database.genre.GenreDTO;
import com.bessonov.musicappserver.database.userData.UserDataShortDTO;
import com.bessonov.musicappserver.database.userRating.UserRatingDTO;
import com.bessonov.musicappserver.database.userRecommendation.UserRecommendationDTO;

import java.util.List;

public class RecommendationInfoDTO {
    private UserRecommendationDTO recommendation;
    private UserDataShortDTO user;
    private List<GenreDTO> genre;
    private List<Integer> trackId;
    private UserRatingDTO rating;

    public UserRecommendationDTO getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(UserRecommendationDTO recommendation) {
        this.recommendation = recommendation;
    }

    public UserDataShortDTO getUser() {
        return user;
    }

    public void setUser(UserDataShortDTO user) {
        this.user = user;
    }

    public List<GenreDTO> getGenre() {
        return genre;
    }

    public void setGenre(List<GenreDTO> genre) {
        this.genre = genre;
    }

    public List<Integer> getTrackId() {
        return trackId;
    }

    public void setTrackId(List<Integer> trackId) {
        this.trackId = trackId;
    }

    public UserRatingDTO getRating() {
        return rating;
    }

    public void setRating(UserRatingDTO rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "RecommendationInfoDTO{" +
                "recommendation=" + recommendation +
                ", user=" + user +
                ", genre=" + genre +
                ", trackId=" + trackId +
                ", rating=" + rating +
                '}';
    }
}
