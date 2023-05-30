package com.cinema.theatrepro.movie.service;

import com.cinema.theatrepro.movie.dto.*;
import com.cinema.theatrepro.movie.model.Movie;
import com.cinema.theatrepro.movie.model.MovieSeat;
import com.cinema.theatrepro.movie.model.MovieShow;
import com.cinema.theatrepro.movie.model.Theatre;
import com.cinema.theatrepro.movie.repo.MovieRepository;
import com.cinema.theatrepro.movie.repo.MovieSeatRepository;
import com.cinema.theatrepro.movie.repo.MovieShowRepository;
import com.cinema.theatrepro.movie.repo.TheatreRepository;
import com.cinema.theatrepro.shared.enums.Status;
import com.cinema.theatrepro.shared.exception.ClientException;
import com.cinema.theatrepro.shared.generic.SuccessResponse;
import com.cinema.theatrepro.shared.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService{
    private final MovieRepository movieRepository;
    private final TheatreRepository theatreRepository;
    private final MovieShowRepository movieShowRepository;
    private final MovieSeatRepository movieSeatRepository;

    public MovieServiceImpl(MovieRepository movieRepository, TheatreRepository theatreRepository, MovieShowRepository movieShowRepository, MovieSeatRepository movieSeatRepository) {
        this.movieRepository = movieRepository;
        this.theatreRepository = theatreRepository;
        this.movieShowRepository = movieShowRepository;
        this.movieSeatRepository = movieSeatRepository;
    }

    @Override
    public List<MovieResponse> fetchAllMovies() {
        log.info("Fetching all movies ...");
        List<MovieResponse> movies = movieRepository.findAll().stream()
                .map(e -> convertAllMovies(e))
                .collect(Collectors.toList());
        return movies;
    }

    @Override
    public SuccessResponse saveMovie(MultipartFile file, String title, String description,
                                     Status status,String releaseDate,String duration) {
        log.info("Saving data into movie ...");
        Movie movieDto = new Movie();
        movieDto.setTitle(title);
        movieDto.setDescription(description);
        movieDto.setStatus(status);
        movieDto.setReleaseDate(DateUtils.parseDateOnly(releaseDate));
        movieDto.setDuration(duration);
        try {
            movieDto.setImage(file.getBytes());
            log.info("Movie Dto is :: {}",movieDto);
            movieRepository.save(movieDto);
            log.info("Movie Saved ..");
            return SuccessResponse.builder()
                    .code(HttpStatus.OK.value())
                    .message("Movie added successfully")
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public SuccessResponse updateMovie(
                                       MultipartFile file,
                                       String title,
                                       String description,
                                       Status status,
                                       String releaseDate,
                                       String duration,
                                       Long movieId) {
        log.info("Fetching movie details by movie id :: {}",movieId);
        Movie movie = movieRepository.findById(movieId).orElseThrow(() ->
                new ClientException("Movie not found with movie is "+movieId));
        try {
            if (file != null || !file.isEmpty()) {
                movie.setImage(file.getBytes());
            }
            if (title != null) {
                movie.setTitle(title);
            }
            if (description != null) {
                movie.setDescription(description);
            }
            if (status != null) {
                movie.setStatus(status);
            }
            if (releaseDate != null) {
                movie.setReleaseDate(DateUtils.parseDateOnly(releaseDate));
            }
            if (duration != null) {
                movie.setDuration(duration);
            }
            movieRepository.save(movie);
            return SuccessResponse.builder()
                    .code(HttpStatus.OK.value())
                    .message("Movie updated successfully !")
                    .build();
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<TheatreResponse> fetchAllThreatres() {
        return theatreRepository.findAll().stream().map(e -> convertToTheatreResponse(e))
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public SuccessResponse addNewMovieShow(MovieShowDto showDto) {
        log.info("Adding show information with dto :: {}",showDto);
        try {
            MovieShow movieShow = new MovieShow();
            movieShow.setShift(showDto.getShift());
            movieShow.setStartDate(DateUtils.parseDate(showDto.getStartDate()));
            movieShow.setDiscountPercentage(showDto.getDiscountPercentage());
            Movie movie = movieRepository.findById(showDto.getMovieId()).orElseThrow(() ->
                    new ClientException("Movie not found with id "+showDto.getMovieId()));
            movieShow.setMovie(movie);
            Optional<Theatre> theatre = theatreRepository.findById(showDto.getTheatreId());
            movieShow.setTheatre(theatre.get());
            movieShow.setStatus(Status.ACTIVE);
            movieShowRepository.save(movieShow);

            // saving seats
            for(int i=0; i<movieShow.getTheatre().getSeats();i++) {
                MovieSeat movieSeat = new MovieSeat();
                movieSeat.setStatus(Status.ACTIVE);
                movieSeat.setMovieShow(movieShow);
                movieSeatRepository.save(movieSeat);
            }

            return SuccessResponse.builder()
                    .code(HttpStatus.OK.value())
                    .message("Movie Show added Successfully")
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<MovieShowResponse> fetchAllShows() {
        List<MovieShowResponse> movieShowResponseList = movieShowRepository.findAll().stream()
                .map(e -> convertToMovieShowResponse(e)).collect(Collectors.toList());
        return movieShowResponseList;
    }

    @Override
    public List<MovieSeatResponse> fetchMovieSeatByMovieShow(Long movieShowId) {
        log.info("Fetching Movie show by id :: {}",movieShowId);
        MovieShow movieShow = movieShowRepository.findById(movieShowId).orElseThrow(() ->
                new ClientException("Movie Show not found"));
        return movieSeatRepository.findMovieSeatByMovieShow(movieShow).stream()
                .map(e -> convertToMovieSeatResponse(e))
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieShowResponse> getMovieShowsByMovieId(Long movieId) {
        log.info("Fetching movie by movie is :: {}",movieId);
        Movie movie = movieRepository.findById(movieId).orElseThrow(() ->
                new ClientException("Movie not found with movie id :: "+movieId));
        log.info("Fetching movie shows by movie id :: {}",movieId);
        List<MovieShowResponse> movieShowList = movieShowRepository.findAllByMovie(movie).stream()
                .map(e -> convertToMovieShowResponse(e)).collect(Collectors.toList());
        return movieShowList;
    }

    private MovieSeatResponse convertToMovieSeatResponse(MovieSeat movieSeat) {
        return MovieSeatResponse.builder()
                .seatId(movieSeat.getId())
                .isBooked(movieSeat.isBooked())
                .isDisabled(movieSeat.isDisabled())
                .movieShowId(movieSeat.getMovieShow().getId())
                .status(Status.ACTIVE)
                .build();
    }

    private MovieShowResponse convertToMovieShowResponse(MovieShow movieShow) {
        return MovieShowResponse.builder()
                .id(movieShow.getId())
                .shift(movieShow.getShift())
                .startTime(DateUtils.extractTime(movieShow.getStartDate()))
                .startDate(DateUtils.formatReadableDate(movieShow.getStartDate()))
                .discountPercentage(movieShow.getDiscountPercentage())
                .movie(MovieMiniResource.builder()
                        .movieId(movieShow.getMovie().getId())
                        .movieTitle(movieShow.getMovie().getTitle())
                        .movieStatus(movieShow.getStatus())
                        .build())
                .theatre(TheatreResponse.builder()
                        .title(movieShow.getTheatre().getTitle())
                        .seats(movieShow.getTheatre().getSeats())
                        .status(movieShow.getTheatre().getStatus())
                        .build())
                .build();
    }

    private MovieMiniResource convertToMovieMiniResource(Movie movie) {
        return MovieMiniResource.builder()
                .movieId(movie.getId())
                .movieTitle(movie.getTitle())
                .movieStatus(movie.getStatus())
                .build();
    }

    private TheatreResponse convertToTheatreResponse(Theatre theatre) {
        return TheatreResponse.builder()
                .title(theatre.getTitle())
                .seats(theatre.getSeats())
                .status(theatre.getStatus())
                .build();
    }

    private MovieResponse convertAllMovies(Movie movie) {
        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .image(Base64.getEncoder().encodeToString(movie.getImage()))
                .status(movie.getStatus())
                .releaseDate(movie.getReleaseDate() != null?DateUtils.formatReadableDate(movie.getReleaseDate()):null)
                .duration(movie.getDuration())
                .build();
    }
}
