package com.blueice.mobilelottery.utils;

import java.util.Properties;

/**
 * 生成业务接口实例的工厂类。
 *
 */
public class EngineFactory {

	private static Properties properties;
	
	//加载配置文件。
	static{
		
		try {
			
			properties = new Properties();
			properties.load(EngineFactory.class.getClassLoader().getResourceAsStream("engine.properties"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private EngineFactory() {
	}

	/**
	 * 加载要获取的接口实现类。
	 * 
	 * @param clazz 接口的类文件名
	 * @return 实现类的实例。
	 */
	public static <T> T Instance(Class<T> clazz) {
		try {
			//得到抽象类的名称。再从properties文件中找出实现类的全名。通过反射得到实例并返回。
			String key = clazz.getSimpleName();
			String className = properties.getProperty(key);

			return (T) Class.forName(className).newInstance();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}

}
