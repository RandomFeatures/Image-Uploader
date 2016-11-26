package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;


public class Extractor {
	public static List<String> extratHashtags(String text) {
		if (text == null || text.isEmpty()) {
			return Collections.emptyList();
		}

		// Performance optimization.
		// If text doesn't contain #/＃ at all, text doesn't contain
		// hashtag, so we can simply return an empty list.
		boolean found = false;
		for (char c : text.toCharArray()) {
			if (c == '#' || c == '＃') {
				found = true;
				break;
			}
		}
		if (!found) {
			return Collections.emptyList();
		}

		List<String> extracted = new ArrayList<String>();
		Matcher matcher = Regex.VALID_HASHTAG.matcher(text);

		while (matcher.find()) {
			String after = text.substring(matcher.end());
			if (!Regex.INVALID_HASHTAG_MATCH_END.matcher(after).find()) {
				extracted.add(matcher.group());
			}
		}

		return extracted;
	}
	
}
