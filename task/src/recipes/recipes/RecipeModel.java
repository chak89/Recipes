package recipes.recipes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@Entity
@Table(name = "recipeModel")
public class RecipeModel {

    //Ignore ID when returning json
    @JsonIgnore
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    @NotBlank
    private String name;

    @Column
    @NotBlank
    private String category;

    @Column
    private LocalDateTime date;

    @Column
    @NotBlank
    private String description;

//    @Column
//    @NotEmpty
//    private List<String> ingredients;
//
//    @Column
//    @NotEmpty
//    private List<String> directions;


    @Column
    @NotEmpty
    private String[] ingredients;

    @Column
    @NotEmpty
    private String[] directions;

}


