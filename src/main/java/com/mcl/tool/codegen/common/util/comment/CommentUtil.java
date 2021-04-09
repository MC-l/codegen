package com.mcl.tool.codegen.common.util.comment;

import com.mcl.tool.codegen.common.util.FileUtil;

import java.util.List;
import java.util.regex.Pattern;

/**
 * 注释提取器
 * @auth caiguowei
 * @date 2021/1/11
 */
public class CommentUtil {
    public static void main(String[] args) {
        String path = "C:\\Users\\j\\IdeaProjects\\codegen\\src\\main\\java\\com\\mcl\\tool\\codegen\\common\\util\\comment\\Test.java";
        List<String> strings = FileUtil.readFileAsLineList(path);
        System.out.println(strings);
        Pattern p = Pattern.compile("NOTE:");
        strings.forEach(str -> {
            if (p.matcher(str).find()) {
                System.out.println(str);
            }
        });
    }
}
