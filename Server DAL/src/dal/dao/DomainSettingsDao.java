package dal.dao;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.ReplaceableItem;
import com.amazonaws.services.simpledb.model.SelectResult;

import dal.dto.DomainSettings;
import dal.dto.DomainSettings_Attr;
import dal.exceptions.DaoException;
import dal.exceptions.DomainSettingsException;

public class DomainSettingsDao extends DomainDao {
	public DomainSettingsDao() {
		super();
	}

	public String insert(DomainSettings ParamTag) throws DomainSettingsException {

		List<ReplaceableAttribute> replaceableAttributes;
		replaceableAttributes = ParamTag.getReplaceableAttributes();

		try {
			super.insert(DomainSettings_Attr.Domain, ParamTag.getID(), replaceableAttributes);
		} catch (DaoException e) {
			throw new DomainSettingsException(e.getMessage());
		}
		return ParamTag.getID();
	}

	public void delete(DomainSettings ParamTag) throws DomainSettingsException {

		// Delete an item and all of its attributes
		try {
			super.delete(DomainSettings_Attr.Domain, ParamTag.getID());
		} catch (DaoException e) {
			throw new DomainSettingsException(e.getMessage());
		}

	}

	public void update(DomainSettings ParamTag) throws DomainSettingsException {
		List<ReplaceableAttribute> replaceableAttributes;
		replaceableAttributes = ParamTag.getReplaceableAttributes();

		try {
			super.update(DomainSettings_Attr.Domain, ParamTag.getID(), replaceableAttributes);
		} catch (DaoException e) {
			throw new DomainSettingsException(e.getMessage());
		}
	}

	public void batchUpdate(DomainSettings ParamTag[]) throws DomainSettingsException {

		List<ReplaceableItem> replaceableItems = new ArrayList<ReplaceableItem>();

		try {

			for (DomainSettings item : ParamTag) {
				replaceableItems.add(item.getReplaceableItem());
			}
			super.batchUpdate(DomainSettings_Attr.Domain, replaceableItems);

		} catch (DaoException e) {
			throw new DomainSettingsException(e.getMessage());
		}

	}

	public DomainSettings findWhereIDEquals(String paramString) throws DomainSettingsException {

		DomainSettings rtn = null;

		// Select data from a domain
		String selectExpression = "select * from `" + DomainSettings_Attr.Domain + "`" + " where itemName() = '" + paramString + "'";

		// get the first/only item
		try {
			SelectResult result = super.select(selectExpression, "", false);

			if (!result.getItems().isEmpty()) {
				Item item = result.getItems().get(0);
				rtn = new DomainSettings.Builder(item).build();
			}

		} catch (Exception e) {
			throw new DomainSettingsException(e.getMessage());
		}

		return rtn;

	}
	
	public DomainSettings findMaster() throws DomainSettingsException {

		DomainSettings rtn = null;

		// Select data from a domain
		String selectExpression = "select * from `" + DomainSettings_Attr.Domain + "`" + " where itemName() = '" + DomainSettings_Attr.Master + "'";

		// get the first/only item
		try {
			SelectResult result = super.select(selectExpression, "", false);

			if (!result.getItems().isEmpty()) {
				Item item = result.getItems().get(0);
				rtn = new DomainSettings.Builder(item).build();
			}

		} catch (Exception e) {
			throw new DomainSettingsException(e.getMessage());
		}

		return rtn;

	}
	
}
