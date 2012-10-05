package net.obnoxint.util.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class LongTag extends NBTTag {

    private long payload;

    public LongTag(final String name, final long payload) {
        super(TAG_LONG, name);
        this.payload = payload;
    }

    LongTag() {}

    @Override
    public Long getPayload() {
        return new Long(payload);
    }

    @Override
    void read(final DataInput in) throws IOException {
        payload = in.readLong();
    }

    @Override
    void write(final DataOutput out) throws IOException {
        out.writeLong(payload);
    }

}
