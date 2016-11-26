package dal.dto;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;

import dal.dto.base.BuilderBase;
import dal.dto.base.DomainBase;

public class DomainUsers extends DomainBase {

	private String m_UserID;
	private String m_Views;
	private String m_Viewing;
	private String m_CurrentImage;
	private String m_CurrentImageOwner;
	private String m_LastUpdated;
	private String m_Favorites;
	private String m_PublicName;
	private String m_Filter;
	
	private boolean m_UpdateUserID;
	private boolean m_UpdateViews;
	private boolean m_UpdateViewing;
	private boolean m_UpdateCurrentImage;
	private boolean m_UpdateCurrentImageOwner;
	private boolean m_UpdateLastUpdated;
	private boolean m_UpdateFavorites;
	private boolean m_UpdatePublicName;
	private boolean m_UpdateFilter;

	
	//Builder Constructor
	public DomainUsers(Builder build) {
		super(build);
		
		m_UserID = build.userid;
		m_Views = build.views;
		m_Viewing = build.viewing;
		m_CurrentImage = build.currentimageid;
		m_LastUpdated = build.timestamp;
		m_Favorites = build.favorites;
		m_PublicName = build.publicname;
		m_Filter = build.filter;
		m_CurrentImageOwner = build.currentimageowner;
		
		m_UpdateUserID = build.newRecord;
		m_UpdateViews = build.newRecord;
		m_UpdateViewing = build.newRecord;
		m_UpdateCurrentImage = build.newRecord;
		m_UpdateLastUpdated = build.newRecord;
		m_UpdateFavorites = build.newRecord;
		m_UpdatePublicName = build.newRecord;
		m_UpdateFilter = build.newRecord;
		m_UpdateCurrentImageOwner = build.newRecord;

		
	}
	
	//Make copy of this object but with a new GUID
	@Override
	public DomainUsers copy() {
		return new DomainUsers.Builder()
								.withUserID(getUserID())
								.withViews(getViews())
								.withViewing(getViewing())
								.withCurrentImage(getCurrentImage(), getCurrentImageOwner())
								.withDate(getLastUpdated())
								.withFavorites(getFavorites())
								.withPublicName(getPublicName())
								.withFilter(getFilter())
								.build();
	}
	
	/***** Getters and Setters *****/
	public String getUserID() {
		return m_UserID;
	}

	public DomainUsers setUserID(String userID) {
		m_UpdateUserID = true;
		m_UserID = userID;
		return this;
	}

	public int getViews() {
		return Integer.parseInt(this.m_Views);
	}

	public DomainUsers setViews(int value) {
		m_UpdateViews = true;
		m_Views = Integer.toString(value);
		return this;
	}

	public int getViewing() {
		return Integer.parseInt(this.m_Viewing);
	}

	public DomainUsers setViewing(int value) {
		m_UpdateViewing = true;
		m_Viewing = Integer.toString(value);
		return this;
	}


	public String getCurrentImage() {
		return m_CurrentImage;
	}

	public DomainUsers setCurrentImage(String currentImage, String currentImageOwner) {
		m_UpdateCurrentImage = true;
		m_UpdateCurrentImageOwner = true;
		m_CurrentImage = currentImage;
		m_CurrentImageOwner = currentImageOwner;
		return this;
	}
	
	public String getCurrentImageOwner() {
		return m_CurrentImage;
	}

	public DomainUsers setCurrentImageOwner(String currentImageOwner) {
		m_UpdateCurrentImageOwner = true;
		m_CurrentImageOwner = currentImageOwner;
		return this;
	}

	public DateTime getLastUpdated() {
		
		Long l = Long.parseLong(m_LastUpdated);
		return new DateTime(l);
	}

	public DomainUsers setLastUpdated(DateTime value) {
		m_UpdateLastUpdated = true;
		m_LastUpdated = Long.toString(value.getMillis());
		return this;
	}
	
	public int getFavorites() {
		return Integer.parseInt(this.m_Favorites);
	}

	public DomainUsers setFavorites(int value) {
		m_UpdateFavorites = true;
		m_Favorites = Integer.toString(value);
		return this;
	}

	public String getPublicName() {
		return m_PublicName;
	}

	public DomainUsers setPublicName(String value) {
		m_UpdatePublicName = true;
		m_PublicName = value;
		return this;
	}

	public String getFilter() {
		return m_Filter;
	}

	public DomainUsers setFilter(String value) {
		m_UpdateFilter = true;
		m_Filter = value;
		return this;
	}
	
	//Build ReplaceableAttributes list used by SimpleDB to create or update existing records
	@Override
	public List<ReplaceableAttribute> getReplaceableAttributes() {
		
		List<ReplaceableAttribute> attrList = new ArrayList<ReplaceableAttribute>();
		
		if(m_UpdateUserID)
			attrList.add(new ReplaceableAttribute(DomainUsers_Attr.UserID, m_UserID, true));
		if(m_UpdateViews)
			attrList.add(new ReplaceableAttribute(DomainUsers_Attr.Views, m_Views, true));
		if(m_UpdateViewing)
			attrList.add(new ReplaceableAttribute(DomainUsers_Attr.Viewing, m_Viewing, true));
		if(m_UpdateCurrentImage)
			attrList.add(new ReplaceableAttribute(DomainUsers_Attr.CurrentImage, m_CurrentImage, true));
		if(m_UpdateCurrentImageOwner)
			attrList.add(new ReplaceableAttribute(DomainUsers_Attr.CurrentImageOwner, m_CurrentImageOwner, true));
		if(m_UpdateLastUpdated)
			attrList.add(new ReplaceableAttribute(DomainUsers_Attr.LastUpdated, m_LastUpdated, true));
		if(m_UpdateFavorites)
			attrList.add(new ReplaceableAttribute(DomainUsers_Attr.Favorites, m_Favorites, true));
		if(m_UpdatePublicName)
			attrList.add(new ReplaceableAttribute(DomainUsers_Attr.PublicName, m_PublicName, true));
		if(m_UpdateFilter)
			attrList.add(new ReplaceableAttribute(DomainUsers_Attr.Filter, m_Filter, true));

	
		return attrList;
	}
	
	//Build Attributes list used by SimpleDB to create new records
	@Override
	public List<Attribute> getAttributes() {
		List<Attribute> attrList = new ArrayList<Attribute>();
		
		if(m_UpdateUserID)
			attrList.add(new Attribute(DomainUsers_Attr.UserID, m_UserID));
		if(m_UpdateViews)
			attrList.add(new Attribute(DomainUsers_Attr.Views, m_Views));
		if(m_UpdateViewing)
			attrList.add(new Attribute(DomainUsers_Attr.Viewing, m_Viewing));
		if(m_UpdateCurrentImage)
			attrList.add(new Attribute(DomainUsers_Attr.CurrentImage, m_CurrentImage));
		if(m_UpdateCurrentImageOwner)
			attrList.add(new Attribute(DomainUsers_Attr.CurrentImageOwner, m_CurrentImageOwner));
		if(m_UpdateLastUpdated)
			attrList.add(new Attribute(DomainUsers_Attr.LastUpdated, m_LastUpdated));
		if(m_UpdateFavorites)
			attrList.add(new Attribute(DomainUsers_Attr.Favorites, m_Favorites));
		if(m_UpdatePublicName)
			attrList.add(new Attribute(DomainUsers_Attr.PublicName, m_PublicName));
		if(m_UpdateFilter)
			attrList.add(new Attribute(DomainUsers_Attr.Filter, m_Filter));

	
		return attrList;
	}

	// Builder Pattern
	public static final class Builder extends BuilderBase {

		private String userid = ""; 
		private String views = "0";
		private String viewing = "0";
		private String currentimageid = "";
		private String currentimageowner = "";
		private String timestamp = getDate();
		private String favorites = "0";
		private String publicname = "";
		private String filter = "";
		private boolean newRecord = true;

		public Builder(Item item) {
			super(item);
			newRecord = false;
			for (Attribute attribute : item.getAttributes()) {

				if (attribute.getName().equalsIgnoreCase(DomainUsers_Attr.UserID))
					userid = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainUsers_Attr.Views))
					views = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainUsers_Attr.Viewing))
					viewing = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainUsers_Attr.CurrentImage))
					currentimageid = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainUsers_Attr.LastUpdated))
					timestamp = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainUsers_Attr.Favorites))
					favorites = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainUsers_Attr.PublicName))
					publicname = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainUsers_Attr.Filter))
					filter = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainUsers_Attr.CurrentImageOwner))
					currentimageowner = attribute.getValue();

			}

		}

		public Builder() {
			super();
		}

		public Builder(String id) {
			super(id);
		}

		public Builder withUserID(String value)	{
			userid = value;
			return this;
		}
		
		public Builder withCurrentImage(String imageid, String userID){
			currentimageid = imageid;
			currentimageowner = userID;
			return this;
		}
		
		public Builder withViews(int value) {
			views = Integer.toString(value);
			return this;
		}

		public Builder withFavorites(int value) {
			favorites = Integer.toString(value);
			return this;
		}

		public Builder withPublicName(String value) {
			publicname = value;
			return this;
		}

		public Builder withDate(DateTime value) {
			timestamp = Long.toString(value.getMillis());
			return this;
		}

		public Builder withViewing(int value) {
			viewing = Integer.toString(value);
			return this;
		}

		public Builder withFilter(String value) {
			filter = value;
			return this;
		}

		
		@Override
		public DomainUsers build() {
			return new DomainUsers(this);
		}
	}

	
}

