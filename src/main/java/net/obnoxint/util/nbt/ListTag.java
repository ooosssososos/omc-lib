package net.obnoxint.util.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ListTag<T extends NBTTag> extends NBTTag {

    private List<T> payload;

    public ListTag(final String name, final List<T> payload) {
        super(TAG_LIST, name);
        this.payload = payload == null ? new ArrayList<T>() : Collections.unmodifiableList(payload);
    }

    ListTag() {}

    public byte getContentType() {
        return payload.isEmpty() ? -1 : payload.get(0).getType();
    }

    @Override
    public List<T> getPayload() {
        return Collections.unmodifiableList(payload);
    }

    @SuppressWarnings("unchecked")
    @Override
    void read(final DataInput in) throws IOException {
        final byte t = in.readByte();
        final int s = in.readInt();
        final List<T> v = new ArrayList<>();
        for (int i = 0; i < s; i++) {
            final NBTTag tag = createSkeleton(t);
            tag.read(in);
            v.add((T) tag);
        }
        payload = v;
    }

    @Override
    void write(final DataOutput out) throws IOException {
        final byte t = getContentType();
        final int s = payload.size();
        out.writeByte(t == -1 ? TAG_BYTE : t);
        out.writeInt(s);
        for (int i = 0; i < s; i++) {
            payload.get(i).write(out);
        }
    }

}
