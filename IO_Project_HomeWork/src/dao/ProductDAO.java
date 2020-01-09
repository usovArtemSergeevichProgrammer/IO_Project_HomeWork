package dao;

import model.Product;

public interface ProductDAO {

	Product getProduct(Product product);

	boolean removeProduct(Product product);

	boolean insertProduct(Product product);

	boolean updateProduct(Product product);
}
