package main.demo.classifier;
import weka.core.Instances;
import weka.experiment.InstanceQuery;
public class DemoSVM {

	public static void main(String[] args) {
		InstanceQuery query;
		try {
			query = new InstanceQuery();
			 query.setUsername("root");
			 query.setPassword("manh980838");
			 query.setQuery("select * from key_words");
			 // You can declare that your data set is sparse
			 // query.setSparseData(true);
			 Instances data = query.retrieveInstances();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
