package recipes.recipes;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<RecipeModel, Integer> {

    //Find by category case-insensitive
    List<RecipeModel> findByCategoryIgnoreCase(String category);

    //Find if name contains a string, case insensitive
    List<RecipeModel> findByNameContainingIgnoreCase(String name);
}