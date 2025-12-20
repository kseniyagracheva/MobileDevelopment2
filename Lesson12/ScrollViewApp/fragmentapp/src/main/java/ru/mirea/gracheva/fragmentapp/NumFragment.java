package ru.mirea.gracheva.fragmentapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class NumFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(NumFragment.class.getSimpleName(), "onCreateView");
        View view   =  inflater.inflate(R.layout.fragment_num,  container, false);
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        int numberStudent = requireArguments().getInt("my_number_student", 0);
        Log.d(NumFragment.class.getSimpleName(), String.valueOf(numberStudent));
        TextView textView = view.findViewById(R.id.textNumber);
        textView.setText("Номер по списку: " + numberStudent);
    }
}
