package net.obnoxint.mcdev.util;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class SerializableItemStack implements Serializable {

    private static final long serialVersionUID = 6251309292346486453L;

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

    public int getAmount() {
        return amount;
    }

    public short getDurability() {
        return durability;
    }

    public Map<Enchantment, Integer> getEnchantments() {
        final Map<Enchantment, Integer> r = new HashMap<>();
        for (final int i : enchantments.keySet()) {
            r.put(Enchantment.getById(i), enchantments.get(i));
        }
        return Collections.unmodifiableMap(r);
    }

    public Material getMaterial() {
        return Material.getMaterial(type);
    }

    public MaterialData getMaterialData() {
        return new MaterialData(type, data);
    }

    public int getType() {
        return type;
    }

    public ItemStack toItemStack() {
        final ItemStack r = new ItemStack(getMaterial(), amount, durability, data);
        final Map<Enchantment, Integer> m = getEnchantments();
        for (final Enchantment e : m.keySet()) {
            r.addUnsafeEnchantment(e, m.get(e));
        }
        return r;
    }

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
