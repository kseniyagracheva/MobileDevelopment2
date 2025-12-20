package ru.mirea.gracheva.resultapifragmentapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DataFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_data, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText editText = view.findViewById(R.id.editTextData);
        Button button = view.findViewById(R.id.buttonOpenSheet);

        button.setOnClickListener(v -> {
            String dataToSend = editText.getText().toString();

            Bundle result = new Bundle();
            result.putString(BottomFragment.BUNDLE_KEY, dataToSend);

            BottomFragment bottomSheet = new BottomFragment();
            bottomSheet.show(getChildFragmentManager(), "MyBottomSheetFragment");

            getChildFragmentManager().setFragmentResult(BottomFragment.REQUEST_KEY, result);
        });
    }
}
