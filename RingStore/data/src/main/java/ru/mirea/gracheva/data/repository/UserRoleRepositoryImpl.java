package ru.mirea.gracheva.data.repository;

import static java.lang.String.valueOf;

import ru.mirea.gracheva.data.DTO.UserRoleDTO;
import ru.mirea.gracheva.data.storage.role.UserRoleDataSource;
import ru.mirea.gracheva.domain.models.User;
import ru.mirea.gracheva.domain.models.UserRole;
import ru.mirea.gracheva.domain.repository.UserRepository;
import ru.mirea.gracheva.domain.repository.auth.UserRoleRepository;

public class UserRoleRepositoryImpl implements UserRoleRepository {
    private final UserRoleDataSource userRoleDataSource;

    public UserRoleRepositoryImpl(UserRoleDataSource userRoleDataSource) {
        this.userRoleDataSource = userRoleDataSource;
    }
    @Override
    public void saveRole(UserRole role) {
        UserRoleDTO userRoleDTO = mapFromDomain(role);
        userRoleDataSource.saveRole(userRoleDTO);
    }
    @Override
    public UserRole getRole(){
        return UserRole.valueOf(userRoleDataSource.getRole());
    };
    private UserRoleDTO mapFromDomain(UserRole role) {
        return new UserRoleDTO(role.name());
    }
}

