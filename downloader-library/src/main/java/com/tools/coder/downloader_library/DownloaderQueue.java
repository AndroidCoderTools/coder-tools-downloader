package com.tools.coder.downloader_library;

/**
 * Created by Spring-Xu on 16/1/7.
 */
public interface DownloaderQueue {
    /**
     * Add a file download request
     *
     * @param fileRequest
     */
    public void offer(FileRequest fileRequest);

    /**
     * Remove a file download request
     *
     * @param fileRequest
     */
    public void remove(FileRequest fileRequest);

    /**
     * Start download queue
     */
    public void start();

    /**
     * Stop download queue
     */
    public void stop();
}
