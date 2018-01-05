package com.example.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class LoadLocalImageUris {
public static List<String> getAllLocalImagesUri(final Activity at) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                List<MediaBean> mediaBeen = new ArrayList<>();
//                HashMap<String,List<MediaBean>> allPhotosTemp = new HashMap<>();//所有照片
            	List<String>uris = new ArrayList<String>();
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                String[] projImage = { MediaStore.Images.Media._ID
                        , MediaStore.Images.Media.DATA
                        ,MediaStore.Images.Media.SIZE
                        ,MediaStore.Images.Media.DISPLAY_NAME};
                Cursor mCursor = at.getContentResolver().query(mImageUri,
                        projImage,
                        MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"},
                        MediaStore.Images.Media.DATE_MODIFIED+" desc");
 
                if(mCursor!=null){
                    while (mCursor.moveToNext()) {
                        // 获取图片的路径
                        String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                        uris.add(path);
//                        int size = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Images.Media.SIZE))/1024;
//                        String displayName = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
//                        //用于展示相册初始化界面
//                        mediaBeen.add(new MediaBean(MediaBean.Type.Image,path,size,displayName));
//                        // 获取该图片的父路径CursorLoader名
//                        String dirPath = new File(path).getParentFile().getAbsolutePath();
//                        //存储对应关系
//                        if (allPhotosTemp.containsKey(dirPath)) {
//                            List<MediaBean> data = allPhotosTemp.get(dirPath);
//                            data.add(new MediaBean(MediaBean.Type.Image,path,size,displayName));
//                            continue;
//                        } else {
//                            List<MediaBean> data = new ArrayList<>();
//                            data.add(new MediaBean(MediaBean.Type.Image,path,size,displayName));
//                            allPhotosTemp.put(dirPath,data);
//                        }
                    }
                    mCursor.close();
                }
                return uris;
//                //更新界面
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        //...
//                    }
//                });
//            }
//        }).start();
    }
}