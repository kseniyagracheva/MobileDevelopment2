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
import ru.mirea.gracheva.domain.usecases.authentification.auth.LogOutUseCase;
import ru.mirea.gracheva.ringstore.R;
import ru.mirea.gracheva.ringstore.databinding.FragmentUserInfoBinding;
import ru.mirea.gracheva.ringstore.presentation.viewmodel.userInfo.UserInfoViewModel;
import ru.mirea.gracheva.ringstore.presentation.viewmodel.userInfo.UserInfoViewModelFactory;

public class UserInfoFragment extends Fragment {
    private FragmentUserInfoBinding binding;
    private NavController navController;

    //private LogOutUseCase logOutUseCase;
    private AuthRepository authRepository;

    private UserInfoViewModel vm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(this, new UserInfoViewModelFactory()).get(UserInfoViewModel.class);
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

        vm.getUser().observe(getViewLifecycleOwner(), user -> {
            if(user==null){
                navController.navigate(R.id.action_userInfoFragment_to_authFragment);
            }
            else {
                binding.emailText.setText(user.getEmail() != null ? user.getEmail() : "Гость");
            }
        });


        binding.goToMetalsButton.setOnClickListener(v -> {
            navController.navigate(R.id.action_userInfoFragment_to_metalPriceFragment);
        });

        binding.goToRingsButton.setOnClickListener(v -> {
            navController.navigate(R.id.action_userInfoFragment_to_ringListFragment);
        });

        binding.logOutButton.setOnClickListener(v -> {
            vm.logout();
        });

        vm.loadUser();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
