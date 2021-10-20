package com.jna.simple;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinUser;

/**
 * 
 * @Describe 遍历所有窗口，获取进程窗口标题，并关闭指定窗口；
 * @author ZSS
 * @date 2021年6月3日
 * @time 下午1:55:29
 */
public class WinPIDTest {

	public static void main(String[] args) {
		User32.INSTANCE.EnumWindows((hWnd, data) -> {
			char[] nameArr = new char[1024];
			User32.INSTANCE.GetWindowText(hWnd, nameArr, nameArr.length);
			String nameStr = Native.toString(nameArr);
			
			if(nameStr.length()>0) {
				System.out.println("窗口标题:  "+nameStr);
			}
			
			if("Postman1".equals(nameStr)) {
				User32.INSTANCE.PostMessage(hWnd, WinUser.WM_CLOSE, null, null);
				System.out.println("Close "+nameStr);
			}
			
			return true;
		}, null);
		
	}
	
	

}
