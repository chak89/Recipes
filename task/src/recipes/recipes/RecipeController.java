package recipes.recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Validated
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    //GET /api/recipe -  returns a recipe with a specified id as a JSON object
    @GetMapping("/api/recipe/{id}")
    public ResponseEntity<RecipeModel> getRecipe(@PathVariable int id) {
        System.out.printf("RecipeController() -> %s -> id:%d %n", "/api/recipe/{id}", id);
        Optional<RecipeModel> recipeModel = recipeService.getRecipe(id);

        if (recipeModel.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            System.out.println("recipeModel:" + recipeModel.get());
            return new ResponseEntity<>(recipeModel.get(), HttpStatus.OK);
        }
    }

    //POST /api/recipe - receives a recipe as a JSON object and returns a JSON object with one id field
    @PostMapping("/api/recipe/new")
    public ResponseEntity<IdResponse> postRecipe(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody RecipeModel recipe) {

        System.out.printf("RecipeController() -> %s -> recipe:%s %n", "/api/recipe/new", recipe);
        System.out.println("Currently logged in user: " + userDetails.getUsername());

        //Set the author as currently logged-in user email
        recipe.setAuthor(userDetails.getUsername());

        //CRUD operation, save the recipe to database
        int id = recipeService.saveRecipe(recipe);
        return new ResponseEntity<>(new IdResponse(id), HttpStatus.OK);
    }

    //DELETE a recipe with a specified id
    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity<RecipeModel> deleteRecipe(@AuthenticationPrincipal UserDetails userDetails, @PathVariable int id) {

        System.out.printf("RecipeController() -> %s -> recipe:%d %n", "@DeleteMapping /api/recipe/{id})", id);

        //Get the email of currently logged-in user
        String userEmail = userDetails.getUsername();

        //Get recipe from database
        Optional<RecipeModel> recipeModel = recipeService.getRecipe(id);

        //Check if recipe is found
        if (recipeModel.isEmpty()) {
            System.out.println("No recipe found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //Get author of the recipe
        String recipeAuthor = recipeModel.get().getAuthor();

        //Check if author of recipe is the same as currently logged-in user
        if (!recipeAuthor.equals(userEmail)) {
            System.out.println("Currently logged-in user and author of recipe do not match");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        recipeService.deleteRecipe(id);
        System.out.printf("Username: %s DELETED recipe: %s %n", userEmail, recipeModel.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    //PUT - receives a recipe as a JSON object and updates a recipe with a specified id Also, update the date field too.
    //The server should return the 204 (No Content) status code.
    //If a recipe with a specified id does not exist, the server should return 404 (Not found).
    //The server should respond with 400 (Bad Request) if a recipe doesn't follow the restrictions indicated above (all fields are required, string fields can't be blank, arrays should have at least one item);
    @PutMapping("/api/recipe/{id}")
    public ResponseEntity<RecipeModel> putRecipe(@AuthenticationPrincipal UserDetails userDetails, @PathVariable int id, @Validated @RequestBody RecipeModel recipe) {

        System.out.printf("RecipeController() -> %s -> recipe:%d %n", "@PutMapping /api/recipe/{id})", id);

        //Get the email of currently logged-in user
        String userEmail = userDetails.getUsername();

        //Get recipe from database
        Optional<RecipeModel> recipeModel = recipeService.getRecipe(id);

        //Check if recipe is found
        if (recipeModel.isEmpty()) {
            System.out.println("No recipe found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //Get author of the recipe
        String recipeAuthor = recipeModel.get().getAuthor();

        //Check if author of recipe is the same as currently logged-in user
        if (!recipeAuthor.equals(userEmail)) {
            System.out.println("Currently logged-in user and author of recipe do not match");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        recipeService.updateRecipe(id, recipeAuthor, recipe);
        System.out.printf("Username: %s UPDATED recipe: %s %n", userEmail, recipe);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    //GET - takes one of the two mutually exclusive query parameters:
    //category – if this parameter is specified, it returns a JSON array of all recipes of the specified category. Search is case-insensitive, sort the recipes by date (newer first);
    //name – if this parameter is specified, it returns a JSON array of all recipes with the names that contain the specified parameter. Search is case-insensitive, sort the recipes by date (newer first).
    //If no recipes are found, the program should return an empty JSON array. If 0 parameters were passed, or more than 1, the server should return 400 (Bad Request).
    //The same response should follow if the specified parameters are not valid. If everything is correct, it should return 200 (Ok).
    @GetMapping("/api/recipe/search/")
    public ResponseEntity<List<RecipeModel>> searchRecipe(@RequestParam Map<String, String> allParams) {
        System.out.printf("RecipeController() -> %s -> allParams:%s%n", "/api/recipe/search/", allParams);
        System.out.printf("allParams.size()%s%n", allParams.size());

        //Check that the query params aren't empty
        if (allParams.size() == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //Check that there is only one param
        if (allParams.size() > 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //Extract GET parameters as firstkey and firstValue
        Optional<String> firstKey = allParams.keySet().stream().findFirst();
        String firstValue = allParams.get(firstKey.get());
        List<RecipeModel> result = recipeService.searchRecipe(firstKey.get(), firstValue);


        //If query params do not exist.
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}


class IdResponse {
    private final int id;

    public IdResponse(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
