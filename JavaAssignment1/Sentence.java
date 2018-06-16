package ass;

import java.util.Arrays;

public class Sentence {
	private String[] words;
	
	public static void main(String[] args) {
		  String[] wordList = {"A", "quick", "brown", "fox", "jumped", "over", "the", "lazy", "dog"};
		  Sentence text = new Sentence(wordList);
		  System.out.println(text);
		  System.out.println("Shortest word: " + text.shortest());
		  System.out.println("Longest word: " + text.longest());
		  System.out.printf("Mean word length:%5.2f\n",text.meanLength());
		  String[] sortedText = text.sorted();
		  System.out.print("Sorted: " + Arrays.toString(sortedText)); 
		}
	
	public Sentence(String[] words){
		this.words = words;
	}
	
	public String toString(){
		String sentenceString = "";
		for(String s : words){
			sentenceString += (s + " ");
		}
		return sentenceString;
	}
	
	public String shortest(){
		String shortestWord = words[0];
		int shortestLength = words[0].length();
		for(String s : words){
			if(s.length() < shortestLength){
				shortestWord = s;
				shortestLength = s.length();
			}
		}
		return shortestWord;
	}
	
	public String longest(){
		String longestWord = words[0];
		int longestLength = words[0].length();
		for(String s : words){
			if(s.length() > longestLength){
				longestWord = s;
				longestLength = s.length();
			}
		}
		return longestWord;
	}
	
	public double meanLength(){
		double average = 0;
		for(String s : words){
			average += s.length();
		}
		average = average / words.length;
		return average;
	}
	
	public String[] sorted(){
		String[] sortedArray = words;
		Arrays.sort(sortedArray);
		return sortedArray;
	}
}
