/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.classifier;

import hcmuaf.nlp.core.dao.QuestionDao;
import hcmuaf.nlp.core.dao.QuestionVectorDao;
import hcmuaf.nlp.core.hibernateDao.impl.QuestionDaoImpl;
import hcmuaf.nlp.core.hibernateDao.impl.QuestionVectorDaoImpl;
import hcmuaf.nlp.core.processing.SparseArffFileWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.SMO;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

/**
 * The Class SVMClassifier.
 */
public class SVMClassifier {
	
	/**
	 * Using svm.
	 *
	 * @param trainFile the train file
	 * @param testFile the test file
	 * @throws Exception the exception
	 */
	public static void usingSVM(String trainFile, String testFile)
			throws Exception {
		// build training set and training data
		BufferedReader reader = new BufferedReader(new FileReader(new File(trainFile)));
		Instances trainData = new Instances(reader);
		// Normalize data
		Normalize norm = new Normalize();
		norm.setInputFormat(trainData);
		Instances processed_train = Filter.useFilter(trainData, norm);
		reader.close();
		processed_train.setClassIndex(processed_train.numAttributes() - 1);
		Classifier cModel = new SMO();
		// build testing data and evaluation
		cModel.buildClassifier(processed_train);
		BufferedReader reader2 = new BufferedReader(new FileReader(new File(testFile)));
		Instances testData = new Instances(reader2);
		testData.setClassIndex(testData.numAttributes() - 1);
		reader2.close();
		Evaluation etest = new Evaluation(processed_train);
		etest.evaluateModel(cModel, testData);
		String strSummary = etest.toSummaryString();
		System.out.println(strSummary);
	}

	/**
	 * Write data to file.
	 *
	 * @param trainFile the train file
	 * @param testFile the test file
	 * @param percentToTrain the percent to train
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SQLException the SQL exception
	 */
	public static void writeDataToFile(String trainFile, String testFile,
			double percentToTrain) throws IOException, SQLException {
		QuestionDao questionDao = new QuestionDaoImpl();
		List<Long> listQuestion = questionDao.getAvailableQuestionList();
		int dataSize = listQuestion.size();
		int betweenIndex = (int) (dataSize * percentToTrain);
		List<Long> listTrainQuestion = questionDao.getAvailableQuestionList();
		List<Long> listTestQuestion = questionDao.getAvailableQuestionList();
		for (int i = 0; i < betweenIndex; i++) {
			listTrainQuestion.add(listQuestion.get(i));
		}
		for (int i = betweenIndex; i < dataSize; i++) {
			listTestQuestion.add(listQuestion.get(i));
		}
		QuestionVectorDao vectorDao = new QuestionVectorDaoImpl();
		List<Long> listKeyWord = vectorDao.getListKeyWord();
		Collections.sort(listKeyWord);
		SparseArffFileWriter fileWriter = new SparseArffFileWriter();
		try {
			fileWriter.writeFile(trainFile, listKeyWord, listTrainQuestion);
			fileWriter.writeFile(testFile, listKeyWord, listTestQuestion);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}
