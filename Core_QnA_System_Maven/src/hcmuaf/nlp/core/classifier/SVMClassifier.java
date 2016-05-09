package hcmuaf.nlp.core.classifier;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import hcmuaf.nlp.core.controller.ArffFileWriter;
import hcmuaf.nlp.core.dao.QuestionDao;
import hcmuaf.nlp.core.jdbcDao.impl.QuestionDaoImpl;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.SMO;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

public class SVMClassifier {
	public static void usingSVM(String trainFile, String testFile) throws Exception {
		// build training set and training data
		BufferedReader reader = new BufferedReader(new FileReader(trainFile + ".arff"));
		Instances trainData = new Instances(reader);
		//Normalize data
		Normalize norm = new Normalize();
		norm.setInputFormat(trainData);
		Instances processed_train = Filter.useFilter(trainData, norm);
		reader.close();
		processed_train.setClassIndex(processed_train.numAttributes() - 1);
		Classifier cModel = new SMO();
		// build testing data and evaluation
		cModel.buildClassifier(processed_train);
		BufferedReader reader2 = new BufferedReader(new FileReader(testFile + ".arff"));
		Instances testData = new Instances(reader2);
		testData.setClassIndex(testData.numAttributes() - 1);
		reader2.close();
		Evaluation etest = new Evaluation(processed_train);
		etest.evaluateModel(cModel, testData);
		String strSummary = etest.toSummaryString();
		System.out.println(strSummary);
	}
	public static void writeDataToFile(String trainFile, String testFile, double percentToTrain) throws IOException, SQLException{
		QuestionDao questionDao = new QuestionDaoImpl();
		ArrayList<Integer> listQuestion = questionDao.getQuestionList();
		int dataSize = listQuestion.size();
		int betweenIndex = (int) (dataSize * percentToTrain);
		int[] trainQues = new int[betweenIndex];
		int[] testQues = new int[dataSize - betweenIndex];
		for (int i = 0; i < betweenIndex; i++) {
			trainQues[i] = listQuestion.get(i);
		}
		for (int i = 0; i < dataSize - betweenIndex; i++) {
			testQues[i] = listQuestion.get(betweenIndex + i);
		}
		ArffFileWriter writer = new ArffFileWriter();
		writer.writeDataToFile(trainFile, trainQues);
		writer.writeDataToFile(testFile, testQues);
	}
}
