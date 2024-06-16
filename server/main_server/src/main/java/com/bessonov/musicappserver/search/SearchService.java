package com.bessonov.musicappserver.search;

import com.bessonov.musicappserver.album.AlbumService;
import com.bessonov.musicappserver.artist.ArtistService;
import com.bessonov.musicappserver.database.album.Album;
import com.bessonov.musicappserver.database.album.AlbumRepository;
import com.bessonov.musicappserver.database.artist.Artist;
import com.bessonov.musicappserver.database.artist.ArtistRepository;
import com.bessonov.musicappserver.database.playlist.Playlist;
import com.bessonov.musicappserver.database.playlist.PlaylistRepository;
import com.bessonov.musicappserver.database.track.Track;
import com.bessonov.musicappserver.database.track.TrackRepository;
import com.bessonov.musicappserver.database.userData.UserData;
import com.bessonov.musicappserver.database.userData.UserDataRepository;
import com.bessonov.musicappserver.playlist.PlaylistService;
import com.bessonov.musicappserver.track.TrackInfoDTO;
import com.bessonov.musicappserver.track.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class SearchService {
    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    PlaylistRepository playlistRepository;

    @Autowired
    TrackRepository trackRepository;

    @Autowired
    UserDataRepository userDataRepository;

    @Autowired
    AlbumService albumService;

    @Autowired
    ArtistService artistService;

    @Autowired
    PlaylistService playlistService;

    @Autowired
    TrackService trackService;

    public SearchInfoDTO searchByName(String name, String username) {
        Optional<UserData> userData = userDataRepository.findByUsername(username);

        if (userData.isEmpty()) {
            return null;
        }

        SearchInfoDTO searchInfoDTO = new SearchInfoDTO();

        List<Album> albumList = albumRepository.findByNameContainingOrderByNameAsc(name);
        List<Integer> albumIdList = new ArrayList<>();
        for (Album album : albumList) {
            albumIdList.add(album.getId());
        }
        searchInfoDTO.setFoundedAlbum(albumService.getByAlbumIdList(username, albumIdList));

        List<Artist> artistList = artistRepository.findByNameContainingOrderByNameAsc(name);
        List<Integer> artistIdList = new ArrayList<>();
        for (Artist artist : artistList) {
            artistIdList.add(artist.getId());
        }
        searchInfoDTO.setFoundedArtist(artistService.getByArtistIdList(username, artistIdList));

        List<Playlist> playlistList = playlistRepository.findByNameContainingOrderByNameAsc(name);
        List<Integer> playlistIdList = new ArrayList<>();
        for (Playlist playlist : playlistList) {
            playlistIdList.add(playlist.getId());
        }
        searchInfoDTO.setFoundedPlaylist(playlistService.getByPlaylistIdList(username, playlistIdList));

        List<Track> trackList = trackRepository.findByNameContainingOrderByNameAsc(name);
        List<Integer> trackIdList = new ArrayList<>();
        for (Track track : trackList) {
            trackIdList.add(track.getId());
        }
        searchInfoDTO.setFoundedTrack(trackService.getByTrackIdList(username, trackIdList));

        return searchInfoDTO;
    }

    public List<TrackInfoDTO> searchTrackByName(String name, String username) {
        Optional<UserData> userData = userDataRepository.findByUsername(username);

        if (userData.isEmpty()) {
            return null;
        }

        List<Track> trackList = trackRepository.findByNameContainingOrderByNameAsc(name);
        List<Integer> trackIdList = new ArrayList<>();
        for (Track track : trackList) {
            trackIdList.add(track.getId());
        }

        return trackService.getByTrackIdList(username, trackIdList);
    }
}
