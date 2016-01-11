package com.tools.coder.downloader_library;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by Spring-Xu on 16/1/11.
 */
public class Utils {

    /**
     * Copy file fromFile to toFile
     *
     * @param fromFile
     * @param toFile
     * @return copy operation result
     */
    public static boolean copyFile(File fromFile, File toFile) {
        boolean regTag = false;

        if (fromFile == null || toFile == null) {
            return regTag;
        }

        if (!fromFile.exists()) {
            return regTag;
        }

        try {
            if (!toFile.exists()) {
                toFile.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return regTag;
        }


        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;

        try {
            fi = new FileInputStream(fromFile);
            fo = new FileOutputStream(toFile);
            in = fi.getChannel();//get from file channel
            out = fo.getChannel();//get to file channel
            in.transferTo(0, in.size(), out);//connect channel and write file
            regTag = true;//copy success
        } catch (IOException e) {
            e.printStackTrace();
            regTag = false;//copy fail

        } finally {
            try {
                fi.close();
                in.close();
                fo.close();
                out.close();

            } catch (IOException e) {
                e.printStackTrace();
                regTag = false;//copy fail

            }
        }

        return regTag;
    }
}
