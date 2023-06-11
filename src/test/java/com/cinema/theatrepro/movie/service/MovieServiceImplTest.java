package com.cinema.theatrepro.movie.service;

import com.cinema.theatrepro.movie.dto.MovieResponse;
import com.cinema.theatrepro.movie.model.Movie;
import com.cinema.theatrepro.movie.repo.*;
import com.cinema.theatrepro.shared.utils.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Base64;
import java.util.Optional;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;
    @Mock
    private TheatreRepository theatreRepository;
    @Mock
    private MovieShowRepository movieShowRepository;
    @Mock
    private MovieSeatRepository movieSeatRepository;
    @Mock
    private BookingDetailsRepository bookingDetailsRepository;
    @Mock
    private BookingDetailSeatsRepository bookingDetailSeatsRepository;

    private MovieService movieService;

    @BeforeEach
    void setUp() {
        this.movieService = new MovieServiceImpl(
                this.movieRepository,
                this.theatreRepository,
                this.movieShowRepository,
                this.movieSeatRepository,
                this.bookingDetailsRepository,
                this.bookingDetailSeatsRepository
        );
    }

   @Test
    void fetchAllMovies() {
        movieService.fetchAllMovies();
       Mockito.verify(movieRepository).findAll();
    }

    @Test
    void fetchAllShows() {
        movieService.fetchAllShows();
        Mockito.verify(movieShowRepository).findAll();
    }

    @Test
    public void getMovieById() {
        Long movieId = 1L;
        Movie movie = new Movie();
        movie.setId(movieId);
        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        MovieResponse result = movieService.getMovieById(movieId);
        assertEquals(convertAllMovies(movie), result);
        Mockito.verify(movieRepository).findById(movieId);
    }

    private MovieResponse convertAllMovies(Movie movie) {
        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .image(movie.getImage()!=null?Base64.getEncoder().encodeToString(movie.getImage()):null)
                .status(movie.getStatus())
                .releaseDate(movie.getReleaseDate() != null? DateUtils.formatReadableDate(movie.getReleaseDate()):null)
                .duration(movie.getDuration())
                .isTrending(movie.isTrending())
                .bannerImage(movie.getBannerImage() != null?Base64.getEncoder().encodeToString(movie.getBannerImage()):null)
                .build();
    }

}