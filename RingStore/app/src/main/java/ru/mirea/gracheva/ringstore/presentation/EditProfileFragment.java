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
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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

        vm.getLoading().observe(getViewLifecycleOwner(), isLoading->{
            binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            binding.scrollView.setVisibility(isLoading ? View.GONE : View.VISIBLE);

            binding.saveUpdateButton.setEnabled(!isLoading);
            binding.deleteProfileButton.setEnabled(!isLoading);
        });

        vm.getUser().observe(getViewLifecycleOwner(), userUI ->{
            if (userUI != null && !vm.getLoading().getValue()){
                binding.editNameText.setText(userUI.getName());
                binding.editSurnameText.setText(userUI.getSurname());
                binding.emailText.setText(userUI.getEmail());
            }
        });



        vm.getError().observe(getViewLifecycleOwner(), e ->{
            if (e!=null){
                Toast.makeText(requireContext(), e, Toast.LENGTH_SHORT).show();;
                vm.clearError();
            }
        });
    }

    private void setupClickListeners(){
        binding.saveUpdateButton.setOnClickListener(v -> saveProfile());
        binding.deleteProfileButton.setOnClickListener(v -> deleteProfile());
        binding.backToProfileButton.setOnClickListener(v -> {
            navController.popBackStack();
            //navController.navigate(R.id.action_editProfileFragment_to_userInfoFragment);
        });
    }

    private void saveProfile(){
        String name =  binding.editNameText.getText().toString().trim();
        String surname = binding.editSurnameText.getText().toString().trim();


        if (name.isEmpty() || surname.isEmpty()){
            Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        vm.editProfile(name, surname);
        vm.loadCurrentUser();
    }

    private void deleteProfile (){
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Вы действительно хотите удалить аккаунт")
                .setMessage("Все данные будут удалены навсегда!")
                .setPositiveButton("Удалить", (dialog, which) -> vm.deleteProfile())
                .setNegativeButton("Отмена", null)
                .show();
        navController.popBackStack();
    }

    @Override
    public void onResume() {
        super.onResume();
        vm.loadCurrentUser();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}