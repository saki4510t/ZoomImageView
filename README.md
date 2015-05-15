ZoomImageView
=============

Extends ImageView widget of Android to support zooming/dragging/rotating image

Copyright (c) 2014-2015 saki t_saki@serenegiant.com

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

All files in the folder are under this Apache License, Version 2.0.

 Usage:
=============

 Double touch and holds while more tha long press timeout, start rotating</br>
 When start rotating, color reversing effect execute as a default visual effect</br>
 You can cancel the default feedback and execute addition feedback in the callback listener</br>
  
 Double touch and pinch in/out zoom the image in/out.
  
 Single touch with move drags the image.
  
 Single touch and hold while more than long press timeouit, reset the zooming/draging/rotaing
 and fit the image in this view.</br>
 You can reset zooming/moving/rotating with calling #reset programmatically
 Limitation of this class:
 This class internally use image matrix to zoom/drag/rotate image,
 therefore you can not set matrix with #setImageMatrix.
 If you set matrix, it is ignored and has no effect.
 
 And the scaleType is fixed to ScaleType.MATRIX. If you set other than ScaleType.MATRIX,
 in xml or programmatically, it is ignored and has no effect.
 
 This class requires API level >= 8

###2014/02/21
First release

###2014/02/26
Support drawables other than BitmapDrawable

###2014/04/03
add #getScale/#getTranslate/#getRotation method

###2015/05/15
add sample project(Android Studio)
change license to Apache 2.0
fixed typo



