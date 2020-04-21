package com.mcl.tool.codegen.core.entity;

/**
 * @Author MCl
 * @Date 2019-05-08 23:25
 */
public class Table {

    private String tableName;
    private String tableComment;

    public Table(String tableName, String tableComment) {
        this.tableName = tableName;
        this.tableComment = tableComment;
    }

    public String getTableName() {
        return tableName;
    }

    public String getTableComment() {
        return tableComment;
    }
}
