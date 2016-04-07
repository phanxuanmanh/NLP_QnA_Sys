package hcmuaf.nlp.core.classifier;

import hcmuaf.nlp.core.DBConnect.QnAAccessor;
import hcmuaf.nlp.core.controller.ArffFileWriter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.SMO;
import weka.core.Instances;

public class WekaDemo {
	public static void main(String[] args) throws Exception {
		ArrayList<Integer> listQuestion = QnAAccessor.getQuestionList();
		int dataSize = listQuestion.size();
		int betweenIndex = (int) (dataSize * 0.6);
		int[] trainQues = new int[betweenIndex];
		int[] testQues = new int[dataSize - betweenIndex];
		for (int i = 0; i < betweenIndex; i++) {
			trainQues[i] = listQuestion.get(i);
		}
		for (int i = 0; i < dataSize - betweenIndex; i++) {
			testQues[i] = listQuestion.get(betweenIndex + i);
		}
		ArffFileWriter writer = new ArffFileWriter();
		writer.writeDataToFile("TrainFile", trainQues);
		writer.writeDataToFile("TestFile", testQues);
		// build training set and training data
		BufferedReader reader = new BufferedReader(new FileReader(
				"TrainFile.arff"));
		Instances trainData = new Instances(reader);
		reader.close();
		trainData.setClassIndex(trainData.numAttributes() - 1);
		Classifier cModel = new SMO();
		// build testing data and evaluation
		cModel.buildClassifier(trainData);
		BufferedReader reader2 = new BufferedReader(new FileReader(
				"TestFile.arff"));
		Instances testData = new Instances(reader2);
		testData.setClassIndex(testData.numAttributes()-1);
		Evaluation etest = new Evaluation(trainData);
		etest.evaluateModel(cModel, testData);
		String strSummary= etest.toSummaryString();
		System.out.println(strSummary);

	}
}
