package dal.dao;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.ReplaceableItem;
import com.amazonaws.services.simpledb.model.SelectResult;

import dal.dto.DomainVotePurchase;
import dal.dto.DomainVotePurchase_Attr;
import dal.exceptions.DaoException;
import dal.exceptions.DomainVotePurchaseException;

public class DomainVotePurchaseDao extends DomainDao {
	public DomainVotePurchaseDao() {
		super();
	}

	public String insert(DomainVotePurchase ParamVote) throws DomainVotePurchaseException {

		List<ReplaceableAttribute> replaceableAttributes;
		replaceableAttributes = ParamVote.getReplaceableAttributes();

		try {
			super.insert(DomainVotePurchase_Attr.Domain, ParamVote.getID(), replaceableAttributes);
		} catch (DaoException e) {
			throw new DomainVotePurchaseException(e.getMessage());
		}
		return ParamVote.getID();
	}

	public void delete(DomainVotePurchase ParamVote) throws DomainVotePurchaseException {

		// Delete an item and all of its attributes
		try {
			super.delete(DomainVotePurchase_Attr.Domain, ParamVote.getID());
		} catch (DaoException e) {
			throw new DomainVotePurchaseException(e.getMessage());
		}

	}

	public void update(DomainVotePurchase ParamVote) throws DomainVotePurchaseException {
		List<ReplaceableAttribute> replaceableAttributes;
		replaceableAttributes = ParamVote.getReplaceableAttributes();

		try {
			super.update(DomainVotePurchase_Attr.Domain, ParamVote.getID(), replaceableAttributes);
		} catch (DaoException e) {
			throw new DomainVotePurchaseException(e.getMessage());
		}
	}

	public void batchUpdate(List<DomainVotePurchase> paramImages) throws DomainVotePurchaseException {

		List<ReplaceableItem> replaceableItems = new ArrayList<ReplaceableItem>();
		int iCount = 0;
		
		try {
			for (DomainVotePurchase item : paramImages) {
				iCount++;
				replaceableItems.add(item.getReplaceableItem());
				if(iCount==25)
				{//max of 25 per request
					super.batchUpdate(DomainVotePurchase_Attr.Domain, replaceableItems);
					replaceableItems.clear();
					iCount = 0;
				}
				
			}
			if(replaceableItems.size() > 0)
				super.batchUpdate(DomainVotePurchase_Attr.Domain, replaceableItems);
		} catch (DaoException e) {
			throw new DomainVotePurchaseException(e.getMessage());
		}

	}

	public DomainVotePurchase findWhereIDEquals(String paramString) throws DomainVotePurchaseException {

		DomainVotePurchase rtn = null;

		// Select data from a domain
		String selectExpression = "select * from `" + DomainVotePurchase_Attr.Domain + "`" + " where itemName() = '" + paramString + "'";

		// get the first/only item
		try {
			SelectResult result = super.select(selectExpression, "", true);

			if (!result.getItems().isEmpty()) {
				Item item = result.getItems().get(0);
				rtn = new DomainVotePurchase.Builder(item).build();
			}

		} catch (Exception e) {
			throw new DomainVotePurchaseException(e.getMessage());
		}

		return rtn;

	}
													 
	public List<DomainVotePurchase> findNew() throws DomainVotePurchaseException {

		List<DomainVotePurchase> rtn = new ArrayList<DomainVotePurchase>();

		// Select data from a domain
		String selectExpression = " select * from `" + DomainVotePurchase_Attr.Domain + "`" +
								  " where " + DomainVotePurchase_Attr.Complete + " = 'false'";
		
		// Select data from a domain
		try {
			SelectResult result = super.select(selectExpression, "", true);

			if (!result.getItems().isEmpty()) {
				// build the list
				for (Item item : result.getItems())
					rtn.add(new DomainVotePurchase.Builder(item).build());
			}

		} catch (Exception e) {
			throw new DomainVotePurchaseException(e.getMessage());
		}

		return rtn;

	}
	
}
