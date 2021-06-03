package com.jna.demo;

import com.sun.jna.platform.win32.Advapi32Util;
import static com.sun.jna.platform.win32.WinReg.HKEY_CURRENT_USER;
import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

/**
 * 
 * @Describe 
 * @author ZSS
 * @date 2021年6月3日
 * @time 下午2:21:53
 */
public class GetCpuTypeTest {

    public static void main(String ... args) {
        System.out.println(Advapi32Util.registryGetStringValue
           (HKEY_LOCAL_MACHINE,
              "HARDWARE\\DESCRIPTION\\System\\CentralProcessor\\0\\",
            "ProcessorNameString"));
        System.out.println(Advapi32Util.registryGetStringValue
                (HKEY_LOCAL_MACHINE,
                   "HARDWARE\\DESCRIPTION\\System\\CentralProcessor\\0\\",
                 "Identifier"));
      }

}
