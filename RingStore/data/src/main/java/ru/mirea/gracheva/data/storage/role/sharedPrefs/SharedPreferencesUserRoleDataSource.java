package ru.mirea.gracheva.data.storage.role.sharedPrefs;

import android.content.Context;
import android.content.SharedPreferences;

import ru.mirea.gracheva.data.DTO.UserRoleDTO;
import ru.mirea.gracheva.data.storage.role.UserRoleDataSource;

public class SharedPreferencesUserRoleDataSource implements UserRoleDataSource {
    private static final String KEY_USER_ROLE = "user_role";
    private final SharedPreferences sharedPreferences;
    private Context context;
    public SharedPreferencesUserRoleDataSource(Context context) {
        sharedPreferences = context.getSharedPreferences(KEY_USER_ROLE, Context.MODE_PRIVATE);
    }

    @Override
    public UserRoleDTO getRole() {
        String roleName = sharedPreferences.getString(KEY_USER_ROLE, "GUEST");
        return new UserRoleDTO(roleName);
    }

    @Override
    public void saveRole(UserRoleDTO role) {
        sharedPreferences.edit().putString(KEY_USER_ROLE, role.getUserRoleName()).apply();
    }
}

