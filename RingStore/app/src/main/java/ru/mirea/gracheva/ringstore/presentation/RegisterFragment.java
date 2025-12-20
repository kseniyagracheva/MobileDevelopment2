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

import ru.mirea.gracheva.ringstore.R;
import ru.mirea.gracheva.ringstore.databinding.FragmentRegisterBinding;
import ru.mirea.gracheva.ringstore.presentation.viewmodel.register.RegisterViewModel;
import ru.mirea.gracheva.ringstore.presentation.viewmodel.register.RegisterViewModelFactory;

public class RegisterFragment extends Fragment {
    private FragmentRegisterBinding binding;
    private NavController navController;

    private RegisterViewModel vm;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(this, new RegisterViewModelFactory()).get(RegisterViewModel.class);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        vm.ifSuccess().observe(getViewLifecycleOwner(), success ->{
            if (success){
                Toast.makeText(requireContext(), "Регистрация успешна", Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.action_registerFragment_to_authFragment);
            }
        });
        vm.getError().observe(getViewLifecycleOwner(), error ->{
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
        });

        binding.registerButton.setOnClickListener(v -> {
            String email = binding.emailInput.getText().toString();
            String password = binding.passwordInput.getText().toString();

            vm.register(email,password);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
