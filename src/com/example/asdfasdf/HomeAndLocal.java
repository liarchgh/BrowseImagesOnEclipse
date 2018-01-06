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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class HomeAndLocal extends Activity implements OnItemClickListener {
//	private Spinner spinner;
	// private ImageView iv;
	private EditText wtv;
	// private EditText edit;
	// private TextView tv0;
	private ListView listViewOnline;
	// String input = "";

	private List<String> uril;
	private List<Integer> l2b;
	private List<Bitmap> bml;
	private int lastSearch;
	private String lastWord;

	OnItemClickListener imageClick;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_images_home);
		//初始化
//		listViewOnline = (ListView) findViewById(R.id.list);
//		wtv = (EditText)findViewById(R.id.key);
		bml = new ArrayList<Bitmap>();
		l2b = new ArrayList<Integer>();

		//预加载图片的uri和图片bitmap
		new Thread(new Runnable() {
			@Override
			public void run() {
				uril = LoadLocalImageUris.getAllLocalImagesUri(HomeAndLocal.this);
				try {
					for (int i = 0; i < uril.size(); ++i) {
						final String iUrl = uril.get(i);
						Bitmap bm = null;
						bm = BitmapFactory.decodeFile(iUrl);
						bml.add(bm);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

//		OnItemClickListener imageClick = 

//		iLO.setOnItemClickListener(new OnItemClickListener() {
//			
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
//				// TODO Auto-generated method stub
//				final Dialog dialog = new Dialog(LocalImages.this);
//				// 以对话框形式显示图片
//				dialog.setContentView(R.layout.big_image);
//				dialog.setTitle("Big Image");
//
////				if(((LinearLayout)view).getChildCount() < 0) {
////				final LinearLayout ll = ((LinearLayout)view);
////				final ImageView ci = (ImageView)(ll.getChildAt(0));
////				((TextView)findViewById(R.id.debug)).setText(ci.getWidth()+" "+ci.getHeight());
//				final ImageView ivImageShow = (ImageView) dialog.findViewById(R.id.iv);
//				ivImageShow.post(new Runnable() {
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
////						Bitmap bm = Bitmap.createBitmap(ci.getWidth(), ci.getHeight(), Bitmap.Config.ARGB_8888);
//		//				ivImageShow.setImageBitmap(parent.getView getChildAt(position-parent.getFirstVisiblePosition()).getview);
////						Canvas c = new Canvas(bm);//使用bitmap构建一个Canvas，绘制的所有内容都是绘制在此Bitmap上的
////						Drawable bgDrawable = ci.getBackground();
////						bgDrawable.draw(c);//绘制背景
////						ci.draw(c);//绘制前景
////						ci.setDrawingCacheEnabled(true);
////						Bitmap bm = ci.getDrawingCache();
//						ivImageShow.setImageBitmap(bml.get((int)id));
////						ci.setDrawingCacheEnabled(false);
//					}
//				});
//				Button btnClose = (Button) dialog.findViewById(R.id.fbtn);
//
//				btnClose.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						dialog.dismiss();
//					}
//					
//				});
//				dialog.show();
////				}
//			}
//		});

//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_images_local);
//		spinner = (Spinner) findViewById(R.id.spinner);
//		ArrayList<String> list = new ArrayList<String>();
//		list.add("Show All");
//		list.add("By Name");
//		list.add("By Date");
//		list.add("By Size");
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
//		spinner.setAdapter(adapter);
		// iv = (ImageView) findViewById(R.id.fiv0);
		// tv0 = (TextView)findViewById(R.id.ftv0);
		// initViews();
	}

	// private void initViews() {
	// TODO Auto-generated method stub
	// edit=(EditText)findViewById(R.id.edit);
	// edit.setOnClickListener(new OnClickListener() {
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// input=edit.getText().toString();
	// }
	// });
	// List<String>uris = getSystemPhotoList(this);
	// if(uris != null) {
	//// for(String uu : uris) {
	//// tv0.setText(tv0.toString()+"\n"+uu);
	//// }
	// }
	public void searchImagesBySize(View view) {
		lastWord = wtv.getText().toString();
		lastSearch = 0;
		listViewOnline.post(new Runnable() {
			@Override
			public void run() {
				l2b.clear();
				List<String> uris = LoadLocalImageUris.getAllLocalImagesUri(HomeAndLocal.this);
				try {
					final List<Map<String, Object>> imageList = new ArrayList<Map<String, Object>>();
					for (int i = 0; i < uris.size(); ++i) {
						float lmt = Float.parseFloat(wtv.getText().toString());
						final String iUrl = uris.get(i);
						File ig = new File(iUrl);
						if ((float) (Math.round((ig.length() * 100 / 1024))) / 100 < lmt) {
							continue;
						}
						Date fileDate = new Date(ig.lastModified());
						// ((TextView)findViewById(R.id.debug)).setText(((float)fileDate.getTime() -
						// key.getTime())/1000/60/60/24+" "+fileDate.getTime()+" "+key.getTime());
						Map<String, Object> map = new HashMap<String, Object>();
						Bitmap bm = null;
						bm = BitmapFactory.decodeFile(iUrl);
						String info = "Image Name:\t" + ig.getName();
						info += "\nDate:\t" + new SimpleDateFormat("yyyy-MM-dd hh:mm").format(fileDate);
						info += "\nSize:\t" + (float) (Math.round((ig.length() * 100 / 1024))) / 100 + "KB";
						map.put("title", info);
						map.put("image", bm);
						l2b.add(i);
						imageList.add(map);
					}
					final String[] from = { "title", "image" };
					final int[] to = { R.id.t0, R.id.imageShow };
					final SimpleAdapter sa = new SimpleAdapter(HomeAndLocal.this, imageList, R.layout.activity_image_single, from, to);
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
					listViewOnline.post(new Runnable() {
						@Override
						public void run() {
							listViewOnline.setAdapter(sa);
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
		lastWord = wtv.getText().toString();
		lastSearch = 1;
		// public void onClick(View v) {
		// new LoadLoacalPhotoCursorTask(LocalImages.this, tv0);
		// TODO Auto-generated method stub
		// printImage();

		listViewOnline.post(new Runnable() {

			@Override
			public void run() {
				l2b.clear();
				// TODO Auto-generated method stub
				// List<String>uris = getSystemPhotoList(LocalImages.this);
				List<String> uril = LoadLocalImageUris.getAllLocalImagesUri(HomeAndLocal.this);
				// tv0.setText("size is " + uris.size());
				// for(String u : uris) {
				// tv0.setText(tv0.getText()+"\n"+u);
				// }
				try {
					final List<Map<String, Object>> imageList = new ArrayList<Map<String, Object>>();
					// String word = "SSS";
					// long word = Long.parseLong(wtv.getText().toString());
					Date limitDay = new SimpleDateFormat("yyyyMMdd").parse(wtv.getText().toString());
					// Calendar lmt = Calendar.getInstance();
					// Calendar fc = Calendar.getInstance();
					// lmt.setTime(key);
					for (int i = 0; i < uril.size(); ++i) {
						final String nowUri = uril.get(i);
						File nowFile = new File(nowUri);
						Date fileDate = new Date(nowFile.lastModified());
						float timeDiff = ((float) fileDate.getTime() - limitDay.getTime()) / 1000 / 60 / 60 / 24;
						int intTD = (int) (timeDiff);
						if (timeDiff < 0)
							intTD = 1;
						if (intTD != 0) {
							continue;
						}
						l2b.add(i);
						Map<String, Object> m2a = new HashMap<String, Object>();
						Bitmap tempBm = null;
						// bm =
						// BitmapFactory.decodeStream(LocalImagget.getContentResolver().openInputStream(Uri.parse(uris.get(0))));
						tempBm = BitmapFactory.decodeFile(nowUri);
						String info = "Image Name:\t" + nowFile.getName();
						info += "\nDate:\t" + new SimpleDateFormat("yyyy-MM-dd hh:mm").format(fileDate);
						info += "\nSize:\t" + (float) (Math.round((nowFile.length() * 100 / 1024))) / 100 + "KB";
						m2a.put("info", info);
						// tv.setText(tv.getText().toString() + "\n" + bm.getHeight());
						// byte[] data = NetUtil.doGetImage(iUrl);
						// Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
						// Bitmap bm = new LoadPic().execute(iUrl).get();
						// map.put("image", bm);

						m2a.put("image", tempBm);
						// map.put("image", NetUtil.doGetBitmap(iUrl));
						imageList.add(m2a);
					}
					final String[] from = { "info", "image" };
					final int[] to = { R.id.t0, R.id.imageShow };
					// final SimpleAdapter sa = new SimpleAdapter(Images.this, listMap,
					// R.layout.listview, from, to);
					final SimpleAdapter map2ListView = new SimpleAdapter(HomeAndLocal.this, imageList, R.layout.activity_image_single, from, to);
					map2ListView.setViewBinder(new SimpleAdapter.ViewBinder() {
						@Override
						public boolean setViewValue(View viewInListView, Object attentionList, String textRepresentation) {
							// TODO Auto-generated method stub
							if (viewInListView instanceof ImageView && attentionList instanceof Bitmap) {
								ImageView imageViewInListView = (ImageView) viewInListView;
								imageViewInListView.setImageBitmap((Bitmap) attentionList);
								return true;
							} else if (viewInListView instanceof TextView && attentionList instanceof String) {
								TextView textViewInListView = (TextView) viewInListView;
								textViewInListView.setText((String) attentionList);
								return true;
							}
							return false;
						}
					});
					listViewOnline.post(new Runnable() {
						@Override
						public void run() {
							listViewOnline.setAdapter(map2ListView);
						}
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		// }
	}

	public void searchImagesByName(View view) {
		lastWord = wtv.getText().toString();
		lastSearch = 2;
		// public void onClick(View v) {
		// new LoadLoacalPhotoCursorTask(LocalImages.this, tv0);
		// TODO Auto-generated method stub
		// printImage();

		listViewOnline.post(new Runnable() {

			@Override
			public void run() {
				l2b.clear();
				// TODO Auto-generated method stub
				// List<String>uris = getSystemPhotoList(LocalImages.this);
				List<String> uris = LoadLocalImageUris.getAllLocalImagesUri(HomeAndLocal.this);
				// tv0.setText("size is " + uris.size());
				// for(String u : uris) {
				// tv0.setText(tv0.getText()+"\n"+u);
				// }
				try {
					final List<Map<String, Object>> imageList = new ArrayList<Map<String, Object>>();
					// String word = "SSS";
					String word = wtv.getText().toString();
					for (int i = 0; i < uris.size(); ++i) {
						final String iUrl = uris.get(i);
						File ig = new File(iUrl);
						if (!ig.getName().equals(word)) {
							continue;
						}
						l2b.add(i);
						Map<String, Object> map = new HashMap<String, Object>();
						Bitmap bm = null;
						// bm =
						// BitmapFactory.decodeStream(LocalImages.this.getContentResolver().openInputStream(Uri.parse(uris.get(0))));
						bm = BitmapFactory.decodeFile(iUrl);
						String info = "Image Name:\t" + ig.getName();
						info += "\nDate:\t"
								+ new SimpleDateFormat("yyyy-MM-dd hh:mm").format(new Date(ig.lastModified()));
						info += "\nSize:\t" + (float) (Math.round((ig.length() * 100 / 1024))) / 100 + "KB";
						map.put("title", info);
						// tv.setText(tv.getText().toString() + "\n" + bm.getHeight());
						// byte[] data = NetUtil.doGetImage(iUrl);
						// Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
						// Bitmap bm = new LoadPic().execute(iUrl).get();
						// map.put("image", bm);

						map.put("image", bm);
						// map.put("image", NetUtil.doGetBitmap(iUrl));
						imageList.add(map);
					}
					final String[] from = { "title", "image" };
					final int[] to = { R.id.t0, R.id.imageShow };
					// final SimpleAdapter sa = new SimpleAdapter(Images.this, listMap,
					// R.layout.listview, from, to);
					final SimpleAdapter sa = new SimpleAdapter(HomeAndLocal.this, imageList, R.layout.activity_image_single, from, to);
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
					listViewOnline.post(new Runnable() {
						@Override
						public void run() {
							listViewOnline.setAdapter(sa);
						}
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		// }
	}

	public void jump2Home(View view) {
		setContentView(R.layout.activity_images_home);
	}

	public void searchImagesAll(View view) {
		lastWord = wtv.getText().toString();
//		lastSearch = 3;
		// public void onClick(View v) {
		// new LoadLoacalPhotoCursorTask(LocalImages.this, tv0);
		// TODO Auto-generated method stub
		// printImage();

		listViewOnline.post(new Runnable() {

			@Override
			public void run() {
				l2b.clear();
				// TODO Auto-generated method stub
				// List<String>uris = getSystemPhotoList(LocalImages.this);
				List<String> uris = LoadLocalImageUris.getAllLocalImagesUri(HomeAndLocal.this);
				// tv0.setText("size is " + uris.size());
				// for(String u : uris) {
				// tv0.setText(tv0.getText()+"\n"+u);
				// }
				try {
					final List<Map<String, Object>> imageList = new ArrayList<Map<String, Object>>();
					// String word = "SSS";
					String word = wtv.getText().toString();
					for (int i = 0; i < uris.size(); ++i) {
						final String iUrl = uris.get(i);
						File ig = new File(iUrl);
						l2b.add(i);
						Map<String, Object> map = new HashMap<String, Object>();
						Bitmap bm = null;
						// bm =
						// BitmapFactory.decodeStream(LocalImages.this.getContentResolver().openInputStream(Uri.parse(uris.get(0))));
						bm = BitmapFactory.decodeFile(iUrl);
						String info = "Image Name:\t" + ig.getName();
						info += "\nDate:\t"
								+ new SimpleDateFormat("yyyy-MM-dd hh:mm").format(new Date(ig.lastModified()));
						info += "\nSize:\t" + (float) (Math.round((ig.length() * 100 / 1024))) / 100 + "KB";
						map.put("title", info);
						// tv.setText(tv.getText().toString() + "\n" + bm.getHeight());
						// byte[] data = NetUtil.doGetImage(iUrl);
						// Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
						// Bitmap bm = new LoadPic().execute(iUrl).get();
						// map.put("image", bm);

						map.put("image", bm);
						// map.put("image", NetUtil.doGetBitmap(iUrl));
						imageList.add(map);
					}
					final String[] from = { "title", "image" };
					final int[] to = { R.id.t0, R.id.imageShow };
					// final SimpleAdapter sa = new SimpleAdapter(Images.this, listMap,
					// R.layout.listview, from, to);
					final SimpleAdapter sa = new SimpleAdapter(HomeAndLocal.this, imageList, R.layout.activity_image_single, from, to);
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
					listViewOnline.post(new Runnable() {
						@Override
						public void run() {
							listViewOnline.setAdapter(sa);
						}
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		// }
	}
	public void jump2List(View view) {
//		jump2Home(view);
		jump2Local(view);
	}
	public void jump2Local(View view) {
		setContentView(R.layout.activity_images_local);
		wtv = (EditText) findViewById(R.id.key);
		new Thread(new Runnable() {

			@Override
			public void run() {
				listViewOnline = (ListView) findViewById(R.id.listOfLocal);
				listViewOnline.post(new Runnable() {
					@Override
					public void run() {
						// 初始化

						listViewOnline.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent, View view, final int position,
									final long id) {
								setContentView(R.layout.activity_image_big);
								final ImageView ivImageShow = (ImageView) findViewById(R.id.ivBig);
								ivImageShow.post(new Runnable() {

									@Override
									public void run() {
										ivImageShow.setImageBitmap(bml.get(l2b.get((int) id)));
									}
								});
							}
						});
					}
				});
			}
		}).start();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	}

	public void jump2Online(View view) {
		Intent intent = new Intent(HomeAndLocal.this, Online.class);
		startActivity(intent);
	}
}
