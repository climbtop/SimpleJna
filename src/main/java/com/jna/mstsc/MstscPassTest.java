package com.jna.mstsc;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Formatter;

import com.sun.jna.platform.win32.Crypt32;
import com.sun.jna.platform.win32.Crypt32Util;
import com.sun.jna.platform.win32.WinCrypt;

/**
 * mstsc C:/a.rdp /console /v: xxx.xxx.xxx.xxx:3389
 * *.rdp 文件密码加密方法
 */
public class MstscPassTest {

	public static void main(String[] args) {
		String password = "2wsx!QAZ";

		String str0 = cryptRdpPassword0(password);
		System.out.println(str0);

		String str1 = cryptRdpPassword(password); //校验可行
		System.out.println(str1);

		
		String str2 = DecodeRdpPassword(str0);
		System.out.println(str2);
		
		String str3 = DecodeRdpPassword(str1);
		System.out.println(str3);

	}

	private static String cryptRdpPassword0(String password) {
		WinCrypt.DATA_BLOB pDataIn = new WinCrypt.DATA_BLOB(password.getBytes(Charset.forName("UTF-16LE")));
		WinCrypt.DATA_BLOB pDataEncrypted = new WinCrypt.DATA_BLOB();
		Crypt32.INSTANCE.CryptProtectData(pDataIn, "psw", null, null, null, 1, pDataEncrypted);
		StringBuffer epwsb = new StringBuffer();
		byte[] pwdBytes = new byte[pDataEncrypted.cbData];
		pwdBytes = pDataEncrypted.getData();
		Formatter formatter = new Formatter(epwsb);
		for (final byte b : pwdBytes) {
			formatter.format("%02X", b);
		}
		formatter.close();
		return epwsb.toString();
	}

	private static String cryptRdpPassword(String password) {
		try {
			return ToHexString(Crypt32Util.cryptProtectData(password.getBytes(("UTF-16LE"))));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "ERROR";
		}
	}

	private static String ToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		for (byte b : bytes) {
			formatter.format("%02X", b);
		}
		formatter.close();
		return sb.toString();
	}

	private static String DecodeRdpPassword(String password) {
		try {
			return new String(Crypt32Util.cryptUnprotectData(toBytes(password)), "UTF-16LE");
		} catch (Exception e1) {
			e1.printStackTrace();
			return "ERROR";
		}
	}

	private static byte[] toBytes(String str) {
		if (str == null || str.trim().equals("")) {
			return new byte[0];
		}
		byte[] bytes = new byte[str.length() / 2];
		for (int i = 0; i < str.length() / 2; i++) {
			String subStr = str.substring(i * 2, i * 2 + 2);
			bytes[i] = (byte) Integer.parseInt(subStr, 16);
		}
		return bytes;
	}

}