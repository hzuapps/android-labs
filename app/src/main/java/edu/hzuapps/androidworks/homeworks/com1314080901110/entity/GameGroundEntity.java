package edu.hzuapps.androidworks.homeworks.com1314080901110.entity;

import java.util.Random;

/**
 * Created by Administrator on 2016/5/4.
 */
public class GameGroundEntity {
//    游戏难度
    private int level;
//    每局游戏雷的总数
    private int allBoomsCount;
//    只能存入GridEntity类型对象的二维数组
    private GridEntity [][] allGrid;
//    一个规范化的边界格子对象
    private GridEntity sideGrid=new GridEntity(false,false,true,0);
//    含参构造方法
    public GameGroundEntity(int level){
        this.level=level;
        this.allBoomsCount=level;
        this.allGrid=new GridEntity[level+2][level+2];
        init();
    }
    /**
    * 方法：初始化游戏
    * 1.给allGrid数组赋值
    * 2.初始化雷所在的格子位置
    * 3.为每个能见到的格子标注周围八个格子的地雷数量
     * */
    public void init(){
        for(int i=0;i<level+2;i++){
            for(int j=0;j<level+2;j++){
//                找出边界格子
                if(i==0||j==0||i==level+1||j==level+1){
                    allGrid[i][j]=sideGrid;
                }
                else{
                    allGrid[i][j]=new GridEntity();
                }
            }
        }
//        随机分配雷的横纵坐标，范围(1,level+1), (1,level)
        Random random=new Random(System.currentTimeMillis());
        for(int i=0;i<allBoomsCount;i++){
            int x=random.nextInt(level)+1;
            int y=random.nextInt(level)+1;
//            排除重复放置雷的可能性
            if(allGrid[x][y].isBoom()){
                i--;
                continue;
            }
            else {
                allGrid[x][y].setIsBoom(true);
            }
        }
//        设置每个格子显示的雷的数量
        for(int i=1;i<=level;i++) {
            for(int j=1;j<=level;j++) {
                setBoomCount(allGrid[i][j],i,j);
            }
        }
    }
    /**
     *通过获取x，y坐标来得到可见格子对象，计算该格子周围雷的数量，设置雷的数量
     * @param grid 需要被计算的格子
     * @param x 格子的横坐标
     * @param y 格子的纵坐标
     * */
    public void setBoomCount(GridEntity grid,int x,int y){
//        初始格子周围雷的数量
        int boomCount=0;
        for(int minX=x-1;minX<=x+1;minX++) {
            for(int minY=y-1;minY<=y+1;minY++) {
                if(allGrid[minX][minY].isBoom()){
                    boomCount++;
                }
            }
        }
        grid.setBoomsCount(boomCount);
    }
//    判定游戏结果
    public boolean isWin(){
//        遍历计算出未被点击过的格子个数
        int grids=0;
        for(int i=1;i<=level;i++){
            for(int j=1;j<=level;j++){
                if(!allGrid[i][j].isShow()){
                    grids++;
                }
            }
        }
//        如果雷的总数和剩余未被点击的格子数量一致，则返回true
        return allBoomsCount==grids;
    }
//    为Adapter适配器编写获取格子对象的方法，position为格子编号
    public GridEntity getEntity(int position){
        GridEntity grid=null;
        grid=allGrid[position/level+1][position%level+1];
        return grid;
    }
    /**
     * 方法：点击格子点到雷，展现所有的雷
     * */
    public void showAllBooms(){
        for(int i=1;i<=level;i++){
            for(int j=1;j<=level;j++){
//                对象是雷且未被点击
                if(allGrid[i][j].isBoom()&&!allGrid[i][j].isShow()){
//                    展示该格子
                    allGrid[i][j].setIsShow(true);
//                    将状态设置为自动展示
                    allGrid[i][j].setIsAutoShow(true);
                }
            }
        }
    }
    /**
     * 方法：检查标记是否正确
     * 1.标记正确，插旗
     * 2.标记错误，红叉
     * */
    public void checkFlag(){
       for(int i=1;i<=level;i++){
           for(int j=1;j<=level;j++){
               if(allGrid[i][j].isFlag()&&allGrid[i][j].isBoom()){
                   allGrid[i][j].setIsFlagWrong(false);
               }
               else if(allGrid[i][j].isFlag()&&!allGrid[i][j].isBoom()){
                   allGrid[i][j].setIsFlagWrong(true);
               }
           }
       }
    }
    /**
     *方法：显示没有雷的区域
     **/
    public void showNotBoomArea(int position){
        if(position<0||position>=level*level){
            return;
        }
        int x=position/level+1;
        int y=position%level+1;
        if(allGrid[x][y].isSide()){
            return;
        }
        if(allGrid[x][y].getBoomsCount()!=0||allGrid[x][y].isShow()){
            allGrid[x][y].setIsShow(true);
            return;
        }
        allGrid[x][y].setIsShow(true);
        for(int ii=x-1;ii<=x+1;ii++){
            for(int jj=y-1;jj<=y+1;jj++){
                if(ii<=0||jj<=0||ii>=level+1||jj>=level+1){
                    continue;
                }else{
                    showNotBoomArea((ii-1)*level+(jj-1));
                }
            }
        }
    }
}
