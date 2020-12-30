package com.company;

import Account.*;
import Client.Client;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Client Petr = new Client.Builder("Petr","Petrov").setAddress("St-Pet").setPassport("12345").buidl();
        Client Ivan = new Client.Builder("Ivan","Ivanov").setPassport("234567").buidl();
        Client Nikolay = new Client.Builder("Nikolay","Nikolaev").buidl();

        List<AccountInterface> accounts = new ArrayList<>();

        AccountInterface PetrAccount = new RealDecorator(FactoryAccount.getAccount(Petr,"Regular",10.0,null,null,null), 3000.0);
        AccountInterface PetrAccount2 = new RealDecorator(FactoryAccount.getAccount(Petr,"Deposit",null,null,10000,null), 3000.0);
        AccountInterface IvanAccount = new RealDecorator(FactoryAccount.getAccount(Ivan,"Credit",null,100.0,null,3000.0), 3000.0);
        AccountInterface NikolayAccount = new RealDecorator(FactoryAccount.getAccount(Nikolay, null,null,null,1000,null), 3000.0);


        accounts.add(PetrAccount);
        accounts.add(PetrAccount2);
        accounts.add(IvanAccount);
        accounts.add(NikolayAccount);

        printStat(accounts);
        PetrAccount.topUp(5000.0);
        printStat(accounts);
        PetrAccount.transfer(3000.0,PetrAccount2);
        printStat(accounts);
        NikolayAccount.topUp(10000.0);
        printStat(accounts);
        NikolayAccount.withdraw(3456.56);
        printStat(accounts);
        IvanAccount.topUp(6000.0);
        printStat(accounts);
        IvanAccount.withdraw(1999.9);
        printStat(accounts);
        IvanAccount.withdraw(3000.1);
        printStat(accounts);
        Request(PetrAccount, "chargeInterest");
        printStat(accounts);
        Request(IvanAccount, "withdrawCommission");
        printStat(accounts);
        IvanAccount.withdraw(2999.9);
        IvanAccount.withdraw(2999.9);
        printStat(accounts);
        Request(IvanAccount, "withdrawCommission");
        printStat(accounts);


    }
    public static void Request(AccountInterface account, String request){
        Handler handler, perHandler, errHandler;
        handler = new CommissionHandler();
        perHandler = handler.setNext(new PercentHandler());
        errHandler = perHandler.setNext(new ErrorHandler());
        handler.handleRequest(account,request);
    }
    private static void printStat(List<AccountInterface> list){
        for (AccountInterface account:list){
            System.out.println(account.toString());
            System.out.println();
        }
        System.out.println("------------------------------------------------------");
    }
}
