package dal.dto;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;

import dal.dto.base.BuilderBase;
import dal.dto.base.DomainBase;

public class DomainSettings extends DomainBase {
	private String m_DownLoadURL;
	private String m_Bucket;
	
	//Builder Constructor
	public DomainSettings(Builder build) {
		super(build);
		m_DownLoadURL = build.download;
		m_Bucket = build.bucket;
	}
	//Make copy of this object but with a new GUID
	@Override
	public DomainSettings copy() {
		return new DomainSettings.Builder()
							 .withDownloadURL(getDownLoadURL())
							 .withBucket(getBucket())
							 .build();
	}

	/***** Getters and Setters *****/
	
	public String getDownLoadURL() {
		return m_DownLoadURL;
	}

	public DomainSettings setDownloadURL(String value) {
		m_DownLoadURL = value;
		return this;
	}
	
	public String getBucket() {
		return m_Bucket;
	}
	public void setBucket(String value) {
		m_Bucket = value;
	}
	
	
	//Build ReplaceableAttributes list used by SimpleDB to create or update existing records
	@Override
	public List<ReplaceableAttribute> getReplaceableAttributes() {

		List<ReplaceableAttribute> attrList = new ArrayList<ReplaceableAttribute>();
		
		attrList.add(new ReplaceableAttribute(DomainSettings_Attr.DownloadURL, m_DownLoadURL, true));
		attrList.add(new ReplaceableAttribute(DomainSettings_Attr.Bucket, m_Bucket, true));

		return attrList;
	}
	//Build Attributes list used by SimpleDB to create new records
	@Override
	public List<Attribute> getAttributes() {
		
		List<Attribute> attrList = new ArrayList<Attribute>();
		
		attrList.add(new Attribute(DomainSettings_Attr.DownloadURL, m_DownLoadURL));
		attrList.add(new Attribute(DomainSettings_Attr.Bucket, m_Bucket));

		return attrList;
	}

	
	// Builder Pattern
	public static final class Builder extends BuilderBase {

		private String download = "";
		private String bucket = "";

		public Builder(Item item) {
			super(item);

			for (Attribute attribute : item.getAttributes()) {
				if (attribute.getName().equalsIgnoreCase(DomainSettings_Attr.DownloadURL))
					download = attribute.getValue();
				else if (attribute.getName().equalsIgnoreCase(DomainSettings_Attr.Bucket))
					bucket = attribute.getValue();
			}

		}

		public Builder() {
			super();
		}

		public Builder(String id) {
			super(id);
		}

		public Builder withDownloadURL(String value) {
			download = value;
			return this;
		}

		public Builder withBucket(String value) {
			bucket = value;
			return this;
		}


		@Override
		public DomainSettings build() {
			return new DomainSettings(this);
		}
	}
}
