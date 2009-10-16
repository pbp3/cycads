/*
 * Created on 13/10/2009
 */
package org.cycads.parser.association;

public class Tools
{
	public static String cleanTextDelimiter(String text, String delimiter) {
		if (text == null || text.length() == 0 || delimiter == null || delimiter.length() == 0) {
			return text;
		}
		int start = 0, end = text.length();
		if (text.startsWith(delimiter)) {
			start = delimiter.length();
		}
		if (text.endsWith(delimiter)) {
			end = end - delimiter.length();
		}
		return text.substring(start, end);
	}

	public static String[] split(String value, String separator) {
		if (value == null) {
			return null;
		}
		if (separator == null || separator.length() == 0) {
			return new String[] {value};
		}
		else {
			return value.split(separator);
		}
	}
}
