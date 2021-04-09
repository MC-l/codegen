package com.mcl.tool.codegen.common.util.comment;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * java 文件解析器
 * @auth caiguowei
 * @date 2021/1/11
 */
public class JavaFileAnalyzer {

    public static void main(String[] args) throws Exception {
        getAllMethods();
    }

    public static void getAllMethods() throws Exception {
        String path = "C:\\Users\\j\\IdeaProjects\\codegen\\src\\main\\java\\com\\mcl\\tool\\codegen\\common\\util\\comment\\Test.java";
        int run = ToolProvider.getSystemDocumentationTool().run(new FileInputStream(new File(path)), null, null, "-d doc");
    }
}
