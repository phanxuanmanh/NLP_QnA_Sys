import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;

public class UrlReader {
	public static QnA_Pair accessQnA(String questionUrl) throws IOException {
		URL url = new URL(questionUrl);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream()));
		String inputLine;
		StringBuffer sb = new StringBuffer();
		in.skip(12513);
		while ((inputLine = in.readLine()) != null) {
			if (inputLine.contains("<p class=\"f10blue\">Câu hỏi:")) {
				sb.append(inputLine);
				if (!inputLine
						.contains("<hr><p align=\"right\" class=\"f8blue\">S&#7889; l&#7847;n xem:")) {
					while ((inputLine = in.readLine()) != null) {
						sb.append(inputLine);
						if (inputLine
								.contains("<hr><p align=\"right\" class=\"f8blue\">S&#7889; l&#7847;n xem:"))
							break;
					}
				}else{
					break;
				}

			}

		}
		in.close();
		if (sb.indexOf("<p class=\"f10blue\">Câu hỏi:") != -1) {
			String questionHtml = sb.substring(
					sb.indexOf("<p class=\"f10blue\">Câu hỏi:")
							+ "<p class=\"f10blue\">Câu hỏi:".length(),
					sb.indexOf("</p></p><p class=\"f10blue\">Trả lời"));
			String answerHtml = sb
					.substring(
							sb.indexOf("</p></p><p class=\"f10blue\">Trả lời")
									+ "</p></p><p class=\"f10blue\">Trả lời"
											.length(),
							sb.indexOf("<hr><p align=\"right\" class=\"f8blue\">S&#7889; l&#7847;n xem:"));
			StringBuffer question = HtmlUtil.extractText(new StringReader(
					questionHtml));
			StringBuffer answer = HtmlUtil.extractText(new StringReader(
					answerHtml));
			QnA_Pair pair = new QnA_Pair(question.toString(), answer.toString());
			System.out.println(question);
			return pair;
		}
		return null;
	}

}
