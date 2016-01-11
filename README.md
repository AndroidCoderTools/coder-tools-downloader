# What is coder-tools-downloader?
It is a lib for android coders to download file in application.

# How to use?
```Java
  FileDownloadManager.getInstance().initManger(this);
FileDownloadManager.getInstance().downloadFile("http://img.kuaidadi.com/download/kuaididache_4.4_20208_10001.apk", "/sdcard/didi/kuaididache_4.4_20208_10001.apk", new IDownloadListener() {
                    @Override
                    public void onStart(String url) {
                      //onStart
                    }

                    @Override
                    public void onProgress(long downloadSize, long totalSize) {
                      //onProgress
                    }

                    @Override
                    public void onComplete(String url, String filePath) {
                      //onComplete
                    }

                    @Override
                    public void onError(String errorMsg) {
                      //onComplete
                    }
                });
```

# How to work?
Create a download task and manage that, It is use the sample way to download file, We will complete at follow versions.

# How to contact us?
Email: hquspring@gmail.com
