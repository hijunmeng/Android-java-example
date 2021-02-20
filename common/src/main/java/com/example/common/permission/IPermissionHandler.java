package com.example.common.permission;

import androidx.annotation.Nullable;

import java.util.List;

public interface IPermissionHandler {

    interface Callback {
        /**
         * 用户同意授权后回调此
         */
        void onAgree();
    }

    interface PermissionsCallback {
        /**
         * 请求权限都已授予
         */
        void onAllGrantSuccess();
    }

    /**
     * 检查权限
     *
     * @param isAutoRequestPermissionWhenUngranted 当发现权限未授予时是否自动发起请求权限
     * @param callback                             当权限授全部授予时会回调此
     * @return true--所有权限都已授予 false--未全部授予
     */
    boolean checkPermissions(boolean isAutoRequestPermissionWhenUngranted, @Nullable PermissionsCallback callback);

    /**
     * 获得需要发起请求的权限
     *
     * @return
     */
    String[] getRequestPermissions();

    /**
     * 发起请求权限
     *
     * @param unGrantedPermissions 未被授予的权限
     */
    void requestPermission(List<String> unGrantedPermissions);

    /**
     * 对权限进行解释（用户可实现此接口弹出对话框对所用权限进行解释说明）
     *
     * @param permissions 应该对用户作解释说明的权限
     * @param callback    如果用户同意则回调此
     * @return true--表示用户会实现此接口，false--表示用户忽略此接口
     */
    boolean shouldShowRequestPermissionRationale(List<String> permissions, Callback callback);

    /**
     * 请求权限都已授予
     */
    void onAllGrantSuccess();

    /**
     * 用户已经勾选了拒绝不再提示
     */
    void onAlwaysRejectGrant();
}
