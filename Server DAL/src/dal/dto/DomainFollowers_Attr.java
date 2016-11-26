package dal.dto;

public final class DomainFollowers_Attr {
	public static final String Domain = "v2_followers";
	public static final String UserID = "userid";
	public static final String FollowID = "followid";

	private DomainFollowers_Attr() {
		// this prevents even the native class from
		// calling this ctor as well :
		throw new AssertionError();
	}
}
