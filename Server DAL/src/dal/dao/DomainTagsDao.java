package dal.dao;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.ReplaceableItem;
import com.amazonaws.services.simpledb.model.SelectResult;

import dal.dto.DomainTags;
import dal.dto.DomainTags_Attr;
import dal.exceptions.DaoException;
import dal.exceptions.DomainTagsException;

public class DomainTagsDao extends DomainDao {
	public DomainTagsDao() {
		super();
	}

	public String insert(DomainTags ParamTag) throws DomainTagsException {

		List<ReplaceableAttribute> replaceableAttributes;
		replaceableAttributes = ParamTag.getReplaceableAttributes();

		try {
			super.insert(DomainTags_Attr.Domain, ParamTag.getID(), replaceableAttributes);
		} catch (DaoException e) {
			throw new DomainTagsException(e.getMessage());
		}
		return ParamTag.getID();
	}

	public void delete(DomainTags ParamTag) throws DomainTagsException {

		// Delete an item and all of its attributes
		try {
			super.delete(DomainTags_Attr.Domain, ParamTag.getID());
		} catch (DaoException e) {
			throw new DomainTagsException(e.getMessage());
		}

	}

	public void update(DomainTags ParamTag) throws DomainTagsException {
		List<ReplaceableAttribute> replaceableAttributes;
		replaceableAttributes = ParamTag.getReplaceableAttributes();

		try {
			super.update(DomainTags_Attr.Domain, ParamTag.getID(), replaceableAttributes);
		} catch (DaoException e) {
			throw new DomainTagsException(e.getMessage());
		}
	}

	public void batchUpdate(DomainTags ParamTag[]) throws DomainTagsException {

		List<ReplaceableItem> replaceableItems = new ArrayList<ReplaceableItem>();

		try {

			for (DomainTags item : ParamTag) {
				replaceableItems.add(item.getReplaceableItem());
			}
			super.batchUpdate(DomainTags_Attr.Domain, replaceableItems);

		} catch (DaoException e) {
			throw new DomainTagsException(e.getMessage());
		}

	}

	public DomainTags findWhereIDEquals(String paramString) throws DomainTagsException {

		DomainTags rtn = null;

		// Select data from a domain
		String selectExpression = "select * from `" + DomainTags_Attr.Domain + "`" + "where itemName() = '" + paramString + "'";

		// get the first/only item
		try {
			SelectResult result = super.select(selectExpression, "", true);

			if (!result.getItems().isEmpty()) {
				Item item = result.getItems().get(0);
				rtn = new DomainTags.Builder(item).build();
			}

		} catch (Exception e) {
			throw new DomainTagsException(e.getMessage());
		}

		return rtn;

	}
	
	public List<DomainTags> findTopRanked() throws DomainTagsException {

		List<DomainTags> rtn = new ArrayList<DomainTags>();

		// Select data from a domain
		String selectExpression = " select * from `" + DomainTags_Attr.Domain + "`" +
								  " where " + DomainTags_Attr.Count + " > '0'" + 
								  " order by " + DomainTags_Attr.Count + " desc" +
								  " limit 10";
		
		
		// Select data from a domain
		try {
			SelectResult result = super.select(selectExpression, "", true);

			if (!result.getItems().isEmpty()) {
				//build the list
				for(Item item:  result.getItems())
					rtn.add(new DomainTags.Builder(item).build());
			}

		} catch (Exception e) {
			throw new DomainTagsException(e.getMessage());
		}

		return rtn;

	}
}
