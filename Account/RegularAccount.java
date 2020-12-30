package Account;

import Client.Client;

public class RegularAccount extends Account{
    public RegularAccount(Client client, double percent) {
        super(client);
        this.percent = percent;
    }

    private final double percent;

    @Override
    public void withdraw(double money) {
        if (this.getMoney() > money){
            super.withdraw(money);
        }
    }

    @Override
    public void setTime(int time) {

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
        return "RegularAccount{" +
                "percent=" + percent +
                ", client=" + client.getName() +
                ", money=" + getMoney() +
                '}';
    }
}
