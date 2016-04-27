package edu.hzuapps.androidworks.homeworks.net1314080903203;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by XIAOCONG_HOME on 2016/4/27 0027.
 */
public class Net1314080903203Image3DView extends ImageView {
    /**
     * 旋转角度的基准值
     */
    private static final float BASE_DEGREE = 50f;
    /**
     * 旋转深度的基准值
     */
    private static final float BASE_DEEP = 150f;
    private Camera mCamera;
    private Matrix mMaxtrix;
    private Bitmap mBitmap;
    /**
     * 当前图片对应的下标
     */
    private int mIndex;
    /**
     * 在前图片在X轴方向滚动的距离
     */
    private int mScrollX;
    /**
     * Image3DSwitchView控件的宽度
     */
    private int mLayoutWidth;
    /**
     * 当前图片的宽度
     */
    private int mWidth;
    /**
     * 当前旋转的角度
     */
    private float mRotateDegree;
    /**
     * 旋转的中心点
     */
    private float mDx;
    /**
     * 旋转的深度
     */
    private float mDeep;

    public Net1314080903203Image3DView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCamera = new Camera();
        mMaxtrix = new Matrix();
    }

    /**
     * 初始化Image3DView所需要的信息，包括图片宽度，截取背景图等。
     */
    public void initImageViewBitmap() {
        if (mBitmap == null) {
            setDrawingCacheEnabled(true);
            buildDrawingCache();
            mBitmap = getDrawingCache();
        }
        mLayoutWidth = Net1314080903203Image3DSwitchView.mWidth;
        mWidth = getWidth() + Net1314080903203Image3DSwitchView.IMAGE_PADDING * 2;
    }

    /**
     * 设置旋转角度。
     *
     * @param index
     *            当前图片的下标
     * @param scrollX
     *            当前图片在X轴方向滚动的距离
     */
    public void setRotateData(int index, int scrollX) {
        mIndex = index;
        mScrollX = scrollX;
    }

    /**
     * 回收当前的Bitmap对象，以释放内存。
     */
    public void recycleBitmap() {
        if (mBitmap != null && !mBitmap.isRecycled()) {
            mBitmap.recycle();
        }
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        mBitmap = null;
        initImageViewBitmap();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        mBitmap = null;
        initImageViewBitmap();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        mBitmap = null;
        initImageViewBitmap();
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        mBitmap = null;
        initImageViewBitmap();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap == null) {
            // 如果Bitmap对象还不存在，先使用父类的onDraw方法进行绘制
            super.onDraw(canvas);
        } else {
            if (isImageVisible()) {
                // 绘图时需要注意，只有当图片可见的时候才进行绘制，这样可以节省运算效率
                computeRotateData();
                mCamera.save();
                mCamera.translate(0.0f, 0.0f, mDeep);
                mCamera.rotateY(mRotateDegree);
                mCamera.getMatrix(mMaxtrix);
                mCamera.restore();
                mMaxtrix.preTranslate(-mDx, -getHeight() / 2);
                mMaxtrix.postTranslate(mDx, getHeight() / 2);
                canvas.drawBitmap(mBitmap, mMaxtrix, null);
            }
        }
    }

    /**
     * 在这里计算所有旋转所需要的数据。
     */
    private void computeRotateData() {
        float degreePerPix = BASE_DEGREE / mWidth;
        float deepPerPix = BASE_DEEP / ((mLayoutWidth - mWidth) / 2);
        switch (mIndex) {
            case 0:
                mDx = mWidth;
                mRotateDegree = 360f - (2 * mWidth + mScrollX) * degreePerPix;
                if (mScrollX < -mWidth) {
                    mDeep = 0;
                } else {
                    mDeep = (mWidth + mScrollX) * deepPerPix;
                }
                break;
            case 1:
                if (mScrollX > 0) {
                    mDx = mWidth;
                    mRotateDegree = (360f - BASE_DEGREE) - mScrollX * degreePerPix;
                    mDeep = mScrollX * deepPerPix;
                } else {
                    if (mScrollX < -mWidth) {
                        mDx = -Net1314080903203Image3DSwitchView.IMAGE_PADDING * 2;
                        mRotateDegree = (-mScrollX - mWidth) * degreePerPix;
                    } else {
                        mDx = mWidth;
                        mRotateDegree = 360f - (mWidth + mScrollX) * degreePerPix;
                    }
                    mDeep = 0;
                }
                break;
            case 2:
                if (mScrollX > 0) {
                    mDx = mWidth;
                    mRotateDegree = 360f - mScrollX * degreePerPix;
                    mDeep = 0;
                    if (mScrollX > mWidth) {
                        mDeep = (mScrollX - mWidth) * deepPerPix;
                    }
                } else {
                    mDx = -Net1314080903203Image3DSwitchView.IMAGE_PADDING * 2;
                    mRotateDegree = -mScrollX * degreePerPix;
                    mDeep = 0;
                    if (mScrollX < -mWidth) {
                        mDeep = -(mWidth + mScrollX) * deepPerPix;
                    }
                }
                break;
            case 3:
                if (mScrollX < 0) {
                    mDx = -Net1314080903203Image3DSwitchView.IMAGE_PADDING * 2;
                    mRotateDegree = BASE_DEGREE - mScrollX * degreePerPix;
                    mDeep = -mScrollX * deepPerPix;
                } else {
                    if (mScrollX > mWidth) {
                        mDx = mWidth;
                        mRotateDegree = 360f - (mScrollX - mWidth) * degreePerPix;
                    } else {
                        mDx = -Net1314080903203Image3DSwitchView.IMAGE_PADDING * 2;
                        mRotateDegree = BASE_DEGREE - mScrollX * degreePerPix;
                    }
                    mDeep = 0;
                }
                break;
            case 4:
                mDx = -Net1314080903203Image3DSwitchView.IMAGE_PADDING * 2;
                mRotateDegree = (2 * mWidth - mScrollX) * degreePerPix;
                if (mScrollX > mWidth) {
                    mDeep = 0;
                } else {
                    mDeep = (mWidth - mScrollX) * deepPerPix;
                }
                break;
        }
    }

    /**
     * 判断当前图片是否可见。
     *
     * @return 当前图片可见返回true，不可见返回false。
     */
    private boolean isImageVisible() {
        boolean isVisible = false;
        switch (mIndex) {
            case 0:
                if (mScrollX < (mLayoutWidth - mWidth) / 2 - mWidth) {
                    isVisible = true;
                } else {
                    isVisible = false;
                }
                break;
            case 1:
                if (mScrollX > (mLayoutWidth - mWidth) / 2) {
                    isVisible = false;
                } else {
                    isVisible = true;
                }
                break;
            case 2:
                if (mScrollX > mLayoutWidth / 2 + mWidth / 2
                        || mScrollX < -mLayoutWidth / 2 - mWidth / 2) {
                    isVisible = false;
                } else {
                    isVisible = true;
                }
                break;
            case 3:
                if (mScrollX < -(mLayoutWidth - mWidth) / 2) {
                    isVisible = false;
                } else {
                    isVisible = true;
                }
                break;
            case 4:
                if (mScrollX > mWidth - (mLayoutWidth - mWidth) / 2) {
                    isVisible = true;
                } else {
                    isVisible = false;
                }
                break;
        }
        return isVisible;
    }

}
