package com.owl.util;

import com.spreada.utils.chinese.ZHConverter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class ChangeFileUtil {
    public static void main(String[] args) {
//        System.out.println(getName("【高清MP4电影www.boxmp4.com】加勒比海盗1：黑珍珠号的诅咒.Pirates of the Caribbean： The Curse of the Black Pearl.2003.BD720P.国英双语.中英双字.mp4"));
//        ChangeFileUtil.movie();
        ChangeFileUtil.music();
//        ChangeFileUtil.Ebook();
//        ChangeFileUtil.removeLittleNotMp4();
//        String dirPath = "/mnt/sd2/Ebook/名著合集(TXT)/";
    }

    public static void Ebook() {
        String dirPath = "/mnt/sd2/Ebook/名著合集(TXT)/";
//        ChangeFileUtil.removeRepeatEbook(dirPath);
        ChangeFileUtil.renameEbook(dirPath);
        ChangeFileUtil.addEPUB(new HashMap<>(), new File(dirPath));
    }

    public static void renameEbook(String dirPath) {
        List<File> files = FileUtil.getFilePath(new File(dirPath));
        files.forEach(it -> {
            String newName = getEbookName(it.getName());
            if (!it.getName().equals(newName)) {
                System.out.println(it.getName());
                System.out.println(newName);
                File file = new File(it.getParentFile() + File.separator + newName);
                if (!file.exists()) {
                    it.renameTo(file);
                }
                it.delete();
            }
        });
    }

    public static String getEbookName(String oldName) {
        if (oldName.contains("刊") || oldName.contains("册")) {
            return oldName;
        }
        return oldName
                .replaceAll("【全本】", "")
                .replaceAll("》.*?\\.txt$", "\\.txt")
                .replaceAll("^.*《", "")
                .replaceAll("》", "")
                .replaceAll("【", "")
                .replaceAll("】", "")
                .replaceAll("^\\d\\d[._]", "")
                ;
    }

    public static void removeRepeatEbook(String dirPath) {
        Map<String, List<File>> repeatFile = new HashMap<>();
        Map<String, List<File>> fileMap = new HashMap<>();
        File dir = new File(dirPath);
        ChangeFileUtil.getFilePathMd5(fileMap, dir);
        fileMap.keySet().stream().filter(key -> fileMap.get(key).size() != 1).forEach(key -> {
            repeatFile.put(key, fileMap.get(key));
            List<File> collect = fileMap.get(key).stream().sorted(
                    Comparator.comparing((File it) -> -it.getName().length())
            ).limit(1).collect(Collectors.toList());
            collect.forEach(it -> {
                System.out.println(it.getName() + " " + it.getAbsolutePath());
                it.delete();
            });
        });
        if (repeatFile.keySet().size() == 0) {
            System.out.println("该目录下文件不存在重复");
        }
    }

    private static void getFilePathMd5(Map<String, List<File>> fileMap, File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File oneFile : files) {
                    ChangeFileUtil.getFilePathMd5(fileMap, oneFile);
                }
            }
        } else {
            String key = MD5Util.getMD5(file);
            Optional<String> first = fileMap.keySet().stream().filter(it -> it.equals(key)).findFirst();
            if (!first.isPresent()) {
                List<File> fileList = new ArrayList<>();
                fileMap.put(key, fileList);
            }
            fileMap.get(key).add(file);
        }
    }


    private static void addEPUB(Map<String, File> fileMap, File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File oneFile : files) {
                    ChangeFileUtil.addEPUB(fileMap, oneFile);
                }
            }
        } else {
            String name = file.getName();
            //不要PDF
            if (name.toLowerCase().contains(".pdf")) {
                file.delete();
            }
            String key = name.replaceAll("\\.txt", "")
                    .replaceAll("\\.epub", "")
                    .replaceAll("\\.mobi", "");
            File oldFile = fileMap.get(key);
            if (null != oldFile) {
                if (name.contains("\\.epub")) {
//                    try {
//                        File newFile = new File("/media/engwen/备份磁盘/电子书/epub/" + name);
//                        Files.copy(file.toPath(), newFile.toPath());
                    oldFile.delete();
//                        file.delete();
                    fileMap.put(key, file);
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
                }
            } else {
                fileMap.put(name, file);
            }
        }
    }

    public static void music() {
        String dir = "/home/engwen/音乐/";
        List<File> files = FileUtil.getFilePath(new File(dir));
        List<String> temp = new ArrayList<>();
        String reg = "\\s-\\s";
        files.forEach(it -> {
            if (temp.contains(it.getName())) {
                System.out.println("音乐重复：" + it.getName() + "  " + it.getAbsolutePath());
            } else {
                temp.add(it.getName());
            }
            if (it.getName().contains("-") && !it.getName().contains(" - ")) {
                String newName = it.getName().replace("-", " - ");
                ChangeFileUtil.renameMp3(it, newName);
            } else {
                ZHConverter converter = ZHConverter.getInstance(ZHConverter.SIMPLIFIED);
                String newName = converter.convert(it.getName());
                if (!newName.equals(it.getName())) {
                    ChangeFileUtil.renameMp3(it, newName);
                }
            }
        });
    }

    /**
     * 重命名
     * @param it 文件
     * @param newName 新的名称
     */
    private static void renameMp3(File it, String newName) {
        String filePath = it.getAbsolutePath().replace(it.getName(), newName);
        File newFile = new File(filePath);
        if (!newFile.exists()) {
            System.out.println("音乐重命名：" + it.getName() + "  " + newFile.getName());
            it.renameTo(newFile);
        } else {
            System.out.println("音乐重复：" + it.getName() + "  " + it.getAbsolutePath());
        }
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

    public static void removeLittleNotMp4() {
        List<String> dirList = new ArrayList<>();
        dirList.add("/mnt/sd2/小电影/Movies/");
        dirList.forEach(file -> {
            List<File> filePath = FileUtil.getFilePath(new File(file));
            filePath.forEach(it -> {
//                 || it.getName().toLowerCase().contains(".avi")
                String type = it.getName().substring(it.getName().lastIndexOf("."));
                mpA(it, type);
            });
        });
    }

    private static void mpA(File it, String type) {
        if (it.getName().toLowerCase().contains(type)) {
            String newName = it.getName()
                    .replaceAll(type, "")
                    .replaceAll("_-_.*\\.mp4", type)
                    .replaceAll("_-_.*\\.mp4", type)
                    .replaceAll(" - ", "")
                    .replaceAll("_-_TheAV", "")
                    .replaceAll("TheAV", "")
                    .replaceAll(" - TheAV", "")
                    .replaceAll("_-_", " ")
                    .replaceAll("www\\.loveuu\\.pw_\\.", "")
                    .replaceAll("https___www\\.j24u8k2h7sr3\\.", "")
                    .replaceAll("迅雷下载-磁力-乐悠悠-www.loveuu.pw_", "")
                    .replaceAll("\\.html", "")
                    .replaceAll("1-", "")
                    .replaceAll("1-", "")
                    .replaceAll("❤️", " ")
                    .replaceAll("私房最新流出售价120元新作❤", "")
                    .replaceAll("在线播放", "") + type;
            File newFile = new File(it.getAbsolutePath().replace(it.getName(), newName));
            if (!newFile.exists()) {
                System.out.println("rename " + it.getName());
                System.out.println("new name " + newName);
                System.out.println(it.renameTo(newFile));
            }
        } else {
            System.out.println("delete" + it.getName());
            it.delete();
        }
    }
}
