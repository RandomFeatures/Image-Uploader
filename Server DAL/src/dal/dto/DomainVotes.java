package dal.dto;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;

import dal.dto.base.BuilderBase;
import dal.dto.base.DomainBase;

public class DomainVotes extends DomainBase {

	private String m_UserID;
	private String m_ImageID;
	private String m_Date;
	private String m_Suffix;
	private String m_VoteType;

	private boolean m_UpdateUserID;
	private boolean m_UpdateImageID;
	private boolean m_UpdateDate;
	private boolean m_UpdateSuffix;
	private boolean m_UpdateVoteType;

	
	//Builder Constructor
	public DomainVotes(Builder build) {
		super(build);
		m_UserID = build.userid;
		m_ImageID = build.imageid;
		m_Date = build.timestamp;
		m_Suffix = build.suffix;
		m_VoteType = build.votetype;
		
		m_UpdateUserID = build.newRecord;
		m_UpdateImageID = build.newRecord;
		m_UpdateDate = build.newRecord;
		m_UpdateSuffix = build.newRecord;
		m_UpdateVoteType = build.newRecord;

	}
	
	//Make copy of this object but with a new GUID
	@Override
	public DomainVotes copy() {
		return new DomainVotes.Builder()
								.withUserID(getUserID())
								.withImageID(getImageID())
								.withDate(getDate())
								.withSuffix(getSuffix())
								.withVoteType(getVoteType())
								.build();
	}

	/***** Getters and Setters *****/
	
	public String getUserID() {
		return m_UserID;
	}

	public DomainVotes setUserID(String value) {
		m_UpdateUserID = true;
		m_UserID = value;
		return this;
	}

	public String getImageID() {
		return m_ImageID;
	}

	public DomainVotes setImageId(String value) {
		m_UpdateImageID = true;
		m_ImageID = value;
		return this;
	}

	public String getSuffix() {
		return m_Suffix;
	}

	public DomainVotes setSuffix(String value) {
		m_UpdateSuffix = true;
		m_Suffix = value;
		return this;
	}
	
	public VoteType getVoteType() {
		return VoteType.valueOf(m_VoteType);
	}

	public DomainVotes setVoteType(VoteType value) {
		m_UpdateVoteType = true;
		m_VoteType = value.toString();
		return this;
	}
	
	public DateTime getDate() {
		
		Long l = Long.parseLong(m_Date);
		return new DateTime(l);
	}

	public DomainVotes setDate(DateTime  value) {
		m_UpdateDate = true;
		m_Date = Long.toString(value.getMillis());
		return this;
	}

	
	//Build ReplaceableAttributes list used by SimpleDB to create or update existing records
	@Override
	public List<ReplaceableAttribute> getReplaceableAttributes() {
		
		List<ReplaceableAttribute> attrList = new ArrayList<ReplaceableAttribute>();
		
		if(m_UpdateUserID)
			attrList.add(new ReplaceableAttribute(DomainVotes_Attr.UserID, m_UserID, true));
		if(m_UpdateImageID)
			attrList.add(new ReplaceableAttribute(DomainVotes_Attr.ImageID, m_ImageID, true));
		if(m_UpdateDate)
			attrList.add(new ReplaceableAttribute(DomainVotes_Attr.Date, m_Date, true));
		if(m_UpdateSuffix)
			attrList.add(new ReplaceableAttribute(DomainVotes_Attr.Suffix, m_Suffix, true));
		if(m_UpdateVoteType)
			attrList.add(new ReplaceableAttribute(DomainVotes_Attr.VoteType, m_VoteType, true));

		return attrList;
	}
	//Build Attributes list used by SimpleDB to create new records
	@Override
	public List<Attribute> getAttributes() {
		List<Attribute> attrList = new ArrayList<Attribute>();
		
		if(m_UpdateUserID)
			attrList.add(new Attribute(DomainVotes_Attr.UserID, m_UserID));
		if(m_UpdateImageID)
			attrList.add(new Attribute(DomainVotes_Attr.ImageID, m_ImageID));
		if(m_UpdateDate)
			attrList.add(new Attribute(DomainVotes_Attr.Date, m_Date));
		if(m_UpdateSuffix)
			attrList.add(new Attribute(DomainVotes_Attr.Suffix, m_Suffix));
		if(m_UpdateVoteType)
			attrList.add(new Attribute(DomainVotes_Attr.VoteType, m_VoteType));

		return attrList;
	}
	
	// Builder Pattern
	public static final class Builder extends BuilderBase {

		private String imageid = "";
		private String userid = "";
		private String timestamp = getDate();
		private String suffix = "0";
		private String votetype = VoteType.NEUTRAL.toString();
		private boolean newRecord = true;
		
		public Builder(Item item) {
			super(item);
			newRecord = false;
			for (Attribute attribute : item.getAttributes()) {
				if (attribute.getName().equalsIgnoreCase(DomainVotes_Attr.Date))
					timestamp = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainVotes_Attr.ImageID))
					imageid = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainVotes_Attr.UserID))
					userid = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainVotes_Attr.Suffix))
					suffix = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainVotes_Attr.VoteType))
					votetype = attribute.getValue();
			}

		}

		public Builder() {
			super();
		}

		public Builder(String id) {
			super(id);
		}

		public Builder withUserID(String value) {
			userid = value;
			return this;
		}

		public Builder withImageID(String value) {
			imageid = value;
			return this;
		}

		public Builder withSuffix(String value) {
			suffix = value;
			return this;
		}

		public Builder withVoteType(VoteType value) {
			votetype = value.toString();
			return this;
		}
		
		public Builder withDate(DateTime value) {
			timestamp = Long.toString(value.getMillis());
			return this;
		}
		

		@Override
		public DomainVotes build() {
			return new DomainVotes(this);
		}
	}

}
