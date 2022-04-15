package com.springboot.assessment3.model;

import javax.persistence.*;

@Entity
public class Orders {
 
	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private long id;
	
	private long userId;
	
	private String message;

	private String orderid;
	
	
	public Orders() {
	}

	public Orders(long id, long userId, String message, String orderid) {
		super();
		this.id = id;
		this.userId = userId;
		this.message = message;
		this.orderid = orderid;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
}
