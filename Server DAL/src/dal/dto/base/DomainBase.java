package dal.dto.base;

import java.util.List;

import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.DeletableItem;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.ReplaceableItem;

public abstract class DomainBase {

	protected String m_ID;
	
	
	public DomainBase(BuilderBase build)
	{
		m_ID = build.id;
	}
	
	public String getID()
	{
		return m_ID;
	}
	
	public abstract DomainBase copy();
	
	//Build ReplaceableItem object used by SimpleDB to create or update existing records
	public ReplaceableItem getReplaceableItem() {
		return new ReplaceableItem(m_ID).withAttributes(getReplaceableAttributes());
	}
	
	//Build Item object used by SimpleDB to create new records
	public Item getItem() {
		return new Item().withName(m_ID).withAttributes(getAttributes());
	}
	
	//Build Item object used by SimpleDB to create new records
	public DeletableItem getDeleteableItem() {
		return new DeletableItem().withName(m_ID).withAttributes(getAttributes());
	}
	
	//Build ReplaceableAttributes list used by SimpleDB to create or update existing records
	public abstract List<ReplaceableAttribute> getReplaceableAttributes();
	
	//Build Attributes list used by SimpleDB to create new records
	public abstract List<Attribute> getAttributes();

}
