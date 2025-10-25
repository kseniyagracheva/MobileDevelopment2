package ru.mirea.gracheva.domain.repository;

import java.util.List;

import ru.mirea.gracheva.domain.models.Ring;

public interface NetworkApi {
    List<Ring> fetchRings();
}
