import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;

public class UrlReaderDemo {
	public static QnA_Pair accessQnA(String questionUrl) throws IOException {
		URL url = new URL(questionUrl);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream()));
		String inputLine;
		StringBuffer sb = new StringBuffer();
		in.skip(12513);
		if ((inputLine = in.readLine()) != null) {
			System.out.println(inputLine);
			if (inputLine.contains("<p class=\"f10blue\">Câu hỏi:")) {
				System.out.println(sb.length());
			}else{
				sb.append(inputLine);
			}
			

		}
		in.close();
		return null;
	}

	public static void main(String[] args) {
		try {
			UrlReaderDemo.accessQnA("http://pdt.hcmuaf.edu.vn/pdt-109192-3/vn/cautraloitructuyen.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
