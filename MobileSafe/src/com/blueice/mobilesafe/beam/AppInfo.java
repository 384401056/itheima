package com.blueice.mobilesafe.beam;

import android.graphics.drawable.Drawable;

/**
 * 手机内应用程序的Bean
 *
 */
public class AppInfo {

	private Drawable icon;
	private String name;
	private String packName;
	private boolean inRom;
	private boolean userApp;
	
	public Drawable getIcon() {
		return icon;
	}
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPackName() {
		return packName;
	}
	public void setPackName(String packName) {
		this.packName = packName;
	}
	public boolean isInRom() {
		return inRom;
	}
	public void setInRom(boolean inRom) {
		this.inRom = inRom;
	}
	public boolean isUserApp() {
		return userApp;
	}
	public void setUserApp(boolean userApp) {
		this.userApp = userApp;
	}
	
	@Override
	public String toString() {
		return "AppInfo [name=" + name + ", packName=" + packName + ", inRom="
				+ inRom + ", userApp=" + userApp + "]";
	}
	
	
	
}
