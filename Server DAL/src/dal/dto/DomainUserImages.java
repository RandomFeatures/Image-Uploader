package dal.dto;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;

import dal.dto.base.BuilderBase;
import dal.dto.base.DomainBase;

public class DomainUserImages extends DomainBase {

	private String m_UserID;
	private String m_ImageID;
	private String m_Views;
	private String m_UpVotes;
	private String m_DownVotes;
	private String m_Score;
	private String m_Viewing;
	
	private boolean m_UpdateUserID;
	private boolean m_UpdateImageID;
	private boolean m_UpdateViews;
	private boolean m_UpdateUpVotes;
	private boolean m_UpdateDownVotes;
	private boolean m_UpdateScore;
	private boolean m_UpdateViewing;

	
	//Builder Constructor
	public DomainUserImages(Builder build) {
		super(build);
		
		m_UserID = build.userid;
		m_ImageID = build.imageid;
		m_Views = build.views;
		m_UpVotes = build.upVotes;
		m_DownVotes = build.downVotes;
		m_Score = build.score;
		m_Viewing = build.viewing;
		
		m_UpdateUserID = build.newRecord;
		m_UpdateImageID = build.newRecord;
		m_UpdateViews = build.newRecord;
		m_UpdateUpVotes = build.newRecord;
		m_UpdateDownVotes = build.newRecord;
		m_UpdateScore = build.newRecord;
		m_UpdateViewing = build.newRecord;
		
	}
	//Make copy of this object but with a new GUID
	@Override
	public DomainUserImages copy() {
		return new DomainUserImages.Builder()
								.withUserID(getUserID())
								.withImageID(getImageID())
								.withViews(getViews())
								.withUpVotes(getUpVotes())
								.withDownVotes(getDownVotes())
								.withScore(getScore())
								.withViewing(getViewing())
								.build();
	}

	/***** Getters and Setters *****/
	
	public String getUserID() {
		return m_UserID;
	}

	public DomainUserImages setUserID(String userID) {
		m_UpdateUserID = true;
		m_UserID = userID;
		return this;
	}

	public String getImageID() {
		return m_ImageID;
	}

	public DomainUserImages setImageId(String imageId) {
		m_UpdateImageID = true;
		m_ImageID = imageId;
		return this;
	}

	public int getViews() {
		return Integer.parseInt(this.m_Views);
	}

	public DomainUserImages setViews(int value) {
		m_UpdateViews = true;
		m_Views = Integer.toString(value);
		return this;
	}

	public int getUpVotes() {
		return Integer.parseInt(this.m_UpVotes);
	}

	public DomainUserImages setUpVotes(int value) {
		m_UpdateUpVotes = true;
		m_UpVotes = Integer.toString(value);
		return this;
	}

	public int getDownVotes() {
		return Integer.parseInt(this.m_DownVotes);
	}

	public DomainUserImages setDownVotes(int value) {
		m_UpdateDownVotes = true;
		m_DownVotes = Integer.toString(value);
		return this;
	}

	public double getScore() {
		return Double.parseDouble(m_Score);
	}

	public DomainUserImages setScore(double value) {
		m_UpdateScore = true;
		m_Score = Double.toString(value);
		return this;
	}

	public int getViewing() {
		return Integer.parseInt(this.m_Viewing);
	}

	public DomainUserImages setViewing(int value) {
		m_UpdateViewing = true;
		m_Viewing = Integer.toString(value);
		return this;
	}

	//Build ReplaceableAttributes list used by SimpleDB to create or update existing records
	@Override
	public List<ReplaceableAttribute> getReplaceableAttributes() {

		List<ReplaceableAttribute> attrList = new ArrayList<ReplaceableAttribute>();
		
		if(m_UpdateUserID)
			attrList.add(new ReplaceableAttribute(DomainUserImages_Attr.UserID, m_UserID, true));
		if(m_UpdateImageID)
			attrList.add(new ReplaceableAttribute(DomainUserImages_Attr.ImageID, m_ImageID, true));
		if(m_UpdateViews)
			attrList.add(new ReplaceableAttribute(DomainUserImages_Attr.Views, m_Views, true));
		if(m_UpdateUpVotes)
			attrList.add(new ReplaceableAttribute(DomainUserImages_Attr.UpVotes, m_UpVotes, true));
		if(m_UpdateDownVotes)
			attrList.add(new ReplaceableAttribute(DomainUserImages_Attr.DownVotes, m_DownVotes, true));
		if(m_UpdateScore)
			attrList.add(new ReplaceableAttribute(DomainUserImages_Attr.Score, m_Score, true));
		if(m_UpdateViewing)
			attrList.add(new ReplaceableAttribute(DomainUserImages_Attr.Viewing, m_Viewing, true));

		return attrList;
	}
	//Build Attributes list used by SimpleDB to create new records
	@Override
	public List<Attribute> getAttributes() {
		
		List<Attribute> attrList = new ArrayList<Attribute>();
		
		if(m_UpdateUserID)
			attrList.add(new Attribute(DomainUserImages_Attr.UserID, m_UserID));
		if(m_UpdateImageID)
			attrList.add(new Attribute(DomainUserImages_Attr.ImageID, m_ImageID));
		if(m_UpdateViews)
			attrList.add(new Attribute(DomainUserImages_Attr.Views, m_Views));
		if(m_UpdateUpVotes)
			attrList.add(new Attribute(DomainUserImages_Attr.UpVotes, m_UpVotes));
		if(m_UpdateDownVotes)
			attrList.add(new Attribute(DomainUserImages_Attr.DownVotes, m_DownVotes));
		if(m_UpdateScore)
			attrList.add(new Attribute(DomainUserImages_Attr.Score, m_Score));
		if(m_UpdateViewing)
			attrList.add(new Attribute(DomainUserImages_Attr.Viewing, m_Viewing));

		return attrList;
	}

	
	// Builder Pattern
	public static final class Builder extends BuilderBase {

		private String imageid = "";
		private String userid = ""; 
		private String views = "0";
		private String upVotes = "0";
		private String downVotes = "0";
		private String score = "0";
		private String viewing = "0";
		private boolean newRecord = true;
		
		public Builder(Item item) {
			super(item);
			newRecord = false;
			for (Attribute attribute : item.getAttributes()) {
				if (attribute.getName().equalsIgnoreCase(DomainUserImages_Attr.Views))
					views = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainUserImages_Attr.UpVotes))
					upVotes = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainUserImages_Attr.DownVotes))
					downVotes = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainUserImages_Attr.Score))
					score = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainUserImages_Attr.Viewing))
					viewing = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainUserImages_Attr.ImageID))
					imageid = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainUserImages_Attr.UserID))
					userid = attribute.getValue();
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
		
		public Builder withViews(int value) {
			views = Integer.toString(value);
			return this;
		}

		public Builder withUpVotes(int value) {
			upVotes = Integer.toString(value);
			return this;
		}

		public Builder withDownVotes(int value) {
			downVotes = Integer.toString(value);
			return this;
		}

		public Builder withScore(double value) {
			score = Double.toString(value);
			return this;
		}

		public Builder withViewing(int value) {
			viewing = Integer.toString(value);
			return this;
		}

		@Override
		public DomainUserImages build() {
			return new DomainUserImages(this);
		}
	}

}
