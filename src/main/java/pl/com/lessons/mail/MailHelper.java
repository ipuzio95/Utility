/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.lessons.mail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
/**
 *
 * @author bfries
 */
public class MailHelper
{
    private static final String SMTP_PORT = "25";
    private static final String CONTENT_TYPE = "text/plain; charset=utf-8";
    private static final List<String>EMPTY_LIST = new ArrayList<String>();

    private static final char[] symbols;

    static {
        StringBuilder tmp = new StringBuilder();
        for (char ch = '0'; ch <= '9'; ++ch)
            tmp.append(ch);
        for (char ch = 'a'; ch <= 'z'; ++ch)
            tmp.append(ch);
        symbols = tmp.toString().toCharArray();
    }

    public static String getRandomString() {
        Random random = new Random();
        char[] buf = new char[20];
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }

    public static void sendMail(
            String host,
            String port,
            String from,
            String to,
            String subject,
            String content,
            List<String> attachmentFiles,
            String user,
            String password,
            String contentType) throws Exception
    {
        Transport tr = null;
        try
        {
            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", port);
            Session session = Session.getInstance(props, null);
            session.setDebug(false);

            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address =
            {
                new InternetAddress(to)
            };
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject,"UTF-8");
            Multipart mp = new MimeMultipart();
            if (attachmentFiles != null && !attachmentFiles.isEmpty())
            {
                for (String attachmentFile : attachmentFiles)
                {
                    FileDataSource fds = new FileDataSource(attachmentFile);
                    MimeBodyPart mbpa = new MimeBodyPart();
                    mbpa.setDataHandler(new DataHandler(fds));
                    mbpa.setFileName(attachmentFile);
                    mbpa.setHeader("Content-Transfer-Encoding", "base64");
                    mp.addBodyPart(mbpa);
                }
            }
            MimeBodyPart mbp = new MimeBodyPart();
            mbp.setText(content, "UTF-8");
            mbp.setHeader("Content-Type", contentType);
            mbp.setHeader("Content-Transfer-Encoding", "base64");
            mp.addBodyPart(mbp);
            msg.setContent(mp);

            msg.setSentDate(new Date());
            tr = session.getTransport("smtp");
            tr.connect(host, user, password);
            msg.saveChanges();
            tr.sendMessage(msg, msg.getAllRecipients());
            tr.close();
        } catch (Exception e)
        {
            throw e;
        } finally
        {
            if (tr != null && tr.isConnected())
            {
                tr.close();
            }
        }
    }

    public static void sendMail(
            String host,
            String from,
            String to,
            String subject,
            String content,
            List<String> attachmentFiles,
            String user,
            String password) throws Exception
    {
        sendMail(host, SMTP_PORT, from, to, subject, content, attachmentFiles, user, password, CONTENT_TYPE);
    }

    public static void sendMail(
            String host,
            String from,
            String to,
            String subject,
            String content,
            String user,
            String password) throws Exception
    {
        sendMail(host, from, to, subject, content, EMPTY_LIST, user, password);
    }

    public static void sendMail(
            String host,
            String port,
            String from,
            String to,
            String subject,
            String content,
            String user,
            String password,
            String contentType) throws Exception
    {
        sendMail(host, port, from, to, subject, content, EMPTY_LIST, user, password, contentType);
    }  
}
