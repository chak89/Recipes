package recipes.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Validated
public class UserController {

    @Autowired
    UserService userService;

//    @Autowired
//    private PasswordEncoder passwordEncoder;


    @PostMapping("/api/register")
    public ResponseEntity<Long> registerUser(@Validated @RequestBody UserModel user) {
        System.out.printf("UserController() -> %s -> user:%s%n", "/api/register", user);

        //Check if a user with a specified email exist
        Optional<UserModel> returnedUser = userService.getUser(user.getEmail());
        if (returnedUser.isPresent()) {
            System.out.println(user.getEmail() + " already exists in the database.");
            return new ResponseEntity<>(-1L,HttpStatus.BAD_REQUEST);
        }

        //CRUD operation, save the user to database
        long id = userService.saveUser(user);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}

