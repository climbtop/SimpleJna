package com.jna.apply;

import java.util.List;
import java.util.function.Consumer;

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
public class KeyboardHook implements Runnable {

	private WinUser.HHOOK hhk;
	private List<Consumer<Integer>> consumers;

	public KeyboardHook(List<Consumer<Integer>> consumers) {
		this.consumers = consumers;
	}

	private WinUser.LowLevelKeyboardProc keyboardProc = new WinUser.LowLevelKeyboardProc() {
		private WinUser.KBDLLHOOKSTRUCT latest;
		@Override
		public LRESULT callback(int nCode, WPARAM wParam, WinUser.KBDLLHOOKSTRUCT event) {
			boolean longPressed = (latest!=null && latest.vkCode==event.vkCode && latest.flags == event.flags);
			latest = event;
			if (nCode >= 0 && event.flags <128 && !longPressed ) {
				if (consumers != null && consumers.size() > 0) {
					consumers.forEach(consumer -> consumer.accept(event.vkCode));
				}
			}
			return User32.INSTANCE.CallNextHookEx(hhk, nCode, wParam, null);
		}
	};

	public void run() {
		setHookOn();
	}

	public void setHookOn() {
		HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);
		hhk = User32.INSTANCE.SetWindowsHookEx(User32.WH_KEYBOARD_LL, keyboardProc, hMod, 0);

		int result;
		WinUser.MSG msg = new WinUser.MSG();
		while ((result = User32.INSTANCE.GetMessage(msg, null, 0, 0)) != 0) {
			if (result == -1) {
				setHookOff();
				break;
			} else {
				User32.INSTANCE.TranslateMessage(msg);
				User32.INSTANCE.DispatchMessage(msg);
			}
		}
	}

	public void setHookOff() {
		if (hhk == null)
			return;
		User32.INSTANCE.UnhookWindowsHookEx(hhk);
		System.exit(0);
	}

}
