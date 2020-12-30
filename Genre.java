package com.company;


import java.util.*;

public class Genre {
    public static Map<String, Genre> map = new HashMap<>();

    public static Genre HardRock = new Genre("HardRock");
    public static Genre Rock = new Genre("Rock", HardRock);
    public static Genre Deep_house = new Genre("Deep house");
    public static Genre Hard_bass = new Genre("Hard bass");
    public static Genre Pumping_house = new Genre("Pumping house", Hard_bass);
    public static Genre House = new Genre("House", Deep_house, Pumping_house);
    public static Genre Electric = new Genre("Electric", House);
    private String name;
    private ArrayList<Genre> subgenres = new ArrayList<>();

    private Genre (String name, Genre ... sub){
        this.name = name;
        this.subgenres.addAll(Arrays.asList(sub));
        map.put(name, this);
    }

    public static Genre findGenre(String name){
        return map.get(name);
    }
    public String getName() {
        return name;
    }
    public List<Genre> getAllSubgenres(){
        List<Genre> list = new ArrayList<>(subgenres);
        for (Genre genre:subgenres){
            list.addAll(genre.getAllSubgenres());
        }
        return list;
    }
}
