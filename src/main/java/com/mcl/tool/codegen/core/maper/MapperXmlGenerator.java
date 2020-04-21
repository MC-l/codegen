package com.mcl.tool.codegen.core.maper;

import com.mcl.tool.codegen.common.util.FileUtil;
import java.util.List;

/**
 * Mapper文件生成
 * @auth caiguowei
 * @date 2020/4/21
 */
public class MapperXmlGenerator {

    static String xml1 = "/static/codetemplate/mapper/MapperXmlTemplate.txt";

    public static String genXml(MapperXmlBean xmlBean){
        String s = FileUtil.loadClassPathFile(xml1);
        s = genPlaceHolder(xmlBean, s);
        s = genResultTags(xmlBean, s);
        return s;
    }

    /**
     * 处理占位符
     * @param xmlBean
     * @param s
     * @return
     */
    private static String genPlaceHolder(MapperXmlBean xmlBean, String s) {
        return s.replace(MapperXmlBean.PLACE_MAPPER_PACKAGE,xmlBean.getMapperPackage())
                .replace(MapperXmlBean.PLACE_MAPPER_CLASS,xmlBean.getMapperClass())
                .replace(MapperXmlBean.PLACE_ENTITY_PACKAGE,xmlBean.getEntityPackage())
                .replace(MapperXmlBean.PLACE_ENTITY,xmlBean.getEntity());
    }

    private static String genResultTags(MapperXmlBean xmlBean, String s) {
        String resultTagTemp = getResultTagTemp(s);
        List<MapperXmlBean.ResultTag> resultTags = xmlBean.getResultTags();
        StringBuilder resultsFilled = new StringBuilder();

        for (int i = 0; i < resultTags.size(); i++) {
            String result = resultTagTemp
                    .replace(MapperXmlBean.PLACE_COLUMN,resultTags.get(i).getColumn())
                    .replace(MapperXmlBean.PLACE_JDBCTYPE,resultTags.get(i).getJdbcType())
                    .replace(MapperXmlBean.PLACE_PROPERTY,resultTags.get(i).getProperty());
            resultsFilled.append(result+"\n");
        }


        s = s.replace(resultTagTemp,resultsFilled.toString());
        return s;
    }

    /**
     * 获取<result>标签
     * @param xmlStr
     * @return
     */
    private static String getResultTagTemp(String xmlStr){
        String[] split = xmlStr.split("\n");
        for (int i = 0; i < split.length; i++) {
            String line = split[i];
            if (line.trim().startsWith("<result ")) {
                return line;
            }
        }
        return null;
    }
}
