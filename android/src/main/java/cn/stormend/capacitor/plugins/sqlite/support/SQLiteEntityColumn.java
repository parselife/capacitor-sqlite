package cn.stormend.capacitor.plugins.sqlite.support;

public class SQLiteEntityColumn {

    /**
     * 字段名称
     */
    private String name;

    /**
     * 字段值
     */
    private Object value;

    /**
     * 是否允许为null 默认 false
     */
    private boolean nullable;

    public String getName() {
        return name;
    }

    public SQLiteEntityColumn setName(String name) {
        this.name = name;
        return this;
    }

    public Object getValue() {
        return value;
    }

    public SQLiteEntityColumn setValue(Object value) {
        this.value = value;
        return this;
    }

    public boolean isNullable() {
        return nullable;
    }

    public SQLiteEntityColumn setNullable(boolean nullable) {
        this.nullable = nullable;
        return this;
    }
}
