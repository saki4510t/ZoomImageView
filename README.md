ZoomImageView
=============

Extends ImageView widget of Android to support zooming/dragging/rotating image

 Usage:
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
