package com.atguigu.srb.oss.service;

import java.io.InputStream;

/**
 * @author: baifengxiao
 * @date: 2022/4/7 18:23
 */
public interface FileService {
    String upload(InputStream inputStream,String module,String fileName);
}
