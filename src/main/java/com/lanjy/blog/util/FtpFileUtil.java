package com.lanjy.blog.util;


import com.lanjy.blog.config.FtpConfig;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.util
 * @类描述：
 * @创建人：lanjy
 * @创建时间：2020/3/14
 */
@Component
public class FtpFileUtil {

    private static FtpFileUtil ftpFileUtil;
    private static FtpConfig ftpConfig;

    public FtpFileUtil(FtpConfig ftpConfig) {
        this.ftpConfig = ftpConfig;
    }

    @PostConstruct
    public void init(){
        ftpFileUtil = this;
    }

    public static boolean uploadFile(String originFileName,InputStream input){
        boolean success = false;
        FTPClient ftp = new FTPClient();
//        ftp.setControlEncoding("GBK");
        try {
            int reply;
            ftp.connect(ftpConfig.getIp(), ftpConfig.getPort());// 连接FTP服务器
            ftp.login(ftpConfig.getFtpuser(), ftpConfig.getPassword());// 登录
            reply = ftp.getReplyCode();
            //判断是否连接上FTP
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.makeDirectory(ftpConfig.getBasepath() );
            ftp.changeWorkingDirectory(ftpConfig.getBasepath() );
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
