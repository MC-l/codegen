package com.mcl.tool.codegen.core.maper;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @auth caiguowei
 * @date 2020/4/21
 */
@Data
public class MapperXmlBean {

    public static final String PLACE_MAPPER_PACKAGE = "[mapperPackage]";
    public static final String PLACE_MAPPER_CLASS = "[mapperClass]";
    public static final String PLACE_ENTITY_PACKAGE = "[entityPackage]";
    public static final String PLACE_ENTITY = "[entity]";
    public static final String PLACE_COLUMN = "[column]";
    public static final String PLACE_JDBCTYPE = "[jdbcType]";
    public static final String PLACE_PROPERTY = "[property]";

    private String  mapperPackage;
    private String mapperClass;
    private String entityPackage;
    private String entity;

    private List<ResultTag> resultTags;

    /**
     * <result> 标签
     */
    @Data
    public static class ResultTag{
        private String column;
        private String jdbcType;
        private String property;
    }
}
