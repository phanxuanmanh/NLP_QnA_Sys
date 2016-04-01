import java.io.FileNotFoundException;
import java.io.IOException;

public class GetRandom {
	public static ExcelWriter writer;

	public GetRandom() {
		writer = new ExcelWriter();
	}

	public void addQnA(int startNum, int endNum) {
		String url;
		for (int i = startNum; i < endNum; i++) {
			url = "http://pdt.hcmuaf.edu.vn/pdt-" + i
					+ "-3/vn/cautraloitructuyen.html";
			try {
				QnA_Pair pair = UrlReader.accessQnA(url);
				if (pair != null)
					writer.addQnA(pair);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public void writeToExcel(String fileName) {
		try {
			writer.writeToExcel(fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		GetRandom getRD = new GetRandom();
		getRD.addQnA(85000,100000);
		getRD.writeToExcel("QnA3");
	}
}
