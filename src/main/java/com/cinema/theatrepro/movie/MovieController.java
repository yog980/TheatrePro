package com.cinema.theatrepro.movie;

import com.cinema.theatrepro.movie.dto.*;
import com.cinema.theatrepro.movie.model.MovieSeat;
import com.cinema.theatrepro.movie.model.MovieShow;
import com.cinema.theatrepro.movie.service.MovieService;
import com.cinema.theatrepro.shared.enums.Status;
import com.cinema.theatrepro.shared.generic.SuccessResponse;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public SuccessResponse addNewMovie(
            @RequestParam("image") MultipartFile file,
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "status", required = false) Status status,
            @RequestParam(value = "releaseDate", required = false) String releaseDate,
            @RequestParam(value = "duration", required = false) String duration) {
        return movieService.saveMovie(file,title,description,status,releaseDate,duration);
    }

    @PostMapping("/{movieId}/update")
    public SuccessResponse updateMovie(
            @RequestParam(value = "image",required = false) MultipartFile file,
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "status", required = false) Status status,
            @RequestParam(value = "releaseDate", required = false) String releaseDate,
            @RequestParam(value = "duration", required = false) String duration,
            @PathVariable(value = "movieId") Long movieId) throws IOException {
        return movieService.updateMovie(file,title,description,status,releaseDate,duration,movieId);
    }

    @GetMapping("/all")
    public List<MovieResponse> fetchAllMovies() {
        return movieService.fetchAllMovies();
    }

    @GetMapping("/theatres")
    public List<TheatreResponse> fetchAllTheatres() {
        return movieService.fetchAllThreatres();
    }

    @PostMapping("/add-show")
    public SuccessResponse addMovieShow(@RequestBody MovieShowDto showDto) {
        return movieService.addNewMovieShow(showDto);
    }

    @GetMapping("/fetch-all-shows")
    public List<MovieShowResponse> fetchAllShows() {
        return movieService.fetchAllShows();
    }

    @GetMapping("/movie-show/{movieShowId}/seats")
    public List<MovieSeatResponse> fetchAllMovieSeatByMovieShow(@PathVariable("movieShowId") Long movieShowId) {
        return movieService.fetchMovieSeatByMovieShow(movieShowId);
    }

    @GetMapping("/{movieId}/movie-show")
    public List<MovieShowResponse> getMovieShowById(@PathVariable("movieId") Long movieId) {
        return movieService.getMovieShowsByMovieId(movieId);
    }
}
