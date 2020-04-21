package com.mcl.tool.codegen.core.controller;

import com.mcl.tool.codegen.common.util.FileUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author MCl
 * @Date 2018-11-27 12:18
 */
public class ControllerGenerator {

    private static final String controllerTemplate = FileUtil.readFileAsOneLine("/Users/mcl/IdeaProjects/yiwei-union/src/main/java/com/yiwei/union/common/util/codegenerator/controller/ControllerGenTemplate.txt");
    private static final String addEntityReqTemplate = FileUtil.readFileAsOneLine("/Users/mcl/IdeaProjects/yiwei-union/src/main/java/com/yiwei/union/common/util/codegenerator/controller/AddEntityReqTemplate.txt");
    
    private static void gen(Class<?> service,String entityDesc,String controllerBasePath,boolean genReq){

        String servicePackage = service.getPackage().getName();
        String subPackage = servicePackage.substring(servicePackage.lastIndexOf(".")+1);
        
        String ServiceClass = service.getSimpleName();
        String serviceClass = String.valueOf(ServiceClass.charAt(0)).toLowerCase()+ServiceClass.substring(1);
        String controllerPackage = servicePackage.replace("service","controller");
        String controllerName = ServiceClass.replace("Service","Controller");
        String Entity = ServiceClass.replace("Service", "");
        String entity = String.valueOf(Entity.charAt(0)).toLowerCase()+Entity.substring(1);
        String importLines = servicePackage.replace("service","entity")+"."+Entity;
        String importReqClassPath = controllerPackage+".req.Add"+Entity+"Req";
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());


        Map<String, String> replaceMap = new HashMap<>(10);
        replaceMap.put("[package]",controllerPackage);
        replaceMap.put("[importLines]",importLines);
        replaceMap.put("[servicePackage]",servicePackage+"."+ServiceClass);
        replaceMap.put("[importReqClassPath]",importReqClassPath);
        replaceMap.put("[date]",date);
        replaceMap.put("[Entity]",Entity);
        replaceMap.put("[entity]",entity);
        replaceMap.put("[EntityDesc]",entityDesc);
        replaceMap.put("[ServiceClass]",ServiceClass);
        replaceMap.put("[serviceClass]",serviceClass);
        String controller = FileUtil.replaceText(controllerTemplate, replaceMap);
        System.out.println(controller);
        
        String controllerPath = controllerBasePath+"/"+subPackage+"/"+controllerName+".java";

        FileUtil.writeLinesAsFile(controller,controllerPath);


        if (genReq){

            String addEntityReqName = "Add"+Entity+"Req.java";
            String addEntityReq = FileUtil.replaceText(addEntityReqTemplate, replaceMap);
            String reqPath = controllerBasePath+"/"+subPackage+"/req/"+addEntityReqName;
            FileUtil.writeLinesAsFile(addEntityReq,reqPath);
        }
    }

    /**
     * 生成控制器，生成Req
     * @param service
     * @param entityDesc
     * @param controllerBasePath
     */
    public static void genWithReq(Class<?> service,String entityDesc,String controllerBasePath){
        gen(service, entityDesc, controllerBasePath, true);
    }

    /**
     * 生成控制器，不生成Req
     * @param service
     * @param entityDesc
     * @param controllerBasePath
     */
    public static void genWithoutReq(Class<?> service,String entityDesc,String controllerBasePath){
        gen(service, entityDesc, controllerBasePath, false);
    }
    
}
