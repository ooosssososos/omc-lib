package net.obnoxint.util.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class NBTTag {

    static final class TagHeader {

        private final byte type;
        private final String name;

        public TagHeader(final byte type, final String name) {
            this.type = type;
            this.name = name == null ? "" : name;
        }

        private void write(final DataOutput out) throws IOException {
            out.writeByte(type);
            out.writeUTF(name);
        }

    }

    public static final byte
            TAG_END = 0,
            TAG_BYTE = 1,
            TAG_SHORT = 2,
            TAG_INTEGER = 3,
            TAG_LONG = 4,
            TAG_FLOAT = 5,
            TAG_DOUBLE = 6,
            TAG_BYTE_ARRAY = 7,
            TAG_STRING = 8,
            TAG_LIST = 9,
            TAG_COMPOUND = 10,
            TAG_INTEGER_ARRAY = 11;

    static NBTTag createSkeleton(final byte type) {
        switch (type) {
        case TAG_END:
            return new EndTag();
        case TAG_BYTE:
            return new ByteTag();
        case TAG_SHORT:
            return new ShortTag();
        case TAG_INTEGER:
            return new IntegerTag();
        case TAG_LONG:
            return new LongTag();
        case TAG_FLOAT:
            return new FloatTag();
        case TAG_DOUBLE:
            return new DoubleTag();
        case TAG_BYTE_ARRAY:
            return new ByteArrayTag();
        case TAG_STRING:
            return new StringTag();
        case TAG_LIST:
            return new ListTag<NBTTag>();
        case TAG_COMPOUND:
            return new CompoundTag();
        case TAG_INTEGER_ARRAY:
            return new IntegerArrayTag();
        }
        return null;
    }

    static NBTTag readTag(final DataInput in) throws IOException {
        NBTTag r = null;
        final byte t = in.readByte();
        if (t == TAG_END) {
            return new EndTag();
        }
        r = createSkeleton(t);
        if (r != null) {
            r.header = new TagHeader(t, in.readUTF());
            r.read(in);
        }
        return r;
    }

    static void writeTag(final NBTTag tag, final DataOutput out) throws IOException {
        tag.header.write(out);
        tag.write(out);
    }

    private TagHeader header = null;

    public NBTTag(final byte type, final String name) {
        this(new TagHeader(type, name));
    }

    NBTTag() {
        this(null);
    }

    NBTTag(final TagHeader header) {
        this.header = header;
    }

    public final String getName() {
        return header.name;
    }

    public abstract Object getPayload();

    public final byte getType() {
        if (hasHeader()) {
            return header.type;
        } else {
            return -1;
        }
    }

    public final boolean hasHeader() {
        return header != null;
    }

    abstract void read(DataInput in) throws IOException;

    abstract void write(DataOutput out) throws IOException;

}
