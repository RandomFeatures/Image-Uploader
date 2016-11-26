package dal.dto;

public final class DomainImageSubmit_Attr {
	public static final String Domain = "v2_imagesubmit";
	public static final String Suffix = "suffix";
	public static final String UserID = "userid";
	public static final String Source = "source";
	public static final String SourceURL = "sourceurl";
	public static final String SourceID = "sourceid";
	public static final String Date = "Date";
	public static final String Instagram = "instagram";


	private DomainImageSubmit_Attr() {
		// this prevents even the native class from
		// calling this ctor as well :
		throw new AssertionError();
	}

}
