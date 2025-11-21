package ru.mirea.gracheva.ringstore.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import ru.mirea.gracheva.domain.repository.auth.AuthRepository;
import ru.mirea.gracheva.domain.repository.auth.UserRoleRepository;
import ru.mirea.gracheva.domain.usecases.authentification.auth.LogOutUseCase;
import ru.mirea.gracheva.ringstore.R;
import ru.mirea.gracheva.ringstore.databinding.FragmentUserInfoBinding;
import ru.mirea.gracheva.ringstore.presentation.viewmodel.userInfo.UserInfoViewModel;
import ru.mirea.gracheva.ringstore.presentation.viewmodel.userInfo.UserInfoViewModelFactory;

public class UserInfoFragment extends Fragment {
    private FragmentUserInfoBinding binding;
    private NavController navController;

    private LogOutUseCase logOutUseCase;

    private UserRoleRepository userRoleRepository;
    private AuthRepository authRepository;

    private UserInfoViewModel vm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(this, new UserInfoViewModelFactory(requireContext())).get(UserInfoViewModel.class);


        logOutUseCase = new LogOutUseCase(authRepository);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUserInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        vm.getUserRole().observe(getViewLifecycleOwner(), currentRole -> {
            Bundle args = getArguments();
            if (args != null) {
                String email = args.getString("email", "");
                String role = args.getString("role", "");
                binding.emailText.setText(email.isEmpty() ? "Гость" : email);
                binding.roleText.setText("Роль: " + role);
            }else {
                binding.roleText.setText("Роль: " + (currentRole != null ? currentRole.name(): "Гость"));
            }
        });

        vm.loadUserRole();

        binding.goToMetalsButton.setOnClickListener(v -> {
            navController.navigate(R.id.action_userInfoFragment_to_metalPriceFragment);
        });

        binding.goToRingsButton.setOnClickListener(v -> {
            navController.navigate(R.id.action_userInfoFragment_to_ringListFragment);
        });

        vm.ifSuccess().observe(getViewLifecycleOwner(), success ->{
            if (success){
                Toast.makeText(requireContext(), "Вы вышли из аккаунта", Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.action_userInfoFragment_to_authFragment);
            }
        });
        vm.getError().observe(getViewLifecycleOwner(), error ->{
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
        });
        binding.logOutButton.setOnClickListener(v -> {
            vm.logout();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
