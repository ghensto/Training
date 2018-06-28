package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Publisher;

public class PublisherDAO extends BaseDAO<Publisher> {

	public PublisherDAO(Connection connection) {
		super(connection);
	}

	public void addPublisherName(Publisher publisher) throws ClassNotFoundException, SQLException {
		save("insert into tbl_publisher (publisherName) values (?)", new Object[] { publisher.getPublisherName() });
	}

	public void addPublisherAddress(Publisher publisher) throws ClassNotFoundException, SQLException {
		save("insert into tbl_publisher (publisherAddress) values (?)",
				new Object[] { publisher.getPublisherAddress() });
	}

	public void addPublisherPhone(Publisher publisher) throws ClassNotFoundException, SQLException {
		save("insert into tbl_publisher (publisherPhone) values (?)", new Object[] { publisher.getPublisherPhone() });
	}

	public void addPublisher(Publisher publisher) throws ClassNotFoundException, SQLException {
		save("insert into tbl_publisher (publisherName, publisherAddress, publisherPhone) values (?, ?, ?)",
				new Object[] { publisher.getPublisherName(), publisher.getPublisherAddress(),
						publisher.getPublisherPhone() });
	}

	public void editPublisher(Publisher publisher) throws ClassNotFoundException, SQLException {
		save("update tbl_publisher set publisherName = ?, publisherAddress = ?, publisherPhone = ? where publisherId = ?",
				new Object[] { publisher.getPublisherName(), publisher.getPublisherAddress(),
						publisher.getPublisherPhone(), publisher.getPublisherId() });
	}

	public void editPublisherName(Publisher publisher) throws ClassNotFoundException, SQLException {
		save("update tbl_publisher set publisherName = ? where publisherId = ?",
				new Object[] { publisher.getPublisherName(), publisher.getPublisherId() });
	}

	public void editPublisherAddress(Publisher publisher) throws ClassNotFoundException, SQLException {
		save("update tbl_publisher set publisherAddress = ? where publisherId = ?",
				new Object[] { publisher.getPublisherAddress(), publisher.getPublisherId() });
	}

	public void editPublisherPhone(Publisher publisher) throws ClassNotFoundException, SQLException {
		save("update tbl_publisher set publisherPhone = ? where publisherId = ?",
				new Object[] { publisher.getPublisherPhone(), publisher.getPublisherId() });
	}

	public void deletePublisher(Publisher publisher) throws ClassNotFoundException, SQLException {
		save("delete from tbl_publisher where publisherId = ? ", new Object[] { publisher.getPublisherId() });
	}

	public List<Publisher> readAllPublishers() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_publisher", null);
	}

	public List<Publisher> readPublishersByName(String publisherName) throws ClassNotFoundException, SQLException {
		String searchName = "%" + publisherName + "%";
		return read("select * from tbl_publisher where publisherName like ?", new Object[] { searchName });
	}

	public List<Publisher> readPublishersByAddress(String publisherAddress)
			throws ClassNotFoundException, SQLException {
		String searchName = "%" + publisherAddress + "%";
		return read("select * from tbl_publisher where publisherAddress like ?", new Object[] { searchName });
	}

	public List<Publisher> readPublishersByPhone(String publisherPhone) throws ClassNotFoundException, SQLException {
		String searchName = "%" + publisherPhone + "%";
		return read("select * from tbl_publisher where publisherPhone like ?", new Object[] { searchName });
	}

	public Publisher readPublisherByPK(Integer publisherId) throws ClassNotFoundException, SQLException {

		List<Publisher> publishers = read("select * from tbl_publisher where publisherId = ?",
				new Object[] { publisherId });
		if (!publishers.isEmpty()) {
			return publishers.get(0);
		}
		return null;
	}

	public Publisher readPublisherWithBid(Integer bookId) throws ClassNotFoundException, SQLException {
		List<Publisher> publishers = read(
				"select * from tbl_publisher where publisherId IN (select pubId from tbl_book where bookId = ?)",
				new Object[] { bookId });
		if (!publishers.isEmpty()) {
			return publishers.get(0);
		}
		return null;
	}

	public List<Publisher> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Publisher> publishers = new ArrayList<>();
		BookDAO bdao = new BookDAO(conn);
		while (rs.next()) {
			Publisher a = new Publisher();
			a.setPublisherId(rs.getInt("publisherId"));
			a.setPublisherName(rs.getString("publisherName"));
			a.setBooks(bdao.readFirstLevel(
					"select * from tbl_book where bookId IN (select bookId from tbl_publisher where publisherId = ?)",
					new Object[] { a.getPublisherId() }));
			publishers.add(a);
		}
		return publishers;
	}

	public List<Publisher> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Publisher> publishers = new ArrayList<>();
		while (rs.next()) {
			Publisher a = new Publisher();
			a.setPublisherId(rs.getInt("publisherId"));
			a.setPublisherName(rs.getString("publisherName"));
			publishers.add(a);
		}
		return publishers;
	}

}
