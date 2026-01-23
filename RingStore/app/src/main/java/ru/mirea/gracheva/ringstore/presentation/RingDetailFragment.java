package ru.mirea.gracheva.ringstore.presentation;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import ru.mirea.gracheva.domain.models.Ring;
import ru.mirea.gracheva.domain.repository.favoriets.FavoriteRepository;
import ru.mirea.gracheva.ringstore.R;
import ru.mirea.gracheva.ringstore.databinding.FragmentRingDetailBinding;
import ru.mirea.gracheva.ringstore.presentation.model.UserUI;
import ru.mirea.gracheva.ringstore.presentation.viewmodel.auth.AuthViewModel;
import ru.mirea.gracheva.ringstore.presentation.viewmodel.ringDetail.RingDetailViewModel;
import ru.mirea.gracheva.ringstore.presentation.viewmodel.ringDetail.RingDetailViewModelFactory;

public class RingDetailFragment extends Fragment {
    private FragmentRingDetailBinding binding;
    private RingDetailViewModel vm;

    private NavController navController;
    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRingDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        mainActivity = (MainActivity) requireActivity();


        String ringId = getArguments() != null ?
                getArguments().getString("ringId") : null;

        if (ringId == null) {
            Log.e("RingDetailFragment", "ringId is null!");
            return;
        }

        // ← Создаем ViewModel
        vm = new ViewModelProvider(this, new RingDetailViewModelFactory(requireContext()))
                .get(RingDetailViewModel.class);

        // ← Подписываемся на LiveData
        vm.getRingLiveData().observe(getViewLifecycleOwner(), ring -> {
            if (ring != null) {
                populateUI(ring);
            }
        });

        vm.getIsLoadingLiveData().observe(getViewLifecycleOwner(), isLoading -> {
            binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            binding.ringDetailContainer.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        });

        vm.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show();
        });

        vm.getRing(ringId);
    }



    private void populateUI(Ring ring) {
        binding.ringNameTextView.setText(ring.getName()); // или ring.getName() если есть
        binding.metalTextView.setText("Металл: " + ring.getMetal());
        binding.priceTextView.setText(ring.getPrice() + " ₽");

        Picasso.get()
                .load(ring.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .fit()
                .into(binding.ringImageView);

        binding.backToCatalogButton.setOnClickListener(v ->{
            navController.popBackStack();
        });
    }

    private void showAuthDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Требуется авторизация")
                .setMessage("Для добавления в избранное необходимо войти в аккаунт")
                .setPositiveButton("Войти", (dialog, which) -> {
                    NavController navController = Navigation.findNavController(requireView());
                    navController.navigate(R.id.action_ringDetailFragment_to_userInfoFragment);
                })
                .setNegativeButton("Отмена", null)
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

