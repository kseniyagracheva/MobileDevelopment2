package ru.mirea.gracheva.ringstore.presentation;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mirea.gracheva.data.repository.MockNetworkApi;
import ru.mirea.gracheva.data.repository.RingRepositoryImpl;
import ru.mirea.gracheva.domain.models.Ring;
import ru.mirea.gracheva.domain.repository.NetworkApi;
import ru.mirea.gracheva.domain.repository.RingRepository;
import ru.mirea.gracheva.domain.usecases.rings.GetRingsUseCase;
import ru.mirea.gracheva.ringstore.R;
import ru.mirea.gracheva.ringstore.databinding.FragmentRingListBinding;

public class RingListFragment extends Fragment {
    private FragmentRingListBinding binding;
    private NavController navController;


    private GetRingsUseCase getRingsUseCase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRingListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        NetworkApi networkApi = new MockNetworkApi();
        RingRepository ringRepository = new RingRepositoryImpl(getContext(), networkApi);
        getRingsUseCase = new GetRingsUseCase(ringRepository);

        binding.ringsRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        loadRings();

        binding.backToUserInfoButton.setOnClickListener(v ->
                navController.navigate(R.id.action_ringListFragment_to_userInfoFragment));
    }

    private void loadRings() {
        getRingsUseCase.execute(new RingRepository.Callback<List<Ring>>() {
            @Override
            public void onSuccess(List<Ring> rings) {
                requireActivity().runOnUiThread(()->{
                    RingsAdapter adapter = new RingsAdapter(rings);
                    binding.ringsRecyclerView.setAdapter(adapter);
                });
            }
            @Override
            public void onError(Throwable t) {
                requireActivity().runOnUiThread(()->{
                    Toast.makeText(requireContext(),  "Ошибка загрузки: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

