package com.gcit.lms.entity;

import java.util.List;

public class Borrower {
	
	private Integer cardNo;
	private String name;
	private String address;
	private String phone;
	private List<Book> books;
	/**
	 * @return the cardNo
	 */
	public Integer getBorrowerId() {
		return cardNo;
	}
	/**
	 * @param cardNo the cardNo to set
	 */
	public void setBorrowerId(Integer cardNo) {
		this.cardNo = cardNo;
	}
	/**
	 * @return the name
	 */
	public String getBorrowerName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setBorrowerName(String name) {
		this.name = name;
	}
	/**
	 * @return the address
	 */
	public String getBorrowerAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setBorrowerAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the phone
	 */
	public String getBorrowerPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setBorrowerPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the books
	 */
	public List<Book> getBooks() {
		return books;
	}
	/**
	 * @param books the books to set
	 */
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Borrower other = (Borrower) obj;
		if (cardNo == null) {
			if (other.cardNo != null)
				return false;
		} else if (!cardNo.equals(other.cardNo))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		
		return true;
	}
	
	

}
