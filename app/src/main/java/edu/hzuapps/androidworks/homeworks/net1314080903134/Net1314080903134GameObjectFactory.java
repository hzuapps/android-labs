package com.alonso.factory;

import com.alonso.object.BigPlane;//大型敌机
import com.alonso.object.BossBullet;//Boss子弹
import com.alonso.object.BossPlane;//Boss敌机
import com.alonso.object.BulletGoods;//子弹补给
import com.alonso.object.GameObject;//游戏对象
import com.alonso.object.MiddlePlane;//中型敌机
import com.alonso.object.MissileGoods;//导弹补给
import com.alonso.object.MyBullet;//玩家子弹
import com.alonso.object.MyBullet2;//玩家子弹2
import com.alonso.object.MyPlane;//玩家飞机
import com.alonso.object.SmallPlane;//小型敌机

import android.content.res.Resources;

/*游戏对象的工厂类*/
public class Net1314080903134GameObjectFactory {
	//创建小型敌机的方法
	public GameObject createSmallPlane(Resources resources){
		return new SmallPlane(resources);
	}
	//创建中型敌机的方法
	public GameObject createMiddlePlane(Resources resources){
		return new MiddlePlane(resources);
	}
	//创建大型敌机的方法
	public GameObject createBigPlane(Resources resources){
		return new BigPlane(resources);
	}
	//创建BOSS敌机的方法
	public GameObject createBossPlane(Resources resources){
		return new BossPlane(resources);
	}
	//创建玩家飞机的方法
	public GameObject createMyPlane(Resources resources){
		return new MyPlane(resources);
	}
	//创建玩家子弹
	public GameObject createMyBullet(Resources resources){
		return new MyBullet(resources);
	}
	//创建玩家子弹
	public GameObject createMyBullet2(Resources resources){
		return new MyBullet2(resources);
	}
	//创建BOSS子弹
	public GameObject createBossBullet(Resources resources){
		return new BossBullet(resources);
	}
	//创建导弹物品
	public GameObject createMissileGoods(Resources resources){
		return new MissileGoods(resources);
	}
	//创建子弹物品
	public GameObject createBulletGoods(Resources resources){
		return new BulletGoods(resources);
	}
}
