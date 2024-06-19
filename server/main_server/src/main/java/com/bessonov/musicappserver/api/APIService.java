package com.bessonov.musicappserver.api;

import com.bessonov.musicappserver.recommendation.RecommendationCreateDTO;
import com.bessonov.musicappserver.recommendation.RecommendationResponseDTO;
import com.bessonov.musicappserver.utils.StatusResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class APIService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${neural_network_microservice.server.ip}")
    private String neuralNetworkMicroserviceServerIp;

    @Value("${neural_network_microservice.server.port}")
    private String neuralNetworkMicroserviceServerPort;

    public RecommendationResponseDTO sendPostRequestToCreateRecommendation(RecommendationCreateDTO recommendationCreateDTO) {
        String neuralNetworkMicroserviceServerURL =
                "http://" + neuralNetworkMicroserviceServerIp + ":" + neuralNetworkMicroserviceServerPort + "/";
        String url = neuralNetworkMicroserviceServerURL + "recommendation/get";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RecommendationCreateDTO> entity = new HttpEntity<>(recommendationCreateDTO, headers);

        ResponseEntity<RecommendationResponseDTO> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                RecommendationResponseDTO.class
        );

        return response.getBody();
    }

    public Boolean sendGetRequestToTrainNeuralNetworkByUserId(Integer userId, Integer extractionTypeId) {
        String neuralNetworkMicroserviceServerURL =
                "http://" + neuralNetworkMicroserviceServerIp + ":" + neuralNetworkMicroserviceServerPort + "/";
        String url = neuralNetworkMicroserviceServerURL + "/neural_network/train/user/" + userId + "/" + extractionTypeId;

        ResponseEntity<StatusResponseDTO> response = restTemplate.getForEntity(
                url,
                StatusResponseDTO.class
        );

        return Objects.equals(Objects.requireNonNull(response.getBody()).getStatus(), "success");
    }
}
