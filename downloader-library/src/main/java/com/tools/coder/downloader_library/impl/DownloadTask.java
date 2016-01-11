package com.tools.coder.downloader_library.impl;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.tools.coder.downloader_library.FileRequest;
import com.tools.coder.downloader_library.IDownloadListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Download task impl
 * Created by Spring-Xu on 16/1/8.
 */
public class DownloadTask implements Runnable, IDownloadListener {

    /**
     * Notify progress interval time
     */
    private int intervalTime = 600;
    private ArrayList<FileRequest> fileRequestArrayList;

    public DownloadTask(ArrayList<FileRequest> fileRequest) {
        fileRequestArrayList = fileRequest;
    }

    @Override
    public void run() {
        if (fileRequestArrayList == null || fileRequestArrayList.size() == 0) {
            return;
        }

        download(fileRequestArrayList.get(0).getPath(), fileRequestArrayList.get(0).getUrl());
    }

    /**
     * Download net work
     *
     * @param path
     * @param url
     */
    public void download(String path, String url) {
        try {
            onStart(url);

            URL e = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) e.openConnection();
            conn.connect();
            int code = conn.getResponseCode();

            if (code == HttpURLConnection.HTTP_OK || code == HttpURLConnection.HTTP_PARTIAL) {

                OutputStream outStream = null;
                InputStream inputStream = null;
                File file = new File(path);
                try {
                    if (file.exists()) {
                        file.delete();
                    }

                    file.createNewFile();
                    outStream = new FileOutputStream(file);
                    inputStream = conn.getInputStream();
                    //four buffer area
                    byte bytes1024[] = new byte[1024];
                    byte bytes128[] = new byte[128];
                    byte bytes32[] = new byte[32];
                    int i = 0;

                    int length = 0;
                    long totalLength = conn.getContentLength();
                    long timeStamp = System.currentTimeMillis();
                    do {
                        if (inputStream.available() > 1024) {
                            inputStream.read(bytes1024);
                            outStream.write(bytes1024);
                            length += 1024;
                            if (isShowTime(timeStamp)) {
                                timeStamp = System.currentTimeMillis();
                                onProgress(length, totalLength);
                            }
                            continue;
                        }

                        if (inputStream.available() > 128) {
                            inputStream.read(bytes128);
                            outStream.write(bytes128);
                            length += 128;
                            if (isShowTime(timeStamp)) {
                                timeStamp = System.currentTimeMillis();
                                onProgress(length, totalLength);
                            }
                            continue;
                        }

                        if (inputStream.available() > 32) {
                            inputStream.read(bytes32);
                            outStream.write(bytes32);
                            length += 32;
                            if (isShowTime(timeStamp)) {
                                timeStamp = System.currentTimeMillis();
                                onProgress(length, totalLength);
                            }
                            continue;
                        }

                        i = inputStream.read();
                        if (i != -1) {
                            outStream.write(i);

                        } else {
                            onProgress(totalLength,totalLength);
                            break;
                        }

                    } while (true);

                    outStream.flush();

                } catch (Throwable e2) {
                    e2.printStackTrace();
                    onError(e2.getMessage());
                    Log.d("DownloadTask", "downloading End, failed: " + e2.getMessage());
                    return;

                } finally {
                    if (outStream != null) {
                        outStream.close();
                    }

                    if (inputStream != null) {
                        inputStream.close();
                    }
                }

                //download finish
                onComplete(url, path);

            } else {
                Log.d("DownloadTask", "downloading End, failed code: " + code);
                onError("");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            Log.d("DownloadTask", "downloading End, failed: " + e1.getMessage());
            onError("");
        }
    }

    /**
     * If the time is larger than 600 mills, show the speed once
     *
     * @param timeStamp
     * @return
     */
    private boolean isShowTime(long timeStamp) {
        return System.currentTimeMillis() - timeStamp > intervalTime;
    }

    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void onStart(final String url) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (fileRequestArrayList == null || fileRequestArrayList.size() == 0) {
                    return;
                }

                int size = fileRequestArrayList.size();
                for (int index = 0; index < size; index++) {
                    FileRequest request = fileRequestArrayList.get(index);
                    if (request != null && request.getDownloadListener() != null) {
                        request.getDownloadListener().onStart(url);
                    }
                }
            }
        });
    }

    @Override
    public void onProgress(final long downloadSize, final long totalSize) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (fileRequestArrayList == null || fileRequestArrayList.size() == 0) {
                    return;
                }

                int size = fileRequestArrayList.size();
                for (int index = 0; index < size; index++) {
                    FileRequest request = fileRequestArrayList.get(index);
                    if (request != null && request.getDownloadListener() != null) {
                        request.getDownloadListener().onProgress(downloadSize, totalSize);
                    }
                }
            }
        });
    }

    @Override
    public void onComplete(final String url, final String filePath) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (fileRequestArrayList == null || fileRequestArrayList.size() == 0) {
                    return;
                }
                int size = fileRequestArrayList.size();
                for (int index = 0; index < size; index++) {
                    FileRequest request = fileRequestArrayList.get(index);
                    if (request != null && request.getDownloadListener() != null) {
                        //TODO Known BUG: if the file path is not the same , we should call Utils.copyFile
                        request.getDownloadListener().onComplete(url, filePath);
                    }
                }
            }
        });
    }

    @Override
    public void onError(final String errorMsg) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (fileRequestArrayList == null || fileRequestArrayList.size() == 0) {
                    return;
                }
                int size = fileRequestArrayList.size();
                for (int index = 0; index < size; index++) {
                    FileRequest request = fileRequestArrayList.get(index);
                    if (request != null && request.getDownloadListener() != null) {
                        request.getDownloadListener().onError(errorMsg);
                    }
                }
            }
        });
    }
}
