package dal.dto;

public final class DomainUsers_Attr {
	public static final String Domain = "v2_users";
	public static final String UserID = "userid";
	public static final String Views = "views";
	public static final String Viewing = "viewing";
	public static final String CurrentImage = "currentimage";
	public static final String CurrentImageOwner = "currentimageowner";
	public static final String LastUpdated = "lastupdate";
	public static final String Favorites = "favorites";
	public static final String PublicName = "publicname";
	public static final String Filter = "filter";
	
	private DomainUsers_Attr() {
		// this prevents even the native class from
		// calling this ctor as well :
		throw new AssertionError();
	}

}
