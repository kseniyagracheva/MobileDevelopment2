package ru.mirea.gracheva.data.storage.role;

import ru.mirea.gracheva.data.DTO.UserRoleDTO;

public interface UserRoleDataSource {
    String getRole();
    void saveRole(UserRoleDTO role);
}
