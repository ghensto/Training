/**
 * 
 */
package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Branch;

/**
 * @author Abiola
 *
 */
public class BorrowerService {
	
	public ConnectionUtil connUtil = new ConnectionUtil();

	public List<Book> readBook(Book book) throws SQLException {
		Connection conn = null;
		List<Book> books = new ArrayList<>();
		try {
			conn = connUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			Book bk = new Book();
			
			if(book.getBookId() == null && book.getBookTitle() == null) {
				books = bdao.readAllBooks();
			}
			else if(book.getBookTitle() != null && book.getPubId() != null) {
				books = bdao.readBooksWithPuid(book);
			}
			
			else if(book.getBookId() == null && book.getBookTitle() != null) {
				books = bdao.readBooksByName(book.getBookTitle());			}
			
			else {
				bk = bdao.readBookByPK(book.getBookId());
				books.add(bk);
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

	public List<Borrower> readBorrower(Borrower borrower) throws SQLException {
		Connection conn = null;
		List<Borrower> borrowers = new ArrayList<>();
		try {
			conn = connUtil.getConnection();
			BorrowerDAO bdao = new BorrowerDAO(conn);
			Borrower bch = new Borrower();
			
			if(borrower.getBorrowerId() == null && borrower.getBorrowerName() == null
					&& borrower.getBorrowerAddress() == null && borrower.getBorrowerPhone() == null) {
				borrowers = bdao.readAllBorrowers();
			}
			
			else if(borrower.getBorrowerId() == null && borrower.getBorrowerName() != null
					&& borrower.getBorrowerAddress() == null && borrower.getBorrowerPhone() == null) {
				borrowers = bdao.readBorrowersByName(borrower.getBorrowerName());
			}
			
			else if(borrower.getBorrowerId() == null && borrower.getBorrowerAddress() != null
					&& borrower.getBorrowerName() == null && borrower.getBorrowerPhone() == null) {
				borrowers = bdao.readBorrowersByAddress(borrower.getBorrowerAddress());
			}
			else if(borrower.getBorrowerId() == null && borrower.getBorrowerPhone() != null 
					&& borrower.getBorrowerAddress() == null && borrower.getBorrowerName() == null) {
				borrowers = bdao.readBorrowersByPhone(borrower.getBorrowerPhone());
			}
			
			else {
				bch = bdao.readBorrowerByPK(borrower.getBorrowerId());
				borrowers.add(bch);
				
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
		return borrowers;
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
	
	public void bookLoan(Integer bookId, Integer branchId, Integer cardNo, Date stoday, Date sweek, Date dateIn) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookDAO bDao = new BookDAO(conn);
			if(dateIn == null) {
				bDao.addBookLoans(bookId, branchId, cardNo, stoday, sweek, dateIn);
			}
			else if (stoday == null && sweek == null) {
				bDao.deleteBookLoans(bookId);
			}
			else {
				bDao.updateBookLoans(bookId, branchId, cardNo, stoday, sweek, dateIn);
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
	
	public List<Book> bookReturn(Integer borrowerId, Integer branchId) throws SQLException{
		Connection conn = null;
		List<Book> books = new ArrayList<>();
		try {
			conn = connUtil.getConnection();
			BookDAO bDao = new BookDAO(conn);
			books = bDao.booksBorrowedbyPK(borrowerId, branchId);
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
	
}

