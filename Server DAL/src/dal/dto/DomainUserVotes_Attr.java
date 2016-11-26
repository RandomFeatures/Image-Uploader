package dal.dto;

public final class DomainUserVotes_Attr {
	public static final String Domain = "v2_uservotes";
	public static final String UserID = "userid";
	public static final String ImageID = "imageid";
	public static final String ImageURL = "imageurl";
	public static final String VoteType = "votetype";
	public static final String Date = "date";
	public static final String Favorite = "favorite";
	
	private DomainUserVotes_Attr() {
		// this prevents even the native class from
		// calling this ctor as well :
		throw new AssertionError();
	}

}
