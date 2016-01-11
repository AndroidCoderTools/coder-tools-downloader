package com.tools.coder.downloader_library;

import android.content.Context;

import com.tools.coder.downloader_library.impl.FileDownloaderQueue;

/**
 * File download manager, use {@link #getInstance()} and must call {@link #initManger}
 * Created by Spring-Xu on 16/1/8.
 */
public class FileDownloadManager {
    private DownloaderQueue downloaderQueue;
    private static FileDownloadManager manager;

    /**
     * get an instance of  {@link FileDownloadManager}
     * @return
     */
    public static FileDownloadManager getInstance() {
        if (manager == null) {
            synchronized (FileDownloadManager.class) {
                if (manager == null) {
                    manager = new FileDownloadManager();
                }
            }
        }
        return manager;
    }

    /**
     * Initialize manager, must be called before call {@link #downloadFile(String, String, IDownloadListener)}
     * @param c
     */
    public void initManger(Context c) {
        downloaderQueue = new FileDownloaderQueue(c);
    }

    /**
     * Create a new download request, and offer it to download queue
     *
     * @param url              file url
     * @param path             download and store path
     * @param downloadListener result call back
     */
    public void downloadFile(String url, String path, IDownloadListener downloadListener) {
        FileRequest fileRequest = new FileRequest();
        fileRequest.setPath(path);
        fileRequest.setUrl(url);
        fileRequest.setDownloadListener(downloadListener);
        downloaderQueue.offer(fileRequest);
    }
}
