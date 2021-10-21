package com.jna;

import com.sun.jna.platform.win32.W32Service;
import com.sun.jna.platform.win32.W32ServiceManager;
import com.sun.jna.platform.win32.Winsvc;

/**
 * 使用JNA启动/停止服务
 */
public class WinServicesTest {

	public static void main(String[] args) {
		W32ServiceManager serviceManager = new W32ServiceManager();
		serviceManager.open(Winsvc.SC_MANAGER_ALL_ACCESS); //打开服务控制管理器
		W32Service service = serviceManager.openService("QcDBS", Winsvc.SC_MANAGER_ALL_ACCESS);
		service.stopService();
		//service.startService();
		service.close();
		serviceManager.close();
		
	}

}
