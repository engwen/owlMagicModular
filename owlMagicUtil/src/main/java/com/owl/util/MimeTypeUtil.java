package com.owl.util;

import java.util.HashMap;
import java.util.Map;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/06/28.
 */
public abstract class MimeTypeUtil {
    private static class MimeTypeMappings {
        private static Map<String, String> mappings = new HashMap<>();

        static {
            // applications
            mappings.put("eps", "application/postscript");
            mappings.put("exe", "application/x-executable");
            mappings.put("doc", "application/vnd.ms-word");
            mappings.put("xls", "application/vnd.ms-excel");
            mappings.put("ppt", "application/vnd.ms-powerpoint");
            mappings.put("pps", "application/vnd.ms-powerpoint");
            mappings.put("pdf", "application/pdf");
            mappings.put("xml", "application/xml");
            mappings.put("swf", "application/x-shockwave-flash");
            mappings.put("torrent", "application/x-bittorrent");
            mappings.put("jar", "application/x-jar");
            // open office (finfo detect as application/zip)
            mappings.put("odt", "application/vnd.oasis.opendocument.text");
            mappings.put("ott", "application/vnd.oasis.opendocument.text-template");
            mappings.put("oth", "application/vnd.oasis.opendocument.text-web");
            mappings.put("odm", "application/vnd.oasis.opendocument.text-master");
            mappings.put("odg", "application/vnd.oasis.opendocument.graphics");
            mappings.put("otg", "application/vnd.oasis.opendocument.graphics-template");
            mappings.put("odp", "application/vnd.oasis.opendocument.presentation");
            mappings.put("otp", "application/vnd.oasis.opendocument.presentation-template");
            mappings.put("ods", "application/vnd.oasis.opendocument.spreadsheet");
            mappings.put("ots", "application/vnd.oasis.opendocument.spreadsheet-template");
            mappings.put("odc", "application/vnd.oasis.opendocument.chart");
            mappings.put("odf", "application/vnd.oasis.opendocument.formula");
            mappings.put("odb", "application/vnd.oasis.opendocument.database");
            mappings.put("odi", "application/vnd.oasis.opendocument.image");
            mappings.put("oxt", "application/vnd.openofficeorg.extension");
            // MS office 2007 (finfo detect as application/zip)
            mappings.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            mappings.put("docm", "application/vnd.ms-word.document.macroEnabled.12");
            mappings.put("dotx", "application/vnd.openxmlformats-officedocument.wordprocessingml.template");
            mappings.put("dotm", "application/vnd.ms-word.template.macroEnabled.12");
            mappings.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            mappings.put("xlsm", "application/vnd.ms-excel.sheet.macroEnabled.12");
            mappings.put("xltx", "application/vnd.openxmlformats-officedocument.spreadsheetml.template");
            mappings.put("xltm", "application/vnd.ms-excel.template.macroEnabled.12");
            mappings.put("xlsb", "application/vnd.ms-excel.sheet.binary.macroEnabled.12");
            mappings.put("xlam", "application/vnd.ms-excel.addin.macroEnabled.12");
            mappings.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
            mappings.put("pptm", "application/vnd.ms-powerpoint.presentation.macroEnabled.12");
            mappings.put("ppsx", "application/vnd.openxmlformats-officedocument.presentationml.slideshow");
            mappings.put("ppsm", "application/vnd.ms-powerpoint.slideshow.macroEnabled.12");
            mappings.put("potx", "application/vnd.openxmlformats-officedocument.presentationml.template");
            mappings.put("potm", "application/vnd.ms-powerpoint.template.macroEnabled.12");
            mappings.put("ppam", "application/vnd.ms-powerpoint.addin.macroEnabled.12");
            mappings.put("sldx", "application/vnd.openxmlformats-officedocument.presentationml.slide");
            mappings.put("sldm", "application/vnd.ms-powerpoint.slide.macroEnabled.12");
            // archives
            mappings.put("gz", "application/x-gzip");
            mappings.put("tgz", "application/x-gzip");
            mappings.put("bz", "application/x-bzip2");
            mappings.put("bz2", "application/x-bzip2");
            mappings.put("tbz", "application/x-bzip2");
            mappings.put("zip", "application/zip");
            mappings.put("rar", "application/x-rar");
            mappings.put("tar", "application/x-tar");
            mappings.put("7z", "application/x-7z-compressed");
            // texts
            mappings.put("txt", "text/plain");
            mappings.put("php", "text/x-php");
            mappings.put("html", "text/html");
            mappings.put("htm", "text/html");
            mappings.put("js", "text/javascript");
            mappings.put("css", "text/css");
            mappings.put("rtf", "text/rtf");
            mappings.put("rtfd", "text/rtfd");
            mappings.put("py", "text/x-python");
            mappings.put("java", "text/x-java-source");
            mappings.put("rb", "text/x-ruby");
            mappings.put("sh", "text/x-shellscript");
            mappings.put("pl", "text/x-perl");
            mappings.put("sql", "text/x-sql");
            mappings.put("c", "text/x-csrc");
            mappings.put("h", "text/x-chdr");
            mappings.put("cpp", "text/x-c++src");
            mappings.put("hh", "text/x-c++hdr");
            mappings.put("log", "text/plain");
            mappings.put("csv", "text/x-comma-separated-values");
            // images
            mappings.put("bmp", "image/x-ms-bmp");
            mappings.put("jpg", "image/jpeg");
            mappings.put("jpeg", "image/jpeg");
            mappings.put("gif", "image/gif");
            mappings.put("png", "image/png");
            mappings.put("tif", "image/tiff");
            mappings.put("tiff", "image/tiff");
            mappings.put("tga", "image/x-targa");
            mappings.put("psd", "image/vnd.adobe.photoshop");
            mappings.put("ai", "image/vnd.adobe.photoshop");
            mappings.put("xbm", "image/xbm");
            mappings.put("pxm", "image/pxm");
            //audio
            mappings.put("mp3", "audio/mpeg");
            mappings.put("mid", "audio/midi");
            mappings.put("ogg", "audio/ogg");
            mappings.put("oga", "audio/ogg");
            mappings.put("m4a", "audio/x-m4a");
            mappings.put("wav", "audio/wav");
            mappings.put("wma", "audio/x-ms-wma");
            // video
            mappings.put("avi", "video/x-msvideo");
            mappings.put("dv", "video/x-dv");
            mappings.put("mp4", "video/mp4");
            mappings.put("mpeg", "video/mpeg");
            mappings.put("mpg", "video/mpeg");
            mappings.put("mov", "video/quicktime");
            mappings.put("wm", "video/x-ms-wmv");
            mappings.put("flv", "video/x-flv");
            mappings.put("mkv", "video/x-matroska");
            mappings.put("webm", "video/webm");
            mappings.put("ogv", "video/ogg");
            mappings.put("ogm", "video/ogg");
        }
    }

    /**
     * 获取通过文件后缀名称，获取详细的文件类型属性
     * @param fileName 文件名称
     * @return 详细的文件属性
     */
    public static String forExtension(String fileName) {
        String extension = getFileTypeByFileName(fileName);
        return null != MimeTypeMappings.mappings.get(extension) ? MimeTypeMappings.mappings.get(extension) : "unknown";
    }

    /**
     * 获取文件的后缀名
     * @param fileName 文件名称
     * @return 文件类型
     */
    public static String getFileTypeByFileName(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
    }

    /**
     * 依据file的contentType类型获取文件的类型
     * @param contentType  file的contentType
     * @return 文件类型
     */
    public static String getFileTypeByContentType(String contentType) {
        String result = "unknown";
        for (String key : MimeTypeMappings.mappings.keySet()) {
            if (MimeTypeMappings.mappings.get(key).equals(contentType)) {
                result = key;
                break;
            }
        }
        return result;
    }
}
