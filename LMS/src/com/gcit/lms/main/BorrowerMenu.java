package com.gcit.lms.main;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Branch;
import com.gcit.lms.service.BorrowerService;

public class BorrowerMenu {
	@SuppressWarnings("resource")
	public static void borrowerMenu() {
		// TODO Auto-generated method stub
		System.out.println("Enter your card number or 0 to return to previous Menu:");
		Scanner kb = new Scanner(System.in);
		int cardNo = kb.nextInt();
		if(cardNo == 0) {
			Outputs.mainMenu();
		}
		Borrower borrower = new Borrower();
		borrower.setBorrowerId(cardNo);
		BorrowerService BorrowerService = new BorrowerService();
		List<Borrower> borrowers = new ArrayList<>();
		try {
			borrowers = BorrowerService.readBorrower(borrower);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		borrower = borrowers.get(0);
		borrowerChoice(borrower);
		
	}

	@SuppressWarnings("resource")
	private static void borrowerChoice(Borrower borrower) {
		// TODO Auto-generated method stub
		System.out.println("1)	Check out a book");
		System.out.println("2)	Return a Book");
		System.out.println("3)	Quit to Previous ");
		Scanner kb = new Scanner(System.in);
		int choice = kb.nextInt();
		while(choice < 1 && choice > 3) {
			System.out.println("Enter a number between 1 and 3");
			choice = kb.nextInt();
		}
		if (choice == 1) {
			borrowerNext(borrower);
		}
		if (choice == 2) {
			borrowerReturn(borrower);
		}
		if(choice == 3) {
			borrowerMenu();
		}
		
	}
	
	@SuppressWarnings("resource")
	private static void borrowerNext(Borrower borrower) {
		// TODO Auto-generated method stub
		System.out.println("Welcome " + borrower.getBorrowerName());
		System.out.println("Pick a branch");
		BorrowerService BorrowerService = new BorrowerService();
		Branch branch = new Branch();
		List<Branch> branches = new ArrayList<>();
		int count = 1;
		try {
			branches = BorrowerService.readBranch(branch);
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
			borrowerChoice(borrower);
		}
		else {
			Branch bchoice = branches.get(choice-1);
			booksToborrow(bchoice, borrower);
		}
		
	}

	@SuppressWarnings("resource")
	private static void booksToborrow(Branch bchoice, Borrower borrower) {
		// TODO Auto-generated method stub
		System.out.println("Welcome " + borrower.getBorrowerName());
		System.out.println("Pick the Book you want to borrow");
		Book book = new Book();
		List<Book> books = new ArrayList<>();
		BorrowerService BorrowerService = new BorrowerService();
		int count = 1;
		try {
			books = BorrowerService.readNoOfCopiesb(book, bchoice);
			for(Book b : books) {
	            System.out.println(count + ")	" + b.getBookTitle());
	            count++;
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(count + ")	Quit to previous" );
		Scanner kb = new Scanner(System.in);
		int choice = kb.nextInt();
		while(choice < 1 && choice > count) {
			System.out.println("Enter a number between 1 and " + count);
			choice = kb.nextInt();
		}
		if(choice == count) {
			borrowerNext(borrower);
		}
		
		LocalDate today = LocalDate.now();
		LocalDate week = today.plusWeeks(1);
		Date stoday = Date.valueOf(today);
		Date sweek = Date.valueOf(week);
		
		try {
			BorrowerService.bookLoan(books.get(choice-1).getBookId(), bchoice.getBranchId(), borrower.getBorrowerId(), 
					stoday, sweek, null);
			BorrowerService.editCopies((books.get(choice-1).getNoOfCopies() -1), books.get(choice-1).getBookId(), bchoice.getBranchId());
			System.out.println(borrower.getBorrowerName() + ", you have checked out " + books.get(choice-1).getBookTitle() + " and the due"
					+ "date is " + sweek);
			System.out.println("Bye");
			borrowerNext(borrower);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings("resource")
	private static void borrowerReturn(Borrower borrower) {
		// TODO Auto-generated method stub
		System.out.println("Welcome " + borrower.getBorrowerName());
		System.out.println("Pick a branch");
		BorrowerService BorrowerService = new BorrowerService();
		Branch branch = new Branch();
		List<Branch> branches = new ArrayList<>();
		int count = 1;
		try {
			branches = BorrowerService.readBranch(branch);
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
			borrowerChoice(borrower);
		}
		else {
			Branch bchoice = branches.get(choice-1);
			booksToReturn(bchoice, borrower);
		}
	}
	
	@SuppressWarnings("resource")
	private static void booksToReturn(Branch bchoice, Borrower borrower) {
		// TODO Auto-generated method stub
		System.out.println("Pick the book you want to return");
		BorrowerService BorrowerService = new BorrowerService();
		List<Book> books = new ArrayList<>();
		int count = 1;
		try {
			System.out.println(borrower.getBorrowerId()+ "  " +  bchoice.getBranchId());
			books = BorrowerService.bookReturn(borrower.getBorrowerId(), bchoice.getBranchId());
			for(Book b: books) {
				System.out.println(count + ")	" + b.getBookTitle() + "\tDate Out" + b.getDateOut() + "\tDue Date" + b.getDateDue());
	            count++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(count + ")	Quit to previous" );
		Scanner kb = new Scanner(System.in);
		int choice = kb.nextInt();
		while(choice < 1 && choice > count) {
			System.out.println("Enter a number between 1 and " + count);
			choice = kb.nextInt();
		}
		if(choice == count) {
			borrowerReturn(borrower);
		}
		LocalDate today = LocalDate.now();
		Date stoday = Date.valueOf(today);
		int copy = 0;
		try {
			copy = BorrowerService.readNoOfCopiesb(books.get(choice-1), bchoice).get(0).getNoOfCopies() + 1;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			BorrowerService.editCopies(copy, books.get(choice-1).getBookId(), bchoice.getBranchId());
			BorrowerService.bookLoan(books.get(choice-1).getBookId(), bchoice.getBranchId(), borrower.getBorrowerId(), null, null, stoday);
			System.out.println("Book returned");
			System.out.println("Bye");
			borrowerReturn(borrower);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
