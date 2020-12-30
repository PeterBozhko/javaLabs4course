package Account;

import Client.Client;

public class DepositAccount extends Account{

    public DepositAccount(Client client, int time) {
        super(client);
        this.time = time;
    }
    private int time;
    private double percent;

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public void withdraw(double money) {
        if (time == 0 && this.getMoney() > money){
            super.withdraw(money);
        }
    }
    public void chargeInterest(){
        double percentage = percent * this.getMoney() / 100;
        this.topUp(percentage);
    }

    @Override
    public void withdrawCommission() {

    }

    @Override
    public String toString() {
        return "DepositAccount{" +
                "time=" + time +
                ", percent=" + percent +
                ", client=" + client.getName() +
                ", money=" + getMoney() +
                '}';
    }
}
