package com.tools.coder.downloader_library;

import java.io.Serializable;

/**
 * File download request
 * <p>
 * Created by Spring-Xu on 16/1/7.
 */
public class FileRequest implements Serializable{
    /**
     * file url
     */
    private String url;
    /**
     * file store path
     */
    private String path;
    /**
     * file download call back
     */
    private IDownloadListener downloadListener;

    public IDownloadListener getDownloadListener() {
        return downloadListener;
    }

    public void setDownloadListener(IDownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
