package coder.tools.downloader;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tools.coder.download.cache.impl.LocalCache;

import java.util.ArrayList;

public class MainActivity extends Activity {

    Button add;
    Button destory;
    Button destoryCache;
    TextView valueTv;
    LocalCache downloadCache = null;
    ArrayList<String> keys = new ArrayList<String>();
    String key = "xyczadsadasdsadsa";
    int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = (Button) findViewById(R.id.add);
        destory = (Button) findViewById(R.id.destory);
        destoryCache = (Button) findViewById(R.id.destory_cache);
        valueTv = (TextView) findViewById(R.id.value);
        downloadCache = new LocalCache();
//        downloadCache.putAndSave(MainActivity.this,key,value);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value = downloadCache.getInner(MainActivity.this, key) == null ? 0 : (int) downloadCache.getInner(MainActivity.this, key);
                valueTv.setText(++value + "");
                downloadCache.putAndSave(MainActivity.this, key, value);
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
