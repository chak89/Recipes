package recipes.recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
        System.out.println("Recipe Service Layer is created");
    }

    //Return recipe with id
    public Optional<RecipeModel> getRecipe(int id) {
        return recipeRepository.findById(id);
    }

    //Save recipe with and auto generate primary key
    public int saveRecipe(RecipeModel recipeModel) {

        //Set the date to the date and time right now
        recipeModel.setDate(LocalDateTime.now());

        return recipeRepository.save(recipeModel).getId();
    }

    //Delete a recipe by ID
    public boolean deleteRecipe(int id) {
        Optional<RecipeModel> taskOptional = recipeRepository.findById(id);

        if (taskOptional.isPresent()) {
            recipeRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    //Update an id with a modified recipe
    public void updateRecipe(int id, RecipeModel recipeModel) {
        recipeModel.setId(id);
        recipeModel.setDate(LocalDateTime.now());
        recipeRepository.save(recipeModel);
    }

    //Search for recipe
    public List<RecipeModel> searchRecipe(String key, String value) {

        List<RecipeModel> result;

        if (key.equals("category")) {
            result = recipeRepository.findByCategoryIgnoreCase(value);
        } else if (key.equals("name")) {
            result = recipeRepository.findByNameContainingIgnoreCase(value);
        } else {
            return null;
        }

        //Date sorter, the newest first
        Comparator<RecipeModel> compareByDate = (RecipeModel r1, RecipeModel r2) ->
                r2.getDate().compareTo(r1.getDate());

        result.sort(compareByDate);
        return result;
    }
}
