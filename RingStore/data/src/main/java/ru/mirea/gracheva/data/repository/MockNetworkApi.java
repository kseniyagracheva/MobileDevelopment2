//package ru.mirea.gracheva.data.repository;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import ru.mirea.gracheva.domain.models.Ring;
//import ru.mirea.gracheva.domain.repository.NetworkApi;
//
//public class MockNetworkApi implements NetworkApi {
//    @Override
//    public List<Ring> fetchRings() {
//        List<Ring> rings = new ArrayList<>();
//        rings.add(new Ring("1", "Золото", 50000));
//        rings.add(new Ring("2", "Серебро", 3000));
//        rings.add(new Ring("3", "Платина", 70000));
//        rings.add(new Ring("4", "Золото", 60000));
//        rings.add(new Ring("5", "Серебро", 5000));
//        rings.add(new Ring("6", "Платина", 75000));
//        rings.add(new Ring("7", "Золото", 55000));
//        rings.add(new Ring("8", "Серебро", 10000));
//        rings.add(new Ring("9", "Платина", 100000));
//        return rings;
//    }
//}
//
