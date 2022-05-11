package cn.stormend.capacitor.plugins.sqlite;

import static android.os.Environment.MEDIA_MOUNTED;

import static cn.stormend.capacitor.plugins.sqlite.CapacitorSqlitePlugin.PERMISSION_ALIAS;

import android.Manifest;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.RequiresApi;

import cn.stormend.capacitor.plugins.sqlite.support.SQLiteEntityDTO;
import cn.stormend.capacitor.plugins.sqlite.support.SQLiteEntityUpdateDTO;
import cn.stormend.capacitor.plugins.sqlite.support.SQLiteQueryDTO;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;

import java.io.File;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 提供方法
 * - 创建数据表
 * - 写入数据
 * - 查询数据
 * - 删除数据
 */
@RequiresApi(api = Build.VERSION_CODES.N)
@CapacitorPlugin(
        name = "SqlitePlugin",
        permissions = {@Permission(strings = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, alias = PERMISSION_ALIAS)}
)
public class CapacitorSqlitePlugin extends Plugin {

    private CapacitorSqlite implementation;
    private static final String TAG = CapacitorSqlitePlugin.class.getName();
    public static final String PERMISSION_ALIAS = "rw";
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
        if (getPermissionState(PERMISSION_ALIAS) != PermissionState.GRANTED) {
            requestPermissionForAlias(PERMISSION_ALIAS, call, "callback");
        } else {
            loadDatabase0(call);
        }
    }

    @PermissionCallback
    private void callback(PluginCall call) {
        loadDatabase0(call);
    }

    private void loadDatabase0(PluginCall call) {
        String storageState = Environment.getExternalStorageState();
        if (!MEDIA_MOUNTED.equals(storageState)) {
            Log.e(TAG, "移动端存储未挂载!");
            callError(call, "移动端存储未挂载");
            return;
        }
//        File file = context.getDatabasePath(call.getString("dbName"));
        File extRootFilesDir = Environment.getExternalStorageDirectory();
        File dbPath = new File(extRootFilesDir, call.getString("dbPath"));
        if (!dbPath.exists()) {
            // 创建db目录
            dbPath.mkdirs();
        }
        File dbFile = new File(dbPath, call.getString("dbName"));
        try {
            if (!dbFile.exists()) {
                dbFile.createNewFile();
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

    @PluginMethod
    public void queryForList(PluginCall call) {
        if (implementation != null) {
            JSArray array = implementation.queryForList(SQLiteQueryDTO.from(call));
            callSingleValue(call, array);
        } else {
            callError(call, "plugin is null");
        }
    }

    @PluginMethod
    public void queryForObject(PluginCall call) {
        if (implementation != null) {
            JSObject object = implementation.queryForObject(SQLiteQueryDTO.from(call));
            callSingleValue(call, object);
        } else {
            callError(call, "plugin is null");
        }
    }

    @PluginMethod
    public void insert(PluginCall call) {
        if (implementation != null) {
            SQLiteEntityDTO dto = SQLiteEntityDTO.from(call);
            boolean b = implementation.saveEntity(dto);
            callSingleValue(call, b);
        } else {
            callError(call, "plugin is null");
        }
    }

    @PluginMethod
    public void update(PluginCall call) {
        if (implementation != null) {
            SQLiteEntityUpdateDTO dto = (SQLiteEntityUpdateDTO) SQLiteEntityDTO.from(call);
            try {
                assert dto != null;
                dto.setWhereClause(call.getString("whereClause")).setWhereClauseArgs(call.getArray("whereClauseArgs").toList());
            } catch (JSONException e) {
                Log.e(TAG, e.getLocalizedMessage());
                callError(call, e.getLocalizedMessage());
                return;
            }
            boolean b = implementation.update(dto);
            callSingleValue(call, b);
        } else {
            callError(call, "plugin is null");
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
            call.resolve(new JSObject().put("data", value));
        } catch (Exception e) {
            call.reject(e.getLocalizedMessage());
        }
    }
}
