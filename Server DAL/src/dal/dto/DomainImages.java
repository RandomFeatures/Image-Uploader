package dal.dto;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import util.Extractor;

import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;

import dal.dto.base.BuilderBase;
import dal.dto.base.DomainBase;

public class DomainImages extends DomainBase {

	private String m_Views;
	private String m_UpVotes;
	private String m_DownVotes;
	private String m_Score;
	private String m_Caption;
	private List<String> m_Tags = new ArrayList<String>();
	private String m_Date;
	private String m_Viewing;
	private String m_Active;
	private String m_Source;
	private String m_SourceURL;
	private String m_SourceID;
	private String m_ImageURL;
	private String m_UserID;
	private String m_LastUpdate;

	private boolean m_UpdateViews;
	private boolean m_UpdateUpVotes;
	private boolean m_UpdateDownVotes;
	private boolean m_UpdateScore;
	private boolean m_UpdateCaption;
	private boolean m_UpdateTags;
	private boolean m_UpdateDate;
	private boolean m_UpdateViewing;
	private boolean m_UpdateActive;
	private boolean m_UpdateSource;
	private boolean m_UpdateSourceURL;
	private boolean m_UpdateSourceID;
	private boolean m_UpdateImageURL;
	private boolean m_UpdateUserID;
	private boolean m_UpdateLastUpdate;
	
	
	//Builder Constructor
	public DomainImages(Builder build) {
		super(build);

		m_Views = build.views;
		m_UpVotes = build.upVotes;
		m_DownVotes = build.downVotes;
		m_Score = build.score;
		m_Caption = build.caption;
		m_Tags = build.tags;
		m_Date  = build.timestamp; 
		m_Viewing = build.viewing;
		m_Active  = build.active;
		m_Source  = build.source;
		m_SourceURL = build.sourceURL;
		m_SourceID = build.sourceID;
		m_ImageURL = build.imageURL;
		m_UserID = build.userID;
		m_LastUpdate = build.lastupdate;
		
		
		m_UpdateViews = build.newRecord;
		m_UpdateUpVotes = build.newRecord;
		m_UpdateDownVotes = build.newRecord;
		m_UpdateScore = build.newRecord;
		m_UpdateCaption = build.newRecord;
		m_UpdateTags = build.newRecord;
		m_UpdateDate  = build.newRecord; 
		m_UpdateViewing = build.newRecord;
		m_UpdateActive  = build.newRecord;
		m_UpdateSource  = build.newRecord;
		m_UpdateSourceURL = build.newRecord;
		m_UpdateSourceID = build.newRecord;
		m_UpdateImageURL = build.newRecord;
		m_UpdateUserID = build.newRecord;
		m_UpdateLastUpdate = build.newRecord;
		
		
	}
	//Make copy of this object but with a new GUID
	@Override
	public DomainImages copy() {
		return new DomainImages.Builder()
								.withViews(getViews())
								.withUpVotes(getUpVotes())
								.withDownVotes(getDownVotes())
								.withScore(getScore())
								.withCaption(getCaption())
								.withTags(getTags())
								.withDate(getDate())
								.withLastUpdate(getLastUpdate())
								.withViewing(getViewing())
								.withActive(getActive())
								.withSource(getSource())
								.withSourceURL(getSourceURL())
								.withSourceID(getSourceID())
								.withImageURL(getImageurl())
								.withUserID(getUserID())
								.build();
	}

	/***** Getters and Setters *****/
	public int getViews() {
		return Integer.parseInt(this.m_Views);
	}

	public DomainImages setViews(int value) {
		m_UpdateViews = true;
		m_Views = Integer.toString(value);
		return this;
	}

	public int getUpVotes() {
		return Integer.parseInt(this.m_UpVotes);
	}

	public DomainImages setUpVotes(int value) {
		m_UpdateUpVotes = true;
		m_UpVotes = Integer.toString(value);
		return this;
	}

	public int getDownVotes() {
		return Integer.parseInt(this.m_DownVotes);
	}

	public DomainImages setDownVotes(int value) {
		m_UpdateDownVotes = true;
		m_DownVotes = Integer.toString(value);
		return this;
	}

	public double getScore() {
		return Double.parseDouble(m_Score);
	}

	public DomainImages setScore(double value) {
		m_UpdateScore = true;
		m_Score = Double.toString(value);
		return this;
	}

	public String getCaption() {
		return m_Caption;
	}

	public DomainImages setCaption(String value) {
		
		m_Caption = value;
		
		return this;
	}

	public DomainImages setCaptionParse(String value) {
		m_UpdateCaption = true;
		m_UpdateTags = true;
		m_Caption = value;
		m_Tags = Extractor.extratHashtags(m_Caption);
		return this;
	}
	
	public List<String> getTags() {
		return m_Tags;
	}

	public DomainImages setTags(List<String> value) {
		m_UpdateTags = true;
		m_Tags = value;
		return this;
	}
	
	public DomainImages addTag(String value) {
		m_UpdateTags = true;
		if(m_Tags.indexOf(value) == -1)
			m_Tags.add(value);
		return this;
	}

	public DateTime getDate() {
		
		Long l = Long.parseLong(m_Date);
		return new DateTime(l);
	}

	public DomainImages setDate(DateTime  value) {
		m_UpdateDate = true;
		m_Date = Long.toString(value.getMillis());
		return this;
	}

	
	public DateTime getLastUpdate() {
		Long l = Long.parseLong(m_LastUpdate);
		return new DateTime(l);
	}

	public DomainImages setLastUpdate(DateTime  value) {
		m_UpdateLastUpdate = true;
		m_LastUpdate = Long.toString(value.getMillis());
		return this;
	}
	
	public int getViewing() {
		return Integer.parseInt(this.m_Viewing);
	}

	public DomainImages setViewing(int value) {
		m_UpdateViewing = true;
		m_Viewing = Integer.toString(value);
		return this;
	}

	public boolean getActive() {
		return Boolean.parseBoolean(m_Active);
	}

	public DomainImages setActive(boolean value) {
		m_UpdateActive = true;
		m_Active = Boolean.toString(value);
		return this;
	}

	public String getSourceURL() {
		return m_SourceURL;
	}

	public DomainImages setSourceURL(String value) {
		m_UpdateSourceURL = true;
		m_SourceURL = value;
		return this;
	}

	public String getSourceID() { 
		return m_SourceID;
	}

	public DomainImages setSourceID(String value) {
		m_UpdateSourceID = true;
		m_SourceID = value;
		return this;
	}
	
	public String getSource() { 
		return m_Source;
	}

	public DomainImages setSource(String value) {
		m_UpdateSource = true;
		m_Source = value;
		return this;
	}

	public String getImageurl() {
		return m_ImageURL;
	}

	public DomainImages setImageurl(String value) {
		m_UpdateImageURL = true;
		m_ImageURL = value;
		return this;
	}

	public String getUserID() { 
		return m_UserID;
	}

	public DomainImages setUserID(String value) {
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
		if(m_UpdateUpVotes)
			attrList.add(new ReplaceableAttribute(DomainImages_Attr.UpVotes, m_UpVotes, true));
		if(m_UpdateDownVotes)
			attrList.add(new ReplaceableAttribute(DomainImages_Attr.DownVotes, m_DownVotes, true));
		if(m_UpdateScore)
			attrList.add(new ReplaceableAttribute(DomainImages_Attr.Score, m_Score, true));
		if(m_UpdateCaption)
			attrList.add(new ReplaceableAttribute(DomainImages_Attr.Caption, m_Caption, true));
		if(m_UpdateDate)
			attrList.add(new ReplaceableAttribute(DomainImages_Attr.Date, m_Date, true));
		if(m_UpdateLastUpdate)
			attrList.add(new ReplaceableAttribute(DomainImages_Attr.LastUpdated, m_LastUpdate, true));
		if(m_UpdateViewing)
			attrList.add(new ReplaceableAttribute(DomainImages_Attr.Viewing, m_Viewing, true));
		if(m_UpdateSource)
			attrList.add(new ReplaceableAttribute(DomainImages_Attr.Source, m_Source, true));
		if(m_UpdateSourceURL)
			attrList.add(new ReplaceableAttribute(DomainImages_Attr.SourceURL, m_SourceURL, true));
		if(m_UpdateSourceID)
			attrList.add(new ReplaceableAttribute(DomainImages_Attr.SourceID, m_SourceID, true));
		if(m_UpdateImageURL)
			attrList.add(new ReplaceableAttribute(DomainImages_Attr.ImageURL, m_ImageURL, true));
		if(m_UpdateUserID)
			attrList.add(new ReplaceableAttribute(DomainImages_Attr.UserID, m_UserID, true));
		if(m_UpdateActive)
			attrList.add(new ReplaceableAttribute(DomainImages_Attr.Active, m_Active, true));

		if(m_UpdateTags)
		for(String tag: m_Tags)
		{
			attrList.add(new ReplaceableAttribute(DomainImages_Attr.Tags, tag, true));
		}
		
		return attrList;
	}
	//Build Attributes list used by SimpleDB to create new records
	@Override
	public List<Attribute> getAttributes() {

		List<Attribute> attrList = new ArrayList<Attribute>();
		
		if(m_UpdateViews)
			attrList.add(new Attribute(DomainImages_Attr.Views, m_Views));
		if(m_UpdateUpVotes)
			attrList.add(new Attribute(DomainImages_Attr.UpVotes, m_UpVotes));
		if(m_UpdateDownVotes)
			attrList.add(new Attribute(DomainImages_Attr.DownVotes, m_DownVotes));
		if(m_UpdateScore)
			attrList.add(new Attribute(DomainImages_Attr.Score, m_Score));
		if(m_UpdateCaption)
			attrList.add(new Attribute(DomainImages_Attr.Caption, m_Caption));
		if(m_UpdateDate)
			attrList.add(new Attribute(DomainImages_Attr.Date, m_Date));
		if(m_UpdateLastUpdate)
			attrList.add(new Attribute(DomainImages_Attr.LastUpdated, m_LastUpdate));
		if(m_UpdateViewing)
			attrList.add(new Attribute(DomainImages_Attr.Viewing, m_Viewing));
		if(m_UpdateSource)
			attrList.add(new Attribute(DomainImages_Attr.Source, m_Source));
		if(m_UpdateSourceURL)
			attrList.add(new Attribute(DomainImages_Attr.SourceURL, m_SourceURL));
		if(m_UpdateSourceID)
			attrList.add(new Attribute(DomainImages_Attr.SourceID, m_SourceID));
		if(m_UpdateImageURL)
			attrList.add(new Attribute(DomainImages_Attr.ImageURL, m_ImageURL));
		if(m_UpdateUserID)
			attrList.add(new Attribute(DomainImages_Attr.UserID, m_UserID));
		if(m_UpdateActive)
			attrList.add(new Attribute(DomainImages_Attr.Active, m_Active));
		
		if(m_UpdateTags)
		for(String tag: m_Tags)
		{
			attrList.add(new Attribute(DomainImages_Attr.Tags, tag));
		}
		
		return attrList;
	}

	
	// Builder Pattern
	public static final class Builder extends BuilderBase {
		
		private String views = "0";
		private String upVotes = "0";
		private String downVotes = "0";
		private String score = "0";
		private String caption = "";
		private List<String> tags = new ArrayList<String>();
		private String timestamp = getDate();
		private String viewing = "0";
		private String active = "true";
		private String source = "";
		private String sourceURL = "";
		private String sourceID = "";
		private String imageURL = "";
		private String userID = "";
		private String lastupdate = getDate();
		private boolean newRecord = true;

		public Builder(Item item) {
			super(item);
			newRecord = false;
			for (Attribute attribute : item.getAttributes()) {
				if (attribute.getName().equalsIgnoreCase(DomainImages_Attr.Views))
					views = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainImages_Attr.UpVotes))
					upVotes = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainImages_Attr.DownVotes))
					downVotes = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainImages_Attr.Score))
					score = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainImages_Attr.Caption))
					caption = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainImages_Attr.Date))
					timestamp = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainImages_Attr.Viewing))
					viewing = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainImages_Attr.Active))
					active = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainImages_Attr.Source))
					source = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainImages_Attr.SourceURL))
					sourceURL = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainImages_Attr.SourceID))
					sourceID = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainImages_Attr.ImageURL))
					imageURL = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainImages_Attr.UserID))
					userID = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainImages_Attr.LastUpdated))
					lastupdate = attribute.getValue();
				
				else if (attribute.getName().equalsIgnoreCase(DomainImages_Attr.Tags))
					tags.add(attribute.getValue());
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

		public Builder withCaption(String value) {
			caption = value;
			return this;
		}
		
		public Builder withCaptionParsing(String value) {
			caption = value;
			tags = Extractor.extratHashtags(caption);
			return this;
		}
		
		
		public Builder withDate(DateTime value) {
			timestamp = Long.toString(value.getMillis());
			return this;
		}
		
		public Builder withLastUpdate(DateTime value) {
			lastupdate = Long.toString(value.getMillis());
			return this;
		}
		
		public Builder withViewing(int value) {
			viewing = Integer.toString(value);
			return this;
		}
		
		public Builder withActive(boolean value) {
			active = Boolean.toString(value);
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

		public Builder withImageURL(String value) {
			imageURL = value;
			return this;
		}

		public Builder withUserID(String value) {
			userID = value;
			return this;
		}
		public Builder withTags(List<String> value) {
			tags = value;
			return this;
		}
		
		
		@Override
		public DomainImages build() {
			return new DomainImages(this);
		}
	}
}
