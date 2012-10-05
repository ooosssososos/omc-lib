package net.obnoxint.util.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class ShortTag extends NBTTag {

    private short payload;

    public ShortTag(final String name, final short payload) {
        super(TAG_SHORT, name);
        this.payload = payload;
    }

    ShortTag() {}

    @Override
    public Short getPayload() {
        return new Short(payload);
    }

    @Override
    void read(final DataInput in) throws IOException {
        payload = in.readShort();
    }

    @Override
    void write(final DataOutput out) throws IOException {
        out.writeShort(payload);
    }

}
