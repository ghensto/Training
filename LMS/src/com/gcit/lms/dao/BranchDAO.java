package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Branch;

public class BranchDAO extends BaseDAO<Branch> {

	public BranchDAO(Connection connection) {
		super(connection);
	}

	public void addBranchName(Branch branch) throws ClassNotFoundException, SQLException {
		save("insert into tbl_library_branch (branchName) values (?)", new Object[] { branch.getBranchName() });
	}
	
	public void addBranchAddress(Branch branch) throws ClassNotFoundException, SQLException {
		save("insert into tbl_library_branch (branchAddress) values (?)", new Object[] { branch.getBranchAddress() });
	}
	
	public void addBranch(Branch branch) throws ClassNotFoundException, SQLException {
		save("insert into tbl_library_branch (branchName, branchAddress) values (?, ?)", 
				new Object[] { branch.getBranchName(), branch.getBranchAddress() });
	}
	
	public void editBranch(Branch branch) throws ClassNotFoundException, SQLException {
		save("update tbl_library_branch set branchName = ?, branchAddress = ? where branchId = ?",
				new Object[] { branch.getBranchName(), branch.getBranchAddress(), branch.getBranchId() });
	}

	public void editBranchName(Branch branch) throws ClassNotFoundException, SQLException {
		save("update tbl_library_branch set branchName = ? where branchId = ?",
				new Object[] { branch.getBranchName(), branch.getBranchId() });
	}
	
	public void editBranchAddress(Branch branch) throws ClassNotFoundException, SQLException {
		save("update tbl_library_branch set branchAddress = ? where branchId = ?",
				new Object[] { branch.getBranchAddress(), branch.getBranchId() });
	}

	public void deleteBranch(Branch branch) throws ClassNotFoundException, SQLException {
		save("delete from tbl_library_branch where branchId = ? ", new Object[] { branch.getBranchId() });
	}
	
	public List<Branch> readAllBranchs() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_library_branch", null);
	}
	
	public List<Branch> readBranchsByName(String branchName) throws ClassNotFoundException, SQLException {
		String searchName = "%"+branchName+"%";
		return read("select * from tbl_library_branch where branchName like ?", new Object[]{searchName});
	}
	
	public List<Branch> readBranchsByAddress(String branchAddress) throws ClassNotFoundException, SQLException {
		String searchName = "%"+branchAddress+"%";
		return read("select * from tbl_library_branch where branchName like ?", new Object[]{searchName});
	}
	
	public Branch readBranchByPK(Integer branchId) throws ClassNotFoundException, SQLException {
		
		List<Branch> branchs = read("select * from tbl_library_branch where branchId = ?", new Object[]{branchId});
		if(!branchs.isEmpty()){
			return branchs.get(0);
		}
		return null;
	}
	
	public Branch readBranchByBid(Integer bookId) throws ClassNotFoundException, SQLException {
			
			List<Branch> branchs = read("select * from tbl_library_branch where branchId IN (select branchId from tbl_book_copies where bookId = ?)", new Object[]{bookId});
			if(!branchs.isEmpty()){
				return branchs.get(0);
			}
			return null;
	}

	public List<Branch> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Branch> branchs = new ArrayList<>();
		BookDAO bdao = new BookDAO(conn);
		while (rs.next()) {
			Branch a = new Branch();
			a.setBranchId(rs.getInt("branchId"));
			a.setBranchName(rs.getString("branchName"));
			a.setBooks(bdao.readFirstLevel("select * from tbl_book where bookId IN (select bookId from tbl_library_branch where branchId = ?)" , new Object[]{a.getBranchId()}));
			branchs.add(a);
		}
		return branchs;
	}
	
	public List<Branch> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Branch> branchs = new ArrayList<>();
		while (rs.next()) {
			Branch a = new Branch();
			a.setBranchId(rs.getInt("branchId"));
			a.setBranchName(rs.getString("branchName"));
			branchs.add(a);
		}
		return branchs;
	}

}
