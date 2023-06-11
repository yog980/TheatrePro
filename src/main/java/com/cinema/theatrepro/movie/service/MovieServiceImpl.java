package com.cinema.theatrepro.movie.service;

import com.cinema.theatrepro.movie.dto.*;
import com.cinema.theatrepro.movie.model.*;
import com.cinema.theatrepro.movie.repo.*;
import com.cinema.theatrepro.shared.enums.Status;
import com.cinema.theatrepro.shared.exception.ClientException;
import com.cinema.theatrepro.shared.generic.GenericResponse;
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
    private final BookingDetailsRepository bookingDetailsRepository;
    private final BookingDetailSeatsRepository bookingDetailSeatsRepository;

    public MovieServiceImpl(MovieRepository movieRepository, TheatreRepository theatreRepository, MovieShowRepository movieShowRepository, MovieSeatRepository movieSeatRepository, BookingDetailsRepository bookingDetailsRepository, BookingDetailSeatsRepository bookingDetailSeatsRepository) {
        this.movieRepository = movieRepository;
        this.theatreRepository = theatreRepository;
        this.movieShowRepository = movieShowRepository;
        this.movieSeatRepository = movieSeatRepository;
        this.bookingDetailsRepository = bookingDetailsRepository;
        this.bookingDetailSeatsRepository = bookingDetailSeatsRepository;
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
                                     Status status,String releaseDate,String duration,boolean isTrending,
                                     MultipartFile bannerImage) {
        log.info("Saving data into movie ...");
        Movie movieDto = new Movie();
        movieDto.setTitle(title);
        movieDto.setDescription(description);
        movieDto.setStatus(status);
        movieDto.setReleaseDate(DateUtils.parseDateOnly(releaseDate));
        movieDto.setDuration(duration);
        movieDto.setTrending(isTrending);
        try {
            movieDto.setImage(file.getBytes());
            if (movieDto.getBannerImage() != null) {
                movieDto.setBannerImage(bannerImage.getBytes());
            }
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
                                       Long movieId,
                                       boolean isTrending,
                                       MultipartFile bannerImage) {
        log.info("Fetching movie details by movie id :: {}",movieId);
        Movie movie = movieRepository.findById(movieId).orElseThrow(() ->
                new ClientException("Movie not found with movie is "+movieId));
        try {
            if (file != null) {
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
            if (isTrending) {
                movie.setTrending(true);
            }
            if (bannerImage != null) {
                movie.setBannerImage(bannerImage.getBytes());
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

    @Override
    public List<MovieResponse> getAllTrendingMovies() {
        log.info("Fetching all trending movies");
        List<MovieResponse> movieList = movieRepository.getAllByIsTrending(Boolean.TRUE).stream()
                .map(e -> convertAllMovies(e))
                .collect(Collectors.toList());
        return movieList;
    }

    @Override
    public MovieResponse getMovieById(Long id) {
        log.info("Fetching movie by ID :: {}",id);
        Movie movie = this.movieRepository.findById(id).orElseThrow(() ->
                new ClientException("Movie not found with movie ID "+id));
        return convertAllMovies(movie);
    }

    @Override
    public MovieShowResponse getMovieShowById(Long id) {
        log.info("Fetching movie show details by show id ::{}",id);
        MovieShow movieShow = this.movieShowRepository.findById(id).orElseThrow(() ->
            new ClientException("Movie Show not found by show id = "+id)
        );
        return convertToMovieShowResponse(movieShow);
    }

    @Override
    public SuccessResponse bookSeat(Boolean isBooked,Long seatId) {
        log.info("Fetching Seat status by seat Id :: {}",seatId);
        MovieSeat movieSeat = movieSeatRepository.findById(seatId).orElseThrow(() ->
                new ClientException("Movie Seat not found with Id :: "+seatId));
        movieSeat.setBooked(isBooked);
        movieSeatRepository.save(movieSeat);
        return SuccessResponse.builder()
                .message("Seat Booked Successfully !!!")
                .build();
    }

    @Override
    @Transactional
    public GenericResponse bookMultipleSeats(BookingDto bookingDto) {
        log.info("Booking Seats with seat IDs :: {}",bookingDto.getSeatIds());
        List<MovieSeat> movieSeatList = movieSeatRepository.findAllByIdsIn(bookingDto.getSeatIds());
        if (movieSeatList == null) {
            return GenericResponse.builder()
                    .code(HttpStatus.FORBIDDEN.value())
                    .message("No Seats found with provided Seat Ids.")
                    .build();
        }
        MovieShow movieShow = movieShowRepository.findById(bookingDto.getShowId()).orElseThrow(() ->
                new ClientException("Movie Show not found with show id :: "+bookingDto.getShowId()));
        movieSeatList.stream().forEach(e -> e.setBooked(Boolean.TRUE));
        movieSeatRepository.saveAll(movieSeatList);
        BookingDetails bookingDetails = new BookingDetails();
        bookingDetails.setBookingDate(new Date());
        bookingDetails.setStatus(Status.ACTIVE);
        bookingDetails.setBookingStatus(BookingStatus.REQUESTED);
        bookingDetails.setUser(null);
        bookingDetails.setMovieShow(movieShow);
        bookingDetailsRepository.save(bookingDetails);

        // saving seat details for booking
        movieSeatList.stream().forEach(e -> {
            BookingDetailSeats bookingDetailSeats = new BookingDetailSeats();
            bookingDetailSeats.setBookingDetails(bookingDetails);
            bookingDetailSeats.setBookingStatus(BookingStatus.REQUESTED);
            bookingDetailSeats.setStatus(Status.ACTIVE);
            bookingDetailSeats.setMovieSeat(e);
            bookingDetailSeatsRepository.save(bookingDetailSeats);
        });

        return GenericResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Seats Booked Successfully !!!")
                .build();
    }

    @Override
    public List<BookingResource> fetchAllBookingDetails() {
        log.info("Fetching booking details ...");
        return this.bookingDetailsRepository.findAll().stream()
                .map(e -> convertToBookingResources(e)).collect(Collectors.toList());
    }

    private BookingResource convertToBookingResources(BookingDetails bookingDetails) {
        log.info("Booking Detials :: {}",bookingDetails.getSeatDetails());
        return BookingResource.builder()
                .booingId(bookingDetails.getId())
                .bookingDate(bookingDetails.getBookingDate())
                .movieTitle(bookingDetails.getMovieShow().getMovie().getTitle())
                .theatre(bookingDetails.getMovieShow().getTheatre().getTitle())
                .bookingStatus(bookingDetails.getBookingStatus())
                .discountPercentage(bookingDetails.getMovieShow().getDiscountPercentage())
                .startDate(bookingDetails.getMovieShow().getStartDate())
                .shift(bookingDetails.getMovieShow().getShift())
                .seatsBooked(bookingDetails.getSeatDetails().stream().count())
                .build();
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
                .pricePerTicket(movieShow.getPricePerTicket())
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
                .isTrending(movie.isTrending())
                .bannerImage(movie.getBannerImage() != null?Base64.getEncoder().encodeToString(movie.getBannerImage()):null)
                .build();
    }
}
