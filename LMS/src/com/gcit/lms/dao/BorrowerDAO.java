package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Borrower;

public class BorrowerDAO extends BaseDAO<Borrower> {

	public BorrowerDAO(Connection connection) {
		super(connection);
	}

	public void addBorrowerName(Borrower borrower) throws ClassNotFoundException, SQLException {
		save("insert into tbl_borrower (name) values (?)", new Object[] { borrower.getBorrowerName() });
	}
	
	public void addBorrowerAddress(Borrower borrower) throws ClassNotFoundException, SQLException {
		save("insert into tbl_borrower (address) values (?)", new Object[] { borrower.getBorrowerAddress() });
	}
	
	public void addBorrowerPhone(Borrower borrower) throws ClassNotFoundException, SQLException {
		save("insert into tbl_borrower (phone) values (?)", new Object[] { borrower.getBorrowerPhone() });
	}
	
	public void addBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {
		save("insert into tbl_borrower (name, address, phone) values (?, ?, ?)", 
				new Object[] { borrower.getBorrowerName(), borrower.getBorrowerAddress(), borrower.getBorrowerPhone()});
	}
	
	public void editBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {
		save("update tbl_borrower set name = ?, address = ?, phone = ? where cardNo = ?",
				new Object[] { borrower.getBorrowerName(), borrower.getBorrowerAddress(), borrower.getBorrowerPhone(),
						borrower.getBorrowerId() });
	}

	public void editBorrowerName(Borrower borrower) throws ClassNotFoundException, SQLException {
		save("update tbl_borrower set name = ? where cardNo = ?",
				new Object[] { borrower.getBorrowerName(), borrower.getBorrowerId() });
	}
	
	public void editBorrowerAddress(Borrower borrower) throws ClassNotFoundException, SQLException {
		save("update tbl_borrower set address = ? where cardNo = ?",
				new Object[] { borrower.getBorrowerAddress(), borrower.getBorrowerId() });
	}
	
	public void editBorrowerPhone(Borrower borrower) throws ClassNotFoundException, SQLException {
		save("update tbl_borrower set phone = ? where cardNo = ?",
				new Object[] { borrower.getBorrowerPhone(), borrower.getBorrowerId() });
	}

	public void deleteBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {
		save("delete from tbl_borrower where cardNo = ? ", new Object[] { borrower.getBorrowerId() });
	}
	
	public List<Borrower> readAllBorrowers() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_borrower", null);
	}
	
	public List<Borrower> readBorrowersByName(String name) throws ClassNotFoundException, SQLException {
		String searchName = "%"+name+"%";
		return read("select * from tbl_borrower where name like ?", new Object[]{searchName});
	}
	
	public List<Borrower> readBorrowersByAddress(String address) throws ClassNotFoundException, SQLException {
		String searchName = address+"%";
		return read("select * from tbl_borrower where address like ?", new Object[]{searchName});
	}
	
	public List<Borrower> readBorrowersByPhone(String phone) throws ClassNotFoundException, SQLException {
		String searchName = phone+"%";
		return read("select * from tbl_borrower where phone like ?", new Object[]{searchName});
	}
	
	public Borrower readBorrowerByPK(Integer cardNo) throws ClassNotFoundException, SQLException {
		
		List<Borrower> borrowers = read("select * from tbl_borrower where cardNo = ?", new Object[]{cardNo});
		if(!borrowers.isEmpty()){
			return borrowers.get(0);
		}
		return null;
	}
	
	/*public Borrower readBorrowerByBid(Integer bookId) throws ClassNotFoundException, SQLException {
			
			List<Borrower> borrowers = read("select * from tbl_borrower where cardNo = ?", new Object[]{cardNo});
			if(!borrowers.isEmpty()){
				return borrowers.get(0);
			}
			return null;
		}*/

	public List<Borrower> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Borrower> borrowers = new ArrayList<>();
		BookDAO bdao = new BookDAO(conn);
		while (rs.next()) {
			Borrower a = new Borrower();
			a.setBorrowerId(rs.getInt("cardNo"));
			a.setBorrowerName(rs.getString("name"));
			a.setBooks(bdao.readFirstLevel("select * from tbl_book where bookId IN (select bookId from tbl_borrower where cardNo = ?)" , new Object[]{a.getBorrowerId()}));
			borrowers.add(a);
		}
		return borrowers;
	}
	
	public List<Borrower> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Borrower> borrowers = new ArrayList<>();
		while (rs.next()) {
			Borrower a = new Borrower();
			a.setBorrowerId(rs.getInt("cardNo"));
			a.setBorrowerName(rs.getString("name"));
			borrowers.add(a);
		}
		return borrowers;
	}
	

}
