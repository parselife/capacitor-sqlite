package cn.stormend.capacitor.plugins.sqlite;

import static android.database.Cursor.FIELD_TYPE_FLOAT;
import static android.database.Cursor.FIELD_TYPE_INTEGER;
import static android.database.Cursor.FIELD_TYPE_NULL;
import static android.database.Cursor.FIELD_TYPE_STRING;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginConfig;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;

import cn.stormend.capacitor.plugins.sqlite.support.SQLiteEntityDTO;
import cn.stormend.capacitor.plugins.sqlite.support.SQLiteEntityUpdateDTO;
import cn.stormend.capacitor.plugins.sqlite.support.SQLiteQueryDTO;

public class CapacitorSqlite {

    private static final String TAG = CapacitorSqlite.class.getName();
    private final Context context;
    private final PluginConfig config;

    /**
     * 避免多次打开数据库 内存中缓存
     * 读写分离
     */
    private static final Map<String, SQLiteDatabase> dbRegistry = new HashMap<>(1);

    public static final int DB_VERSION = 1;

    public CapacitorSqlite(Context context, PluginConfig config) {
        this.context = context;
        this.config = config;
    }

    /**
     * 插件测试方法
     *
     * @param value
     * @return
     */
    public String echo(String value) {
        return value;
    }

    /**
     * 加载sqlite db 文件
     *
     * @param dbFile 数据文件路径
     * @return
     */
    public boolean loadSQLiteDatabase(String dbFile) {
        if (!dbRegistry.containsKey(dbFile)) {
            CapSQLiteHelper sqLiteOpenHelper = new CapSQLiteHelper(context, dbFile, null, DB_VERSION);
            SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
            dbRegistry.put(dbFile, db);
        } else {
            Log.i(TAG, String.format("数据库文件[%s]已经加载", dbFile));
        }
        return true;
    }

    /**
     * 查询列表
     *
     * @param dto
     * @return
     */
    public JSArray queryForList(SQLiteQueryDTO dto) {
        SQLiteDatabase database = getNextDatabase();
        JSArray array = new JSArray();
        if (database == null) {
            return array;
        }
        Cursor cursor = database.query(dto.getTblName(), dto.getReturnColumns().toArray(new String[0]), dto.getSelection(),
                dto.getSelectionArgs().toArray(new String[0]), dto.getGroupBy(), dto.getHaving(), dto.getOrderBy(), dto.getLimit());
        while (!cursor.isAfterLast()) {
            cursor.moveToNext();
            array.put(cursorToObject(cursor));
        }
        return array;
    }

    /**
     * 查询单个对象
     *
     * @param dto
     * @return
     */
    public JSObject queryForObject(SQLiteQueryDTO dto) {
        SQLiteDatabase database = getNextDatabase();
        JSObject object = new JSObject();
        if (database == null) {
            return object;
        }
        Cursor cursor = database.query(dto.getTblName(), dto.getReturnColumns().toArray(new String[0]), dto.getSelection(),
                dto.getSelectionArgs().toArray(new String[0]), dto.getGroupBy(), dto.getHaving(), dto.getOrderBy(), dto.getLimit());
        cursor.moveToNext();
        return cursorToObject(cursor);
    }

    /**
     * 保存数据
     * insert or replace entity
     *
     * @param entityDTO
     * @return
     */
    public boolean saveEntity(SQLiteEntityDTO entityDTO) {
        SQLiteDatabase db = getNextDatabase();
        if (db != null) {
            long rowId = db.replaceOrThrow(entityDTO.getTblName(), entityDTO.nullColumnHack(),
                    entityDTO.convert());
            return rowId > 0;
        }
        Log.e(TAG, "数据库为null");
        return false;
    }

    /**
     * 更新数据
     *
     * @param updateDTO
     * @return
     */
    public boolean update(SQLiteEntityUpdateDTO updateDTO) {
        SQLiteDatabase db = getNextDatabase();
        if (db != null) {
            int affected = db.update(updateDTO.getTblName(), updateDTO.convert(), updateDTO.getWhereClause(),
                    updateDTO.getWhereClauseArgs().toArray(new String[0]));
            return affected > 0;
        }
        Log.e(TAG, "数据库为null");
        return false;
    }

    /**
     * 将数据库的cursor 转为一个json 对象
     *
     * @param cursor
     * @return
     */
    private JSObject cursorToObject(final Cursor cursor) {
        int columnCount = cursor.getColumnCount();
        JSObject object = new JSObject();
        for (int i = 0; i < columnCount; i++) {
            String columnName = cursor.getColumnName(i);
            int type = cursor.getType(i);
            switch (type) {
                case FIELD_TYPE_FLOAT:
                    object.put(columnName, cursor.getFloat(i));
                    break;
                case FIELD_TYPE_INTEGER:
                    object.put(columnName, cursor.getInt(i));
                    break;
                case FIELD_TYPE_STRING:
                    object.put(columnName, cursor.getString(i));
                    break;
                case FIELD_TYPE_NULL:
                default:
                    break;
            }
        }
        return object;
    }

    /**
     * FIXME:
     * 获取第一个db
     *
     * @return
     */
    private SQLiteDatabase getNextDatabase() {
        Iterator<SQLiteDatabase> iterator = dbRegistry.values().iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }
}
