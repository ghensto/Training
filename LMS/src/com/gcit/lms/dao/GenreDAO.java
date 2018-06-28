/**
 * 
 */
package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Genre;

/**
 * @author Abiola
 *
 */
public class GenreDAO extends BaseDAO<Genre>{

	public GenreDAO(Connection connection) {
		super(connection);
	}

	
	public void addGenre(Genre genre) throws ClassNotFoundException, SQLException {
		save("insert into tbl_genre (genre_name) values (?)", 
				new Object[] { genre.getGenreName() });
	}
	
	public void editGenre(Genre genre) throws ClassNotFoundException, SQLException {
		save("update tbl_genre set genre_name = ? where genre_id = ?",
				new Object[] { genre.getGenreName(), genre.getGenreId() });
	}

	public void deleteGenre(Genre genre) throws ClassNotFoundException, SQLException {
		save("delete from tbl_genre where genre_id = ? ", new Object[] { genre.getGenreId() });
	}
	
	public List<Genre> readAllGenres() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_genre", null);
	}
	
	public List<Genre> readGenresByName(String genre_name) throws ClassNotFoundException, SQLException {
		String searchName = "%"+genre_name+"%";
		return read("select * from tbl_genre where genre_name like ?", new Object[]{searchName});
	}
	
	public Genre readGenreByPK(Integer genreId) throws ClassNotFoundException, SQLException {
		
		List<Genre> genres = read("select * from tbl_genre where genre_id= ?", new Object[]{genreId});
		if(!genres.isEmpty()){
			return genres.get(0);
		}
		return null;
	}
	
	public Genre readGenreByBid(Integer bookId) throws ClassNotFoundException, SQLException {
			
			List<Genre> genres = read("select * from tbl_genre where genre_id IN (select genre_id from tbl_book_genres where bookId = ?)", new Object[]{bookId});
			if(!genres.isEmpty()){
				return genres.get(0);
			}
			return null;
	}

	public List<Genre> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Genre> genres = new ArrayList<>();
		GenreDAO bdao = new GenreDAO(conn);
		while (rs.next()) {
			Genre a = new Genre();
			a.setGenreId(rs.getInt("genre_id"));
			a.setGenreName(rs.getString("genre_name"));
			a.setGenres(bdao.readFirstLevel("select * from tbl_genre where genre_id IN (select genre_id from tbl_book_genres where genre_id = ?)" , new Object[]{a.getGenreId()}));
			genres.add(a);
		}
		return genres;
	}
	
	public List<Genre> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Genre> genres = new ArrayList<>();
		while (rs.next()) {
			Genre a = new Genre();
			a.setGenreId(rs.getInt("genre_id"));
			a.setGenreName(rs.getString("genre_name"));
			genres.add(a);
		}
		return genres;
	}
}
