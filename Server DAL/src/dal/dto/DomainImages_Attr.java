package dal.dto;

public final class DomainImages_Attr {
	public static final String Domain = "v2_images";
	public static final String ImageID = "imageid";
	public static final String UserID = "userid";
	public static final String Views = "views";
	public static final String UpVotes = "upvotes";
	public static final String DownVotes = "downvotes";
	public static final String Score = "score";
	public static final String Caption = "caption";
	public static final String Tags = "tags";
	public static final String Date = "Date";
	public static final String Viewing = "viewing";
	public static final String Active = "active";
	public static final String LastUpdated = "lastupdate";
	public static final String ImageURL = "imageurl";
	public static final String Source = "source";
	public static final String SourceURL = "sourceurl";
	public static final String SourceID = "sourceid";

	private DomainImages_Attr() {
		// this prevents even the native class from
		// calling this ctor as well :
		throw new AssertionError();
	}

}
