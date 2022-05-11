package cn.stormend.capacitor.plugins.sqlite.support;

import java.io.Serializable;

public abstract class AbstractSQLiteDTO implements Serializable {

    protected final String tblName;

    protected AbstractSQLiteDTO(String tblName) {
        this.tblName = tblName;
    }

    public String getTblName() {
        return tblName;
    }
}
