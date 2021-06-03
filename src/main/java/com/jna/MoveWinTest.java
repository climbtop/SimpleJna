package com.jna;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

/**
 * 
 * @Describe 
 * @author ZSS
 * @date 2021年6月3日
 * @time 下午1:32:23
 */
public class MoveWinTest {

	public static void main(String[] args) {
        WinDef.HWND hwnd = User32.INSTANCE.FindWindow(null, "Postman");
        if (hwnd == null) {
            System.out.println("Postman is not running");
        } else {
            WinDef.RECT win_rect = new  WinDef.RECT();
            User32.INSTANCE.GetWindowRect(hwnd, win_rect);
            int win_width = win_rect.right - win_rect.left;
            int win_height = win_rect.bottom - win_rect.top;

            User32.INSTANCE.MoveWindow(hwnd, 300, 100, win_width, win_height, true);
            
            System.out.println(String.format("Postman rect: %s,%s", win_width, win_height));
        }
	}

}
