package com.example.github.model;

public class Tables {

    String tableName;
    String tableSize;
    String tableCount;

    public Tables(String tableName, String tableSize, String tableCount) {
        this.tableName = tableName;
        this.tableSize = tableSize;
        this.tableCount = tableCount;
    }

    public Tables() {
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableSize() {
        return tableSize;
    }

    public void setTableSize(String tableSize) {
        this.tableSize = tableSize;
    }

    public String getTableCount() {
        return tableCount;
    }

    public void setTableCount(String tableCount) {
        this.tableCount = tableCount;
    }
}
