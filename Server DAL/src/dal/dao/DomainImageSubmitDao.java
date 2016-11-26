package dal.dao;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.simpledb.model.DeletableItem;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.ReplaceableItem;
import com.amazonaws.services.simpledb.model.SelectResult;

import dal.dto.DomainImageSubmit;
import dal.dto.DomainImageSubmit_Attr;
import dal.exceptions.DaoException;
import dal.exceptions.DomainImageSubmitException;

public class DomainImageSubmitDao extends DomainDao {
	public DomainImageSubmitDao() {
		super();
	}

	public String insert(DomainImageSubmit paramImages) throws DomainImageSubmitException {

		List<ReplaceableAttribute> replaceableAttributes;
		replaceableAttributes = paramImages.getReplaceableAttributes();

		try {
			super.insert(DomainImageSubmit_Attr.Domain + paramImages.getSuffix(), paramImages.getID(), replaceableAttributes);
		} catch (DaoException e) {
			throw new DomainImageSubmitException(e.getMessage());
		}
		return paramImages.getID();
	}

	public void delete(DomainImageSubmit paramImages) throws DomainImageSubmitException {

		// Delete an item and all of its attributes
		try {
			super.delete(DomainImageSubmit_Attr.Domain + paramImages.getSuffix(), paramImages.getID());
		} catch (DaoException e) {
			throw new DomainImageSubmitException(e.getMessage());
		}

	}

	public void update(DomainImageSubmit paramImages) throws DomainImageSubmitException {
		List<ReplaceableAttribute> replaceableAttributes;
		replaceableAttributes = paramImages.getReplaceableAttributes();

		try {
			super.update(DomainImageSubmit_Attr.Domain + paramImages.getSuffix(), paramImages.getID(), replaceableAttributes);
		} catch (DaoException e) {
			throw new DomainImageSubmitException(e.getMessage());
		}
	}

	public void batchUpdate(String suffix, DomainImageSubmit paramImages[]) throws DomainImageSubmitException {

		List<ReplaceableItem> replaceableItems = new ArrayList<ReplaceableItem>();

		try {

			for (DomainImageSubmit item : paramImages) {
				replaceableItems.add(item.getReplaceableItem());
			}
			super.batchUpdate(DomainImageSubmit_Attr.Domain + suffix, replaceableItems);

		} catch (DaoException e) {
			throw new DomainImageSubmitException(e.getMessage());
		}

	}

	public void deleteBatch(String domain, List<DomainImageSubmit> ParamSubmit) throws DomainImageSubmitException {

		List<DeletableItem> deleteItems = new ArrayList<DeletableItem>();
		int iCount = 0;
		try {
			
			for (DomainImageSubmit item : ParamSubmit) {
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
			throw new DomainImageSubmitException(e.getMessage());
		}
	}
	
	public DomainImageSubmit findWhereIDEquals(String suffix, String paramString) throws DomainImageSubmitException {

		DomainImageSubmit rtn = null;

		// Select data from a domain
		String selectExpression = "select * from `" + DomainImageSubmit_Attr.Domain + suffix + "`" + " where itemName() = '" + paramString + "'";

		// get the first/only item
		try {
			SelectResult result = super.select(selectExpression, "", true);

			if (!result.getItems().isEmpty()) {
				Item item = result.getItems().get(0);
				rtn = new DomainImageSubmit.Builder(item).build();
			}

		} catch (Exception e) {
			throw new DomainImageSubmitException(e.getMessage());
		}
		return rtn;
	}
	
	public List<DomainImageSubmit> findAll(String domain) throws DomainImageSubmitException {

		List<DomainImageSubmit> rtn = new ArrayList<DomainImageSubmit>();

		// Select data from a domain
		String selectExpression = " select * from `" + domain + "`";
		// Select data from a domain
		try {
			SelectResult result = super.select(selectExpression, "", true);

			if (!result.getItems().isEmpty()) {
				// build the list
				for (Item item : result.getItems())
					rtn.add(new DomainImageSubmit.Builder(item).build());
			}

		} catch (Exception e) {
			throw new DomainImageSubmitException(e.getMessage());
		}

		return rtn;

	}
	
}
