package ru.mirea.gracheva.data.DTO;

public class UserRoleDTO {
    private String userRoleName;

    public UserRoleDTO(String userRoleName){
        this.userRoleName = userRoleName;
    }

    public String getUserRoleName(){
        return userRoleName;
    }

}
