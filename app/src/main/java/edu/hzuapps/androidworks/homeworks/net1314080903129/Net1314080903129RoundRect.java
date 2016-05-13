package edu.hzuapps.androidworks.homeworks.net1314080903129;

/**
 * Created by Administrator on 2016/4/25.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;


public class Net1314080903129RoundRect {

    private int width;
    private int height;
    private float cornerRadius;

    /**
     * 用于初始化圆角矩形基本参数
     *
     * @param width        图片宽度
     * @param height       图片高度
     * @param cornerRadius 圆角半径
     */

    public Net1314080903129RoundRect(int width, int height, float cornerRadius) {
        this.width = width;
        this.height = height;
        this.cornerRadius = cornerRadius;
    }

    /**
     * 用于把普通图片转换为圆角矩形图像
     *
     * @param imageID 图片资源ID
     * @param context 上下文对象
     * @return output 转换后的圆角矩形图像
     */

    Bitmap toRoundRect(Context context, int imageID) {


        //创建位图对象
        Bitmap photo = BitmapFactory.decodeResource(context.getResources(), imageID);


        //根据源文件新建一个darwable对象
        Drawable imageDrawable = new BitmapDrawable(photo);

        // 新建一个新的输出图片
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        // 新建一个矩形
        RectF outerRect = new RectF(0, 0, width, height);

        // 产生一个红色的圆角矩形
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        canvas.drawRoundRect(outerRect, cornerRadius, cornerRadius, paint);

        // 将源图片绘制到这个圆角矩形上
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        imageDrawable.setBounds(0, 0, width, height);
        canvas.saveLayer(outerRect, paint, Canvas.ALL_SAVE_FLAG);
        imageDrawable.draw(canvas);
        canvas.restore();

        return output;
    }

}
