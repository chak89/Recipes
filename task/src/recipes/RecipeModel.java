package recipes;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecipeModel {
    private String name;
    private String description;
    private String[] ingredients;
    private String[] directions;
}
