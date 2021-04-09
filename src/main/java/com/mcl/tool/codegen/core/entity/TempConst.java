package com.mcl.tool.codegen.core.entity;

/**
 * 模板常量
 * @Author MCl
 * @Date 2019-05-09 01:11
 */
public final class TempConst {

    public static final String setMethodTemp = "\tpublic void set[setterName]([dataType] [columnName]){\n" +
            "\t\tthis.[columnName] = [columnName];\n" +
            "\t}";

    public static final String getMethodTemp = "\tpublic [dataType] get[getterName](){\n" +
            "\t\treturn this.[columnName];\n" +
            "\t}";

    public static final String entityCommentTemp =
            "/**\n" +
            " * [comment]\n" +
            " * 数据库表：[tableName]\n" +
            " * [createdDate]\n" +
            " */";


    public static final String classLine = "@Data\n@TableName(\"[tableName]\")\npublic class [className]";


}
