package recipes.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    //This method will be used by Spring Security when someone tries to authenticate
    //Retrieve user data by username from the storage and convert that data to UserDetails
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserModel> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Not found: " + email);
        }


        return new UserDetailsImpl(user.get());
    }
}
