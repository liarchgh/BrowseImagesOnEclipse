package com.example.asdfasdf;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.example.Utils.LoadImageUrls;
import com.example.Utils.LoadPic;

import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class Online extends Activity {
	public static final int MY_MSG = 0;
	public static final int MY_PIC = 1;

	private String lastWord;
	private List<String> urll;
	private List<Bitmap> bml;

	private EditText etWd = null;
	// 查找界面中的控件
	// final ImageView iv = (ImageView)findViewById(R.id.iv);
	// final ImageView iv0 = (ImageView)findViewById(R.id.iv0);
	// final TextView tv = (TextView)findViewById(R.id.tv);
	// final EditText wd = (EditText)findViewById(R.id.word);
	// final Button bts = (Button)findViewById(R.id.bts);
	// //创建滚动进度条
	// ProgressDialog dialog = new ProgressDialog(this);
	// 加载百度图片搜索获取的链接
	private ListView li;

	public void searchImages(View view) {
		bml.clear();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				final EditText wd = (EditText) findViewById(R.id.word);
				// final ImageView iv = (ImageView)findViewById(R.id.iv);
//				final TextView tv = (TextView) findViewById(R.id.tv);
				final ListView lv = (ListView) findViewById(R.id.imageList);
//				final ImageView iv0 = (ImageView) findViewById(R.id.iv0);
				// 加载百度图片搜索获取的链接
				// 获取用户输入的关键字
				final String word = wd.getText().toString();
//				lastWord = word;
				// 拼接百度图片搜索的链接
				final String url = "http://image.baidu.com/search/index?tn=baiduimage&word=" + word;
//				String url = "https://www.bing.com/";
				// 拼接bing图片搜索的链接
//				String host = "https://api.cognitive.microsoft.com";
//				String path = "/bing/v7.0/images/search";
//				String searchTerm = "puppies";
//				String url = host+path+"?q="+URLEncoder.encode(searchQuery, "UTF-8")+word;
//				""
//		        URL url = new URL(host + path + "?q=" +  URLEncoder.encode(searchQuery, "UTF-8"));
//		        HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
//		        connection.setRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey);
				try {
//					wd.setText(url);
					urll = new LoadImageUrls(url).execute().get();
//				lv.post(new Runnable() {
//					@Override
//					public void run() {
//						Dialog dl = new Dialog(Online.this);
//						dl.setTitle(""+urll.size());
//						dl.show();
//					}
//				});
//					wd.setText(urll.size());
//					if (tv != null && urls != null) {
//						for (int i = 0; i < urls.size(); ++i) {
//							tv.setText(tv.getText().toString() + "\n" + urls.get(i));
//						}
//					}
//					tv.setText(urls.get(0));
					if (urll.size() > 0) {
						// tv.setText(urls.size()+"");
						// tv.setText(url);
						// for(int i = 0; i < urls.size(); ++i){
						// new LoadPic(dialog, iv).execute(urls.get(i));
						// }
						// 根据获取的图片链接加载图片
						// new LoadPic(iv).execute(urls.get(3));
						// new LoadPic(dialog, iv0).execute(urls.get(5));
						final List<Map<String, Object>> imageList = new ArrayList<Map<String, Object>>();
//						tv.setText(urls.size());
//						for (String iUrl : urls) {
//						for(int i = 0; i < urls.size(); ++i) {
						for(int i = 0; i < Math.min(25, urll.size()); ++i) {
							final String iUrl = urll.get(i);
							Map<String, Object> map = new HashMap<String, Object>();
//							tv.setText(tv.getText().toString() + "\n" + bm.getHeight());
//							byte[] data = NetUtil.doGetImage(iUrl);
//							Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length); 
							Bitmap bm = new LoadPic().execute(iUrl).get();
//							map.put("image", bm);
							String info = "Resolution:\t"+bm.getWidth()+"×"+bm.getHeight()
								+"\nSize:\t"+bm.getByteCount()/1024+"KB"
								+"\nFrom:\t"+(iUrl.split("/"))[2];
							map.put("title", info);
							map.put("image", bm);
							bml.add(bm);
//							map.put("image", NetUtil.doGetBitmap(iUrl));
							imageList.add(map);
						}
//						final String[] from = { "image", "title"};
//						final int[] to = { R.id.iv, R.id.t0 };
						final String[] from = { "title", "image"};
						final int[] to = { R.id.t0, R.id.imageShow};
						// final SimpleAdapter sa = new SimpleAdapter(Images.this, listMap,
						// R.layout.listview, from, to);
						final SimpleAdapter sa = new SimpleAdapter(Online.this, imageList, R.layout.activity_image_single, from,
								to);
						sa.setViewBinder(new SimpleAdapter.ViewBinder() {
							@Override
							public boolean setViewValue(View aView, Object attentionList, String textRepresentation) {
								// TODO Auto-generated method stub
								if (aView instanceof ImageView && attentionList instanceof Bitmap) {
									ImageView iv = (ImageView) aView;
									iv.setImageBitmap((Bitmap) attentionList);
									return true;
								} else if (aView instanceof TextView && attentionList instanceof String) {
									TextView tt = (TextView) aView;
									tt.setText((String) attentionList);
									return true;
								}
								return false;
							}
						});
						lv.post(new Runnable() {
							@Override
							public void run() {
								lv.setAdapter(sa);
							}
						});
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_images_online);

		etWd = (EditText) findViewById(R.id.word);
		li = (ListView) findViewById(R.id.imageList);
		bml = new ArrayList<Bitmap>();
		
		// dialog.setMessage("Loading......");

		// LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
		// LinearLayout.LayoutParams.FILL_PARENT,
		// LinearLayout.LayoutParams.WRAP_CONTENT
		// );
		// String url = "http://acm.hdu.edu.cn/images/banner.jpg";
		// LoadPic lp = new LoadPic(dialog, iv);
		// lp.execute(url);
		// LoadText lt = new LoadText(dialog, tv);
		// String word = "iphone";
		// url = "https://image.baidu.com/search/index?tn=baiduimage&word="+word;
		// lt.execute(url);

		// final Handler mainHandler = new Handler();
		// {
		// @Override
		// public void handleMessage(Message msg) {
		// super.handleMessage(msg);
		//// tv2.setText(msg.what);
		// switch(msg.what){
		// case MY_MSG:
		// tv.setText((String)msg.obj);
		// break;
		// case MY_PIC:
		// break;
		// }
		// }
		// };

		// new Thread(new Runnable() {
		// @Override
		// public void run() {
		// String url = "http://www.bilibili.com";
		// try {
		// String res = NetUtil.doGet(url);
		// Message msg = new Message();
		// msg.what = MY_MSG;
		// msg.obj = res;
		// mainHandler.sendMessage(msg);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		// }).start();

		// new Thread(new Runnable() {
		// @Override
		// public void run() {
		// String url = "http://acm.hdu.edu.cn/images/banner.jpg";
		// try {
		//// new LoadPic(this, null, iv);
		// byte[] res = NetUtil.doGetImage(url);
		// final Bitmap bm = BitmapFactory.decodeByteArray(res, 0, res.length);
		//// runOnUiThread(new Runnable() {
		//// @Override
		//// public void run() {
		//// }
		//// });
		//
		// //直接运行
		//// iv.post(new Runnabl() {
		//// @Override
		//// public void run() {
		//// iv.setImageBitmap(bm);
		//// }
		//// });
		//
		//// //延迟运行
		//// iv.postDelayed(
		//// new Runnable() {
		//// @Override
		//// public void run() {
		//// iv.setImageBitmap(bm);
		//// }
		//// }, 3000);
		////
		////// Message msg = new Message();
		////// msg.what = MY_PIC;
		////// msg.obj = ;
		////// mainHandler.sendMessage(msg);
		//// url = "http://www.bilibili.com";
		////// url =
		// "http://image.baidu.com/i?tn=baiduimagejson&word=周杰伦&pn=10&rn=10&ie=utf8";
		//// final String rest = NetUtil.doGet(url);
		////
		//// tv.postDelayed(new Runnable() {
		//// @Override
		//// public void run() {
		//// tv.setText(rest);
		//// }
		//// }, 5000);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		// }).start();
		li.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
//				Uri uri = Uri.parse(urll.get((int) id));
//				Intent downloadIntent = new Intent(Intent.ACTION_VIEW, uri);
//				startActivity(downloadIntent);

				DownloadManager downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
				String apkUrl = urll.get((int) id);
				DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkUrl));
				int end = apkUrl.length(), begin = end - 1;
				while(begin >= 0 && apkUrl.charAt(begin) != '.') --begin;
				while(begin >= 0) {
					--begin;
					char c = apkUrl.charAt(begin);
					if(c >= 'a' && c <= 'z') continue;
					if(c >= 'A' && c <= 'Z') continue;
					if(c >= '0' && c <= '9') continue;
					++begin;
					break;
				}
				request.setDestinationInExternalPublicDir("Download", apkUrl.substring(begin, end));
				// request.setTitle("MeiLiShuo");
				// request.setDescription("MeiLiShuo desc");
				// request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
				// request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
				// request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
				// request.setMimeType("application/cn.trinea.download.file");
				long downloadId = downloadManager.enqueue(request);
				return true;
			}
		});

		li.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
				setContentView(R.layout.activity_image_big);
				final ImageView ivImageShow = (ImageView) findViewById(R.id.ivBig);
				ivImageShow.post(new Runnable() {

					@Override
					public void run() {
						ivImageShow.setImageBitmap(bml.get((int) id));
					}
				});
			}
		});
	}
	public void jump2Home(View view) {
		Intent intent = new Intent(Online.this, HomeAndLocal.class);
		startActivity(intent);
	}
	public void jump2List(View view) {
		jump2Home(view);
//		li.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
//				setContentView(R.layout.activity_image_big);
//				final ImageView ivImageShow = (ImageView) findViewById(R.id.ivBig);
//				ivImageShow.post(new Runnable() {
//
//					@Override
//					public void run() {
//						ivImageShow.setImageBitmap(bml.get((int) id));
//					}
//				});
//			}
//		});
//		etWd.setText(lastWord);
//		searchImages(view);
	}
}
