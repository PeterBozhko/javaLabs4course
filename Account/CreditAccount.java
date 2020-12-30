package Account;

import Client.Client;

public class CreditAccount extends Account {
    public CreditAccount(Client client, double commission, double creditLimit) {
        super(client);
        this.commission = commission;
        this.creditLimit = creditLimit;
    }
    private final double commission;

    private double creditLimit;
    @Override
    public void withdraw(double money) {
        if (this.getMoney() > money-this.creditLimit){
            super.withdraw(money);
        }
    }

    @Override
    public void transfer(double money, AccountInterface account) {
        if (this.getMoney() > money-this.creditLimit){
            super.transfer(money, account);
        }
    }

    @Override
    public void withdrawCommission() {
        if (getMoney() < 0)
            super.withdraw(commission);
    }

    @Override
    public void topUp(double money) {
        super.topUp(money);
    }

    @Override
    public String toString() {
        return "CreditAccount{" +
                "commission=" + commission +
                ", creditLimit=" + creditLimit +
                ", client=" + client.getName() +
                ", money=" + getMoney() +
                '}';
    }
}
