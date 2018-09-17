package com.vdin.JxProduct.OSSService;

import android.app.Activity;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hurong on 2017/3/29.
 */

public class FileUtils {

    public static String rootPath = Environment.getExternalStorageDirectory() + "/ls/";
    public static String PIC_TEMP_DIR = FileUtils.rootPath + "temp/";
    public static String crashPath = FileUtils.rootPath + "crashPath/";

    static {
        File file = new File(PIC_TEMP_DIR);

        if (!file.exists())
            file.mkdirs();
    }

    /**
     * 判断sd卡是否存在
     *
     * @return
     */
    public static boolean isSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    public static String getLocalLoadUrl(String url) {
        return "file://" + url;
    }

    /**
     * 删除整个目录
     *
     * @param file
     */
    public static void deleteDir(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            Log.d("CacheFile", "delete-->[" + file.length() + "]" + file.getPath());
            file.delete();
            return;
        }
        File[] lists = file.listFiles();
        for (File f : lists) {
            deleteDir(f);
        }
    }
    /**
     * 删除文件夹
     *
     * @param file
     */
    public static boolean deleteFile(File file) {
        if (file.exists() == false) {
            return false;
        } else {
            deleteDirWihtFile(file);
            return true;
        }
    }

    public static void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirWihtFile(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }
    /**
     * 删除文件夹
     *
     * @param file
     */
    public static void delFileDir(File file) {
        if (!file.exists()) {
            return;
        }
        try {
            file.delete();
        } catch (Exception e) {
        }
    }

    /**
     * 删除文件
     *
     * @param loaclPath 本地文件存储路径
     */
    public static void delFileByLocalPath(String loaclPath) {
        if (TextUtils.isEmpty(loaclPath)) {
            return;
        }
        delFileDir(new File(loaclPath));
    }

    /**
     * 生成video文件名字
     */
    public static File generateVideoName(String videoDir) {
        //用系统当前时间命名
        String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".3gp";
        //创建文件夹
        File out = new File(videoDir);
        if (!out.exists()) {
            out.mkdirs();
        }
        return new File(videoDir + "VIDEO_" + fileName);
    }

    /**
     * 获取目录文件大小
     *
     * @param dir
     * @return
     */
    public static long getDirSize(File dir) {
        if (null == dir || !dir.exists()) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                Log.d("CacheFile", "[" + file.length() + "]" + file.getPath());
                dirSize += file.length();
            } else if (file.isDirectory()) {
                dirSize += getDirSize(file); // 递归调用继续统计
            }
        }
        return dirSize;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return B/KB/MB/GB
     */
    public static String formatFileSize(long fileS) {
        if (fileS == 0) {
            return "0.00 B";
        }

        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + " B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + " KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + " MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + " G";
        }
        return fileSizeString;
    }


    public static String getPicTempOnExternal(Activity activity){

        try {
            File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
            return image.getAbsolutePath();
        } catch (IOException e) {
            return FileUtils.PIC_TEMP_DIR + File.separator + System.currentTimeMillis() + ".jpg";
        }
    }
}
