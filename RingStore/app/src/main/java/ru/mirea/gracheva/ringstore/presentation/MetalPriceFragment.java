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
import androidx.fragment.app.Fragment;
import ru.mirea.gracheva.ringstore.databinding.FragmentMetalPriceBinding;

public class MetalPriceFragment extends Fragment {

    private FragmentMetalPriceBinding binding;
    private GetMetalPriceInfo getMetalPriceInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMetalPriceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getMetalPriceInfo = new GetMetalPriceInfo(new MetalPriceInfoRepositoryImpl());

        getMetalPriceInfo.fetchMetalPriceInfo(new MetalPriceInfoRepository.MetalPriceCallback() {
            @Override
            public void onSuccess(MetalPriceInfo metalPriceInfo) {
                if(getActivity() == null) return;
                getActivity().runOnUiThread(() -> {
                    binding.metalName.setText(metalPriceInfo.getMetalName());
                    binding.price.setText(String.valueOf(metalPriceInfo.getPrice()));
                    binding.currency.setText(metalPriceInfo.getCurrency());
                    binding.lastUpdated.setText(metalPriceInfo.getLastUpdated());
                });
            }

            @Override
            public void onError(String errorMessage) {
                if(getActivity() == null) return;
                getActivity().runOnUiThread(() ->
                        Toast.makeText(getActivity(), "[translate:Ошибка:] " + errorMessage, Toast.LENGTH_LONG).show()
                );
            }
        });

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



