package net.obnoxint.util.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class ByteTag extends NBTTag {

    private byte payload;

    public ByteTag(final String name, final byte payload) {
        super(TAG_BYTE, name);
        this.payload = payload;
    }

    ByteTag() {}

    @Override
    public Byte getPayload() {
        return new Byte(payload);
    }

    @Override
    void read(final DataInput in) throws IOException {
        payload = in.readByte();
    }

    @Override
    void write(final DataOutput out) throws IOException {
        out.writeByte(payload);
    }

}
