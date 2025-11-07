package ru.mirea.gracheva.domain.models;

public class User {
    private final String userId;
    private final String email;

    public User(String userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    public String getUserId() { return userId; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + userId + '\'' +
                ", email='" + email + '\'';
    }
}
