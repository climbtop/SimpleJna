package com.jna.apply;

import java.io.File;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.Advapi32;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Kernel32Util;
import com.sun.jna.platform.win32.Secur32;
import com.sun.jna.platform.win32.WinBase.PROCESS_INFORMATION;
import com.sun.jna.platform.win32.WinBase.STARTUPINFO;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.W32APIOptions;

/**
 * 
 * @Describe
 * @author ZSS
 * @date 2021年10月20日
 * @time 下午3:55:10
 */
public class StartProcess {

	@SuppressWarnings("unused")
	private static interface MoreAdvApi32 extends Advapi32 {
		MoreAdvApi32 INSTANCE = (MoreAdvApi32) Native.load("AdvApi32", MoreAdvApi32.class);

		boolean CreateProcessWithLogonW(WString lpUsername, WString lpDomain, WString lpPassword, int dwLogonFlags,
				WString lpApplicationName, WString lpCommandLine, int dwCreationFlags, Pointer lpEnvironment,
				WString lpCurrentDirectory, STARTUPINFO lpStartupInfo, PROCESS_INFORMATION lpProcessInfo);

		public static final int LOGON_WITH_PROFILE = 0x00000001;
		public static final int LOGON_NETCREDENTIALS_ONLY = 0x00000002;

		int CREATE_NO_WINDOW = 0x08000000;
		int CREATE_UNICODE_ENVIRONMENT = 0x00000400;
		int CREATE_NEW_CONSOLE = 0x00000010;
		int DETACHED_PROCESS = 0x00000008;
	}

	private static final Secur32 secur32 = (Secur32) Native.load("secur32", Secur32.class,
			W32APIOptions.DEFAULT_OPTIONS);

	public static String[] getDomainUser() {
		char[] userNameBuf = new char[512];
		IntByReference size = new IntByReference(userNameBuf.length);
		boolean result = secur32.GetUserNameEx(Secur32.EXTENDED_NAME_FORMAT.NameSamCompatible, userNameBuf, size);

		if (!result) return null;
		
		String domainUserArr = new String(userNameBuf, 0, size.getValue());
		int splitIndex = domainUserArr.indexOf(File.separator);
		return new String[] {domainUserArr.substring(0, splitIndex), domainUserArr.substring(splitIndex+1)};
	}
	
	public static boolean exec(String exeFile) {
		WString nullW = null;
		PROCESS_INFORMATION processInformation = new PROCESS_INFORMATION();
		STARTUPINFO startupInfo = new STARTUPINFO();

		String exeDir = exeFile.substring(0, exeFile.lastIndexOf(File.separator) + 1);
		String[] domainUser = getDomainUser();

		boolean result = MoreAdvApi32.INSTANCE.CreateProcessWithLogonW(
				new WString(domainUser[1]), // user
				new WString(domainUser[0]), // domain , null if local
				new WString("1qaz@WSX"), // password
				MoreAdvApi32.LOGON_WITH_PROFILE, // dwLogonFlags
				nullW, // lpApplicationName
				new WString(exeFile), // command line
				MoreAdvApi32.CREATE_NEW_CONSOLE, // dwCreationFlags
				null, // lpEnvironment
				new WString(exeDir), // directory
				startupInfo, processInformation);

		if (!result) {
			int error = Kernel32.INSTANCE.GetLastError();
	        System.out.println("" + error +": "+Kernel32Util.formatMessageFromLastErrorCode(error));
			return false;
		} else {
			return true;
		}
	}
}
