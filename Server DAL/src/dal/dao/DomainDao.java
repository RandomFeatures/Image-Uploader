package dal.dao;

import java.io.IOException;
import java.util.List;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.BatchDeleteAttributesRequest;
import com.amazonaws.services.simpledb.model.BatchPutAttributesRequest;
import com.amazonaws.services.simpledb.model.DeletableItem;
import com.amazonaws.services.simpledb.model.DeleteAttributesRequest;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.ReplaceableItem;
import com.amazonaws.services.simpledb.model.SelectRequest;
import com.amazonaws.services.simpledb.model.SelectResult;

import dal.exceptions.DaoException;

public class DomainDao {

	protected AmazonSimpleDB m_Simpledb;
	
	public DomainDao() {
		super();
		try {
			//m_Simpledb = new AmazonSimpleDBClient(new PropertiesCredentials(property));
			m_Simpledb = new AmazonSimpleDBClient(new PropertiesCredentials(DomainDao.class.getResourceAsStream("/AwsCredentials.properties")));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void insert(String domain, String id,  List<ReplaceableAttribute> attributes) throws DaoException {
		m_Simpledb.putAttributes(new PutAttributesRequest(domain,id, attributes));
	}


	protected void delete(String domain, String id) throws DaoException {
		 // Delete an item and all of its attributes
        m_Simpledb.deleteAttributes(new DeleteAttributesRequest(domain, id));
	}
 
	protected void batchDelete(String domain, List<DeletableItem> items) throws DaoException {
		 // Delete an item and all of its attributes
       m_Simpledb.batchDeleteAttributes(new BatchDeleteAttributesRequest(domain, items));
	}
	
	protected void update(String domain, String id,  List<ReplaceableAttribute> attributes) throws DaoException {
		//sdb.domainMetadata(new DomainMetadataRequest(DomainSets_Attr.Domain)).getItemCount()
        m_Simpledb.putAttributes(new PutAttributesRequest(domain,id, attributes));
        
	}

	protected void batchUpdate(String domain, List<ReplaceableItem> items) throws DaoException {
		 m_Simpledb.batchPutAttributes(new BatchPutAttributesRequest(domain, items));
	}
	
	
	protected SelectResult select(String query, String nextToken, boolean consistentRead)
	{
		SelectRequest selectRequest;
		
		//if(nextToken.isEmpty())
		//	selectRequest = new SelectRequest(query);
		//else
			selectRequest = new SelectRequest(query).withNextToken(nextToken).withConsistentRead(consistentRead);
		
		//get the results
		return m_Simpledb.select(selectRequest);
	}
	
	
}
