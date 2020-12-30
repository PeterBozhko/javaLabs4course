package com.company;

import java.util.*;

public class Collection {
    private List<Song> songs = new ArrayList<>();
    Collection(Song... list) {
        this.songs.addAll(Arrays.asList(list));
    }
    public void addSong(Song song) {
        songs.add(song);
    }

    public Set<Artist> getArtists(){
        HashSet<Artist> answer = new HashSet<>();
        for (Song song:songs){
            answer.add(song.getArtist());
        }
        return answer;
    }

    public List<Song> getSongs() {
        return songs;
    }
}
