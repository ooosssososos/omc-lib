package net.obnoxint.util.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class ByteArrayTag extends NBTTag {

    private byte[] payload;

    public ByteArrayTag(final String name, final byte[] payload) {
        super(TAG_BYTE_ARRAY, name);
        final byte[] p = payload == null ? new byte[0] : new byte[payload.length];
        if (p.length > 0) {
            System.arraycopy(payload, 0, p, 0, p.length);
        }
        this.payload = p;
    }

    ByteArrayTag() {}

    @Override
    public byte[] getPayload() {
        final byte[] r = new byte[payload.length];
        System.arraycopy(payload, 0, r, 0, r.length);
        return r;
    }

    @Override
    void read(final DataInput in) throws IOException {
        final int s = in.readInt();
        final byte[] p = new byte[s];
        in.readFully(p);
        payload = p;
    }

    @Override
    void write(final DataOutput out) throws IOException {
        out.writeInt(payload.length);
        out.write(payload);
    }

}
