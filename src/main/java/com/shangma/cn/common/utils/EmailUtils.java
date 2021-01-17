package com.shangma.cn.common.utils;

import com.shangma.cn.common.container.SpringBeanUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class EmailUtils {


    /**
     * @param from        发件人
     * @param to          收件人
     * @param subject     主题
     * @param templateStr 内容
     * @throws MessagingException
     */
    public static void sendEmail(String from, String to, String subject, String templateStr) {

        try {
            JavaMailSender javaMailSender = SpringBeanUtils.getBean(JavaMailSender.class);
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(templateStr, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
