/**
 * 
 */
package com.gcit.lms.main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Branch;
import com.gcit.lms.service.LibrarianService;

/**
 * @author therence
 *
 */
public class LibrarianMenu {

	@SuppressWarnings("resource")
	public static void libMenu() {
		// TODO Auto-generated method stub
		System.out.println("Welcome Librarian");
		System.out.println("1)	Enter branch you manage");
		System.out.println("2)	Previous Menu");
		System.out.println("3)	Quit");
		
		Scanner kb = new Scanner(System.in);
		int choice = kb.nextInt();
		
		while(choice > 3 && choice < 1) {
			System.out.println("Enter a number between 1 and 3!");
			choice = kb.nextInt();
		}
		if (choice == 1) {
			branchManaged();
		}
		if (choice == 2) {
			Outputs.mainMenu();
		}
		if (choice == 3) {
			System.exit(0);
		}
	}

	@SuppressWarnings("resource")
	private static void branchManaged() {
		// TODO Auto-generated method stub
		System.out.println("Pick a branch");
		LibrarianService LibrarianService = new LibrarianService();
		Branch branch = new Branch();
		List<Branch> branches = new ArrayList<>();
		int count = 1;
		try {
			branches = LibrarianService.readBranch(branch);
			for(Branch a : branches) {
	            System.out.println(count + ")	" + a.getBranchName());
	            count++;
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(count + ")	Quit to previous ");
		Scanner kb = new Scanner(System.in);
		int choice = kb.nextInt();
		
		while(choice < 1 && choice > count) {
			System.out.println("Enter a number between 1 and "+ count);
			choice = kb.nextInt();
		}
		if(choice == count) {
			libMenu();
		}
		else {
			Branch bchoice = branches.get(choice-1);
			specifyBranch(bchoice);
		}
		
		
	}
	
	@SuppressWarnings("resource")
	private static void specifyBranch(Branch bchoice) {
		// TODO Auto-generated method stub
		System.out.println("Welcome to " + bchoice.getBranchName());
		System.out.println("1)	Update the details of the Library ");
		System.out.println("2)	Add copies of Book to the Branch");
		System.out.println("3)	Quit to previous ");
		Scanner kb = new Scanner(System.in);
		int choice = kb.nextInt();
		while(choice < 1 && choice > 3) {
			System.out.println("Enter a number between 1-3");
			choice = kb.nextInt();
		}
		if(choice == 1) {
			updateLibBranch(bchoice);
		}
		if(choice == 2) {
			addcopies(bchoice);
		}
		if(choice == 3) {
			branchManaged();
		}
	}

	@SuppressWarnings("resource")
	private static void updateLibBranch(Branch bchoice) {
		// TODO Auto-generated method stub
		System.out.println("You have chosen to update the Branch with Branch Id:"+ bchoice.getBranchId() + 
				"and Branch Name: "+ bchoice.getBranchName());
		System.out.println("Enter ‘quit’ at any prompt to cancel operation.");
		Scanner kb = new Scanner(System.in);
		String choice;
		
		System.out.println("Please enter new branch name or enter N/A for no change:");
		choice = kb.nextLine();
		if("quit".equalsIgnoreCase(choice)) {
			System.out.println("Nothing changed");
			specifyBranch(bchoice);
		}
		if(!"N/A".equalsIgnoreCase(choice)) {
			bchoice.setBranchName(choice);
		}
		
		System.out.println("Please enter new branch address or enter N/A for no change:");
		choice = kb.nextLine();
		if("quit".equalsIgnoreCase(choice)) {
			System.out.println("Nothing changed");
			specifyBranch(bchoice);
		}
		if(!"N/A".equalsIgnoreCase(choice)) {
			bchoice.setBranchAddress(choice);
		}
		
		LibrarianService LibrarianService = new LibrarianService();
		try {
			LibrarianService.saveBranch(bchoice);
			System.out.println("Branch Details Updated");
			specifyBranch(bchoice);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@SuppressWarnings("resource")
	private static void addcopies(Branch bchoice) {
		// TODO Auto-generated method stub
		System.out.println("Pick the Book you want to add copies of, to your branch:");
		Book book = new Book();
		List<Book> books = new ArrayList<>();
		LibrarianService LibrarianService = new LibrarianService();
		int count = 1;
		try {
			books = LibrarianService.readNoOfCopies(book, bchoice);
			for(Book b : books) {
	            System.out.println(count + ")	" + b.getBookTitle());
	            count++;
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(count + ")	Previous Menu");
		Scanner kb = new Scanner(System.in);
		int choice = kb.nextInt();
		while(choice < 1 && choice > count) {
			System.out.println("Enter a number between 1 and " + count);
			choice = kb.nextInt();
		}
		if(choice == (count)) {
			specifyBranch(bchoice);
		}
		System.out.println("Existing number of copies: " + books.get(choice -1).getNoOfCopies());
		
		System.out.print("Enter new number of copies: ");
		int number = kb.nextInt();
		try {
			LibrarianService.editCopies(number, books.get(choice-1).getBookId(), bchoice.getBranchId());
			System.out.println("Number of copies changed");
			specifyBranch(bchoice);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

}
