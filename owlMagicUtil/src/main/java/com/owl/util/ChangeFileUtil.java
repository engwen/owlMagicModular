package com.owl.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangeFileUtil {
    public static void main(String[] args) {
//        System.out.println(getName("【高清MP4电影www.boxmp4.com】加勒比海盗1：黑珍珠号的诅咒.Pirates of the Caribbean： The Curse of the Black Pearl.2003.BD720P.国英双语.中英双字.mp4"));
//        ChangeFileUtil.movie();
        ChangeFileUtil.music();
    }

    public static void music() {
        String dir = "/home/engwen/音乐/";
        List<File> files = FileUtil.getFilePath(new File(dir));
        List<String> temp = new ArrayList<>();
        files.forEach(it -> {
            if (temp.contains(it.getName())) {
                System.out.println("音乐重复：" + it.getName() + "  " + it.getAbsolutePath());
            } else {
                temp.add(it.getName());
            }
        });
    }


    public static void movie() {
        List<String> dirList = new ArrayList<>();
        dirList.add("/mnt/sd2/视频/");
        dirList.add("/mnt/sd2/动漫电影/");
        dirList.forEach(dirPath -> {
            List<File> fileList = FileUtil.getFilePath(new File(dirPath));
            String regex = "\\.(mp4|mkv|rmvb)";
            for (File file : fileList) {
                File newFile = new File(dirPath + ChangeFileUtil.getName(file.getName()));
                String oldName = file.getName().toLowerCase();
                if (oldName.contains(".torrent") || oldName.contains(".js")) {
                    file.delete();
                    continue;
                }
                if (oldName.contains(".nfo") || oldName.contains(".jpg") || oldName.contains("png")) {
//                    file.delete();
                    continue;
                }
                if (file.getAbsoluteFile().equals(newFile.getAbsoluteFile())) {
                    continue;
                } else if (newFile.getName().replaceAll(regex, "").equals("")) {
                    continue;
                }
                if (newFile.exists()) {
                    System.out.println("文件已存在" + newFile.getName());
                    continue;
                }
                System.out.println("old name is " + file.getName());
                System.out.println("new name is " + newFile.getName());
                if (!file.renameTo(newFile)) {
                    System.out.println("rename is error");
                }
            }
        });

        ChangeFileUtil.removeDir();
    }


    public static String getName(String oldName) {
        return oldName.toLowerCase()
                .replaceAll("tctyjxl1", "铁齿铜牙纪晓岚第一季")
                .replaceAll("爱情公y", "爱情公寓")
                .replaceAll("阳光电影www\\.ygdy8\\.com\\.", "")
                .replaceAll("\\[.*\\]", "")
                .replaceAll("【.*】", "")
                .replaceAll("\\.?1080p\\.?", "")
                .replaceAll("\\.?720p\\.?", "")
                .replaceAll("_蓝光.*\\.+mp4", "\\.mp4")
                .replaceAll("_蓝光.*\\.+mkv", "\\.mkv")
                .replaceAll("_蓝光.*\\.+rmvb", "\\.rmvb")
                .replaceAll("\\.?(bd|hd).*\\.+rmvb", "\\.rmvb")
                .replaceAll("\\.?(bd|hd).*\\.+mkv", "\\.mkv")
                .replaceAll("\\.?(bd|hd).*\\.+mp4", "\\.mp4")
                .replaceAll("_\\.mp4", "\\.mp4")
                .replaceAll("_\\.mkv", "\\.mkv")
                .replaceAll("_\\.rmvb", "\\.rmvb")
//                .replaceAll("\\[GM-Team\\]\\[国漫\\]\\[一人之下 第3季\\]\\[The Outcast 3rd Season\\]","一人之下")
//                .replaceAll("\\[","").replaceAll("\\]"," ")
                .replaceAll("迅雷.*\\.mp4", ".mp4");
    }


    public static void removeDir() {
        List<String> dirList = new ArrayList<>();
        dirList.add("/mnt/sd2/视频/");
        dirList.add("/mnt/sd2/动漫电影/");
        dirList.forEach(file -> {
            List<File> filePath = FileUtil.getFileDirPath(new File(file));
            filePath.forEach(it -> {
                if (it.isDirectory()) {
                    if (FileUtil.getFilePath(it).size() == 0) {
                        it.delete();
                        System.out.println("目录删除：" + it.getAbsolutePath());
                    } else {
                        System.out.println("包含文件，跳過刪除：" + it.getAbsolutePath());
                    }
                }
            });
        });

    }
}
