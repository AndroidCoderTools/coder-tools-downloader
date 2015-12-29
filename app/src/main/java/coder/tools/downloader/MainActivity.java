package coder.tools.downloader;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tools.coder.downloader.store.cache.DownloaderCache;

import java.util.ArrayList;

public class MainActivity extends Activity {

    Button add;
    Button destory;
    Button destoryCache;
    TextView valueTv;
    DownloaderCache downloadCache = null;
    ArrayList<String> keys = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = (Button) findViewById(R.id.add);
        destory = (Button) findViewById(R.id.destory);
        destoryCache = (Button) findViewById(R.id.destory_cache);
        valueTv = (TextView) findViewById(R.id.value);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        destory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        destoryCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}
