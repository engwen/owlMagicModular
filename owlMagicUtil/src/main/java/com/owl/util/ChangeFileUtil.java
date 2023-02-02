package com.owl.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangeFileUtil {
    private final static String dirPath = "/mnt/sd2/视频/";

    public static void main(String[] args) {
//        System.out.println(getName("【高清MP4电影www.boxmp4.com】加勒比海盗1：黑珍珠号的诅咒.Pirates of the Caribbean： The Curse of the Black Pearl.2003.BD720P.国英双语.中英双字.mp4"));
        ChangeFileUtil.movie();
    }

    public static void movie(){
        List<File> fileList = FileUtil.getFilePath(new File(dirPath));
        String regex = "\\.mp4";
        for (File file : fileList) {
            File newFile = new File(dirPath + ChangeFileUtil.getName(file.getName()));
            if(file.getAbsoluteFile().equals(newFile.getAbsoluteFile())){
                continue;
            } else if(newFile.getName().replaceAll(regex,"").equals("")){
                continue;
            }
            if(newFile.exists()){
                continue;
            }
            System.out.println("old name is " + file.getName() + "  new name is " + newFile.getName());
            if(!file.renameTo(newFile)){
                System.out.println("rename is error");
            }
        }
    }


    public static String getName(String oldName){
        return oldName
                .replaceAll("tctyjxl1","铁齿铜牙纪晓岚第一季")
                .replaceAll("爱情公Y","爱情公寓")
                .replaceAll("阳光电影www\\.ygdy8\\.com\\.","")
                .replaceAll("\\[.*\\]","")
                .replaceAll("【.*】","")
//                .replaceAll("\\[GM-Team\\]\\[国漫\\]\\[一人之下 第3季\\]\\[The Outcast 3rd Season\\]","一人之下")
//                .replaceAll("\\[","").replaceAll("\\]"," ")
                .replaceAll("迅雷.*\\.mp4",".mp4");
    }

}
