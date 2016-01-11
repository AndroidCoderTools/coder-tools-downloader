package coder.tools.downloader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tools.coder.downloader_library.FileDownloadManager;
import com.tools.coder.downloader_library.IDownloadListener;

public class MainActivity extends Activity {

    Button add;
    TextView valueTv;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = (Button) findViewById(R.id.add);
        valueTv = (TextView) findViewById(R.id.value);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        FileDownloadManager.getInstance().initManger(this);
        progressBar.setMax(100);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileDownloadManager.getInstance().downloadFile("http://img.kuaidadi.com/download/kuaididache_4.4_20208_10001.apk", "/sdcard/didi/kuaididache_4.4_20208_10001.apk", new IDownloadListener() {
                    @Override
                    public void onStart(String url) {
                        valueTv.setText("onStart:" + url);
                        progressBar.setProgress(0);
                    }

                    @Override
                    public void onProgress(long downloadSize, long totalSize) {
                        double pr = (downloadSize * 1.0) / (totalSize * 1.0);
                        Log.d("MainActivity","onProgress:"+pr);
                        progressBar.setProgress((int) (pr*100));
                    }

                    @Override
                    public void onComplete(String url, String filePath) {
                        valueTv.setText("onComplete:" + filePath);
                    }

                    @Override
                    public void onError(String errorMsg) {
                        valueTv.setText("onError:" + errorMsg);
                    }
                });
            }
        });
    }
}
