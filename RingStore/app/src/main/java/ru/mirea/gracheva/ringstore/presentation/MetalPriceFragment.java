package ru.mirea.gracheva.ringstore.presentation;

import android.os.Bundle;
import android.widget.Toast;

import ru.mirea.gracheva.domain.usecases.metalInfo.GetMetalPriceInfo;
import ru.mirea.gracheva.data.repository.MetalPriceInfoRepositoryImpl;
import ru.mirea.gracheva.domain.models.MetalPriceInfo;
import ru.mirea.gracheva.domain.repository.MetalPriceInfoRepository;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.gracheva.ringstore.R;
import ru.mirea.gracheva.ringstore.databinding.FragmentMetalPriceBinding;
import ru.mirea.gracheva.ringstore.presentation.viewmodel.metalPriceInfo.MetalPriceInfoViewModel;
import ru.mirea.gracheva.ringstore.presentation.viewmodel.metalPriceInfo.MetalPriceInfoViewModelFactory;
import ru.mirea.gracheva.ringstore.presentation.viewmodel.register.RegisterViewModel;
import ru.mirea.gracheva.ringstore.presentation.viewmodel.register.RegisterViewModelFactory;

public class MetalPriceFragment extends Fragment {

    private FragmentMetalPriceBinding binding;
    private MetalPriceInfoViewModel vm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(this, new MetalPriceInfoViewModelFactory()).get(MetalPriceInfoViewModel.class);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMetalPriceBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm.getMetalPriveInfo().observe(getViewLifecycleOwner(), metalPriceInfo ->{
            binding.metalName.setText(metalPriceInfo.getMetalName());
            binding.price.setText(String.valueOf(metalPriceInfo.getPrice()));
            binding.currency.setText(metalPriceInfo.getCurrency());
            binding.lastUpdated.setText(metalPriceInfo.getLastUpdated());
        });
        vm.getError().observe(getViewLifecycleOwner(), error ->{
            Toast.makeText(getActivity(), "[translate:Ошибка:] " + error, Toast.LENGTH_LONG).show();
        });
        vm.fetch();

        binding.backButton.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}



