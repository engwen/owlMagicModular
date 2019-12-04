package com.owl.email;

import com.owl.util.LogPrintUtil;

import javax.mail.Session;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
    private String port;
    private Boolean useAuth = true;
    private Boolean useSSL = true;
    private Boolean useDebug = true;
    private Boolean useHtml = false;

    private String subject;//主題
    private String context;//内容
    private String userName;//發送用戶
    private String userPassword;//發送用戶密碼

    public void send() {
        Properties properties = System.getProperties();
        properties.put("mail.transport.protocol", "smtp");// 连接协议
        properties.put("mail.smtp.host", host);// 主机名
        properties.put("mail.smtp.port", port);// 端口号
        properties.put("mail.smtp.auth", useAuth.toString());
        properties.put("mail.smtp.ssl.enable", useSSL.toString());// 设置是否使用ssl安全连接
        properties.put("mail.debug", useDebug.toString());// 设置是否显示debug信息 true 会在控制台显示相关信息
        Session session = Session.getDefaultInstance(properties);
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
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
            Transport transport = session.getTransport();//getTransport
            // 连接自己的邮箱账户
            transport.connect(userName, userPassword);//"jnzwmdimklhujdeb"
            // Send message
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            LogPrintUtil.info("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
