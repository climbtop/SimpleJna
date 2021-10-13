package com.jna.core;

import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.INPUT;

/**
 * 
 * @Describe 模拟键盘按下、释放
 * @author ZSS
 * @date 2021年6月3日
 * @time 下午1:58:22
 */
public class JnaKit {

	public static synchronized void keyPressed(char ch) {
        INPUT ip = new INPUT();
         
        ip.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
        ip.input.setType("ki");
        ip.input.ki.wScan = new WinDef.WORD(0);
        ip.input.ki.time = new WinDef.DWORD(0);
        ip.input.ki.dwExtraInfo = new BaseTSD.ULONG_PTR(0);
        ip.input.ki.wVk = new WinDef.WORD(ch);     // 0x41 'A'
        ip.input.ki.dwFlags = new WinDef.DWORD(0); // keydown
         
        User32.INSTANCE.SendInput(new WinDef.DWORD(1), (INPUT[])ip.toArray(1), ip.size());
    }
     
	public static synchronized void keyRealesed(char ch) {
        INPUT ip = new INPUT();
         
        ip.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
        ip.input.setType("ki");
        ip.input.ki.wScan = new WinDef.WORD(0);
        ip.input.ki.time = new WinDef.DWORD(0);
        ip.input.ki.dwExtraInfo = new BaseTSD.ULONG_PTR(0);
        ip.input.ki.wVk = new WinDef.WORD(ch);    // 0x41 'A'
        ip.input.ki.dwFlags = new WinDef.DWORD(WinUser.KEYBDINPUT.KEYEVENTF_KEYUP); // keyup
         
        User32.INSTANCE.SendInput(new WinDef.DWORD(1), (INPUT[])ip.toArray(1), ip.size());
    }
	
}
