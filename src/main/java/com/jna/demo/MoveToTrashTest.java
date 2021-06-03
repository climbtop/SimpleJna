package com.jna.demo;

import java.io.File;
import java.io.IOException;

import com.sun.jna.platform.FileUtils;

/**
 * 
 * @Describe 
 * @author ZSS
 * @date 2021年6月3日
 * @time 下午2:20:46
 */
public class MoveToTrashTest {

    public static void main(String[] args){
        FileUtils fileUtils = FileUtils.getInstance();
        if (fileUtils.hasTrash()) {
            try {
                fileUtils.moveToTrash( new File[] {new File("c:/temp/dummy.txt") });
            }
            catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        else {
            System.out.println("No Trash available");
        }
    }

}
