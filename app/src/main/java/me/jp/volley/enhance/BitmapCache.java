package me.jp.volley.enhance;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

/**
 * 网络图片缓存
 * 
 * @author ChenLong
 * 
 */
public class BitmapCache extends LruCache<String, Bitmap> implements ImageCache
{
	private static BitmapCache mInstance;

	/**
	 * Use 1/8th of the available memory for this memory cache.
	 * 
	 * @return
	 */
	public static int getDefaultLruCacheSize(Context context)
	{
		int memCache = ((ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
		return 1024 * 1024 * memCache / 8;
	}

	public BitmapCache(Context context)
	{
		
		this(getDefaultLruCacheSize(context));
	}

	public BitmapCache(int maxSize)
	{
		super(maxSize);
	}

	public static BitmapCache getInstance(Context context)
	{
		if (mInstance == null)
		{
			mInstance = new BitmapCache(context);
		}
		return mInstance;
	}

	@Override
	public Bitmap getBitmap(String url)
	{
		return get(url);
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap)
	{
		put(url, bitmap);
	}

}