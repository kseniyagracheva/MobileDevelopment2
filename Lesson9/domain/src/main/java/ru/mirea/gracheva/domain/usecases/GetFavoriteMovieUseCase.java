package ru.mirea.gracheva.domain.usecases;

import ru.mirea.gracheva.domain.models.MovieDomain;
import ru.mirea.gracheva.domain.repository.MovieRepository;

public class GetFavoriteMovieUseCase {
    private MovieRepository movieRepository;
    public GetFavoriteMovieUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    public MovieDomain execute(){
        return movieRepository.getMovie();
    }
}
