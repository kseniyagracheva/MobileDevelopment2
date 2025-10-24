package ru.mirea.gracheva.domain.repository.auth;

import ru.mirea.gracheva.domain.models.UserRole;

public interface UserRoleRepository {
    public void saveRole(UserRole role);
}
