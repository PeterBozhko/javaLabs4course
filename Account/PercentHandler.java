package Account;

public class PercentHandler extends Handler{
    @Override
    public void handleRequest(AccountInterface account,String request) {
        if (request.equals("chargeInterest")){
            account.chargeInterest();
        }else {
            next.handleRequest(account, request);
        }
    }
}
