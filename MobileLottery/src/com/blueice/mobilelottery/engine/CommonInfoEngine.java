package com.blueice.mobilelottery.engine;

import com.blueice.mobilelottery.net.protocal.Message;

/**
 * 公共的业务处理。
 *
 */
public interface CommonInfoEngine {

	/**
	 * 获取彩种当前销售期信息。
	 * @param integer 彩种编号.
	 * @return
	 */
	Message getCurrentIssueInfo(Integer integer);

}
