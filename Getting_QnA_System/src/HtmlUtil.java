import javax.swing.text.html.parser.ParserDelegator;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;
import javax.swing.text.html.HTML.Tag;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import javax.swing.text.MutableAttributeSet;
public class HtmlUtil {
	 public HtmlUtil() {}

	  public static StringBuffer extractText(Reader reader) throws IOException {
	   final StringBuffer sb = new StringBuffer();

	    ParserDelegator parserDelegator = new ParserDelegator();
	    ParserCallback parserCallback = new ParserCallback() {
	      public void handleText(final char[] data, final int pos) {
	        sb.append(new String(data));
	      }
	      public void handleStartTag(Tag tag, MutableAttributeSet attribute, int pos) { }
	      public void handleEndTag(Tag t, final int pos) {  }
	      public void handleSimpleTag(Tag t, MutableAttributeSet a, final int pos) { }
	      public void handleComment(final char[] data, final int pos) { }
	      public void handleError(final java.lang.String errMsg, final int pos) { }
	    };
	    parserDelegator.parse(reader, parserCallback, true);
	    return sb;
	  }
}
