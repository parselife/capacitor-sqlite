package cn.stormend.capacitor;

import android.util.Log;

public class SqlitePlugin {

    public String echo(String value) {
        Log.i("Echo", value);
        return value;
    }
}
