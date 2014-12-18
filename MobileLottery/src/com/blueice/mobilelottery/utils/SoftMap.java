package com.blueice.mobilelottery.utils;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashMap;

/**
 * 
 * 创建一个存储弱引用界面的HashMap,并重载其中的put(),get()和containsKey（）方法。用它来替代原来使用的HashMap。
 *
 * @param <K> key.
 * @param <V> BaseUI.
 */
public class SoftMap<K,V> extends HashMap<K, V> {
	
	
	private HashMap<K, SoftValue<K,V>> temp; //创建一个存储SoftReference对象的集合。
	private ReferenceQueue<V> queue;
	
	public SoftMap() {
		queue = new ReferenceQueue<V>();
		temp = new HashMap<K, SoftValue<K,V>>();
	}

	@Override
	public V put(K key, V value) {
		
		
		//将UI对象使用弱引用封装。并存入temp集合中。
		SoftValue<K,V> sr = new SoftValue<K,V>(key,value,queue); 
		
		temp.put(key, sr); 

		return null;
	}
	
	@Override
	public V get(Object key) {

		clearRs();
		
		SoftValue<K,V> sr = temp.get(key);
		//如果sr对象不为空(有可能已经被GC回收了)，则取出其内部封装的对象，并返回。
		if(sr!=null){
			return sr.get();
		}
		
		return null;
	}
	
	@Override
	public boolean containsKey(Object key) {
		
		//调用上面的get()方法，判断是否有这个对象，不为null返回true;为null返回false;
		return get(key)!=null;
	}
	
	
	private void clearRs(){
		
		SoftValue<K,V> poll = (SoftValue<K,V>) queue.poll();//请理SoftReference中的V
		
		while(poll!=null){
//			temp.remove(key);
			temp.remove(poll.key);//清理temp中已经被清理了V的SoftReference.（袋子）
			poll = (SoftValue<K,V>) queue.poll();
		}
		
	}
	
	/**
	 * 增强版的袋子，加了一个Key,为了方便清理。
	 * @author ServerAdmin
	 *
	 * @param <K>
	 * @param <V>
	 */
	private class SoftValue<K,V> extends SoftReference<V>{

		private Object key;
		
		public SoftValue(K key,V r, ReferenceQueue<? super V> q) {
			super(r, q);
			this.key = key;
		}
		
		
	}

}





