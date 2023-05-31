package niuniu.javaweb.utils;

import niuniu.javaweb.common.ApplicationContextHolder;
import niuniu.javaweb.config.FileConfig;
import niuniu.javaweb.utils.result.CommonResult;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.utils
 * @date 2023/2/10 17:47
 */
public class FileUtil {
    private static FileConfig fileConfig = ApplicationContextHolder.getContext().getBean(FileConfig.class);

    // 下划线
    public static final String UNDER_LINE = "_";

    // 上传文件
    public static CommonResult uploadFile(MultipartFile file) {
        // 1.获取一个新的文件名
        String newFileName = getNewFileName(file);
        if (StringUtils.isEmpty(newFileName)) {
            System.out.println("【上传文件】转换文件名称失败");
            return CommonResult.failed("【上传文件】转换文件名称失败");
        }
        // 2.获取文件上传路径
        String uploadPath = fileConfig.getUploadPath();
        if (StringUtils.isEmpty(uploadPath)) {
            System.out.println("【上传文件】获取文件上传路径失败");
            return CommonResult.failed("【上传文件】获取文件上传路径失败");
        }
        uploadPath = uploadPath + File.separator + "Cache";
        // 3.生成上传目录
        File uploadDir = mkdirs(uploadPath);
        if (!uploadDir.exists()) {
            System.out.println("【上传文件】生成上传目录失败");
            return CommonResult.failed("【上传文件】生成上传目录失败");
        }
        // 4.文件全路径
        String fileFullPath = uploadPath + File.separator + newFileName;
        System.out.println("上传的文件：" + file.getName() + "，" + file.getContentType() + "，保存的路径为：" + fileFullPath);

        try {
            // 5.上传文件
            doUploadFile(file, fileFullPath);
        } catch (IOException e) {
            System.out.println("【上传文件】上传文件报IO异常，异常信息为:" + e.getMessage());

            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(fileFullPath);
    }

    public static String uploadAvatar(MultipartFile file, String username) {
        // 2.获取文件上传路径
        String uploadPath = fileConfig.getUploadPath();
        if (StringUtils.isEmpty(uploadPath)) {
            System.out.println("【上传文件】获取文件上传路径失败");
            return "";
        }
        uploadPath = uploadPath + File.separator + "Avatar";
        // 3.生成上传目录

        File uploadDir = mkdirs(uploadPath);
        if (!uploadDir.exists()) {
            System.out.println("【上传文件】生成上传目录失败");
            return "";
        }
        // 4.文件全路径
        String savePath = username + UUID.randomUUID() + ".jpeg";
        String fileFullPath = uploadPath + File.separator + savePath;

        System.out.println("上传的文件：" + file.getName() + "，" + file.getContentType() + "，保存的路径为：" + fileFullPath);
        ;
        try {
            // 5.上传文件
            doUploadFile(file, fileFullPath);
        } catch (IOException e) {
            System.out.println("【上传文件】上传文件报IO异常，异常信息为:" + e.getMessage());
            ;
            return "";
        }
        return savePath;
    }

    public static String uploadIDCard(MultipartFile file, String username, int flag) {
        // 2.获取文件上传路径
        String uploadPath = fileConfig.getUploadPath();
        if (StringUtils.isEmpty(uploadPath)) {
            System.out.println("【上传文件】获取文件上传路径失败");
            return "";
        }
        uploadPath = uploadPath + File.separator + "IDCard";
        // 3.生成上传目录

        File uploadDir = mkdirs(uploadPath);
        if (!uploadDir.exists()) {
            System.out.println("【上传文件】生成上传目录失败");
            return "";
        }
        // 4.文件全路径
        String fileFullPath = uploadPath + File.separator + username + "_" + flag + ".png";
        System.out.println("上传的文件：" + file.getName() + "，" + file.getContentType() + "，保存的路径为：" + fileFullPath);
        ;
        try {
            // 5.上传文件
            doUploadFile(file, fileFullPath);
        } catch (IOException e) {
            System.out.println("【上传文件】上传文件报IO异常，异常信息为:" + e.getMessage());
            ;
            return "";
        }
        return fileFullPath;
    }

    // 将上传的文件转换为一个新的文件名
    public static String getNewFileName(MultipartFile file) {
        // 1.获取上传的文件名称（包含后缀。如：test.jpg）
        String originalFilename = file.getOriginalFilename();
        System.out.println("【上传文件】上传的文件名为" + originalFilename);
        ;
        // 2.以小数点进行分割
        String[] split = originalFilename.split("\\.");
        String newFileName = null;
        if (null == split || split.length == 0) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        if (1 == split.length) {
            // 3.此文件无后缀
            newFileName = builder.append(originalFilename).append(UNDER_LINE).append(System.nanoTime()).toString();
            return newFileName;
        }
        // 4.获取文件的后缀
        String fileSuffix = split[split.length - 1];
        for (int i = 0; i < split.length - 1; i++) {
            builder.append(split[i]);
            if (null != split[i + 1] && "" != split[i + 1]) {
                builder.append(UNDER_LINE);
            }
        }
        newFileName = builder.append(System.nanoTime()).append(".").append(fileSuffix).toString();
        return newFileName;
    }

    // 生成相应的目录
    public static File mkdirs(String path) {
        File file = new File(path);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
        return file;
    }

    // 上传文件
    public static void doUploadFile(MultipartFile file, String path) throws IOException {
        IOUtils.copy(file.getInputStream(), new FileOutputStream(path));
    }

    //设置下载请求头
    public static void setResponse(File file, HttpServletResponse response) throws UnsupportedEncodingException {
        // 清空response
        response.reset();
        response.setCharacterEncoding("UTF-8");
        // 返回给客户端类型，任意类型
        response.setContentType("application/octet-stream");
        // Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
        // attachment表示以附件方式下载 inline表示在线打开 "Content-Disposition: inline; filename=文件名.mp3"
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
        // 告知浏览器文件的大小
        response.addHeader("Content-Length", String.valueOf(file.length()));
    }

    //下载文件
    public static CommonResult downloadFile(File file, HttpServletResponse response) {
        try {
            // 1.设置响应头
            setResponse(file, response);
        } catch (UnsupportedEncodingException e) {
            System.out.println("文件名{}不支持转换为字符集:" + file.getName() + "UTF-8");
            ;
            return CommonResult.failed(e.getMessage());
        }
        // 2.下载文件
        return doDownLoadFile(file, response);
    }

    //    数据解析
    public static CommonResult doDownLoadFile(File file, HttpServletResponse response) {
        // 将输入流中的数据循环写入到响应输出流中，而不是一次性读取到内存，通过响应输出流输出到前端
        try (InputStream in = new FileInputStream(file);
             OutputStream out = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            System.out.println("【文件下载】文件下载成功");
            ;
            return null;
        } catch (FileNotFoundException e) {
            System.out.println("【文件下载】下载文件时，没有找到相应的文件，文件路径为:" + file.getAbsolutePath());
            return CommonResult.failed(e.getMessage());
        } catch (IOException e) {
            System.out.println("【文件下载】下载文件时，出现文件IO异常");
            return CommonResult.failed(e.getMessage());
        }
    }

    // 递归删除目录下的所有文件及子目录下所有文件
    public static boolean deleteFile(File file) {
        if (!file.exists()) {
            return false;
        }
        if (file.isDirectory()) {
            String[] children = file.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteFile(new File(file, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return file.delete();
    }

    //用以模糊删除头部为str的文件
    public static boolean deleteFile(String path, String str) throws NullPointerException {
        boolean flag = false;
        System.out.println(path);
        File file = new File(path);
        File[] tempFile = file.listFiles();
        for (int i = 0; i < tempFile.length; i++) {
            if (tempFile[i].getName().startsWith(str) || tempFile[i].getName().endsWith(str)) {
                tempFile[i].delete();
                flag = true;
            }
        }
        return flag;

    }

    public static String getDownLoadPath() {
        return fileConfig.getDownloadPath();
    }
}
