package org.example.tongzhi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by meihousanwen on 2017/5/29.
 */

public class RegisterActivity extends Activity implements View.OnClickListener {
    public static final int SHOW_RESPONSE = 0;
    private Button submit;
    private TextView title;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOW_RESPONSE:
                   final String ms = (String) msg.obj;
                    Toast.makeText(RegisterActivity.this, ms, Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        title = (TextView) findViewById(R.id.at_title);
        title.setText("注册界面");
        submit = (Button)findViewById(R.id.btregister1);
        // responseText = (TextView) findViewById(R.id.response_text);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        if (v.getId() == R.id.btregister1) {
            //String mess = ms;

                Button submit = (Button) v;
                submit.setText("注册中，请稍候...");
                sendRequestWithHttpURLConnetion();
                //Toast.makeText(RegisterActivity.this,"注册成功，请登录",Toast.LENGTH_LONG).show();

            }
        }

    private void sendRequestWithHttpURLConnetion(){

        EditText etuser = (EditText)findViewById(R.id.edname1);
        EditText etpass = (EditText)findViewById(R.id.edpassword1);
        final String user = etuser.getText().toString();
        final String pass = etpass.getText().toString();






        //开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection connection = null;

                try{

                    URL url = new URL("http://10.3.149.46:8080/RestTest/rest/doc/user/register");
                    //http://10.3.168.92:9998/doc/user/register

                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());

                    out.writeBytes("user="+user+"&pass="+pass);
                    //out.writeUTF("user&pass");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine())!=null){
                        response.append(line);
                    }
                    Message message = new Message();
                   // message.what = 1;
                    message.what = SHOW_RESPONSE;
                    //将服务器返回的结果存放到Message中
                    message.obj = response.toString();
                    handler.sendMessage(message);
                    if(response.toString().equals("success register")){//注册成功
                        //Toast.makeText(RegisterActivity.this,response.toString(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                        intent.putExtra("ok","1");



                        startActivity(intent);
                    }else {
                        //Toast.makeText(RegisterActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }






}


