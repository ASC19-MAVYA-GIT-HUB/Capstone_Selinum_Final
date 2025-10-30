package com.ajio.utilities;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class FileUtilities {

    public static void copyFile(File source, File destination) throws IOException {
        FileUtils.copyFile(source, destination);
    }
}