package pl.kkowalewski.recipeapp.converter.commandto;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import pl.kkowalewski.recipeapp.command.IngredientCommand;
import pl.kkowalewski.recipeapp.converter.Command;
import pl.kkowalewski.recipeapp.model.Ingredient;
import pl.kkowalewski.recipeapp.model.Recipe;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient>,
        Command<IngredientCommand, Ingredient> {

    /*------------------------ FIELDS REGION ------------------------*/
    private final UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;

    /*------------------------ METHODS REGION ------------------------*/
    public IngredientCommandToIngredient(
            UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure) {
        this.unitOfMeasureCommandToUnitOfMeasure = unitOfMeasureCommandToUnitOfMeasure;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand ingredientCommand) {
        if (ingredientCommand == null) {
            return null;
        }

        Recipe recipe = new Recipe(ingredientCommand.getRecipeId());

        Ingredient ingredient = new Ingredient(ingredientCommand.getId(),
                ingredientCommand.getDescription(),
                ingredientCommand.getAmount(),
                unitOfMeasureCommandToUnitOfMeasure.convert(ingredientCommand.getUom()),
                recipe);

        recipe.addIngredient(ingredient);

        return ingredient;
    }
}
