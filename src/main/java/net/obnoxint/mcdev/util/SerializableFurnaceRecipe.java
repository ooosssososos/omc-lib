package net.obnoxint.mcdev.util;

import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;

public class SerializableFurnaceRecipe extends SerializableRecipe {

    private static final long serialVersionUID = 1745775534255055205L;

    private final int inputId;

    public SerializableFurnaceRecipe(final FurnaceRecipe recipe) {
        super(RECIPE_TYPE_FURNACE, recipe);
        this.inputId = recipe.getInput().getTypeId();
    }

    public final Material getInput() {
        return Material.getMaterial(inputId);
    }

    public FurnaceRecipe toFurnaceRecipe() {
        return new FurnaceRecipe(getResult().toItemStack(), getInput());
    }

}
