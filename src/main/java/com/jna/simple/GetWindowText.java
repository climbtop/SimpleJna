package com.jna.simple;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;

/**
 * 
 * @Describe 
 * @author ZSS
 * @date 2022年5月25日
 * @time 下午5:44:40
 */
public class GetWindowText {

	private static final int MAX_TITLE_LENGTH = 1024;
	
	public static void main(String[] args) throws Exception {
	
	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	    LocalDateTime now = LocalDateTime.now();
	    TimerTask task = new TimerTask() {
	        @Override
	        public void run() {
	            char[] buffer = new char[MAX_TITLE_LENGTH * 2];
	            HWND hwnd = User32.INSTANCE.GetForegroundWindow();
	            User32.INSTANCE.GetWindowText(hwnd, buffer, MAX_TITLE_LENGTH);
	            System.out.println("Active window title: " + Native.toString(buffer));
	            try {
	                BufferedWriter bw = new BufferedWriter(
	                    new FileWriter(
	                        new File("d:/output/useracivity.txt"),
	                        true
	                    )
	                );
	                bw.write(Native.toString(buffer) + " time was " + dtf.format(now));
	                bw.newLine();
	                bw.close();
	            } catch (Exception e) {
	            }
	        }
	    };
	
	    Timer timer = new Timer();
	    timer.schedule(task, new Date(), 5000);
	
	}
}
