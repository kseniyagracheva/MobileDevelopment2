package ru.mirea.gracheva.domain.models;

public class User {
    private final String userId;
    private final String email;
    private final UserRole role;

    public User(String userId, String email, UserRole role) {
        this.userId = userId;
        this.email = email;
        this.role = role;
    }

    public String getUserId() { return userId; }
    public String getEmail() { return email; }
    public UserRole getRole() { return role; }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
