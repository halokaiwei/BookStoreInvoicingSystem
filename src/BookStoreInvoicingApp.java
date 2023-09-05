package App;

import java.io.*;
import java.util.*;

import Domain.Administrator;
import Domain.ClientUser;
import Domain.Item;
import Domain.OrderController;
import Domain.Promotion;
public class BookStoreInvoicingApp {

	static Scanner input = new Scanner(System.in);
	static ArrayList <ClientUser> clientUser = new ArrayList<ClientUser> ();
	static ArrayList <Administrator> admin = new ArrayList<Administrator> ();
	static ArrayList <Promotion> promotion = new ArrayList<Promotion> ();


	public static void main(String[] args) throws FileNotFoundException
	{
		//read all the file needed
		readAdminFile();
		readClientUserFile();
		readPromotionFile();

		boolean found = false;
		int no = -1;
		do
		{
			try
			{
				System.out.println("------------------------------------------------------------------");
				System.out.println("\t   ~ WELCOME TO BOOKSTORE INVOICING SYSTEM ~");
				System.out.println("------------------------------------------------------------------");
				System.out.println("		         | LOGIN ACCOUNT |");
				System.out.println("------------------------------------------------------------------\n");
				System.out.println("		         1. ADMIN\n");
				System.out.println("		         2. CLIENT USER\n");
				System.out.println("		         3. EXIT SYSTEM\n");
				System.out.println("==================================================================");
				System.out.print("		        Option: ");
				int loginOpt = input.nextInt(); //get input from user
				input.nextLine();

				if (loginOpt == 3)
				{
					no = 0;
					saveToClientUserFile();
					System.out.println(" The information has been saved to file...");
					System.out.println("============== Exiting Bookstore Invoicing System ==============");
					System.exit(0);
				}
				else if (loginOpt == 1 || loginOpt == 2)
				{
					System.out.println("------------------------------------------------------------------");
					System.out.println("\t   ~ WELCOME TO BOOKSTORE INVOICING SYSTEM ~");
					System.out.println("------------------------------------------------------------------");
					System.out.println("		         | LOGIN ACCOUNT |");
					System.out.println("------------------------------------------------------------------");
					System.out.println("		   Please provide your...\n");
					System.out.print("	   User ID        : ");
					String userID = input.nextLine(); //get userID
					System.out.print("	   User password  : ");
					String password = input.nextLine(); //get userPassword
					System.out.println("==================================================================");

					if (loginOpt == 1) //if option 1 chosen, proceed to administrator login
					{
						no = loginAdmin(userID, password);
						if (no >= 0) //if the ID found in database
						{
							adminMenu(no); //display administrator menu
						}
						else
						{
							System.out.println(" Please enter valid ID.");                    	
						}

					}
					else if (loginOpt == 2) //proceed option 2 which is customer login
					{
						no = loginClientUser(userID, password); //if found, will return no >= 0
						System.out.printf(" Number is: %d",no);
						int clientMenuOpt = 0;
						if (no >= 0)
						{
							do
							{
								try
								{
									ClientUser cu = new ClientUser(clientUser.get(no));
									cu.clientUserMenu(); //display customer menu
									clientMenuOpt = input.nextInt();
									if (clientMenuOpt == 0) //if option 0 chosen, exit customer interface
									{
										System.out.println(" Logout customer account...");
										no = -1;
									}
									if (clientMenuOpt == 1) //if option 1 chosen, display client personal details
									{
										cu.displayClientUserDetails();

									}
									else if (clientMenuOpt == 2) //if option 2 chosen, create orders
									{
										cu.createOrders(userID, promotion);
									}
									else if (clientMenuOpt == 3) //if option 3 chosen, cancel orders
									{
										input.nextLine();
										cu.cancelOrder(userID);
										//after cancel the order, client user can check via option 4 : View billing statement
										//to verify whether order delete or not
									}
									else if (clientMenuOpt == 4) //if option 4 chosen, view billing statement
									{
										//view billing statement here

										viewBillingStatement(userID);
									}
									else if (clientMenuOpt == 5) //if option 5 chosen, check promotion
									{
										//check promotion here
										checkPromotion();
									}
									else if (clientMenuOpt == 6) //if option 6 chosen, search for items
									{
										//this function will only allow user to search one time and then will directly exit the function
										boolean valid = true;
										do
										{
											try
											{												
												System.out.println(" ( 0-Quit	1-Search for items )");
												System.out.print(" Search Item Option: ");
												int opt = input.nextInt();
												if (opt == 1)
												{
													input.nextLine();
										            Item item = new Item();
										            item.displayItemList();
										            System.out.print("\n Enter item ID: ");
										            String itemId = input.nextLine();
										            Item target = item.searchForItem(itemId);
										            if (target!=null)
										            {
										            item.displayItemDetails(target);										            	
										            }
										            else
										            {
										            	valid= false;
										            	System.out.println(" Item ID invalid.");
										            }
												}
												if(opt != 0 && opt != 1)
												{
													valid = false;
													System.out.println(" Please enter correct option.");
												}
												else
													valid = true;
											}
											catch(InputMismatchException exp)
											{
												System.out.println(" Please enter correct option.");
												valid = false;
												input.nextLine();
											}
										}while(valid == false);
									}
								}
								catch(InputMismatchException exp)
								{
									System.out.println(" Please enter correct option.");
									clientMenuOpt = 1;
									input.nextLine();
								}

							}while(clientMenuOpt != 0);
						}
						else
						{
							int regOpt = 0;
							System.out.println(" No customer found.");
							System.out.println(" Would you like to register a new account? ( 1 = Yes  0 = No)\n");
							System.out.print(" Option: ");
							regOpt = input.nextInt();

							if(regOpt == 1)
							{
								createUserProfile();
							}
							else
								no = -1;
						}
					}
				}
				else
				{
					System.out.println(" Error option entered.");
					no = -1;
				}
			}
			catch(InputMismatchException i)
			{
				System.out.println(" Please enter correct option.");
				input.nextLine();
				no = -1;
			}
		}while(found == false || no == -1 );
	}

	//read admin data from file
	public static void readAdminFile()
	{
		String source = "Admin.txt"; //put file name here
		String line = "";
		try
		{
			BufferedReader br = new BufferedReader (new FileReader (source)); //read in file--create obj
			while ((line = br.readLine()) != null) //file line not empty--go into end
			{
				String [] a=line.split("#");//split line by #
				Administrator ad1 = null;
				String adminName = a[0];
				String adminID = a[1];
				String adminPassword = a[2];
				String jobTitle = a[3];
				ad1 = new Administrator(adminName, adminID, adminPassword, jobTitle);
				admin.add(ad1);
			}
			br.close();
		}
		catch (FileNotFoundException exp) //print out error if the file is not found
		{
			exp.printStackTrace();
		}
		catch (IOException exp) //line end
		{
			exp.printStackTrace();
		}
	}

	//read client user data from file
	public static void readClientUserFile()
	{
		String source = "ClientUser.txt"; //put file name here
		String line = "";
		try
		{
			BufferedReader br = new BufferedReader (new FileReader (source)); //read in file--create obj
			while ((line = br.readLine()) != null) //file line not empty--go into end
			{
				String [] c=line.split("#");//split line by #
				ClientUser cu1 = null;
				String clientUserID = c[0];
				String clientUsername = c[1];
				String clientUserPassword = c[2];
				int phoneNo = Integer.parseInt(c[3]);
				String email = c[4];
				String homeAddress = c[5];
				cu1 = new ClientUser(clientUserID, clientUsername, clientUserPassword, phoneNo, email, homeAddress);
				clientUser.add(cu1);
			}
			br.close();
		}
		catch (FileNotFoundException exp) //print out error if the file is not found
		{
			exp.printStackTrace();
		}
		catch (IOException exp) //line end
		{
			exp.printStackTrace();
		}
	}

	//read customer data from file
	public static void readPromotionFile()
	{
		String source = "Promotion.txt"; //put file name here
		String line = "";
		try
		{
			BufferedReader br = new BufferedReader (new FileReader (source)); //read in file--create obj
			while ((line = br.readLine()) != null) //file line not empty--go into end
			{
				String [] p=line.split("#");//split line by comma
				Promotion promo1 = null;
				String promotionID = p[0];
				double promotionDiscount = Double.parseDouble(p[1]);
				int promotionAmt = Integer.parseInt(p[2]);
				promo1 = new Promotion(promotionID, promotionDiscount, promotionAmt);
				promotion.add(promo1);
			}
			br.close();
		}
		catch (FileNotFoundException exp) //print out error if the file is not found
		{
			exp.printStackTrace();
		}
		catch (IOException exp) //line end
		{
			exp.printStackTrace();
		}
	}

	//login to the system with administrator ID and password
	public static int loginAdmin(String userID, String userPassword)
	{
		boolean found = false;
		int no = -100;
		int i = 0;
		Administrator adm = null;
		while(found == false && i < admin.size())
		{
			adm = admin.get(i); //get the first administrator for matching
			if(adm.getUserID().equals(userID) && adm.getPassword().equals(userPassword)) //find whether the input match with database or not
			{
				found = true; //return true if match with input(for checking only)
				System.out.println(" Valid admin. WELCOME TO BOOKSTORE INVOICING SYSTEM. ");
				no = i;
			}
			else i++;
		}
		return no;
	}

	//login to the system with customer ID and password
	public static int loginClientUser(String userID,String userPassword)
	{
		boolean found = false;
		int i = 0;
		ClientUser cu = null;
		while(found == false && i < clientUser.size())
		{
			cu = clientUser.get(i); //get the first customer for matching
			if(cu.getUserID().equals(userID) && cu.getPassword().equals(userPassword)) //find whether the input match with database or not
			{
				found = true; //return true if match
				System.out.println(" Valid client user. WELCOME TO HOURLY BIKE RENTAL SYSTEM. ");
			}
			else i++; //repeat until match
		}
		if(found)
			return i; //return the number
		else return -1;
	}

	public static void adminMenu(int no)
	{
		int adminOpt = 0;
		do {
			System.out.println("------------------------------------------------------------------");
			System.out.println("\t   ~ WELCOME TO BOOKSTORE INVOICING SYSTEM ~");
			System.out.println("------------------------------------------------------------------");
			System.out.println("\t\t       | ADMIN MENU |");
			System.out.println("------------------------------------------------------------------\n");
			System.out.println("\t\t   1. Generate Sales Report\n");
			System.out.println("\t\t   2. Maintain Orders\n");
			System.out.println("\t\t   3. Manage Book Store Items\n");
			System.out.println("==================================================================");
			System.out.println(" Enter 0 to quit this interface. ");
			System.out.print(" Option: ");
			Scanner scanner=new Scanner(System.in);
			int option=scanner.nextInt();
			switch(option) {

			case 0:
			{
				System.out.print(" Exiting admin function...\n");
				adminOpt = option;
				break;
			}			
			case 1:
			{
				Administrator a = new Administrator();
				a.generateSalesReport();
				adminOpt = option;
				break;
			}
			case 2:
			{
				maintainOrder();
				adminOpt = option;
				break;
			}
			case 3:
			{
				Administrator a = new Administrator();
				a.manageBookStoreItem();
				adminOpt = option;
				break;
			}
			default:
			{
				//if enter wrong option here will directly exit to admin menu
				System.out.print(" Please enter a valid option.");
			}

			}
		}while(adminOpt != 0);
	}

	public static void createUserProfile()
	{
		System.out.println("------------------------------------------------------------------");
		System.out.println("\t   ~ WELCOME TO BOOKSTORE INVOICING SYSTEM ~");
		System.out.println("------------------------------------------------------------------");
		System.out.println("\t\t   | CLIENT USER REGISTRATION |");
		System.out.println("------------------------------------------------------------------");
		Scanner input = new Scanner(System.in);

		displayPolicy();
		char approved = input.next().charAt(0);
		input.nextLine();
		if (Character.toLowerCase(approved) == 'y')
		{
			//request customer ID from user with validation
			boolean validID = false;
			String clientUserID;
			do
			{				
				//forget change to 'print'
				System.out.print(" (CXXXX\tX - Digits)\n Enter customer ID : ");
				clientUserID = input.nextLine().toUpperCase();
				validID = isValidID(clientUserID); //customerID validation
			} while (validID == false);


			//request customer name from user with validation
			boolean validName = false;
			String clientUsername;
			do
			{
				System.out.print(" Enter customer name : ");
				clientUsername = input.nextLine();
				validName = isValidUsername(clientUsername); //customer name validation
			} while (validName == false);
			//request customer password from user with validation
			boolean validPassword = false;
			String clientUserPassword;
			do
			{
				System.out.print(" Password Can Only Be 6-22 Characters.\n Enter customer password  : ");
				clientUserPassword = input.nextLine();
				validPassword = isValidPassword(clientUserPassword); //password validation
			} while (validPassword == false);

			//request customer email from user with validation
			boolean validEmail = false;
			boolean emailRegistered = true;
			String email;
			do
			{
				System.out.print(" Enter customer email address : ");
				email = input.nextLine();
				validEmail = isValidEmail(email); //email validation
				for (int i = 0; i < clientUser.size(); i++)
				{
					if (email == clientUser.get(i).getEmail())
						emailRegistered = true;
					else
						emailRegistered = false;
				}

			} while(validEmail == false || emailRegistered != false);

			System.out.print(" Continue fill up the optional details? (y-yes / n-no): ");
			char optionalChoice = input.next().charAt(0);
			String homeAddress = "";
			int phoneNo = 0;
			if (Character.toLowerCase(optionalChoice) == 'y')
			{
				input.nextLine();
				boolean validOptDetails = false;
				//request customer address from user with validation
				do
				{
					try
					{
						System.out.print(" Enter customer address  : ");
						homeAddress = input.nextLine();
						System.out.print(" Enter customer Tel.No (+60)  : ");
						phoneNo = Integer.parseInt(input.nextLine());
						validOptDetails = validateOptDetails(homeAddress, phoneNo);
					}
					catch (Exception e)
					{
						System.out.print(" Please enter a valid address and phone number.\n");
					}

				} while (validOptDetails == false);
			}

			System.out.println("==================================================================");

			ClientUser newUser = new ClientUser(clientUserID, clientUsername, clientUserPassword, phoneNo, email, homeAddress);
			//add the newUser object to ArrayList - clientUser
			clientUser.add(newUser);
		}
		else
			System.out.print(" Reminder: You can only create account once accepting the policy.\n\n");
	}

	//display policy before register account
	public static void displayPolicy()
	{
		System.out.print("\t\t   ~ Privacy and Policy ~\n"
				+" How does book store invoicing system protect your privacy?\n"
				+ " We only collect the data that is necessary to provide a \n"
				+ " better and efficient services and store it for our organisation\n"
				+ " used only.\n\n"
				+ " Our group will not selling your data to other and all the process\n"
				+ " is just for educational purpose. The data would provide further\n"
				+ " image and purchasing habit for our analysis.\n");
		System.out.println("==================================================================\n");
		System.out.print(" Approval for policy (y-yes / n-no): ");
	}

	//check the promotion of bookstore invoicing system
	public static void checkPromotion()
	{
		displayPromotionList();
		boolean found = false;
		int option = 0;
		do
		{
			found = searchPromotion();
			if(found == false)
				System.out.println(" Promotion ID not found . Please enter again .");
			boolean valid = true;
			do
			{
				try
				{
					System.out.print(" Do you want to search promotion ID again ?( 1 = Yes  0 = No)\n");
					System.out.print(" Option: ");
					option = input.nextInt();
					input.nextLine();
					if (option == 0 || option == 1)
						valid = true;
					else
						valid = false;
				}
				catch(InputMismatchException exp)
				{
					System.out.println(" Please enter correct option.");
					input.nextLine();
					valid = false;
				}
			}while(valid == false);
		}while(option != 0);
	}

	public static void displayPromotionList()
	{
		System.out.println("------------------------------------------------------------------");
		System.out.println("\t   ~ WELCOME TO BOOKSTORE INVOICING SYSTEM ~");
		System.out.println("------------------------------------------------------------------");
		System.out.println("\t\t    | PROMOTION LIST |\t\tTotal: " + promotion.size());
		System.out.println("------------------------------------------------------------------");
		System.out.println("  No.  |  Promotion ID\t| Amount |");
		System.out.println("------------------------------------------------------------------");

		for(int i = 0; i < promotion.size();i++)
		{
			System.out.printf("  %-2d.\t  %-6s\t   %-5d\n", (i+1), promotion.get(i).getPromoID(),promotion.get(i).getPromoAmt());
		}
		System.out.println("==================================================================\n");
	}

	public static boolean searchPromotion() {
		Scanner input = new Scanner(System.in);
		String promoID;
		System.out.print(" Enter promo ID to search: ");
		promoID = input.nextLine();
		boolean found = false;
		int i = 0;
		Promotion p = null;
		while(found == false && i < promotion.size())
		{
			p = promotion.get(i); //get the first promotion for matching
			if(p.getPromoID().equals(promoID) ) //find whether the input match with database or not
			{
				found = true;
				System.out.println(" Promotion found and available!");
				p.displaySpecificPromoDetails();
				break;
			}
			else i++; //repeat until match
		}
		return found;
	}

	//save client user data to file
	public static void saveToClientUserFile()
	{
		try
		{ //try first
			File customerFile = new File("ClientUser.txt");
			FileWriter fileWriter = new FileWriter(customerFile,false);
			PrintWriter printWriter = new PrintWriter(fileWriter);

			for(int i = 0; i < clientUser.size(); i++)
			{
				if (clientUser.get(i).getHomeAddress() != "" && clientUser.get(i).getPhoneNo() != 0)
				{
					printWriter.println(clientUser.get(i).getUserID()+"#"+clientUser.get(i).getName()
							+"#"+clientUser.get(i).getPassword()+"#"+clientUser.get(i).getPhoneNo()
							+"#"+clientUser.get(i).getEmail()+"#"+clientUser.get(i).getHomeAddress());
				}
				else
				{
					printWriter.println(clientUser.get(i).getUserID()+"#"+clientUser.get(i).getName()
							+"#"+clientUser.get(i).getPassword()+"#"+0
							+"#"+clientUser.get(i).getEmail()+"#"+"null");
				}
			}
			printWriter.close();
		}
		catch (IOException e)
		{
			System.out.println(" Error saving client user file.");
		}
	}


	//customer ID validation
	public static boolean isValidID(String clientUserID)
	{
		boolean validID;
		if (clientUserID.matches("[C]\\d{4}"))  //set the first ID format to 'C' and then from start to end, validate that the 4 letters behind are digits
		{
			boolean conflict = false;
			int i = 0;
			while (conflict == false && i < clientUser.size())
			{
				if (clientUserID.equals(clientUser.get(i).getUserID()))
					conflict = true;
				else
				{
					conflict = false;
					i++;
				}
			}
			if (conflict == false)
			{
				validID = true;
			}
			else
			{
				validID = false;
				System.out.println(" ID is already in use. Try a different one.");
			}
		}
		else
		{
			validID = false;
			System.out.println(" Invalid ID. Enter again.");
		}
		return validID;
	}

	//customer name validation
	public static boolean isValidUsername(String username)
	{
		boolean validName;
		if (username.matches("[a-zA-Z\\s\\.]+"))  //'+'-contain at least one, s-white space character, '.'-many character
		{
			validName = true;
		}
		else
		{
			validName = false;
			System.out.println(" Please write in lowercase or uppercase letters only. Enter again.");
		}
		return validName;
	}

	//password validation
	public static boolean isValidPassword(String password)
	{
		boolean validPassword;
		if (password.matches("\\S+")) //Any non-whitespace character
		{
			if (password.length() >= 6 && password.length() <= 22)
				validPassword = true;
			else
			{
				validPassword = false;
				System.out.println(" Password Invalid");
			}
		}
		else
		{
			validPassword = false;
			System.out.println(" No Spaces Are Allowed In The Password");
		}
		return validPassword;
	}

	//email validation
	public static boolean isValidEmail(String email)
	{
		boolean validEmail;
		if (email.matches("[a-zA-Z0-9\\.\\_]+\\@[a-z\\.]+"))
		{
			validEmail = true;
		}
		else
		{
			validEmail = false;
			System.out.println(" Invalid E-mail. Please Enter Again.");
		}
		return validEmail;
	}

	private static boolean validateOptDetails(String homeAddress, int phoneNo) {
		// TODO Auto-generated method stub

		if (homeAddress.matches("[\\w\\s\\,\\.\\/\\-]+")) //'\w'-A word character: [a-zA-Z_0-9], '\s'-A whitespace character: [ \t\n\x0B\f\r]
		{
			if (phoneNo >= 100000000 && phoneNo <= 1999999999)
			{
				return true;
			}
		}
		System.out.println(" Invalid Address or phone number. Please Enter Again.");
		return false;
	}

	public static void viewBillingStatement(String clientID)
	{
		OrderController clientOrderCtrl=new OrderController();
		clientOrderCtrl.viewBillingStatement(clientID);

	}

	public static void maintainOrder()
	{
		OrderController adminOrderCtrl=new OrderController();
		adminOrderCtrl.maintainOrder();
	}
}