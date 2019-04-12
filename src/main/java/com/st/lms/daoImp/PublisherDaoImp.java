package com.st.lms.daoImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.st.lms.dao.GenericDao;
import com.st.lms.models.Publisher;

public class PublisherDaoImp implements GenericDao<Publisher> {

	private Connection con;
	
	public PublisherDaoImp(Connection con) {
		this.con = con;
	}
	
	@Override
	public boolean add(Publisher obj) throws SQLException {
		if(has(obj.getPublisherId()))
			return false;
		String query = "INSERT INTO tbl_publisher (publisherName, publisherAddress, publisherPhone) " + 
				       "VALUES (?,?,?)";

		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setString(1, obj.getPublisherName());
		pstmt.setString(2, obj.getPublisherAddress());
		pstmt.setString(3, obj.getPublisherPhone());
		pstmt.executeUpdate();
		return true;
	}

	@Override
	public Publisher get(int objId) throws SQLException {
		Publisher publisher = null;
		String query = "SELECT * FROM tbl_publisher WHERE publisherId=?";
		
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, objId);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			int pubId = rs.getInt("publisherId");
			String pubName = rs.getString("publisherName");
			String pubAddr = rs.getString("publisherAddress");
			String pubPhone = rs.getString("publisherPhone");
			publisher = new Publisher(pubId, pubName, pubAddr, pubPhone);
		}	

		return publisher;
	}

	@Override
	public ArrayList<Publisher> getAll() throws SQLException {
		ArrayList<Publisher> publishers = new ArrayList<>();
		String query = "SELECT * FROM tbl_publisher";
		
		PreparedStatement pstmt = con.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			int pubId = rs.getInt("publisherId");
			String pubName = rs.getString("publisherName");
			String pubAddr = rs.getString("publisherAddress");
			String pubPhone = rs.getString("publisherPhone");
			Publisher pub = new Publisher(pubId, pubName, pubAddr, pubPhone);
			publishers.add(pub);
		}	

		return publishers;
	}

	@Override
	public boolean update(Publisher obj) throws SQLException {
		if(!has(obj.getPublisherId()))
			return false;
		String query = "UPDATE tbl_publisher " + 
			           "SET publisherName=?, publisherAddress=?, publisherPhone=? " +
			           "WHERE publisherId=?";
	
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setString(1, obj.getPublisherName());
		pstmt.setString(2, obj.getPublisherAddress());
		pstmt.setString(3, obj.getPublisherPhone());
		pstmt.setInt(4, obj.getPublisherId());
		pstmt.executeUpdate();
		return true;
	}

	@Override
	public boolean delete(Publisher obj) throws SQLException {
		if(!has(obj.getPublisherId()))
			return false;
		String query = "DELETE FROM tbl_publisher WHERE publisherId=?";

		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, obj.getPublisherId());
		pstmt.executeUpdate();
		return true;
	}
	
	@Override
	public boolean has(int objId) throws SQLException {
		String query = "SELECT * FROM tbl_publisher WHERE publisherId=?";
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, objId);
		ResultSet rs = pstmt.executeQuery();
		return (rs.next());
	}
}