package ru.mirea.gracheva.domain.models;

public class User {
    private final String userId;
    private final String email;
    private final String name;
    private final String surname;

    public User(String userId, String email, String name, String surname) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.surname = surname;
    }

    public String getUserId() { return userId; }

    public String getEmail() { return email; }

    public String getName() {return name;}
    public String getSurname(){return surname;}

}
