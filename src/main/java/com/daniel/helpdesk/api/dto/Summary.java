package com.daniel.helpdesk.api.dto;

import java.io.Serializable;

public class Summary implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer amountNew;
	private Integer amountResolved;
	private Integer amountApproved;
	private Integer amountDisapproved;
	private Integer amountAssigned;
	private Integer amountClosed;
	
	
	public Integer getamountNew() {
		return amountNew;
	}
	public void setamountNew(Integer amountNew) {
		this.amountNew = amountNew;
	}
	public Integer getamountResolved() {
		return amountResolved;
	}
	public void setamountResolved(Integer amountResolved) {
		this.amountResolved = amountResolved;
	}
	public Integer getamountApproved() {
		return amountApproved;
	}
	public void setamountApproved(Integer amountApproved) {
		this.amountApproved = amountApproved;
	}
	public Integer getamountDisapproved() {
		return amountDisapproved;
	}
	public void setamountDisapproved(Integer amountDisapproved) {
		this.amountDisapproved = amountDisapproved;
	}
	public Integer getamountAssigned() {
		return amountAssigned;
	}
	public void setamountAssigned(Integer amountAssigned) {
		this.amountAssigned = amountAssigned;
	}
	public Integer getamountClosed() {
		return amountClosed;
	}
	public void setamountClosed(Integer amountClosed) {
		this.amountClosed = amountClosed;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
