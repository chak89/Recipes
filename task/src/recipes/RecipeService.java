package recipes;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeService {

    Map<Integer, RecipeModel> recipeMap = new HashMap<>();

    public RecipeService() {
        System.out.println("Service Layer is created");
    }

    //Return recipe with id
    public RecipeModel getRecipe(int id) {
         return recipeMap.get(id);
    }

    //Save recipe with a unique id
    public int saveRecipe(RecipeModel recipeModel) {
        int id = recipeModel.hashCode();
        recipeMap.put(id, recipeModel);
        return id;
    }
}
