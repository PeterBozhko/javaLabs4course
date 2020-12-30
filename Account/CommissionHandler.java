package Account;

public class CommissionHandler extends Handler{
    public void handleRequest(AccountInterface account, String request) {
        if (request.equals("withdrawCommission")){
            account.withdrawCommission();
        }else {
            next.handleRequest(account,request);
        }
    }
}
