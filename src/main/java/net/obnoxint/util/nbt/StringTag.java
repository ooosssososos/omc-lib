package net.obnoxint.util.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class StringTag extends NBTTag {

    private String payload;

    public StringTag(final String name, final String payload) {
        super(TAG_STRING, name);
        this.payload = payload == null ? "" : payload;
    }

    StringTag() {}

    @Override
    public String getPayload() {
        return payload;
    }

    @Override
    void read(final DataInput in) throws IOException {
        payload = in.readUTF();
    }

    @Override
    void write(final DataOutput out) throws IOException {
        out.writeUTF(payload);
    }

}
