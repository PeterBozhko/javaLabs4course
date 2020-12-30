package Account;

public abstract class Handler implements HandlerInterface {
    protected Account account;
    protected String request;
    @Override
    public void handleRequest(AccountInterface account, String request) {

    }
    protected Handler next;

    public Handler setNext(Handler next) {
        this.next = next;
        return next;
    }

}
