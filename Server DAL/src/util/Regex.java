package util;

import java.util.regex.Pattern;

public class Regex {
	 
	/*
	private static final String UNICODE_SPACES = "[" +
			    "\\u0009-\\u000d" + // # White_Space # Cc [5] <control-0009>..<control-000D>
			    "\\u0020" + // White_Space # Zs SPACE
			    "\\u0085" + // White_Space # Cc <control-0085>
			    "\\u00a0" + // White_Space # Zs NO-BREAK SPACE
			    "\\u1680" + // White_Space # Zs OGHAM SPACE MARK
			    "\\u180E" + // White_Space # Zs MONGOLIAN VOWEL SEPARATOR
			    "\\u2000-\\u200a" + // # White_Space # Zs [11] EN QUAD..HAIR SPACE
			    "\\u2028" + // White_Space # Zl LINE SEPARATOR
			    "\\u2029" + // White_Space # Zp PARAGRAPH SEPARATOR
			    "\\u202F" + // White_Space # Zs NARROW NO-BREAK SPACE
			    "\\u205F" + // White_Space # Zs MEDIUM MATHEMATICAL SPACE
			    "\\u3000" + // White_Space # Zs IDEOGRAPHIC SPACE
			  "]";
	*/
	  private static String LATIN_ACCENTS_CHARS = "\\u00c0-\\u00d6\\u00d8-\\u00f6\\u00f8-\\u00ff" + // Latin-1
	                                          "\\u0100-\\u024f" + // Latin Extended A and B
	                                          "\\u0253\\u0254\\u0256\\u0257\\u0259\\u025b\\u0263\\u0268\\u026f\\u0272\\u0289\\u028b" + // IPA Extensions
	                                          "\\u02bb" + // Hawaiian
	                                          "\\u0300-\\u036f" + // Combining diacritics
	                                          "\\u1e00-\\u1eff"; // Latin Extended Additional (mostly for Vietnamese)
	  private static final String HASHTAG_ALPHA_CHARS = "a-z" + LATIN_ACCENTS_CHARS +
	                                               "\\u0400-\\u04ff\\u0500-\\u0527" + // Cyrillic
	                                               "\\u2de0-\\u2dff\\ua640-\\ua69f" + // Cyrillic Extended A/B
	                                               "\\u0591-\\u05bf\\u05c1-\\u05c2\\u05c4-\\u05c5\\u05c7" +
	                                               "\\u05d0-\\u05ea\\u05f0-\\u05f4" + // Hebrew
	                                               "\\ufb1d-\\ufb28\\ufb2a-\\ufb36\\ufb38-\\ufb3c\\ufb3e\\ufb40-\\ufb41" +
	                                               "\\ufb43-\\ufb44\\ufb46-\\ufb4f" + // Hebrew Pres. Forms
	                                               "\\u0610-\\u061a\\u0620-\\u065f\\u066e-\\u06d3\\u06d5-\\u06dc" +
	                                               "\\u06de-\\u06e8\\u06ea-\\u06ef\\u06fa-\\u06fc\\u06ff" + // Arabic
	                                               "\\u0750-\\u077f\\u08a0\\u08a2-\\u08ac\\u08e4-\\u08fe" + // Arabic Supplement and Extended A
	                                               "\\ufb50-\\ufbb1\\ufbd3-\\ufd3d\\ufd50-\\ufd8f\\ufd92-\\ufdc7\\ufdf0-\\ufdfb" + // Pres. Forms A
	                                               "\\ufe70-\\ufe74\\ufe76-\\ufefc" + // Pres. Forms B
	                                               "\\u200c" + // Zero-Width Non-Joiner
	                                               "\\u0e01-\\u0e3a\\u0e40-\\u0e4e" + // Thai
	                                               "\\u1100-\\u11ff\\u3130-\\u3185\\uA960-\\uA97F\\uAC00-\\uD7AF\\uD7B0-\\uD7FF" + // Hangul (Korean)
	                                               "\\p{InHiragana}\\p{InKatakana}" + // Japanese Hiragana and Katakana
	                                               "\\p{InCJKUnifiedIdeographs}" + // Japanese Kanji / Chinese Han
	                                               "\\u3003\\u3005\\u303b" + // Kanji/Han iteration marks
	                                               "\\uff21-\\uff3a\\uff41-\\uff5a" + // full width Alphabet
	                                               "\\uff66-\\uff9f" + // half width Katakana
	                                               "\\uffa1-\\uffdc"; // half width Hangul (Korean)
	  private static final String HASHTAG_ALPHA_NUMERIC_CHARS = "0-9\\uff10-\\uff19_" + HASHTAG_ALPHA_CHARS;
	  private static final String HASHTAG_ALPHA = "[" + HASHTAG_ALPHA_CHARS +"]";
	  private static final String HASHTAG_ALPHA_NUMERIC = "[" + HASHTAG_ALPHA_NUMERIC_CHARS +"]";
	 
	  public static final Pattern INVALID_HASHTAG_MATCH_END = Pattern.compile("^(?:[#＃]|://)");
	  public static final Pattern VALID_HASHTAG = Pattern.compile("(^|[^&" + HASHTAG_ALPHA_NUMERIC_CHARS + "])(#|\uFF03)(" + HASHTAG_ALPHA_NUMERIC + "*" + HASHTAG_ALPHA + HASHTAG_ALPHA_NUMERIC + "*)", Pattern.CASE_INSENSITIVE);
	 
}
