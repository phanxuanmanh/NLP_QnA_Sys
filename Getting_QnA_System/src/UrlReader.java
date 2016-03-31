import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;

public class UrlReader {
	public static QnA_Pair accessQnA(String questionUrl) throws IOException {
		URL url = new URL(questionUrl);
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		String inputLine;
		StringBuffer sb = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			if (inputLine.contains("<p class=\"f10blue\">Câu hỏi:<p>")) {
				sb.append(inputLine);
				if (inputLine.contains("<hr><p align=\"right\" class=\"f8blue\">S&#7889; l&#7847;n xem:"))
					break;
				else {
					while ((inputLine = in.readLine()) != null) {
						sb.append(inputLine);
						if (inputLine.contains("<hr><p align=\"right\" class=\"f8blue\">S&#7889; l&#7847;n xem:"))
							break;
					}
					break;
				}
			}
		}
	
		String questionHtml = sb.substring(
				sb.indexOf("<p class=\"f10blue\">Câu hỏi:<p>") + "<p class=\"f10blue\">Câu hỏi:<p>".length(),
				sb.indexOf("</p></p><p class=\"f10blue\">Trả lời"));
		String answerHtml = sb.substring(
				sb.indexOf("</p></p><p class=\"f10blue\">Trả lời") + "</p></p><p class=\"f10blue\">Trả lời".length(),
				sb.indexOf("<hr><p align=\"right\" class=\"f8blue\">S&#7889; l&#7847;n xem:"));
		StringBuffer question = HtmlUtil.extractText(new StringReader(questionHtml));
		StringBuffer answer = HtmlUtil.extractText(new StringReader(answerHtml));
		QnA_Pair pair = new QnA_Pair(question.toString(), answer.toString());
		in.close();
		return pair;
	}

}
