package com.blueice.mobilelottery.engine;

import com.blueice.mobilelottery.bean.User;
import com.blueice.mobilelottery.net.protocal.Message;

/**
 * 用户业务类的接口。
 *
 */
public interface UserEngine {
	
	/**
	 * 用户登陆
	 * @param user 用户对象。
	 * @return 服务器端的Message结果。
	 */
	public Message login(User user);

	/**
	 * 获取用户的余额。
	 * @param user 用户信息。
	 * @return 余额信息。
	 */
	public Message getBalance(User user);

	
}
