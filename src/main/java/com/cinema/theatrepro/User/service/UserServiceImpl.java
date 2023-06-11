package com.cinema.theatrepro.User.service;

import com.cinema.theatrepro.User.model.User;
import com.cinema.theatrepro.User.model.UserResources;
import com.cinema.theatrepro.User.model.UsersDto;
import com.cinema.theatrepro.User.repo.UserRepository;
import com.cinema.theatrepro.shared.enums.RoleName;
import com.cinema.theatrepro.shared.enums.Status;
import com.cinema.theatrepro.shared.exception.ClientException;
import com.cinema.theatrepro.shared.generic.SuccessResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    @Override
    public SuccessResponse addNewUser(UsersDto usersDto) {
        log.info("Saving User info as :: {}",usersDto);
        userRepository.save(convertToUser(usersDto));
        return SuccessResponse.builder()
                .message("User Created Successfully !!!")
                .build();
    }

    @Override
    public List<UserResources> fetchAllUsers() {
        log.info("Fetching All users");
        return userRepository.findAll().stream().map(e -> convertToUserResources(e))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findUserByUserName(String username) {
        log.info("Fetching user by username : "+username);
        return userRepository.findUserByUsername(username);
    }

    @Override
    public SuccessResponse deleteUser(Long userId) {
        log.info("Fetching user details by userId :: {}",userId);
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ClientException("User not found with user id "+userId));
        userRepository.delete(user);
        return SuccessResponse.builder()
                .code(HttpStatus.OK.value())
                .message("User Deleted Successfully !!!")
                .build();
    }

    private User convertToUser(UsersDto usersDto) {
        User user = new User();
        user.setFullName(usersDto.getFullName());
        user.setEmail(usersDto.getEmail());
        user.setUsername(usersDto.getUsername());
        user.setPassword(passwordEncoder.encode(usersDto.getPassword()));
        user.setContact(usersDto.getContact());
        user.setRoleName(RoleName.valueOf(usersDto.getRole()));
        user.setStatus(Status.ACTIVE);
        return user;
    }

    private UserResources convertToUserResources(User user) {
        return UserResources.builder()
                .userId(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .username(user.getUsername())
                .contact(user.getContact())
                .role(String.valueOf(user.getRoleName()))
                .status(Status.ACTIVE)
                .build();
    }
}
