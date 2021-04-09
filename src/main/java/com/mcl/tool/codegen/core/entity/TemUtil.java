package com.mcl.tool.codegen.core.entity;

import com.mcl.tool.codegen.common.util.PropertiesUtil;
import com.mcl.tool.codegen.core.ConfigConst;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 模板工具类
 * @Author MCl
 * @Date 2019-05-08 23:57
 */
public final class TemUtil {

    private static String javaUtilDate = "import java.util.Date;";

    private static Map<String,String> dataTypeMap = new HashMap<>();

    /**
     * 存储公共字段
     */
    private static Set<String> baseColums = new HashSet<>();

    static {
        dataTypeMap.put("BIT","Boolean");

        dataTypeMap.put("TINYINT","Integer");
        dataTypeMap.put("SMALLINT","Integer");
        dataTypeMap.put("MEDIUMINT","Integer");
        dataTypeMap.put("INT","Integer");
        dataTypeMap.put("INTEGER","Integer");

        dataTypeMap.put("BIGINT","Long");

        dataTypeMap.put("FLOAT","Float");
        dataTypeMap.put("DOUBLE","Double");
        dataTypeMap.put("DECIMAL","BigDecimal");

        dataTypeMap.put("DATE","Date");
        dataTypeMap.put("TIME","Date");
        dataTypeMap.put("YEAR","String");
        dataTypeMap.put("DATETIME","Date");
        dataTypeMap.put("TIMESTAMP","Date");

        dataTypeMap.put("CHAR","Char");
        dataTypeMap.put("VARCHAR","String");
        dataTypeMap.put("TINYBLOB","String");
        dataTypeMap.put("TINYTEXT","String");
        dataTypeMap.put("TEXT","String");
        dataTypeMap.put("MEDIUMTEXT","String");
        dataTypeMap.put("LONGTEXT","String");


    }

    public static final void build(StringBuilder sb,Table table,
                                   String tablePrefix,
                                   String packageName,
                                   List<RowDetail> rowDetails
                                   ){

        genPackage(sb,packageName);
        genImport(sb,rowDetails);
        sb.append("\n");
        genClassComment(sb,table);
        genClassLine(sb,table,tablePrefix);
        sb.append(" {\n");
        genPropertyDefined(sb,rowDetails);
        //genGetterAndSetter(sb,rowDetails); // 推荐使用 Lombok
        sb.append("}");
    }

    public static final void genClassComment(StringBuilder sb,Table table){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String createdDate = sdf.format(new Date());

        sb.append( TempConst.entityCommentTemp.replace("[comment]", table.getTableComment())
                .replace("[tableName]", table.getTableName())
                .replace("[createdDate]", createdDate)

        );
        sb.append("\n");
    }

    public static final void genClassLine(StringBuilder sb,Table table,String tablePrefix){

        String tableName = table.getTableName().replaceFirst(tablePrefix,"");


        String[] ns = tableName.split("_");
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < ns.length; i++) {
            temp.append(ns[i].substring(0, 1).toUpperCase() + ns[i].substring(1));
        }
        String className = temp.toString();

        String replace = TempConst.classLine.replace("[className]", className).replace("[tableName]",table.getTableName());
        sb.append(replace);

        if (PropertiesUtil.contains(ConfigConst.BASE_ENTITY)){
            String baseEntityFullName = PropertiesUtil.getVal(ConfigConst.BASE_ENTITY);
            String baseEntityName = baseEntityFullName.substring(baseEntityFullName.lastIndexOf(".")+1);
            sb.append(" extends " + baseEntityName);
        }
    }

    public static final void genPackage(StringBuilder sb,String packageName){
        sb.append("package ");
        sb.append(packageName);
        sb.append(";\n\n");
    }

    public static final void genImport(StringBuilder sb,List<RowDetail> rowDetails){
        sb.append("import lombok.Data;\n");
        sb.append("import com.baomidou.mybatisplus.annotation.TableName;\n");

        // 导入 BaseEntity
        if (PropertiesUtil.contains(ConfigConst.BASE_ENTITY)){
            String baseEntityFullName = PropertiesUtil.getVal(ConfigConst.BASE_ENTITY);
            sb.append("import "+baseEntityFullName+";\n");
            String baseEntityName = baseEntityFullName.substring(baseEntityFullName.lastIndexOf("."));
        }

        // 如果有 Date 类型字段，则导入 "import java.util.Date;"
        for (RowDetail rowDetail : rowDetails) {
            if ("Date".equals(dataTypeMap.get(rowDetail.getDataType().toUpperCase()))){
                sb.append(javaUtilDate);
                sb.append("\n\n");
                return;
            }
        }
    }


    /**
     * 生成get 和 set 方法
     * 推荐使用 lombok
     * @param sb
     * @param rowDetails
     */
    @Deprecated
    public static final void genGetterAndSetter(StringBuilder sb, List<RowDetail> rowDetails){
        for (RowDetail rowDetail : rowDetails) {

            String columnName = rowDetail.getColumnName();
            String dataType = dataTypeMap.get(rowDetail.getDataType().toUpperCase());

            String colNameUpCase = columnName.substring(0, 1).toUpperCase() + columnName.substring(1);


            String setMethod = TempConst.setMethodTemp.replace("[setterName]", colNameUpCase)
                    .replace("[dataType]", dataType)
                    .replace("[columnName]", columnName);

            String getMethod = TempConst.getMethodTemp.replace("[dataType]",dataType)
                    .replace("[getterName]",colNameUpCase)
                    .replace("[columnName]",columnName);

            sb.append(setMethod);
            sb.append("\n");
            sb.append(getMethod);

            sb.append(";\n\n");
        }
    }

    public static final void genPropertyDefined(StringBuilder sb, List<RowDetail> rowDetails){

        for (RowDetail rowDetail : rowDetails) {
            List<String> baseFields = PropertiesUtil.getVals(ConfigConst.BASE_ENTITY_FIELDS);

            // 如果是 BaseEntity 的公共字段，则无需生成代码
            if (baseFields.contains(rowDetail.getColumnName())){
                continue;
            }

            sb.append("\t/**\n");
            appendCommentLine(sb,rowDetail.getColumnComment());
            if (rowDetail.getCharacterMaximumLength() != null){
                appendCommentLine(sb,"maxLen:"+rowDetail.getCharacterMaximumLength());
            }
            if ("NO".equals(rowDetail.getIsNullable())){
                appendCommentLine(sb,"Not Null");
            }
            sb.append("\t **/\n");
            sb.append("\tprivate ");
            sb.append(dataTypeMap.get(rowDetail.getDataType().toUpperCase()));
            sb.append(" ");
            sb.append(rowDetail.getColumnName());
            sb.append(";\n\n");
        }
    }

    private static void appendCommentLine(StringBuilder sb, String comment){
        sb.append("\t * "+comment+"\n");
    }


    /**
     * 将表的列名（下划线）转换成实体类的属性名（驼峰）<br/>
     * 如：column_name 转换后 ： columnName
     *
     * @param columnName
     * @return
     */
    public static final String colNameToPropertyName(String columnName){
        if (columnName == null){
            return null;
        }
        String[] ss = columnName.split("_");
        String r = ss[0];
        for (int i = 1; i < ss.length; i++) {
            r += ss[i].substring(0,1).toUpperCase()+ss[i].substring(1);
        }
        return r;
    }


}
