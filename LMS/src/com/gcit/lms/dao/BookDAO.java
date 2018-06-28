package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Branch;

public class BookDAO extends BaseDAO<Book> {

	public BookDAO(Connection connection) {
		super(connection);
		// TODO Auto-generated constructor stub
	}

	//public void addBook(Book book) throws ClassNotFoundException, SQLException {
		//save("insert into tbl_book (title) values (?)", new Object[] { book.getBookTitle() });
	//}
	
	public Integer addBook(Book book) throws ClassNotFoundException, SQLException {
		return saveWithID("insert into tbl_book (title) values (?)", new Object[] { book.getBookTitle()});
	}
	
	public void addBookAuthors(Integer authorId, Integer bookId) throws ClassNotFoundException, SQLException {
		save("insert into tbl_book_authors values (?, ?)", new Object[] { bookId, authorId});
	}
	
	public void addBookLoans(Integer bookId, Integer branchId, Integer cardNo, Date stoday, Date sweek, Date dateIn) throws ClassNotFoundException, SQLException {
		save("insert into tbl_book_loans values (?, ?, ?, ?, ?, ?)", new Object[] { bookId, branchId, cardNo, stoday, sweek, dateIn});
	}
	
	public void updateBookLoans(Integer bookId, Integer branchId, Integer cardNo, Date stoday, Date sweek, Date dateIn) throws ClassNotFoundException, SQLException {
		save("update tbl_book_loans set dateIn = ? where bookId = ? and  branchId = ? and  cardNo = ? and dateOut = ? and dueDate = ?", 
				new Object[] { dateIn, bookId, branchId, cardNo, stoday, sweek});
	}
	
	public void deleteBookLoans(Integer bookId) throws ClassNotFoundException, SQLException {
		save("delete from tbl_book_loans where bookId = ?",new Object[] {bookId});
	}
	
	public void addBookGenres(Integer genreId, Integer bookId) throws ClassNotFoundException, SQLException {
		//TODO - ?
		save("insert into tbl_book_genres values (?, ?)", new Object[] { genreId, bookId});
	}
	
	
	public void addNoOfCopies(Book book, Branch branch) throws ClassNotFoundException, SQLException {
		//TODO - ?
		save("insert into tbl_book_copies values (?, ?, ?)", new Object[] { book.getBookId(), 
				branch.getBranchId(), book.getNoOfCopies()});
	}
	
	public void addBookPublisher(Book book) throws ClassNotFoundException, SQLException {
		save("insert into tbl_publisher values (?, ?, ?)", new Object[] { book.getPublisher().getPublisherName(), 
				book.getPublisher().getPublisherAddress(), book.getPublisher().getPublisherPhone()});
	}
	
	public Integer addBookPublisherWithID(Book book) throws ClassNotFoundException, SQLException {
		return saveWithID("insert ignore into tbl_publisher values (?, ?, ?) ", 
				new Object[] { book.getPublisher().getPublisherName(), 
				book.getPublisher().getPublisherAddress(), book.getPublisher().getPublisherPhone()});
	}
	
	public Integer addBranch(Book book) throws ClassNotFoundException, SQLException {
		return saveWithID("insert ignore into tbl_library_branch values (?, ?)", new Object[] { book.getBranch().getBranchName(), 
				book.getBranch().getBranchAddress()});
	}
	
	public Integer addBookWithID(Book book, Integer pubId) throws ClassNotFoundException, SQLException {
		return saveWithID("insert into tbl_book (title, pubId) values (?, ?)", new Object[] { book.getBookTitle(), pubId });
	}
	
	public void addBookWithPuid(Book book, Integer pubId) throws ClassNotFoundException, SQLException {
		save("insert into tbl_book (title, pubId) values (?, ?)", new Object[] { book.getBookTitle(), pubId });
	}
	

	public void editBook(Book book) throws ClassNotFoundException, SQLException {
		save("update tbl_book set title = ? where bookId = ?",
				new Object[] { book.getBookTitle(), book.getBookId() });
	}
	
	public void editBook(Book book, Integer pubId) throws ClassNotFoundException, SQLException {
		save("update tbl_book set title = ?, pubId = ? where bookId = ?",
				new Object[] { book.getBookTitle(), pubId, book.getBookId() });
	}
	
	public void editNoOfCopies(Integer noOfCopies, Integer bookId, Integer branchId) throws ClassNotFoundException, SQLException {
		save("update tbl_book_copies set noOfCopies = ? where bookId = ? and branchId = ?",
				new Object[] { noOfCopies, bookId, branchId});
	}

	public void deleteBook(Book book) throws ClassNotFoundException, SQLException {
		save("delete from tbl_book where bookId = ?", new Object[] { book.getBookId() });
	}
	
	public List<Book> readAllBooks() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book", null);
	}
	
	public List<Book> readAllNoOfCopy() throws ClassNotFoundException, SQLException {
		return read("select a.bookId, title, branchName, noOfCopies from tbl_book a, tbl_library_branch b, tbl_book_copies c where c.bookId = a.bookId and b.branchId = c.branchId", null);
	}
	
	public List<Book> readCopiesInAllBranches(String bookName) throws ClassNotFoundException, SQLException {
		return read("select a.bookId, title, branchName, noOfCopies from tbl_book a, tbl_library_branch b, tbl_book_copies c where a.title = ? and c.bookId = a.bookId and b.branchId = c.branchId", new Object[]{bookName});
	}
	
	public List<Book> readCopiesInABranch(String branchName) throws ClassNotFoundException, SQLException {
		return read("select a.bookId, title, branchName, noOfCopies from tbl_book a, tbl_library_branch b, tbl_book_copies c where b.branchName = ? and c.bookId = a.bookId and b.branchId = c.branchId", new Object[]{branchName});
	}
	
	public List<Book> readCopiesInABranchb(String branchName) throws ClassNotFoundException, SQLException {
		return read("select a.bookId, title, branchName, noOfCopies from tbl_book a, tbl_library_branch b, tbl_book_copies c where b.branchName = ? and c.bookId = a.bookId and b.branchId = c.branchId and c.noOfCopies > 0", new Object[]{branchName});
	}
	
	public List<Book> readCopyofABookInABranch(String bookName, String branchName) throws ClassNotFoundException, SQLException {
		return read("select a.bookId, title, branchName, noOfCopies from tbl_book a, tbl_library_branch b, tbl_book_copies c where a.title = ? and b.branchName = ? and c.bookId = a.bookId and b.branchId = c.branchId", new Object[]{bookName, branchName});
	}
	
	public List<Book> booksBorrowedbyPK(Integer borrowerId, Integer branchId) throws ClassNotFoundException, SQLException {
		return read("select c.bookId, title, name, dateOut, dueDate from tbl_book c, tbl_book_loans a, tbl_borrower b where a.cardNo = b.cardNo and a.bookId = c.bookId and a.dueDate is not null and b.cardNo = ? and a.branchId = ?", new Object[]{borrowerId, branchId});
	}
	
	public List<Book> readBooksByName(String bookName) throws ClassNotFoundException, SQLException {
		String searchName = "%"+bookName+"%";
		return read("select * from tbl_book where title like ?", new Object[]{searchName});
	}
	
	public List<Book> readBooksWithPuid(Book book) throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book where title = ? and pubId = ?", new Object[]{book.getBookTitle(), book.getPubId()});
	}
	
	public List<Book> readAllBorrowers() throws ClassNotFoundException, SQLException {
		return read("select book.bookId, bor.cardNo, title, name, dueDate, dateOut, dateIn from tbl_book book, tbl_borrower bor, tbl_book_loans l where book.bookId = l.bookId and bor.cardNo = l.cardNo",null);
	}
	
	public void UpdateDueDate(Date dueDate, Integer bookId, Integer branchId, Integer cardNo) throws ClassNotFoundException, SQLException {
		save("update tbl_book_loans set dueDate = ? WHERE bookId = ? and branchId = ? and cardNo = ?",new Object[]{dueDate, bookId, branchId, cardNo});
	}
	
	public Book readBookByPK(Integer bookId) throws ClassNotFoundException, SQLException {
		
		List<Book> books = read("select * from tbl_book where bookId = ?", new Object[]{bookId});
		if(!books.isEmpty()){
			return books.get(0);
		}
		return null;
	}

	public List<Book> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Book> books = new ArrayList<>();
		AuthorDAO adao = new AuthorDAO(conn);
		PublisherDAO pdao = new PublisherDAO(conn);
		GenreDAO gdao = new GenreDAO(conn);
		BranchDAO branchDAO = new BranchDAO(conn);
		String str;
		Date date;
		while (rs.next()) {
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setBookTitle(rs.getString("title"));
			b.setAuthors(adao.readFirstLevel("select * from tbl_author where authorId IN (select authorId from tbl_book_authors where bookId = ?)", new Object[]{b.getBookId()}));
			//TODO - populate Genres & Publisher
			b.setPublisher(pdao.readPublisherWithBid(b.getBookId()));
			b.setGenres(gdao.readGenreByBid(b.getBookId()));
			b.setBranch(branchDAO.readBranchByBid(b.getBookId()));
			
			try {
			    str = rs.getString("name");
			} catch (java.sql.SQLException e) {
			    str = null;
			}
			if (str != null) {
			    b.setBorrowerName(rs.getString("name"));
			}
			try {
			    str = rs.getString("cardNo");
			} catch (java.sql.SQLException e) {
			    str = null;
			}
			if (str != null) {
			    b.setBorrowerId(rs.getInt("cardNo"));
			}
			
			try {
			    str = rs.getString("pubId");
			} catch (java.sql.SQLException e) {
			    str = null;
			}
			if (str != null) {
			    b.setPubId(rs.getInt("pubId"));
			}
			
			try {
			    str = rs.getString("noOfCopies");
			} catch (java.sql.SQLException e) {
			    str = null;
			}
			if (str != null) {
			    b.setNoOfCopies(rs.getInt("noOfCopies"));
			}
			
			try {
			    str = rs.getString("dueDate");
			} catch (java.sql.SQLException e) {
			    str = null;
			}
			if (str != null) {
				date = rs.getDate("dueDate");
				b.setDateDue(date);
			}
			try {
			    str = rs.getString("dateOut");
			} catch (java.sql.SQLException e) {
			    str = null;
			}
			if(str != null) {
				date = rs.getDate("dateOut");
				b.setDateOut(date);
			}
			try {
			    str = rs.getString("dateIn");
			} catch (java.sql.SQLException e) {
			    str = null;
			}
			if(str != null) {
				date = rs.getDate("dateIn");
				b.setDateIn(date);
			}
			books.add(b);
		}
		return books;
	}
	
	public List<Book> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Book> books = new ArrayList<>();
		while (rs.next()) {
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setBookTitle(rs.getString("title"));
			books.add(b);
		}
		return books;
	}

}
