package dal.dto;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;

import dal.dto.base.BuilderBase;
import dal.dto.base.DomainBase;

public class DomainImageScores extends DomainBase  {

	
	private String m_UpVotes;
	private String m_DownVotes;
	private String m_Score;
	private String m_Date;
	private String m_LastUpdate;

	private boolean m_UpdateUpVotes;
	private boolean m_UpdateDownVotes;
	private boolean m_UpdateScore;
	private boolean m_UpdateDate;
	private boolean m_UpdateLastUpdate;

	//Builder Constructor
	public DomainImageScores(Builder build) {
		super(build);

		m_UpVotes = build.upVotes;
		m_DownVotes = build.downVotes;
		m_Score = build.score;
		m_Date  = build.timestamp; 
		m_LastUpdate = build.lastupdate;
		
		m_UpdateUpVotes = build.newRecord;
		m_UpdateDownVotes = build.newRecord;
		m_UpdateScore = build.newRecord;
		m_UpdateDate  = build.newRecord; 
		m_UpdateLastUpdate = build.newRecord;
		
	}
	//Make copy of this object but with a new GUID
	@Override
	public DomainImages copy() {
		return new DomainImages.Builder()
								.withUpVotes(getUpVotes())
								.withDownVotes(getDownVotes())
								.withScore(getScore())
								.withDate(getDate())
								.withLastUpdate(getLastUpdate())
								.build();
	}

	/***** Getters and Setters *****/

	public int getUpVotes() {
		return Integer.parseInt(this.m_UpVotes);
	}

	public DomainImageScores setUpVotes(int value) {
		m_UpdateUpVotes = true;
		m_UpVotes = Integer.toString(value);
		return this;
	}

	public int getDownVotes() {
		return Integer.parseInt(this.m_DownVotes);
	}

	public DomainImageScores setDownVotes(int value) {
		m_UpdateDownVotes = true;
		m_DownVotes = Integer.toString(value);
		return this;
	}

	public double getScore() {
		return Double.parseDouble(m_Score);
	}

	public DomainImageScores setScore(double value) {
		m_UpdateScore = true;
		m_Score = Double.toString(value);
		return this;
	}

	public DateTime getDate() {
		
		Long l = Long.parseLong(m_Date);
		return new DateTime(l);
	}

	
	public DateTime getLastUpdate() {
		
		Long l = Long.parseLong(m_LastUpdate);
		return new DateTime(l);
	}

	public DomainImageScores setLastUpdate(DateTime  value) {
		m_UpdateLastUpdate = true;
		m_LastUpdate = Long.toString(value.getMillis());
		return this;
	}

	
	//Build ReplaceableAttributes list used by SimpleDB to create or update existing records
	@Override
	public List<ReplaceableAttribute> getReplaceableAttributes() {
		
		List<ReplaceableAttribute> attrList = new ArrayList<ReplaceableAttribute>();
		if(m_UpdateUpVotes)
			attrList.add(new ReplaceableAttribute(DomainImages_Attr.UpVotes, m_UpVotes, true));
		if(m_UpdateDownVotes)
			attrList.add(new ReplaceableAttribute(DomainImages_Attr.DownVotes, m_DownVotes, true));
		if(m_UpdateScore)
			attrList.add(new ReplaceableAttribute(DomainImages_Attr.Score, m_Score, true));
		if(m_UpdateDate)
			attrList.add(new ReplaceableAttribute(DomainImages_Attr.Date, m_Date, true));
		if(m_UpdateLastUpdate)
			attrList.add(new ReplaceableAttribute(DomainImages_Attr.LastUpdated, m_LastUpdate, true));

		return attrList;
	}
	//Build Attributes list used by SimpleDB to create new records
	@Override
	public List<Attribute> getAttributes() {

		List<Attribute> attrList = new ArrayList<Attribute>();
		
		if(m_UpdateUpVotes)
			attrList.add(new Attribute(DomainImages_Attr.UpVotes, m_UpVotes));
		if(m_UpdateDownVotes)
			attrList.add(new Attribute(DomainImages_Attr.DownVotes, m_DownVotes));
		if(m_UpdateScore)
			attrList.add(new Attribute(DomainImages_Attr.Score, m_Score));
		if(m_UpdateLastUpdate)
			attrList.add(new Attribute(DomainImages_Attr.LastUpdated, m_LastUpdate));
		if(m_UpdateDate)
			attrList.add(new Attribute(DomainImages_Attr.Date, m_Date));

		
		return attrList;
	}

	
	// Builder Pattern
	public static final class Builder extends BuilderBase {
		
		private String upVotes = "0";
		private String downVotes = "0";
		private String score = "0";
		private String timestamp = getDate();
		private String lastupdate = getDate();
		private boolean newRecord = true;

		public Builder(Item item) {
			super(item);
			newRecord = false;
			for (Attribute attribute : item.getAttributes()) {
				if (attribute.getName().equalsIgnoreCase(DomainImages_Attr.UpVotes))
					upVotes = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainImages_Attr.DownVotes))
					downVotes = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainImages_Attr.Score))
					score = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainImages_Attr.Date))
					timestamp = attribute.getValue();
			}

		}

		public Builder() {
			super();
		}

		public Builder(String id) {
			super(id);
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

		public Builder withDate(DateTime value) {
			timestamp = Long.toString(value.getMillis());
			return this;
		}
		
		public Builder withLastUpdate(DateTime value) {
			lastupdate = Long.toString(value.getMillis());
			return this;
		}
		
		@Override
		public DomainImageScores build() {
			return new DomainImageScores(this);
		}
	}
}
