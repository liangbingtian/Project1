package com.example.latte.delegates;

import com.example.latte.ui.camera.RequestCodes;
import com.example.latte.ui.scanner.ScannerDelegate;

/**
 * Created by liangbingtian on 2018/3/15.
 */

public abstract class PermissionCheckerDelegate extends BaseDelegate {


    //扫描二维码，不直接调用
    void startScan(BaseDelegate delegate){
//       delegate.getSupportDelegate().startForResult(new ScannerDelegate(), RequestCodes);
    }
}
