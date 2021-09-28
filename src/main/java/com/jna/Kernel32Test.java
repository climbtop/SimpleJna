package com.jna;

import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;
import com.sun.jna.win32.StdCallLibrary;

/**
 * 
 * @Describe 从内核读取时间例子(当天日期)
 * @author ZSS
 * @date 2021年9月28日
 * @time 下午2:27:13
 */

public class Kernel32Test {

	//kernel32.dll uses the __stdcall calling convention (check the function
	//declaration for "WINAPI" or "PASCAL"), so extend StdCallLibrary
	//Most C libraries will just extend com.sun.jna.Library,
	public static interface Kernel32 extends StdCallLibrary { 
	// Method declarations, constant and structure definitions go here
		
		Kernel32 INSTANCE = (Kernel32)
			    Native.load("kernel32", Kernel32.class);
		// Optional: wraps every call to the native library in a
		// synchronized block, limiting native calls to one at a time
		Kernel32 SYNC_INSTANCE = (Kernel32)
		    Native.synchronizedLibrary(INSTANCE);
		
		@FieldOrder({ "wYear", "wMonth", "wDayOfWeek", "wDay", "wHour", "wMinute", "wSecond", "wMilliseconds" })
		public static class SYSTEMTIME extends Structure {
		    public short wYear;
		    public short wMonth;
		    public short wDayOfWeek;
		    public short wDay;
		    public short wHour;
		    public short wMinute;
		    public short wSecond;
		    public short wMilliseconds;
		}

		void GetSystemTime(SYSTEMTIME result);
	}
	
	
	public static void main(String[] args) {
		Kernel32 lib = Kernel32.SYNC_INSTANCE;
		Kernel32.SYSTEMTIME time = new Kernel32.SYSTEMTIME();
		lib.GetSystemTime(time);

		System.out.println("Today's day: " + time.wDay);
	}
			
}







