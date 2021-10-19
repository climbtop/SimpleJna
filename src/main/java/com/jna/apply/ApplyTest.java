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
		
		KeySeries keyCase1 = new KeySeries();
		keyCase1.setWhenKeys(new int[] {162, 162});
		keyCase1.setQuitKeys(new int[] {162});
		keyCase1.setWhenCall(arr->{KeyPress.apply(91, 82);}); //Win+R
		keyCase1.setQuitCall(arr->{KeyPress.apply(164, 115);}); //Alt+F4
		
		KeySeries keyCase2 = new KeySeries();
		keyCase2.setWhenKeys(new int[] {163, 163});
		keyCase2.setQuitKeys(new int[] {163});
		keyCase2.setWhenCall(arr->{KeyPress.apply(91, 82);}); //Win+R
		keyCase2.setQuitCall(arr->{KeyPress.apply(164, 115);}); //Alt+F4
		
		List<Consumer<Integer>> consumers = new ArrayList<Consumer<Integer>>();
		consumers.add((code ->{keyCase1.accept(code);}));
		consumers.add((code ->{keyCase2.accept(code);}));
		
		KeyboardHook kbhook = new KeyboardHook(consumers);
		new Thread(kbhook).start();
		
	}

}
