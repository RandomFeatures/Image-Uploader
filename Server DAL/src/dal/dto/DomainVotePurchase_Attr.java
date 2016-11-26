package dal.dto;

public final class DomainVotePurchase_Attr {
	public static final String Domain = "v2_votepurchase";
	public static final String UserID = "userid";
	public static final String ImageID = "imageid";
	public static final String Votes = "votes";
	public static final String Complete = "complete";
	public static final String Date = "date";
	
	
	private DomainVotePurchase_Attr() {
		// this prevents even the native class from
		// calling this ctor as well :
		throw new AssertionError();
	}

}

