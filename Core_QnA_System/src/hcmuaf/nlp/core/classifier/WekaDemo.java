package hcmuaf.nlp.core.classifier;


public class WekaDemo {
	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		SVMClassifier.writeDataToFile("TrainFile", "TestFile", 0.6);
		SVMClassifier.usingSVM("TrainFile", "TestFile");
		long end = System.currentTimeMillis();
		System.out.println("Time spent: " + (end-start));
	}
}
