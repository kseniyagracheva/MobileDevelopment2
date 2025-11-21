package ru.mirea.gracheva.ringstore.presentation;

import android.os.AsyncTask;
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
import androidx.recyclerview.widget.GridLayoutManager;

import ru.mirea.gracheva.ringstore.R;
import ru.mirea.gracheva.ringstore.databinding.FragmentRingListBinding;
import ru.mirea.gracheva.ringstore.presentation.viewmodel.rings.RingListViewModel;
import ru.mirea.gracheva.ringstore.presentation.viewmodel.rings.RingListViewModelFactory;

public class RingListFragment extends Fragment {
    private FragmentRingListBinding binding;
    private NavController navController;

    private RingListViewModel vm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRingListBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this, new RingListViewModelFactory(requireContext())).get(RingListViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        binding.ringsRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        vm.getRingsLiveData().observe(getViewLifecycleOwner(), rings->{
            requireActivity().runOnUiThread(()->{
                RingsAdapter adapter = new RingsAdapter(rings);
                binding.ringsRecyclerView.setAdapter(adapter);
            });
        });
        vm.getErrorLiveData().observe(getViewLifecycleOwner(), error ->{
            Toast.makeText(requireContext(),  error, Toast.LENGTH_SHORT).show();
        });

        vm.getRings();

        binding.backToUserInfoButton.setOnClickListener(v ->
                navController.navigate(R.id.action_ringListFragment_to_userInfoFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

