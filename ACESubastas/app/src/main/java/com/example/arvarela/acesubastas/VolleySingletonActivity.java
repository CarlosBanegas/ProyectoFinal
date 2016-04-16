package com.example.arvarela.acesubastas;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by ARVARELA on 3/11/2016.
 */
public class VolleySingletonActivity {
   private static VolleySingletonActivity mInstance = null;
    private RequestQueue mRQ;
    private ImageLoader mIL;

    private VolleySingletonActivity(){
        mRQ = Volley.newRequestQueue(ListCar.getAppContext());
        mIL = new ImageLoader(this.mRQ,new ImageLoader.ImageCache(){
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap){
                mCache.put(url,bitmap);
            }
            public Bitmap getBitmap(String url){
                return mCache.get(url);
            }
        });
    }
    public static VolleySingletonActivity getInstance(){
        if (mInstance == null){
            mInstance = new VolleySingletonActivity();
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
       return mRQ;
    }
    public ImageLoader getImageLoader(){
        return mIL;
    }
}
