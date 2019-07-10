package com.dc.base.util;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author 黄继升 16041321
 * @Description: 时间类操作工具类
 * @date Created in 2019/7/7 14:38
 * @Modified By:
 */

public class UploaderFileUtil {
    /**
     * @return void
     * @title:<h3> 下载文件 <h3>
     * @author: 黄继升 16041321
     * @date: 2018-11-21 9:41
     * @params [response, inputStream, fileName：输出的文件名]
     **/
    public static void downFile(HttpServletResponse response, InputStream inputStream, String fileName) throws Exception {
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition",
                "attachment;filename=" + new String(fileName.getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(inputStream);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int size = 0;
            while ((size = bis.read(buff)) > -1) {//// 循环读取
                bos.write(buff, 0, size);
            }
        } catch (final Exception e) {
            throw e;
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
    /**
     * <h3>下载文件</h3>
     *
     * @param file
     * @param fileName
     * @throws IOException
     * @author 黄继升 16041321
     * @date 2018年1月25日 上午9:05:10
     */
    public static void downFile(HttpServletResponse response, File file, String fileName) throws Exception {
        InputStream in = new FileInputStream(file);
        downFile(response, in, fileName);
    }
    /**
     * @return void
     * @title:<h3> 根据绝对路径下载文件下载文件<h3>
     * @author: 黄继升 16041321
     * @date: 2018-8-28 10:27
     * @params [response, filePath, fileName]
     **/
    public static void downFile(HttpServletResponse response, String filePath, String fileName) throws Exception {
        InputStream in = new FileInputStream(filePath);
        downFile(response, in, fileName);
    }
}
