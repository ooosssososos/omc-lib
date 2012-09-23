package net.obnoxint.mcdev.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

public class SerializableShapelessRecipe extends SerializableRecipe {

    private static final long serialVersionUID = -2543468009929371382L;

    private final List<SerializableItemStack> ingredients;

    public SerializableShapelessRecipe(final ShapelessRecipe recipe) {
        super(RECIPE_TYPE_SHAPELESS, recipe);
        final List<SerializableItemStack> in = new ArrayList<>();
        for (final ItemStack is : recipe.getIngredientList()) {
            in.add(new SerializableItemStack(is));
        }
        ingredients = in;
    }

    public List<SerializableItemStack> getIngredients() {
        return Collections.unmodifiableList(ingredients);
    }

    public ShapelessRecipe toShapelessRecipe() {
        final ShapelessRecipe r = new ShapelessRecipe(getResult().toItemStack());
        for (final SerializableItemStack sis : ingredients) {
            r.addIngredient(sis.getAmount(), sis.getMaterialData());
        }
        return r;
    }

}
