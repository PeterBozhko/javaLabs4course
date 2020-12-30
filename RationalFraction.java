package com.company;

public class RationalFraction {
    private int num, den, intPart = 0;
    RationalFraction (int a, int b) throws ArithmeticException{
        if (b == 0){
            throw new ArithmeticException("Error. Denum is 0");
        }
        this.num = a;
        this.den = b;
        getMixedView();
    }
    private void getMixedView(){
        calculateIntPart();
        int divisor = cancellation(num, den);
        num/=divisor;
        den/=divisor;
    }
    private void calculateIntPart(){ //выделение целой части
        if (num >= den){
            intPart += num / den;
            num %= den;
        }
    }
    private static int cancellation(int n, int d) { //Сокращение дроби по алгоритму Евклида(поиск НОД)
        return (d == 0 ? n : cancellation(d, n % d));
    }

    @Override
    public String toString() {
        if (num == 0){
            return "" +intPart;
        }
        else {
            if (intPart != 0) {
                return intPart + " + " + num + "/" + den;
            }
            return num + "/" + den;
        }
    }

    public  int getNum(){
        return intPart *den + num;
    }

    public static RationalFraction rationalsum(RationalFraction a, RationalFraction b){
        return new RationalFraction( a.getNum() * b.den + b.getNum() * a.den, a.den * b.den);
    }
    public static RationalFraction rationalsub(RationalFraction a, RationalFraction b){
        return new RationalFraction( a.getNum() * b.den - b.getNum() * a.den, a.den * b.den);
    }
    public static RationalFraction rationalmul(RationalFraction a, RationalFraction b){
        return new RationalFraction(a.getNum() * b.getNum(), a.den * b.den);
    }
    public static RationalFraction rationaldiv(RationalFraction a, RationalFraction b){
        return new RationalFraction(a.getNum() * b.den, a.den * b.getNum());
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        RationalFraction b = (RationalFraction) obj;
        if (this.getNum() != b.getNum() || this.den != b.den) return false;
        else return true;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + num;
        result = prime * result + den;
        result = prime * result + intPart;
        return result;
    }

    public int rationalcmp(RationalFraction b){
        if (!this.equals(b)) {
            if ((this.getNum() * b.den) > (b.getNum() * this.den)) return 1;
            else return -1;
        } else return 0;
    }
}