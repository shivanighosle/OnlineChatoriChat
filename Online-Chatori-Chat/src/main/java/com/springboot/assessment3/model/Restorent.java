package com.springboot.assessment3.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Restorent {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long RestorentId;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false) 
    private String menu;
	 
	
	@Column(nullable = false)
	private float price;
	
	@Column(nullable = false, length =64)
	private String image;

	private long clientId;
	
	@OneToMany(mappedBy = "restorent")
	private List<Dish> dish;

	
	//default constructor
	public Restorent() { }


	public Restorent(long restorentId, String name, String menu, float price, String image, long clientId,
			List<Dish> dish) {
		super();
		RestorentId = restorentId;
		this.name = name;
		this.menu = menu;
		this.price = price;
		this.image = image;
		this.clientId = clientId;
		this.dish = dish;
	}


	public long getRestorentId() {
		return RestorentId;
	}


	public void setRestorentId(long restorentId) {
		RestorentId = restorentId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getMenu() {
		return menu;
	}


	public void setMenu(String menu) {
		this.menu = menu;
	}


	public float getPrice() {
		return price;
	}


	public void setPrice(float price) {
		this.price = price;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public long getClientId() {
		return clientId;
	}


	public void setClientId(long clientId) {
		this.clientId = clientId;
	}


	public List<Dish> getDish() {
		return dish;
	}


	public void setDish(List<Dish> dish) {
		this.dish = dish;
	}
}
