package Account;

public class RealDecorator extends Decorator {
    private double limit;
    private Account account;
    public RealDecorator(Account account, Double limit) {
        super(account);
        this.account = account;
        this.limit = limit;
    }

    @Override
    public void withdraw(double money) {
        if (account.client.getPassport() == null || account.client.getAddress() == null){
            if (money <limit){
                super.withdraw(money);
            }
        }
    }


    @Override
    public void transfer(double money, AccountInterface account) {
        if (this.account.client.getPassport() == null || this.account.client.getAddress() == null){
            if (money <limit){
                super.transfer(money,account);
            }
        }
    }

}
