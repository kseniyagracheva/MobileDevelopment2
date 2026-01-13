package ru.mirea.gracheva.ringstore.presentation;

import android.os.Bundle;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.mirea.gracheva.ringstore.R;
import ru.mirea.gracheva.ringstore.databinding.FragmentMetalPriceBinding;
import ru.mirea.gracheva.ringstore.presentation.viewmodel.metalPriceInfo.MetalPriceInfoViewModel;
import ru.mirea.gracheva.ringstore.presentation.viewmodel.metalPriceInfo.MetalPriceInfoViewModelFactory;

public class MetalPriceFragment extends Fragment {

    private FragmentMetalPriceBinding binding;
    private MetalPriceInfoViewModel vm;
    private NavController navController;
    private MetalPriceAdapter adapter;

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

        navController = Navigation.findNavController(view);

        binding.metalPriceRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        adapter = new MetalPriceAdapter(new ArrayList<>());
        binding.metalPriceRecyclerView.setAdapter(adapter);

        vm.getMetalPriceInfo().observe(getViewLifecycleOwner(), metalPriceInfoList -> {
            adapter.updateData(metalPriceInfoList);
        });
        vm.getError().observe(getViewLifecycleOwner(), error -> {
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
        });

        vm.fetch();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}



