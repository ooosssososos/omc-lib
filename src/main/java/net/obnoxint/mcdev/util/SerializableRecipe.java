package net.obnoxint.mcdev.util;

import java.io.Serializable;

import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

@SuppressWarnings("serial")
public abstract class SerializableRecipe implements Serializable {

    public static final int RECIPE_TYPE_FURNACE = 0;

    public static final int RECIPE_TYPE_SHAPED = 1;
    public static final int RECIPE_TYPE_SHAPELESS = 2;

    public static SerializableRecipe getFromRecipe(final Recipe recipe) {
        SerializableRecipe r = null;
        if (recipe != null) {
            if (recipe instanceof FurnaceRecipe) {
                r = new SerializableFurnaceRecipe((FurnaceRecipe) recipe);
            } else if (recipe instanceof ShapedRecipe) {
                // TODO
            } else if (recipe instanceof ShapelessRecipe) {
                // TODO
            }
        }
        return r;
    }

    private final int type;
    private final SerializableItemStack result;

    protected SerializableRecipe(final int type, final Recipe recipe) {
        if (type < 0 || type > 2) {
            throw new IllegalArgumentException();
        }
        this.type = type;
        this.result = new SerializableItemStack(recipe.getResult());
    }

    public final SerializableItemStack getResult() {
        return result;
    }

    public final int getType() {
        return type;
    }

}
