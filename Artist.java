package com.company;

public class Artist {
    private String name, surname;
    private Genre genre;
    Artist (String name, String surname, Genre genre){
        this.name = name;
        this.surname = surname;
        this.genre = genre;
    }
    public String getName(){
        return name + " " + surname;
    }
    public Genre getGenre(){
        return genre;
    }
}
