package ru.mirea.gracheva.ringstore.presentation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.mirea.gracheva.ringstore.R;
import ru.mirea.gracheva.ringstore.databinding.FragmentEditProfileBinding;
import ru.mirea.gracheva.ringstore.presentation.viewmodel.editProfile.EditProfileViewModel;
import ru.mirea.gracheva.ringstore.presentation.viewmodel.editProfile.EditProfileViewModelFactory;

public class EditProfileFragment extends Fragment {
    private FragmentEditProfileBinding binding;
    private NavController navController;

    private EditProfileViewModel vm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(this, new EditProfileViewModelFactory()).get(EditProfileViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        observeViewModel();
        setupClickListeners();

    }

    private void observeViewModel(){
        vm.getUser().observe(getViewLifecycleOwner(), userUI ->{
            if (userUI != null){
                binding.editNameText.setText(userUI.getName());
                binding.editSurnameText.setText(userUI.getSurname());
                binding.emailText.setText(userUI.getEmail());
            }
        });

        vm.getLoading().observe(getViewLifecycleOwner(), isLoading->{
            binding.
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}