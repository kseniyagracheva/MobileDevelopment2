package ru.mirea.gracheva.fragmentmanagerapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Country> selectedCountry = new MutableLiveData<>();

    public void selectCountry(Country country) {
        selectedCountry.setValue(country);
    }

    public LiveData<Country> getSelectedCountry() {
        return selectedCountry;
    }
}
