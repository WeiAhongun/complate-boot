package com.shangma.cn.common.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.shangma.cn.common.http.AxiosStatus;
import com.shangma.cn.exception.UplaodException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@PropertySource("classpath:aliyun.properties")
public class UploadService {
    @Value("${aliyun_key}")
    private  String aliyun_key ;
    @Value("${aliyun_secret}")
    private  String aliyun_secret ;
    @Value("${aliyun_endpoint}")
    private  String aliyun_endpoint;
    @Value("${aliyun_bucketName}")
    private  String aliyun_bucketName;
    @Value("${aliyun_baseUrl}")
    private  String aliyun_baseUrl ;
    @Value("${file_ext}")
    private List<String> extList ;
    @Value("${max_size}")
    private int maxSize ;

    public String upload(InputStream is, String fileName) {
        //验证图片
        try {
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            BufferedImage read = ImageIO.read(new ByteArrayInputStream(bytes));
            if (read != null){
                //判断后缀名
                if(extList.contains(StringUtils.getFilenameExtension(fileName))){
                    //判断文件大小
                    if(bytes.length/1024 < maxSize){
                        OSS ossClient = new OSSClientBuilder().build(aliyun_endpoint, aliyun_key, aliyun_secret);
                        ossClient.putObject(aliyun_bucketName, fileName, new ByteArrayInputStream(bytes));
                        ossClient.shutdown();
                        String url = aliyun_baseUrl+fileName;
                        return url;
                    }else {
                        //文件过大
                        throw new UplaodException(AxiosStatus.IMG_TOMAX);
                    }
                }else {
                    //文件后缀名错误
                    throw new UplaodException(AxiosStatus.IMG_ERROR);
                }
            }else {
                //不是一个图片
                throw new UplaodException(AxiosStatus.IMG_ERROR);
            }
        } catch (IOException e) {
            throw new UplaodException(AxiosStatus.IMG_ERROR);
        }
    }
}
