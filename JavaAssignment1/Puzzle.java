package ass;

import java.util.Random;
import java.util.Scanner;

public class Puzzle {

	public static final int N = 4;
	public static final int NUMBER_OF_ROTATIONS = 1;

	public static void main(String[] args) {
		int[][] puzzle = new int[N][N];
		reset(puzzle);
		test(puzzle);
		reset(puzzle);
		scramble(puzzle);
		System.out.println("### Testing puzzle game play\n");
		play(puzzle);
	}

	public static void print(int[][] puzzle) {
		for (int[] row : puzzle) {
			for (int elem : row) {
				System.out.printf("%4d", elem);
			}
			System.out.println();
		}
		System.out.println();
	}

	public static void test(int[][] puzzle) {
		System.out.println("### Testing reset method\n");
		print(puzzle);
		System.out.println("### Testing rotate methods\n");
		print(puzzle);
		for (int i = 0; i < N; i++) {
			System.out.println("### rotateColumn(" + i + ")\n");
			rotateColumn(puzzle, i);
			print(puzzle);
			System.out.println("### rotateRow(" + i + ")\n");
			rotateRow(puzzle, i);
			print(puzzle);
		}
		reset(puzzle); 
		System.out.println("### Testing random rotations\n");
		print(puzzle); 
		for (int i = 0; i < 5; i++){
			randomRotation(puzzle);
			print(puzzle); 
		}
	}

	public static void reset(int[][] puzzle) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++)
				puzzle[i][j] = i * N + j;
		}
	}

	public static void scramble(int[][] puzzle) {
		for (int i = 0; i < NUMBER_OF_ROTATIONS; i++) {
			randomRotation(puzzle);
		}
	}

	public static void rotateRow(int[][] arr, int row) {
		for(int i = 3; i > 0; i--){
			int temp = arr[row][i];
			arr[row][i] = arr[row][i - 1];
			arr[row][i - 1] = temp;
		}
	}

	public static void rotateColumn(int[][] arr, int column) {
		for(int i = 3; i > 0; i--){
			int temp = arr[i][column];
			arr[i][column] = arr[i - 1][column];
			arr[i - 1][column] = temp;
		}
	}

	public static void randomRotation(int[][] puzzle) {
		Random r = new Random();
		int rotationType = r.nextInt(2) + 1;
		int rotationValue = r.nextInt(4);
		if(rotationType == 1){
			rotateRow(puzzle, rotationValue);
		}
		else{
			rotateColumn(puzzle, rotationValue);
		}
	}
	
	public static boolean check(int[][] puzzle) {
		String checkLine = "0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 ";
		String puzzleLine = "";
		for (int[] row : puzzle) {
			for (int elem : row) {
				puzzleLine += (elem + " ");
			}
		}
		if(puzzleLine.equals(checkLine)){
			return true;
		}
		else{
			return false;
		}
	}
	
	static void play(int[][] puzzle) {
		Scanner scan = new Scanner(System.in);
		String userInput;
		
		boolean check = check(puzzle);
		check = check(puzzle);
		if(!check){
			print(puzzle);
			System.out.println("Input 'row' or 'col' followed by a number from 0 to 3!");
			userInput = scan.nextLine();
			String[] parts = userInput.split(" ");
			if(parts[0].equals("row")){
				int value = Integer.parseInt(parts[1]);
				if(value<0 || value>3){
					System.out.println("\nInvalid input!\n");
					System.out.println("\nNumber not in range 0-3!\n");
				}
				else{
					rotateRow(puzzle, value);
				}
			}
			else if(parts[0].equals("col")){
				int value = Integer.parseInt(parts[1]);
				if(value<0 || value>3){
					System.out.println("\nInvalid input!\n");
					System.out.println("\nNumber not in range 0-3!\n");
				}
				else{
					rotateColumn(puzzle, value);
				}
			}
			else{
				System.out.println("\nInvalid input!\n");
			}
			play(puzzle);
		}
		else{
			System.out.println("\nCongratulations! You've won!\n");		
		}
	}

}
