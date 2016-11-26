package dal.dao;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.ReplaceableItem;
import com.amazonaws.services.simpledb.model.SelectResult;

import dal.dto.DomainImages_Attr;
import dal.dto.DomainUserVotes;
import dal.dto.DomainUserVotes_Attr;
import dal.exceptions.DaoException;
import dal.exceptions.DomainUserVotesException;

public class DomainUserVotesDao extends DomainDao {
	public DomainUserVotesDao() {
		super();
	}

	public String insert(DomainUserVotes paramImages) throws DomainUserVotesException {

		List<ReplaceableAttribute> replaceableAttributes;
		replaceableAttributes = paramImages.getReplaceableAttributes();

		try {
			super.insert(DomainUserVotes_Attr.Domain, paramImages.getID(), replaceableAttributes);
		} catch (DaoException e) {
			throw new DomainUserVotesException(e.getMessage());
		}
		return paramImages.getID();
	}

	public void delete(DomainUserVotes paramImages) throws DomainUserVotesException {

		// Delete an item and all of its attributes
		try {
			super.delete(DomainUserVotes_Attr.Domain, paramImages.getID());
		} catch (DaoException e) {
			throw new DomainUserVotesException(e.getMessage());
		}

	}

	public void update(DomainUserVotes paramImages) throws DomainUserVotesException {
		List<ReplaceableAttribute> replaceableAttributes;
		replaceableAttributes = paramImages.getReplaceableAttributes();

		try {
			super.update(DomainUserVotes_Attr.Domain, paramImages.getID(), replaceableAttributes);
		} catch (DaoException e) {
			throw new DomainUserVotesException(e.getMessage());
		}
	}

	public void batchUpdate(DomainUserVotes paramImages[]) throws DomainUserVotesException {

		List<ReplaceableItem> replaceableItems = new ArrayList<ReplaceableItem>();

		try {

			for (DomainUserVotes item : paramImages) {
				replaceableItems.add(item.getReplaceableItem());
			}
			super.batchUpdate(DomainUserVotes_Attr.Domain, replaceableItems);

		} catch (DaoException e) {
			throw new DomainUserVotesException(e.getMessage());
		}

	}

	public DomainUserVotes findWhereIDEquals(String paramString) throws DomainUserVotesException {

		DomainUserVotes rtn = null;

		// Select data from a domain
		String selectExpression = "select * from `" + DomainUserVotes_Attr.Domain + "`" + "where itemName() = '" + paramString + "'";

		// get the first/only item
		try {
			SelectResult result = super.select(selectExpression, "", true);

			if (!result.getItems().isEmpty()) {
				Item item = result.getItems().get(0);
				rtn = new DomainUserVotes.Builder(item).build();
			}

		} catch (Exception e) {
			throw new DomainUserVotesException(e.getMessage());
		}

		return rtn;

	}
	
	public DomainUserVotes findImageIDEquals(String userID, String imageID) throws DomainUserVotesException {

		DomainUserVotes rtn = null;

		// Select data from a domain
		String selectExpression = " select * from `" + DomainUserVotes_Attr.Domain + "`" +
								  " where " + DomainUserVotes_Attr.UserID + " = '" + userID + "'" + 
								  " and " + DomainUserVotes_Attr.ImageID + " = '" + imageID + "'";

		// get the first/only item
		try {
			SelectResult result = super.select(selectExpression, "", true);

			if (!result.getItems().isEmpty()) {
				Item item = result.getItems().get(0);
				rtn = new DomainUserVotes.Builder(item).build();
			}

		} catch (Exception e) {
			throw new DomainUserVotesException(e.getMessage());
		}

		return rtn;

	}
	
	public List<DomainUserVotes> findUserIDEquals(String userID) throws DomainUserVotesException {

		List<DomainUserVotes> resultList = new ArrayList<DomainUserVotes>();
		String nextToken = "";

		// Select data from a domain
		String selectExpression = " select * from `" + DomainUserVotes_Attr.Domain + "`" +
								  " where " + DomainUserVotes_Attr.UserID + " = '" + userID + "'" + 
								  " and " + DomainImages_Attr.Date + " is not null" +	
								  " order by " + DomainImages_Attr.Date  + " desc";

		try {
			do
			{
				//get the results
				SelectResult result =  super.select(selectExpression, nextToken, true);
				
				if(!result.getItems().isEmpty())
				{
					for (Item item : result.getItems()) 
					{
						 resultList.add(new DomainUserVotes.Builder(item).build());
			        }
				}	
				nextToken = result.getNextToken();
			} while (nextToken != null);
		} catch (Exception e) {
			throw new DomainUserVotesException(e.getMessage());
		}
	

		return resultList;

	}
	
	public int checkPreviousVotes(String userID, String imageID) throws DomainUserVotesException {

		int rtn = 0;

		// Select data from a domain
		String selectExpression = " select count(*) from `" + DomainUserVotes_Attr.Domain + "`" +
								  " where " + DomainUserVotes_Attr.UserID + " = '" + userID + "'" + 
								  " and " + DomainUserVotes_Attr.ImageID + " = '" + imageID + "'";

		// get the first/only item
		try {
			SelectResult result = super.select(selectExpression, "", true);
		
			if (!result.getItems().isEmpty()) {
				//{Name: Domain, Attributes: [{Name: Count, Value: 3, }], }
				Item item = result.getItems().get(0);
				//{Name: Count, Value: 3, }
				rtn = Integer.parseInt(item.getAttributes().get(0).getValue());
			}

		} catch (Exception e) {
			throw new DomainUserVotesException(e.getMessage());
		}

		return rtn;

	}
	
}
