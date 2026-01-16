package ru.mirea.gracheva.data.DTO;


public class UserDTO {
    private String userId;
    private String email;
    private String name;
    private String surname;

    public UserDTO(String userId, String email, String name, String surname){
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.surname = surname;

    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getName(){return name;}
    public String getSurname(){return surname;}
}
