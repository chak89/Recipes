package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.Optional;


@RestController
@Validated
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    //GET /api/recipe
    @GetMapping("/api/recipe/{id}")
    public ResponseEntity<RecipeModel> getRecipe(@PathVariable int id) {

        Optional<RecipeModel> recipeModel = recipeService.getRecipe(id);

        if (recipeModel.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(recipeModel.get(), HttpStatus.OK);
        }
    }


    //POST /api/recipe
    @PostMapping("/api/recipe/new")
    public ResponseEntity<IdResponse> postRecipe(@Valid @RequestBody RecipeModel recipe) {

        int id = recipeService.saveRecipe(recipe);
        return new ResponseEntity<>(new IdResponse(id), HttpStatus.OK);
    }

    //DELETE
    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity<RecipeModel> deleteRecipe(@PathVariable int id) {
        boolean removed = recipeService.deleteRecipe(id);

        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}


class IdResponse {
    private int id;

    public IdResponse(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
