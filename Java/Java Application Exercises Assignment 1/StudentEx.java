package ass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class StudentEx {

	private static ArrayList<Student> students = new ArrayList<>();
	
    public static void main(String[] args) throws IOException{    
		initStudents();
		Collections.sort(students);
		PrintWriter outFile = new PrintWriter(new FileWriter("RankedList.txt", false));
		for(Student student: students){
			outFile.printf("%-8d%-30s %n", student.studentId, student.studentName);
			outFile.printf("%-8s %-8.1f%-8s%-8.1f%-8s %-8.1f %n", "IR101" ,student.mark101, "IR102" ,student.mark102, "Aggregate" ,student.aggregateMark);
			outFile.printf("%-8s %-8s%-8s %-20s %n", "Class:" ,student.studentClass, "Outcome:" ,student.studentOutcome);
			outFile.println("----------------------------------------------------");
			System.out.printf("%-8d%-30s %n", student.studentId, student.studentName);
			System.out.printf("%-8s %-8.1f%-8s%-8.1f%-8s %-8.1f %n", "IR101" ,student.mark101, "IR102" ,student.mark102, "Aggregate" ,student.aggregateMark);
			System.out.printf("%-8s %-8s%-8s %-20s %n", "Class:" ,student.studentClass, "Outcome:" ,student.studentOutcome);
			System.out.println("----------------------------------------------------");
		}
		outFile.close();
	}
	
	
	private static void initStudents() throws IOException{
		String IRStudents = "F:/users/Alex/Documents/University/Java Assignment/Assignment/src/ass/data/IRStudents.csv";
		String IR101 = "F:/users/Alex/Documents/University/Java Assignment/Assignment/src/ass/data/IR101.csv";
		String IR102 = "F:/users/Alex/Documents/University/Java Assignment/Assignment/src/ass/data/IR101.csv";
		String name = "";
		String id = "";
		String mark101 = "";
		String mark102 = "";
		BufferedReader br = null;
		String line;
		ArrayList<String[]> dataSetA = new ArrayList<String[]>();
		ArrayList<String[]> dataSetB = new ArrayList<String[]>();
		ArrayList<String[]> dataSetC = new ArrayList<String[]>();
		ArrayList<String> idList = new ArrayList<String>();

		br = new BufferedReader(new FileReader(IRStudents));
		while ((line = br.readLine()) != null) {
			String[] splitLine = line.split(",");
			dataSetA.add(splitLine);
			idList.add(splitLine[0]);
		}
		
		br = new BufferedReader(new FileReader(IR101));
		while ((line = br.readLine()) != null) {
			String[] splitLine = line.split(",");
			dataSetB.add(splitLine);
		}

		br = new BufferedReader(new FileReader(IR102));
		while ((line = br.readLine()) != null) {
			String[] splitLine = line.split(",");
			dataSetC.add(splitLine);
		}

		for(String idTemp: idList){
			id = idTemp;
			for(int i = 0; i < idList.size(); i++){
				if(dataSetA.get(i)[0].equals(id)){
					name = dataSetA.get(i)[1];
				}
				if(dataSetB.get(i)[0].equals(id)){
					mark101 = dataSetB.get(i)[1];
				}
				if(dataSetC.get(i)[0].equals(id)){
					mark102 = dataSetC.get(i)[1];
				}
			}
        	students.add(new Student( Integer.parseInt(id), name,  Double.parseDouble(mark101),  Double.parseDouble(mark102)));
		}		
	}

}
