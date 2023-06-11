package com.cinema.theatrepro.User;

import com.cinema.theatrepro.User.model.UserResources;
import com.cinema.theatrepro.User.model.UsersDto;
import com.cinema.theatrepro.User.service.UserService;
import com.cinema.theatrepro.shared.generic.SuccessResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create-user")
    public SuccessResponse addNewUser(@RequestBody UsersDto usersDto) {
        return userService.addNewUser(usersDto);
    }

    @GetMapping("")
    public List<UserResources> fetchAllUsers() {
        return userService.fetchAllUsers();
    }

    @GetMapping("{id}/delete-user")
    public SuccessResponse deleteUser(@PathVariable("id") Long userId) {
        return userService.deleteUser(userId);
    }
}
