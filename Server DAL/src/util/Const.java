package util;

public final class Const {
	public static final String TopRankedImages = "v2_toprankedimages";
	public static final String NewestImages = "v2_newestimages";
	public static final String TopTags = "v2_toptags";
	public static String TopRankedByTag = "v2_toprankedbytag_";
	public static String NewestByTag = "v2_newestbytag_";
	public static String UserData = "v2_userdata_";
	public static final String DescTopRanked = "Top ranked images descending order";
	public static final String DescNewest = "Newest images ascending order";
	public static final String DescTopRankedByTag = "Ranked images for top ranked tags based on user filters";
	public static final String DescNewestByTag = "Newest images for top ranked tags based on user filters";
	
	 /** Opposite of {@link #FAILS}.  */
	  public static final boolean PASSES = true;
	  /** Opposite of {@link #PASSES}.  */
	  public static final boolean FAILS = false;
	  
	  /** Opposite of {@link #FAILURE}.  */
	  public static final boolean SUCCESS = true;
	  /** Opposite of {@link #SUCCESS}.  */
	  public static final boolean FAILURE = false;

	  /** 
	   Useful for {@link String} operations, which return an index of <tt>-1</tt> when 
	   an item is not found. 
	  */
	  public static final int NOT_FOUND = -1;
	  
	  /** System property - <tt>line.separator</tt>*/
	  public static final String NEW_LINE = System.getProperty("line.separator");
	  /** System property - <tt>file.separator</tt>*/
	  public static final String FILE_SEPARATOR = System.getProperty("file.separator");
	  /** System property - <tt>path.separator</tt>*/
	  public static final String PATH_SEPARATOR = System.getProperty("path.separator");
	  
	  public static final String EMPTY_STRING = "";
	  public static final String SPACE = " ";
	  public static final String TAB = "\t";
	  public static final String SINGLE_QUOTE = "'";
	  public static final String PERIOD = ".";
	  public static final String DOUBLE_QUOTE = "\"";

	private Const() {
		// this prevents even the native class from
		// calling this ctor as well :
		throw new AssertionError();
	}

}
