package dal.dto;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;

import dal.dto.base.BuilderBase;
import dal.dto.base.DomainBase;

public class DomainViews extends DomainBase {

	private String m_UserID;
	private String m_ImageID;
	private String m_Date;
	private String m_Suffix;

	private boolean m_UpdateUserID;
	private boolean m_UpdateImageID;
	private boolean m_UpdateDate;
	private boolean m_UpdateSuffix;

	//Builder Constructor
	public DomainViews(Builder build) {
		super(build);
		m_UserID = build.userid;
		m_ImageID = build.imageid;
		m_Date = build.timestamp;
		m_Suffix = build.suffix;
		
		m_UpdateUserID = build.newRecord;
		m_UpdateImageID = build.newRecord;
		m_UpdateDate = build.newRecord;
		m_UpdateSuffix = build.newRecord;

	}
	
	//Make copy of this object but with a new GUID
	@Override
	public DomainViews copy() {
		return new DomainViews.Builder()
								.withUserID(getUserID())
								.withImageID(getImageID())
								.withDate(getDate())
								.withSuffix(getSuffix())
								.build();
	}

	/***** Getters and Setters *****/
	
	public String getUserID() {
		return m_UserID;
	}

	public DomainViews setUserID(String value) {
		m_UpdateUserID = true;
		m_UserID = value;
		return this;
	}

	public String getImageID() {
		return m_ImageID;
	}

	public DomainViews setImageId(String value) {
		m_UpdateImageID = true;
		m_ImageID = value;
		return this;
	}
	public String getSuffix() {
		return m_Suffix;
	}

	public DomainViews setSuffix(String value) {
		m_UpdateSuffix = true;
		m_Suffix = value;
		return this;
	}
	
	public DateTime getDate() {
		
		Long l = Long.parseLong(m_Date);
		return new DateTime(l);
	}

	public DomainViews setDate(DateTime  value) {
		m_UpdateDate = true;
		m_Date = Long.toString(value.getMillis());
		return this;
	}

	
	//Build ReplaceableAttributes list used by SimpleDB to create or update existing records
	@Override
	public List<ReplaceableAttribute> getReplaceableAttributes() {
		
		List<ReplaceableAttribute> attrList = new ArrayList<ReplaceableAttribute>();
		
		if(m_UpdateUserID) 
			attrList.add(new ReplaceableAttribute(DomainViews_Attr.UserID, m_UserID, true));
		if(m_UpdateImageID)
			attrList.add(new ReplaceableAttribute(DomainViews_Attr.ImageID, m_ImageID, true));
		if(m_UpdateDate)
			attrList.add(new ReplaceableAttribute(DomainViews_Attr.Date, m_Date, true));
		if(m_UpdateSuffix)
			attrList.add(new ReplaceableAttribute(DomainViews_Attr.Suffix, m_Suffix, true));

		return attrList;
	}
	//Build Attributes list used by SimpleDB to create new records
	@Override
	public List<Attribute> getAttributes() {
		List<Attribute> attrList = new ArrayList<Attribute>();
		
		if(m_UpdateUserID)
			attrList.add(new Attribute(DomainViews_Attr.UserID, m_UserID));
		if(m_UpdateImageID)
			attrList.add(new Attribute(DomainViews_Attr.ImageID, m_ImageID));
		if(m_UpdateDate)
			attrList.add(new Attribute(DomainViews_Attr.Date, m_Date));
		if(m_UpdateSuffix)
			attrList.add(new Attribute(DomainViews_Attr.Suffix, m_Suffix));

		return attrList;
	}

	
	// Builder Pattern
		public static final class Builder extends BuilderBase {

			private String imageid = "";
			private String userid = ""; 
			private String timestamp = getDate();
			private String suffix = "0";
			private boolean newRecord = true;
			
			public Builder(Item item) {
				super(item);
				newRecord = false;
				for (Attribute attribute : item.getAttributes()) {
					if (attribute.getName().equalsIgnoreCase(DomainViews_Attr.Date))
						timestamp = attribute.getValue();
					else if (attribute.getName().equalsIgnoreCase(DomainViews_Attr.ImageID))
						imageid = attribute.getValue();
					else if (attribute.getName().equalsIgnoreCase(DomainViews_Attr.UserID))
						userid = attribute.getValue();
					else if (attribute.getName().equalsIgnoreCase(DomainViews_Attr.Suffix))
						suffix = attribute.getValue();
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
			
			public Builder withImageID(String value){
				imageid = value;
				return this;
			}
			
			public Builder withSuffix(String value){
				suffix = value;
				return this;
			}
			
			
			public Builder withDate(DateTime value) {
				timestamp = Long.toString(value.getMillis());
				return this;
			}
		

			@Override
			public DomainViews build() {
				return new DomainViews(this);
			}
		}


}
