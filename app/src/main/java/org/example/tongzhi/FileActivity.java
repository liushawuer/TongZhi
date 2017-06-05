package org.example.tongzhi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meihousanwen on 2017/5/29.
 */

public class FileActivity extends Activity {
    private  MyListView listview;

    private List<Record> recordList = new ArrayList<>();
    private FileAdapter FileAdapter;
    private TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filelist);
        listview = (MyListView) findViewById(R.id.listview);
        title = (TextView) findViewById(R.id.at_title);
        title.setText("检索结果展示");
      //  filestext.setMovementMethod(ScrollingMovementMethod.getInstance());//滚动显示内容
        getdata();

        FileAdapter = new FileAdapter(FileActivity.this, recordList);
        listview.setAdapter(FileAdapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(FileActivity.this, FileDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", recordList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
    void getdata(){
        Intent intent2 = getIntent();
        String response = intent2.getStringExtra("data");
        JSONObject data= JSONObject.parseObject(response);
        JSONArray jsonlist= JSONArray.parseArray(data.get("results").toString());
        for(int i=0;i<jsonlist.size();i++){
            recordList.add(jsonlist.getObject(i,Record.class));
        }
    }



}

