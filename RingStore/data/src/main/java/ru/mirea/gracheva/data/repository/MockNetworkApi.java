package ru.mirea.gracheva.data.repository;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.gracheva.domain.models.Ring;
import ru.mirea.gracheva.domain.repository.NetworkApi;

public class MockNetworkApi implements NetworkApi {
    @Override
    public List<Ring> fetchRings() {
        List<Ring> rings = new ArrayList<>();
        rings.add(new Ring("1", "Золото", 50000));
        rings.add(new Ring("2", "Серебро", 3000));
        rings.add(new Ring("3", "Платина", 70000));
        return rings;
    }
}

