package com.hewq.linkgame2.utils;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.hewq.linkgame2.R;
import com.hewq.linkgame2.view.PieceImage;


public class ImageUtil {
	
	
	public static List<PieceImage>createPieceImageList(Context context,int picNum){
		List<PieceImage> retunList=new ArrayList<PieceImage>();
		List<Integer>picList= getBackPictureNums(picNum);
		for(int index=0;index<picList.size();index++){
			Integer picId= picList.get(index);
			Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), picId);
			PieceImage tempPieceImage = new PieceImage();
			tempPieceImage.setBitmap(bitmap);
			tempPieceImage.setId(picId);
			retunList.add(tempPieceImage);
		}
		return retunList;
	}
	
	private static List<Integer> getBackPictureNums(int sumSize){
		int halfSize =sumSize/2;
		List<Integer>pictureList = new ArrayList<Integer>();
		Random random = new Random(System.currentTimeMillis());
		List<Integer> cartoonPicures =getCartoonPicureNums();
		for(int index=0;index<halfSize;index++){
			pictureList.add(cartoonPicures.get(random.nextInt(cartoonPicures.size())));
		}
		pictureList.addAll(pictureList);
		Collections.shuffle(pictureList);
		return pictureList;
	}
	
	private static List<Integer> getCartoonPicureNums(){
		List<Integer> result = new ArrayList<Integer>();
		Field[] fields =R.drawable.class.getFields();
		for(Field field:fields){
			if(field.getName().indexOf("cartoon")!=-1){
				try {
					result.add(field.getInt(R.drawable.class));
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			}
		}
		return result;
	}
}
