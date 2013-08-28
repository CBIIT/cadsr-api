package gov.nih.nci.cadsrapi.dao;

import java.util.List;

import gov.nih.nci.cadsr.objectcart.domain.Cart;
import gov.nih.nci.cadsr.objectcart.domain.CartObject;
import gov.nih.nci.system.dao.DAO;
import gov.nih.nci.system.dao.DAOException;

/**
 * @author Denis Avdic
 */

public interface CartDAO extends DAO {

	public Cart storeCart(Cart newCart) throws DAOException, Exception;
	public Cart updateCart(Cart newCart) throws DAOException, Exception;
	public List<Cart> cartSearch(Cart newCart) throws DAOException, Exception;
	public List<CartObject> cartObjectSearchByType(Cart exampleCart, String type) throws DAOException, Exception;
}
