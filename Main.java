package com.company;


public class Main {

    public static void main(String[] args) {
        Reader r1 = new Reader("test.ini");
        r1.printContent();
        System.out.println();
        String s = r1.getString("sec1","par10");
        System.out.println("sec1 - par10 = (" + s.getClass().getSimpleName() + ") " + s);
        Integer i = r1.getInteger("sec0", "par01");
        System.out.println("sec0 - par01 = (" + i.getClass().getSimpleName() + ") " + i);
        Double d = r1.getDouble("sec3", "par30");
        System.out.println("sec0 - par01 = (" + d.getClass().getSimpleName() + ") " + d);
    }
}
