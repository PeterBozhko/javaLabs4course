package Account;

public class ErrorHandler extends Handler{
    @Override
    public void handleRequest(AccountInterface account,String request) {
        try{
            throw new Exception("Invalid request");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
