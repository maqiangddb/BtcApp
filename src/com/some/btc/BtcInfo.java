package com.some.btc;

public class BtcInfo {
	private String name;
	private String lastPrice;
	private String buyPrice;
	private String sellPrice;
	private String volume;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastPrice() {
		return lastPrice;
	}
	public void setLastPrice(String lastPrice) {
		this.lastPrice = lastPrice;
	}
	public String getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(String buyPrice) {
		this.buyPrice = buyPrice;
	}
	public String getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(String sellPrice) {
		this.sellPrice = sellPrice;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	@Override
	public String toString() {
		return "BtcInfo [name=" + name + ", lastPrice=" + lastPrice
				+ ", buyPrice=" + buyPrice + ", sellPrice=" + sellPrice
				+ ", volume=" + volume + "]";
	}
}
