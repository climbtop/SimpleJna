package com.jna.core;

import java.util.Collection;
import java.util.function.Consumer;

import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.INPUT;

/**
 * 
 * @Describe 
 * @author ZSS
 * @date 2021年10月13日
 * @time 上午9:50:31
 */
public class JnaHotKey {

	//ALT+F4
	public static synchronized void combinKeyInput(long ctrl, long keys) {
		INPUT[] ipArr = new INPUT[]{new INPUT(),new INPUT(),new INPUT(),new INPUT()};
		arrayset(ipArr, ip -> {ip.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);});
		
		arrayset(ipArr, ip -> {ip.input.setType("ki");});
		arrayset(ipArr, ip -> {ip.input.ki.wScan = new WinDef.WORD(0);});
		arrayset(ipArr, ip -> {ip.input.ki.time = new WinDef.DWORD(0);});
		arrayset(ipArr, ip -> {ip.input.ki.dwExtraInfo = new BaseTSD.ULONG_PTR(0);});
		
		arrayset(ipArr, new int[] {0, 2}, ip -> {ip.input.ki.wVk = new WinDef.WORD(ctrl);});  //ctrl
		arrayset(ipArr, new int[] {1, 3}, ip -> {ip.input.ki.wVk = new WinDef.WORD(keys);});  //keys
		
		arrayset(ipArr, new int[] {0, 1}, ip -> {ip.input.ki.dwFlags = new WinDef.DWORD(0);});//keydown
		arrayset(ipArr, new int[] {2, 3}, ip -> {ip.input.ki.dwFlags = new WinDef.DWORD(2);});//keyup
		
		if (ipArr == null || ipArr.length == 0) return;
        User32.INSTANCE.SendInput(new WinDef.DWORD(ipArr.length), ipArr, ipArr[0].size());
        
    }
	
	public static synchronized void arrayset(INPUT[] array, Consumer<INPUT> consumer) {
		arrayset(array, null, consumer);
	}
	
	public static synchronized void arrayset(INPUT[] array, int[] ids, Consumer<INPUT> consumer) {
		if (array == null || consumer == null) {
			return;
		}
		if (ids == null) {
			ids = new int[array.length];
			for (int i = 0; i < ids.length; i++) {
				ids[i] = i;
			}
		}
		for (int i = 0; i < ids.length; i++) {
			if (ids[i] < array.length) {
				consumer.accept(array[ids[i]]);
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(5*1000);
				} catch (Exception e) {
				}
				
				combinKeyInput(162, 86);//Ctrl + V
			}
		}).start();
		Thread.currentThread().join();
		
	}
}
