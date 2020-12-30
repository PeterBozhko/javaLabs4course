package com.company;

import java.util.ArrayList;
import java.util.List;

public class Album {
    private List<Song> songs = new ArrayList<>();
    private Artist artist;
    private int date;

    Album(Song... list) {
        for (Song song:list){
            if (this.artist == null){
                this.artist = song.getArtist();
                this.date = song.getDate();
            }
            if (this.artist != song.getArtist()){
                throw new IllegalArgumentException("Songs have different artists");
            }
            else {
                if (this.date != song.getDate()){
                    throw new IllegalArgumentException("Songs have different dates");
                }
                else {
                    this.songs.add(song);
                }
            }
        }
    }

    public List<Song> getSongs() {
        return songs;
    }
    public Genre getGenre(){
        return artist.getGenre();
    }
    public Artist getArtist(){
        return artist;
    }
    public int getDate() {
        return songs.get(0).getDate();
    }
}
