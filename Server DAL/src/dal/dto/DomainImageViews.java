package dal.dto;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;

import dal.dto.base.BuilderBase;
import dal.dto.base.DomainBase;

public class DomainImageViews extends DomainBase {

	private String m_Views;
	private String m_Viewing;
	private String m_UserID;

	private boolean m_UpdateViews;
	private boolean m_UpdateViewing;
	private boolean m_UpdateUserID;
	
	//Builder Constructor
	public DomainImageViews(Builder build) {
		super(build);

		m_Views = build.views;
		m_Viewing = build.viewing;
		m_UserID = build.userID;

		m_UpdateViews = build.newRecord;
		m_UpdateViewing = build.newRecord;
		m_UpdateUserID = build.newRecord;

	}
	//Make copy of this object but with a new GUID
	@Override
	public DomainImageViews copy() {
		return new DomainImageViews.Builder()
								.withViews(getViews())
								.withViewing(getViewing())
								.withUserID(getUserID())
								.build();
	}

	/***** Getters and Setters *****/
	public int getViews() {
		return Integer.parseInt(this.m_Views);
	}

	public DomainImageViews setViews(int value) {
		m_UpdateViews = true;
		m_Views = Integer.toString(value);
		return this;
	}

	public int getViewing() {
		return Integer.parseInt(this.m_Viewing);
	}

	public DomainImageViews setViewing(int value) {
		m_UpdateViewing = true;
		m_Viewing = Integer.toString(value);
		return this;
	}


	public String getUserID() { 
		return m_UserID;
	}

	public DomainImageViews setUserID(String value) {
		m_UpdateUserID = true;
		m_UserID = value;
		return this;
	}
	
	
	//Build ReplaceableAttributes list used by SimpleDB to create or update existing records
	@Override
	public List<ReplaceableAttribute> getReplaceableAttributes() {
		
		List<ReplaceableAttribute> attrList = new ArrayList<ReplaceableAttribute>();
		if(m_UpdateViews)
			attrList.add(new ReplaceableAttribute(DomainImages_Attr.Views, m_Views, true));
		if(m_UpdateViewing)
			attrList.add(new ReplaceableAttribute(DomainImages_Attr.Viewing, m_Viewing, true));
		if(m_UpdateUserID)
			attrList.add(new ReplaceableAttribute(DomainImages_Attr.UserID, m_UserID, true));

		
		return attrList;
	}
	//Build Attributes list used by SimpleDB to create new records
	@Override
	public List<Attribute> getAttributes() {

		List<Attribute> attrList = new ArrayList<Attribute>();
		
		if(m_UpdateViews)
			attrList.add(new Attribute(DomainImages_Attr.Views, m_Views));
		if(m_UpdateViewing)
			attrList.add(new Attribute(DomainImages_Attr.Viewing, m_Viewing));
		if(m_UpdateUserID)
			attrList.add(new Attribute(DomainImages_Attr.UserID, m_UserID));

		return attrList;
	}

	
	// Builder Pattern
	public static final class Builder extends BuilderBase {
		
		private String views = "0";
		private String viewing = "0";
		private String userID = "";
		private boolean newRecord = true;

		public Builder(Item item) {
			super(item);
			newRecord = false;
			for (Attribute attribute : item.getAttributes()) {
				if (attribute.getName().equalsIgnoreCase(DomainImages_Attr.Views))
					views = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainImages_Attr.Viewing))
					viewing = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainImages_Attr.UserID))
					userID = attribute.getValue();
				
			}

		}

		public Builder() {
			super();
		}

		public Builder(String id) {
			super(id);
		}

		public Builder withViews(int value) {
			views = Integer.toString(value);
			return this;
		}

		public Builder withViewing(int value) {
			viewing = Integer.toString(value);
			return this;
		}
		
		public Builder withUserID(String value) {
			userID = value;
			return this;
		}
		
		@Override
		public DomainImageViews build() {
			return new DomainImageViews(this);
		}
	}
}
