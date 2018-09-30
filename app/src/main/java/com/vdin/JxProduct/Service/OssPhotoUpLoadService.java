package com.vdin.JxProduct.Service;

import com.vdin.JxProduct.API.MetaDataApiRequest;
import com.vdin.JxProduct.API.NetWorkCallBack;
import com.vdin.JxProduct.API.WorkApiRequest;
import com.vdin.JxProduct.Model.WorkRegistAddPicInfo;
import com.vdin.JxProduct.Util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @开发者 YanSY
 * @日期 2018/9/20
 * @描述 Vdin成都研发部
 */
public class OssPhotoUpLoadService {

    public static void upLoadPhoto(final ArrayList<WorkRegistAddPicInfo> photos,NetWorkCallBack callBack) {

        Runnable runnable = () -> {

            // 创建线程池 最多支持9个子线程
            ExecutorService exec = Executors.newFixedThreadPool(9);
            // 线程计数
            final CountDownLatch mCountDownLatch = new CountDownLatch(photos.size());
            // 存储上传错误图片列表
            ArrayList<Integer> failPics = new ArrayList<>();

            //有图片上传图片
            if (photos.size() > 0) {
                //上传图片
                for (int i = 0; i < photos.size(); i++) {
                    // 记录位置
                    int position = i;
                    // 创建图片上传的task
                    Runnable upTask = new Runnable() {
                        @Override
                        public void run() {
                            // 获取图片信息
                            WorkRegistAddPicInfo picInfo = photos.get(position);
                            //判断图片是否已经上传
                            if (StringUtils.isEmpty(picInfo.url)) {
                                //如果为空, 上传图片
                                WorkApiRequest.upLoadPhoto(picInfo.path, position, (isSuccess, object) -> {
                                    // 获取返回信息
                                    HashMap<String, Object> map = (HashMap<String, Object>) object;
                                    // 位置
                                    int index = (int) map.get("index");
                                    // 是否成功
                                    if (isSuccess) {

                                        String url = (String) map.get("netUrl");
                                        photos.get(index).url = url;

                                    } else {
                                        failPics.add(index);
                                    }

                                    mCountDownLatch.countDown();

                                });

                            } else {

                                mCountDownLatch.countDown();
                            }
                        }
                    };
                    exec.submit(upTask);
                }
            }

            try {
                mCountDownLatch.await();
                // 关闭线程池
                exec.shutdown();

                if (failPics.size() > 0) {
                    callBack.completeBlock(false,photos);
                } else {
                    callBack.completeBlock(true,photos);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        };
        new Thread(runnable).start();
    }

}

/*


//            //上传现场照片
//            pool.submit(() -> {
//                //判断是否已经上传现场照片
//                if (photoPath.startsWith("http://") || photoPath.startsWith("https://")) {
//                    latch.countDown();
//                } else {
//
//                    WorkNetworkApi.upload(photoPath, -1, getFilename(), (isSuccess, object) -> {
//                        Map<String, Object> map = (Map<String, Object>) object;
//                        int index = (int) map.get("index");
//                        if (isSuccess) {
//                            String url = (String) map.get("url");
//                            //删除图片地址
//                            FileUtils.delFileByLocalPath(photoPath);
//                            photoPath = url;
//                        } else {
//                            failPics.add(index);
//                        }
//                        latch.countDown();
//                    });
//                }
//            });

        Callable<WorkRegistAddPicInfo> callable = new Callable<WorkRegistAddPicInfo>() {
            @Override
            public WorkRegistAddPicInfo call() throws Exception {

                // 计时等待，线程为1
//                CountDownLatch mCountDownLatch = new CountDownLatch(1);

                WorkRegistAddPicInfo uploadPhoto = new WorkRegistAddPicInfo();
                uploadPhoto.uploadState = photo.State_In;
                uploadPhoto.bitmap = photo.bitmap;
                uploadPhoto.path = photo.path;
                uploadPhoto.url = photo.url;

                UploadFileOSS.upladFileOSS(mContext, uploadPhoto.path, index, new OnUploadOssCallbackListener() {
                    @Override
                    public void uploadFail(int index, String msg, String filePath) {
                        uploadPhoto.uploadState = State_No;
                        Log.d(this.toString(), "uploadPercent: "+"图片上传失败");
                        Log.d(this.toString(), "uploadPercent: "+index);
                        Log.d(this.toString(), "uploadPercent: "+msg);
                        Log.d(this.toString(), "uploadPercent: "+filePath);
//                        mCountDownLatch.countDown();
                    }

                    @Override
                    public void uploadSuccessBackData(int index, String netUrl, String filePath) {
                        uploadPhoto.uploadState = State_Success;
                        uploadPhoto.url = netUrl;
                        Log.d(this.toString(), "uploadPercent: "+"图片上传成功");
                        Log.d(this.toString(), "uploadPercent: "+index);
                        Log.d(this.toString(), "uploadPercent: "+netUrl);
                        Log.d(this.toString(), "uploadPercent: "+filePath);
//                        mCountDownLatch.countDown();

                    }

                    @Override
                    public void uploadPercent(int percent) {
                        Log.d(this.toString(), "uploadPercent: "+percent);
                    }
                });

//                mCountDownLatch.await();
                return uploadPhoto;
            }
        };

        FutureTask<WorkRegistAddPicInfo> task = new FutureTask<>(callable);
        Thread thread = new Thread(task);
        thread.start();
        Log.d("YanSYDebug", "upLoadPhoto: "+"正在等待");

        try {
            WorkRegistAddPicInfo aaa = task.get();
            Log.d("YanSYDebug", "upLoadPhoto: "+aaa.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }


  // 创建图片上传的进程
    static class upPhotoThread extends Thread {

        private Context MyContext;
        private CountDownLatch countDownLatch;
        private WorkRegistAddPicInfo picInfo;
        private int index;

        public upPhotoThread (Context context,CountDownLatch latch,WorkRegistAddPicInfo info,int index){
            this.MyContext = context;
            this.countDownLatch = latch;
            this.picInfo = info;
            this.index = index;
        }

        @Override
        public void run() {

            UploadFileOSS.upladFileOSS(MyContext, picInfo.path, index, new OnUploadOssCallbackListener() {
                @Override
                public void uploadFail(int index, String msg, String filePath) {
                    picInfo.uploadState = State_No;
                    Log.d(this.toString(), "uploadPercent: "+"图片上传失败");
                    Log.d(this.toString(), "uploadPercent: "+index);
                    Log.d(this.toString(), "uploadPercent: "+msg);
                    Log.d(this.toString(), "uploadPercent: "+filePath);
                    countDownLatch.countDown();
                }

                @Override
                public void uploadSuccessBackData(int index, String netUrl, String filePath) {
                    picInfo.uploadState = State_Success;
                    picInfo.url = netUrl;
                    Log.d(this.toString(), "uploadPercent: "+"图片上传成功");
                    Log.d(this.toString(), "uploadPercent: "+index);
                    Log.d(this.toString(), "uploadPercent: "+netUrl);
                    Log.d(this.toString(), "uploadPercent: "+filePath);
                    countDownLatch.countDown();

                }

                @Override
                public void uploadPercent(int percent) {
                    Log.d(this.toString(), "uploadPercent: "+percent);
                }
            });

            super.run();
        }


    public WorkRegistAddPicInfo getPicInfo() {
    return picInfo;
    }


    public int getIndex() {
        return index;
    }

}

//        // 计时等待，线程为1
//        CountDownLatch mCountDownLatch = new CountDownLatch(1);
//        photo.uploadState = State_In;
//        // 上传单个图片
//        upPhotoThread thread = new upPhotoThread(mContext,mCountDownLatch,photo,index);
//        thread.start();
//
//        try {
//            mCountDownLatch.await();
//            if (thread.getPicInfo().uploadState == State_Success){
//                return photo;
//            }else {
//                Log.d(mContext.toString(), "uploadPercent: "+ "上传出现未知错误");
//            }
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }




 */






















