package ru.mirea.gracheva.movieproject.domain.repository;

import ru.mirea.gracheva.movieproject.domain.models.Movie;

public interface MovieRepository {
    public boolean saveMovie(Movie movie);
    public Movie getMovie();
}