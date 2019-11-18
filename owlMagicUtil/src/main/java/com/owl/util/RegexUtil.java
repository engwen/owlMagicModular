package com.owl.util;

import com.owl.model.ModelPrototype;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2017/3/16.
 */
public abstract class RegexUtil {

    private static final String is_empty = "(\\s)";

    private static final String is_mobile = "^1[3456789]\\d{9}$";

    private static final String is_email = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    private static final String is_date = "^\\d{4}[-/]\\d{1,2}[-/]\\d{1,2}$";

    private static final String is_postCodes = "^[1-9]\\d{5}$";

    private static final String is_ip = "^([0-9]{1,2}|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.([0-9]{1,2}|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.([0-9]{1,2}|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.([0-9]{1,2}|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";

    private static final String is_http = "^(http|https)\\://(\\w+\\.\\w+\\.\\w+|\\w+\\.\\w+)";

    /**
     * 驗證字符串中是否有符合正則的字串
     * @param reg   正則
     * @param input 字符串
     * @return boolean
     */
    public static String getContainsStr(String reg, String input) {
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            return matcher.group();
        }
        return null;
    }

    /**
     * 驗證字符串中是否有符合正則的字串
     * @param reg   正則
     * @param input 字符串
     * @return boolean
     */
    public static boolean isContainsStr(String reg, String input) {
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    /**
     * 驗證是否爲空
     * @param input 字符串
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        return null == input || "".equals(input) || Pattern.matches(is_empty, input) || "null".equals(input);
    }

    /**
     * 驗證是否爲空 傳遞參數時
     * @param input 字符串
     * @return boolean
     */
    public static boolean isEmpty(Object input) {
        return null == input || (input instanceof String && isEmpty((String) input)) || (input instanceof Collection && ((Collection) input).size() <= 0);
    }


    /**
     * 手机号码验证,11位，不知道详细的手机号码段，只是验证开头必须是1和位数
     * @param input 字符串
     * @return boolean
     */
    public static boolean isMobile(String input) {
        return !isEmpty(input) && Pattern.matches(is_mobile, input);
    }

    /**
     * 驗證是否是郵件
     * @param input 字符串
     * @return boolean
     */
    public static boolean isEmail(String input) {
        return !isEmpty(input) && Pattern.matches(is_email, input);
    }

    /**
     * 驗證是否是日期
     * @param input 字符串
     * @return boolean
     */
    public static boolean isDate(String input) {
        return !isEmpty(input) && Pattern.matches(is_date, input);
    }


    /**
     * 检查邮政编码(中国),6位，第一位必须是非0开头，其他5位数字为0-9
     * @param input 字符串
     * @return boolean
     */
    public static boolean isPostCodes(String input) {
        return !isEmpty(input) && input.matches(is_postCodes);
    }


    /**
     * 检查邮政编码(中国),6位，第一位必须是非0开头，其他5位数字为0-9
     * @param input 字符串
     * @return boolean
     */
    public static boolean isIp(String input) {
        return !isEmpty(input) && Pattern.matches(is_ip, input);
    }


    public boolean checkWebSite(String url) {
        return !isEmpty(url) && url.matches(is_http);
    }

    /*
     * 检验用户名 取值范围为a-z,A-Z,0-9,"_",汉字，不能以"_"结尾 用户名有最小长度和最大长度限制，比如用户名必须是4-20位
     */
    public boolean isUserName(String username, int min, int max) {
        String regex = "[\\w\u4e00-\u9fa5]{" + min + "," + max + "}(?<!_)";
        return username.matches(regex);
    }


    /**
     * 檢測所有的參數是否爲空
     * @param inputs 未定義長度數組
     * @return 結果
     */
    public static boolean isParamsAllEmpty(Object... inputs) {
        return Arrays.stream(inputs).noneMatch(input -> null == input || (input instanceof String && !isEmpty((String) input))
                || (input instanceof Collection && ((Collection) input).size() <= 0));
    }

    /**
     * 數組對象中是否含有爲空
     * @param inputs 數組
     * @return boolean
     */
    public static boolean isParamsHaveEmpty(Object... inputs) {
        return Arrays.stream(inputs).anyMatch(input -> null == input || (input instanceof String && !isEmpty((String) input))
                || (input instanceof Collection && ((Collection) input).size() <= 0));
    }

    /**
     * 檢測是否具有爲空選項,注意先後順序
     * @param params 確保對象不能爲空
     * @param inputs 確保對象下的屬性不能爲空
     * @return boolean
     */
    public static boolean isParamsHaveEmpty(ModelPrototype params, Object... inputs) {
        return null == params || isParamsHaveEmpty(inputs);
    }

    /**
     * 判断一个对象是否有属性为空--有一个为空即为true，全部非空才为false
     * @param object MsgResult的子类对象
     * @return boolean
     */
    public static boolean isObjectHaveEmpty(ModelPrototype object) {
        if (object == null) {
            return true;
        } else {
            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass(), ModelPrototype.class);
                PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
                if (descriptors != null && descriptors.length > 0) {
                    for (PropertyDescriptor descriptor : descriptors) {
                        Method method = descriptor.getReadMethod();
                        Object obj = method.invoke(object);
                        //如果类型是String类型,需满足正则校验
                        if (obj == null || (obj instanceof String && isEmpty((String) obj))) {
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return true;
            }
        }
        return false;
    }

    /**
     * 校验单列集合是否有数据
     * @param collection 對象集合
     * @return true 有
     */
    public static boolean isCollectionHaveData(Collection collection) {
        return collection != null && collection.size() > 0;
    }
}
