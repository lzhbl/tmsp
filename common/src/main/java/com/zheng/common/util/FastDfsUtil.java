package com.zheng.common.util;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: lx
 * @description: FastDfsUtil工具类
 * @author: Mr.Zhbn
 * @create: 2018-08-28 10:07:51
 **/
public class FastDfsUtil {
    public static Logger logger = LoggerFactory.getLogger(FastDfsUtil.class);

    /**
     * @Description: 初始化fastDfs
     * @Param:
     * @return:
     * @Author: Mr.Zhbn
     * @Date: 2018/08/28
     */
    public static Map<String, Object> init() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String classPath = new File(FastDfsUtil.class.getResource("/").getFile()).getCanonicalPath();
//            String configFilePath = classPath + File.separator + "fdfs_client.conf";
            String configFilePath = classPath + File.separator + "config.properties";
            logger.info("配置文件：" + configFilePath);
            // 初始化参数
            ClientGlobal.init(configFilePath);
            // 初始化跟踪器
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            // 初始化存储器
            StorageServer storageServer = null;
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            map.put("trackerClient", trackerClient);
            map.put("trackerServer", trackerServer);
            map.put("storageClient", storageClient);
            map.put("storageServer", storageServer);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @Description: 上传文件
     * @Param:
     * @return:
     * @Author: Mr.Zhbn
     * @Date: 2018/08/28
     */
    public static Map<String, Object> upload(String filePath) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            // 初始化fastDfs
            Map<String, Object> initMap = FastDfsUtil.init();
            TrackerClient trackerClient = (TrackerClient) initMap.get("trackerClient");
            TrackerServer trackerServer = (TrackerServer) initMap.get("trackerServer");
            StorageClient storageClient = (StorageClient) initMap.get("storageClient");
            StorageServer storageServer = (StorageServer) initMap.get("storageServer");
            // 初始化参数
            NameValuePair[] meta_list = new NameValuePair[3];

            File file = new File(filePath);
            String fileName = file.getName();
            //获取文件后缀名
            String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
            FileInputStream fis = new FileInputStream(file);
            byte[] file_buff = null;
            if (fis != null) {
                int len = fis.available();
                file_buff = new byte[len];
                fis.read(file_buff);
            }
            logger.info("文件长度: " + file_buff.length);

            meta_list[0] = new NameValuePair("fileName", fileName);
            meta_list[1] = new NameValuePair("fileExtName", prefix);
            meta_list[2] = new NameValuePair("fileLength", String.valueOf(file_buff.length));
            // 声明组
            String group_name = null;
            // 根据跟踪器和组查找存储器
            StorageServer[] storageServers = trackerClient.getStoreStorages(trackerServer, group_name);
            if (storageServers == null) {
                logger.info("无法找到存储器服务：" + storageClient.getErrorCode());
                map.put("resCode", storageClient.getErrorCode());
                map.put("resMsg", "无法找到存储器服务");
                return map;
            } else {
                logger.info("存储器数量: " + storageServers.length);
                for (int k = 0; k < storageServers.length; k++) {
                    logger.info("当前文件：" + k + 1 + ". " + "当前ip和端口" + storageServers[k].getInetSocketAddress().getAddress().getHostAddress() + ":" + storageServers[k].getInetSocketAddress().getPort());
                }
            }
            // 开始时间
            long startTime = System.currentTimeMillis();
            // 文件上传返回结果
            String[] results = storageClient.upload_file(file_buff, prefix, null);//meta_list
            logger.info("上传耗费时间: " + (System.currentTimeMillis() - startTime) + " ms");
            if (results == null) {
                logger.info("上传失败：" + storageClient.getErrorCode());
                map.put("resCode", storageClient.getErrorCode());
                map.put("resMsg", "上传失败");
                return map;
            }
            //返回结果赋值给组
            group_name = results[0];
            String remote_filename = results[1];
            logger.info("组名: " + group_name + ", 文件路径: " + remote_filename);
            logger.info(storageClient.get_file_info(group_name, remote_filename).toString());
            //根据跟踪器，组和文件路径查找跟踪服务器
            ServerInfo[] servers = trackerClient.getFetchStorages(trackerServer, group_name, remote_filename);
            if (servers == null) {
                logger.info("无法找到跟踪器服务：" + storageClient.getErrorCode());
                map.put("resCode", trackerClient.getErrorCode());
                map.put("resMsg", "无法找到跟踪器服务");
                return map;
            } else {
                logger.info("跟踪服务器数量: " + servers.length);
                for (int k = 0; k < servers.length; k++) {
                    logger.info("当前文件：" + k + 1 + ". " + "当前ip和端口" + servers[k].getIpAddr() + ":" + servers[k].getPort());
                }
            }
            map.put("resCode", "0000");//代表上传成功
            map.put("resMsg", group_name + ":" + remote_filename);
            logger.info("上传成功");
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("resCode", "9999");
            map.put("resMsg", "文件服务器连接异常");
        }
        return map;
    }


    /**
     * @Description: 上传文件, 流操作
     * @Param:
     * @return:
     * @Author: Mr.Zhbn
     * @Date: 2018/08/28
     */
    public static Map<String, Object> uploadFileByStream(InputStream inStream, String uploadFileName) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            // 初始化fastDfs
            Map<String, Object> initMap = FastDfsUtil.init();
            TrackerClient trackerClient = (TrackerClient) initMap.get("trackerClient");
            TrackerServer trackerServer = (TrackerServer) initMap.get("trackerServer");
            StorageClient storageClient = (StorageClient) initMap.get("storageClient");
            StorageServer storageServer = (StorageServer) initMap.get("storageServer");
            // 初始化参数
            NameValuePair[] meta_list = new NameValuePair[3];

            String fileName = uploadFileName;
            //获取文件后缀名
            String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
//            InputStream fis = inStream;
            byte[] file_buff = null;
            if (inStream != null) {
                int len = inStream.available();
                file_buff = new byte[len];
                inStream.read(file_buff);
            }
            logger.info("文件长度: " + file_buff.length);

            meta_list[0] = new NameValuePair("fileName", fileName);
            meta_list[1] = new NameValuePair("fileExtName", prefix);
            meta_list[2] = new NameValuePair("fileLength", String.valueOf(file_buff.length));


            // 声明组
            String group_name = null;
            // 根据跟踪器和组查找存储器
            StorageServer[] storageServers = trackerClient.getStoreStorages(trackerServer, group_name);
            if (storageServers == null) {
                map.put("resCode", storageClient.getErrorCode());
                map.put("resMsg", "无法找到存储器服务");
                logger.info("无法找到存储器服务：" + storageClient.getErrorCode());
                return map;
            } else {
                logger.info("存储器数量: " + storageServers.length);
                for (int k = 0; k < storageServers.length; k++) {
                    logger.info("当前文件：" + k + 1 + ". " + "当前ip和端口" + storageServers[k].getInetSocketAddress().getAddress().getHostAddress() + ":" + storageServers[k].getInetSocketAddress().getPort());
                }
            }
            // 开始时间
            long startTime = System.currentTimeMillis();
            // 文件上传返回结果
            String[] results = storageClient.upload_file(file_buff, prefix, null);//meta_list
            logger.info("上传耗费时间: " + (System.currentTimeMillis() - startTime) + " ms");
            if (results == null) {
                map.put("resCode", storageClient.getErrorCode());
                map.put("resMsg", "上传失败");
                logger.info("上传失败：" + storageClient.getErrorCode());
                return map;
            }
            //返回结果赋值给组
            group_name = results[0];
            String remote_filename = results[1];
            logger.info("组名: " + group_name + ", 文件路径: " + remote_filename);
            logger.info(storageClient.get_file_info(group_name, remote_filename).toString());
            //根据跟踪器，组和文件路径查找跟踪服务器
            ServerInfo[] servers = trackerClient.getFetchStorages(trackerServer, group_name, remote_filename);
            if (servers == null) {
                map.put("resCode", trackerClient.getErrorCode());
                map.put("resMsg", "无法找到跟踪器服务");
                logger.info("无法找到跟踪器服务：" + storageClient.getErrorCode());
                return map;
            } else {
                logger.info("跟踪服务器数量: " + servers.length);
                for (int k = 0; k < servers.length; k++) {
                    logger.info("当前文件：" + k + 1 + ". " + "当前ip和端口" + servers[k].getIpAddr() + ":" + servers[k].getPort());
                }
            }
            map.put("resCode", "0000");//代表上传成功
            map.put("resMsg", group_name + ":" + remote_filename);
            logger.info("上传成功");
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("resCode", "9999");
            map.put("resMsg", "文件服务器连接异常");
        }
        return map;
    }


    /**
     * @Description: 下载文件
     * @Param:
     * @return:
     * @Author: Mr.Zhbn
     * @Date: 2018/08/28
     */
    public static Map<String, Object> download(String group_name, String remote_filename) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            // 初始化fastDfs
            Map<String, Object> initMap = FastDfsUtil.init();
            StorageClient storageClient = (StorageClient) initMap.get("storageClient");
            FileInfo fi = storageClient.get_file_info(group_name, remote_filename);
//            byte[] b = storageClient.download_file(group_name, remote_filename);
            String sourceIpAddr = fi.getSourceIpAddr();
            long size = fi.getFileSize();
            logger.info("ip地址:" + sourceIpAddr + ",文件大小:" + size);
            map.put("resCode", "0000");//代表下载成功
            map.put("resMsg", remote_filename);
            logger.info("下载成功");
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void downFile(String groupandfilename, String fileName, HttpServletResponse response) throws IOException {
        try {
            String[] str = groupandfilename.split(":");
            String group_name = str[0];
            String remote_filename = str[1];

            response.setContentType("text/html; charset=UTF-8");
            // 设置this.getResponse()的编码方式
            response.setContentType("application/x-msdownload");
            // 解决中文乱码问题
            fileName = new String(fileName.getBytes("GBK"), "ISO8859_1");
            // 解决中文乱码
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            // 初始化fastDfs
            Map<String, Object> initMap = FastDfsUtil.init();
            StorageClient storageClient = (StorageClient) initMap.get("storageClient");
            byte[] content = storageClient.download_file(group_name, remote_filename);//得到文件的字节数组
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(content); // 输出数据
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 删除文件
     * @Param: fileId/group filePath
     * @return:删除失败返回-1，否则返回0
     * @Author: Mr.Zhbn
     * @Date: 2018/08/28
     */
    public static int deleteFile(String groupandfilename) {
        try {
            String[] str = groupandfilename.split(":");
            String group = str[0];
            String filePath = str[1];
            // 初始化fastDfs
            Map<String, Object> initMap = FastDfsUtil.init();
            StorageClient storageClient = (StorageClient) initMap.get("storageClient");
            int i = storageClient.delete_file(group, filePath);
            logger.info("组名:" + group + ",文件:" + filePath + "  " + (i == 0 ? "删除成功" : "删除失败:" + i));
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static File getFileInfo(String groupandfilename) {
        String[] str = groupandfilename.split(":");
        String groupname = str[0];
        String remote_filename = str[1];
//        String fileName  = FileTool.getFileRoot() +File.separator+ remote_filename + ".db";
        String fpath = remote_filename.trim();
        String fName = fpath.substring(fpath.lastIndexOf("/") + 1);
        fpath = fpath.substring(0, fpath.lastIndexOf("/") + 1);
        String fileName = "";//FileTool.getFileRoot() + File.separator + fpath;
        File filep = new File(fileName);
        //如果文件夹不存在则创建
        if (!filep.exists() && !filep.isDirectory()) {
            logger.info(fileName + "//不存在");
            filep.mkdirs();
        } else {
            logger.info(fileName + "//目录存在");
        }
        File file = new File(fileName + File.separator + fName);
        try {
            // 初始化fastDfs
            Map<String, Object> initMap = FastDfsUtil.init();
            StorageClient storageClient = (StorageClient) initMap.get("storageClient");
//            FileInfo fi = storageClient.get_file_info(groupname, remote_filename);
//            logger.info(fi.getSourceIpAddr());
//            logger.info(fi.getFileSize() + "");
//            logger.info(fi.getCreateTimestamp() + "");
//            logger.info(fi.getCrc32() + "");
            OutputStream output = null;
            byte[] content = storageClient.download_file(groupname, remote_filename);//得到文件的字节数组
            output = new FileOutputStream(file);
            BufferedOutputStream bufferedOutput = new BufferedOutputStream(output);
            bufferedOutput.write(content);
            bufferedOutput.flush();
            bufferedOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * @Description:测试
     * @Param:
     * @return:
     * @Author: Mr.Zhbn
     * @Date: 2018/08/28
     */
    public static void main(String[] args) {
        // 上传
        Map<String, Object>  map = FastDfsUtil.upload("D:\\20160501224806_0G1433_7120444_7-3646-1.mp4");
        logger.info(map.get("resCode").toString());
        logger.info(map.get("resMsg").toString());
        // 下载
//        FastDfsUtil.download("group1", "M00/00/00/rBAmH1uE2GiAAhryAAJEqPxpQJs950.jpg");
        // 删除
//        FastDfsUtil.deleteFile("group1", "M00/00/00/rBAmH1uEvSaAZNRIAAJEqPxpQJs614.jpg");
        // 查看
//        FastDfsUtil.getFileInfo("group1:M00/00/00/rBAmH1uF9qyAbFFTAAEIAJVnWXw0734.db");

    }
}




