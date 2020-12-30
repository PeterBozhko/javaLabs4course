package Account;

import Client.Client;

public class FactoryAccount {
    private FactoryAccount() {
    }

    public static Account getAccount(Client client, String type, Double percent, Double commission, Integer time, Double creditLimit) {

        Account result = null;
        if (type != null){
            switch(type) {
                case "Regular":
                    result = new RegularAccount(client,percent);
                    break;
                case "Deposit":
                    result = new DepositAccount(client,time);
                    break;
                case "Credit":
                    result = new CreditAccount(client,commission,creditLimit);
                    break;
                default:
                    try {
                        throw new Exception("Wrong type of Account");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
            return result;
        }
        if (percent != null && commission == null && time == null && creditLimit == null){
            result = new RegularAccount(client,percent);
        } else if(percent == null && commission == null && time != null && creditLimit == null){
            result = new DepositAccount(client,time);
        } else if(percent == null && commission != null && time == null && creditLimit != null){
            result = new CreditAccount(client,commission,creditLimit);
        }
        return result;
    }
}
