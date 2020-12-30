package Client;


public class Client {
    private final String name;
    private final String surname;
    private final String address;
    private final String passport;

    public static class Builder {
        // Обязательные параметры
        private final String name;
        private final String surname;

        // Необязательные параметры с значениями по умолчанию
        private String address;
        private String passport = null;

        public Builder(String name, String surname) {
            this.name = name;
            this.surname = surname;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setPassport(String passport) {
            this.passport = passport;
            return this;
        }

        public Client buidl() {
            return new Client(this);
        }
    }

    private Client(Builder builder) {
        name = builder.name;
        surname = builder.surname;
        address = builder.address;
        passport = builder.passport;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public String getPassport() {
        return passport;
    }
}
