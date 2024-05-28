package com.bessonov.musicappserver.track;

import com.bessonov.musicappserver.database.album.AlbumDTO;
import com.bessonov.musicappserver.database.artist.ArtistDTO;
import com.bessonov.musicappserver.database.genre.GenreDTO;
import com.bessonov.musicappserver.database.license.LicenseDTO;
import com.bessonov.musicappserver.database.track.TrackDTO;

import java.util.List;

public class TrackInfoDTO {
    private TrackDTO track;
    private List<ArtistDTO> artist;
    private List<ArtistDTO> featuredArtist;
    private List<AlbumDTO> album;
    private GenreDTO genre;
    private LicenseDTO license;

    public TrackDTO getTrack() {
        return track;
    }

    public void setTrack(TrackDTO track) {
        this.track = track;
    }

    public List<ArtistDTO> getArtist() {
        return artist;
    }

    public void setArtist(List<ArtistDTO> artist) {
        this.artist = artist;
    }

    public List<ArtistDTO> getFeaturedArtist() {
        return featuredArtist;
    }

    public void setFeaturedArtist(List<ArtistDTO> featuredArtist) {
        this.featuredArtist = featuredArtist;
    }

    public List<AlbumDTO> getAlbum() {
        return album;
    }

    public void setAlbum(List<AlbumDTO> album) {
        this.album = album;
    }

    public GenreDTO getGenre() {
        return genre;
    }

    public void setGenre(GenreDTO genre) {
        this.genre = genre;
    }

    public LicenseDTO getLicense() {
        return license;
    }

    public void setLicense(LicenseDTO license) {
        this.license = license;
    }

    @Override
    public String toString() {
        return "TrackInfoDTO{" +
                "track=" + track +
                ", artist=" + artist +
                ", featuredArtist=" + featuredArtist +
                ", album=" + album +
                ", genre=" + genre +
                ", license=" + license +
                '}';
    }
}
