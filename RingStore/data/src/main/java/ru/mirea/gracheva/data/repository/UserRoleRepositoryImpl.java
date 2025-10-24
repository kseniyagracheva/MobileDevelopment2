package ru.mirea.gracheva.data.repository;

import ru.mirea.gracheva.data.DTO.UserRoleDTO;
import ru.mirea.gracheva.data.storage.role.UserRoleDataSource;
import ru.mirea.gracheva.domain.models.UserRole;
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

    private UserRole mapToDomain(UserRoleDTO dto) {
        return UserRole.valueOf(dto.getUserRoleName());
    }

    private UserRoleDTO mapFromDomain(UserRole role) {
        return new UserRoleDTO(role.name());
    }
}

