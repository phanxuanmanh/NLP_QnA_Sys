package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CmdTest {
public static void main(String[] args) {
	try {
		 // Run Windows command
		Runtime rt= Runtime.getRuntime();
		Process process1=rt.exec("ipconfig");

		Process process = rt.exec("netstat -nao");
		
		

        // Get input streams
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process1.getInputStream()));
        BufferedReader stdInput2 = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(process1.getErrorStream()));

        // Read command standard output
        String s;
        System.out.println("Standard output: ");
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }

        String s2;
        System.out.println("Standard output: ");
        while ((s2 = stdInput2.readLine()) != null) {
            System.out.println(s2);
        }
        // Read command errors
        System.out.println("Standard error: ");
        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
