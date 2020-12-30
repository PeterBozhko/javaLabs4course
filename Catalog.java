package com.company;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Catalog {
    private Set<Album> albums = new HashSet<>();
    public void addAlbum(Album name){
        this.albums.add(name);
    }
    private Set<Collection> collections = new HashSet<>();
    public void addCollection(Collection name){
        this.collections.add(name);
    }

    public Set<Artist> findArtists(String genre, String name_surname, Integer date){
        Set<Artist> answer = new HashSet<>();
        List<Genre> genres = null;
        if (genre != null){
            Genre G = Genre.findGenre(genre);
            if (G == null) {
                throw new IllegalArgumentException("Genre wasn't found");
            }
            genres = G.getAllSubgenres();
            genres.add(G);
        }
        for (Album album:albums) {
            if (name_surname == null || name_surname == album.getArtist().getName()) {
                if (date == null || date == album.getDate()) {
                    if (genre != null) {
                        for (Genre g : genres) {
                            if (album.getGenre() == g) {
                                answer.add(album.getArtist());
                                break;
                            }
                        }
                    }
                    else{
                        answer.add(album.getArtist());
                    }
                }
            }
        }
        return answer;
    }

    public Set<Album> findAlbums(String genre, String artist, Integer date){
        Set<Album> answer = new HashSet<>();
        List<Genre> genres = new ArrayList<>();
        if (genre != null){
            Genre G = Genre.findGenre(genre);
            if (G == null) {
                throw new IllegalArgumentException("Genre wasn't found");
            }
            genres = G.getAllSubgenres();
            genres.add(G);
        }
        for(Album album:albums){
            if (artist == null || album.getArtist().getName().equals(artist)){
                if ( date == null || album.getDate() == date){
                    if (genre != null){
                        for (Genre g : genres) {
                            if (album.getGenre() == g) {
                                answer.add(album);
                                break;
                            }
                        }
                    }
                    else {
                        answer.add(album);
                    }
                }
            }
        }
        return answer;
    }

    public Set<Collection> findCollections(String genre, String artist, Integer date){
        Set<Collection> answer = new HashSet<>();
        List<Genre> genres = null;
        if (genre != null){
            Genre G = Genre.findGenre(genre);
            if (G == null) {
                throw new IllegalArgumentException("Genre wasn't found");
            }
            genres = G.getAllSubgenres();
            genres.add(G);
        }
        for(Collection collection: collections){
            List<Song> songs = collection.getSongs();
            for (Song song:songs){
                if ((artist != null && song.getArtist().getName().equals(artist)) || (date != null && song.getDate() == date)) {
                    answer.add(collection);
                    break;
                }
                else {
                    if (genre != null){
                        for (Genre g:genres){
                            if (g == song.getArtist().getGenre()){
                                answer.add(collection);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return answer;
    }
    public List<Song> findSongs(String genre, String artist, Integer date){
        List<Song> answer = new ArrayList<>();
        List<Genre> genres = null;
        if (genre != null){
            Genre G = Genre.findGenre(genre);
            if (G == null) {
                throw new IllegalArgumentException("Genre wasn't found");
            }
            genres = G.getAllSubgenres();
            genres.add(G);
        }
        for (Album album:albums) {
            if (date == null || date == album.getDate()) {
                if (artist == null || album.getArtist().getName() == artist) {
                    if (genre != null) {
                        for (Genre g : genres) {
                            if (album.getGenre() == g) {
                                answer.addAll(album.getSongs());
                                break;
                            }
                        }
                    } else {
                        answer.addAll(album.getSongs());
                    }
                } else {
                    break;
                }
            }
        }
        return answer;
    }
}
