package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    //GET /api/recipe
    @GetMapping("/api/recipe/{id}")
    public ResponseEntity<RecipeModel> getRecipe(@PathVariable int id) {

        RecipeModel recipeModel = recipeService.getRecipe(id);

        if (recipeModel == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(recipeModel, HttpStatus.OK);
        }
    }


    //POST /api/recipe
    @PostMapping("/api/recipe/new")
    public ResponseEntity<IdResponse> postRecipe(@RequestBody RecipeModel recipe) {

        int id = recipeService.saveRecipe(recipe);
        return new ResponseEntity<>(new IdResponse(id), HttpStatus.OK);
    }
}


class IdResponse {
    private int id;

    public IdResponse(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
