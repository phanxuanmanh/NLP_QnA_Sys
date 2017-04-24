/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.classifier;

/**
 * The Class WekaDemo.
 */
public class WekaDemo {
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		SVMClassifier.writeDataToFile("TrainFile", "TestFile", 0.3);
		SVMClassifier.usingSVM("resources/weka/TrainFile.arff", "resources/weka/TestFile.arff");
		long end = System.currentTimeMillis();
		System.out.println("Time spent: " + (end-start));
	}
}
