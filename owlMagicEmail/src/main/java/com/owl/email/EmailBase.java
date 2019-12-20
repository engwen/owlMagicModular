package com.owl.email;

import com.owl.util.LogPrintUtil;
import com.owl.util.RegexUtil;

import javax.mail.Session;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * 發送郵件
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/12/4.
 */
public class EmailBase {
    private String to;
    private String from;
    private String host;
    private String smtpType;
    private String port = "465";
    private Boolean useAuth = true;
    private Boolean useSSL = true;
    private Boolean useDebug = true;
    private Boolean useHtml = false;

    private String subject;//主題
    private String context;//内容
    private String userName;//發送用戶
    private String password;//發送用戶密碼

    private EmailBase(String to, String from, String password) {
        this.to = to;
        this.from = from;
        this.password = password;
    }

    public static EmailBase getInstances(String to, String from, String password) {
        return new EmailBase(to, from, password);
    }

    public void init(String subject, String context) {
        this.subject = subject;
        this.context = context;
    }

    public void send() {
        if (!sendReady()) {
            return;
        }
        Properties properties = System.getProperties();
        properties.put("mail.transport.protocol", smtpType);// 连接协议
        properties.put("mail.smtp.host", host);// 主机名
        properties.put("mail.smtp.port", port);// 端口号
        properties.put("mail.smtp.auth", useAuth.toString());
        properties.put("mail.smtp.ssl.enable", useSSL.toString());// 设置是否使用ssl安全连接
        properties.put("mail.debug", useDebug.toString());// 设置是否显示debug信息 true 会在控制台显示相关信息
        Session session = Session.getDefaultInstance(properties);
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            String nick = "";
            Transport transport;
            try {
                nick = javax.mail.internet.MimeUtility.encodeText(userName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(nick + " <" + from + ">"));
            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // Set Subject: header field
            message.setSubject(subject);
            // Now set the actual message
            if (useHtml) {
                message.setContent(context, "text/html");
            } else {
                message.setText(context);
            }
            transport = session.getTransport();//getTransport
            // 连接自己的邮箱账户
            transport.connect(from, password);//"jnzwmdimklhujdeb"
            // Send message
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            if (transport.isConnected()) {
                transport.close();
            }
            LogPrintUtil.info("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    private boolean sendReady() {
        boolean result = true;
        if (!RegexUtil.isEmail(this.from)) {
            LogPrintUtil.error("发件人邮箱格式非法");
            result = false;
        }
        if (RegexUtil.isEmpty(this.password)) {
            LogPrintUtil.error("发件人密码不能为空");
            result = false;
        }
        if (RegexUtil.isEmpty(this.to)) {
            LogPrintUtil.error("收件人不能为空");
            result = false;
        }
        if (RegexUtil.isEmpty(this.subject)) {
            LogPrintUtil.error("邮件主题不能为空");
            result = false;
        }
        if (RegexUtil.isEmpty(this.context)) {
            LogPrintUtil.error("邮件内容不能为空");
            result = false;
        }
        if (RegexUtil.isEmpty(this.host)) {
            LogPrintUtil.error("邮件服务器不能为空");
            result = false;
        }
        if (RegexUtil.isEmpty(this.port)) {
            LogPrintUtil.error("发送端口不能为空");
            result = false;
        }
        if (RegexUtil.isEmpty(this.smtpType)) {
            LogPrintUtil.error("邮件服务器类型不能为空");
            result = false;
        }
        return result;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Boolean getUseAuth() {
        return useAuth;
    }

    public void setUseAuth(Boolean useAuth) {
        this.useAuth = useAuth;
    }

    public Boolean getUseSSL() {
        return useSSL;
    }

    public void setUseSSL(Boolean useSSL) {
        this.useSSL = useSSL;
    }

    public Boolean getUseDebug() {
        return useDebug;
    }

    public void setUseDebug(Boolean useDebug) {
        this.useDebug = useDebug;
    }

    public Boolean getUseHtml() {
        return useHtml;
    }

    public void setUseHtml(Boolean useHtml) {
        this.useHtml = useHtml;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSmtpType() {
        return smtpType;
    }

    public void setSmtpType(String smtpType) {
        this.smtpType = smtpType;
    }
}
