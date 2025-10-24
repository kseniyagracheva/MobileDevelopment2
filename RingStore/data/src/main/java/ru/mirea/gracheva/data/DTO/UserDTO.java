package ru.mirea.gracheva.data.DTO;


public class UserDTO {
    private String userId;
    private String email;
    private UserRoleDTO userRoleDTO;

    public UserDTO(String userId, String email, UserRoleDTO userRoleDTO){
        this.userId = userId;
        this.email = email;
        this.userRoleDTO = userRoleDTO;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public UserRoleDTO getUserRoleDTO() {
        return userRoleDTO;
    }
}
