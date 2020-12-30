package com.company;

import java.util.ArrayList;

public class Polinom {
    private SetOfFractions list;
    Polinom(SetOfFractions a){
         list = a;
    }
    Polinom(ArrayList<RationalFraction> list){
        this.list = new SetOfFractions();
        for (RationalFraction i:list){
            this.list.add(i);
        }
    }
    Polinom(RationalFraction ... list){
        this.list = new SetOfFractions();
        for (RationalFraction i:list){
            this.list.add(i);
        }
    }
    public Polinom sum(Polinom b){
        ArrayList<RationalFraction> list = new ArrayList<>();
        ArrayList<RationalFraction> list1 = this.list.list;
        ArrayList<RationalFraction> list2 = b.list.list;
        if (list1.size() >= list2.size()){
            for (int i = 0; i < list1.size(); i++) {
                if (i < list2.size()) {
                    list.add(RationalFraction.rationalsum(list1.get(i), list2.get(i)));
                } else {
                    list.add(list1.get(i));
                }
            }
        }
        else{
            for (int i = 0; i < list2.size(); i++) {
                if (i < list1.size()) {
                    list.add(RationalFraction.rationalsum(list1.get(i), list2.get(i)));
                } else {
                    list.add(list2.get(i));
                }
            }
        }
        return new Polinom(list);
    }

    @Override
    public String toString() {
        return "" + list;
    }

    /*public void print(){
        list.print();
    }*/

}
