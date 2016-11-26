package dal.dto;

public final class DomainVotes_Attr {
	public static final String Domain = "v2_votes";
	public static final String Suffix = "sufix";
	public static final String UserID = "userid";
	public static final String ImageID = "imageid";
	public static final String VoteType = "votetype";
	public static final String Date = "date";
	
	private DomainVotes_Attr() {
		// this prevents even the native class from
		// calling this ctor as well :
		throw new AssertionError();
	}

}
