package com.owl.file.excel;

import com.alibaba.fastjson.JSONArray;
import com.owl.file.excel.model.ExcelImportObj;
import com.owl.file.excel.util.ExcelUtil;
import com.owl.mvc.vo.MsgResultVO;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2025/2/27.
 */
public class ExcelUtilsExample {

    public static void main(String[] args) throws IOException {
        File excel = new File("");
        InputStream inputStream = Files.newInputStream(excel.toPath());

        MsgResultVO<Workbook> workbookMsgResultVO = ExcelUtil.getWorkBookResult(excel, inputStream);
        if (!workbookMsgResultVO.getResult()) {
            System.out.println("获取文件失败");
            return;
        }
        Sheet sheet = workbookMsgResultVO.getResultData().getSheetAt(0);

        ExcelImportObj obj = new ExcelImportObj();
        obj.setYTitle(1);
        obj.setYValue(2);
        obj.addToList(0, 0, true, "开户机构编码", "openaccountOrgCode");
        obj.addToList(1, 0, true, "开户机构类型名称", "openaccountOrgName");
        obj.addToList(2, 0, true, "客户编码", "orgCode");
        obj.addToList(3, 0, true, "客户名称", "orgName");
        obj.addToList(4, 0, true, "成员单位银行账户", "partAcco");
        obj.addToList(5, 0, true, "关联账户", "insideAcco");
        obj.addToList(6, 0, true, "资金中心总账号", "proxyAccoCode");
        obj.addToList(7, 0, true, "银企直连权限编码(参考sheet2)", "authCode");
        obj.addToList(8, 0, true, "银企直连权限名称", "authName");

        //验证头部文字是否正确
        MsgResultVO<String> stringMsgResultVO = ExcelUtil.checkTitle(sheet, obj);
        if (!stringMsgResultVO.getResult()) {
            System.out.println(stringMsgResultVO);
            return;
        }
        //验证数据
        MsgResultVO<JSONArray> jsonArrayResult = ExcelUtil.getSheetInfo(sheet, obj);
        if (!jsonArrayResult.getResult()) {
            System.out.println(jsonArrayResult);
            return;
        }
        inputStream.close();
        //拿到数据
        List<MsgResultVO> list = jsonArrayResult.getResultData().toJavaList(MsgResultVO.class);

    }

}
