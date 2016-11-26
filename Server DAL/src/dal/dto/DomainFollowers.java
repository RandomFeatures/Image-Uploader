package dal.dto;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;

import dal.dto.base.BuilderBase;
import dal.dto.base.DomainBase;

public class DomainFollowers extends DomainBase {

	private boolean m_UpdateUserID = true;
	private String m_UserID;
	private boolean m_UpdateFollowID = true;
	private String m_FollowID;
	
	public DomainFollowers(Builder build) {
		super(build);
		m_UserID = build.UserID;
		m_FollowID = build.FollowID;
		
		m_UpdateUserID = build.newRecord;
		m_UpdateFollowID = build.newRecord;
		
	}

	//Make copy of this object but with a new GUID
	@Override
	public DomainFollowers copy() {
		return new DomainFollowers.Builder()
								.withUserID(getUserID())
								.withFollowID(getFollowID())
								.build();
	}

	
	
	public String getUserID() {
		return m_UserID;
	}

	public void setUserID(String userID) {
		m_UpdateUserID = true;
		m_UserID = userID;
	}

	public String getFollowID() {
		return m_FollowID;
	}

	public void setFollowID(String followID) {
		m_UpdateFollowID = true;
		m_FollowID = followID;
	}

	//Build ReplaceableAttributes list used by SimpleDB to create or update existing records
	@Override
	public List<ReplaceableAttribute> getReplaceableAttributes() {

		List<ReplaceableAttribute> replaceableAttributes = new ArrayList<ReplaceableAttribute>();
		
		if(m_UpdateUserID)
			replaceableAttributes.add(new ReplaceableAttribute(DomainFollowers_Attr.UserID, m_UserID, true));
		if(m_UpdateFollowID)
			replaceableAttributes.add(new ReplaceableAttribute(DomainFollowers_Attr.FollowID, m_FollowID, true));

		return replaceableAttributes;
	}

	//Build Attributes list used by SimpleDB to create new records
	@Override
	public List<Attribute> getAttributes() {

		List<Attribute> attributes = new ArrayList<Attribute>();
		if(m_UpdateUserID)
			attributes.add(new Attribute(DomainFollowers_Attr.UserID, m_UserID));
		if(m_UpdateFollowID)
			attributes.add(new Attribute(DomainFollowers_Attr.FollowID, m_FollowID));
		
		return attributes;
	}
	
	//Builder Pattern
	public static final class Builder extends BuilderBase {
		private boolean newRecord = true;
		private String UserID = "";
		private String FollowID = "";
		
		public Builder(Item item){
			super(item);
			newRecord = false;
			for (Attribute attribute : item.getAttributes()) {
				if (attribute.getName().equalsIgnoreCase(DomainFollowers_Attr.UserID))
					UserID = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainFollowers_Attr.FollowID))
					FollowID = attribute.getValue();
			}
			
		}
		
		public Builder() {
			super();
		}

		public Builder(String id)
		{
			super(id);
		}
		
		public Builder withUserID(String value) {
			UserID = value;
			return this;
		}
		
		public Builder withFollowID(String value) {
			FollowID = value;
			return this;
		}
		
		
		@Override
		public DomainFollowers build() {
			return new DomainFollowers(this);
		}
	}
}
