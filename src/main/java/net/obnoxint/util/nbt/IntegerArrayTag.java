package net.obnoxint.util.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class IntegerArrayTag extends NBTTag {

    private int[] payload;

    public IntegerArrayTag(final String name, final int[] payload) {
        super(TAG_INTEGER_ARRAY, name);
        final int[] p = payload == null ? new int[0] : new int[payload.length];
        if (p.length > 0) {
            System.arraycopy(payload, 0, p, 0, p.length);
        }
        this.payload = p;
    }

    IntegerArrayTag() {}

    @Override
    public int[] getPayload() {
        final int[] r = new int[payload.length];
        System.arraycopy(payload, 0, r, 0, r.length);
        return r;
    }

    @Override
    void read(final DataInput in) throws IOException {
        final int s = in.readInt();
        final int[] p = new int[s];
        for (int i = 0; i < s; i++) {
            p[i] = in.readInt();
        }
        payload = p;
    }

    @Override
    void write(final DataOutput out) throws IOException {
        out.writeInt(payload.length);
        for (int i = 0; i < payload.length; i++) {
            out.writeInt(payload[i]);
        }
    }

}
