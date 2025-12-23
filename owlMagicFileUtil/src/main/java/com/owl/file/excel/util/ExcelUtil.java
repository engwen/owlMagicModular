package com.owl.file.excel.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.owl.file.excel.model.ExcelColumnValid;
import com.owl.file.excel.model.ExcelImportObj;
import com.owl.mvc.vo.MsgResultVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class ExcelUtil {
    //  使用方式
//    public static MsgResultVO<?> test(MultipartFile file) throws IOException {
//        MsgResultVO<Workbook> workbookResult = ExcelUtil.getWorkBookResult(file);
//        if (!MsgConstant.REQUEST_SUCCESS.getCode().equals(workbookResult.getResultCode())) {
//            return workbookResult;
//        }
//        Workbook workbook = workbookResult.getResultData();
//        Sheet sheet = workbook.getSheetAt(0);
//        ExcelImportObj obj = new ExcelImportObj();
//        obj.setYTitle(1);
//        obj.setYValue(2);
//        obj.addToList(0, 0, false, "序号", "xh", val -> {
//            if (val.isEmpty()) {
//                return MsgResultVO.failed("lalalal");
//            }
//            return MsgResultVO.success();
//        });
//        obj.addToList(1, 0, true, "申请授信单位", "orgName");
//
//        //验证表头
//        MsgResultVO<String> result = ExcelUtil.checkTitel(sheet, obj);
//        if (!MsgConstant.REQUEST_SUCCESS.getCode().equals(result.getResultCode())) {
//            return result;
//        }
//        //验证数据
//        MsgResultVO<JSONArray> jsonArrayResult = ExcelUtil.getSheetInfo(sheet, obj);
//        if (!MsgConstant.REQUEST_SUCCESS.getCode().equals(jsonArrayResult.getResultCode())) {
//            return jsonArrayResult;
//        }
////        //拿到数据
////        List<T> list = jsonArrayResult.getResultData().toJavaList(T.class);
////        List<Map<String, String>> test = new ArrayList<>();
//        file.getInputStream().close();
//        return MsgResultVO.success();
//    }

    /**
     * 导入文件读流是否正确
     * @param multipartFile 文件
     * @return workbook
     */
    public static MsgResultVO<Workbook> getWorkBookResult(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.getOriginalFilename() == null) {
            return MsgResultVO.failed("请选择文件");
        }
        String fileSuffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
        if (StringUtils.isEmpty(fileSuffix) || (!"xls".equals(fileSuffix) && !"xlsx".equals(fileSuffix))) {
            return MsgResultVO.failed("导入文件格式不正确，请重新下载模板。");
        }
        Workbook workbook = null;
        try {
            if ("xlsx".equals(fileSuffix)) {
                workbook = new XSSFWorkbook(multipartFile.getInputStream());
            } else {
                workbook = new HSSFWorkbook(multipartFile.getInputStream());
            }
        } catch (IOException e) {
            return MsgResultVO.failed("获取文件失败:" + e);
        }
        return MsgResultVO.success(workbook);
    }

    /**
     * 导入文件读流是否正确
     * @param file 文件
     * @return workbook
     */
    public static MsgResultVO<Workbook> getWorkBookResult(File file, InputStream inputStream) {
        if (file == null) {
            return MsgResultVO.failed("请选择文件");
        }
        String fileSuffix = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        if (StringUtils.isEmpty(fileSuffix) || (!"xls".equals(fileSuffix) && !"xlsx".equals(fileSuffix))) {
            return MsgResultVO.failed("导入文件格式不正确，请重新下载模板。");
        }
        Workbook workbook = null;
        try {
            if ("xlsx".equals(fileSuffix)) {
                workbook = new XSSFWorkbook(inputStream);
            } else {
                workbook = new HSSFWorkbook(inputStream);
            }
        } catch (IOException e) {
            return MsgResultVO.failed("获取文件失败:" + e);
        }
        return MsgResultVO.success(workbook);
    }

    /**
     * 根据excel单元格类型获取excel单元格值
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                // 如果当前Cell的Type为NUMERIC
                case NUMERIC: {
//                case HSSFCell.CELL_TYPE_NUMERIC: {
                    short format = cell.getCellStyle().getDataFormat();
                    if (format == 14 || format == 31 || format == 57 || format == 58) {   //excel中的时间格式
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        double value = cell.getNumericCellValue();
                        Date date = DateUtil.getJavaDate(value);
                        cellvalue = sdf.format(date);
                    }
                    // 判断当前的cell是否为Date
                    else if (DateUtil.isCellDateFormatted(cell)) {  //先注释日期类型的转换，在实际测试中发现HSSFDateUtil.isCellDateFormatted(cell)只识别2014/02/02这种格式。
                        // 如果是Date类型则，取得该Cell的Date值           // 对2014-02-02格式识别不出是日期格式
                        Date date = cell.getDateCellValue();
                        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                        cellvalue = formater.format(date);
                    } else { // 如果是纯数字
                        // 取得当前Cell的数值
                        cellvalue = NumberToTextConverter.toText(cell.getNumericCellValue());

                    }
                    break;
                }
                // 如果当前Cell的Type为STRIN
                case STRING:
//                case HSSFCell.CELL_TYPE_STRING:
                    // 取得当前的Cell字符串
                    cellvalue = cell.getStringCellValue().replaceAll("'", "''");
                    break;
                case BLANK:
//                case  HSSFCell.CELL_TYPE_BLANK:
                    cellvalue = null;
                    break;
                // 默认的Cell值
                default: {
                    cellvalue = " ";
                }
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;
    }

    /**
     * 导入文件和预定标题是否一致
     * @param sheet sheet
     * @param obj   导入对象
     * @return 结果
     */
    public static MsgResultVO<String> checkTitle(Sheet sheet, ExcelImportObj obj) {
        for (ExcelColumnValid valid : obj.getExcelColumnValidList()) {
            String callValue = ExcelUtil.getCellValue(sheet.getRow(obj.getYTitle()).getCell(valid.getX()));
            if (!valid.getTitle().equals(callValue)) {
                return MsgResultVO.failed("文件第" + (obj.getYTitle() + 1) + "行，第" + (valid.getX() + 1) + "列，列名与系统模板文件不符,请先下载模板!");
            }
        }
        return MsgResultVO.success();
    }

    /**
     * 读取excel内容
     * @param sheet sheet
     * @param obj   导入对象
     * @return 结果
     */
    public static MsgResultVO<JSONArray> getSheetInfo(Sheet sheet, ExcelImportObj obj) {
        int rownum = sheet.getLastRowNum();
        if (rownum < 1) {
            return MsgResultVO.failed("导入模板不能为空");
        }
        if (rownum < obj.getYValue()) {
            return MsgResultVO.failed("未填写内容");
        }
        JSONArray beanList = new JSONArray();
        String str = "";
        for (int i = obj.getYValue(); i <= rownum; i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                break;
            }
            JSONObject beanJson = new JSONObject();
            for (ExcelColumnValid valid : obj.getExcelColumnValidList()) {
                String errorTopStr = "\n" + "第" + (i + 1) + "行，第" + (valid.getX() + 1) + "列，" + valid.getTitle();
                String data = ExcelUtil.getCellValue(row.getCell(valid.getX()));
                if (StringUtils.isNotEmpty(data)) {
                    data = data.trim();
                    //格式判断
                    if (valid.getValidType() == 0) {
                        data = data.replace(".00", "").replace(".0", "");
                    } else if (valid.getValidType() == 1) {
                        data = data.replace(".00", "").replace(".0", "");
                    } else if (valid.getValidType() == 2) {
                        if (!ExcelUtil.validateRateDouble(data)) {
                            str = str + errorTopStr + "浮点数格式错误:" + data;
                            continue;
                        }
                    } else if (valid.getValidType() == 3) {
                        if (!ExcelUtil.validateAmountDouble(data)) {
                            str = str + errorTopStr + "金额格式错误:" + data;
                            continue;
                        }
                    } else if (valid.getValidType() == 4) {
                        if (!ExcelUtil.isDateString(data)) {
                            str = str + errorTopStr + "日期格式错误:" + data;
                            continue;
                        }
                    }
                    if (null != valid.getLenType()) {
                        if (valid.getLenType() < 0 && valid.getLen() < data.length()) {
                            str = str + errorTopStr + "超过该字段最长长度:" + valid.getLen();
                            continue;
                        } else if (valid.getLenType() == 0 && valid.getLen() != data.length()) {
                            str = str + errorTopStr + "该字段长度应为:" + valid.getLen();
                            continue;
                        } else if (valid.getLenType() > 0 && valid.getLen() > data.length()) {
                            str = str + errorTopStr + "小于该字段最低长度:" + valid.getLen();
                            continue;
                        }
                    }
                    if (valid.isInner()) {
                        if (!valid.getInnerList().contains(data)) {
                            str = str + errorTopStr + "取值范围为:" + valid.getInnerList();
                            continue;
                        }
                    }
                    if (valid.isHyper()) {
                        String hyperData = ExcelUtil.getCellValue(row.getCell(valid.getXHyper()));
                        if (StringUtils.isEmpty(hyperData)) {
                            str = str + "\n" + errorTopStr + "，在本行第" + (valid.getXHyper() + 1) + "列存在时，" + valid.getTitle() + "是必填项";
                            continue;
                        }
                    }
                    if (null != valid.getExcelCheckParam()) {
                        MsgResultVO<String> test = valid.getExcelCheckParam().test(data);
                        if (!test.getResult()) {
                            str = str + errorTopStr + test.getResultMsg();
                            continue;
                        }
                    }
                    if (valid.isMapper()) {
                        if (valid.getFiledMap().containsKey(data)) {
                            beanJson.put(valid.getField(), valid.getFiledMap().get(data));
                        } else {
                            str = str + errorTopStr + "取值不在设定范围内";
                            continue;
                        }
                    } else {
                        beanJson.put(valid.getField(), data);
                    }
                } else {
                    if (valid.isMust()) {
                        str = str + errorTopStr + "是必填项";
                        continue;
                    }
                    beanJson.put(valid.getField(), data);
                }
            }
            beanList.add(beanJson);
        }
        if (!StringUtils.isEmpty(str)) {
            return MsgResultVO.failed(str);
        }
        return MsgResultVO.success(beanList);
    }


    public static boolean validateAmountDouble(String number) {
        String regex = "^[-+]?\\d*(\\.\\d{0,2})?$|^0$";
        return Pattern.matches(regex, number);
    }

    public static boolean validateRateDouble(String number) {
        String regex = "^[-+]?\\d*(\\.\\d{0,6})?$|^0$";
        return Pattern.matches(regex, number);
    }

    public static boolean isDateString(String dateStr) {
        String regex = "^\\d{4}-\\d{2}-\\d{2}$";
        String regex2 = "^\\d{4}-\\d{2}-\\d{2}.([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
        return Pattern.matches(regex, dateStr) || Pattern.matches(regex2, dateStr);
    }
}
