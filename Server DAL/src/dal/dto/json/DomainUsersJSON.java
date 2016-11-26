package dal.dto.json;

import java.util.List;

import dal.dto.DomainImages;
import dal.dto.DomainUserVotes;
import dal.dto.DomainUsers;

public class DomainUsersJSON {
	public static String getUser(DomainUsers user, List<DomainImages> images, List<DomainUserVotes> votes)
	{
		StringBuilder json = new StringBuilder();
		json.append("{");
			json.append("\"user\": {");
			
			json.append("\"id\":\""); json.append(user.getID());json.append("\",");
			json.append("\"name\":\""); json.append(user.getPublicName());json.append("\",");
				json.append("\"counts\":{"); 
					json.append("\"views\":"); json.append(user.getViews());json.append(",");
					json.append("\"viewing\":"); json.append(user.getViewing());
				json.append("},");
				json.append(DomainImagesJSON.getImageList(images));json.append(",");	
				json.append(getVoteList(votes));
				json.append("}");
		json.append("}");
		
		return json.toString();
	}
	
	public static String getVote(DomainUserVotes user)
	{
		StringBuilder json = new StringBuilder();
		
		json.append("{");
		
		json.append("\"imageid\":\""); json.append(user.getID());json.append("\",");
		json.append("\"date\":\""); json.append(user.getDate());json.append("\",");
		json.append("\"imageurl\":\""); json.append(user.getImageURL());json.append("\",");
		json.append("\"votetype\":\""); json.append(user.getVoteType().toString());json.append("\"");
		
		json.append("}");
		
		return json.toString();
	}
	
	public static String getVoteList(List<DomainUserVotes> votes)
	{
		StringBuilder json = new StringBuilder();
		json.append("\"votes\":[");
		
		String prefix = "";
		for(DomainUserVotes dto: votes)
		{
			json.append(prefix);
			prefix = ",";
			json.append(getVote(dto));
		}
		
		json.append("]");
		return json.toString();
	}
	
	
}
