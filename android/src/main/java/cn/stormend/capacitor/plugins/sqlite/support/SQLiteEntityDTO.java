package cn.stormend.capacitor.plugins.sqlite.support;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 保存数据的载体
 */
public class SQLiteEntityDTO extends AbstractSQLiteDTO {

    /**
     * 数据列
     */
    private List<SQLiteEntityColumn> columns;



    /**
     * 转为 content values 以保存到 sqlite
     *
     * @return
     */
    public ContentValues convert() {
        ContentValues values = new ContentValues(getColumns().size());
        for (SQLiteEntityColumn column : getColumns()) {


            values.put(column.getName(), column.getValue());
        }

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
                if (sb.length()>0) {
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
