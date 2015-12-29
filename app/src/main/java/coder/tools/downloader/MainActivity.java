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

        final long curr = System.currentTimeMillis();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (downloadCache == null) {
                    downloadCache = new DownloaderCache();
                }
//
//                Ta aa = new Ta();
//                aa.setName("object name");
//                aa.setLevele(11);
//                String key = System.currentTimeMillis() + "";
//                downloadCache.putAndSave(MainActivity.this, key, aa);
//                keys.add(key);
//                show();
//
//                if (true) {
//                    return;
//                }

                //Parcelable
                TT a = new TT();
                long keyL = System.currentTimeMillis();
                a.setName(keyL + "");
                downloadCache.putAndSave(MainActivity.this, keyL + "", a);
                keys.add(keyL + "");


                //int
                int intvalue = 12345;
                downloadCache.putAndSave(MainActivity.this, keyL + "1", intvalue);
                keys.add(keyL + "1");

                long longvalue = 12345;
                downloadCache.putAndSave(MainActivity.this, keyL + "2", longvalue);
                keys.add(keyL + "2");

                float floatvalue = 12345.01f;
                downloadCache.putAndSave(MainActivity.this, keyL + "3", floatvalue);
                keys.add(keyL + "3");

                short shortvalue = 11;
                downloadCache.putAndSave(MainActivity.this, keyL + "4", shortvalue);
                keys.add(keyL + "4");

                char charvalue = '1';
                downloadCache.putAndSave(MainActivity.this, keyL + "5", charvalue);
                keys.add(keyL + "5");

                double doublevalue = 12.33333444;
                downloadCache.putAndSave(MainActivity.this, keyL + "6", doublevalue);
                keys.add(keyL + "6");

                show();
            }
        });

        destory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadCache = null;
                downloadCache = new DownloaderCache();
            }
        });

        destoryCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (String key : keys) {
                    downloadCache.clearAll(key);
                }

                keys.clear();
            }
        });
    }

    private void show() {
        System.out.println("keys length:" + keys.size());
        valueTv.setText("");
//        for (int i = 0; i < keys.size(); i++) {
//            String key = keys.get(i);
//            Ta a = (Ta) downloadCache.getInner(this, key);
//            if(a!=null){
//                valueTv.append("name:" + a.getName() + "  level:" + a.getLevele() + "\n");
//            }
//        }
//
//        if (true) {
//            return;
//        }

        try {
            for (int i = 0; i < keys.size(); i++) {
                String key = keys.get(i);
                String value = "";
                switch (i % 7) {
                    case 0:
                        TT ta = (TT)downloadCache.getInner(this, key);
                        value = ta.getName();
                        break;

                    case 1:
                        int intvalue = (int)downloadCache.getInner(this, key);
                        value += intvalue;
                        break;

                    case 2:
                        long longvalue = (long)downloadCache.getInner(this, key);
                        value += longvalue;
                        break;

                    case 3:
                        float floatvalue = (float)downloadCache.getInner(this, key);
                        value += floatvalue;
                        break;

                    case 4:
                        short shortvalue = (short)downloadCache.getInner(this, key);
                        value += shortvalue;
                        break;

                    case 5:
                        char charvalue = (char)downloadCache.getInner(this, key);
                        value += charvalue;
                        break;

                    case 6:
                        double doublevalue = (double)downloadCache.getInner(this, key);
                        value += doublevalue;
                        break;

                    default:
                        break;
                }
                valueTv.append("value:" + value + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
