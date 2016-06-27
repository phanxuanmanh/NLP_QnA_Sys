/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.wiki;

/**
 * The Class WikiBaseTFIDFCalculatorGenerator.
 */
public class WikiBaseTFIDFCalculatorGenerator {
	
	/** The caculator. */
	private static WikiBaseTFIDFCalculator caculator;

	/**
	 * Builds the calculator.
	 *
	 * @return the wiki base tfidf calculator
	 */
	private static WikiBaseTFIDFCalculator buildCalculator() {
		caculator = new WikiBaseTFIDFCalculator();
		return caculator;
	}

	/**
	 * Gets the current calculator.
	 *
	 * @return the current calculator
	 */
	public static WikiBaseTFIDFCalculator getCurrentCalculator() {
		if (caculator == null) {
			caculator = buildCalculator();
		}
		return caculator;
	}
}
