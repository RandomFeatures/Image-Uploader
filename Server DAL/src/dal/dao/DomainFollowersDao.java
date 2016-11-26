package dal.dao;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.ReplaceableItem;
import com.amazonaws.services.simpledb.model.SelectResult;

import dal.dto.DomainFollowers;
import dal.dto.DomainFollowers_Attr;
import dal.exceptions.DaoException;
import dal.exceptions.DomainFollowersException;

public class DomainFollowersDao extends DomainDao {

	public DomainFollowersDao() {
		super();
	}

	public String insert(DomainFollowers paramImages) throws DomainFollowersException {

		List<ReplaceableAttribute> replaceableAttributes;
		replaceableAttributes = paramImages.getReplaceableAttributes();

		try {
			super.insert(DomainFollowers_Attr.Domain, paramImages.getID(), replaceableAttributes);
		} catch (DaoException e) {
			throw new DomainFollowersException(e.getMessage());
		}
		return paramImages.getID();
	}

	public void delete(DomainFollowers paramImages) throws DomainFollowersException {

		// Delete an item and all of its attributes
		try {
			super.delete(DomainFollowers_Attr.Domain, paramImages.getID());
		} catch (DaoException e) {
			throw new DomainFollowersException(e.getMessage());
		}

	}

	public void update(DomainFollowers paramImages) throws DomainFollowersException {
		List<ReplaceableAttribute> replaceableAttributes;
		replaceableAttributes = paramImages.getReplaceableAttributes();

		try {
			super.update(DomainFollowers_Attr.Domain, paramImages.getID(), replaceableAttributes);
		} catch (DaoException e) {
			throw new DomainFollowersException(e.getMessage());
		}
	}

	public void batchUpdate(DomainFollowers paramImages[]) throws DomainFollowersException {

		List<ReplaceableItem> replaceableItems = new ArrayList<ReplaceableItem>();

		try {

			for (DomainFollowers item : paramImages) {
				replaceableItems.add(item.getReplaceableItem());
			}
			super.batchUpdate(DomainFollowers_Attr.Domain, replaceableItems);

		} catch (DaoException e) {
			throw new DomainFollowersException(e.getMessage());
		}

	}

	public DomainFollowers findWhereIDEquals(String paramString) throws DomainFollowersException {

		DomainFollowers rtn = null;

		// Select data from a domain
		String selectExpression = "select * from `" + DomainFollowers_Attr.Domain + "`" + "where itemName() = '" + paramString + "'";

		// get the first/only item
		try {
			SelectResult result = super.select(selectExpression, "", false);

			if (!result.getItems().isEmpty()) {
				Item item = result.getItems().get(0);
				rtn = new DomainFollowers.Builder(item).build();
			}

		} catch (Exception e) {
			throw new DomainFollowersException(e.getMessage());
		}

		return rtn;

	}
	
}
