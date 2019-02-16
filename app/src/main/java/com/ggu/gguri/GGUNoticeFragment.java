package com.ggu.gguri;


import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ggu.gguri.databinding.FragmentGgunoticeBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class GGUNoticeFragment extends Fragment {

    FragmentGgunoticeBinding binding;
    Fragment fragment = null;

    private ListView listView;
    private NoticeListViewAdapter adapter;

    public GGUNoticeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ggunotice, container, false);
        View v = binding.getRoot();

        binding.tabHost.setup();
        binding.tabHost.addTab(binding.tabHost.newTabSpec("Tab Spec 1").setIndicator("학사공지").setContent(binding.tab1.getId()));
        binding.tabHost.addTab(binding.tabHost.newTabSpec("Tab Spec 2").setIndicator("비교과").setContent(binding.tab2.getId()));
        binding.tabHost.addTab(binding.tabHost.newTabSpec("Tab Spec 3").setIndicator("취업공지").setContent(binding.tab3.getId()));
        binding.tabHost.addTab(binding.tabHost.newTabSpec("Tab Spec 4").setIndicator("일반공지").setContent(binding.tab4.getId()));

        adapter = new NoticeListViewAdapter();
        listView = binding.noticeView;

        listView.setAdapter(adapter);

        GetResult task = new GetResult();
        task.execute();

        return v;
    }

    private class GetResult extends AsyncTask<Void, Void, Map<String, String>> {
        ProgressDialog asyncDialog = new ProgressDialog(getActivity());
        Elements board_title_raw, board_head_raw, board_content_raw, board_content_downloader, board_footer;

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("잠시만 기다려주세요...");

            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Map<String, String> doInBackground(Void... voids) {
            Map<String, String> result = new HashMap<>();

            try {
                String href = getArguments().getString("href");

                // 파싱 설정
                String strUrl = "https://www.ggu.ac.kr" + href;
                Document doc = Jsoup.connect(strUrl).get();

                // 파싱 해오기
                board_title_raw = doc.select(".bbs > thead > tr");
                board_head_raw = doc.select(".bbs > tbody > tr");
                board_content_raw = doc.select(".bbs-html > p");
                board_content_downloader = doc.select(".downloader > li");
                board_footer = doc.select(".gray-border-box > ul > li");

            } catch (Exception e) {
                e.printStackTrace();

            }

            return result;
        }

        @Override
        protected void onPostExecute(Map<String, String> map) {

            asyncDialog.dismiss();

//            try {
//                title = board_title_raw.select("th").text();
//                writer = board_head_raw.select("td").not("span").get(0).text();
//                regdate = board_head_raw.select("td").get(1).text();
//                hit = board_head_raw.select("td").get(2).text();
//                download_href = board_content_downloader.select("a").attr("href");
//                download = board_content_downloader.select("a").text();
//                department_text = board_footer.get(1).select("span").text();
//                department_tel_text = board_footer.get(2).select("span").text();
//
//                for(Element arr : board_content_raw) {
//                    content += (Html.fromHtml(arr.html()) + "\n\n");
//                }
//
//                board_title.setText(title);
//                board_writer.setText(writer.replaceAll("작성자 ", ""));
//                board_regdate.setText(regdate.replaceAll("작성일 ", ""));
//                board_hit.setText(hit.replaceAll("조회수 ", ""));
//                board_content.setText(content);
//                board_download.setText(download);
//                board_download.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://ggu.ac.kr" + download_href));
//                        startActivity(intent);
//                    }
//                });
//                department.setText(department_text);
//                department_tel.setText(department_tel_text);
//            } catch (Exception e) {
//                e.printStackTrace();
//
//            }
        }
    }

}
