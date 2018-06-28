package com.gcit.lms.entity;

import java.util.List;

public class Genre {
	
	private Integer genreId;
	private String genreName;
	private List<Genre> genres;
	/**
	 * @return the genreId
	 */
	public Integer getGenreId() {
		return genreId;
	}
	/**
	 * @param genreId the genreId to set
	 */
	public void setGenreId(Integer genreId) {
		this.genreId = genreId;
	}
	/**
	 * @return the genreName
	 */
	public String getGenreName() {
		return genreName;
	}
	/**
	 * @param genreName the genreName to set
	 */
	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}
	/**
	 * @return the books
	 */
	
	public List<Genre> getGenres() {
		return genres;
	}
	/**
	 * @param books the books to set
	 */
	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}

}
