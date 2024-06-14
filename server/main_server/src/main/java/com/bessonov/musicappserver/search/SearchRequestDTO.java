package com.bessonov.musicappserver.search;

public class SearchRequestDTO {
    private String name;

    public SearchRequestDTO() {
    }

    public SearchRequestDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SearchRequestDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
