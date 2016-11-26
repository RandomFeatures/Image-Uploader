package dal.dto;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;

import dal.dto.base.BuilderBase;
import dal.dto.base.DomainBase;

public class DomainVotePurchase extends DomainBase {

	private String m_UserID;
	private String m_ImageID;
	private String m_Votes;
	private String m_Date;
	private String m_Complete;

	private boolean m_UpdateUserID;
	private boolean m_UpdateImageID;
	private boolean m_UpdateVotes;
	private boolean m_UpdateDate;
	private boolean m_UpdateComplete;

	
	//Builder Constructor
	public DomainVotePurchase(Builder build) {
		super(build);
		
		m_UserID = build.userID;
		m_ImageID = build.imageID;
		m_Votes = build.votes;
		m_Date = build.timestamp;
		m_Complete = build.complete;
		
		m_UpdateUserID = build.newRecord;
		m_UpdateImageID = build.newRecord;
		m_UpdateVotes = build.newRecord;
		m_UpdateDate = build.newRecord;
		m_UpdateComplete = build.newRecord;

	}
	
	//Make copy of this object but with a new GUID
	@Override
	public DomainVotePurchase copy() {
		return new DomainVotePurchase.Builder()
									.withUserID(getUserID())
									.withImageID(getImageID())
									.withVotes(getVotes())
									.withCompleteStatus(getComplete())
									.build();
	}
	
	/***** Getters and Setters *****/
	
	public String getUserID() {
		return m_UserID;
	}

	public DomainVotePurchase setUserID(String userID) {
		m_UpdateUserID = true;
		m_UserID = userID;
		return this;
	}

	public String getImageID() {
		return m_ImageID;
	}

	public DomainVotePurchase setImageID(String imageID) {
		m_UpdateImageID = true;
		m_ImageID = imageID;
		return this;
	}

	public int getVotes() {
		return Integer.parseInt(this.m_Votes);
	}

	public DomainVotePurchase setVotes(int value) {
		m_UpdateVotes = true;
		m_Votes = Integer.toString(value);
		return this;
	}

	
	public boolean getComplete() {
		return Boolean.parseBoolean(this.m_Complete);
	}

	public DomainVotePurchase setComplete(boolean value) {
		m_UpdateComplete = true;
		m_Complete = Boolean.toString(value);
		return this;
	}
	
	public DateTime getDate() {
		
		Long l = Long.parseLong(m_Date);
		return new DateTime(l);
	}

	public DomainVotePurchase setDate(DateTime  value) {
		m_UpdateDate = true;
		m_Date = Long.toString(value.getMillis());
		return this;
	}

	
	//Build ReplaceableAttributes list used by SimpleDB to create or update existing records
	@Override
	public List<ReplaceableAttribute> getReplaceableAttributes() {
		
		List<ReplaceableAttribute> replaceableAttributes = new ArrayList<ReplaceableAttribute>();
		
		if(m_UpdateUserID)
			replaceableAttributes.add(new ReplaceableAttribute(DomainVotePurchase_Attr.UserID, m_UserID, true));
		if(m_UpdateImageID)
			replaceableAttributes.add(new ReplaceableAttribute(DomainVotePurchase_Attr.ImageID, m_ImageID, true));
		if(m_UpdateDate)
			replaceableAttributes.add(new ReplaceableAttribute(DomainVotePurchase_Attr.Date, m_Date, true));
		if(m_UpdateVotes)
			replaceableAttributes.add(new ReplaceableAttribute(DomainVotePurchase_Attr.Votes, m_Votes, true));
		if(m_UpdateComplete)
			replaceableAttributes.add(new ReplaceableAttribute(DomainVotePurchase_Attr.Complete, m_Complete, true));
		
		return replaceableAttributes;
	}
	
	//Build Attributes list used by SimpleDB to create new records
	@Override
	public List<Attribute> getAttributes() {
		
		List<Attribute> attributes = new ArrayList<Attribute>();
		
		if(m_UpdateUserID)
			attributes.add(new Attribute(DomainVotePurchase_Attr.UserID, m_UserID));
		if(m_UpdateImageID)
			attributes.add(new Attribute(DomainVotePurchase_Attr.ImageID, m_ImageID));
		if(m_UpdateDate)
			attributes.add(new Attribute(DomainVotePurchase_Attr.Date, m_Date));
		if(m_UpdateVotes)
			attributes.add(new Attribute(DomainVotePurchase_Attr.Votes, m_Votes));
		if(m_UpdateComplete)
			attributes.add(new Attribute(DomainVotePurchase_Attr.Complete, m_Complete));
		
		return attributes;
	}

	
	// Builder Pattern
	public static final class Builder extends BuilderBase {
		private String userID = "";
		private String imageID = "";
		private String votes = "0";
		private String timestamp = getDate();
		private String complete = "false";
		private boolean newRecord = true; 
		
		public Builder(Item item) {
			super(item);
			newRecord = false;
			for (Attribute attribute : item.getAttributes()) {
				if (attribute.getName().equalsIgnoreCase(DomainVotePurchase_Attr.UserID))
					userID = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainVotePurchase_Attr.ImageID))
					imageID = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainVotePurchase_Attr.Votes))
					votes = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainVotePurchase_Attr.Date))
					timestamp = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainVotePurchase_Attr.Complete))
					complete = attribute.getValue();
				
			}

		}

		public Builder() {
			super();
		}

		
		public Builder(String id) {
			super(id);
		}

				
		public Builder withUserID(String value) {
			userID = value;
			return this;
		}

		public Builder withImageID(String value) {
			imageID = value;
			return this;
		}

		public Builder withVotes(int value)
		{
			votes = Integer.toString(value);
			return this;
		}
		
		public Builder withCompleteStatus(boolean value)
		{
			complete = Boolean.toString(value);
			return this;
		}

		public Builder withDate(DateTime value) {
			timestamp = Long.toString(value.getMillis());
			return this;
		}
		
		@Override
		public DomainVotePurchase build() {
			
			
			return new DomainVotePurchase(this);
		}
	}
}
