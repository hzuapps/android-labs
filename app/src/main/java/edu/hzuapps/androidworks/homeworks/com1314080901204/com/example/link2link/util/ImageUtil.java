package com.example.link2link.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.link2link.R;
import com.example.link2link.view.PieceImage;

public class ImageUtil {

	// 创建一个List用于保存所有连连看图片资源的ID
	private static List<Integer> imageValues = getImageValues();

	// 获取连连看所有图片的ID
	public static List<Integer> getImageValues() {
		try {
			// 通过反射得到R.drawable所有的属性，即获取到drawable目录下的所有图片
			Field[] drawableFields = R.drawable.class.getFields();
			// 创建一个List用于保存需要被用于连连看的图片的ID
			List<Integer> resourceValues = new ArrayList<Integer>();
			for (Field field : drawableFields) {
				// 如果该Field的名称以p_开头，则确认属于方块图片,将它添加到List中
				if (field.getName().indexOf("p_") != -1) {
					resourceValues.add(field.getInt(R.drawable.class));
				}
			}
			return resourceValues;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 随机从sourceValues的集合中获取size张图片，返回的结果为随机获取的图片的集合
	 * 
	 * @param sourceValues
	 * @param size
	 * @return
	 */
	public static List<Integer> getRandomValues(List<Integer> sourceValues,
			int size) {
		// 创建一个随机生成器，用于随机获取某一张图片
		Random random = new Random();
		// 创建结果集合，用于保存随机获取到的图片
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < size; i++) {
			try {
				// 随机获取一个在0到sourceValues.size()之间的数值
				int index = random.nextInt(sourceValues.size());
				// 根据索引找到对应的图片资源ID
				Integer image = sourceValues.get(index);
				// 将它添加到结果集中
				result.add(image);
			} catch (IndexOutOfBoundsException e) {
				return result;
			}

		}
		return result;
	}

	/**
	 * 根据size从drawable目录下获取图片资源，将它们复制一份，并打散它们在集合中的位置
	 * 
	 * @param size
	 * @return
	 */
	public static List<Integer> getPlayValues(int size) {
		if (size % 2 != 0) {
			// 如果size除以2不等于0，将size加1
			size += 1;
		}
		// 从所有的图片中随机获取size的一半数量
		List<Integer> playImageValues = getRandomValues(imageValues, size / 2);
		// 将playImageValues集合的元素增加一倍，保证所有图片都有与之配对的图片
		playImageValues.addAll(playImageValues);
		// 将集合中所有图片打散
		Collections.shuffle(playImageValues);
		return playImageValues;
	}

	/**
	 * 将图片Id集合转换成PieceImage对象集合
	 * 
	 * @param context
	 * @param size
	 * @return
	 */
	public static List<PieceImage> getPlayImages(Context context, int size) {
		// 获取图片ID组成的集合
		List<Integer> resourceValues = getPlayValues(size);
		// 保存封装成PieceImage对象的图片资源集合
		List<PieceImage> result = new ArrayList<PieceImage>();
		for (Integer value : resourceValues) {
			// 根据图片的ID将图片封装成Bitmap对象
			Bitmap bm = BitmapFactory.decodeResource(context.getResources(),
					value);
			// 封装图片Id和图片
			PieceImage pieceImage = new PieceImage(bm, value);
			result.add(pieceImage);
		}
		return result;
	}

	// 获取选中标识的图片
	public static Bitmap getSelectImage(Context context) {
		Bitmap bm = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.selected);
		return bm;
	}
}
