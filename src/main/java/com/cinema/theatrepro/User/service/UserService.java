package com.cinema.theatrepro.User.service;

import com.cinema.theatrepro.User.model.UserResources;
import com.cinema.theatrepro.User.model.UsersDto;
import com.cinema.theatrepro.shared.generic.SuccessResponse;

import java.util.List;

public interface UserService {
    SuccessResponse addNewUser(UsersDto usersDto);

    List<UserResources> fetchAllUsers();
}
