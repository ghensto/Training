package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Author;

public class AuthorDAO extends BaseDAO<Author> {

	public AuthorDAO(Connection connection) {
		super(connection);
	}

	public void addAuthor(Author author) throws ClassNotFoundException, SQLException {
		save("insert into tbl_author (authorName) values (?)", new Object[] { author.getAuthorName() });
	}

	public void editAuthor(Author author) throws ClassNotFoundException, SQLException {
		save("update tbl_author set authorName = ? where authorId = ?",
				new Object[] { author.getAuthorName(), author.getAuthorId() });
	}

	public void deleteAuthor(Author author) throws ClassNotFoundException, SQLException {
		save("delete from tbl_author where authorId = ?", new Object[] { author.getAuthorId() });
	}
	
	public List<Author> readAllAuthors() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_author", null);
	}
	
	public List<Author> readAuthorsByName(String authorName) throws ClassNotFoundException, SQLException {
		//String searchName = "%"+authorName+"%";
		return read("select * from tbl_author where authorName = ?", new Object[]{authorName});
	}
	
	public Author readAuthorByPK(Integer authorId) throws ClassNotFoundException, SQLException {
		
		List<Author> authors = read("select * from tbl_author where authorId = ?", new Object[]{authorId});
		if(!authors.isEmpty()){
			return authors.get(0);
		}
		return null;
	}

	public List<Author> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Author> authors = new ArrayList<>();
		BookDAO bdao = new BookDAO(conn);
		while (rs.next()) {
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			a.setAuthorName(rs.getString("authorName"));
			a.setBooks(bdao.readFirstLevel("select * from tbl_book where bookId IN (select bookId from tbl_book_authors where authorId = ?)" , new Object[]{a.getAuthorId()}));
			authors.add(a);
		}
		return authors;
	}
	
	public List<Author> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Author> authors = new ArrayList<>();
		while (rs.next()) {
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			a.setAuthorName(rs.getString("authorName"));
			authors.add(a);
		}
		return authors;
	}

}
