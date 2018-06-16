package ass;

public class Student implements Comparable<Student>{
	public String studentName;
	public String studentClass;
	public String studentOutcome;
	public int studentId;
	public double mark101;
	public double mark102;
	public double aggregateMark;
	
	@Override
	public int compareTo(Student studentB) {
	    if(this.aggregateMark < studentB.aggregateMark){
	    	return 1;
	    }
	    else if(this.aggregateMark == studentB.aggregateMark){
	    	return 0;
	    }
	    else{
	    	return -1;
	    }
	}
	
	public Student(int id, String name, double mark101, double mark102){
		this.studentId = id;
		this.studentName = name;
		this.mark101 = mark101;
		this.mark102 = mark102;
		this.aggregateMark = ( mark101 + mark102 ) / 2;
		
		if(mark101 < 40 && mark102 < 40){
			this.studentClass = "Fail";
			this.studentOutcome = "Repeat Year";		
		}
		else if(mark101 < 40 || mark102 < 40){
			if(mark101 < 40){
				this.studentClass = "Fail";
				this.studentOutcome = "Resit IR101";					
			}
			if(mark102 < 40){
				this.studentClass = "Fail";
				this.studentOutcome = "Resit IR102";					
			}
		}
		else if(aggregateMark >= 40 && aggregateMark < 50){
			this.studentClass = "3rd";
			this.studentOutcome = "Proceed to Stage 2";			
		}
		else if(aggregateMark >= 50 && aggregateMark < 60){
			this.studentClass = "2.2";
			this.studentOutcome = "Proceed to Stage 2";			
		}
		else if(aggregateMark >= 60 && aggregateMark < 70){
			this.studentClass = "2.1";
			this.studentOutcome = "Proceed to Stage 2";			
		}
		else if(aggregateMark >= 70 && aggregateMark <= 100){
			this.studentClass = "1st";
			this.studentOutcome = "Proceed to Stage 2";			
		}
	}
}
