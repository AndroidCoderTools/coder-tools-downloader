package coder.tools.downloader;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xuchun on 15/12/29.
 */
public class Ta implements Parcelable{

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

    public Ta() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(age);
        dest.writeInt(levele);
    }

    // 用来创建自定义的Parcelable的对象
    public final Parcelable.Creator<Ta> CREATOR
            = new Parcelable.Creator<Ta>() {
        public Ta createFromParcel(Parcel in) {
            return new Ta(in);
        }

        public Ta[] newArray(int size) {
            return new Ta[size];
        }
    };

    // 读数据进行恢复
    private Ta(Parcel in) {
        name = in.readString();
        age = in.readString();
        levele = in.readInt();
    }
}
