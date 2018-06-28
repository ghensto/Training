package com.gcit.lms.main;


import java.util.Scanner;


public class Outputs {
	@SuppressWarnings("resource")
	public static void mainMenu() {
		System.out.println("Welcome to the GCIT Library Management System. Which category of a user are you?");
		System.out.println("1)	Librarian");
		System.out.println("2)	Administrator");
		System.out.println("3)	Borrower");
		System.out.println("4)	Quit");
		
		Scanner kb = new Scanner(System.in);
		int choice = kb.nextInt();
		
		while(choice > 4 && choice < 1) {
			System.out.println("Enter a number between 1 and 4!");
			choice = kb.nextInt();
		}
		if (choice == 1) {
			LibrarianMenu.libMenu();
		}
		if (choice == 2) {
			AdminMenu.adminMenu();
		}
		if (choice == 3) {
			BorrowerMenu.borrowerMenu();
		}
		if (choice == 4) {
			System.exit(0);
		}
	}
}
	
	