package dal.dto;

public final class DomainUserImages_Attr {
	public static final String Domain = "v2_userimages";
	public static final String UserID = "userid";
	public static final String ImageID = "imageid";
	public static final String Views = "views";
	public static final String UpVotes = "upvotes";
	public static final String DownVotes = "downvotes";
	public static final String Viewing = "viewing";
	public static final String Score = "score";

	private DomainUserImages_Attr() {
		// this prevents even the native class from
		// calling this ctor as well :
		throw new AssertionError();
	}

}
