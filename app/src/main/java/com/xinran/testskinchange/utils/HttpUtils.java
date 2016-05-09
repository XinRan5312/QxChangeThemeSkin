package com.xinran.testskinchange.utils;

import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ren.solid.materialdesigndemo.R;
import ren.solid.materialdesigndemo.SolidApplication;

/**
 * Created by _SOLID
 * Date:2016/3/30
 * Time:15:02
 */
public class HttpUtils {

    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    private Handler mHandler;
    private static volatile HttpUtils mInstance;
    private static final OkHttpClient mOkHttpClient;

    static {
        mOkHttpClient = new OkHttpClient().newBuilder().connectTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).build();
    }

    private HttpUtils() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static HttpUtils getInstance() {
        if (mInstance == null)
            synchronized (HttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new HttpUtils();
                }
            }
        return mInstance;
    }

    /**
     * 不会开启异步线程
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static Response execute(Request request) throws IOException {
        return mOkHttpClient.newCall(request).execute();
    }

    /**
     * 开启异步线程访问网络
     *
     * @param request
     * @param responseCallback
     */
    public static void enqueue(Request request, okhttp3.Callback responseCallback) {
        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }


    public void loadString(final String url, final HttpCallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                try {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onLoading();
                        }
                    });

                    final Response response = execute(request);

                    if (response.isSuccessful()) {//请求成功
                        final String result = response.body().string();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onSuccess(result);
                            }
                        });
                    } else {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onError(new Exception("请求失败"));
                            }
                        });
                    }

                } catch (final IOException e) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onError(e);
                        }
                    });
                }
            }
        }).start();
    }

    /**
     * post JSON data to server
     *
     * @param url
     * @param json
     * @param callBack
     */
    public void post(String url, String json, final HttpCallBack callBack) {

        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = null;
        try {
            response = mOkHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                callBack.onSuccess(response.body().string());
            } else {
                callBack.onError(new Exception("Unexpected code " + response));
            }
        } catch (IOException e) {
            e.printStackTrace();
            callBack.onError(e);
        }

    }

    /**
     * 加载图片
     *
     * @param url the url of image
     * @param iv  ImageView
     */
    public void loadImage(String url, ImageView iv) {
        loadImage(url, iv, false);
    }

    public void loadImage(String url, ImageView iv, boolean isCenterCrop) {
        loadImageWithHolder(url, iv, R.drawable.default_load_img, isCenterCrop);
    }

    /**
     * 加载图片
     *
     * @param url              the url of image
     * @param iv               ImageView
     * @param placeholderResID default image
     */
    public void loadImageWithHolder(String url, ImageView iv, int placeholderResID, boolean isCenterCrop) {
        Picasso.with(SolidApplication.getInstance()).load(url).placeholder(placeholderResID).fit().into(iv);
        RequestCreator creator = Picasso.with(SolidApplication.getInstance()).load(url).placeholder(R.drawable.default_load_img);
        if (isCenterCrop) {
            creator.centerCrop();
        }
        creator.fit().into(iv);
    }


    public interface HttpCallBack {
        void onLoading();

        void onSuccess(String result);

        void onError(Exception e);
    }
}
