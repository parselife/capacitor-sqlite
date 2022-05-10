package cn.stormend.capacitor.plugins.sqlite;

import android.Manifest;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * 提供方法
 * - 创建数据表
 * - 写入数据
 * - 查询数据
 * - 删除数据
 */
@RequiresApi(api = Build.VERSION_CODES.N)
@CapacitorPlugin(name = "SqlitePlugin",
        permissions = {@Permission(strings = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE})})
public class CapacitorSqlitePlugin extends Plugin {

    private CapacitorSqlite implementation;
    private static final String TAG = CapacitorSqlitePlugin.class.getName();
    private Context context;
    private String message = "";

    @Override
    public void load() {
        context = getContext();
        try {
//            AddObserversToNotificationCenter();
            implementation = new CapacitorSqlite(context, getConfig());
        } catch (Exception e) {
            implementation = null;
            message = "CapacitorSQLitePlugin: " + e.getMessage();
            Log.e(TAG, message);
        }
    }

    /**
     * 加载 sdcard 中数据
     *
     * @param call
     */
    @PermissionCallback
    @PluginMethod
    public void loadDatabase(PluginCall call) {
        File extRootFilesDir = context.getExternalFilesDir(null);
        File dbPath = new File(extRootFilesDir, call.getString("dbPath"));
        if (!dbPath.exists()) {
            // 创建db目录
            dbPath.mkdirs();
        }
        File dbFile = new File(dbPath, call.getString("dbName"));
        try {
            if (!dbFile.exists()) {
                dbFile.createNewFile();
//                Uri uri = Uri.fromFile(dbFile);
//                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
//                context.sendBroadcast(intent);
            }
        } catch (IOException e) {
            Log.v(TAG, "create db file failed");
            callError(call, e.getLocalizedMessage());
            return;
        }
        Log.i(TAG, String.format("db file : %s", dbFile.getAbsolutePath()));
        boolean succeed = implementation.loadSQLiteDatabase(dbFile.getAbsolutePath());
        if (succeed) {
            callSingleValue(call, true);
        } else {
            callError(call, "加载数据文件失败");
        }
    }

    public JSArray queryList() {
        implementation.echo()

    }


    /**
     * 返回异常
     *
     * @param call
     * @param msg
     * @param ex
     */
    private void callError(PluginCall call, String msg, Exception ex) {
        call.reject(msg, ex);
    }

    /**
     * 返回异常
     *
     * @param call
     * @param msg
     */
    private void callError(PluginCall call, String msg) {
        call.reject(msg);
    }

    /**
     * 返回单个对象
     *
     * @param call
     * @param value
     */
    private void callSingleValue(PluginCall call, Object value) {
        try {
            call.resolve(new JSObject().put("value", value));
        } catch (Exception e) {
            call.reject(e.getLocalizedMessage());
        }
    }


    /**
     * 插件测试方法
     *
     * @param call
     */
    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");
        if (implementation != null) {
            try {
                JSObject ret = new JSObject();
                ret.put("value", implementation.echo(value));
                call.resolve(ret);
            } catch (Exception e) {
                call.reject(e.getMessage());
            }
        } else {
            call.reject(message);
        }
    }
}
