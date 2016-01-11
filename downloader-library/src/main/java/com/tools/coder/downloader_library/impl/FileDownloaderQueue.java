package com.tools.coder.downloader_library.impl;

import android.content.Context;

import com.tools.coder.cacher.impl.LocalCache;
import com.tools.coder.downloader_library.DownloaderQueue;
import com.tools.coder.downloader_library.FileRequest;

import java.util.ArrayList;
import java.util.HashMap;

import cjstar.com.customthreadpoollibrary.CustomExecutor;
import cjstar.com.customthreadpoollibrary.CustomThreadPoolExecutor;

/**
 * This queue is manage the file requests, offer request or delete requests
 * <p/>
 * Created by Spring-Xu on 16/1/7.
 */
public class FileDownloaderQueue implements DownloaderQueue {
    private HashMap<String, ArrayList<FileRequest>> queueMap;
    private LocalCache localCache;
    private CustomExecutor customExecutor;

    private boolean layzInitTag = false;

    private Context context;

    public FileDownloaderQueue(Context context) {
        this.context = context;
        lazyInit();
    }

    private synchronized void lazyInit() {
        if (context == null) {
            throw new NullPointerException("context is null");
        }

        if (!layzInitTag) {
            layzInitTag = true;
            localCache = new LocalCache("FileDownloaderQueue");
            customExecutor = new CustomThreadPoolExecutor();
            queueMap = new HashMap<>();
        }
    }

    @Override
    public synchronized void offer(FileRequest fileRequest) {
        if (fileRequest == null) {
            throw new NullPointerException("fileRequest is null");
        }

        lazyInit();

//        FileRequest cacheRequest = (FileRequest) localCache.getInner(context, fileRequest.getUrl());
//
//        if (cacheRequest == null) {
//            ArrayList<FileRequest> requestArrayList = queueMap.get(fileRequest.getUrl());
//            if (requestArrayList == null || requestArrayList.size() == 0) {
//                requestArrayList = new ArrayList<>();
//            }
//            requestArrayList.add(fileRequest);
//            queueMap.put(fileRequest.getUrl(), requestArrayList);
//
//        } else {
//            ArrayList<FileRequest> requestArrayList = queueMap.get(fileRequest.getUrl());
//            if (requestArrayList == null || requestArrayList.size() == 0) {
//                requestArrayList = new ArrayList<>();
//            }
//            requestArrayList.add(fileRequest);
//            queueMap.put(fileRequest.getUrl(), requestArrayList);
//        }

        if (queueMap.get(fileRequest.getUrl()) == null) {
            ArrayList<FileRequest> requestArrayList = new ArrayList<>();
            if (requestArrayList == null || requestArrayList.size() == 0) {
                requestArrayList = new ArrayList<>();
            }
            requestArrayList.add(fileRequest);
            queueMap.put(fileRequest.getUrl(), requestArrayList);

            //call download
            DownloadTask downloadTask = new DownloadTask(requestArrayList);
            customExecutor.execute(downloadTask);
        } else {

            ArrayList<FileRequest> requestArrayList = queueMap.get(fileRequest.getUrl());
            requestArrayList.add(fileRequest);
        }


    }

    @Override
    public synchronized void remove(FileRequest fileRequest) {
        lazyInit();
    }

    @Override
    public synchronized void start() {
        lazyInit();
    }

    @Override
    public synchronized void stop() {
        lazyInit();
    }
}
