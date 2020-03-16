package com.lanjy.blog.util;


import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.util
 * @类描述：
 * @创建人：lanjy
 * @创建时间：2020/3/14
 */
public class FtpFileUtil {
    //ftp服务器ip地址
    @Value("${ftp.ip}")
    private static final String FTP_IP  = "192.168.1.5";
    //端口号
    @Value("${ftp.port}")
    private static final int    FTP_PORT     = 21;
    //用户名
    @Value("${ftp.username}")
    private static final String FTP_USERNAME = "ftpuser";
    //密码
    @Value("${ftp.password}")
    private static final String FTP_PASSWORD = "Password";
    //图片路径
    @Value("${ftp.basepath}")
    private static final String FTP_BASEPATH = "/home/ftpuser/image";

    public  static boolean uploadFile(String originFileName,InputStream input){
        boolean success = false;
        FTPClient ftp = new FTPClient();
//        ftp.setControlEncoding("GBK");
        try {
            int reply;
            ftp.connect(FTP_IP, FTP_PORT);// 连接FTP服务器
            ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
            reply = ftp.getReplyCode();
            //判断是否连接上FTP
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.makeDirectory(FTP_BASEPATH );
            ftp.changeWorkingDirectory(FTP_BASEPATH );
            ftp.storeFile(originFileName,input);
            input.close();
            ftp.logout();
            success = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }


}
