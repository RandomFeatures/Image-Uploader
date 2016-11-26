package dal.dto;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;

import dal.dto.base.BuilderBase;
import dal.dto.base.DomainBase;

public class DomainImageSubmit extends DomainBase {

	private String m_Date;
	private String m_Source;
	private String m_SourceURL;
	private String m_SourceID;
	private String m_UserID;
	private String m_Suffix;

	private boolean m_UpdateDate;
	private boolean m_UpdateSource;
	private boolean m_UpdateSourceURL;
	private boolean m_UpdateSourceID;
	private boolean m_UpdateUserID;
	private boolean m_UpdateSuffix;

	//Builder Constructor
	public DomainImageSubmit(Builder build) {
		super(build);
		m_Date  = build.timestamp; 
		m_Source  = build.source;
		m_SourceURL = build.sourceURL;
		m_SourceID = build.sourceID;
		m_UserID = build.userID;
		m_Suffix = build.suffix;
		
		m_UpdateDate  = build.newRecord; 
		m_UpdateSource  = build.newRecord;
		m_UpdateSourceURL = build.newRecord;
		m_UpdateSourceID = build.newRecord;
		m_UpdateUserID = build.newRecord;
		m_UpdateSuffix = build.newRecord;
		
	}

	//Make copy of this object but with a new GUID
	@Override
	public DomainImageSubmit copy() {
		return new DomainImageSubmit.Builder()
									.withDate(getDate())
									.withSource(getSource())
									.withSourceURL(getSourceURL())
									.withSourceID(getSourceID())
									.withUserID(getUserID())
									.withSuffix(getSuffix())
									.build();
	}

	/***** Getters and Setters *****/
	public DateTime getDate() {
		
		Long l = Long.parseLong(m_Date);
		return new DateTime(l);
	}

	public DomainImageSubmit setDate(DateTime  value) {
		m_UpdateDate = true;
		m_Date = Long.toString(value.getMillis());
		return this;
	}

	public String getSourceURL() {
		return m_SourceURL;
	}

	public DomainImageSubmit setSourceURL(String value) {
		m_UpdateSourceURL = true;
		m_SourceURL = value;
		return this;
	}

	public String getSourceID() { 
		return m_SourceID;
	}

	public DomainImageSubmit setSourceID(String value) {
		m_UpdateSourceID = true;
		m_SourceID = value;
		return this;
	}
	
	public String getSource() { 
		return m_Source;
	}

	public DomainImageSubmit setSource(String value) {
		m_UpdateSource = true;
		m_Source = value;
		return this;
	}

	public String getUserID() { 
		return m_UserID;
	}

	public DomainImageSubmit setUserID(String value) {
		m_UpdateUserID = true;
		m_UserID = value;
		return this;
	}
	
	public String getSuffix() {
		return m_Suffix;
	}

	public DomainImageSubmit setSuffix(String value) {
		m_UpdateSuffix = true;
		m_Suffix = value;
		return this;
	}
	
	//Build ReplaceableAttributes list used by SimpleDB to create or update existing records
	@Override
	public List<ReplaceableAttribute> getReplaceableAttributes() {
		
		List<ReplaceableAttribute> attrList = new ArrayList<ReplaceableAttribute>();
		if(m_UpdateDate)
			attrList.add(new ReplaceableAttribute(DomainImageSubmit_Attr.Date, m_Date, true));
		if(m_UpdateSource)
			attrList.add(new ReplaceableAttribute(DomainImageSubmit_Attr.Source, m_Source, true));
		if(m_UpdateSourceURL)
			attrList.add(new ReplaceableAttribute(DomainImageSubmit_Attr.SourceURL, m_SourceURL, true));
		if(m_UpdateSourceID)
			attrList.add(new ReplaceableAttribute(DomainImageSubmit_Attr.SourceID, m_SourceID, true));
		if(m_UpdateUserID)
			attrList.add(new ReplaceableAttribute(DomainImageSubmit_Attr.UserID, m_UserID, true));
		if(m_UpdateSuffix)
			attrList.add(new ReplaceableAttribute(DomainImageSubmit_Attr.Suffix, m_Suffix, true));
		
		return attrList;
	}
	//Build Attributes list used by SimpleDB to create new records
	@Override
	public List<Attribute> getAttributes() {
		List<Attribute> attrList = new ArrayList<Attribute>();
		
		if(m_UpdateDate)
			attrList.add(new Attribute(DomainImageSubmit_Attr.Date, m_Date));
		if(m_UpdateSource)
			attrList.add(new Attribute(DomainImageSubmit_Attr.Source, m_Source));
		if(m_UpdateSourceURL)
			attrList.add(new Attribute(DomainImageSubmit_Attr.SourceURL, m_SourceURL));
		if(m_UpdateSourceID)
			attrList.add(new Attribute(DomainImageSubmit_Attr.SourceID, m_SourceID));
		if(m_UpdateUserID)
			attrList.add(new Attribute(DomainImageSubmit_Attr.UserID, m_UserID));
		if(m_UpdateSuffix)
			attrList.add(new Attribute(DomainImageSubmit_Attr.Suffix, m_Suffix));
		
		return attrList;
	}

	// Builder Pattern
	public static final class Builder extends BuilderBase {

		private String timestamp = getDate();
		private String source = "";
		private String sourceURL = "";
		private String sourceID = "";
		private String userID = "";
		private String suffix = "0";
		private boolean newRecord = true;
		
		public Builder(Item item) {
			super(item);
			newRecord = false;
			for (Attribute attribute : item.getAttributes()) {
				if (attribute.getName().equalsIgnoreCase(DomainImageSubmit_Attr.Date))
					timestamp = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainImageSubmit_Attr.Source))
					source = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainImageSubmit_Attr.SourceURL))
					sourceURL = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainImageSubmit_Attr.SourceID))
					sourceID = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainImageSubmit_Attr.UserID))
					userID = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainImageSubmit_Attr.Suffix))
					suffix = attribute.getValue();
			}

		}

		public Builder() {
			super();
		}

		public Builder(String id) {
			super(id);
		}

		public Builder withDate(DateTime value) {
			timestamp = Long.toString(value.getMillis());
			return this;
		}
		
		public Builder withSource(String value) {
			source = value;
			return this;
		}

		public Builder withSourceURL(String value) {
			sourceURL = value;
			return this;
		}

		public Builder withSourceID(String value) {
			sourceID = value;
			return this;
		}

		public Builder withUserID(String value) {
			userID = value;
			return this;
		}

		public Builder withSuffix(String value){
			suffix = value;
			return this;
		}
		
		@Override
		public DomainImageSubmit build() {
			return new DomainImageSubmit(this);
		}
	}
	
}
