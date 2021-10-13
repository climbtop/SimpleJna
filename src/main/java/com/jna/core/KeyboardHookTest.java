package com.jna.core;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HMODULE;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinUser;

/**
 * 
 * @Describe 键盘钩子：监听键盘按键
 * @author ZSS
 * @date 2021年6月3日
 * @time 下午2:43:02
 */
public class KeyboardHookTest {

	static class KeyboardHook implements Runnable{
		private WinUser.HHOOK hhk;
	 
		//钩子回调函数
		private WinUser.LowLevelKeyboardProc keyboardProc = new WinUser.LowLevelKeyboardProc() {
			@Override
			public LRESULT callback(int nCode, WPARAM wParam, WinUser.KBDLLHOOKSTRUCT event) {
				// 输出按键值和按键时间
				if (nCode >= 0) {
					String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
					
					System.out.println(event.vkCode+" -> "+(char)event.vkCode);
					//System.out.println(time + " KEY: " + (char)event.vkCode);
					// 按下ESC退出
					if(event.vkCode==27) KeyboardHook.this.setHookOff();
				}
				return User32.INSTANCE.CallNextHookEx(hhk, nCode, wParam, null);
			}
		};
	 
		//使用线程
		public void run() {
			setHookOn();
		}
		
		// 安装钩子
		public void setHookOn(){
			System.out.println("Hook On!");
	 
			HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);
			hhk = User32.INSTANCE.SetWindowsHookEx(User32.WH_KEYBOARD_LL, keyboardProc, hMod, 0);
	 
			int result;
			WinUser.MSG msg = new WinUser.MSG();
			//让程序在GetMessage阻塞，实际没有执行
			while ((result = User32.INSTANCE.GetMessage(msg, null, 0, 0)) != 0) {
				if (result == -1) {
					setHookOff();
					break;
				} else {
					User32.INSTANCE.TranslateMessage(msg); //将虚拟键消息转换为字符消息,它并不会修改原有的消息，它只是产生新的消息并投递到消息队列中
					User32.INSTANCE.DispatchMessage(msg); //该函数调度一个消息给窗口程序。通常调度从GetMessage取得的消息
				}
			}
		}
		
		// 移除钩子并退出
		public void setHookOff(){
			if(hhk==null) return;
			System.out.println("Hook Off!");
			User32.INSTANCE.UnhookWindowsHookEx(hhk);
			System.exit(0);
		}
	}
	
	
	public static void main(String[] args) {
		KeyboardHook kbhook = new KeyboardHook();
		new Thread(kbhook).start();
	}

}
