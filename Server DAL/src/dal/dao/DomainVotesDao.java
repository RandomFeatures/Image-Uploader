package dal.dao;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.simpledb.model.DeletableItem;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.ReplaceableItem;
import com.amazonaws.services.simpledb.model.SelectResult;

import dal.dto.DomainVotes;
import dal.dto.DomainVotes_Attr;
import dal.exceptions.DaoException;
import dal.exceptions.DomainVotesException;

public class DomainVotesDao extends DomainDao {
	public DomainVotesDao() {
		super();
	}

	public String insert(DomainVotes ParamVote) throws DomainVotesException {

		List<ReplaceableAttribute> replaceableAttributes;
		replaceableAttributes = ParamVote.getReplaceableAttributes();

		try {
			super.insert(DomainVotes_Attr.Domain + ParamVote.getSuffix(), ParamVote.getID(), replaceableAttributes);
		} catch (DaoException e) {
			throw new DomainVotesException(e.getMessage());
		}
		return ParamVote.getID();
	}

	public void delete(DomainVotes ParamVote) throws DomainVotesException {

		// Delete an item and all of its attributes
		try {
			super.delete(DomainVotes_Attr.Domain + ParamVote.getSuffix(), ParamVote.getID());
		} catch (DaoException e) {
			throw new DomainVotesException(e.getMessage());
		}

	}

	public void update(DomainVotes ParamVote) throws DomainVotesException {
		List<ReplaceableAttribute> replaceableAttributes;
		replaceableAttributes = ParamVote.getReplaceableAttributes();

		try {
			super.update(DomainVotes_Attr.Domain + ParamVote.getSuffix(), ParamVote.getID(), replaceableAttributes);
		} catch (DaoException e) {
			throw new DomainVotesException(e.getMessage());
		}
	}

	public void UpdateBatch(String domain,  List<DomainVotes> ParamVote) throws DomainVotesException {

		List<ReplaceableItem> replaceableItems = new ArrayList<ReplaceableItem>();
		int iCount = 0;
		try {

			for (DomainVotes item : ParamVote) {
				iCount++;
				replaceableItems.add(item.getReplaceableItem());
				if(iCount==25)
				{//max of 25 per request
					super.batchUpdate(domain, replaceableItems);
					replaceableItems.clear();
					iCount = 0;
				}
			}
			if(replaceableItems.size() > 0)
				super.batchUpdate(domain, replaceableItems);

		} catch (DaoException e) {
			throw new DomainVotesException(e.getMessage());
		}

	}

	public void deleteBatch(String domain, List<DomainVotes> ParamVote) throws DomainVotesException {

		List<DeletableItem> deleteItems = new ArrayList<DeletableItem>();
		int iCount = 0;
		try {
			
			for (DomainVotes item : ParamVote) {
				iCount++;
				deleteItems.add(item.getDeleteableItem());
				if(iCount==25)
				{//max of 25 per request
					super.batchDelete(domain, deleteItems);
					deleteItems.clear();
					iCount = 0;
				}
			}
			if(deleteItems.size() > 0)
				super.batchDelete(domain, deleteItems);

		} catch (DaoException e) {
			throw new DomainVotesException(e.getMessage());
		}

	}

	
	public DomainVotes findWhereIDEquals(String suffix, String paramString) throws DomainVotesException {

		DomainVotes rtn = null;

		// Select data from a domain
		String selectExpression = "select * from `" + DomainVotes_Attr.Domain + suffix + "`" + "where itemName() = '" + paramString + "'";

		// get the first/only item
		try {
			SelectResult result = super.select(selectExpression, "", true);

			if (!result.getItems().isEmpty()) {
				Item item = result.getItems().get(0);
				rtn = new DomainVotes.Builder(item).build();
			}

		} catch (Exception e) {
			throw new DomainVotesException(e.getMessage());
		}

		return rtn;

	}
	
	public List<DomainVotes> findAll(String domain) throws DomainVotesException {

		List<DomainVotes> rtn = new ArrayList<DomainVotes>();

		// Select data from a domain
		String selectExpression = " select * from `" + domain + "`";
		// Select data from a domain
		try {
			SelectResult result = super.select(selectExpression, "", true);

			if (!result.getItems().isEmpty()) {
				// build the list
				for (Item item : result.getItems())
					rtn.add(new DomainVotes.Builder(item).build());
			}

		} catch (Exception e) {
			throw new DomainVotesException(e.getMessage());
		}

		return rtn;

	}
	
}
