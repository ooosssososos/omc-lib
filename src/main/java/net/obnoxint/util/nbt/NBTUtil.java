package net.obnoxint.util.nbt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class NBTUtil {

    public static CompoundTag readFromFile(final File file, final boolean compressed) throws IOException {
        DataInputStream in;
        if (compressed) {
            in = new DataInputStream(new BufferedInputStream(new GZIPInputStream(new FileInputStream(file))));
        } else {
            in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
        }
        final CompoundTag tag = (CompoundTag) NBTTag.readTag(in);
        in.close();
        return tag;
    }

    public static void writeToFile(final CompoundTag tag, final File file, final boolean compress) throws IOException {
        DataOutputStream out;
        if (compress) {
            out = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(file))));
        } else {
            out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
        }
        NBTTag.writeTag(tag, out);
        out.flush();
        out.close();
    }

}
