/**
 * 
 */
package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Branch;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.Publisher;

/**
 * @author Abiola
 *
 */
public class AdminService {
	
	public ConnectionUtil connUtil = new ConnectionUtil();
	
	public void saveAuthor(Author author) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			if(author.getAuthorId()!=null && author.getAuthorName()!=null){
				adao.editAuthor(author);
			}else if(author.getAuthorId()!=null){
				adao.deleteAuthor(author);
			}else{
				adao.addAuthor(author);
			}
			conn.commit();
			System.out.println("Author added sucessfully");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			if(conn!=null){
				conn.rollback();
			}
		} finally{
			conn.close();
		}
		
	}
	
	public void saveBorrower(Borrower bor) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BorrowerDAO bdao = new BorrowerDAO(conn);
			if(bor.getBorrowerId()!=null && bor.getBorrowerName() == null && bor.getBorrowerAddress() == null && bor.getBorrowerPhone() == null){
				bdao.deleteBorrower(bor);
			}
			else if(bor.getBorrowerId() == null && (bor.getBorrowerName() != null || bor.getBorrowerAddress() != null || bor.getBorrowerPhone() != null)){
				bdao.addBorrower(bor);
			}
			else{
				bdao.editBorrower(bor);
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

	public void savePublisher(Publisher pub) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			PublisherDAO pdao = new PublisherDAO(conn);
			if(pub.getPublisherId()!=null && pub.getPublisherName()==null && pub.getPublisherAddress()==null && pub.getPublisherPhone()==null){
				pdao.deletePublisher(pub);
			}
			else if(pub.getPublisherId()==null && (pub.getPublisherName() !=null || pub.getPublisherAddress() !=null || pub.getPublisherPhone() !=null)){
				pdao.addPublisher(pub);
			}
			else{
				pdao.editPublisher(pub);
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

	public void saveGenre(Genre g) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			GenreDAO gdao = new GenreDAO(conn);
			if(g.getGenreId()!=null && g.getGenreName()==null){
				gdao.deleteGenre(g);
			}
			else if(g.getGenreId()==null && g.getGenreName()!=null){
				gdao.addGenre(g);
			}
			else{
				gdao.editGenre(g);
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

	public List<Author> readAuthor(Author author) throws SQLException {
		Connection conn = null;
		List<Author> authors = new ArrayList<>();
		try {
			conn = connUtil.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			Author auth = new Author();
			
			if(author.getAuthorId() == null && author.getAuthorName() == null) {
				authors = adao.readAllAuthors();
			}
			
			else if(author.getAuthorId() == null && author.getAuthorName() != null) {
				authors = adao.readAuthorsByName(author.getAuthorName());
			}
			
			else {
				auth = adao.readAuthorByPK(author.getAuthorId());
				authors.add(auth);	
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
		return authors;
	}

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

	public List<Publisher> readPublisher(Publisher publisher) throws SQLException {
		Connection conn = null;
		List<Publisher> publishers = new ArrayList<>();
		try {
			conn = connUtil.getConnection();
			PublisherDAO pdao = new PublisherDAO(conn);
			Publisher pb = new Publisher();
			
			if(publisher.getPublisherId() == null && publisher.getPublisherName() == null
					&& publisher.getPublisherAddress() == null && publisher.getPublisherPhone() == null) {
				publishers = pdao.readAllPublishers();
			}
			
			else if(publisher.getPublisherId() == null && publisher.getPublisherName() != null
					&& publisher.getPublisherAddress() == null && publisher.getPublisherPhone() == null) {
				publishers = pdao.readPublishersByName(publisher.getPublisherName());
			}
			
			else if(publisher.getPublisherId() == null && publisher.getPublisherAddress() != null
					&& publisher.getPublisherName() == null && publisher.getPublisherPhone() == null) {
				publishers = pdao.readPublishersByAddress(publisher.getPublisherAddress());
			}
			else if(publisher.getPublisherId() == null && publisher.getPublisherPhone() != null 
					&& publisher.getPublisherAddress() == null && publisher.getPublisherName() == null) {
				publishers = pdao.readPublishersByPhone(publisher.getPublisherPhone());
			}
			
			else {
				pb = pdao.readPublisherByPK(publisher.getPublisherId());
				publishers.add(pb);
				
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
		return publishers;
	}

	public List<Genre> readGenre(Genre genre) throws SQLException {
		Connection conn = null;
		List<Genre> genres = new ArrayList<>();
		try {
			conn = connUtil.getConnection();
			GenreDAO gdao = new GenreDAO(conn);
			Genre g = new Genre();
			
			if(genre.getGenreId() == null && genre.getGenreName() == null) {
				genres = gdao.readAllGenres();
			}
			
			else if(genre.getGenreId() == null && genre.getGenreName() != null) {
				genres = gdao.readGenresByName(genre.getGenreName());
			}
			
			else {
				g = gdao.readGenreByPK(genre.getGenreId());
				genres.add(g);
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
		return genres;
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

	public void bookAuthors(Author author, Book book) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			bdao.addBookAuthors(author.getAuthorId(), book.getBookId());
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
	
	public void bookGenres(Book book, Genre genre) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			bdao.addBookGenres(genre.getGenreId(), book.getBookId());;
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
	
	public void newBooks(Book book, Branch branch) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			bdao.addNoOfCopies(book, branch);
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
	
	public List<Book> borrowers(Book book, Branch branch) throws SQLException {
		Connection conn = null;
		List<Book> books = new ArrayList<>();
		try {
			conn = connUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			books = bdao.readAllBorrowers();
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

	public void changeDueDate(Book book) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			bdao.UpdateDueDate(book.getDateDue(), book.getBookId(), book.getBranch().getBranchId(), book.getBorrowerId());
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

