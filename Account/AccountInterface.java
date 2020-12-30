package Account;

import Client.Client;

import javax.xml.ws.handler.Handler;

public interface AccountInterface extends Handler {
    void withdraw(double money);
    void topUp(double money);
    void transfer(double money, AccountInterface accountInterface);
    void setTime(int time);
    void chargeInterest();
    void withdrawCommission();
    double getMoney();
    Client getClient();

}
//imp;ements Handler
//уточнить шаблонный метод
//деократор фабрики