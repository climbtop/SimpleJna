package com.jna.demo;

import java.util.Map;
import com.sun.jna.platform.win32.Advapi32Util;
import static com.sun.jna.platform.win32.WinReg.HKEY_CURRENT_USER;
import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

/**
 * 
 * @Describe 操作管理：注册表信息
 * @author ZSS
 * @date 2021年6月3日
 * @time 下午2:10:39
 */
public class WinRegisterTest {

	public static void main(String[] args) {
		System.out.println(Advapi32Util.registryGetStringValue(HKEY_CURRENT_USER,
				"Software\\Microsoft\\Internet Explorer\\Main", "Search Page"));
		System.out.println(Advapi32Util.registryGetStringValue(HKEY_LOCAL_MACHINE,
				"Software\\Microsoft\\Windows\\CurrentVersion\\App Paths\\AcroRd32.exe", ""));
		System.out.println(Advapi32Util.registryGetStringValue(HKEY_LOCAL_MACHINE,
				"Software\\Microsoft\\Windows\\CurrentVersion\\App Paths\\AcroRd32.exe", "Path"));
		//System.out.println(Advapi32Util.registryGetIntValue(HKEY_LOCAL_MACHINE,
		//		"Software\\Wow6432Node\\Javasoft\\Java Update\\Policy", "Frequency"));
		String[] keys = Advapi32Util.registryGetKeys(HKEY_CURRENT_USER,
				"Software\\Microsoft\\Windows\\CurrentVersion\\Internet Settings");
		for (String key : keys) {
			System.out.println(key);
		}
		Map<String, Object> values = Advapi32Util.registryGetValues(HKEY_CURRENT_USER,
				"Software\\Microsoft\\Windows\\CurrentVersion\\Internet Settings");
		for (String value : values.keySet()) {
			System.out.println(value);
		}

		System.out.println(Advapi32Util.registryKeyExists(HKEY_CURRENT_USER, "Software\\RealHowTo"));
		//Advapi32Util.registryCreateKey(HKEY_CURRENT_USER, "Software\\RealHowTo");
		System.out.println(Advapi32Util.registryKeyExists(HKEY_CURRENT_USER, "Software\\RealHowTo"));
		//Advapi32Util.registrySetStringValue(HKEY_CURRENT_USER, "Software\\RealHowTo", "url", "http://www.rgagnon.com");
		System.out.println(Advapi32Util.registryValueExists(HKEY_CURRENT_USER, "Software\\RealHowTo", "url"));
		System.out.println(Advapi32Util.registryValueExists(HKEY_CURRENT_USER, "Software\\RealHowTo", "foo"));
	}

}
