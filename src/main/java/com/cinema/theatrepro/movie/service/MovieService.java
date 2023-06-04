package com.cinema.theatrepro.movie.service;

import com.cinema.theatrepro.movie.dto.*;
import com.cinema.theatrepro.movie.model.MovieSeat;
import com.cinema.theatrepro.movie.model.MovieShow;
import com.cinema.theatrepro.shared.enums.Status;
import com.cinema.theatrepro.shared.generic.SuccessResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {
    List<MovieResponse> fetchAllMovies();

    SuccessResponse saveMovie(MultipartFile file, String title, String description,
                              Status status,String releaseDate,String duration,boolean isTrending,
                              MultipartFile bannerImage);

    SuccessResponse updateMovie(MultipartFile file, String title, String description,
                              Status status,String releaseDate,String duration,Long movieId,boolean isTrending,
                                MultipartFile bannerImage) throws IOException;

    List<TheatreResponse> fetchAllThreatres();

    SuccessResponse addNewMovieShow(MovieShowDto showDto);

    List<MovieShowResponse> fetchAllShows();

    List<MovieSeatResponse> fetchMovieSeatByMovieShow(Long movieShowId);

    List<MovieShowResponse> getMovieShowsByMovieId(Long movieId);

    List<MovieResponse> getAllTrendingMovies();

    MovieResponse getMovieById(Long id);

    MovieShowResponse getMovieShowById(Long id);

    SuccessResponse bookSeat(Boolean isBooked,Long seatId);
}
