package com.jna.apply;

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

	public KeyCase(String caseName) {
		this.caseName = caseName;
		this.spanTime = 300L;
		this.quitTime = 3000L;
		initial();
	}
	
	public void setting(long spanTime, long quitTime) {
		this.spanTime = spanTime;
		this.quitTime = quitTime;
	}
	
	public String toString() {
		return String.format("{'caseName':'%s', 'whenFlag':'%s', 'quitFlag':'%s',"
				+ " 'whenIdx':'%s', 'quitIdx':'%s', 'spanOver':'%s', 'quitOver':'%s'}", 
				caseName, whenFlag, quitFlag, whenIdx, quitIdx, isSpanOver(), isQuitOver());
	}
	
	public boolean accept(int code) {
		if(isQuitFlag() && isQuitOver()) {
			initial();
		}
		
		if(isWhenFlag() && isWhenUsed()) {
			if(whenIdx<whenKeys.length) {
				if(whenKeys[whenIdx]==code && !isSpanOver()) {
					whenIdx ++;
					spanBegin = System.currentTimeMillis();
				}else {
					whenIdx = 0;
				}
				
				if(whenIdx>=whenKeys.length) {
					whenFlag = false;
					quitFlag = true;
					if(whenCall!=null) {
						whenCall.accept(whenKeys);
					}
					quitBegin = System.currentTimeMillis();
				}
			}
		}else if(isQuitFlag() && isQuitUsed()) {
			if(quitIdx<quitKeys.length  && !isSpanOver() && !isQuitOver()) {
				if(quitKeys[quitIdx]==code) {
					quitIdx ++;
					spanBegin = System.currentTimeMillis();
				}else {
					quitIdx = 0;
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

	protected void initial() {
		whenFlag = true;
		quitFlag = false;
		whenIdx = 0;
		quitIdx = 0;
		spanBegin = System.currentTimeMillis();
		quitBegin = System.currentTimeMillis();
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
