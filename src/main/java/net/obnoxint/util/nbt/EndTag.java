package net.obnoxint.util.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class EndTag extends NBTTag {

    public EndTag() {
        super(TAG_END, null);
    }

    @Override
    public Object getPayload() {
        return null;
    }

    @Override
    void read(final DataInput in) throws IOException {}

    @Override
    void write(final DataOutput out) throws IOException {}

}
