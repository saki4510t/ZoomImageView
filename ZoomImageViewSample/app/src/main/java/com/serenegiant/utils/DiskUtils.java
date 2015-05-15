package com.serenegiant.utils;

/*
 * ZoomingImageView for Android:
 * Copyright(c) 2014-2015 t_saki@serenegiant.com
 *
 * This class extends ImageView to support zooming/draging/rotating of image with touch.
 * You can replace usual ImageView with this class.
 *
 * File name: DiskUtils.java
*/
/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
*/

import android.content.Context;
import android.os.Environment;

import java.io.File;

public final class DiskUtils {
	/**
	 * キャッシュディレクトリのフルパスを取得する
	 * 外部ストレージが使える場合は外部ストレージのキャッシュディレクトリを、そうでない場合は内部のディレクトリを使う
	 * @param context
	 * @param uniqueName
	 * @return キャッシュディレクトリパス
	 */
	public static String getCacheDir(final Context context, final String uniqueName) {
		// 外部ストレージが使える場合はそっちのディレクトリを、そうでない場合は内部のディレクトリを使う
		final String cachePath =
				(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
				 && !Environment.isExternalStorageRemovable()	// これが使えるのはAPI9以上
				) ? context.getExternalCacheDir().getPath() : context.getCacheDir().getPath();
		return cachePath + File.separator + uniqueName;
	}

	/**
     * 画像のディレクトリパスを取得する
     * @return
	 * @throws Exception
     */
    public static String getExternalPhotoDir(final Context context) {
        String dirPath = null;
        File photoDir = null;
        // 標準のカメラ撮影時の保存場所を取得
        final File externalPublicDir
          	= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (externalPublicDir.canWrite()) {
        	photoDir = new File(externalPublicDir.getPath() + "/" + context.getPackageName());
        } else {
        	// 標準のカメラ撮影時の保存場所を取得出来なかった時
    	    final File extStorageDir = Environment.getExternalStorageDirectory();
    	    if (extStorageDir.canWrite()) {
    	    	photoDir = new File(extStorageDir.getPath() + "/" + context.getPackageName());
    	    }
        }
        if (photoDir != null) {
            if (!photoDir.exists()) {
                photoDir.mkdirs();
            }
            if (photoDir.canWrite()) {
                dirPath = photoDir.getPath();
            }
        }
        return dirPath;
    }

    public static String getInternalPhotoDir(final Context context) {
        String dirPath = null;
       	final File photoDir = context.getDir("photo", Context.MODE_PRIVATE);
        if (photoDir != null) {
            if (!photoDir.exists()) {
                photoDir.mkdirs();
            }
            if (photoDir.canWrite()) {
                dirPath = photoDir.getPath();
            }
        }
        return dirPath;
    }

}
