package com.owl.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2021/7/2.
 */

public class LnkParser {

    public static File parse(File f) throws Exception {
        // read the entire file into a byte buffer
        FileInputStream fin = new FileInputStream(f);
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] buff = new byte[256];
        while (true) {
            int n = fin.read(buff);
            if (n == -1) {
                break;
            }
            bout.write(buff, 0, n);
        }
        fin.close();
        byte[] link = bout.toByteArray();

        // get the flags byte
        byte flags = link[0x14];

        // if the shell settings are present, skip them
        final int shell_offset = 0x4c;
        int shell_len = 0;
        if ((flags & 0x1) > 0) {
            // the plus 2 accounts for the length marker itself
            shell_len = bytes2short(link, shell_offset) + 2;
        }

        // get to the file settings
        int file_start = 0x4c + shell_len;

        // get the local volume and local system values
        int local_sys_off = link[file_start + 0x10] + file_start;
        String real_file = getNullDelimitedString(link, local_sys_off);
        return new File(real_file);
    }

    private static String getNullDelimitedString(byte[] bytes, int off) {
        int len = 0;
        // count bytes until the null character (0)
        while (true) {
            if (bytes[off + len] == 0) {
                break;
            }
            len++;
        }
        return new String(bytes, off, len, Charset.forName("gbk"));
    }

    // convert two bytes into a short // note, this is little endian because
    // it's for an // Intel only OS.
    private static int bytes2short(byte[] bytes, int off) {
        byte a1 = bytes[off];
        int temp = bytes[off + 1];
        int a2 = (bytes[off + 1] << 8);
        return a1 | a2;
    }

}
