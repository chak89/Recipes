package recipes;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RecipeController {

    RecipeModel recipeModel = new RecipeModel();


    //GET /api/recipe
    @GetMapping("/api/recipe")
    public RecipeModel getRecipe() {
        return recipeModel;
    }


    //POST /api/recipe
    @PostMapping("/api/recipe")
    public void postRecipe(@RequestBody RecipeModel recipe) {
        this.recipeModel = recipe;
    }
}
