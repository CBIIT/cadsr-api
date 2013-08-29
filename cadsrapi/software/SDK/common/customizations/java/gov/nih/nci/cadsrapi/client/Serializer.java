/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-api/LICENSE.txt for details.
 */

package gov.nih.nci.cadsrapi.client;

import gov.nih.nci.cadsr.objectcart.domain.CartObject;

public interface Serializer {

	public CartObject serializeObject(Class cl, String displayName, String nativeId, Object ob) throws ObjectCartException;

	public Object deserializeObject(Class cl, CartObject cob) throws ObjectCartException;

}
