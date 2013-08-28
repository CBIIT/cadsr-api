package gov.nih.nci.cadsrapi.dao.orm;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.JDBCException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import gov.nih.nci.cadsr.objectcart.domain.Cart;
import gov.nih.nci.cadsr.objectcart.domain.CartObject;
import gov.nih.nci.cadsrapi.dao.CartDAO;
import gov.nih.nci.cadsrapi.util.PropertiesLoader;
import gov.nih.nci.security.acegi.authentication.CSMAuthenticationProvider;
import gov.nih.nci.system.dao.DAOException;
import gov.nih.nci.system.dao.orm.WritableORMDAOImpl;
import gov.nih.nci.system.query.hibernate.HQLCriteria;
import gov.nih.nci.system.dao.Request;
import gov.nih.nci.system.dao.Response;


public class CartORMDAOImpl extends WritableORMDAOImpl implements CartDAO {

	private static Logger log = Logger.getLogger(CartORMDAOImpl.class.getName());

	/*public CartORMDAOImpl(SessionFactory sessionFactory, Configuration config,
			boolean caseSensitive, int resultCountPerQuery,
			boolean instanceLevelSecurity, boolean attributeLevelSecurity,
			CSMAuthenticationProvider authenticationProvider) {
		super();
		// TODO Auto-generated constructor stub
	}
*/
	
	public Cart storeCart(Cart newCart) throws DAOException, Exception {

		try
		{
			insert(newCart, true);

		} catch (JDBCException ex){
			log.error("JDBC Exception in ORMDAOImpl ", ex);
			throw new DAOException("JDBC Exception in ORMDAOImpl ", ex);
		} catch(org.hibernate.HibernateException hbmEx)	{
			log.error(hbmEx.getMessage());
			throw new DAOException("DAO:Hibernate problem ", hbmEx);
		} catch(Exception e) {
			log.error("Exception ", e);
			throw new DAOException("Exception in ORMDAOImpl ", e);
		}

		return newCart;
	}
	
	public Cart storeCart2(Cart newCart) throws DAOException, Exception {

		Session session = getSession();
		Transaction t = session.beginTransaction();


		try
		{
			session.saveOrUpdate(newCart);

		} catch (JDBCException ex){
			log.error("JDBC Exception in ORMDAOImpl ", ex);
			throw new DAOException("JDBC Exception in ORMDAOImpl ", ex);
		} catch(org.hibernate.HibernateException hbmEx)	{
			log.error(hbmEx.getMessage());
			throw new DAOException("DAO:Hibernate problem ", hbmEx);
		} catch(Exception e) {
			log.error("Exception ", e);
			throw new DAOException("Exception in ORMDAOImpl ", e);
		} finally {
			try
			{
				t.commit();
				session.close();
			}
			catch (Exception eSession)
			{
				log.error("Could not close the session - "+ eSession.getMessage());
				throw new DAOException("Could not close the session  " + eSession);
			}
		}

		return newCart;
	}

	
	public Cart updateCart(Cart newCart) throws DAOException, Exception {

		try
		{
			newCart.setLastWriteDate(new Date(System.currentTimeMillis()));
			update(newCart, true);
		} catch (JDBCException ex){
			log.error("JDBC Exception in ORMDAOImpl ", ex);
			throw new DAOException("JDBC Exception in ORMDAOImpl ", ex);
		} catch(org.hibernate.HibernateException hbmEx)	{
			log.error(hbmEx.getMessage());
			throw new DAOException("DAO:Hibernate problem ", hbmEx);
		} catch(Exception e) {
			log.error("Exception ", e);
			throw new DAOException("Exception in ORMDAOImpl ", e);
		}

		return newCart;
	}
	

	public List<Cart> cartSearch(Cart exampleCart) throws DAOException, Exception {
		List<Cart> results = new ArrayList<Cart>();
		List params = new ArrayList();
		StringBuilder query = new StringBuilder();
		query.append("from gov.nih.nci.cadsr.objectcart.domain.Cart where");
		if (exampleCart.getId() != null)
		{
			query.append(" id = ?");
			params.add(exampleCart.getId());
		}
		else {
			int andCntr = 0;
			if (exampleCart.getUserId() != null && exampleCart.getUserId().length() > 0){
				query.append(" userId = ?");
				andCntr++;
				params.add(exampleCart.getUserId());
			}
			if (exampleCart.getName() != null && exampleCart.getName().length() > 0){
				if (andCntr >0)
					query.append(" and");
				query.append(" name = ?");
				andCntr++;
				params.add(exampleCart.getName());
			}
		}
		query.append(" and (expirationDate > ? or expirationDate is null)");
		params.add(new Timestamp(System.currentTimeMillis()));

		HQLCriteria criteria = new HQLCriteria(query.toString(), params);

		Request request = new Request(criteria);
		request.setIsCount(Boolean.FALSE);
		request.setFirstRow(0);
		request.setDomainObjectName("gov.nih.nci.cadsr.objectcart.domain.Cart");
		try
		{
			Response response = query(request);
			results = (List<Cart>) response.getResponse();
		} catch (JDBCException ex){
			ex.printStackTrace();
			log.error("JDBC Exception in ORMDAOImpl ", ex);
			throw new DAOException("JDBC Exception in ORMDAOImpl ", ex);
		} catch(org.hibernate.HibernateException hbmEx)	{
			hbmEx.printStackTrace();
			log.error(hbmEx.getMessage());
			throw new DAOException("DAO:Hibernate problem ", hbmEx);
		} catch(Exception e) {
			e.printStackTrace();
			log.error("Exception ", e);
			throw new DAOException("Exception in ORMDAOImpl ", e);
		}
		return results;
	}

	public List<Cart> cartSearch2(Cart exampleCart) throws DAOException, Exception {
		List<Cart> results = new ArrayList<Cart>();
		Session session = getSession();

		//Transaction t = session.beginTransaction();
		StringBuilder query = new StringBuilder();
		query.append("from gov.nih.nci.cadsr.objectcart.domain.Cart where");

		System.out.println("exampleCart.getId() "+exampleCart.getId());
		
		if (exampleCart.getId() != null)
			query.append(" id = :cartId");
		else {
			int andCntr = 0;
			if (exampleCart.getUserId() != null && exampleCart.getUserId().length() > 0){
				query.append(" userId = :userId");
				andCntr++;
			}
			if (exampleCart.getName() != null && exampleCart.getName().length() > 0){
				if (andCntr >0)
					query.append(" and");
				query.append(" name = :name");
				andCntr++;
			}
			/*
			if (exampleCart.getType() != null && exampleCart.getType().length() > 0){
				if (andCntr >0)
					query.append(" and");
				query.append(" type = :type");
			}*/
		}
			query.append(" and (expirationDate > :expirationDate or expirationDate is null)");


		Query q = session.createQuery(query.toString());
		System.out.println("query.toString() "+query.toString());
		String[] params = q.getNamedParameters();

		for (String param: params){
		System.out.println("param "+param);
		System.out.println("cart "+exampleCart.toString());
			if ("cartId".equals(param))
				q.setInteger(param, exampleCart.getId());
			else {
				if ("userId".equals(param))
					q.setString(param, exampleCart.getUserId());
				if ("name".equals(param))
					q.setString(param, exampleCart.getName());
				/*if ("type".equals(param))
					q.setString(param, exampleCart.getType());*/
			}
		}

		q.setTimestamp("expirationDate", new Timestamp(System.currentTimeMillis()));

		try
		{
			results = (List<Cart>)q.list();

		} catch (JDBCException ex){
			ex.printStackTrace();
			log.error("JDBC Exception in ORMDAOImpl ", ex);
			throw new DAOException("JDBC Exception in ORMDAOImpl ", ex);
		} catch(org.hibernate.HibernateException hbmEx)	{
			hbmEx.printStackTrace();
			log.error(hbmEx.getMessage());
			throw new DAOException("DAO:Hibernate problem ", hbmEx);
		} catch(Exception e) {
			e.printStackTrace();
			log.error("Exception ", e);
			throw new DAOException("Exception in ORMDAOImpl ", e);
		} finally {
			try
			{
				//t.commit();
				session.close();
			}
			catch (Exception eSession)
			{
				log.error("Could not close the session - "+ eSession.getMessage());
				throw new DAOException("Could not close the session  " + eSession);
			}
		}
		return results;
	}
	public List<CartObject> cartObjectSearchByType(Cart exampleCart, String type) throws DAOException, Exception {
		List<CartObject> results = new ArrayList<CartObject>();
		Session session = getSession();

		Transaction t = session.beginTransaction();
		StringBuilder query = new StringBuilder();
		query.append("from gov.nih.nci.cadsr.objectcart.domain.CartObject where");
		query.append(" CART_ID = :cartId");
		query.append(" and");
		query.append(" type = :type");

		Query q = session.createQuery(query.toString());

		q.setInteger("cartId", exampleCart.getId());
		q.setString("type", type);

		try
		{
			results = (List<CartObject>) q.list();

		} catch (JDBCException ex){
			log.error("JDBC Exception in ORMDAOImpl ", ex);
			throw new DAOException("JDBC Exception in ORMDAOImpl ", ex);
		} catch(org.hibernate.HibernateException hbmEx)	{
			log.error(hbmEx.getMessage());
			throw new DAOException("DAO:Hibernate problem ", hbmEx);
		} catch(Exception e) {
			log.error("Exception ", e);
			throw new DAOException("Exception in ORMDAOImpl ", e);
		} finally {
			try
			{
				t.commit();
				session.close();
			}
			catch (Exception eSession)
			{
				log.error("Could not close the session - "+ eSession.getMessage());
				throw new DAOException("Could not close the session  " + eSession);
			}
		}
		return results;
	}
}
