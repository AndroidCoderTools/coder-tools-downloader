package com.tools.coder.downloader_library;

/**
 * Created by Spring-Xu on 2015/12/4.
 */
public interface IDownloadListener {

    void onStart(String url);

    void onProgress(long downloadSize, long totalSize);

    void onComplete(String url, String filePath);

    void onError(String errorMsg);
}
