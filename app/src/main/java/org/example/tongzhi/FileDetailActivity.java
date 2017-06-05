package org.example.tongzhi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by meihousanwen on 2017/5/29.
 */

public class FileDetailActivity extends Activity {
    private TextView filestext;
    private Record record;
    private Button downfilebtn;
    private List<String> files=new ArrayList<String>();
    private MyListView listview;
    private FilelistAdapter filelistAdapter;
    private String urlurl=null;
    private String filename;
    private String id2;
    private TextView title;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filedetail_domnload);

        filestext = (TextView) findViewById(R.id.files_name);
        listview = (MyListView) findViewById(R.id.listview);
        title = (TextView) findViewById(R.id.at_title);
        title.setText("附件文档下载");

        final Intent Intent = getIntent();
        record = (Record) Intent.getSerializableExtra("data");
        files = record.getFiles();

        filelistAdapter = new FilelistAdapter(FileDetailActivity.this, files);
        listview.setAdapter(filelistAdapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String id_name = files.get(position);
                int pos = id_name.lastIndexOf("\\");

                  filename = id_name.substring(pos + 1, id_name.length());
                  id2  = record.getId();
                DownLoad();

                AlertDialog.Builder dialog = new AlertDialog.Builder(FileDetailActivity.this);
                dialog.setTitle("请到保存路径查看");
                dialog.setMessage("下载文件保存路径：MyDownLoad");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //??
//                        dialog.dismiss();
//                        Intent intentx = new Intent(FileDetailActivity.this, ShowFileActivity.class);
//                        startActivity(intentx);

                    }
                });
                dialog.show();
            }
        });
    }
        Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        String ms = (String) msg.obj;
                        Toast.makeText(FileDetailActivity.this, ms, Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
            }
        };



        public void DownLoad() {
           // final String url = URL;
            final String id =  id2;
            final String name = filename;

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL Url = new URL("http://10.3.149.46:8080/file/" + id + "/" + name);

                    connection = (HttpURLConnection) Url.openConnection();
                    connection.setRequestMethod("GET");


                    String inPath = Environment.getExternalStorageDirectory().getPath();
                    String path = inPath + "/MyDownLoad/";
                    File f = new File(path);
                    if (!f.exists()) {
                        f.mkdir();
                    }

                    InputStream input = connection.getInputStream();
                    byte[] bytes = new byte[1024];
                    File file = new File(path + name);
                    OutputStream out = new FileOutputStream(file);
                    int len = 0;
                    while ((len = input.read(bytes)) != -1) {
                        out.write(bytes, 0, len);
                    }
                    out.flush();
                    input.close();
                    out.close();
                    Message message = new Message();
                    message.what = 1;
                    message.obj = "success";
                    mHandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}


