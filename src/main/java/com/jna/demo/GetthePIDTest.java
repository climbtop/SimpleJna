package com.jna.demo;

/**
 * 
 * @Describe 获取进程ID
 * @author ZSS
 * @date 2021年6月3日
 * @time 下午2:28:10
 */
public class GetthePIDTest {
	static class JavaHowTo {
		public native long getCurrentProcessId();

		static {
			System.loadLibrary("jni2");
		}
	}
	
	public static void main(String[] args) {
		JavaHowTo jht = new JavaHowTo();
		System.out.println("Press Any key...");
		java.io.BufferedReader input = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
		try {
			input.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(jht.getCurrentProcessId());
	}


}
