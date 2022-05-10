package cn.stormend.capacitor.plugins.sqlite.support;

import java.util.List;

/**
 * 实体更新对象
 *
 */
public class SQLiteEntityUpdateDTO  extends SQLiteEntityDTO{
    protected SQLiteEntityUpdateDTO(String tblName) {
        super(tblName);
    }

    /**
     * where 条件 eg: a=? and b=?
     */
    private String whereClause;

    /**
     * where 条件参数
     */
    private List<String> whereClauseArgs;


    public String getWhereClause() {
        return whereClause;
    }

    public SQLiteEntityUpdateDTO setWhereClause(String whereClause) {
        this.whereClause = whereClause;
        return this;
    }

    public List<String> getWhereClauseArgs() {
        return whereClauseArgs;
    }

    public SQLiteEntityUpdateDTO setWhereClauseArgs(List<String> whereClauseArgs) {
        this.whereClauseArgs = whereClauseArgs;
        return this;
    }
}
