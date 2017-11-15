package com.example.michael.watnotes.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by michael on 10/19/17.
 */

public class IOUtil {

    public static class FileInfo {
        private final String mFileUri;
        private final byte[] mFileContents;

        public FileInfo(String fileUri, byte[] fileContents) {
            mFileUri = fileUri;
            mFileContents = fileContents;
        }

        public String getFileUri() {
            return mFileUri;
        }

        public byte[] getFileContents() {
            return mFileContents;
        }
    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }

        return byteBuffer.toByteArray();
    }
}
