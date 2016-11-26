package dal.dto;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;

import dal.dto.base.BuilderBase;
import dal.dto.base.DomainBase;

public class DomainTags extends DomainBase {
	private String m_Count;
	
	//Builder Constructor
	public DomainTags(Builder build) {
		super(build);
		m_Count = build.count;
	}
	//Make copy of this object but with a new GUID
	@Override
	public DomainTags copy() {
		return new DomainTags.Builder()
							 .withCount(getCount())
							 .build();
	}

	/***** Getters and Setters *****/
	
	public int getCount() {
		return Integer.parseInt(m_Count);
	}

	public DomainTags setCount(int value) {
		m_Count = Integer.toString(value);
		return this;
	}
	
	//Build ReplaceableAttributes list used by SimpleDB to create or update existing records
	@Override
	public List<ReplaceableAttribute> getReplaceableAttributes() {

		List<ReplaceableAttribute> attrList = new ArrayList<ReplaceableAttribute>();
		
		attrList.add(new ReplaceableAttribute(DomainTags_Attr.Count, m_Count, true));

		return attrList;
	}
	//Build Attributes list used by SimpleDB to create new records
	@Override
	public List<Attribute> getAttributes() {
		
		List<Attribute> attrList = new ArrayList<Attribute>();
		
		attrList.add(new Attribute(DomainTags_Attr.Count, m_Count));

		return attrList;
	}

	
	// Builder Pattern
	public static final class Builder extends BuilderBase {

		private String count = "0";

		public Builder(Item item) {
			super(item);

			for (Attribute attribute : item.getAttributes()) {
				if (attribute.getName().equalsIgnoreCase(DomainTags_Attr.Count))
					count = attribute.getValue();
			}

		}

		public Builder() {
			super();
		}

		public Builder(String id) {
			super(id);
		}

		public Builder withCount(int value) {
			count = Integer.toString(value);
			return this;
		}



		@Override
		public DomainTags build() {
			return new DomainTags(this);
		}
	}
}
