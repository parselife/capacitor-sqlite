package cn.stormend.capacitor.plugins.sqlite.support;

import static cn.stormend.capacitor.plugins.sqlite.support.SQLiteTypeUtil.TYPE_BYTE;
import static cn.stormend.capacitor.plugins.sqlite.support.SQLiteTypeUtil.TYPE_DOUBLE;
import static cn.stormend.capacitor.plugins.sqlite.support.SQLiteTypeUtil.TYPE_FLOAT;
import static cn.stormend.capacitor.plugins.sqlite.support.SQLiteTypeUtil.TYPE_INT;
import static cn.stormend.capacitor.plugins.sqlite.support.SQLiteTypeUtil.TYPE_LONG;
import static cn.stormend.capacitor.plugins.sqlite.support.SQLiteTypeUtil.TYPE_STRING;
import static cn.stormend.capacitor.plugins.sqlite.support.SQLiteTypeUtil.determineType;

import android.content.ContentValues;
import android.util.Log;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 保存数据的载体
 */
public class SQLiteEntityDTO extends AbstractSQLiteDTO {

    /**
     * 数据列
     */
    private List<SQLiteEntityColumn> columns;


    /**
     * 转换为 dto
     *
     * @param call
     * @return
     */
    public static SQLiteEntityDTO from(PluginCall call) {
        JSArray array = call.getArray("columns");
        SQLiteEntityDTO dto = new SQLiteEntityDTO(call.getString("table"));
        List<SQLiteEntityColumn> entityCols = Collections.emptyList();
        try {
            if (array != null) {
                entityCols = new ArrayList<>(array.length());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    entityCols.add(new SQLiteEntityColumn().setName(object.getString("name"))
                            .setNullable(object.getBoolean("nullable"))
                            .setValue(object.get("value")));
                }
            }
        } catch (JSONException e) {
            Log.e("entity", e.getLocalizedMessage());
            e.printStackTrace();
        }
        if (entityCols.size() == 0) {
            return null;
        }
        return dto.setColumns(entityCols);

    }

    /**
     * 转为 content values 以保存到 sqlite
     *
     * @return
     */
    public ContentValues convert() {
        ContentValues cv = new ContentValues(getColumns().size());
        for (SQLiteEntityColumn column : getColumns()) {
            Object value = column.getValue();
            if (value != null) {
                int type = determineType(value.getClass());
                switch (type) {
                    case TYPE_STRING:
                        cv.put(column.getName(), value.toString());
                        break;
                    case TYPE_INT:
                        cv.put(column.getName(), Integer.parseInt(value.toString()));
                        break;
                    case TYPE_FLOAT:
                        cv.put(column.getName(), Float.parseFloat(value.toString()));
                        break;
                    case TYPE_BYTE:
                        cv.put(column.getName(), Byte.valueOf(value.toString()));
                        break;
                    case TYPE_DOUBLE:
                        cv.put(column.getName(), Double.parseDouble(value.toString()));
                        break;
                    case TYPE_LONG:
                        cv.put(column.getName(), Long.parseLong(value.toString()));
                        break;
                    default:
                        break;
                }
            } else {
                cv.putNull(column.getName());
            }
        }
        return cv;
    }

    /**
     * 返回可以为null的列
     * eg: A,B,C
     *
     * @return
     */
    public String nullColumnHack() {
        StringBuilder sb = new StringBuilder();
        for (SQLiteEntityColumn column : getColumns()) {
            if (column.isNullable()) {
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append(column.getName());
            }
        }
        return sb.length() > 0 ? sb.toString() : null;
    }

    protected SQLiteEntityDTO(String tblName) {
        super(tblName);
    }


    public List<SQLiteEntityColumn> getColumns() {
        return columns == null ? Collections.emptyList() : columns;
    }

    public SQLiteEntityDTO setColumns(List<SQLiteEntityColumn> columns) {
        this.columns = columns;
        return this;
    }
}
