package com.example.latte.ui.camera;

import android.net.Uri;

import com.example.latte.delegates.PermissionCheckerDelegate;
import com.example.latte.util.FileUtil;

/**
 * Created by liangbingtian on 2018/4/24.
 * 照相机调用类
 */



public class LatteCamera {

    //我们需要一个剪裁的东西，就必须先需要一个文件地址去存放它
    public static Uri createCropFile(){
         return Uri.
                 parse(FileUtil.createFile
                         ("crop_image",
                                 FileUtil.getFileNameByTime
                                         ("IMG","jpg"))
                         .getPath());

    }

    public static void start(PermissionCheckerDelegate delegate){
        new CameraHandler(delegate).beginCameraDialog();
    }


}
