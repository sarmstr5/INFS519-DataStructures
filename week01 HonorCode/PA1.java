/**
 * @author Shane Armstrong
 * INFS 519
 * Fall 2015
 */
 import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
/**
 * This class will be an interactive GMU CS Honor Code 
 * The user will be able to view various portions of the honor code using the cmd prompt
 */
public class PA1 {

	
	/**
	 * The main runs a majority of the code for the Interactive GMU CS Honor Code
	 * <p>
	 * The program asks for user input to select options 1-6.  It checks for user input in two places, first when the user inputs their choice to see if it is
	 * a numeric answer with error handling, then with a switch statement to see if the number value is within the bounds of the options.  The program loops until 
	 * the user chooses to exit the program.
	 * <p>
	 */
	public static void main(String [] args) throws Throwable{
		//print off first portion of the Honor Code and options

		System.out.println("CS Honor Code Policies "
				+ "CS Honor Code Policies\n"
				+ "(Source: https://cs.gmu.edu/wiki/pmwiki.php/HonorCode/CSHonorCodePolicies)\n\n"
				+ " All CS students must adhere to the GMU Honor Code. "
				+ "In addition to this honor code, the computer science department has further honor code policies regarding programming projects, "
				+ "which is detailed below. Your instructor may state further policies for his or her class as well. \n\n"
				+ "Unless otherwise stated, at the time that an assignment or project is given, all work handed in for credit is to be the result "
				+ "of individual effort. (In some classes group work is encouraged; if so, that will be made explicit when the assignment is given.)"
				+ "\n\n-----------------------------------------");
		
		boolean userQuit = false;
		int userChoice = 0;
		Scanner userScan = new Scanner(System.in);
		
		//Continues to ask for userInput until option 6 is chosen
		while(!userQuit){
			System.out.print("\n-----------------------------------------\n"
					+ "Select an option from the following menu:\n\n"
				+ "1) View contents: You (or your group, if a group assignment) may...\n\n"
				+ "2) View contents: You (or your group, if a group assignment) may not seek assistance from anyone else, other than your instructor or teaching assistant...\n\n"
				+ "3) View contents: Unless permission to do so is granted by the instructor, you (or your group, if a group assignment) may not...\n\n"
				+ "4) View contents: You must...\n\n"
				+ "5) Print the honor code to output.txt\n\n"
				+ "6) Quit\n"
				+ "--------------------------------------------------------\n\n");
			System.out.print("What do you want to do?-> ");
			
			// user input is requested then checked to see if it is a numeric value.
			// if it acceptable calls method printUserChoice
			String userInput = userScan.next();
			try{
				userChoice = Integer.parseInt(userInput.toString());
				userQuit = printUserChoice(userChoice);
				}
			catch(Exception e){
				System.out.println("\nThat is not a valid selection\n");
			}
		}//while
	userScan.close();	
	}//main
	
	/**
	 * Prints portions of CS Honor code to standard output, the honor code to a text file, or quits based on userinput
	 * @param userChoice The user's input as to what they want the program to do
	 * @return Returns true or false based on if the user wishes to continue to use the program e.g. true unless the user chooses to quit
	 */
	public static boolean printUserChoice(int userChoice){
		boolean userQuit= false;
		switch(userChoice){
		case 1: userChoice = 1;
			System.out.println("\n-----------------------------------------\n"
					+ "You or your group, if a group assignment) may:\n"
					+ "- seek assistance in learning to use the computing facilities;\n"
					+ "- seek assistance in learning to use special features of a programming language's implementation;\n"
					+ "- seek assistance in determining the syntactic correctness of a particular programming language statement or construct;\n"
					+ "- seek an explanation of a particular syntactic error;\n"
					+ "- seek explanations of compilation or run-time error messages;\n");
			break;
		case 2: userChoice = 2;
			System.out.println("\n-----------------------------------------\n"
					+ "You (or your group, if a group assignment) may not "
					+ "- seek assistance from anyone else, other than your instructor or teaching assistant:\n"
					+ "- in designing the data structures used in your solution to a problem;\n"
					+ "- in designing the algorithm to solve a problem;\n"
					+ "- in modifying the design of an algorithm determined to be faulty;\n"
					+ "- in implementing your algorithm in a programming language;\n"
					+ "- in correcting a faulty implementation of your algorithm;\n"
					+ "- in determining the semantic correctness of your algorithm; \n");
			break;
			
		case 3: userChoice = 3;
			System.out.println("\n-----------------------------------------\n"
					+ "Unless permission to do so is granted by the instructor, you (or your group, if a group assignment) may not\n "
					+ "- give a copy of your work in any form to another student;\n"
					+ "- receive a copy of someone else's work in any form;\n"
					+ "- attempt to gain access to any files other than your own or those authorized by the instructor or computer center;\n"
					+ "- inspect or retain in your possession another student's work, whether it was given to you by another student, it was found after other student has discarded his/her work, or it accidently came into your possession; \n"
					+ "- in any way collaborate with someone else in the design or implementation or logical revision of an algorithm;\n"
					+ "- present as your own, any algorithmic procedure which is not of your own or of the instructor's design, or which is not part of the course's required reading (if you modify any procedure which is presented in the course's texts but which is not specifically mentioned in class or covered in reading assignments, then a citation with page number must be given);\n"
					+ "- incorporate code written by others (such as can be found on the Internet);\n");
			break;
			
		case 4: userChoice = 4;
			System.out.println("\n-----------------------------------------\n"
					+ "You must:\n"
					+ "- report any violations of II and III that you become aware of;\n"
					+ "- if part of a group assignment, be an equal 'partner' in your group's activities and productions, and represent accurately the level of your participation in your group's activities and productions;\n");
			break;
			
		case 5: userChoice = 5;
			printToText();
			System.out.println("The honor code has been printed to output.txt");
			break;
			
		case 6: userChoice = 6;
			System.out.println("Goodbye!");
			userQuit=true;
			break;
			
		default:
			System.out.println("Choose a number between 1-6, thank you");
			break;
}//switch
		return userQuit;
		
	}//method
	
	/**
	 * Prints the honor code to a text file
	 */
	public static void printToText(){
		PrintWriter honorCodeText = null;
		try {
			honorCodeText = new PrintWriter("output.txt");
			honorCodeText.print("CS Honor Code Policies"
					+ "CS Honor Code Policies\n"
					+ "(Source: https://cs.gmu.edu/wiki/pmwiki.php/HonorCode/CSHonorCodePolicies)\n\n"
					+ " All CS students must adhere to the GMU Honor Code. "
					+ "In addition to this honor code, the computer science department has further honor code policies regarding programming projects, "
					+ "which is detailed below. Your instructor may state further policies for his or her class as well. \n\n"
					+ "Unless otherwise stated, at the time that an assignment or project is given, all work handed in for credit is to be the result "
					+ "of individual effort. (In some classes group work is encouraged; if so, that will be made explicit when the assignment is given.\n\n"
					+ "You or your group, if a group assignment) may:\n"
					+ "- seek assistance in learning to use the computing facilities;\n"
					+ "- seek assistance in learning to use special features of a programming language's implementation;\n"
					+ "- seek assistance in determining the syntactic correctness of a particular programming language statement or construct;\n"
					+ "- seek an explanation of a particular syntactic error;\n"
					+ "- seek explanations of compilation or run-time error messages;\n\n"
					+ "You (or your group, if a group assignment) may not seek assistance from anyone else, other than your instructor or teaching assistant:\n"
					+ "- in designing the data structures used in your solution to a problem;\n"
					+ "- in designing the algorithm to solve a problem;\n"
					+ "- in modifying the design of an algorithm determined to be faulty;\n"
					+ "- in implementing your algorithm in a programming language;\n"
					+ "- in correcting a faulty implementation of your algorithm\n"
					+ "- in determining the semantic correctness of your algorithm.\n\n"
					+ "Unless permission to do so is granted by the instructor, you (or your group, if a group assignment) may not\n "
					+ "- give a copy of your work in any form to another student;\n"
					+ "- receive a copy of someone else's work in any form;\n"
					+ "- attempt to gain access to any files other than your own or those authorized by the instructor or computer center;\n"
					+ "- inspect or retain in your possession another student's work, whether it was given to you by another student, it was found after other student has discarded his/her work, or it accidently came into your possession; \n"
					+ "- in any way collaborate with someone else in the design or implementation or logical revision of an algorithm;\n"
					+ "- present as your own, any algorithmic procedure which is not of your own or of the instructor's design, or which is not part of the course's required reading (if you modify any procedure which is presented in the course's texts but which is not specifically mentioned in class or covered in reading assignments, then a citation with page number must be given);\n"
					+ "- incorporate code written by others (such as can be found on the Internet);\n\n"
					+ "You must:\n"
					+ "- report any violations of II and III that you become aware of;\n"
					+ "- if part of a group assignment, be an equal 'partner' in your group's activities and productions, and represent accurately the level of your participation in your group's activities and productions.\n");
			
		}//try
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		finally{
			honorCodeText.close();
		}
	}//method
}//class


