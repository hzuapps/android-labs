package edu.hzuapps.androidworks.homework.com1314080901142;

/************************
 * 类名：GridPack<br>
 * 功能：方块信息封装类<br>
 * 创建事件：
 *
 * @author xll
 ************************/
public class GridPack {
    /**
     * 方块图片的停留时间
     */
    private int time;
    /**
     * 判断方块是否有图片
     */
    private int isImg;

    /**
     * 函数名称：setTime<br>
     * 函数功能：设置方块图片的停留时间
     *
     * @param int time
     * @return 空
     * @author xll
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * 函数名称：getTime<br>
     * 函数功能：获得方块图片的停留时间
     *
     * @param 空
     * @return int
     * @author xll
     */
    public int getTime() {
        return time;
    }

    /**
     * 函数名称：setIsImg<br>
     * 函数功能：设置“方块是否有图片”的信息
     *
     * @param int isImg
     * @return 空
     * @author xll
     */
    public void setIsImg(int isImg) {
        this.isImg = isImg;
    }

    /**
     * 函数名称：getIsImg<br>
     * 函数功能：获得“方块是否有图片”的信息
     *
     * @param 空
     * @return int
     * @author xll
     */
    public int getIsImg() {
        return isImg;
    }
}
