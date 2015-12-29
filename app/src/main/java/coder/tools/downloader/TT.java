package coder.tools.downloader;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by xuchun on 15/12/29.
 */
public class TT implements Serializable {

    private String name = "xuchun";
    private String age = "1991.11.14";

    public int getLevele() {
        return levele;
    }

    public void setLevele(int levele) {
        this.levele = levele;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    private int levele = 6;

    public TT() {
    }
}