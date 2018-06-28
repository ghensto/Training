/**
 * 
 */
package com.gcit.lms.main;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Branch;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.Publisher;
import com.gcit.lms.service.AdminService;

/**
 * @author therence
 *
 */
public class AdminMenu {
	
	@SuppressWarnings("resource")
	public static void adminMenu() {
		// TODO Auto-generated method stub
		System.out.println("Welcome Administartor");
		System.out.println("1)	Add/Update/Delete/Read Author");
		System.out.println("2)	Add/Update/Delete/Read Book");
		System.out.println("3)	Add/Update/Delete/Read Publisher");
		System.out.println("4)	Add/Update/Delete/Read Library Branches");
		System.out.println("5)	Add/Update/Delete/Read Borrowers");
		System.out.println("6)	Override Due Date for a Book Loan");
		System.out.println("7)	Quit To previous Menu");
		
		Scanner kb = new Scanner(System.in);
		int choice = kb.nextInt();
		
		while(choice > 4 && choice < 1) {
			System.out.println("Enter a number between 1 and 4!");
			choice = kb.nextInt();
		}
		if (choice == 1) {
			authorMenu();
		}
		if (choice == 2) {
			bookMenu();
		}
		if (choice == 3) {
			publisherMenu();
		}
		if (choice == 4) {
			libBranchesMenu();
		}
		if (choice == 5) {
			borrowersMenu();
		}
		if (choice == 6) {
			overrideMenu();
		}
		if (choice == 7) {
			Outputs.mainMenu();
		}
	}
	
	@SuppressWarnings("resource")
	private static void overrideMenu() {
		// TODO Auto-generated method stub
		System.out.println("Choose the borrower whose due date you want to change or 0 to return to previous menu");
		List<Book> books = new ArrayList<>();
		AdminService adminService = new AdminService();
		int choice;
		Scanner kb = new Scanner(System.in);
		try {
			books = adminService.borrowers(null, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String format = "|%1$-3s|%2$-25s|%3$-25s|%4$-25s|%5$-25s|%6$-25s\n";
		System.out.format(format, "  ", "title", "name", "Due Date", "Date Out", "Date In"); 
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		String due;
		String out;
		String in;
		int count = 1;
		for(Book b : books) {
			if (b.getDateDue() == null) {
				due = "";
			}
			else {
				due = df.format(b.getDateDue());
			}
			if (b.getDateOut() == null) {
				out = "";
			}
			else {
				out = df.format(b.getDateOut());
			}
			if (b.getDateIn() == null) {
				in = "";
			}
			else {
				in = df.format(b.getDateIn());
			}
			String pass[] = { Integer.toString(count), b.getBookTitle(), b.getBorrowerName(), due, out, in};
			System.out.format(String.format(format, (Object[]) pass));
			count ++;
		}
		choice = kb.nextInt();
		if(choice == 0) {
			adminMenu();
		}
		Book book = new Book();
		book = books.get(choice -1);
		System.out.println("Enter the new due date using the format MM/dd/yyyy");
		String dd = kb.nextLine();
		dd = kb.nextLine();
		SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date date = null;
		try {
			date = f.parse(dd);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		java.sql.Date sqlDate = new java.sql.Date(date.getTime()); 
		book.setDateDue(sqlDate);
		try {
			adminService.changeDueDate(book);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Date changed");
		adminMenu();
	}

	@SuppressWarnings("resource")
	private static void authorMenu() {
		// TODO Auto-generated method stub
		System.out.println("1)	Add Authors");
		System.out.println("2)	Update Authors");
		System.out.println("3)	Delete Authors");
		System.out.println("4)	Read Authors");
		System.out.println("5)	Quit to Previous menu");
		Scanner kb = new Scanner(System.in);
		int choice = kb.nextInt();
		
		while(choice > 4 && choice < 1) {
			System.out.println("Enter a number between 1 and 3!");
			choice = kb.nextInt();
		}
		if(choice == 1) {
			addingA();
		}
		if(choice == 2) {
			updatingA();
		}
		if(choice == 3) {
			DeletingA();
		}
		if(choice == 4) {
			ReadingA();
		}
		if(choice == 5) {
			adminMenu();;
		}
		
	}

	@SuppressWarnings("resource")
	private static void addingA() {
		// TODO Auto-generated method stub
		System.out.println("Enter author name you wish to add");
		Author author = new Author();
		AdminService adminService = new AdminService();
		Scanner kb = new Scanner(System.in);
		author.setAuthorName(kb.nextLine());
		List<Book> books = new ArrayList<>();
		int count = 1;
		try {
			adminService.saveAuthor(author);
			System.out.println("Author added!");
			author = adminService.readAuthor(author).get(0);
			// authorMenu();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Choose or Add a book related to this author");
		Book book = new Book();
		try {
			books = adminService.readBook(book);
			for (Book b : books) {
				System.out.println(count + ")	" + b.getBookTitle());
				count++;
			}
			System.out.println(count + ")	Return to previous Menu");
			count++;
			System.out.println(count + ")	Add New Book");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int choice = kb.nextInt();
		if (choice == (count - 1)) {
			authorMenu();
		}
		if (choice == count) {
			addingBook(author);
		} else {
			book.setBookTitle(books.get(choice - 1).getBookTitle());
			book.setPubId(books.get(choice - 1).getPubId());
			try {
				adminService.saveBook(book);
				book = adminService.readBook(book).get(0);
				adminService.bookAuthors(author, book);
				System.out.println("New author Added");
				authorMenu();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	@SuppressWarnings("resource")
	private static void updatingA() {
		// TODO Auto-generated method stub
		System.out.println("Pick author to edit");
		AdminService adminService = new AdminService();
		Author author = new Author();
		List<Author> authors = new ArrayList<>();
		int count = 1;
		try {
			authors = adminService.readAuthor(author);
			for(Author a : authors) {
	            System.out.println(count + ")	" + a.getAuthorName());
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
			authorMenu();
		}
		author = authors.get(choice-1);
		System.out.println("Enter new Author name");
		author.setAuthorName(kb.nextLine());
		author.setAuthorName(kb.nextLine());
		try {
			adminService.saveAuthor(author);
			System.out.println("Author updated!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Choose or Add a book related to this author");
		List<Book> books = new ArrayList<>();
		count = 1;
		Book book = new Book();
		try {
			books = adminService.readBook(book);
			for(Book b : books) {
				System.out.println(count + ")	" + b.getBookTitle());
		        count++;
		     }
			System.out.println(count + ")	Return to previous Menu");
			count++;
			System.out.println(count + ")	Add New Book");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		choice = kb.nextInt();
		if(choice == (count -1)) {
			authorMenu();
		}
		if(choice == count) {
			addingBook(author);
		}
		else {
			book.setBookTitle(books.get(choice-1).getBookTitle());
			book.setPubId(books.get(choice-1).getPubId());
			try {
				adminService.bookAuthors(author, book);
				System.out.println("New author Added");
				authorMenu();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("resource")
	private static void DeletingA() {
		// TODO Auto-generated method stub
		System.out.println("Pick author to Delete");
		AdminService adminService = new AdminService();
		Author author = new Author();
		List<Author> authors = new ArrayList<>();
		int count = 1;
		try {
			authors = adminService.readAuthor(author);
			for(Author a : authors) {
	            System.out.println(count + ")	" + a.getAuthorName());
	            count++;
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		count++;
		System.out.println(count + ")	Quit to previous ");
		Scanner kb = new Scanner(System.in);
		int choice = kb.nextInt();
		
		while(choice < 1 && choice > count) {
			System.out.println("Enter a number between 1 and "+ count);
			choice = kb.nextInt();
		}
		if(choice == count) {
			authorMenu();
		}
		author = authors.get(choice-1);
		author.setAuthorName(null);
		try {
			adminService.saveAuthor(author);
			System.out.println("Author Deleted!");
			authorMenu();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("resource")
	private static void ReadingA() {
		// TODO Auto-generated method stub
		AdminService adminService = new AdminService();
		Author author = new Author();
		List<Author> authors = new ArrayList<>();
		int count = 1;
		try {
			authors = adminService.readAuthor(author);
			for(Author a : authors) {
	            System.out.println(count + ")	" + a.getAuthorName());
	            count++;
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(count + ")	Quit to previous ");
		Scanner kb = new Scanner(System.in);
		int choice = kb.nextInt();
		
		while(choice < 1 && choice != count) {
			System.out.println("Enter a number between 1 and "+ count);
			choice = kb.nextInt();
		}
		
		if(choice == count) {
			authorMenu();
		}
		
		
	}

	@SuppressWarnings("resource")
	private static void bookMenu() {
		// TODO Auto-generated method stub
		System.out.println("1)	Add Books");
		System.out.println("2)	Update Books");
		System.out.println("3)	Delete Books");
		System.out.println("4)	Read Books");
		System.out.println("5)  Quit to previous");
		Author author = new Author();
		
		Scanner kb = new Scanner(System.in);
		int choice = kb.nextInt();
		
		while(choice > 4 && choice < 1) {
			System.out.println("Enter a number between 1 and 3!");
			choice = kb.nextInt();
		}
		if(choice == 1) {
			addingBook(author);
		}
		if(choice == 2) {
			updatingBook(null);
		}
		if(choice == 3) {
			DeletingBook();
		}
		if(choice == 4) {
			ReadingBook();
		}
		if (choice == 5) {
			adminMenu();
		}
	}

	@SuppressWarnings("resource")
	private static void addingBook(Author author) {
		// TODO Auto-generated method stub
		System.out.println("Enter the title of the book you wish to add");
		Book book = new Book();
		AdminService adminService = new AdminService();
		Scanner kb = new Scanner(System.in);
		book.setBookTitle(kb.nextLine());
		
		if(author.getAuthorName() == null) {
			System.out.println("Choose or add new author");
			List<Author> authors = new ArrayList<>();
			int count = 1;
			try {
				authors = adminService.readAuthor(author);
				for (Author a: authors ) {
					System.out.println(count + ")	" + a.getAuthorName());
		            count++;
				}
				System.out.println(count + ")	Quit to previous ");
				count++;
				System.out.println(count + ")	Add new Author");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int choice = kb.nextInt();
			if(choice == (count -1)) {
				bookMenu();
			}
			if(choice == count) {
				System.out.println("Enter author name you wish to add");
				author.setAuthorName(kb.nextLine());
				try {
					adminService.saveAuthor(author);
					System.out.println("Author added!");
					author = adminService.readAuthor(author).get(0);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				author = authors.get(choice-1);
			}
		}
		
		
		System.out.println("Choose or add a publisher for the book");
		Publisher publisher = new Publisher();
		List<Publisher> publishers = new ArrayList<>();
		int count = 1;
		try {
			publishers = adminService.readPublisher(publisher);
			for(Publisher a : publishers) {
	            System.out.println(count + ")	" + a.getPublisherName());
	            count++;
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(count + ")	Quit to previous ");
		//count++;
		//System.out.println(count + ")	Add new Publisher");
		int choice = kb.nextInt();
		
		while(choice < 1 && choice > count) {
			System.out.println("Enter a number between 1 and "+ count);
			choice = kb.nextInt();
		}
		if(choice == count) {
			try {
				adminService.saveBook(book);
				System.out.println("Book added without publisher, genre, branch and number copies ");
				if (author != null) {
					Book aBook = new Book();
					try {
						aBook = adminService.readBook(book).get(0);
						adminService.bookAuthors(author, aBook);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				bookMenu();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*if(choice == count) {
			addingPublisher(book, author);
		}*/
		publisher = publishers.get(choice-1);
		book.setPubId(publisher.getPublisherId());
		try {
			adminService.saveBook(book);
			book = adminService.readBook(book).get(0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (author != null) {
			Book aBook = new Book();
			try {
				aBook = adminService.readBook(book).get(0);
				adminService.bookAuthors(author, aBook);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("Choose or add new genre for the book");
		Genre genre = new Genre();
		count = 1;
		List<Genre> genres = new ArrayList<>();
		try {
			genres = adminService.readGenre(genre);
			for(Genre g: genres) {
				System.out.println(count + ")	" + g.getGenreName());
	            count++;
			}
			System.out.println(count + ")	Quit to previous ");
			/*count++;
			System.out.println(count + ")	Add new Genre");*/
			choice = kb.nextInt();
			
			while(choice < 1 && choice > count) {
				System.out.println("Enter a number between 1 and "+ count);
				choice = kb.nextInt();
			}
			if(choice == count) {
				System.out.println("Book added without genre, branch and number copies ");
				bookMenu();
			}
			/*if(choice == count) {
				addingGenre(book);
			}*/
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		genre = genres.get(choice - 1);
		try {
			adminService.bookGenres(book, genre);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			adminService.bookGenres(book, genre);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("Choose or add new branch for the book");
		Branch branch = new Branch();
		count = 1;
		List<Branch> branches = new ArrayList<>();
		try {
			branches = adminService.readBranch(branch);
			for(Branch b: branches) {
				System.out.println(count + ")	" + b.getBranchName());
	            count++;
			}
			System.out.println(count + ")	Quit to previous ");
			/*count++;
			System.out.println(count + ")	Add new Branch");*/
			choice = kb.nextInt();
			
			while(choice < 1 && choice > count) {
				System.out.println("Enter a number between 1 and "+ count);
				choice = kb.nextInt();
			}
			if(choice == count) {
				System.out.println("Book added without branch and copies");
				bookMenu();
			}
			/*if(choice == count) {
				addingBranch(book);
			}*/
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		branch = branches.get(choice - 1);
		System.out.println("Enter number of copies or 0 to return");
		choice = kb.nextInt();
		if (choice == 0) {
			try {
				book.setNoOfCopies(0);
				adminService.newBooks(book, branch);
				System.out.println("Book added without number of copies");
				bookMenu();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			book.setNoOfCopies(choice);
			adminService.newBooks(book, branch);
			System.out.println("Book added");
			bookMenu();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	
	@SuppressWarnings("resource")
	private static void DeletingBook() {
		// TODO Auto-generated method stub
		System.out.println("Pick book to Delete");
		AdminService adminService = new AdminService();
		Book book = new Book();
		List<Book> books = new ArrayList<>();
		int count = 1;
		try {
			books = adminService.readBook(book);
			for(Book a : books) {
	            System.out.println(count + ")	" + a.getBookTitle());
	            count++;
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		count++;
		System.out.println(count + ")	Quit to previous ");
		Scanner kb = new Scanner(System.in);
		int choice = kb.nextInt();
		
		while(choice < 1 && choice > count) {
			System.out.println("Enter a number between 1 and "+ count);
			choice = kb.nextInt();
		}
		if(choice == count) {
			bookMenu();
		}
		book = books.get(choice-1);
		book.setBookTitle(null);
		try {
			adminService.saveBook(book);
			System.out.println("Book deleted!");
			bookMenu();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("resource")
	private static void ReadingBook() {
		// TODO Auto-generated method stub
		AdminService adminService = new AdminService();
		Book book = new Book();
		List<Book> books = new ArrayList<>();
		int count = 1;
		try {
			books = adminService.readBook(book);
			for(Book a : books) {
	            System.out.println(count + ")	" + a.getBookTitle());
	            count++;
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		count++;
		System.out.println(count + ")	Quit to previous ");
		Scanner kb = new Scanner(System.in);
		int choice = kb.nextInt();
		
		while(choice < 1 && choice != count) {
			System.out.println("Enter a number between 1 and "+ count);
			choice = kb.nextInt();
		}
		if(choice == count) {
			bookMenu();
		}
		
	}
	
	@SuppressWarnings("resource")
	private static void updatingBook(Author author) {
		// TODO Auto-generated method stub
		System.out.println("Pick book to edit");
		AdminService adminService = new AdminService();
		Book book = new Book();
		List<Book> books = new ArrayList<>();
		int count = 1;
		try {
			books = adminService.readBook(book);
			for(Book a : books) {
	            System.out.println(count + ")	" + a.getBookTitle());
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
			bookMenu();
		}
		book = books.get(choice-1);
		System.out.println("Enter new Book name");
		book.setBookTitle(kb.nextLine());
		book.setBookTitle(kb.nextLine());
		
		try {
			adminService.saveBook(book);
			System.out.println("Book title changed");
			bookMenu();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings("resource")
	private static void publisherMenu() {
		// TODO Auto-generated method stub
		System.out.println("1)	Add Publishers");
		System.out.println("2)	Update Publishers");
		System.out.println("3)	Delete Publishers");
		System.out.println("4)	Read Publishers");
		System.out.println("5)	Return to previous");
		Scanner kb = new Scanner(System.in);
		int choice = kb.nextInt();
		
		while(choice > 4 && choice < 1) {
			System.out.println("Enter a number between 1 and 3!");
			choice = kb.nextInt();
		}
		if(choice == 1) {
			addingPublisher(null, null);
		}
		if(choice == 2) {
			updatingPublisher();
		}
		if(choice == 3) {
			DeletingPublisher();
		}
		if(choice == 4) {
			ReadingPublisher();
		}
		if(choice == 5) {
			adminMenu();
		}
	}

	@SuppressWarnings("resource")
	private static void ReadingPublisher() {
		// TODO Auto-generated method stub
		AdminService adminService = new AdminService();
		Publisher publisher = new Publisher();
		List<Publisher> publishers = new ArrayList<>();
		int count = 1;
		try {
			publishers = adminService.readPublisher(publisher);
			for(Publisher a : publishers) {
	            System.out.println(count + ")	" + a.getPublisherName());
	            count++;
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		count++;
		System.out.println(count + ")	Quit to previous ");
		Scanner kb = new Scanner(System.in);
		int choice = kb.nextInt();
		
		while(choice < 1 && choice != count) {
			System.out.println("Enter a number between 1 and "+ count);
			choice = kb.nextInt();
		}
		if(choice == count) {
			publisherMenu();
		}
		
	}

	@SuppressWarnings("resource")
	private static void DeletingPublisher() {
		// TODO Auto-generated method stub
		System.out.println("Pick publisher to Delete");
		AdminService adminService = new AdminService();
		Publisher publisher = new Publisher();
		List<Publisher> publishers = new ArrayList<>();
		int count = 1;
		try {
			publishers = adminService.readPublisher(publisher);
			for(Publisher a : publishers) {
	            System.out.println(count + ")	" + a.getPublisherName());
	            count++;
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		count++;
		System.out.println(count + ")	Quit to previous ");
		Scanner kb = new Scanner(System.in);
		int choice = kb.nextInt();
		
		while(choice < 1 && choice > count) {
			System.out.println("Enter a number between 1 and "+ count);
			choice = kb.nextInt();
		}
		if(choice == count) {
			publisherMenu();
		}
		publisher = publishers.get(choice-1);
		publisher.setPublisherName(null);
		publisher.setPublisherAddress(null);
		publisher.setPublisherPhone(null);
		try {
			adminService.savePublisher(publisher);
			System.out.println("Publisher deleted");
			publisherMenu();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings("resource")
	private static void updatingPublisher() {
		// TODO Auto-generated method stub
		System.out.println("Pick publisher to edit");
		AdminService adminService = new AdminService();
		Publisher publisher = new Publisher();
		List<Publisher> publishers = new ArrayList<>();
		int count = 1;
		try {
			publishers = adminService.readPublisher(publisher);
			for(Publisher a : publishers) {
	            System.out.println(count + ")	" + a.getPublisherName());
	            count++;
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		count++;
		System.out.println(count + ")	Quit to previous ");
		Scanner kb = new Scanner(System.in);
		int choice = kb.nextInt();
		
		while(choice < 1 && choice > count) {
			System.out.println("Enter a number between 1 and "+ count);
			choice = kb.nextInt();
		}
		if(choice == count) {
			publisherMenu();
		}
		publisher = publishers.get(choice-1);
		System.out.println("Enter new Publisher name or N/A to skip");
		String entry = kb.nextLine();
		entry = kb.nextLine();
		if(!"N/A".equalsIgnoreCase(entry)){
			publisher.setPublisherName(entry);
		}
		System.out.println("Enter new Publisher address or N/A to skip");
		entry = kb.nextLine();
		if(!"N/A".equalsIgnoreCase(entry)){
			publisher.setPublisherAddress(entry);
		}
		System.out.println("Enter new Publisher phone or N/A to skip");
		entry = kb.nextLine();
		if(!"N/A".equalsIgnoreCase(entry)){
			publisher.setPublisherPhone(entry);
		}
		try {
			adminService.savePublisher(publisher);
			System.out.println("Publisher updated");
			publisherMenu();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings("resource")
	private static void addingPublisher(Book book, Author author) {
		Publisher publisher = new Publisher();
		AdminService adminService = new AdminService();
		Scanner kb = new Scanner(System.in);
		System.out.println("Enter new Publisher name or N/A to skip");
		String entry = kb.nextLine();
		if(!"N/A".equalsIgnoreCase(entry)){
			publisher.setPublisherName(entry);
		}
		System.out.println("Enter new Publisher address or N/A to skip");
		entry = kb.nextLine();
		if(!"N/A".equalsIgnoreCase(entry)){
			publisher.setPublisherAddress(entry);
		}
		System.out.println("Enter new Publisher phone or N/A to skip");
		entry = kb.nextLine();
		if(!"N/A".equalsIgnoreCase(entry)){
			publisher.setPublisherPhone(entry);
		}
		try {
			adminService.savePublisher(publisher);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	private static void borrowersMenu() {
		// TODO Auto-generated method stub
		System.out.println("1)	Add Borrowers");
		System.out.println("2)	Update Borrowers");
		System.out.println("3)	Delete Borrowers");
		System.out.println("4)	Read Borrowers");
		System.out.println("5)	Return to previous");
		Scanner kb = new Scanner(System.in);
		int choice = kb.nextInt();
		
		while(choice > 4 && choice < 1) {
			System.out.println("Enter a number between 1 and 3!");
			choice = kb.nextInt();
		}
		if(choice == 1) {
			addingBorrower();
		}
		if(choice == 2) {
			updatingBorrower();
		}
		if(choice == 3) {
			DeletingBorrower();
		}
		if(choice == 4) {
			ReadingBorrower();
		}
		if(choice == 5) {
			adminMenu();
		}
	}

	@SuppressWarnings("resource")
	private static void ReadingBorrower() {
		// TODO Auto-generated method stub
		AdminService adminService = new AdminService();
		Borrower borrower = new Borrower();
		List<Borrower> borrowers = new ArrayList<>();
		int count = 1;
		try {
			borrowers = adminService.readBorrower(borrower);
			for(Borrower a : borrowers) {
	            System.out.println(count + ")	" + a.getBorrowerName());
	            count++;
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		count++;
		System.out.println(count + ")	Quit to previous ");
		Scanner kb = new Scanner(System.in);
		int choice = kb.nextInt();
		
		while(choice < 1 && choice != count) {
			System.out.println("Enter a number between 1 and "+ count);
			choice = kb.nextInt();
		}
		if(choice == count) {
			borrowersMenu();
		}
		
	}

	@SuppressWarnings("resource")
	private static void DeletingBorrower() {
		// TODO Auto-generated method stub
		System.out.println("Pick borrower to Delete");
		AdminService adminService = new AdminService();
		Borrower borrower = new Borrower();
		List<Borrower> borrowers = new ArrayList<>();
		int count = 1;
		try {
			borrowers = adminService.readBorrower(borrower);
			for(Borrower a : borrowers) {
	            System.out.println(count + ")	" + a.getBorrowerName());
	            count++;
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		count++;
		System.out.println(count + ")	Quit to previous ");
		Scanner kb = new Scanner(System.in);
		int choice = kb.nextInt();
		
		while(choice < 1 && choice > count) {
			System.out.println("Enter a number between 1 and "+ count);
			choice = kb.nextInt();
		}
		if(choice == count) {
			borrowersMenu();
		}
		borrower = borrowers.get(choice-1);
		borrower.setBorrowerName(null);
		borrower.setBorrowerAddress(null);
		borrower.setBorrowerPhone(null);
		try {
			adminService.saveBorrower(borrower);
			System.out.println("Borrower Deleted");
			borrowersMenu();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings("resource")
	private static void updatingBorrower() {
		// TODO Auto-generated method stub
		System.out.println("Pick borrower to edit");
		AdminService adminService = new AdminService();
		Borrower borrower = new Borrower();
		List<Borrower> borrowers = new ArrayList<>();
		int count = 1;
		try {
			borrowers = adminService.readBorrower(borrower);
			for(Borrower a : borrowers) {
	            System.out.println(count + ")	" + a.getBorrowerName());
	            count++;
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		count++;
		System.out.println(count + ")	Quit to previous ");
		Scanner kb = new Scanner(System.in);
		int choice = kb.nextInt();
		
		while(choice < 1 && choice > count) {
			System.out.println("Enter a number between 1 and "+ count);
			choice = kb.nextInt();
		}
		if(choice == count) {
			borrowersMenu();
		}
		borrower = borrowers.get(choice-1);
		System.out.println("Enter new Borrower name or N/A to skip");
		String entry = kb.nextLine();
		entry = kb.nextLine();
		if(!"N/A".equalsIgnoreCase(entry)){
			borrower.setBorrowerName(entry);
		}
		System.out.println("Enter new Borrower address or N/A to skip");
		entry = kb.nextLine();
		if(!"N/A".equalsIgnoreCase(entry)){
			borrower.setBorrowerAddress(entry);
		}
		System.out.println("Enter new Borrower phone or N/A to skip");
		entry = kb.nextLine();
		if(!"N/A".equalsIgnoreCase(entry)){
			borrower.setBorrowerPhone(entry);
		}
		try {
			adminService.saveBorrower(borrower);
			System.out.println("Borrower updated!");
			borrowersMenu();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings("resource")
	private static void addingBorrower() {
		Borrower borrower = new Borrower();
		AdminService adminService = new AdminService();
		Scanner kb = new Scanner(System.in);
		System.out.println("Enter new Borrower name or N/A to skip");
		String entry = kb.nextLine();
		entry = kb.nextLine();
		if(!"N/A".equalsIgnoreCase(entry)){
			borrower.setBorrowerName(entry);
		}
		System.out.println("Enter new Borrower address or N/A to skip");
		entry = kb.nextLine();
		if(!"N/A".equalsIgnoreCase(entry)){
			borrower.setBorrowerAddress(entry);
		}
		System.out.println("Enter new Borrower phone or N/A to skip");
		entry = kb.nextLine();
		if(!"N/A".equalsIgnoreCase(entry)){
			borrower.setBorrowerPhone(entry);
		}
		try {
			adminService.saveBorrower(borrower);
			System.out.println("Borrower added!");
			borrowersMenu();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("resource")
	private static void libBranchesMenu() {
		// TODO Auto-generated method stub
		System.out.println("1)	Add Branches");
		System.out.println("2)	Update Branches");
		System.out.println("3)	Delete Branches");
		System.out.println("4)	Read Branches");
		System.out.println("5)	Return to previous");
		Scanner kb = new Scanner(System.in);
		int choice = kb.nextInt();
		
		while(choice > 4 && choice < 1) {
			System.out.println("Enter a number between 1 and 3!");
			choice = kb.nextInt();
		}
		if(choice == 1) {
			addingBranch();
		}
		if(choice == 2) {
			updatingBranch();
		}
		if(choice == 3) {
			DeletingBranch();
		}
		if(choice == 4) {
			ReadingBranch();
		}
		if(choice == 5) {
			adminMenu();
		}
	}

	@SuppressWarnings("resource")
	private static void ReadingBranch() {
		// TODO Auto-generated method stub
		AdminService adminService = new AdminService();
		Branch branch = new Branch();
		List<Branch> branchs = new ArrayList<>();
		int count = 1;
		try {
			branchs = adminService.readBranch(branch);
			for(Branch a : branchs) {
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
		
		while(choice < 1 && choice != count) {
			System.out.println("Enter a number between 1 and "+ count);
			choice = kb.nextInt();
		}
		if(choice == count) {
			libBranchesMenu();
		}
		
	}

	@SuppressWarnings("resource")
	private static void DeletingBranch() {
		// TODO Auto-generated method stub
		System.out.println("Pick branch to Delete");
		AdminService adminService = new AdminService();
		Branch branch = new Branch();
		List<Branch> branchs = new ArrayList<>();
		int count = 1;
		try {
			branchs = adminService.readBranch(branch);
			for(Branch a : branchs) {
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
			libBranchesMenu();
		}
		branch = branchs.get(choice-1);
		branch.setBranchName(null);
		branch.setBranchAddress(null);
		try {
			adminService.saveBranch(branch);
			System.out.println("Branch deleted!");
			libBranchesMenu();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings("resource")
	private static void updatingBranch() {
		// TODO Auto-generated method stub
		System.out.println("Pick branch to edit");
		AdminService adminService = new AdminService();
		Branch branch = new Branch();
		List<Branch> branchs = new ArrayList<>();
		int count = 1;
		try {
			branchs = adminService.readBranch(branch);
			for(Branch a : branchs) {
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
			libBranchesMenu();
		}
		branch = branchs.get(choice-1);
		System.out.println("Enter new Branch name or N/A to skip");
		String entry = kb.nextLine();
		entry = kb.nextLine();
		if(!"N/A".equalsIgnoreCase(entry)){
			branch.setBranchName(entry);
		}
		System.out.println("Enter new Branch address or N/A to skip");
		entry = kb.nextLine();
		if(!"N/A".equalsIgnoreCase(entry)){
			branch.setBranchAddress(entry);
		}
		
		try {
			adminService.saveBranch(branch);
			System.out.println("Branch updated!");
			libBranchesMenu();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings("resource")
	private static void addingBranch() {
		// TODO Auto-generated method stub
		System.out.println("Enter autho name you wish to add");
		Branch branch = new Branch();
		AdminService adminService = new AdminService();
		Scanner kb = new Scanner(System.in);
		System.out.println("Enter new Branch name or N/A to skip");
		String entry = kb.nextLine();
		if(!"N/A".equalsIgnoreCase(entry)){
			branch.setBranchName(entry);
		}
		System.out.println("Enter new Branch address or N/A to skip");
		entry = kb.nextLine();
		if(!"N/A".equalsIgnoreCase(entry)){
			branch.setBranchAddress(entry);
		}
		try {
			adminService.saveBranch(branch);
			System.out.println("Branch added!");
			libBranchesMenu();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
