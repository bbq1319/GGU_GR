package com.ggu.gguri;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NoticeListViewAdapter extends BaseAdapter {

    private ArrayList<NoticeListVO> noticeListVO = new ArrayList<NoticeListVO>();
    public NoticeListViewAdapter() {

    }

    @Override
    public int getCount() {
        return noticeListVO.size();
    }

    @Override
    public Object getItem(int position) {
        return noticeListVO.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*
         * position = ListView의 위치
         * 첫번째면 position = 0
        */

        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.notice_listview, parent, false);
        }

        TextView no = convertView.findViewById(R.id.no);
        TextView title = convertView.findViewById(R.id.title);
        TextView reg_date = convertView.findViewById(R.id.reg_date);

        NoticeListVO listVO = noticeListVO.get(position);

        no.setText(listVO.getNo());
        title.setText(listVO.getTitle());
        reg_date.setText(listVO.getRegDate());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }

    public void addVO(String no, String title, String reg_date) {
        NoticeListVO item = new NoticeListVO();

        item.setNo(no);
        item.setTitle(title);
        item.setRegDate(reg_date);

        noticeListVO.add(item);
    }
}
