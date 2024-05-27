package com.owl.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2024/5/18.
 */
public class PngToPdf {
    public static void main(String[] args) throws DocumentException, IOException {
        String filePath = "/mnt/sda2/temp/[韩漫]2022年新整理大合集[224本][36.5G]/2022新整理-2/";
        Map<String, List<File>> mapFilePath = FileUtil.getMapFilePath(new File(filePath));
        System.out.println(mapFilePath);

        for (String fp : mapFilePath.keySet()) {
            List<File> collect = mapFilePath.get(fp).stream().sorted(Comparator.comparing(File::getName)).collect(Collectors.toList());
            System.out.println(collect);
            if (fp.contains("zip") || fp.contains("output") || fp.replaceAll("/", "").equals(filePath.replaceAll("/", "")) || collect.isEmpty() ||
                    collect.stream().anyMatch(it -> it.getName().toLowerCase().contains("pdf"))) {
                continue;
            }
            generatePdfFile(fp, mapFilePath.get(fp));
        }
    }

    /**
     * 将图片转换为PDF文件
     * @param fileList 图片文件集合
     * @throws IOException IO异常
     */
    private static void generatePdfFile(String filePath, List<File> fileList) throws IOException, DocumentException {
        String fileName = filePath.replaceAll("/mnt/.*2022新整理-./", "");
        String pdfFileName = "/mnt/sda2/temp/" + fileName + ".pdf";
        System.out.println(fileName);
        File pdfFile = new File(pdfFileName);
        if (pdfFile.exists()) {
            return;
        }
        Document doc = new Document(PageSize.A4, 20, 20, 20, 20);
        PdfWriter.getInstance(doc, Files.newOutputStream(Paths.get(pdfFileName)));
        doc.open();
        List<File> collect = fileList.stream().sorted(Comparator.comparing(File::getName)).collect(Collectors.toList());
        for (File file : collect) {
            Image image = Image.getInstance(file.getAbsolutePath());
            float height = image.getHeight();
            float width = image.getWidth();
            if (image.getWidth() > 14400 || height > 14400) {
                int percent = getPercent(height, width);
                image.setAlignment(Image.MIDDLE);
                image.scalePercent(percent);
            } else {
                image.scaleToFit(width, height);
                doc.setPageSize(new Rectangle(0, 0, width, height));
            }
            doc.newPage();
            doc.add(image);
        }

        doc.close();
    }

    /**
     * 等比压缩，获取压缩百分比
     * @param height 图片的高度
     * @param weight 图片的宽度
     * @return 压缩百分比
     */
    private static int getPercent(float height, float weight) {
        float percent = 0.0F;
        if (height > weight) {
            percent = PageSize.A4.getHeight() / height * 100;
        } else {
            percent = PageSize.A4.getWidth() / weight * 100;
        }
        return Math.round(percent);
    }
}
