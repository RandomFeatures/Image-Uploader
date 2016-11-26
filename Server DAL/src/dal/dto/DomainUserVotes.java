package dal.dto;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;

import dal.dto.base.BuilderBase;
import dal.dto.base.DomainBase;

public class DomainUserVotes extends DomainBase {
	private String m_UserID;
	private String m_ImageID;
	private String m_VoteType;
	private String m_Date;
	private String m_Favorite;
	private String m_ImageURL;

	private boolean m_UpdateUserID;
	private boolean m_UpdateImageID;
	private boolean m_UpdateVoteType;
	private boolean m_UpdateDate;
	private boolean m_UpdateFavorite;
	private boolean m_UpdateImageURL;

	//Builder Constructor
	public DomainUserVotes(Builder build) {
		super(build);
		
		m_UserID = build.userid;
		m_ImageID = build.imageid;
		m_ImageURL = build.imageurl;
		m_VoteType = build.votetype;
		m_Date = build.timestamp;
		m_Favorite = build.favorite;
		
		m_UpdateUserID = build.newRecord  ;
		m_UpdateImageID = build.newRecord;
		m_UpdateImageURL = build.newRecord;
		m_UpdateVoteType = build.newRecord;
		m_UpdateDate = build.newRecord;
		m_UpdateFavorite = build.newRecord;

		
	}
	

	//Make copy of this object but with a new GUID
	@Override
	public DomainUserVotes copy() {
		return new DomainUserVotes.Builder()
								.withUserID(getUserID())
								.withImageID(getImageID())
								.withImageURL(getImageURL())
								.withVoteType(getVoteType())
								.withDate(getDate())
								.withFavorite(getFavorite())
								.build();
	}

	/***** Getters and Setters *****/
	
	public String getUserID() {
		return m_UserID;
	}

	public DomainUserVotes setUserID(String value) {
		m_UpdateUserID = true;
		m_UserID = value;
		return this;
	}

	public String getImageID() {
		return m_ImageID;
	}

	public DomainUserVotes setImageId(String value) {
		m_UpdateImageID = true;
		m_ImageID = value;
		return this;
	}
	
	public VoteType getVoteType() {
		return  VoteType.valueOf(m_VoteType);
	}

	public DomainUserVotes setVoteType(VoteType value) {
		m_UpdateVoteType = true;
		m_VoteType = value.toString();
		return this;
	}

	public DateTime getDate() {
		
		Long l = Long.parseLong(m_Date);
		return new DateTime(l);
	}

	public DomainUserVotes setDate(DateTime  value) {
		m_UpdateDate = true;
		m_Date = Long.toString(value.getMillis());
		return this;
	}

	
	public boolean getFavorite() {
		return Boolean.parseBoolean(m_Favorite);
	}

	public DomainUserVotes setFavorite(boolean value) {
		m_UpdateFavorite = true;
		m_Favorite = Boolean.toString(value);
		return this;
	}
	
	public String getImageURL() {
		return m_ImageURL;
	}

	public DomainUserVotes setImageURL(String value) {
		m_UpdateImageURL = true;
		m_ImageURL = value;
		return this;
	}
	
	
	//Build ReplaceableAttributes list used by SimpleDB to create or update existing records
	@Override
	public List<ReplaceableAttribute> getReplaceableAttributes() {
		
		List<ReplaceableAttribute> attrList = new ArrayList<ReplaceableAttribute>();
		
		if(m_UpdateUserID)
			attrList.add(new ReplaceableAttribute(DomainUserVotes_Attr.UserID, m_UserID, true));
		if(m_UpdateImageID)
			attrList.add(new ReplaceableAttribute(DomainUserVotes_Attr.ImageID, m_ImageID, true));
		if(m_UpdateVoteType)
			attrList.add(new ReplaceableAttribute(DomainUserVotes_Attr.VoteType, m_VoteType, true));
		if(m_UpdateDate)
			attrList.add(new ReplaceableAttribute(DomainUserVotes_Attr.Date, m_Date, true));
		if(m_UpdateFavorite)
			attrList.add(new ReplaceableAttribute(DomainUserVotes_Attr.Favorite, m_Favorite, true));
		if(m_UpdateImageURL)
			attrList.add(new ReplaceableAttribute(DomainUserVotes_Attr.ImageURL, m_ImageURL, true));

		return attrList;
	}
	//Build Attributes list used by SimpleDB to create new records
	@Override
	public List<Attribute> getAttributes() {
		
		List<Attribute> attrList = new ArrayList<Attribute>();
		
		if(m_UpdateUserID)
			attrList.add(new Attribute(DomainUserVotes_Attr.UserID, m_UserID));
		if(m_UpdateImageID)
			attrList.add(new Attribute(DomainUserVotes_Attr.ImageID, m_ImageID));
		if(m_UpdateVoteType)
			attrList.add(new Attribute(DomainUserVotes_Attr.VoteType, m_VoteType));
		if(m_UpdateDate)
			attrList.add(new Attribute(DomainUserVotes_Attr.Date, m_Date));
		if(m_UpdateFavorite)
			attrList.add(new Attribute(DomainUserVotes_Attr.Favorite, m_Favorite));
		if(m_UpdateImageURL)
			attrList.add(new Attribute(DomainUserVotes_Attr.ImageURL, m_ImageURL));

		return attrList;
	}

	
	// Builder Pattern
	public static final class Builder extends BuilderBase {

		public boolean newRecord  = true;
		private String imageid = "";
		private String userid = ""; 
		private String votetype = VoteType.NEUTRAL.toString();
		private String timestamp = getDate();
		private String favorite = "false";
		private String imageurl = "";
		 
		
		public Builder(Item item) {
			super(item);
			newRecord = false;
			for (Attribute attribute : item.getAttributes()) {
				if (attribute.getName().equalsIgnoreCase(DomainUserVotes_Attr.VoteType))
					votetype = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainUserVotes_Attr.Date))
					timestamp = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainUserVotes_Attr.Favorite))
					favorite = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainUserVotes_Attr.ImageID))
					imageid = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainUserVotes_Attr.UserID))
					userid = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainUserVotes_Attr.ImageURL))
					imageurl = attribute.getValue();
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
		
		public Builder withImageURL(String value){
			imageurl = value;
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
	

		public Builder withFavorite(boolean value) {
			favorite = Boolean.toString(value);
			return this;
		}

		@Override
		public DomainUserVotes build() {
			return new DomainUserVotes(this);
		}
	}
}
