package ass;

public class Cooling {

	public static final double FREEZER_TEMPERATURE = -20;
	public static final double K = 0.001;

	public static void main(String[] args) {
		temperatureTest(70, 0);
		temperatureTest(70, 60); 
		temperatureReport(70);
		timeToCoolTest(70, -10);
		timeToCoolTest(70, -20);
	}

	public static double temperature(double initialTemperature, int seconds) {
		double currentTemp = initialTemperature;
		double k = 0.001;
		int freezerTemp = -20;
		double tempDifference;
		for(int i = 0; i < seconds; i++){
			tempDifference = currentTemp - freezerTemp;
			currentTemp = currentTemp - ( k * tempDifference);
		}
		return currentTemp; 
	}

	public static void temperatureReport(double initialTemperature) {
		double temperature = initialTemperature;
		System.out.println("### Temperature Report");
		for(int minTime = 0; minTime <= 60; minTime += 10){
			int secTime = minTime * 60;
			temperature = temperature(temperature, secTime);
			System.out.printf("%2d mins: %5.1fC %n", minTime, temperature);
		}
		System.out.println();
	}
	
	public static int timeToCool(double initialTemperature, double targetTemperature) {
		if(targetTemperature <= -20){
			return -1;
		}
		double currentTemp = initialTemperature;
		double k = 0.001;
		int freezerTemp = -20;
		int timeTaken = 0;
		double tempDifference;
		for(int i = 0; currentTemp > targetTemperature; i++){
			timeTaken = i;
			tempDifference = currentTemp - freezerTemp;
			currentTemp = currentTemp - ( k * tempDifference);
		}
		return timeTaken; 
	}

	public static void timeToCoolTest(double initialTemperature, double targetTemperature) {
		System.out.println("### Time To Cool");
		System.out.println("Initial temperature = " + initialTemperature);
		System.out.println("Target temperature = " + targetTemperature);
		int timeTaken = timeToCool(initialTemperature, targetTemperature);
		System.out.println("Time to cool = " + timeTaken + " seconds\n");
	}

	public static void temperatureTest(double initialTemperature, int seconds) {
		System.out.println("### Temperature Test");
		System.out.println("Initial temperature = " + initialTemperature);
		System.out.println("Seconds = " + seconds);
		double temp = temperature(initialTemperature, seconds);
		System.out.println("Temperature = " + temp + "\n");
	}

}
