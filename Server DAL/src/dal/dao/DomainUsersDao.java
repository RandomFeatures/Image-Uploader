package dal.dao;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.ReplaceableItem;
import com.amazonaws.services.simpledb.model.SelectResult;

import dal.dto.DomainUsers;
import dal.dto.DomainUsers_Attr;
import dal.exceptions.DaoException;
import dal.exceptions.DomainUsersException;

public class DomainUsersDao extends DomainDao {
	
	public DomainUsersDao() {
		super();
	}

	public String insert(DomainUsers paramImages) throws DomainUsersException {

		List<ReplaceableAttribute> replaceableAttributes;
		replaceableAttributes = paramImages.getReplaceableAttributes();

		try {
			super.insert(DomainUsers_Attr.Domain, paramImages.getID(), replaceableAttributes);
		} catch (DaoException e) {
			throw new DomainUsersException(e.getMessage());
		}
		return paramImages.getID();
	}

	public void delete(DomainUsers paramImages) throws DomainUsersException {

		// Delete an item and all of its attributes
		try {
			super.delete(DomainUsers_Attr.Domain, paramImages.getID());
		} catch (DaoException e) {
			throw new DomainUsersException(e.getMessage());
		}

	}

	public void update(DomainUsers paramImages) throws DomainUsersException {
		List<ReplaceableAttribute> replaceableAttributes;
		replaceableAttributes = paramImages.getReplaceableAttributes();

		try {
			super.update(DomainUsers_Attr.Domain, paramImages.getID(), replaceableAttributes);
		} catch (DaoException e) {
			throw new DomainUsersException(e.getMessage());
		}
	}

	public void batchUpdate(DomainUsers paramImages[]) throws DomainUsersException {

		List<ReplaceableItem> replaceableItems = new ArrayList<ReplaceableItem>();

		try {

			for (DomainUsers item : paramImages) {
				replaceableItems.add(item.getReplaceableItem());
			}
			super.batchUpdate(DomainUsers_Attr.Domain, replaceableItems);

		} catch (DaoException e) {
			throw new DomainUsersException(e.getMessage());
		}

	}

	public DomainUsers findWhereIDEquals(String paramString) throws DomainUsersException {

		DomainUsers rtn = null;

		// Select data from a domain
		String selectExpression = "select * from `" + DomainUsers_Attr.Domain + "`" + "where itemName() = '" + paramString + "'";

		// get the first/only item
		try {
			SelectResult result = super.select(selectExpression, "", false);

			if (!result.getItems().isEmpty()) {
				Item item = result.getItems().get(0);
				rtn = new DomainUsers.Builder(item).build();
			}

		} catch (Exception e) {
			throw new DomainUsersException(e.getMessage());
		}

		return rtn;

	}
	
	public DomainUsers findWhereUserIDEquals(String paramString) throws DomainUsersException {

		DomainUsers rtn = null;

		// Select data from a domain
		String selectExpression = "select * from `" + DomainUsers_Attr.Domain + "`" + " where "+ DomainUsers_Attr.UserID +" = '" + paramString + "'";

		// get the first/only item
		try {
			SelectResult result = super.select(selectExpression, "", true);

			if (!result.getItems().isEmpty()) {
				Item item = result.getItems().get(0);
				rtn = new DomainUsers.Builder(item).build();
			}

		} catch (Exception e) {
			throw new DomainUsersException(e.getMessage());
		}

		return rtn;

	}
	
	
	public List<DomainUsers> findCurrentViewing(String oldest) throws DomainUsersException {

		List<DomainUsers> resultList = new ArrayList<DomainUsers>();
		String nextToken = "";
		
		// Select data from a domain
		String selectExpression = " select * from `" + DomainUsers_Attr.Domain + "`" +
								  " where " + DomainUsers_Attr.CurrentImage + " is not null" +
								  " and " + DomainUsers_Attr.LastUpdated + " > '" + oldest +"'" ;
		
		try {
			do
			{
				//get the results
				SelectResult result =  super.select(selectExpression, nextToken, false);
				
				if(!result.getItems().isEmpty())
				{
					for (Item item : result.getItems()) 
					{
						 resultList.add(new DomainUsers.Builder(item).build());
			        }
				}	
				nextToken = result.getNextToken();
			} while (nextToken != null);
		} catch (Exception e) {
			throw new DomainUsersException(e.getMessage());
		}
		return resultList;

	}

}
