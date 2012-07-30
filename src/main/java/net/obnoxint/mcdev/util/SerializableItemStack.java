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

    public static SerializableItemStack fromString(String string) {
        SerializableItemStack r = null;
        String[] s = string.split(",");

        if (s.length == 4 || s.length == 5) {
            try {
                int type = Integer.parseInt(s[0]);
                int amount = Integer.parseInt(s[1]);
                short durability = Short.parseShort(s[2]);
                byte data = Byte.parseByte(s[3]);
                Map<Integer, Integer> enchantments = new HashMap<>();
                if (s.length == 5) {
                    String[] es = s[4].substring(1, s[4].length() - 1).split(";");
                    for (int i = 0; i < es.length; i++) {
                        String[] e = es[i].split("=");
                        if (e.length == 2) {
                            enchantments.put(Integer.parseInt(e[0]), Integer.parseInt(e[1]));
                        }
                    }
                }
                r = new SerializableItemStack(type, amount, durability, data, enchantments);
            } catch (NumberFormatException e) {}
        }

        return r;
    }

    private static Map<Integer, Integer> getEnchantmentsMap(Map<Enchantment, Integer> map) {
        Map<Integer, Integer> r = new HashMap<>();
        for (Enchantment e : map.keySet()) {
            r.put(e.getId(), map.get(e));
        }
        return r;
    }

    private final int type;
    private final int amount;
    private final short durability;
    private final byte data;

    private final Map<Integer, Integer> enchantments;

    public SerializableItemStack(ItemStack itemStack) {
        this(itemStack.getTypeId(),
                itemStack.getAmount(),
                itemStack.getDurability(),
                itemStack.getData().getData(),
                getEnchantmentsMap(itemStack.getEnchantments()));
    }

    private SerializableItemStack(int type, int amount, short durability, byte data, Map<Integer, Integer> enchantments) {
        this.type = type;
        this.amount = amount;
        this.durability = durability;
        this.data = data;
        this.enchantments = enchantments;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof SerializableItemStack) {
                SerializableItemStack o = (SerializableItemStack) obj;
                return o.type == type && o.amount == amount && o.durability == durability && o.data == data && o.enchantments.equals(enchantments);
            } else if (obj instanceof Material){
                return ((Material)obj).getId() == type;
            } else if (obj instanceof ItemStack){
                ItemStack o = (ItemStack) obj;
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
        Map<Enchantment, Integer> r = new HashMap<>();
        for (int i : enchantments.keySet()) {
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
        ItemStack r = new ItemStack(getMaterial(), amount, durability, data);
        Map<Enchantment, Integer> m = getEnchantments();
        for (Enchantment e : m.keySet()) {
            r.addUnsafeEnchantment(e, m.get(e));
        }
        return r;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append(type).append(",")
                .append(amount).append(",")
                .append(durability).append(",")
                .append(data);

        if (!enchantments.isEmpty()) {
            sb.append(",{");
            int c = 1;
            for (int i : enchantments.keySet()) {
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
