package com.bessonov.musicappserver.search;

import com.bessonov.musicappserver.track.TrackInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search/")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @PostMapping("/name")
    public SearchInfoDTO searchByName(@RequestBody SearchRequestDTO name, Authentication authentication) {
        return searchService.searchByName(name.getName(), authentication.getName());
    }

    @PostMapping("/track/name")
    public List<TrackInfoDTO> searchTrackByName(@RequestBody SearchRequestDTO name, Authentication authentication) {
        return searchService.searchTrackByName(name.getName(), authentication.getName());
    }
}
