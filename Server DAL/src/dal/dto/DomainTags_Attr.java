package dal.dto;

public final class DomainTags_Attr {
	public static final String Domain = "v2_tags";
	public static final String Tag = "tag";
	public static final String Count = "count";

	private DomainTags_Attr() {
		// this prevents even the native class from
		// calling this ctor as well :
		throw new AssertionError();
	}

}
