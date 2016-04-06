package hcmuaf.nlp.core.classifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import weka.classifiers.Classifier;
import weka.classifiers.functions.SMO;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils.DataSource;

public class WekaDemo {
	public static void main(String[] args) throws Exception {
	/*	CSVLoader loader = new CSVLoader();
		loader.setSource(new File("Testfile.csv"));
		loader.setOptions(new String[] { "-H" });
		Instances data = loader.getDataSet();

		ArffSaver saver = new ArffSaver();
		saver.setInstances(data);
		saver.setFile(new File("Training.arff"));
		saver.writeBatch();*/

		BufferedReader reader = new BufferedReader(new FileReader("Training.arff"));
		Instances data = new Instances(reader);
		reader.close();
		// setting class attribute
		data.setClassIndex(data.numAttributes() - 1);
		data.setClassIndex(data.numAttributes() - 1);
		Classifier cModel = new SMO();
		cModel.buildClassifier(data);
	}
}
