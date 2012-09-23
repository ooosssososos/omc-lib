package net.obnoxint.mcdev.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class SerializableShapedRecipe extends SerializableRecipe {

    private static final long serialVersionUID = 7165405366341246113L;

    private final Map<Character, SerializableItemStack> ingredients;
    private final String[] shape;

    public SerializableShapedRecipe(final ShapedRecipe recipe) {
        super(RECIPE_TYPE_SHAPED, recipe);
        final Map<Character, ItemStack> map = recipe.getIngredientMap();
        final Map<Character, SerializableItemStack> in = new HashMap<>();
        final String[] sh = recipe.getShape();
        final String[] shp = new String[sh.length];
        for (final char c : map.keySet()) {
            in.put(c, new SerializableItemStack(map.get(c)));
        }
        System.arraycopy(sh, 0, shp, 0, sh.length);
        ingredients = in;
        shape = shp;
    }

    public SerializableItemStack getIngredient(final char c) {
        return ingredients.get(c);
    }

    public Map<Character, SerializableItemStack> getIngredients() {
        return Collections.unmodifiableMap(ingredients);
    }

    public String[] getShape() {
        final String[] r = new String[shape.length];
        System.arraycopy(shape, 0, r, 0, shape.length);
        return r;
    }

    public ShapedRecipe toShapedRecipe() {
        final ShapedRecipe r = new ShapedRecipe(getResult().toItemStack());
        for (final char c : ingredients.keySet()) {
            r.setIngredient(c, ingredients.get(c).getMaterialData());
        }
        r.shape(getShape());
        return r;
    }

}
