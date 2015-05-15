package com.serenegiant.zoomimageviewsample;

/*
 * ZoomingImageView for Android:
 * Copyright(c) 2014-2015 t_saki@serenegiant.com
 *
 * This class extends ImageView to support zooming/draging/rotating of image with touch.
 * You can replace usual ImageView with this class.
 *
 * File name: MainActivity.java
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

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.serenegiant.graphics.BitmapHelper;
import com.serenegiant.utils.CameraIntentHelper;

import java.io.FileNotFoundException;
import java.io.IOException;


public class MainActivity extends Activity {
	private static final boolean DEBUG = false;
	private static final String TAG = "MainActivity";

	private static final int REQUEST_GALLERY_GET_IMAGE = 123;
	private static final int REQUEST_CAMERA_GET_IMAGE = 234;

	/**
	 * hold ZoomImageView
	 * if you don't need to use ZoomImageView specific feature
	 * like OnStartRotationListener, #getCurrentImage, #getRotation, etc.
	 * if can just use as ImageView.
	 */
	private ImageView mImageView;
	/**
	 * uri that ZoomImageView load bitmap from
	 */
	private Uri mUri;
	/**
	 * loaded bitmap
	 * this is for bitmap recycling to avoid OOM when changing bitmap of ImageView
	 */
	private Bitmap mBitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState != null) {
			mUri = savedInstanceState.getParcelable("URI");
		}
		mImageView = (ImageView)findViewById(R.id.imageview);
		Button button = (Button)findViewById(R.id.camera_button);
		button.setOnClickListener(mOnClickListener);
		button = (Button)findViewById(R.id.gallery_button);
		button.setOnClickListener(mOnClickListener);
		mImageView.setImageURI(mUri);
	}

	@Override
	public void onSaveInstanceState(final Bundle args) {
		if (DEBUG) Log.v(TAG, "onSaveInstanceState:" + args);
		args.putParcelable("URI", mUri);
		super.onSaveInstanceState(args);
	}

	@Override
	public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		if (DEBUG) Log.v(TAG, "onActivityResult:" + resultCode + ",intent=" + data);
		if (resultCode != Activity.RESULT_OK) {
			// if failed/canceled to take/select photo

			if (mUri != null) {
				final ContentResolver contentResolver = getContentResolver();
				try{
					contentResolver.delete(mUri, null, null);
				} catch (final Exception e) {
					// there is no photo
				}
				mUri = null;
			}
			return;
		}

		final ContentResolver cr = getContentResolver();
		Uri resultUri = null;
		switch (requestCode) {
		case REQUEST_CAMERA_GET_IMAGE:
		{
			// success taking photo
			if (data != null && data.getData() != null) {
				resultUri = data.getData();
			} else {
				resultUri = mUri;
			}
			break;
		}
		case REQUEST_GALLERY_GET_IMAGE:
		{
			// success selecting photo
			if (data != null && data.getData() != null) {
				resultUri = data.getData();
			}
			break;
		}
		}
		if (resultUri != null) {
			if (mBitmap != null) {
				mBitmap.recycle();
				mBitmap = null;
			}
			// resultUri is URI of photo
			try {
				mBitmap = BitmapHelper.asBitmap(cr, resultUri);
				mUri = resultUri;
//				if (requestCode == REQUEST_CAMERA_GET_IMAGE)
//					cr.delete(resultUri, null, null);
			} catch (final FileNotFoundException e) {
			} catch (final IOException e) {
			}
		}
		if (DEBUG) Log.v(TAG, "onActivityResult:result=" + resultUri + " bitmap=" + mBitmap);
		if (mBitmap != null) {
			mImageView.setImageBitmap(mBitmap);
		}
	}

	private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(final View view) {
			switch (view.getId()) {
			case R.id.camera_button:
			{
				final Intent intent = CameraIntentHelper.getCameraIntent(MainActivity.this, null);
				mUri = intent.getParcelableExtra(MediaStore.EXTRA_OUTPUT);
				startActivityForResult(intent, REQUEST_CAMERA_GET_IMAGE);
				break;
			}
			case R.id.gallery_button:
			{
				final Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(intent, REQUEST_GALLERY_GET_IMAGE);
				break;
			}
			}
		}
	};


}
