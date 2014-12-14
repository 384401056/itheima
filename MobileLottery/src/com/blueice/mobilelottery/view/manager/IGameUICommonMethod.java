package com.blueice.mobilelottery.view.manager;

/**
 * 玩法界面中的共公方法接口,与玩法相关的界面要实现此接口。
 */
public interface IGameUICommonMethod {
	
	/**
	 * 清空选号。
	 */
	void clearBall();
	
	/**
	 * 选号完成。
	 */
	void selectDone();
}
