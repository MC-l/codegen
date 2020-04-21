package com.mcl.tool.codegen.core.service;


import com.mcl.tool.codegen.common.util.FileUtil;
import org.springframework.util.StringUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controller 代码自动生成
 * @Author MCl
 * @Date 2018-11-17 03:08
 */
public final class ServiceGenerator {

    private static final String serviceImplTemplate = FileUtil.readFileAsOneLine("/Users/mcl/IdeaProjects/yiwei-union/src/main/java/com/yiwei/union/common/util/codegenerator/service/ServiceGenTemplate.txt");

    /**
     * 生成ServiceInterface文件
     * @param mapperClass
     * @param mapperPath
     * @param servicePackage
     */
    public static void genInterface(Class<?> mapperClass, String mapperPath, String servicePackage){

        String mapperPackage = mapperClass.getPackage().getName();
        String simpleName = mapperClass.getSimpleName();
        String serviceName = simpleName.replace("Mapper","Service");
        String mapperFileText = FileUtil.readFileAsOneLine(mapperPath);
        String serviceFileText = mapperFileText
                .replace(mapperPackage, servicePackage)
                .replace(simpleName, serviceName);

        String mapperPackagePath = mapperPackage.replace(".","/");
        String absPathNotPackagePath = mapperPath.replace(mapperPackagePath+"/"+simpleName+".java","");
        String servicePackagePath = servicePackage.replace(".","/");
        String serviceAbsPath = absPathNotPackagePath+servicePackagePath;
        FileUtil.writeLinesAsFile(serviceFileText,serviceAbsPath+"/"+serviceName+".java");

        System.out.println(serviceFileText);
    }

    /**
     * 生成ServiceImpl文件
     * @param mapperClass
     * @param mapperPath
     * @param servicePackage
     */
    public static void genInterfaceImpl(Class<?> mapperClass, String mapperPath, String servicePackage){

        String mapperPackage = mapperClass.getPackage().getName();
        String simpleName = mapperClass.getSimpleName();
        String serviceName = simpleName.replace("Mapper","Service");
        String entiryName = simpleName.replace("Mapper","");

        List<String> codeLines = FileUtil.readFileAsLineList(mapperPath);
        String importLines = codeLines.stream().filter(line -> line.startsWith("import")).collect(Collectors.joining("\n"));

        importLines = importLines.replace("import org.apache.ibatis.annotations.Param;","")
                .replace("import "+servicePackage+"."+serviceName,"");

        String template = serviceImplTemplate;
        Map<String, String> replaceMap = new HashMap<>(6);
        replaceMap.put("[date]",new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        replaceMap.put("[importLines]",importLines);
        replaceMap.put("[servicePackage]",servicePackage);
        replaceMap.put("[mapperPackage]",mapperPackage);
        replaceMap.put("[ServiceTemplate]",serviceName);
        replaceMap.put("[MapperClass]",simpleName);
        replaceMap.put("[mapperClass]",simpleName.substring(0,1).toLowerCase() + simpleName.substring(1));
        replaceMap.put("[Entity]",entiryName);
        String serviceImpl = FileUtil.replaceText(template, replaceMap);


        String mapperPackagePath = mapperPackage.replace(".","/");
        String absPathNotPackagePath = mapperPath.replace(mapperPackagePath+"/"+simpleName+".java","");
        String servicePackagePath = servicePackage.replace(".","/");
        String serviceAbsPath = absPathNotPackagePath+servicePackagePath;
        FileUtil.writeLinesAsFile(serviceImpl,serviceAbsPath+"/impl/"+serviceName+"Impl.java");


        System.out.println(serviceImpl);
    }

    /**
     * 一键生成service 接口 和 实现类
     * @param daoAbsPath /Users/mcl/IdeaProjects/yiwei-basic/src/main/java/com/yiwei/basic/dao/
     * @param serviceBasePackage com.yiwei.basic.service
     * @param subPackage com.yiwei.basic.service.[subPackage]
     * @param mapperClass UserMapper.class
     */
    public static void genService(
            String daoAbsPath,
            String serviceBasePackage,
            String subPackage,
            Class<?> mapperClass){

        String sp = "/";

        if (!daoAbsPath.endsWith(sp)) {
            daoAbsPath = daoAbsPath+sp;
        }

        String daoReplacement = "";
        String daoReplacementImpl = "";
        if (!StringUtils.isEmpty(subPackage)) {
            serviceBasePackage = serviceBasePackage+"."+subPackage;
            daoReplacement = subPackage.replace(".",sp)+sp;
            daoReplacementImpl = subPackage+sp;
        }

        ServiceGenerator.genInterface(mapperClass,
                daoAbsPath+daoReplacement+mapperClass.getSimpleName()+".java",
                serviceBasePackage
        );

        ServiceGenerator.genInterfaceImpl(mapperClass,
                daoAbsPath+daoReplacementImpl+mapperClass.getSimpleName()+".java",
                serviceBasePackage
        );
    }



}
