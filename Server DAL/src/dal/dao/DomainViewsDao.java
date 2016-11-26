package dal.dao;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.simpledb.model.DeletableItem;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.ReplaceableItem;
import com.amazonaws.services.simpledb.model.SelectResult;

import dal.dto.DomainViews;
import dal.dto.DomainViews_Attr;
import dal.exceptions.DaoException;
import dal.exceptions.DomainViewsException;

public class DomainViewsDao extends DomainDao {
	
	public DomainViewsDao() {
		super();
	}

	public String insert(DomainViews paramViews) throws DomainViewsException {

		List<ReplaceableAttribute> replaceableAttributes;
		replaceableAttributes = paramViews.getReplaceableAttributes();

		try {
			super.insert(DomainViews_Attr.Domain + paramViews.getSuffix(), paramViews.getID(), replaceableAttributes);
		} catch (DaoException e) {
			throw new DomainViewsException(e.getMessage());
		}
		return paramViews.getID();
	}

	public void delete(DomainViews paramViews) throws DomainViewsException {

		// Delete an item and all of its attributes
		try {
			super.delete(DomainViews_Attr.Domain + paramViews.getSuffix(), paramViews.getID());
		} catch (DaoException e) {
			throw new DomainViewsException(e.getMessage());
		}

	}

	public void deleteBatch(String domain, List<DomainViews> ParamViews) throws DomainViewsException {

		List<DeletableItem> deleteItems = new ArrayList<DeletableItem>();
		int iCount = 0;
		try {
			
			for (DomainViews item : ParamViews) {
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
			throw new DomainViewsException(e.getMessage());
		}

	}
	
	
	public void update(DomainViews paramViews) throws DomainViewsException {
		List<ReplaceableAttribute> replaceableAttributes;
		replaceableAttributes = paramViews.getReplaceableAttributes();

		try {
			super.update(DomainViews_Attr.Domain + paramViews.getSuffix(), paramViews.getID(), replaceableAttributes);
		} catch (DaoException e) {
			throw new DomainViewsException(e.getMessage());
		}
	}

	public void batchUpdate(String suffix, DomainViews paramViews[]) throws DomainViewsException {

		List<ReplaceableItem> replaceableItems = new ArrayList<ReplaceableItem>();

		try {

			for (DomainViews item : paramViews) {
				replaceableItems.add(item.getReplaceableItem());
			}
			super.batchUpdate(DomainViews_Attr.Domain + suffix, replaceableItems);

		} catch (DaoException e) {
			throw new DomainViewsException(e.getMessage());
		}

	}

	public DomainViews findWhereIDEquals(String suffix, String paramString) throws DomainViewsException {

		DomainViews rtn = null;

		// Select data from a domain
		String selectExpression = "select * from `" + DomainViews_Attr.Domain + suffix + "`" + "where itemName() = '" + paramString + "'";

		// get the first/only item
		try {
			SelectResult result = super.select(selectExpression, "", true);

			if (!result.getItems().isEmpty()) {
				Item item = result.getItems().get(0);
				rtn = new DomainViews.Builder(item).build();
			}

		} catch (Exception e) {
			throw new DomainViewsException(e.getMessage());
		}

		return rtn;

	}
	
	public List<DomainViews> findAll(String domain) throws DomainViewsException {

		List<DomainViews> rtn = new ArrayList<DomainViews>();

		// Select data from a domain
		String selectExpression = " select * from `" + domain + "`";
		// Select data from a domain
		try {
			SelectResult result = super.select(selectExpression, "", true);

			if (!result.getItems().isEmpty()) {
				// build the list
				for (Item item : result.getItems())
					rtn.add(new DomainViews.Builder(item).build());
			}

		} catch (Exception e) {
			throw new DomainViewsException(e.getMessage());
		}

		return rtn;

	}
}
