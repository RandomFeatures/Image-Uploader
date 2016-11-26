package dal.dto.base;

import java.util.UUID;

import org.joda.time.DateTime;

import com.amazonaws.services.simpledb.model.Item;

public abstract class BuilderBase {
	public String id;
	
	public BuilderBase() {
		this.id = UUID.randomUUID().toString();
	}
	
	public BuilderBase(String id) {
		if(id.isEmpty())
			this.id = UUID.randomUUID().toString();
		else
			this.id = id;
	}
	
	public BuilderBase(Item item) {
		this.id = item.getName();
	}
	
	protected String getDate() {
        return Long.toString(new DateTime().getMillis());
	}
	
	public abstract Object build();
}
