package dal.dao;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.ReplaceableItem;
import com.amazonaws.services.simpledb.model.SelectResult;

import dal.dto.DomainUserImages;
import dal.dto.DomainUserImages_Attr;
import dal.exceptions.DaoException;
import dal.exceptions.DomainUserImagesException;

public class DomainUserImagesDao extends DomainDao {
	public DomainUserImagesDao() {
		super();
	}

	public String insert(DomainUserImages paramImages) throws DomainUserImagesException {

		List<ReplaceableAttribute> replaceableAttributes;
		replaceableAttributes = paramImages.getReplaceableAttributes();

		try {
			super.insert(DomainUserImages_Attr.Domain, paramImages.getID(), replaceableAttributes);
		} catch (DaoException e) {
			throw new DomainUserImagesException(e.getMessage());
		}
		return paramImages.getID();
	}

	public void delete(DomainUserImages paramImages) throws DomainUserImagesException {

		// Delete an item and all of its attributes
		try {
			super.delete(DomainUserImages_Attr.Domain, paramImages.getID());
		} catch (DaoException e) {
			throw new DomainUserImagesException(e.getMessage());
		}

	}

	public void update(DomainUserImages paramImages) throws DomainUserImagesException {
		List<ReplaceableAttribute> replaceableAttributes;
		replaceableAttributes = paramImages.getReplaceableAttributes();

		try {
			super.update(DomainUserImages_Attr.Domain, paramImages.getID(), replaceableAttributes);
		} catch (DaoException e) {
			throw new DomainUserImagesException(e.getMessage());
		}
	}

	public void batchUpdate(DomainUserImages paramImages[]) throws DomainUserImagesException {

		List<ReplaceableItem> replaceableItems = new ArrayList<ReplaceableItem>();

		try {

			for (DomainUserImages item : paramImages) {
				replaceableItems.add(item.getReplaceableItem());
			}
			super.batchUpdate(DomainUserImages_Attr.Domain, replaceableItems);

		} catch (DaoException e) {
			throw new DomainUserImagesException(e.getMessage());
		}

	}

	public DomainUserImages findWhereIDEquals(String paramString) throws DomainUserImagesException {

		DomainUserImages rtn = null;

		// Select data from a domain
		String selectExpression = "select * from `" + DomainUserImages_Attr.Domain + "`" + "where itemName() = '" + paramString + "'";

		// get the first/only item
		try {
			SelectResult result = super.select(selectExpression, "", false);

			if (!result.getItems().isEmpty()) {
				Item item = result.getItems().get(0);
				rtn = new DomainUserImages.Builder(item).build();
			}

		} catch (Exception e) {
			throw new DomainUserImagesException(e.getMessage());
		}

		return rtn;

	}
}
