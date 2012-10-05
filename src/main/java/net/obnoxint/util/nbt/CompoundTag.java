package net.obnoxint.util.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class CompoundTag extends NBTTag {

    private Map<String, NBTTag> payload;

    public CompoundTag(final String name, final Map<String, NBTTag> payload) {
        super(TAG_COMPOUND, name);
        this.payload = payload == null ? new HashMap<String, NBTTag>() : Collections.unmodifiableMap(payload);
    }

    CompoundTag() {}

    @Override
    public Map<String, NBTTag> getPayload() {
        return Collections.unmodifiableMap(payload);
    }

    @Override
    void read(final DataInput in) throws IOException {
        NBTTag tag;
        final Map<String, NBTTag> p = new HashMap<>();
        while ((tag = readTag(in)).getType() != TAG_END) {
            p.put(tag.getName(), tag);
        }
        payload = p;
    }

    @Override
    void write(final DataOutput out) throws IOException {
        for (final NBTTag tag : payload.values()) {
            writeTag(tag, out);
        }
        out.writeByte(TAG_END);
    }

}
