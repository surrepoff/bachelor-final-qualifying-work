package com.bessonov.musicappserver.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search/")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/name")
    public SearchInfoDTO searchByName(@RequestBody String name, Authentication authentication) {
        return searchService.searchByName(name, authentication.getName());
    }
}
