package com.bessonov.musicappserver.api;

import com.bessonov.musicappserver.recommendation.RecommendationCreateDTO;
import com.bessonov.musicappserver.recommendation.RecommendationResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {
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
}
