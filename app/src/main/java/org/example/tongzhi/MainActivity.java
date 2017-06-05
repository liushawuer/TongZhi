package org.example.tongzhi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
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

import static org.example.tongzhi.R.id.txt_name;

public class MainActivity extends AppCompatActivity {

    public static final int SHOW_RESPONSE2 = 0;
    EditText edname;
    EditText edpassword;
    Button btlogin;
    Button btregister;
    TextView title;

    private TextView responseText2;
    private Handler handler2 = new Handler(){
        public void handleMessage(Message msg2){
            switch (msg2.what){
                case SHOW_RESPONSE2:

                    String ms = (String) msg2.obj;
                    Toast.makeText(MainActivity.this, ms, Toast.LENGTH_LONG).show();
                    if(ms.equals("login success")){
                        Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                        startActivity(intent);
                    }else{
                        edname.setText("");
                        edpassword.setText("");

                    }
                    break;
                default:
                    break;
            }
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        edname = (EditText) findViewById(txt_name);
        edpassword = (EditText) findViewById(R.id.lg_password);
        btregister = (Button) findViewById(R.id.btregister);
        btlogin = (Button) findViewById(R.id.btlogin);

        title = (TextView) findViewById(R.id.at_title);
        title.setText("登录界面");
        // responseText2 = (TextView) findViewById(R.id.response_text2);
//        Intent intent = new Intent();
//        intent.setClass(MainActivity.this, test.class);
//        startActivity(intent);



        //跳转到注册界面
        btregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });
        btlogin.setOnClickListener(new LoginListener());

    }




    class LoginListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String name = edname.getText().toString();
            String password = edpassword.getText().toString();
            if (name.equals("") || password.equals("")) {

                //弹出消息框
                new AlertDialog.Builder(MainActivity.this).setTitle("错误").setMessage("账号或密码不能为空").setPositiveButton("确定", null).show();

            } else {
//                Button lgsubmit = (Button) v;
//                lgsubmit.setText("登录中，请稍候...");

                sendLoginRequestWithHttpURLConnetion();
            }



        }

        public void sendLoginRequestWithHttpURLConnetion(){
            EditText etlguser = (EditText)findViewById(txt_name);
            EditText etlgpass = (EditText)findViewById(R.id.lg_password);
            final String user2 = etlguser.getText().toString();
            final String pass2 = etlgpass.getText().toString();

            //开启线程发送网络请求
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpURLConnection connection2 = null;
                    try {
                        URL url2 = new URL("http://10.3.149.46:8080/RestTest/rest/doc/user/login");
                        connection2 = (HttpURLConnection) url2.openConnection();
                        connection2.setRequestMethod("POST");
                        DataOutputStream out2 = new DataOutputStream(connection2.getOutputStream());
                        out2.writeBytes("user="+user2+"&pass="+pass2);
                        connection2.setConnectTimeout(8000);
                        connection2.setReadTimeout(8000);
                        InputStream in2 = connection2.getInputStream();
                        BufferedReader reader2 = new BufferedReader(new InputStreamReader(in2));
                        StringBuilder response2 = new StringBuilder();
                        String line2;
                        while ((line2=reader2.readLine())!=null){
                            response2.append(line2);
                        }
                        Message message2 = new Message();
                        message2.what = SHOW_RESPONSE2;
                        //将服务器返回的结果放入message2中
                        message2.obj = response2.toString();
                        handler2.sendMessage(message2);



                    }catch (Exception e){
                        e.printStackTrace();

                    }finally {
                        if (connection2!=null){
                            connection2.disconnect();
                        }

                    }

                }
            }).start();
        }
    }

}
