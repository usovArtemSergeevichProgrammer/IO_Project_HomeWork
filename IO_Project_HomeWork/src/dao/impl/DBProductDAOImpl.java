package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.ProductDAO;
import model.Product;
import util.JDBCUtils;

public class DBProductDAOImpl implements ProductDAO {

	public String actualTable = "products";

	@Override
	public Product getProduct(Product product) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Product result = null;
		String selectSQL = "SELECT * FROM " + JDBCUtils.DB_NAME + "." + actualTable + " WHERE productCode=?";
		try {
			conn = JDBCUtils.createConnection();
			pstmt = conn.prepareStatement(selectSQL);
			pstmt.setString(1, product.getProductCode());
			ResultSet rs = pstmt.executeQuery();
			result = isGetProduct(rs);
			if (result == null) {
				System.out.println("No such product found  productCode = " + product.getProductCode());
			}
			JDBCUtils.release(conn, pstmt);
		} catch (Exception e) {
		}
		return result;
	}

	@Override
	public boolean removeProduct(Product product) {
		product = getProduct(product);
		String deleteSQL = "DELETE FROM " + JDBCUtils.DB_NAME + "." + actualTable + " WHERE productCode=?";
		boolean isRemove = false;
		if (product != null) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn = JDBCUtils.createConnection();
				pstmt = conn.prepareStatement(deleteSQL);
				pstmt.setString(1, product.getProductCode());
				isRemove = pstmt.executeUpdate() == 1;
				JDBCUtils.release(conn, pstmt);
				System.out.println("The product was delete!");
			} catch (Exception e) {
			}
		}

		return isRemove;
	}

	@Override
	public boolean insertProduct(Product product) {

		return false;
	}

	@Override
	public boolean updateProduct(Product product) {

		return false;
	}

	private Product isGetProduct(ResultSet rs) throws SQLException {
		Product result = null;
		if (rs.next()) {
			result = new Product();
			result.setProductCode(rs.getString("productCode"));
			result.setProductName(rs.getString("productName"));
			result.setProductLine(rs.getString("productLine"));
			result.setProductScale(rs.getString("productScale"));
			result.setProductVendor(rs.getString("productVendor"));
			result.setProductDescription(rs.getString("productDescription"));
			result.setQuantityInStock(rs.getInt("quantityInStock"));
			result.setBuyPrice(rs.getDouble("buyPrice"));
			result.setMsrt(rs.getDouble("MSRP"));
			System.out.println("The product was found!");
		} else {
			System.out.println("The product wasn't found!");
		}
		return result;

	}

	public static void main(String[] args) {
		// methotGetProduct(new Product("S10_1678"));
		methodRemoveProduct(new Product("S10_1678"));
	}

	static void methotGetProduct(Product product) {
		DBProductDAOImpl dao = new DBProductDAOImpl();
		System.out.println(dao.getProduct(product));
	}

	static void methodRemoveProduct(Product product) {
		DBProductDAOImpl dao = new DBProductDAOImpl();
		System.out.println(dao.removeProduct(product));
	}
}
