/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-api/LICENSE.txt for details.
 */

package gov.nih.nci.cadsrapi.jUnit.applicationService.impl;

import gov.nih.nci.cadsr.objectcart.domain.Cart;
import gov.nih.nci.cadsr.objectcart.domain.CartObject;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ORMTest extends TestCase
{
	public void test1()
	{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("Spring-Config.xml");
		TestDAO dao = (TestDAO)ctx.getBean("DAO");
		CartObject o = new CartObject();
		o.setData("My Data");
		o.setDateAdded(new Date());
		o.setType("My Type");

		Cart c = new Cart();
		c.setCreationDate(new Date());
		c.setExpirationDate(new Date());
		c.setLastWriteDate(new Date());
		c.setLastReadDate(new Date());
		c.setName("My Name");
		c.setUserId("My User");

		Collection<CartObject> cartObjectCollection = new  HashSet<CartObject>();
		cartObjectCollection.add(o);
		c.setCartObjectCollection(cartObjectCollection);
		dao.saveOrUpdate(c);

	}
}