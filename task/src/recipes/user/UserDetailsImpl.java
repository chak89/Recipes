package recipes.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

/*Used to store and transfer user information from the user store(h2 database) to Spring Security.
The class should implement the UserDetails interface because Spring Security can only recognize users of this type.*/
public class UserDetailsImpl implements UserDetails {
    private final String email;
    private final String password;

    //Constructor - converter from UserModel to UserDetails.
    public UserDetailsImpl(UserModel user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
