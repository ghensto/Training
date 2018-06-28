/**
 * 
 */
package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Branch;

/**
 * @author Abiola
 *
 */
public class LibrarianService {
	
	public ConnectionUtil connUtil = new ConnectionUtil();
	
	public void saveBranch(Branch branch) throws SQLException{
		Connection conn = null;
		try {			
			conn = connUtil.getConnection();
			BranchDAO bdao = new BranchDAO(conn);
			if(branch.getBranchId()!=null && branch.getBranchName()==null && branch.getBranchAddress()==null){
				bdao.deleteBranch(branch);
			}
			else if(branch.getBranchId() == null && (branch.getBranchName() !=null || branch.getBranchAddress() !=null)){
				bdao.addBranch(branch);
			}
			else{
				bdao.editBranch(branch);
			}
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			if(conn!=null){
				conn.rollback();
			}
		} finally{
			conn.close();
		}
		
	}

	public Book saveBook(Book book) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			if(book.getBookTitle() != null && book.getPubId() != null ) {
				bdao.addBookWithPuid(book, book.getPubId());
			}
			else if(book.getBookId()!=null && book.getBookTitle()==null){
				bdao.deleteBook(book);
			}
			else if(book.getBookId()==null && book.getBookTitle()!=null){
				bdao.addBook(book);
				
			}
			else{
				bdao.editBook(book);
			}
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			if(conn!=null){
				conn.rollback();
			}
		} finally{
			conn.close();
		}

		return book;
		
	}

	public List<Branch> readBranch(Branch branch) throws SQLException {
		Connection conn = null;
		List<Branch> branchs = new ArrayList<>();
		try {
			conn = connUtil.getConnection();
			BranchDAO bdao = new BranchDAO(conn);
			Branch bch = new Branch();
			
			if(branch.getBranchId() == null && branch.getBranchName() == null) {
				branchs = bdao.readAllBranchs();
			}
			
			else if(branch.getBranchId() == null && branch.getBranchName() != null) {
				branchs = bdao.readBranchsByName(branch.getBranchName());
			}
			
			else {
				bch = bdao.readBranchByPK(branch.getBranchId());
				branchs.add(bch);
			}
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			if(conn!=null){
				conn.rollback();
			}
		} finally{
			conn.close();
		}
		return branchs;
	}

	public List<Book> readNoOfCopies(Book book, Branch branch) throws SQLException {
		Connection conn = null;
		List<Book> books = new ArrayList<>();
		try {
			conn = connUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			
			if(book.getBookId() == null && branch.getBranchId() == null) {
				books = bdao.readAllNoOfCopy();
			}
			
			else if(book.getBookId() != null && branch.getBranchId() != null) {
				books = bdao.readCopyofABookInABranch(book.getBookTitle(), branch.getBranchName());			
			}
			else if(book.getBookId() == null && branch.getBranchId() != null) {
				books = bdao.readCopiesInABranch(branch.getBranchName());			
			}
			else {
				books = bdao.readCopiesInAllBranches(book.getBookTitle());
			}
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			if(conn!=null){
				conn.rollback();
			}
		} finally{
			conn.close();
		}
		return books;
	}

	public List<Book> readNoOfCopiesb(Book book, Branch branch) throws SQLException {
		Connection conn = null;
		List<Book> books = new ArrayList<>();
		try {
			conn = connUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			
			if(book.getBookId() != null && branch.getBranchId() != null) {
				books = bdao.readCopyofABookInABranch(book.getBookTitle(), branch.getBranchName());			
			}
			if(book.getBookId() == null && branch.getBranchId() != null) {
				books = bdao.readCopiesInABranchb(branch.getBranchName());			
			}
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			if(conn!=null){
				conn.rollback();
			}
		} finally{
			conn.close();
		}
		return books;
	}
	
	public void editCopies(Integer copy, Integer bId, Integer braId) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookDAO bDao = new BookDAO(conn);
			bDao.editNoOfCopies(copy, bId, braId);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			if(conn!=null){
				conn.rollback();
			}
		} finally{
			conn.close();
		}
		 
	}
	
}

