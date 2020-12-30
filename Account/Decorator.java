package Account;

import Client.Client;

public abstract class Decorator implements AccountInterface{
    private AccountInterface accountInterface;

    public Decorator(AccountInterface account) {
        this.accountInterface = account;
    }

    @Override
    public double getMoney() {
        return this.accountInterface.getMoney();
    }

    @Override
    public void withdraw(double money) {
        this.accountInterface.withdraw(money);
    }

    @Override
    public void topUp(double money) {
        this.accountInterface.topUp(money);
    }

    @Override
    public void transfer(double money, AccountInterface accountInterface) {
        this.accountInterface.transfer(money, accountInterface);
    }

    @Override
    public void chargeInterest() {
        this.accountInterface.chargeInterest();
    }

    @Override
    public Client getClient() {
        return this.accountInterface.getClient();
    }

    @Override
    public void setTime(int time) {
        this.accountInterface.setTime(time);
    }

    @Override
    public void withdrawCommission() {
        this.accountInterface.withdrawCommission();
    }

    @Override
    public String toString() {
        return "Decorator{" +
                "accountInterface=" + accountInterface +
                '}';
    }
}
