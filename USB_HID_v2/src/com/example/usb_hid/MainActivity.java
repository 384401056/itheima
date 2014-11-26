package com.example.usb_hid;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.R.integer;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class MainActivity extends Activity {
	
	private static final String TAG = "MainActivity"; //记录标识
	private Button btnGetInfo;     //获取信息按钮
	private Button btnRecv;        //接收数据按钮
	private Button btnSend;        //发送数据按钮
    private ListView lsv1;         //显示USB信息的列表
    private TextView tvRecv;
    private EditText etSend;
    
    private UsbManager manager;       //USB管理对象.
    private UsbDevice mUsbDevice;     //USB设备对象.
    private UsbInterface mInterface;  //USB接口对象. 
    private UsbDeviceConnection mDeviceConnection; //USB设备连接对象。
    
    private UsbEndpoint epOut; //USB设备端点(OUT)
    private UsbEndpoint epIn;  //USB设备端点(IN)
	
    
    private HashMap<String, UsbDevice> deviceList;
    private Iterator<UsbDevice> deviceIterator;     //USB设备类型的遍历器。
    private ArrayList<String> USBDeviceList;        //存放USB设备信息的列表。
    
    private byte[] SendBuff;    //发送信息buffer
    private byte[] RecvBuff = new byte[1024];    //接收信息buffer
    
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnGetInfo = (Button)findViewById(R.id.btnGetInfo);
		btnRecv = (Button)findViewById(R.id.btreceive);
		lsv1 = (ListView)findViewById(R.id.lsv1);
		tvRecv = (TextView)findViewById(R.id.tvreceive);
		etSend = (EditText)findViewById(R.id.etsend);
		
		
		btnSend = (Button)findViewById(R.id.btsend);
		
		btnGetInfo.setOnClickListener(new GetInfoListener());
		btnRecv.setOnClickListener(new RecvListener());
		btnSend.setOnClickListener(new SendListener());
		

	}
	
	/*
	 * 获取设备信息按钮事件。
	 */
	class GetInfoListener implements OnClickListener{
		int ret = -100;
		@Override
		public void onClick(View v) {
			// 获取USB设备
	        manager = (UsbManager) getSystemService(Context.USB_SERVICE);
	        if (manager == null) {
	            return;
	        } else {
	            Log.i(TAG, "usb设备：" + String.valueOf(manager.toString()));
	        }
	        
	        //返回当前连接的USB设备的key和value(设备对象)，如果没有连接任何设备或者USB host模式没有开启，返回值就为空。
	        deviceList = manager.getDeviceList(); 
	        
	        deviceIterator = deviceList.values().iterator();
	        
	        USBDeviceList = new ArrayList<String>(); //存放USB设备信息的列表。
	        
	        while (deviceIterator.hasNext()) {
	            UsbDevice device = deviceIterator.next();
	   
	            USBDeviceList.add(String.valueOf("VID:"+device.getVendorId()));
	            USBDeviceList.add(String.valueOf("PID:"+device.getProductId()));
	            
	            USBDeviceList.add(String.valueOf("DeviceName:"+device.getDeviceName()));
	            USBDeviceList.add(String.valueOf("DeviceProtocol:"+device.getDeviceProtocol()));
	            USBDeviceList.add(String.valueOf("DeviceId:"+device.getDeviceId()));
	            USBDeviceList.add(String.valueOf("DeviceClass:"+device.getDeviceClass()));
	            
	            mUsbDevice = device;
	            
	            /*获取符合要求的设备
	             * 
	            if (device.getVendorId() == 5692 && device.getProductId() == 21617) {
	            	mUsbDevice = device;
	            }else{
	            	Toast.makeText(MainActivity.this, "设备不匹配", Toast.LENGTH_SHORT).show();
	            }
	            */
	        }
	        // 创建一个ArrayAdapter,
	        lsv1.setAdapter(new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,USBDeviceList));
		}
		
	}
	
	/*
	 * 接收数据按钮事件
	 */
	class RecvListener implements OnClickListener{
		@Override
		public void onClick(View arg0) {	
			if(mUsbDevice != null){
				// 获取设备接口，一般都是一个接口，你可以打印getInterfaceCount()方法查看接口的个数，在这个接口上有两个端点，OUT 和 IN 
				//for(int i=0;i<mUsbDevice.getInterfaceCount();){
					mInterface = mUsbDevice.getInterface(0);
				//}
				if(mInterface != null){
					epIn = mInterface.getEndpoint(0);

					//int retIn = epIn.getDirection();
					//int retOut = epOut.getDirection();
					//tvRecv.setText("epIn:" + String.valueOf(retIn)+"  epOut:"+String.valueOf(retOut));
				}else{
					Toast.makeText(MainActivity.this, "设备接口没有找到！", Toast.LENGTH_SHORT).show();
					return;
				}
				
			}else{
				Toast.makeText(MainActivity.this, "设备没有找到！", Toast.LENGTH_SHORT).show();
				return;
			}

			// 判断是否有权限
			if (manager.hasPermission(mUsbDevice)){
				//打开USB设备，并建立连接
				mDeviceConnection = manager.openDevice(mUsbDevice);
				
				if(mDeviceConnection == null)
					return;
				if(mDeviceConnection.claimInterface(mInterface, true)){
					
					//传输成功则返回所传输的字节数组的长度，失败则返回负数。
					int ret = mDeviceConnection.bulkTransfer(epIn, RecvBuff, RecvBuff.length, 1000);
					
					String isoString = "";
					try {
						isoString = new String(RecvBuff,"iso-8859-1");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}

					tvRecv.setText(isoString);
					
					etSend.setText(String.valueOf(ret));
					
					
				}else{
					Toast.makeText(MainActivity.this, "claimInterface() 失败！", Toast.LENGTH_SHORT).show();
				}
			}
			else{
				Toast.makeText(MainActivity.this, "没有操作设备的权限！", Toast.LENGTH_SHORT).show();
				return;
			}
			
			mDeviceConnection.releaseInterface(mInterface);
			mDeviceConnection.close();
		}
		
	}
	
	/*
	 * 发送数据按钮事件
	 */
	class SendListener implements OnClickListener{
		public void onClick(View v) {
			if(mUsbDevice != null){
				mInterface = mUsbDevice.getInterface(0);
				if(mInterface != null){
					epOut = mInterface.getEndpoint(1);

				}else{
					Toast.makeText(MainActivity.this, "设备接口没有找到！", Toast.LENGTH_SHORT).show();
					return;
				}
				
			}else{
				Toast.makeText(MainActivity.this, "设备没有找到！", Toast.LENGTH_SHORT).show();
				return;
			}

			// 判断是否有权限
			if (manager.hasPermission(mUsbDevice)){
				//打开USB设备，并建立连接
				mDeviceConnection = manager.openDevice(mUsbDevice);
				
				if(mDeviceConnection == null)
					return;
				if(mDeviceConnection.claimInterface(mInterface, true)){
						
					String isoString = "Gaoyanbin";
					try {
						SendBuff = isoString.getBytes("iso-8859-1");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					
					
					//传输成功则返回所传输的字节数组的长度，失败则返回负数。
					int ret = mDeviceConnection.bulkTransfer(epOut, SendBuff, SendBuff.length, 1000);
					
					try {
						tvRecv.setText(new String(SendBuff, "iso-8859-1"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					
					etSend.setText(String.valueOf(ret));
					
				}else{
					Toast.makeText(MainActivity.this, "claimInterface() 失败！", Toast.LENGTH_SHORT).show();
				}

			}
			else{
				Toast.makeText(MainActivity.this, "没有操作设备的权限！", Toast.LENGTH_SHORT).show();
				return;
			}
			
			mDeviceConnection.releaseInterface(mInterface);
			mDeviceConnection.close();
			
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}































