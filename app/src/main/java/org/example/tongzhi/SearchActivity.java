package org.example.tongzhi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static org.example.tongzhi.RegisterActivity.SHOW_RESPONSE;

/**
 * Created by meihousanwen on 2017/5/29.
 */

public class SearchActivity extends Activity  {
    EditText depsearch;
    EditText querysearch;
    EditText filesearch;
    EditText contentsearch;
    EditText starttime;
    EditText endtime;
    Button searchbt;
    TextView title;

    private TextView responseText2;
    private Handler handler2 = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESPONSE:
                    String response2 = (String) msg.obj;
                    //在这里进行UI操作，将结果显示到界面上
                    //responseText2.setText(response2);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        searchbt = (Button) findViewById(R.id.search_button);
        depsearch = (EditText) findViewById(R.id.dep_search);
        querysearch = (EditText) findViewById(R.id.query_search);
        filesearch = (EditText) findViewById(R.id.file_search);
        contentsearch = (EditText) findViewById(R.id.content_search);
        starttime = (EditText)findViewById(R.id.start_time);
        endtime = (EditText)findViewById(R.id.end_time);
        title = (TextView) findViewById(R.id.at_title);
        title.setText("检索界面");




      searchbt.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if (v.getId() == R.id.search_button) {
                  Button submit = (Button) v;
                  submit.setText("检索中，请稍候...");
                  sendSearchWithHttpURLConnetion();

              }
          }
      });
            }



    public void sendSearchWithHttpURLConnetion() {
        final String title = querysearch.getText().toString();
        final String author = depsearch.getText().toString();
        final String start = starttime.getText().toString();
        final String end = endtime.getText().toString();
        final String fileName = filesearch.getText().toString();
        final String content = contentsearch.getText().toString();


        //开启线程来发送网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection connection3 = null;
                try {
                    URL url = new URL("http://10.3.149.46:8080/RestTest/rest/search/result/jsonresult");
                    connection3 = (HttpURLConnection) url.openConnection();
                    connection3.setRequestMethod("POST");
                    DataOutputStream out = new  DataOutputStream(connection3.getOutputStream());

//                    connection3.setRequestProperty("ContentType","application/x-www-form-urlencoded;charset=UTF-8");
                    String msg = "title=" +  URLEncoder.encode(title,"UTF-8") +
                            "&author=" + URLEncoder.encode(author,"UTF-8") +
                            "&start=" + URLEncoder.encode(start,"UTF-8")  +
                            "&end=" +  URLEncoder.encode(end,"UTF-8") +
                            "&fileName=" + URLEncoder.encode(fileName,"UTF-8") +
                            "&content=" + URLEncoder.encode(content,"UTF-8");
                    Log.e("SEARCH",msg);
                    out.writeBytes(msg);
                    //out.writeBytes("title=" + title + "&author=" + author + "&start=" + start + "&end=" + end + "&fileName=" + fileName + "&content=" + content);

                    connection3.setConnectTimeout(8000);
                    connection3.setReadTimeout(8000);
                    InputStream in2 = connection3.getInputStream();
                    BufferedReader reader2 = new BufferedReader(new InputStreamReader(in2));
                    StringBuilder response2 = new StringBuilder();

                    String line;
                    while ((line = reader2.readLine()) != null) {
                        response2.append(line);
                    }

                    Intent intent = new Intent();
                    intent.setClass(SearchActivity.this, FileActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", response2.toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();

                } finally {
                    if (connection3 != null) {
                        connection3.disconnect();
                    }
                }


            }


        }).start();
    }
}


