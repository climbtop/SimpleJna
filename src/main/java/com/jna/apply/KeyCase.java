package com.jna.apply;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

/**
 * 
 * @Describe 
 * @author ZSS
 * @date 2021年10月18日
 * @time 上午10:09:21
 */
public class KeyCase {
	private String  caseName;
	
	private boolean whenFlag;
	private long    spanTime;
	private long    spanBegin;
	
	private int   whenIdx;
	private int[] whenKeys;
	private Consumer<int[]> whenCall;
	
	private boolean quitFlag;
	private long    quitTime;
	private long    quitBegin;
	
	private int   quitIdx;
	private int[] quitKeys;
	private Consumer<int[]> quitCall;
	
	private Queue<Integer> backKeys;

	public KeyCase(String caseName) {
		this.caseName = caseName;
		this.spanTime = 600L;
		this.quitTime = 2000L;
		this.backKeys = new LinkedList<Integer>();
		initial();
	}
	
	public void setting(long spanTime, long quitTime) {
		this.spanTime = spanTime;
		this.quitTime = quitTime;
	}
	
	public String toString() {
		return String.format("{'caseName':'%s', 'whenFlag':'%s', 'quitFlag':'%s',"
				+ " 'whenIdx':'%s', 'quitIdx':'%s', 'spanOver':'%s', 'quitOver':'%s', 'backKeys':'%s'}", 
				caseName, whenFlag, quitFlag, whenIdx, quitIdx, isSpanOver(), isQuitOver(), backKeys.size());
	}
	
	public boolean accept(int code) {
		if (backKeys.size() > 0 && backKeys.peek() == code) {
			backKeys.poll();
			return false;
		}
		if(isSpanOver()) {
			initial();
		}
		if(isQuitFlag() && isQuitOver()) {
			initial();
		}
		
		if(isWhenFlag() && isWhenUsed()) {
			if(whenIdx<whenKeys.length) {
				if(whenKeys[whenIdx]==code && !isSpanOver()) {
					this.whenIdx ++;
					this.spanBegin = System.currentTimeMillis();
				}else {
					this.whenIdx = 0;
				}
				
				if(whenIdx>=whenKeys.length) {
					this.whenFlag = false;
					this.quitFlag = true;
					if(whenCall!=null) {
						whenCall.accept(whenKeys);
					}
					this.quitBegin = System.currentTimeMillis();
				}
			}
		}else if(isQuitFlag() && isQuitUsed()) {
			if(quitIdx<quitKeys.length  && !isSpanOver() && !isQuitOver()) {
				if(quitKeys[quitIdx]==code) {
					this.quitIdx ++;
					this.spanBegin = System.currentTimeMillis();
				}else {
					this.quitIdx = 0;
				}
				
				if(quitIdx>=quitKeys.length) {
					initial();
					if(quitCall!=null) {
						quitCall.accept(quitKeys);
					}
				}
				
				if(quitIdx==0) {
					initial();
				}
			}
		}
		
		System.out.println(this);
		
		return true;
	}
	
	protected void revert(Long... codes) {
		for (Long code : codes) {
			this.backKeys.offer(code.intValue());
		}
	}

	public void keypress(long ctrl, long keys) {
		revert(ctrl, keys);
		KeyPress.apply(ctrl, keys);
	}

	protected void initial() {
		this.whenFlag = true;
		this.quitFlag = false;
		this.whenIdx = 0;
		this.quitIdx = 0;
		this.backKeys.clear();
		this.spanBegin = System.currentTimeMillis();
		this.quitBegin = System.currentTimeMillis();
	}
	
	public boolean isSpanOver() {
		return isWhenFlag() ? isSpanOver(whenIdx) : isSpanOver(quitIdx);
	}
	
	public boolean isSpanOver(int index) {
		return index!=0 && (System.currentTimeMillis()-spanBegin>spanTime);
	}
	
	public boolean isQuitOver() {
		return (System.currentTimeMillis()-quitBegin>quitTime);
	}
	
	public boolean isWhenUsed() {
		return whenKeys!=null && whenKeys.length>0;
	}
	
	public boolean isQuitUsed() {
		return quitKeys!=null && quitKeys.length>0;
	}
	
	public boolean isWhenFlag() {
		return whenFlag;
	}

	public void setWhenFlag(boolean whenFlag) {
		this.whenFlag = whenFlag;
	}

	public long getSpanBegin() {
		return spanBegin;
	}

	public void setSpanBegin(long spanBegin) {
		this.spanBegin = spanBegin;
	}

	public int getWhenIdx() {
		return whenIdx;
	}

	public void setWhenIdx(int whenIdx) {
		this.whenIdx = whenIdx;
	}

	public int[] getWhenKeys() {
		return whenKeys;
	}

	public void setWhenKeys(int[] whenKeys) {
		this.whenKeys = whenKeys;
	}

	public Consumer<int[]> getWhenCall() {
		return whenCall;
	}

	public void setWhenCall(Consumer<int[]> whenCall) {
		this.whenCall = whenCall;
	}

	public boolean isQuitFlag() {
		return quitFlag;
	}

	public void setQuitFlag(boolean quitFlag) {
		this.quitFlag = quitFlag;
	}


	public long getQuitBegin() {
		return quitBegin;
	}

	public void setQuitBegin(long quitBegin) {
		this.quitBegin = quitBegin;
	}

	public int getQuitIdx() {
		return quitIdx;
	}

	public void setQuitIdx(int quitIdx) {
		this.quitIdx = quitIdx;
	}

	public int[] getQuitKeys() {
		return quitKeys;
	}

	public void setQuitKeys(int[] quitKeys) {
		this.quitKeys = quitKeys;
	}

	public Consumer<int[]> getQuitCall() {
		return quitCall;
	}

	public void setQuitCall(Consumer<int[]> quitCall) {
		this.quitCall = quitCall;
	}

	public long getSpanTime() {
		return spanTime;
	}

	public void setSpanTime(long spanTime) {
		this.spanTime = spanTime;
	}

	public long getQuitTime() {
		return quitTime;
	}

	public void setQuitTime(long quitTime) {
		this.quitTime = quitTime;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

}
