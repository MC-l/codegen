package com.mcl.tool.codegen.core.maper;

import java.util.ArrayList;
import java.util.List;

/**
 * @auth caiguowei
 * @date 2020/4/21
 */
public class MapperGenUtil {

    public static void main(String[] args) {
        genXml();
    }

    public static void genXml() {
        MapperXmlBean xmlBean = new MapperXmlBean();
        xmlBean.setMapperClass("UserMapper");
        xmlBean.setMapperPackage("com.mcl.hr.mapper");
        xmlBean.setEntity("User");
        xmlBean.setEntityPackage("com.mcl.hr.entity");
        List<MapperXmlBean.ResultTag> resultTags = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            MapperXmlBean.ResultTag resultTag = new MapperXmlBean.ResultTag();
            resultTag.setColumn("id"+i);
            resultTag.setJdbcType("bigint");
            resultTag.setProperty("id"+i);
            resultTags.add(resultTag);
        }

        xmlBean.setResultTags(resultTags);
        System.out.println(MapperXmlGenerator.genXml(xmlBean));
    }
}
