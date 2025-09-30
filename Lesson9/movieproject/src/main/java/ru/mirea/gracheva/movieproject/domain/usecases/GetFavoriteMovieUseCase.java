package ru.mirea.gracheva.movieproject.domain.usecases;

import ru.mirea.gracheva.movieproject.domain.models.Movie;
import ru.mirea.gracheva.movieproject.domain.repository.MovieRepository;

public class GetFavoriteMovieUseCase {
    private MovieRepository movieRepository;
    public GetFavoriteMovieUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    public Movie execute(){
        return movieRepository.getMovie();
    }
}
