package com.jna.demo;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.Secur32;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.W32APIOptions;

/**
 * 
 * @Describe 获取登录：域\用户名
 * @author ZSS
 * @date 2021年6月3日
 * @time 下午2:25:41
 */
public class WindowsUtilsTest {

	static final Secur32 secur32 = (Secur32) Native.load("secur32", Secur32.class,
			W32APIOptions.DEFAULT_OPTIONS);

	public static String getCurrentUserName() {
		char[] userNameBuf = new char[10000];
		IntByReference size = new IntByReference(userNameBuf.length);
		boolean result = secur32.GetUserNameEx(Secur32.EXTENDED_NAME_FORMAT.NameSamCompatible, userNameBuf, size);

		if (!result)
			throw new IllegalStateException("Cannot retreive name of the currently logged-in user");

		return new String(userNameBuf, 0, size.getValue());
	}

	public static void main(String[] args) {
		System.out.println(WindowsUtilsTest.getCurrentUserName()); // output [domain]\[user]
	}
}
