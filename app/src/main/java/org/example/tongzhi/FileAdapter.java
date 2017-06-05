package org.example.tongzhi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class FileAdapter extends BaseAdapter {
    public Context context;
    public List<Record> goodsList;

    @Override
    public int getCount() {
        return goodsList.size();
    }

    @Override
    public Object getItem(int position) {
        return goodsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
         ViewHolder vh ;
        if (convertView != null) {
            vh = (ViewHolder) convertView.getTag();
        } else {
            vh =  new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file, parent, false);
            vh.tv_title = (TextView) convertView.findViewById(R.id.title);
            vh.tv_author = (TextView) convertView.findViewById(R.id.author);//
            //vh.tv_id = (TextView) convertView.findViewById(R.id.filesid);

            convertView.setTag(vh);
        }
        Record record = goodsList.get(position);
        vh.tv_title.setText(record.getTitle());
        vh.tv_author.setText(record.getAuthor());
      //  vh.tv_id.setText(record.getId());
        return convertView;
    }
    public FileAdapter(Context context, List<Record> goodsList) {
        this.context = context;
        this.goodsList = goodsList;
    }
    public class ViewHolder {
        private TextView tv_title ;
        private TextView tv_author ;
      //  private TextView tv_id;

    }
}
