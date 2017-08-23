package com.jdkgroup.pattern;

public class User {
    private String firstname;
    private String lastname;
    private String phone;
    private String address;
    private int age;

     public User(UserBuilder builder) {
        this.firstname = builder.firstname;
        this.lastname = builder.lastname;
        this.age = builder.age;
        this.phone = builder.phone;
        this.address = builder.address;
    }

    public String getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPhone() {
        return phone;
    }

    public static class UserBuilder {
        private String firstname;
        private String lastname;
        private String phone;
        private String address;
        private int age;

        public UserBuilder(String firstname, String lastname) {
            this.firstname = firstname;
            this.lastname = lastname;
        }

        public UserBuilder age(int age) {
            this.age = age;
            return this;
        }

        public UserBuilder address(String address) {
            this.address = address;
            return this;
        }

        public UserBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public User user() {
            User user = new User(this);
            return user;
        }
    }
}
