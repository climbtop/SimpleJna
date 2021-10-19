package com.jna.apply;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 
 * @Describe 
 * @author ZSS
 * @date 2021年10月19日
 * @time 下午1:38:50
 */
public class ApplyTest {

	public static void main(String[] args) {
		
		KeyCase keyCase1 = new KeyCase("case1");
		keyCase1.setting(500, 3000);
		keyCase1.setWhenKeys(new int[] {162, 162});
		keyCase1.setQuitKeys(new int[] {162});
		keyCase1.setWhenCall(arr->{KeyPress.apply(91, 82);}); //Win+R
		keyCase1.setQuitCall(arr->{KeyPress.apply(164, 115);}); //Alt+F4
		
		KeyCase keyCase2 = new KeyCase("case2");
		keyCase2.setting(500, 3000);
		keyCase2.setWhenKeys(new int[] {163, 163});
		keyCase2.setQuitKeys(new int[] {163});
		keyCase2.setWhenCall(arr->{KeyPress.apply(91, 82);}); //Win+R
		keyCase2.setQuitCall(arr->{KeyPress.apply(164, 115);}); //Alt+F4
		
		List<Consumer<Integer>> consumers = new ArrayList<Consumer<Integer>>();
		consumers.add((code ->{keyCase1.accept(code);}));
		consumers.add((code ->{keyCase2.accept(code);}));
		consumers.add((code ->{System.out.println(code+"->"+((char)code.intValue()));}));
		
		KeyboardHook kbhook = new KeyboardHook(consumers);
		new Thread(kbhook).start();
		
	}

}
