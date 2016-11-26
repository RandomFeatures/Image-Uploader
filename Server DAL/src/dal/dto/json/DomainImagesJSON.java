package dal.dto.json;

import java.util.List;

import dal.dto.DomainImages;
import dal.dto.DomainTags;

public class DomainImagesJSON {
	public static String getImageItem(DomainImages dto)
	{
		StringBuilder json = new StringBuilder();
		
		json.append("{");
		
		json.append("\"imageid\":\""); json.append(dto.getID());json.append("\",");
		json.append("\"caption\":\""); json.append(dto.getCaption().replace("\"", "\\\""));json.append("\",");
		json.append("\"date\":\""); json.append(dto.getDate());json.append("\",");
		json.append("\"imageurl\":\""); json.append(dto.getImageurl());json.append("\",");
			json.append("\"counts\":{"); 
				json.append("\"views\":"); json.append(dto.getViews());json.append(",");
				json.append("\"viewing\":"); json.append(dto.getViewing());json.append(",");
				json.append("\"score\":"); json.append(dto.getScore());json.append(",");
				json.append("\"upvotes\":"); json.append(dto.getUpVotes());json.append(",");
				json.append("\"downvotes\":"); json.append(dto.getDownVotes());
			json.append("},");
			json.append("\"tags\":[");
				String prefix = "";
				for(String tag: dto.getTags())
				{
					json.append(prefix);
					prefix = ",";
					json.append("{");
					json.append("\"tag\":\"");json.append(tag);json.append("\"");
					json.append("}");
				}
			json.append("]");
		json.append("}");
		
		return json.toString();
	}
	
	public static String getImageList(List<DomainImages> images)
	{
		StringBuilder json = new StringBuilder();
		json.append("\"imagelist\":[");
		
		String prefix = "";
		for(DomainImages dto: images)
		{
			json.append(prefix);
			prefix = ",";
			json.append(getImageItem(dto));
		}
		
		json.append("]");
		return json.toString();
	}
	
	public static String getImages(List<DomainImages> images, String desc)
	{
		StringBuilder json = new StringBuilder();
		json.append("{\"images\":{");
		json.append("\"desc\":\"");json.append(desc);json.append("\",");
		json.append(getImageList(images));		
		json.append("}}");
		return json.toString();
	}
	
	public static String getTagList(List<DomainTags> tags)
	{
		StringBuilder json = new StringBuilder();
		json.append("{\"tags\":[");
		
		String prefix = "";
		for(DomainTags dto: tags)
		{
			json.append(prefix);
			prefix = ",";
			json.append("{\"tag\":\""); 
			json.append(dto.getID());json.append("\",");
			json.append("\"count\":");json.append(dto.getCount());
			json.append("}");
		}
		
		json.append("]}");
		
		return json.toString();
	}
	
}
