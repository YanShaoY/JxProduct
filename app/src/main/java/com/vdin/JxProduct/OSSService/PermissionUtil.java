package com.vdin.JxProduct.OSSService;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 */

public class PermissionUtil {

    public static final int REQUEST_CODE_STORAGE = 0x001;
    public static final int REQUEST_CODE_RECORD_AUDIO = 0x002;
    public static final int REQUEST_CODE_CAMERA = 0x003;
    public static final int REQUEST_CODE_PHONE = 0x004;
    public static final int REQUEST_CODE_CONTACTS = 0x005;
    public static final int REQUEST_CODE_CALENDAR = 0x006;
    public static final int REQUEST_CODE_LOCATION = 0x007;
    public static final int REQUEST_CODE_SMS = 0x008;
    public static final int REQUEST_CODE_APP_NEED = 0x009;   //拍照所需权限
    public static final int REQUEST_CODE_GETUI_NEED = 0x010;  //注册个推所需权限

    public static final int REQUEST_CODE_LOCATION_NEED = 0x011;  //定位权限
    public static final int REQUEST_CALL_PHONE = 0x012;  //打电话

    private static boolean checkPermissions(Activity activity, String permission, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
                return false;
            }
        }
        return true;
    }

    private static boolean checkPermissions(Fragment activity, String permission, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity.getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{permission}, requestCode);
                return false;
            }
        }
        return true;
    }

    /**
     * 申请需要的所有的权限
     * @param activity
     * @return
     */
    public static boolean grantNeedPermission(Activity activity) {
        List<String> permissionList = new ArrayList<String>();
        int i = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (i != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        int i1 = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);
        if (i1 != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        int i2 = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        if (i2 != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.CAMERA);
        }
        int i3 = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO);
        if (i3 != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.RECORD_AUDIO);
        }
        if (permissionList.size() == 0) {
            // 已授权
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 如果是6.0及以后,则申请权限
            try {
                ActivityCompat.requestPermissions(activity, permissionList.toArray(new String[permissionList.size()]), REQUEST_CODE_APP_NEED);
                return false;
            } catch (Exception e) {
                return true;
            }
        } else {
            // 提示未授权
            return true;
        }
    }

    public static boolean isGranted(@NonNull int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 定位权限
     */
    public static boolean checkLocation(Activity activity) {
        return checkPermissions(activity, Manifest.permission.ACCESS_COARSE_LOCATION, REQUEST_CODE_LOCATION);
    }

    /**
     * 定位权限
     */
    public static boolean checkLocation(Fragment activity) {
        return checkPermissions(activity, Manifest.permission.ACCESS_COARSE_LOCATION, REQUEST_CODE_LOCATION);
    }

    /**
     * 短信权限
     */
    public static boolean checkSms(Activity activity) {
        return checkPermissions(activity, Manifest.permission.READ_SMS, REQUEST_CODE_SMS);
    }

    /**
     * 读取日历信息
     */
    public static boolean checkCalendar(Activity activity) {
        return checkPermissions(activity, Manifest.permission.READ_CALENDAR, REQUEST_CODE_CALENDAR);
    }

    /**
     * 读取联系人
     */
    public static boolean checkContacts(Activity activity) {
        return checkPermissions(activity, Manifest.permission.READ_CONTACTS, REQUEST_CODE_CONTACTS);
    }

    /**
     * 手机状态权限
     */
    public static boolean checkPhone(Activity activity) {
        return checkPermissions(activity, Manifest.permission.READ_PHONE_STATE, REQUEST_CODE_PHONE);
    }

    /**
     * 手机状态权限
     */
    public static boolean checkCellPhone(Activity activity) {
        return checkPermissions(activity, Manifest.permission.CALL_PHONE, REQUEST_CALL_PHONE);
    }

    /**
     * 定位权限
     */
    public static boolean checkLocationPermission(Activity activity) {
        List<String> permissionList = new ArrayList<String>();
        int i = ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
        if (i != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        int i1 = ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        if (i1 != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (permissionList.size() == 0) {
            // 已授权
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 如果是6.0及以后,则申请权限
            try {
                activity.requestPermissions( permissionList.toArray(new String[permissionList.size()]), REQUEST_CODE_LOCATION_NEED);
                return false;
            } catch (Exception e) {
                return true;
            }
        } else {
            // 提示未授权
            return true;
        }
    }

    /**
     * 相机权限
     */
    public static boolean checkCameraPermission(Activity activity) {
        List<String> permissionList = new ArrayList<String>();
        int i = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (i != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        int i1 = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        if (i1 != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.CAMERA);
        }
        if (permissionList.size() == 0) {
            // 已授权
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 如果是6.0及以后,则申请权限
            try {
                ActivityCompat.requestPermissions(activity, permissionList.toArray(new String[permissionList.size()]), REQUEST_CODE_CAMERA);
                return false;
            } catch (Exception e) {
                return true;
            }
        } else {
            // 提示未授权
            return true;
        }
    }

    /**
     * Fragment相机权限
     */
    public static boolean checkCameraPermission(Fragment activity) {
        List<String> permissionList = new ArrayList<String>();
        int i = ContextCompat.checkSelfPermission(activity.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (i != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        int i1 = ContextCompat.checkSelfPermission(activity.getContext(), Manifest.permission.CAMERA);
        if (i1 != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.CAMERA);
        }
        if (permissionList.size() == 0) {
            // 已授权
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 如果是6.0及以后,则申请权限
            try {
                activity.requestPermissions(permissionList.toArray(new String[permissionList.size()]), REQUEST_CODE_CAMERA);
                return false;
            } catch (Exception e) {
                return true;
            }
        } else {
            // 提示未授权
            return true;
        }
    }

    /**
     * 外部存储权限
     */
    public static boolean checkExternalStorage(Activity activity) {
        return checkPermissions(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_CODE_STORAGE);
    }

    /**
     * fragment外部存储权限
     */
    public static boolean checkExternalStorage(Fragment activity) {
        return checkPermissions(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_CODE_STORAGE);
    }


    /**
     * 检查录音权限
     */
    public static boolean checkAudioPermission(Activity activity) {
        return checkPermissions(activity, Manifest.permission.RECORD_AUDIO, REQUEST_CODE_RECORD_AUDIO);
    }

    /**
     * 检查个推注册权限
     *
     * @param activity
     */
    public static boolean checkGetuiPermission(Activity activity) {
        List<String> permissionList = new ArrayList<String>();
        int i = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (i != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        int i1 = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);
        if (i1 != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (permissionList.size() == 0) {
            // 已授权
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 如果是6.0及以后,则申请权限
            try {
                ActivityCompat.requestPermissions(activity, permissionList.toArray(new String[permissionList.size()]), REQUEST_CODE_GETUI_NEED);
                return false;
            } catch (Exception e) {
                return true;
            }
        } else {
            // 提示未授权
            return true;
        }
    }

    /**
     * 检查授权是否成功
     * @param grantResults
     * @return
     */
    public static boolean checkGrantIsSuccess(int[] grantResults){
        boolean isSuccess = true;
        for (int i:grantResults){
            if(i != PackageManager.PERMISSION_GRANTED){
                isSuccess = false;
                break;
            }
        }
        return isSuccess;
    }

}
