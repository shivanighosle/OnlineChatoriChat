package com.springboot.assessment3.model;

import javax.persistence.*;

@Entity
public class Buy {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long buyerid;
	
	private String totalprice;
	
	
	private String orderid;
	
	private String reciept;

	private long userId;
	
	private String status;
	
	private String orderdatetime;
	
	public Buy() {
	}

	public Buy(long buyerid, String totalprice, String orderid, String reciept, long userId, String status,String orderdatetime) {
		super();
		this.buyerid = buyerid;
		this.totalprice = totalprice;
		this.orderid = orderid;
		this.reciept = reciept;
		this.userId = userId;
		this.status = status;
		this.orderdatetime = orderdatetime;
	}

	public long getBuyerid() {
		return buyerid;
	}

	public void setBuyerid(long buyerid) {
		this.buyerid = buyerid;
	}

	public String getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(String totalprice) {
		this.totalprice = totalprice;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getReciept() {
		return reciept;
	}

	public void setReciept(String reciept) {
		this.reciept = reciept;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrderdatetime() {
		return orderdatetime;
	}

	public void setOrderdatetime(String orderdatetime) {
		this.orderdatetime = orderdatetime;
	}

	@Override
	public String toString() {
		return "Buy [buyerid=" + buyerid + ", totalprice=" + totalprice + ", orderid=" + orderid + ", reciept="
				+ reciept + ", userId=" + userId + ", status=" + status + ", orderdatetime=" + orderdatetime + "]";
	}


}
