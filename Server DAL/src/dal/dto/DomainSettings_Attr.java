package dal.dto;

public class DomainSettings_Attr {
	public static final String Domain = "v2_settings";
	public static final String Master = "master";
	public static final String DownloadURL = "downloadurl";
	public static final String Bucket = "bucket";

	private DomainSettings_Attr() {
		// this prevents even the native class from
		// calling this ctor as well :
		throw new AssertionError();
	}

}
