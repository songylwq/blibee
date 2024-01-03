package org.jeecg.modules.nethospital;

import cn.hutool.core.io.FileUtil;
import java.io.File;
import java.util.List;

/**
 * TODO
 *
 * @author abc
 * @version 1.0
 * @date 2023/12/27 9:37
 */
public class FileName {
    public static void main(String[] args) throws Exception{
        // 指定目录
        String directoryPath = "C:\\Users\\Administrator\\Desktop\\新建文件夹 (2)\\漫聊五胡十六国";
        // 指定的字符串
        String specifiedString = "yourString";

        // 遍历指定目录下的所有文件
        List<File> files = FileUtil.loopFiles(directoryPath);
        for (File file : files) {
            // 获取原文件名（不包含扩展名）
            String oldName = FileUtil.mainName(file);
            System.out.println("开始处理：" + oldName);
            String newName = oldName.replaceAll("《漫聊五胡十六國》", "").trim();

            System.out.println("新名字：" + newName);

            // 重命名文件（保留原扩展名）
            FileUtil.rename(file, newName, true, false);
        }
    }
}
