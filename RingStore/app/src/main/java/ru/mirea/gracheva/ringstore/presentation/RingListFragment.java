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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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

        loadRings();

        binding.backToUserInfoButton.setOnClickListener(v ->
                navController.navigate(R.id.action_ringListFragment_to_userInfoFragment));
    }

    private void loadRings() {
        new AsyncTask<Void, Void, List<Ring>>() {
            private Exception exception;

            @Override
            protected List<Ring> doInBackground(Void... voids) {
                try {
                    return getRingsUseCase.execute();
                } catch (Exception e) {
                    exception = e;
                    return null;
                }
            }

            @Override
            protected void onPostExecute(List<Ring> rings) {
                if (exception != null) {
                    Toast.makeText(requireContext(), "Ошибка загрузки: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuilder displayText = new StringBuilder();
                for (Ring ring : rings) {
                    displayText.append("ID: ").append(ring.getRingId()).append("\n");
                    displayText.append("Металл: ").append(ring.getMetal()).append("\n");
                    displayText.append("Цена: ").append(ring.getPrice()).append(" руб.\n\n");
                }
                binding.ringsTextView.setText(displayText.toString());
            }
        }.execute();
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
