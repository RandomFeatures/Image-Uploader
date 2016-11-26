package dal.dto;

public final class DomainViews_Attr {
	public static final String Domain = "v2_views";
	public static final String Suffix = "suffix";
	public static final String UserID = "userid";
	public static final String ImageID = "imageid";
	public static final String Date = "date";
	
	private DomainViews_Attr() {
		// this prevents even the native class from
		// calling this ctor as well :
		throw new AssertionError();
	}
	
}
