package cn.stormend.capacitor.plugins.sqlite.support;


import android.util.Log;

import com.getcapacitor.JSArray;
import com.getcapacitor.PluginCall;

import org.json.JSONException;

import java.util.List;

/**
 * sqlite 查询 dto
 */
public class SQLiteQueryDTO extends AbstractSQLiteDTO {


    /**
     * 查询条件 eg: a=? and b like '%?'
     */
    private String selection;

    /**
     * 查询的参数 替换 selection 中的 ?
     */
    private List<String> selectionArgs;

    /**
     * 返回的列
     */
    private List<String> returnColumns;

    /**
     * groupby 语句
     * eg: group by A
     */
    private String groupBy;

    /**
     * having 语句
     * eg: Having...
     */
    private String having;

    /**
     * order by 语句
     * eg: order by A
     */
    private String orderBy;

    /**
     * limit 语句
     * eg: LIMIT 10
     */
    private String limit;

    /**
     * 装配成 DTO
     *
     * @param call
     * @return
     */
    public static SQLiteQueryDTO from(PluginCall call) {
        SQLiteQueryDTO dto = new SQLiteQueryDTO(call.getString("table"));
        JSArray columns = call.getArray("returnColumns");
        JSArray selectionArgs = call.getArray("selectionArgs");
        try {
            if (columns != null) {
                dto.setReturnColumns(columns.toList());
            }
            if (selectionArgs != null) {
                dto.setSelectionArgs(selectionArgs.toList());
            }
        } catch (JSONException e) {
            Log.e("QUERY", e.getLocalizedMessage());
            e.printStackTrace();
        }
        dto.setSelection(call.getString("selection"))
                .setGroupBy(call.getString("groupBy"))
                .setHaving(call.getString("having"))
                .setOrderBy(call.getString("orderBy"))
                .setLimit(call.getString("limit"));
        return dto;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public SQLiteQueryDTO setOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public String getHaving() {
        return having;
    }

    public SQLiteQueryDTO setHaving(String having) {
        this.having = having;
        return this;
    }

    public String getLimit() {
        return limit;
    }

    public SQLiteQueryDTO setLimit(String limit) {
        this.limit = limit;
        return this;
    }

    public String getGroupBy() {
        return groupBy;
    }

    public SQLiteQueryDTO setGroupBy(String groupBy) {
        this.groupBy = groupBy;
        return this;
    }

    public String getSelection() {
        return selection;
    }

    public SQLiteQueryDTO setSelection(String selection) {
        this.selection = selection;
        return this;
    }

    public List<String> getSelectionArgs() {
        return selectionArgs;
    }

    public List<String> getReturnColumns() {
        return returnColumns;
    }

    public SQLiteQueryDTO setReturnColumns(List<String> returnColumns) {
        this.returnColumns = returnColumns;
        return this;
    }

    public SQLiteQueryDTO setSelectionArgs(List<String> selectionArgs) {
        this.selectionArgs = selectionArgs;
        return this;
    }

    protected SQLiteQueryDTO(String tblName) {
        super(tblName);
    }
}
