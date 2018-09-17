package com.vdin.JxProduct.OSSService;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * 图片工具类
 *
 * @author blue
 */
public class BitmapUtil {

    /**
     * Constants
     */
    private static final String TAG = "BitmapUtil";
    private static final String CONTENT = "content";
    private static final String FILE = "file";

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 压缩图片
     *
     * @param filePath
     * @return
     */
    public static Bitmap compressFile(Context context, String filePath, float width, float height) {
        Log.d(TAG, "compressFile() called with: " + "context = [" + context + "], filePath = [" + filePath + "], width = [" + width + "], height = [" + height + "]");
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, opts);
        float scale_h = (float) opts.outHeight / height;
        float scale_w = (float) opts.outWidth / width;
        Log.d(TAG, "compressFile: scale_h = " + scale_h);
        Log.d(TAG, "compressFile: scale_w = " + scale_w);
        float scale = scale_h > scale_w ? scale_w : scale_h;
        if (scale < 1) {
            scale = 1f;
        } else if (scale < 2) {
            scale = 2f;
        } else if (scale < 4) {
            scale = 4f;
        } else if (scale < 8) {
            scale = 8f;
        } else {
            scale = 16f;
        }
        Log.d(TAG, "compressFile: scale = " + scale);
        opts.inSampleSize = (int) scale;
        opts.inJustDecodeBounds = false;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, opts);
        Log.d(TAG, "compressFile: filePath = " + filePath);
        Log.d(TAG, "compressFile: getWidth = " + bmp.getWidth() + "====" + bmp.getHeight());
        return bmp;
    }
    /**
     * 獲取图片
     *
     * @param filePath
     * @return
     */
    public static Bitmap getBitMapFile(Context context, String filePath) {
       BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap bmp = BitmapFactory.decodeFile(filePath, opts);
        return bmp;
    }

    /**
     * 获取文件的路径
     */
    public static String getFilePath(Context context, Uri uri) {
        String filePath = null;
        if (CONTENT.equalsIgnoreCase(uri.getScheme())) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
            if (null == cursor) {
                return null;
            }
            try {
                if (cursor.moveToNext()) {
                    filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                }
            } finally {
                cursor.close();
            }
        }

        // 从文件中选择
        if (FILE.equalsIgnoreCase(uri.getScheme())) {
            filePath = uri.getPath();
        }
        return filePath;
    }

    /**
     * 根据指定比例缩放图片
     *
     * @param fileDescriptor
     * @param reqWidth
     * @return
     * @author blue
     */
    public static Bitmap decodeSampledBitmap(FileDescriptor fileDescriptor, int reqWidth, int reqHeight) {
        Log.d(TAG, "decodeSampledBitmap() called with: " + "fileDescriptor = [" + fileDescriptor + "], reqWidth = [" + reqWidth + "], reqHeight = [" + reqHeight + "]");
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
    }

    public static Bitmap decodeSampledBitmap(InputStream inputStream, int reqWidth, int reqHeight) throws FileNotFoundException {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(inputStream, null, options);
    }

    public static Bitmap decodeSampledBitmap(Resources resources, int resId, int reqWidth, int reqHeight) throws FileNotFoundException {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, resId, options);
    }

    /**
     * 保存Bitmap到SDCard
     *
     * @param bitmap
     * @param filePath 图片保存路径
     */
    public static boolean saveBitmap(Bitmap bitmap, String filePath) {
        boolean success;
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            success = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            success = false;
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    /**
     * 保存Bitmap到SDCard
     *
     * @param bitmap
     */
    public static boolean saveBitmap(Bitmap bitmap, String filePath, double avatarSize) {
        boolean success;
        bitmap = zoomImage(bitmap, avatarSize, avatarSize);
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            success = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            success = false;
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }


    /**
     * 将图片缩放到指定尺寸
     *
     * @param sourceBitmap 图片源
     * @param newWidth     新的宽度
     * @param newHeight    新的高度
     * @return
     */
    public static Bitmap zoomImage(Bitmap sourceBitmap, double newWidth, double newHeight) {
        // 获取这个图片的宽和高
        float width = sourceBitmap.getWidth();
        float height = sourceBitmap.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(sourceBitmap, 0, 0, (int) width, (int) height, matrix, true);
        return bitmap;
    }


    public static Bitmap replaceBitmapBackground(int color, Bitmap originBitmap) {
        Paint paint = new Paint();
        paint.setColor(color);
        Bitmap bitmap = Bitmap.createBitmap(originBitmap.getWidth(),
                originBitmap.getHeight(), originBitmap.getConfig());
        Canvas canvas = new Canvas(bitmap);
        canvas.drawRect(0, 0, originBitmap.getWidth(), originBitmap.getHeight(), paint);
        canvas.drawBitmap(originBitmap, 0, 0, paint);
        return bitmap;
    }

    public static Bitmap bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    public Bitmap stringtoBitmap(String string) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public String saveImgByString(String string){
        Bitmap bitmap = stringtoBitmap(string);
        return "";
    }

    public static byte[] getByteByBitmap(Bitmap bmp){
        int bytes = bmp.getByteCount();

        ByteBuffer buf = ByteBuffer.allocate(bytes);
        bmp.copyPixelsToBuffer(buf);
        return buf.array();
    }


}
