package com.bessonov.musicappserver.recommendation;

import com.bessonov.musicappserver.playlist.PlaylistInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {
    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/get/{userRecommendationId}")
    public RecommendationInfoDTO getByUserRecommendationId(@PathVariable Integer userRecommendationId, Authentication authentication) {
        return recommendationService.getByUserRecommendationId(authentication.getName(), userRecommendationId);
    }

    @GetMapping("/get/user")
    public List<RecommendationInfoDTO> getUserRecommendationList(Authentication authentication) {
        return recommendationService.getUserRecommendationList(authentication.getName());
    }

    @PostMapping("/rate/{userRecommendationId}")
    public RecommendationInfoDTO rateUserRecommendation(@PathVariable Integer userRecommendationId, @RequestBody Integer rateId, Authentication authentication) {
        return recommendationService.rateUserRecommendation(authentication.getName(), userRecommendationId, rateId);
    }

    @PostMapping("/create")
    public RecommendationInfoDTO createUserRecommendation(@RequestBody RecommendationCreateDTO recommendationCreateDTO, Authentication authentication) {
        return recommendationService.createUserRecommendation(authentication.getName(), recommendationCreateDTO);
    }

    @GetMapping("/delete/{userRecommendationId}")
    public RecommendationInfoDTO deleteUserRecommendation(@PathVariable Integer userRecommendationId, Authentication authentication) {
        return recommendationService.deleteUserRecommendation(authentication.getName(), userRecommendationId);
    }

    @GetMapping("/add/{userRecommendationId}")
    public PlaylistInfoDTO addUserRecommendationAsPlaylist(@PathVariable Integer userRecommendationId, Authentication authentication) {
        return recommendationService.addUserRecommendationAsPlaylist(authentication.getName(), userRecommendationId);
    }

    @GetMapping("/update/status/{extractionTypeId}")
    public Boolean getUpdateUserNeuralNetworkStatus(@PathVariable Integer extractionTypeId, Authentication authentication) {
        return recommendationService.getUpdateUserNeuralNetworkStatus(authentication.getName(), extractionTypeId);
    }

    @GetMapping("/update/{extractionTypeId}")
    public Boolean updateUserNeuralNetwork(@PathVariable Integer extractionTypeId, Authentication authentication) {
        return recommendationService.updateUserNeuralNetwork(authentication.getName(), extractionTypeId);
    }
}
