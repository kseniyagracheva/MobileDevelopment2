package ru.mirea.gracheva.domain.repository;

import ru.mirea.gracheva.domain.models.MovieDomain;

public interface MovieRepository {
    public boolean saveMovie(MovieDomain movieDomain);
    public MovieDomain getMovie();
}
