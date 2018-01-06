package com.example.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.TextView;

/**
 * Created by neo on 2017/12/9.
 */

public class LoadImageUrls extends AsyncTask<String, Integer, List<String>>{
    private TextView tv;
    private String word = null;

    public LoadImageUrls(TextView imageView) {
        this.setTv(imageView);
    }
    public LoadImageUrls(String ii) {
    	this.word = ii;
    }
    public LoadImageUrls() {
    }

    @Override
    protected void onPostExecute(List<String> result) {
        super.onPostExecute(result);
//        if(tv != null && result != null){
//            for(int i = 0; i < result.size(); ++i){
//                tv.setText(tv.getText().toString()+"\n"+result.get(i));
//            }
//        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<String> doInBackground(String... strings) {
        publishProgress(0);
		String url;       
        if(strings.length > 1) {
			url = strings[0];
        }
        else {
        	url = word;
        }
		List<String> urls = new ArrayList<String>();
		// tv.setText(url);
		try {
			String html = NetUtil.doGet(url);
			// String html = url;
			// tv.setText("get html over");
			// tv.setText(html);
			// Pattern pattern = Pattern.compile("^(\'imgData\',.*;$");
			// Matcher matcher = pattern.matcher(html);
			// boolean b= matcher.matches();
			// //当条件满足时，将返回true，否则返回false
			// System.out.println(b);
			// publishProgress(1);

			String jsonRe = "\\('imgData',[^)]*\\)";
			Pattern pattern = Pattern.compile(jsonRe);
			// publishProgress(2);
			Matcher matcher = pattern.matcher(html);
			// publishProgress(3);
			while (matcher.find()) {
				// tv.setText(matcher.groupCount());
				// urls.add(matcher.group());
				String objUrls = matcher.group();
				// String urlJsonRe = "\"thumbURL\": \"[^\"]*\"";
				String urlJsonRe = "\"thumbURL\":\"[^\"]*\"";
				// String urlJsonRe = "http";
				Pattern urlJsonPattern = Pattern.compile(urlJsonRe);
				Matcher urlJsonMc = urlJsonPattern.matcher(objUrls);
				while (urlJsonMc.find()) {
					// urls.add(urlJsonMc.group());
					String tt = urlJsonMc.group();
					int send = tt.length() - 1, sbegin = 12;
					send = send >= 0 ? send : 0;
					urls.add(tt.substring(sbegin, send).replaceAll("\\\\/", "/").replace("\n", ""));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return urls;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

	public TextView getTv() {
		return tv;
	}

	public void setTv(TextView tv) {
		this.tv = tv;
	}
}
