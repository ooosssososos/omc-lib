package net.obnoxint.util.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class DoubleTag extends NBTTag {

    private double payload;

    public DoubleTag(final String name, final double payload) {
        super(TAG_DOUBLE, name);
        this.payload = payload;
    }

    DoubleTag() {}

    @Override
    public Double getPayload() {
        return new Double(payload);
    }

    @Override
    void read(final DataInput in) throws IOException {
        payload = in.readDouble();
    }

    @Override
    void write(final DataOutput out) throws IOException {
        out.writeDouble(payload);
    }

}
