package com.company;

public class Song {
    private Artist singer;
    private String name;
    private int date;

    Song (String name, Artist singer, int date){
        this.name = name;
        this.singer = singer;
        this.date = date;
    }

    public String getName(){
        return name;
    }
    public Artist getArtist(){
        return singer;
    }
    public int getDate() {
        return date;
    }
}
