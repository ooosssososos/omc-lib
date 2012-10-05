package net.obnoxint.util.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class IntegerTag extends NBTTag {

    private int payload;

    public IntegerTag(final String name, final int payload) {
        super(TAG_INTEGER, name);
        this.payload = payload;
    }

    IntegerTag() {}

    @Override
    public Integer getPayload() {
        return new Integer(payload);
    }

    @Override
    void read(final DataInput in) throws IOException {
        payload = in.readInt();
    }

    @Override
    void write(final DataOutput out) throws IOException {
        out.writeInt(payload);
    }

}
