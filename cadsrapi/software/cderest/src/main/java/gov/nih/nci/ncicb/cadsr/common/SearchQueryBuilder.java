package gov.nih.nci.ncicb.cadsr.common;

import javax.servlet.http.HttpServletRequest;
import gov.nih.nci.ncicb.cadsr.common.cdebrowser.DataElementSearchBean;

import gov.nih.nci.ncicb.cadsr.common.cdebrowser.DESearchQueryBuilder;
import gov.nih.nci.ncicb.cadsr.common.cdebrowser.DataElementSearchBean;

public class SearchQueryBuilder extends DESearchQueryBuilder {
	public SearchQueryBuilder(HttpServletRequest request,
            String treeParamType,
            String treeParamIdSeq,
            String treeConteIdSeq,
            DataElementSearchBean searchBean)  {
		super(request, treeParamType, treeParamIdSeq, treeConteIdSeq, searchBean);
	}
	
	@Override  
	protected String getUsageWhereClause() {
		  String usageWhere = "";

	      if ("CONTEXT".equals(getTreeParamType())) {
	        usageWhere =
	          " and de.de_idseq IN (select ac_idseq " +
	          "                     from   sbr.designations_view des " +
	          "                     where  des.conte_idseq in ("+getTreeConteIdSeq()+")" +
	          "                     and    des.DETL_NAME = 'USED_BY' " +
	          "                     UNION "+
	          "                     select de_idseq "+
	          "                     from   sbr.data_elements_view de1 "+
	          "                     where  de1.conte_idseq in ("+getTreeConteIdSeq()+")) ";
	      }


          return usageWhere;
	  }
	  
	@Override   
	protected String getCSWhere(String csId) {
		    String csWhere =  " and de.de_idseq IN ( " +
		                      " select de_idseq " +
		                      " from  sbr.data_elements_view de , " +
		                      "       sbr.ac_csi_view acs, " +
		                      "       sbr.cs_csi_view csc " +
		                      " where csc.cs_idseq in ("+csId+")" +
		                      " and   csc.cs_csi_idseq = acs.cs_csi_idseq " +
		                      " and   acs.ac_idseq = de_idseq ) ";
		    return csWhere;

	   }
	
	@Override
	protected String getCSItemWhereClause(String searchStr5) {
		String csiWhere;
		csiWhere = " and de.de_idseq = acs.ac_idseq " +
		           " and acs.cs_csi_idseq in ("+searchStr5+") ";
		return csiWhere;
	}
}
