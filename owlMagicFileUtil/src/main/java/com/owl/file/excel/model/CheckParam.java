package com.owl.file.excel.model;

import com.owl.mvc.vo.MsgResultVO;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2024/12/11.
 */
@FunctionalInterface
public interface CheckParam {
    /**
     * 校验的值
     * @param param 待校验参数
     * @return 结果
     */
    MsgResultVO<String> test(String param);
}
