package com.springboot.assessment3.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Dish {

	@Id 
	@GeneratedValue (strategy = GenerationType.AUTO)
	private long dishid;
	
	@Column (name="dishname") 
	private String dishName;
	
	@Column (name = "dishprice")
	private float dishPrice;
	
	@Column (name="dishimage")
	private String dishImage;

	@ManyToOne
	@JoinColumn(name = "RestorentId")
	private Restorent restorent;


	public Dish() {
	}

	public Dish(long dishid, String dishName, float dishPrice, String dishImage, Restorent restorent) {
		this.dishid = dishid;
		this.dishName = dishName;
		this.dishPrice = dishPrice;
		this.dishImage = dishImage;
		this.restorent = restorent;
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

	public float getDishPrice() {
		return dishPrice;
	}

	public void setDishPrice(float dishPrice) {
		this.dishPrice = dishPrice;
	}

	public String getDishImage() {
		return dishImage;
	}

	public void setDishImage(String dishImage) {
		this.dishImage = dishImage;
	}

	public Restorent getRestorent() {
		return restorent;
	}

	public void setRestorent(Restorent restorent) {
		this.restorent = restorent;
	}

}
