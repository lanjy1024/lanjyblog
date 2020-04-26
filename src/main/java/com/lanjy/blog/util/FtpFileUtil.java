package com.lanjy.blog.util;


import com.lanjy.blog.config.FtpConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.util
 * @类描述：
 * @创建人：lanjy
 * @创建时间：2020/3/14
 */
@Slf4j
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
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            ftp.connect(ftpConfig.getIp(), ftpConfig.getPort());// 连接FTP服务器
            // 登录
            boolean login = ftp.login(ftpConfig.getFtpuser(), ftpConfig.getPassword());
            //判断是否连接上FTP
            if (!login) {
                ftp.disconnect();
                return false;
            }
            //FTP支持两种模式，一种方式叫做Standard主动方式，缺省时默认的方式，一种是 Passive 被动方式。
            //ftp.enterLocalPassiveMode();
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.makeDirectory(ftpConfig.getBasepath() );
            ftp.changeWorkingDirectory(ftpConfig.getBasepath() );
            if (!ftp.storeFile(originFileName, input)) {
                log.error("文件上传失败：ftp.storeFile({}, {})",originFileName,input);
                return false;
            }
            // 退出FTP
            ftp.logout();
            result = true;
        } catch (SocketException e) {
            log.error("无法连接到FTP服务器ip:{},port:{}, exception:{}",ftpConfig.getFtpuser(),ftpConfig.getIp(),ftpConfig.getPort(),StringUtil.getStackTraceAsString(e));
        } catch (IOException e) {
            log.error("无法连接到FTP服务器ip:{},port:{}, exception:{}",ftpConfig.getFtpuser(),ftpConfig.getIp(),ftpConfig.getPort(),StringUtil.getStackTraceAsString(e));
        } finally {
            IOUtils.closeQuietly(input);
            disconnect(ftp);
        }
        return result;
    }


    /**
     * 关闭FTP连接
     *
     * @param ftp
     */
    private static void disconnect(FTPClient ftp) {
        if (ftp.isConnected()) {
            try {
                ftp.disconnect();
            } catch (IOException ioe) {
                log.error("关闭FTP连接 {} 出错,{}",ftp.getRemoteAddress(),StringUtil.getStackTraceAsString(ioe));
            }
        }
    }

}
