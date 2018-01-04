package com.example.asdfasdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.example.Utils.LoadLoacalPhotoCursorTask;
import com.example.Utils.LoadLocalImageUris;
import com.example.Utils.LoadPic;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

public class LocalImages extends Activity implements OnItemClickListener{
//	private ImageView iv;
	private Button bn;
	private Button bd;
	private Button bs;
	private Button ba;
	private EditText wtv;
//	private EditText edit;
//	private TextView tv0;
	private ListView iLO;
//	String input = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_images_local);
		bn = (Button) findViewById(R.id.fbSN);
		bd = (Button) findViewById(R.id.fbSD);
		bs = (Button) findViewById(R.id.fbSS);
		ba = (Button) findViewById(R.id.fbSA);
		iLO = (ListView) findViewById(R.id.list);
		wtv = (EditText)findViewById(R.id.key);
		iLO.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(LocalImages.this);
				// 以对话框形式显示图片
				dialog.setContentView(R.layout.big_image);
				dialog.setTitle("图片显示");

//				if(((LinearLayout)view).getChildCount() < 0) {
				final LinearLayout ll = ((LinearLayout)view);
				final ImageView ci = (ImageView)(ll.getChildAt(0));
				((TextView)findViewById(R.id.debug)).setText(ci.getWidth()+" "+ci.getHeight());
				final ImageView ivImageShow = (ImageView) dialog.findViewById(R.id.iv);
				ivImageShow.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
//						Bitmap bm = Bitmap.createBitmap(ci.getWidth(), ci.getHeight(), Bitmap.Config.ARGB_8888);
		//				ivImageShow.setImageBitmap(parent.getView getChildAt(position-parent.getFirstVisiblePosition()).getview);
//						Canvas c = new Canvas(bm);//使用bitmap构建一个Canvas，绘制的所有内容都是绘制在此Bitmap上的
//						Drawable bgDrawable = ci.getBackground();
//						bgDrawable.draw(c);//绘制背景
//						ci.draw(c);//绘制前景
						ci.setDrawingCacheEnabled(true);
						Bitmap bm = ci.getDrawingCache();
						ivImageShow.setImageBitmap(bm);
//						ci.setDrawingCacheEnabled(false);
					}
				});
				Button btnClose = (Button) dialog.findViewById(R.id.fbtn);

				btnClose.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
					
				});
				dialog.show();
//				}
			}
		});
//		iv = (ImageView) findViewById(R.id.fiv0);
//		tv0 = (TextView)findViewById(R.id.ftv0);
//		initViews();
	}

//	private void initViews() {
//		 TODO Auto-generated method stub
//		 edit=(EditText)findViewById(R.id.edit);
//		 edit.setOnClickListener(new OnClickListener() {
//		 public void onClick(View v) {
//		 // TODO Auto-generated method stub
//		 input=edit.getText().toString();
//		 }
//		 });
//			List<String>uris = getSystemPhotoList(this);
//			if(uris != null) {
////				for(String uu : uris) {
////					tv0.setText(tv0.toString()+"\n"+uu);
////				}
//			}
		public void searchImagesBySize(View view) {
				iLO.post(new Runnable() {
					@Override
					public void run() {
						List<String>uris = LoadLocalImageUris.getAllPhotoInfo(LocalImages.this);
						try {
							final List<Map<String, Object>> imageList = new ArrayList<Map<String, Object>>();
							for(int i = 0; i < uris.size(); ++i) {

								float lmt = Float.parseFloat(wtv.getText().toString());
								final String iUrl = uris.get(i);
								File ig = new File(iUrl);
								if((float)(Math.round((ig.length()*100/1024)))/100 < lmt) {
									continue;
								}
								Date fileDate = new Date(ig.lastModified());
//								((TextView)findViewById(R.id.debug)).setText(((float)fileDate.getTime() - key.getTime())/1000/60/60/24+" "+fileDate.getTime()+" "+key.getTime());
								Map<String, Object> map = new HashMap<String, Object>();
								Bitmap bm = null;
								bm = BitmapFactory.decodeFile(iUrl);
								String info = "Image Name:\t" + ig.getName();
								info +=     "\nDate:\t" + new SimpleDateFormat("yyyy-MM-dd hh:mm").format(fileDate);
								info +=     "\nSize:\t"+(float)(Math.round((ig.length()*100/1024)))/100+"KB";
								map.put("title", info);
								map.put("image", bm);
								imageList.add(map);
							}
							final String[] from = { "title", "image"};
							final int[] to = { R.id.t0, R.id.imageShow};
							final SimpleAdapter sa = new SimpleAdapter(LocalImages.this, imageList, R.layout.a_image, from,
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
							iLO.post(new Runnable() {
								@Override
								public void run() {
									iLO.setAdapter(sa);
								}
							});
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

	}
		public void searchImagesByDate(View view) {
//			public void onClick(View v) {
//				new LoadLoacalPhotoCursorTask(LocalImages.this, tv0);
				// TODO Auto-generated method stub
//				printImage();
				
				iLO.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
//						List<String>uris = getSystemPhotoList(LocalImages.this);
						List<String>uris = LoadLocalImageUris.getAllPhotoInfo(LocalImages.this);
//						tv0.setText("size is " + uris.size());
//						for(String u : uris) {
//							tv0.setText(tv0.getText()+"\n"+u);
//						}
						try {
							final List<Map<String, Object>> imageList = new ArrayList<Map<String, Object>>();
//							String word = "SSS";
//							long word = Long.parseLong(wtv.getText().toString());
							Date key = new SimpleDateFormat("yyyyMMdd").parse(wtv.getText().toString());
//							Calendar lmt = Calendar.getInstance();
//							Calendar fc = Calendar.getInstance();
//							lmt.setTime(key);
							for(int i = 0; i < uris.size(); ++i) {

								final String iUrl = uris.get(i);
								File ig = new File(iUrl);
								Date fileDate = new Date(ig.lastModified());
//								fc.setTime(fileDate);
//								if(fc.get(Calendar.YEAR) == lmt.get(Calendar.YEAR)
//										&& fc.get(Calendar.MONTH) == lmt.get(Calendar.MONTH)
//										&& fc.get(Calendar.DAY_OF_MONTH) == lmt.get(Calendar.DAY_OF_MONTH)) {
								float ttime = ((float)fileDate.getTime() - key.getTime()) / 1000 / 60 / 60 / 24;
								int ttime1 = (int)(ttime);
								if(ttime < 0) ttime1 = 1;
								if(ttime1 != 0) {
									continue;
								}
								Map<String, Object> map = new HashMap<String, Object>();
								Bitmap bm = null;
	//							bm = BitmapFactory.decodeStream(LocalImagget.getContentResolver().openInputStream(Uri.parse(uris.get(0))));
								bm = BitmapFactory.decodeFile(iUrl);
								String info = "Image Name:\t" + ig.getName();
								info +=     "\nDate:\t" + new SimpleDateFormat("yyyy-MM-dd hh:mm").format(fileDate);
								info +=     "\nSize:\t"+(float)(Math.round((ig.length()*100/1024)))/100+"KB";
								map.put("title", info);
	//							tv.setText(tv.getText().toString() + "\n" + bm.getHeight());
	//							byte[] data = NetUtil.doGetImage(iUrl);
	//							Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length); 
	//							Bitmap bm = new LoadPic().execute(iUrl).get();
	//							map.put("image", bm);


								map.put("image", bm);
	//							map.put("image", NetUtil.doGetBitmap(iUrl));
								imageList.add(map);
							}
							final String[] from = { "title", "image"};
							final int[] to = { R.id.t0, R.id.imageShow};
							// final SimpleAdapter sa = new SimpleAdapter(Images.this, listMap,
							// R.layout.listview, from, to);
							final SimpleAdapter sa = new SimpleAdapter(LocalImages.this, imageList, R.layout.a_image, from,
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
							iLO.post(new Runnable() {
								@Override
								public void run() {
									iLO.setAdapter(sa);
								}
							});
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

//			}
	}
		public void searchImagesByName(View view) {
//			public void onClick(View v) {
//				new LoadLoacalPhotoCursorTask(LocalImages.this, tv0);
				// TODO Auto-generated method stub
//				printImage();
				
				iLO.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
//						List<String>uris = getSystemPhotoList(LocalImages.this);
						List<String>uris = LoadLocalImageUris.getAllPhotoInfo(LocalImages.this);
//						tv0.setText("size is " + uris.size());
//						for(String u : uris) {
//							tv0.setText(tv0.getText()+"\n"+u);
//						}
						try {
							final List<Map<String, Object>> imageList = new ArrayList<Map<String, Object>>();
//							String word = "SSS";
							String word = wtv.getText().toString();
							for(int i = 0; i < uris.size(); ++i) {
								final String iUrl = uris.get(i);
								File ig = new File(iUrl);
								if(!ig.getName().equals(word)) {
									continue;
								}
								Map<String, Object> map = new HashMap<String, Object>();
								Bitmap bm = null;
	//							bm = BitmapFactory.decodeStream(LocalImages.this.getContentResolver().openInputStream(Uri.parse(uris.get(0))));
								bm = BitmapFactory.decodeFile(iUrl);
								String info = "Image Name:\t" + ig.getName();
								info +=     "\nDate:\t" + new SimpleDateFormat("yyyy-MM-dd hh:mm").format(new Date(ig.lastModified()));
								info +=     "\nSize:\t"+(float)(Math.round((ig.length()*100/1024)))/100+"KB";
								map.put("title", info);
	//							tv.setText(tv.getText().toString() + "\n" + bm.getHeight());
	//							byte[] data = NetUtil.doGetImage(iUrl);
	//							Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length); 
	//							Bitmap bm = new LoadPic().execute(iUrl).get();
	//							map.put("image", bm);


								map.put("image", bm);
	//							map.put("image", NetUtil.doGetBitmap(iUrl));
								imageList.add(map);
							}
							final String[] from = { "title", "image"};
							final int[] to = { R.id.t0, R.id.imageShow};
							// final SimpleAdapter sa = new SimpleAdapter(Images.this, listMap,
							// R.layout.listview, from, to);
							final SimpleAdapter sa = new SimpleAdapter(LocalImages.this, imageList, R.layout.a_image, from,
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
							iLO.post(new Runnable() {
								@Override
								public void run() {
									iLO.setAdapter(sa);
								}
							});
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

//			}
	}
//		ba.setOnClickListener(new OnClickListener() {
		public void searchImages(View view) {
//			public void onClick(View v) {
//				new LoadLoacalPhotoCursorTask(LocalImages.this, tv0);
				// TODO Auto-generated method stub
//				printImage();
				
				iLO.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
//						List<String>uris = getSystemPhotoList(LocalImages.this);
						List<String>uris = LoadLocalImageUris.getAllPhotoInfo(LocalImages.this);
//						tv0.setText("size is " + uris.size());
//						for(String u : uris) {
//							tv0.setText(tv0.getText()+"\n"+u);
//						}
						try {
							final List<Map<String, Object>> imageList = new ArrayList<Map<String, Object>>();
							for(int i = 0; i < uris.size(); ++i) {
								final String iUrl = uris.get(i);
								Map<String, Object> map = new HashMap<String, Object>();
								Bitmap bm = null;
	//							bm = BitmapFactory.decodeStream(LocalImages.this.getContentResolver().openInputStream(Uri.parse(uris.get(0))));
								bm = BitmapFactory.decodeFile(iUrl);
								File ig = new File(iUrl);
//							TextView wtv = (TextView)findViewById(R.id.word);
//							wtv.setText(ig.getName());
//								bn.setText(ig.getName());
								String info = "Image Name:\t" + ig.getName();
								info +=     "\nDate:\t" + new SimpleDateFormat("yyyy-MM-dd hh:mm").format(new Date(ig.lastModified()));
								info +=     "\nSize:\t"+(float)(Math.round((ig.length()*100/1024)))/100+"KB";
								map.put("title", info);
	//							tv.setText(tv.getText().toString() + "\n" + bm.getHeight());
	//							byte[] data = NetUtil.doGetImage(iUrl);
	//							Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length); 
	//							Bitmap bm = new LoadPic().execute(iUrl).get();
	//							map.put("image", bm);


								map.put("image", bm);
	//							map.put("image", NetUtil.doGetBitmap(iUrl));
								imageList.add(map);
							}
							final String[] from = { "title", "image"};
							final int[] to = { R.id.t0, R.id.imageShow};
							// final SimpleAdapter sa = new SimpleAdapter(Images.this, listMap,
							// R.layout.listview, from, to);
							final SimpleAdapter sa = new SimpleAdapter(LocalImages.this, imageList, R.layout.a_image, from,
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
							iLO.post(new Runnable() {
								@Override
								public void run() {
									iLO.setAdapter(sa);
								}
							});
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

//			}
	}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			
		}

//        @Override
//	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//		// TODO Auto-generated method stub
//		btnClose.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//			}
//			
//		});
//	}
//	protected void printImage() {
//		
////		try {
////			@SuppressWarnings("unchecked")
////			ArrayList<Uri> uris = (ArrayList<Uri>) new LoadLoacalPhotoCursorTask(this).get();
////			for(Uri uu : uris) {
////				tv0.setText(tv0.toString()+"\n"+uu.toString());
////			}
////		} catch (InterruptedException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		} catch (ExecutionException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//		// TODO Auto-generated method stub
//		Intent intent = new Intent();
//		intent.setType("image/*");
//		intent.setAction(Intent.ACTION_GET_CONTENT);
////		intent.setAction(Intent.ACTION_DATE_CHANGED);
//		
//		startActivityForResult(intent, 1);
//	}

//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if (resultCode == RESULT_OK) {
//			Uri uri = data.getData();
////			Log.e("uri", uri.toString());
//			ContentResolver cr = this.getContentResolver();
//			try {
//				Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
//				iv.setImageBitmap(bitmap);
//			} catch (FileNotFoundException e) {
//				Log.e("Exception", e.getMessage(), e);
//			}
//		}
//		super.onActivityResult(requestCode, resultCode, data);
//	}
//	public static List<String> getSystemPhotoList(Context context) {
//	    List<String> result = new ArrayList<String>();
//	    Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//
//	    ContentResolver contentResolver = context.getContentResolver();
//	    Cursor cursor = contentResolver.query(uri, null, null, null, null);
//	    if (cursor == null || cursor.getCount() <= 0) return null; // 没有图片
//	    while (cursor.moveToNext()) {
//	        int index = cursor
//	                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//	        String path = cursor.getString(index); // 文件地址
//	        File file = new File(path);
//	        if (file.exists()) {
//	            result.add(path);
////	            Log.i(TAG, path);
//	        }
//	    }
//
//	    return result;
//	}
		public void jump2Online(View view) {
            Intent intent = new Intent(LocalImages.this, MainActivity.class);
            startActivity(intent);
		}
}
