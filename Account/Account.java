package Account;

import Client.Client;

public abstract class Account implements AccountInterface{
    final Client client;
    private double money;

    public Account(Client client) {
        this.client = client;
        this.money = 0;
    }

    //Снятие
    public void withdraw(double money) {
        if (money > 0) {
            this.money -= money;
        }
    }
    //Пополнение
    public void topUp(double money){
        if (money > 0){
            this.money += money;
        }
    }

    @Override
    public void setTime(int time) {
    }

    @Override
    public void chargeInterest() {

    }

    public void transfer (double money, AccountInterface accountInterface){
        if (accountInterface.getClient() == this.client){
            this.withdraw(money);
            //Нужна проверка на наличие денег, для транзакции
            accountInterface.topUp(money);
        }
    }

    public double getMoney(){
        return money;
    }

    public Client getClient() {
        return this.client;
    }

    @Override
    public String toString() {
        return "Account{" +
                "client=" + client.getName() +
                ", money=" + money +
                '}';
    }
}
