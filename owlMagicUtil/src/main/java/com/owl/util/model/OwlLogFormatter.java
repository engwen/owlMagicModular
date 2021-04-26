package com.owl.util.model;

import com.owl.util.DateCountUtil;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2021/4/22.
 */
public class OwlLogFormatter extends Formatter {
    /**
     * Format the given log record and return the formatted string.
     * <p>
     * The resulting formatted String will normally include a
     * localized and formatted version of the LogRecord's message field.
     * It is recommended to use the {@link Formatter#formatMessage}
     * convenience method to localize and format the message field.
     * @param record the log record to be formatted.
     * @return the formatted log record
     */
    @Override
    public String format(final LogRecord record) {
        String msg = record.getLevel() == Level.INFO ? "\033[0;35m" :
                record.getLevel() == Level.WARNING ? "\033[0;32m" : record.getLevel() == Level.SEVERE ? "\033[0;31m" : "";
        return msg + "[" + record.getLevel() + "][" + DateCountUtil.getDateFormSdf(new Date(), DateCountUtil.YMDHMS4BAR) +
                "][" + Thread.currentThread().getStackTrace()[9].getClassName() + "."
                + Thread.currentThread().getStackTrace()[9].getMethodName() + "] " + record.getMessage() + "\n";
    }
}
