package wyf.ytl;

public class WelcomeViewGoThread extends Thread{
	int sleepSpan = 200;//睡眠的毫秒数
	private boolean flag = true;
	int status = 0;
	PushBoxActivity pushBoxActivity;//activity的引用
	public WelcomeViewGoThread(PushBoxActivity pushBoxActivity){
		this.pushBoxActivity = pushBoxActivity;
	}
	public void setFlag(boolean flag){
		this.flag = flag;
	}
	public void run() {//重写的run方法
		while(flag){
			switch(status){
				case 0://木门运动
					pushBoxActivity.welcomeView.woodLeftX -= 2;
					pushBoxActivity.welcomeView.woodRightX += 2;
					if(pushBoxActivity.welcomeView.woodLeftX<-90){
						status = 1;
					}
					break;
				case 1://铁门运动
					pushBoxActivity.welcomeView.ironY -= 8;
					if(pushBoxActivity.welcomeView.ironY<-380){
						status = 2;
					}
					break;
				case 2:
					pushBoxActivity.welcomeView.wallLeftX -= 3;
					pushBoxActivity.welcomeView.wallRightX += 3;
					if(pushBoxActivity.welcomeView.wallLeftX<-90){
						status = 3;
					}
					break;
				case 3:
					this.flag = false;
					pushBoxActivity.myHandler.sendEmptyMessage(1);//向主activity发送Handler消息
					break;
			}
			try{
				Thread.sleep(sleepSpan);//睡眠
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}