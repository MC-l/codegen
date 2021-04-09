package com.mcl.tool.codegen.core.entity;

import com.google.common.collect.Lists;
import com.mcl.tool.codegen.common.util.DBUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author MCl
 * @Date 2019-05-08 22:27
 */
@Slf4j
public class EntityGenUtil {

    public static void main(String[] args) throws Exception {
        Connection conn = DBUtil.getConnection();
        String tablePrefix = "t_";

        String dbName = "qixing";
        List<String> tableNames = Arrays.asList("t_memory","t_memory_node");
        String packageName = "com.blsx.usercenter.entity";

//        String dbName = "blsx_web";
//        List<String> tableNames = Arrays.asList("t_msg_status");
//        String packageName = "com.blsx.usercenter.entity";

        List<Table> tables = getTables(conn, dbName, tableNames);

        if (tables == null || tables.size() == 0){
            System.out.println("数据库【"+dbName+"】中没有表");
        }

        for (Table table : tables) {
            List<RowDetail> rowDetails = getRowDetail(conn,dbName,table.getTableName());

            StringBuilder sb = new StringBuilder();
            TemUtil.build(sb,table,tablePrefix,packageName,rowDetails);

            System.out.println(sb.toString());

            // 生成文件 TODO
        }
    }

    public static List<Table> getTables(Connection conn,String dbName, List<String> tableNames) throws Exception {

        StringBuilder sql = new StringBuilder("select table_name,table_comment from TABLES where table_schema = ");

        sql.append("'"+dbName+"'");

        if (tableNames != null && tableNames.size() > 0){
            sql.append("and table_name in (");

            for (String tableName : tableNames) {
                sql.append("'"+tableName+"',");
            }
            int i = sql.lastIndexOf(",");
            sql.replace(i,i+1,"");
            sql.append(")");
        }


        PreparedStatement statement = DBUtil.getPreparedStatement(conn, sql.toString());
        ResultSet resultSet = statement.executeQuery();
        List<Table> tables = new ArrayList<>();

        while(resultSet.next()){
            String table_name = resultSet.getString("table_name");
            String table_comment = resultSet.getString("table_comment");
            tables.add(new Table(table_name,table_comment));
        }

        return tables;
    }

    public static final List<RowDetail> getRowDetail(Connection conn,String dbName,String tableName) throws Exception {

        String sql = "select column_name,ordinal_position,column_default,is_nullable,data_type,character_maximum_length,column_type,column_key,column_comment from columns " +
                "where table_schema = '"+dbName+"' and table_name = '"+tableName+"'";

        PreparedStatement statement = DBUtil.getPreparedStatement(conn, sql);
        ResultSet resultSet = statement.executeQuery();

        List<RowDetail> rowDetails = Lists.newArrayList();

        while(resultSet.next()){
            String column_name = resultSet.getString("column_name");
            String ordinal_position = resultSet.getString("ordinal_position");
            String column_default = resultSet.getString("column_default");
            String is_nullable = resultSet.getString("is_nullable");
            String data_type = resultSet.getString("data_type");
            String character_maximum_length = resultSet.getString("character_maximum_length");
            String column_type = resultSet.getString("column_type");
            String column_key = resultSet.getString("column_key");
            String column_comment = resultSet.getString("column_comment");

            RowDetail rowDetail = new RowDetail(
                    TemUtil.colNameToPropertyName(column_name),
                    TemUtil.colNameToPropertyName(ordinal_position),
                    TemUtil.colNameToPropertyName(column_default),
                    TemUtil.colNameToPropertyName(is_nullable),
                    TemUtil.colNameToPropertyName(data_type),
                    TemUtil.colNameToPropertyName(character_maximum_length),
                    TemUtil.colNameToPropertyName(column_type),
                    TemUtil.colNameToPropertyName(column_key),
                    TemUtil.colNameToPropertyName(column_comment)
                    );
            rowDetails.add(rowDetail);
        }

        return rowDetails;
    }
}
