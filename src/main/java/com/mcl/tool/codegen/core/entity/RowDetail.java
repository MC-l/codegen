package com.mcl.tool.codegen.core.entity;

/**
 * @Author MCl
 * @Date 2019-05-08 23:34
 */
public class RowDetail {

    private String columnName;
    private String ordinalPosition;
    private String columnDefault;
    private String isNullable;
    private String dataType;
    private String characterMaximumLength;
    private String columnType;
    private String columnKey;
    private String columnComment;

    public RowDetail(String columnName, String ordinalPosition,
                     String columnDefault, String isNullable,
                     String dataType, String characterMaximumLength,
                     String columnType, String columnKey,
                     String columnComment) {

        this.columnName = columnName;
        this.ordinalPosition = ordinalPosition;
        this.columnDefault = columnDefault;
        this.isNullable = isNullable;
        this.dataType = dataType;
        this.characterMaximumLength = characterMaximumLength;
        this.columnType = columnType;
        this.columnKey = columnKey;
        this.columnComment = columnComment;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getOrdinalPosition() {
        return ordinalPosition;
    }

    public String getColumnDefault() {
        return columnDefault;
    }

    public String getIsNullable() {
        return isNullable;
    }

    public String getDataType() {
        return dataType;
    }

    public String getCharacterMaximumLength() {
        return characterMaximumLength;
    }

    public String getColumnType() {
        return columnType;
    }

    public String getColumnKey() {
        return columnKey;
    }

    public String getColumnComment() {
        return columnComment;
    }
}
