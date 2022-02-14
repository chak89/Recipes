package recipes.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// Extending the adapter and adding the annotation
@EnableWebSecurity
public class WebSecurityConfigurerImpl extends WebSecurityConfigurerAdapter {

    // Acquiring the builder
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // storing users in memory
        auth.inMemoryAuthentication()
                .withUser("user1").password(getEncoder().encode("pass1")).roles()
                .and()
                .withUser("user2").password(getEncoder().encode("pass2")).roles("USER")
                .and()
                .withUser("user3").password(getEncoder().encode("pass3")).roles("ADMIN")
                .and()
                .passwordEncoder(getEncoder()); // specifying what encoder we used
    }


    @Override
    //This will override the config file
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/admin").hasRole("ADMIN")
                .mvcMatchers("/user").hasAnyRole("ADMIN", "USER")
                .mvcMatchers("/", "/public", "/h2/**").permitAll()
                .mvcMatchers("/**").authenticated() // or .anyRequest().authenticated()
                .and()
                .csrf().disable() // disabling CSRF will allow sending POST request using Postman
                .headers().frameOptions().sameOrigin()
                .and()
                .httpBasic(); // enables basic auth.
    }


    // creating a PasswordEncoder that is needed in two places
    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}
