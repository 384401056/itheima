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
	 * @return 服务器端的ServerResponsMessage结果。
	 */
	public Message login(User user);
	
}
