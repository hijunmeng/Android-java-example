package com.junmeng.android_java_example.dialog;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ui.dialog.CommonBottomSheetDialogFragment;
import com.example.ui.dialog.CommonDialogFragment;
import com.example.ui.dialog.SlidablePopupWindow;
import com.junmeng.android_java_example.R;

public class DialogShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_show);
    }

    public void onClickDialogFragment(View view) {
        new CommonDialogFragment.Builder()
                .setTitle("标题")
                .setTitleBold(true)
                .isContentCenter(false)
                .setContent("这是content")
                .setCloseIcon(-1)
                .setPositiveButton("确定", -1, null)
                .setNegativeButton("取消", -1, null)
                .create()
                .show(getSupportFragmentManager(), "")
        ;
    }

    public void onClickDialogBottomSheet(View view) {
        new CommonBottomSheetDialogFragment.Builder()
                .addButton("1", 1, -1, null)
                .addButton("2", 2, -1, null)
                .setDividerItemDecoration(android.R.color.darker_gray)
                .setCancelButton(null, -1, null)
                .create()
                .show(getSupportFragmentManager(), "")
        ;
    }

    public void onClickDialogPopup(View view) {

        new SlidablePopupWindow(this).show(getWindow().getDecorView());
    }
}