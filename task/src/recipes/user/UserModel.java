package recipes.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;


@Data
@NoArgsConstructor
@Entity
@Table(name = "userModel")
public class UserModel {

    //Ignore ID when returning json
    @JsonIgnore
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    @NotBlank(message = "Email is mandatory")
    //@Email(message = "Email should be valid")
    @EmailConstraint
    private String email;

    @Column
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password should contain at least 8 characters and shouldn't be blank")
    private String password;
}
