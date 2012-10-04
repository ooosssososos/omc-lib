package net.obnoxint.mcdev.util;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

/**
 * <p>
 * A serializable and immutable wrapper for org.bukkit.inventory.ItemStack objects.
 * </p>
 */
public class SerializableItemStack implements Serializable {

    private static final long serialVersionUID = 6251309292346486453L;

    /**
     * Creates a new instance based on the given string. The general contract of this method is that if the output of the {@link #toString()} is passed it will result in a clone.
     * 
     * @param string the string.
     * @return a new instance or null if string could not be parsed.
     */
    public static SerializableItemStack fromString(final String string) {
        SerializableItemStack r = null;
        final String[] s = string.split(",");

        if (s.length == 4 || s.length == 5) {
            try {
                final int type = Integer.parseInt(s[0]);
                final int amount = Integer.parseInt(s[1]);
                final short durability = Short.parseShort(s[2]);
                final byte data = Byte.parseByte(s[3]);
                final Map<Integer, Integer> enchantments = new HashMap<>();
                if (s.length == 5) {
                    final String[] es = s[4].substring(1, s[4].length() - 1).split(";");
                    for (int i = 0; i < es.length; i++) {
                        final String[] e = es[i].split("=");
                        if (e.length == 2) {
                            enchantments.put(Integer.parseInt(e[0]), Integer.parseInt(e[1]));
                        }
                    }
                }
                r = new SerializableItemStack(type, amount, durability, data, enchantments);
            } catch (final NumberFormatException e) {}
        }

        return r;
    }

    private static Map<Integer, Integer> getEnchantmentsMap(final Map<Enchantment, Integer> map) {
        final Map<Integer, Integer> r = new HashMap<>();
        for (final Enchantment e : map.keySet()) {
            r.put(e.getId(), map.get(e));
        }
        return r;
    }

    private final int type;
    private final int amount;
    private final short durability;
    private final byte data;

    private final Map<Integer, Integer> enchantments;

    /**
     * Creates a new instance based on the given ItemStack.
     * 
     * @param itemStack the ItemStack.
     */
    public SerializableItemStack(final ItemStack itemStack) {
        this(itemStack.getTypeId(),
                itemStack.getAmount(),
                itemStack.getDurability(),
                itemStack.getData().getData(),
                getEnchantmentsMap(itemStack.getEnchantments()));
    }

    private SerializableItemStack(final int type, final int amount, final short durability, final byte data, final Map<Integer, Integer> enchantments) {
        this.type = type;
        this.amount = amount;
        this.durability = durability;
        this.data = data;
        this.enchantments = enchantments;
    }

    /**
     * <p>
     * The following types can be used to compare the instance with the passed value:
     * </p>
     * <ul>
     * <li><b>SerializableItemstack:</b> All values (including enchantments) will be compared.</li>
     * <li><b>Material:</b> Only the material id will be compared.</li>
     * <li><b>Itemstack:</b> All values (including enchantments) will be compared by internally creating a new instance of SerializableItemStack based on the given ItemStack.</li>
     * <li><b>String:</b> All values (including enchantments) will be compared by internally creating a new instance of SerializableItemStack. The internally used instance will be
     * created through the {@link #fromString(String)} method.</li>
     * </ul>
     * 
     * @return true if the passed value is equal to this instance.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj != null) {
            if (obj instanceof SerializableItemStack) {
                final SerializableItemStack o = (SerializableItemStack) obj;
                return o.type == type && o.amount == amount && o.durability == durability && o.data == data && o.enchantments.equals(enchantments);
            } else if (obj instanceof Material) {
                return ((Material) obj).getId() == type;
            } else if (obj instanceof ItemStack) {
                final ItemStack o = (ItemStack) obj;
                return equals(new SerializableItemStack(o));
            } else if (obj instanceof String) {
                return equals(fromString((String) obj));
            }
        }
        return false;
    }

    /**
     * @return the amount.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @return the durability.
     */
    public short getDurability() {
        return durability;
    }

    /**
     * @return a Map containing all enchantments (key) as well as their level (value).
     */
    public Map<Enchantment, Integer> getEnchantments() {
        final Map<Enchantment, Integer> r = new HashMap<>();
        for (final int i : enchantments.keySet()) {
            r.put(Enchantment.getById(i), enchantments.get(i));
        }
        return Collections.unmodifiableMap(r);
    }

    /**
     * @return the Material.
     */
    public Material getMaterial() {
        return Material.getMaterial(type);
    }

    /**
     * @return the MaterialData.
     */
    public MaterialData getMaterialData() {
        return new MaterialData(type, data);
    }

    /**
     * @return the type.
     */
    public int getType() {
        return type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + amount;
        result = prime * result + data;
        result = prime * result + durability;
        result = prime * result + ((enchantments == null) ? 0 : enchantments.hashCode());
        result = prime * result + type;
        return result;
    }

    /**
     * Creates a new ItemStack based on this instance. If this instance contains enchantments they will be added in an unsafe way.
     * 
     * @return the ItemStack.
     */
    public ItemStack toItemStack() {
        final ItemStack r = new ItemStack(getMaterial(), amount, durability, data);
        final Map<Enchantment, Integer> m = getEnchantments();
        for (final Enchantment e : m.keySet()) {
            r.addUnsafeEnchantment(e, m.get(e));
        }
        return r;
    }

    /**
     * <p>
     * Creates a String representing this instance in the following format:
     * <p>
     * <ul>
     * <li><b>Without enchantments:</b> [type],[amount],[durability],[data]</li>
     * <li><b>With enchantments:</b> [type],[amount],[durability],[data],{[enchantment1]=[level];[enchantment2]=[level]}</li>
     * </ul>
     * 
     * @return a String representing this instance.
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder()
                .append(type).append(",")
                .append(amount).append(",")
                .append(durability).append(",")
                .append(data);

        if (!enchantments.isEmpty()) {
            sb.append(",{");
            int c = 1;
            for (final int i : enchantments.keySet()) {
                sb.append(i).append("=").append(enchantments.get(i));
                if (c != enchantments.size()) {
                    sb.append(";");
                }
                c++;
            }
            sb.append("}");
        }

        return sb.toString();
    }

}
