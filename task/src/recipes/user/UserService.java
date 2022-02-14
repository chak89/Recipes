package recipes.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        System.out.println("User Service Layer is created");
    }

    //Return a user with specific email
    public Optional<UserModel> getUser(String email) {
        Optional<UserModel> userOptional = userRepository.findByEmail(email);
        System.out.println("In UserService.java -> getUser");
        System.out.println("userOptional:" + userOptional);
        return userOptional;
    }

    // Save user to database and return primary key
    public long saveUser(UserModel userModel) {
        System.out.println("In UserService.java -> saveUser");
        System.out.println("SAVES THE USER TO DATABASE");
        return userRepository.save(userModel).getId();
    }
}


