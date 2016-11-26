package dal.dao;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.ReplaceableItem;
import com.amazonaws.services.simpledb.model.SelectResult;

import dal.dto.DomainImageScores;
import dal.dto.DomainImageViews;
import dal.dto.DomainImages;
import dal.dto.DomainImages_Attr;
import dal.exceptions.DaoException;
import dal.exceptions.DomainImagesException;

public class DomainImagesDao extends DomainDao {

	public DomainImagesDao() {
		super();
	}

	public String insert(DomainImages paramImages) throws DomainImagesException {

		List<ReplaceableAttribute> replaceableAttributes;
		replaceableAttributes = paramImages.getReplaceableAttributes();

		try {
			super.insert(DomainImages_Attr.Domain, paramImages.getID(), replaceableAttributes);
		} catch (DaoException e) {
			throw new DomainImagesException(e.getMessage());
		}
		return paramImages.getID();
	}

	public void delete(DomainImages paramImages) throws DomainImagesException {

		// Delete an item and all of its attributes
		try {
			super.delete(DomainImages_Attr.Domain, paramImages.getID());
		} catch (DaoException e) {
			throw new DomainImagesException(e.getMessage());
		}

	}

	public void update(DomainImages paramImages) throws DomainImagesException {
		List<ReplaceableAttribute> replaceableAttributes;
		replaceableAttributes = paramImages.getReplaceableAttributes();

		try {
			super.update(DomainImages_Attr.Domain, paramImages.getID(), replaceableAttributes);
		} catch (DaoException e) {
			throw new DomainImagesException(e.getMessage());
		}
	}

	public void update(DomainImageViews paramImages) throws DomainImagesException {
		List<ReplaceableAttribute> replaceableAttributes;
		replaceableAttributes = paramImages.getReplaceableAttributes();

		try {
			super.update(DomainImages_Attr.Domain, paramImages.getID(), replaceableAttributes);
		} catch (DaoException e) {
			throw new DomainImagesException(e.getMessage());
		}
	}

	public void update(DomainImageScores paramImages) throws DomainImagesException {
		List<ReplaceableAttribute> replaceableAttributes;
		replaceableAttributes = paramImages.getReplaceableAttributes();

		try {
			super.update(DomainImages_Attr.Domain, paramImages.getID(), replaceableAttributes);
		} catch (DaoException e) {
			throw new DomainImagesException(e.getMessage());
		}
	}
	
	public void batchUpdate(List<DomainImages> paramImages) throws DomainImagesException {

		List<ReplaceableItem> replaceableItems = new ArrayList<ReplaceableItem>();
		int iCount = 0;
		
		try {

			for (DomainImages item : paramImages) {
				iCount++;
				replaceableItems.add(item.getReplaceableItem());
				if(iCount==25)
				{//max of 25 per request
					super.batchUpdate(DomainImages_Attr.Domain, replaceableItems);
					replaceableItems.clear();
					iCount = 0;
				}
				
			}
			if(replaceableItems.size() > 0)
				super.batchUpdate(DomainImages_Attr.Domain, replaceableItems);

		} catch (DaoException e) {
			throw new DomainImagesException(e.getMessage());
		}

	}
	
	public void batchUpdateScores(List<DomainImageScores> paramImages) throws DomainImagesException {

		List<ReplaceableItem> replaceableItems = new ArrayList<ReplaceableItem>();
		int iCount = 0;
		
		try {

			for (DomainImageScores item : paramImages) {
				iCount++;
				replaceableItems.add(item.getReplaceableItem());
				if(iCount==25)
				{//max of 25 per request
					super.batchUpdate(DomainImages_Attr.Domain, replaceableItems);
					replaceableItems.clear();
					iCount = 0;
				}
				
			}
			if(replaceableItems.size() > 0)
				super.batchUpdate(DomainImages_Attr.Domain, replaceableItems);

		} catch (DaoException e) {
			throw new DomainImagesException(e.getMessage());
		}

	}
	
	public DomainImages findWhereIDEquals(String paramString) throws DomainImagesException {

		DomainImages rtn = null;

		// Select data from a domain
		String selectExpression = "select * from `" + DomainImages_Attr.Domain + "`" + "where itemName() = '" + paramString + "'";

		// get the first/only item
		try {
			SelectResult result = super.select(selectExpression, "", false);

			if (!result.getItems().isEmpty()) {
				Item item = result.getItems().get(0);
				rtn = new DomainImages.Builder(item).build();
			}

		} catch (Exception e) {
			throw new DomainImagesException(e.getMessage());
		}

		return rtn;

	}
	
	public DomainImageScores findScoreWhereIDEquals(String paramString) throws DomainImagesException {

		DomainImageScores rtn = null;

		// Select data from a domain
		String selectExpression = "select " + DomainImages_Attr.DownVotes  + "," +
											 DomainImages_Attr.UpVotes  + "," +
											 DomainImages_Attr.Score + "," +
											 DomainImages_Attr.Date +  "," +
											 DomainImages_Attr.LastUpdated +  
								  " from `" + DomainImages_Attr.Domain + "`" + 
								  " where itemName() = '" + paramString + "'";

		// get the first/only item
		try {
			SelectResult result = super.select(selectExpression, "", true);

			if (!result.getItems().isEmpty()) {
				Item item = result.getItems().get(0);
				rtn = new DomainImageScores.Builder(item).build();
			}

		} catch (Exception e) {
			throw new DomainImagesException(e.getMessage());
		}

		return rtn;

	}
	
	public List<DomainImageScores> findDecayScore(String oldest) throws DomainImagesException {

		List<DomainImageScores> rtn = new ArrayList<DomainImageScores>();

		// Select data from a domain
		String selectExpression = "select " + DomainImages_Attr.DownVotes  + "," +
											 DomainImages_Attr.UpVotes  + "," +
											 DomainImages_Attr.Score + "," +
											 DomainImages_Attr.Date +  "," +
											 DomainImages_Attr.LastUpdated +  
								  " from `" + DomainImages_Attr.Domain + "`" + 
								  " where " + DomainImages_Attr.Score + " > '0'" + 
								  " and " + DomainImages_Attr.LastUpdated  + " < '" + oldest + "'";

		
		// Select data from a domain
		try {
			SelectResult result = super.select(selectExpression, "", true);

			if (!result.getItems().isEmpty()) {
				//build the list
				for(Item item:  result.getItems())
					rtn.add(new DomainImageScores.Builder(item).build());
			}

		} catch (Exception e) {
			throw new DomainImagesException(e.getMessage());
		}
		return rtn;

	}
	

	public DomainImageViews findViewsWhereIDEquals(String paramString) throws DomainImagesException {

		DomainImageViews rtn = null;

		// Select data from a domain
		String selectExpression = "select " + DomainImages_Attr.Viewing  + "," +
											 DomainImages_Attr.Views  + "," +
											 DomainImages_Attr.UserID + 
								  " from `" + DomainImages_Attr.Domain + "`" + 
								  " where itemName() = '" + paramString + "'";

		// get the first/only item
		try {
			SelectResult result = super.select(selectExpression, "", false);

			if (!result.getItems().isEmpty()) {
				Item item = result.getItems().get(0);
				rtn = new DomainImageViews.Builder(item).build();
			}

		} catch (Exception e) {
			throw new DomainImagesException(e.getMessage());
		}

		return rtn;

	}

	

	public List<DomainImages> findTopRanked() throws DomainImagesException {

		List<DomainImages> rtn = new ArrayList<DomainImages>();

		// Select data from a domain
		String selectExpression = " select * from `" + DomainImages_Attr.Domain + "`" +
								  " where " + DomainImages_Attr.Score + " > '0'" + 
								  " order by " + DomainImages_Attr.Score + " desc " +
								  " limit 10";
		
		
		// Select data from a domain
		try {
			SelectResult result = super.select(selectExpression, "", true);

			if (!result.getItems().isEmpty()) {
				//build the list
				for(Item item:  result.getItems())
					rtn.add(new DomainImages.Builder(item).build());
			}

		} catch (Exception e) {
			throw new DomainImagesException(e.getMessage());
		}

		return rtn;

	}
	
	
	public List<DomainImages> findNewest() throws DomainImagesException {

		List<DomainImages> rtn = new ArrayList<DomainImages>();

		// Select data from a domain
		String selectExpression = " select * from `" + DomainImages_Attr.Domain + "`" +
								  " where " + DomainImages_Attr.Date + " is not null" +	
								  " order by " + DomainImages_Attr.Date  + " desc " +
								  " limit 10";
		
		
		
		try {
			//select from db
			SelectResult result = super.select(selectExpression, "", true);

			if (!result.getItems().isEmpty()) {
				//build the list
				for(Item item:  result.getItems())
					rtn.add(new DomainImages.Builder(item).build());
			}

		} catch (Exception e) {
			throw new DomainImagesException(e.getMessage());
		}

		return rtn;

	}
	
	
	public List<DomainImages> findTopRankedByTag(String tag) throws DomainImagesException {

		List<DomainImages> rtn = new ArrayList<DomainImages>();

		// Select data from a domain
		String selectExpression = " select * from `" + DomainImages_Attr.Domain + "`" +
								  " where " + DomainImages_Attr.Score + " > '0'" + 
								  " and " + DomainImages_Attr.Tags + " = '" + tag + "'" +
								  " order by " + DomainImages_Attr.Score + " desc " +
								  " limit 10";
		
		
		// Select data from a domain
		try {
			SelectResult result = super.select(selectExpression, "", true);

			if (!result.getItems().isEmpty()) {
				//build the list
				for(Item item:  result.getItems())
					rtn.add(new DomainImages.Builder(item).build());
			}

		} catch (Exception e) {
			throw new DomainImagesException(e.getMessage());
		}

		return rtn;

	}
	
	public DomainImages findImageBySource(String source, String sourceid) throws DomainImagesException {

		DomainImages rtn = null;

		// Select data from a domain
		String selectExpression = " select * from `" + DomainImages_Attr.Domain + "`" +
								  " where " + DomainImages_Attr.Source + " = '" + source + "'" + 
								  " and " + DomainImages_Attr.SourceID + " = '" + sourceid + "'" +
								  " limit 1";
		
		
		// Select data from a domain
		try {
			SelectResult result = super.select(selectExpression, "", true);

			if (!result.getItems().isEmpty()) {
				//build the list
				Item item = result.getItems().get(0);
				rtn = new DomainImages.Builder(item).build();
			}

		} catch (Exception e) {
			throw new DomainImagesException(e.getMessage());
		}

		return rtn;

	}
	
	
	
	public List<DomainImages> findNewestByTag(String tag) throws DomainImagesException {

		List<DomainImages> rtn = new ArrayList<DomainImages>();

		// Select data from a domain
		String selectExpression = " select * from `" + DomainImages_Attr.Domain + "`" +
								  " where " + DomainImages_Attr.Date + " is not null" +	
								  " and " + DomainImages_Attr.Tags + " = '" + tag + "'" +
								  " order by " + DomainImages_Attr.Date  + " desc" +
								  " limit 10";
		
		
		
		try {
			//select from db
			SelectResult result = super.select(selectExpression, "", true);

			if (!result.getItems().isEmpty()) {
				//build the list
				for(Item item:  result.getItems())
					rtn.add(new DomainImages.Builder(item).build());
			}

		} catch (Exception e) {
			throw new DomainImagesException(e.getMessage());
		}

		return rtn;

	}
	
	public List<DomainImages> findAllByUser(String userID) throws DomainImagesException {

		List<DomainImages> rtn = new ArrayList<DomainImages>();

		// Select data from a domain
		String selectExpression = " select * from `" + DomainImages_Attr.Domain + "`" +
								  " where " + DomainImages_Attr.UserID + " = '" + userID + "'" +
								  " and " + DomainImages_Attr.Date + " is not null" +	
								  " order by " + DomainImages_Attr.Date  + " desc";
		
		
		
		try {
			//select from db
			SelectResult result = super.select(selectExpression, "", true);

			if (!result.getItems().isEmpty()) {
				//build the list
				for(Item item:  result.getItems())
					rtn.add(new DomainImages.Builder(item).build());
			}

		} catch (Exception e) {
			throw new DomainImagesException(e.getMessage());
		}

		return rtn;

	}

}
