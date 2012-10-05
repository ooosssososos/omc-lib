package net.obnoxint.util.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class FloatTag extends NBTTag {

    private float payload;

    public FloatTag(final String name, final float payload) {
        super(TAG_FLOAT, name);
        this.payload = payload;
    }

    FloatTag() {}

    @Override
    public Float getPayload() {
        return new Float(payload);
    }

    @Override
    void read(final DataInput in) throws IOException {
        payload = in.readFloat();
    }

    @Override
    void write(final DataOutput out) throws IOException {
        out.writeFloat(payload);
    }

}
