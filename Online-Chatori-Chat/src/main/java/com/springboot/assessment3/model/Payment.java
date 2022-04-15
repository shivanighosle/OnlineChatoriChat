package com.springboot.assessment3.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="membership")
public class Payment {

		
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="membershipid")
    private long id;
	
	@Column(name="orderid", unique=true)
	private String orderId;	

	private String amount;	

	private String reciept;	

	private String status;
	
	private String currentdate;
	
	private String expiredate;
	
	private long clientid;

	
	public Payment() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getReciept() {
		return reciept;
	}

	public void setReciept(String reciept) {
		this.reciept = reciept;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCurrentdate() {
		return currentdate;
	}

	public void setCurrentdate(String currentdate) {
		this.currentdate = currentdate;
	}

	public String getExpiredate() {
		return expiredate;
	}

	public void setExpiredate(String expiredate) {
		this.expiredate = expiredate;
	}

	public long getClientid() {
		return clientid;
	}

	public void setClientid(long clientid) {
		this.clientid = clientid;
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", orderId=" + orderId + ", amount=" + amount + ", reciept=" + reciept
				+ ", status=" + status + ", currentdate=" + currentdate + ", expiredate=" + expiredate + ", clientid="
				+ clientid + "]";
	}
	

}
