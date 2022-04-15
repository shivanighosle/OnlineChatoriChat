package com.springboot.assessment3.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Card {
	
	@Id 
	@Column(nullable = false)
	private long dishid;
	
	@Column(nullable = false)
	private String dishName;
	
	@Column(nullable = false)
	private double dishPrice;
	
	@Column(nullable = false)
	private String dishimage;
	
	@Column(nullable = false)
	private long restorentId;
	
    private long userId;
	

	public Card() {
	}

	
	
	public Card(long dishid, String dishName, double dishPrice, String dishimage, long restorentId, long userId) {
		super();
		this.dishid = dishid;
		this.dishName = dishName;
		this.dishPrice = dishPrice;
		this.dishimage = dishimage;
		this.restorentId = restorentId;
		this.userId = userId;
	}



	public long getDishid() {
		return dishid;
	}

	public void setDishid(long dishid) {
		this.dishid = dishid;
	}

	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	public double getDishPrice() {
		return dishPrice;
	}

	public void setDishPrice(double dishPrice) {
		this.dishPrice = dishPrice;
	}

	public String getDishimage() {
		return dishimage;
	}

	public void setDishimage(String dishimage) {
		this.dishimage = dishimage;
	}

	public long getRestorentId() {
		return restorentId;
	}

	public void setRestorentId(long restorentId) {
		this.restorentId = restorentId;
	}

	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}



	@Override
	public String toString() {
		return "Card [dishid=" + dishid + ", dishName=" + dishName + ", dishPrice=" + dishPrice + ", dishimage="
				+ dishimage + ", restorentId=" + restorentId + ", userId=" + userId + "]";
	}
   
	
}
