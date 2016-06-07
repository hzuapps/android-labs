/**
 *
 */
package org.crazyit.event;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Description:
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br/>Copyright (C), 2001-2014, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class Net1314080903227PlaneView extends View
{
	public float currentX;
	public float currentY;
	Bitmap plane;
	// 定义、并创建画笔
	Paint p = new Paint();
	public Net1314080903227PlaneView(Context context)
	{
		super(context);
		// 定义飞机图片
		plane = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.plane);
		setFocusable(true);
	}

	@Override
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		// 绘制飞机
		canvas.drawBitmap(plane, currentX, currentY, p);
	}
}

