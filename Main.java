package com.company;

public class Main {

    public static void main(String[] args) {
        System.out.println();
	    Artist n1 = new Artist("Jack", "Nikolson", Genre.Deep_house);
        Artist n2 = new Artist("James", "Carrey", Genre.Hard_bass);
        Artist n3 = new Artist("Elvis", "Presley", Genre.Electric);
	    Song s1 = new Song("s1", n1, 2017);
	    Song s2 = new Song("s2", n1,2017);
	    Song s3 = new Song("s3", n1,2017);
        Song ss1 = new Song("ss1", n2, 2018);
        Song ss2 = new Song("ss2", n2,2018);
        Song ss3 = new Song("ss3", n2,2018);
        Song sss1 = new Song("sss1", n3, 1968);
        Song sss2 = new Song("sss2", n3,1968);
        Song sss3 = new Song("sss3", n3,1968);
	    Album a1 = new Album(s1, s2, s3);
	    Album a2 = new Album(ss1,ss2,ss3);
	    Album a3 = new Album(sss1,sss2,sss3);
	    Collection c1 = new Collection(s1,s3,ss2,sss3);
	    Collection c2 = new Collection(ss1);
	    Catalog catalog = new Catalog();
	    catalog.addAlbum(a1);
        catalog.addAlbum(a2);
        catalog.addAlbum(a3);
        catalog.addCollection(c1);
        catalog.addCollection(c2);

	    /*for (Album album:catalog.findAlbums("House", null, null)){
            for (Song song:album.getSongs()){
                System.out.println(song.getName());
            }
        }
        System.out.println();
        for (Album album:catalog.findAlbums(null, "James Carrey", null)){
            for (Song song:album.getSongs()){
                System.out.println(song.getName());
            }
        }
        System.out.println();
        for (Album album:catalog.findAlbums(null, null, 1968)){
            for (Song song:album.getSongs()){
                System.out.println(song.getName());
            }
        }
        System.out.println();
        for (Artist artist:catalog.findArtists("House", null, null)) {
            System.out.println(artist.getName());
        }*/
        System.out.println();
        for (Collection collection:catalog.findCollections(null, "James Carrey",null)){
            for (Song song:collection.getSongs()){
                System.out.println(song.getName());
            }
        }
    }
}
