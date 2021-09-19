package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
        System.out.println("Service Layer is created");
    }

    //Return recipe with id
    public Optional<RecipeModel>  getRecipe(int id) {
        Optional<RecipeModel> recipeOptional = recipeRepository.findById(id);
        return recipeOptional;
    }

    //Save recipe with and auto generate primary key
    public int saveRecipe(RecipeModel recipeModel) {
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
}
