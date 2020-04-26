package com.lanjy.blog.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.config
 * @类描述：
 * @创建人：lanjy
 * @创建时间：2020/3/22
 */
@Data
@Component
@Configuration
@PropertySource({"classpath:application.yml"})
public class FtpConfig {
    //ftp服务器ip地址
    @Value("${ftp.ip}")
    private String ip;
    //端口号
    @Value("${ftp.port}")
    private int    port;
    //用户名
    @Value("${ftp.ftpuser}")
    private String ftpuser;
    //密码
    @Value("${ftp.password}")
    private String password;
    //图片路径
    @Value("${ftp.basepath}")
    private String basepath;

}
