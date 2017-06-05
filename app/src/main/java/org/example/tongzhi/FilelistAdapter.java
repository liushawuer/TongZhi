package org.example.tongzhi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class FilelistAdapter extends BaseAdapter {
    public Context context;
    public List<String> goodsList;

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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filedetail, parent, false);
            vh.tv_title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(vh);
        }

        String file = goodsList.get(position);
         int pos=file.lastIndexOf("\\");
        String filename=file.substring(pos+1,file.length());

        vh.tv_title.setText(filename);
        //  vh.tv_id.setText(record.getId());
        return convertView;
    }
    public FilelistAdapter(Context context, List<String> goodsList) {
        this.context = context;
        this.goodsList = goodsList;
    }
    public class ViewHolder {
        private TextView tv_title ;

    }
}
