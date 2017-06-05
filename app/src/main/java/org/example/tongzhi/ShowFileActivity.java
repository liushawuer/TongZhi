package org.example.tongzhi;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import java.io.File;

/**
 * Created by meihousanwen on 2017/6/2.
 */

public class ShowFileActivity extends Activity {
    private TextView title;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showfilemannager);
        title = (TextView) findViewById(R.id.at_title);
        title.setText("附件文档下载");
//        String inPath2 = Environment.getExternalStorageDirectory().getPath();
//        String path = inPath2 + "/MyDownLoad/";
//        File file = new File(path);
//        File parentFile = new File(file.getParent());
//        Intent intent = new Intent();
//        intent.setAction("android.intent.action.GET_CONTENT");
//        intent.setDataAndType(Uri.fromFile(parentFile),"/MyDownLoad");
//        intent.setData(Uri.parse("DEFAULT"));
//        intent.addCategory("android.intent.category.CATEGORY_OPENABLE");
//        startActivity(intent);
    }
    private void openFile(File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        String inPath2 = Environment.getExternalStorageDirectory().getPath();
        String type = inPath2 + "/MyDownLoad/";
        intent.setDataAndType(Uri.fromFile(file), type);
        startActivity(intent);
    }


}