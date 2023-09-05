package Domain;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class OrderController {

	private Order order;

	public OrderController(){
		this.order = new Order();
	}

	//random generate the order ID in 4 digits
	public void randomGeneratorOrderID(){
		Random rand = new Random();
		int orderID = rand.nextInt(10000);
		order.setOrderID(String.valueOf(orderID));
	}

	public void createOrder(String clientId,ArrayList<Promotion> promotionLst){
		Item item;
		String itemId;
		int quantity;
		Scanner scanner = new Scanner(System.in);
		String choice = null;

		//Create Order
		do {
			item = new Item();
			item.displayItemList();

			System.out.print(" Enter item ID: ");
			itemId = scanner.nextLine();

			System.out.print(" Enter quantity: ");
			quantity=scanner.nextInt();
			scanner.nextLine();

			item = item.searchForItem(itemId);
			if(item == null){
				System.out.println(" Error item ID");
			}else{
				this.order.setItem(item);
				this.order.setQuantity(quantity);
				this.order.setClientID(clientId);
				System.out.print(" Want more Item? (y/n) :");
				choice = scanner.nextLine();
				choice = choice.toUpperCase();
			}

		}while(item == null || choice.charAt(0) == 'Y' );

		randomGeneratorOrderID();
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String strDate = formatter.format(date);
		order.setDate(strDate);
		order.createOrder(this.order);

		//Make Payment
		Promotion promotion = new Promotion();
		order.displayOrder(order.getOrderID());

		System.out.print(" Do you have any promotion Code?(y/n)");
		String promo = scanner.nextLine();
		promo = promo.toUpperCase();

		String promotionCode = null;
		Double promotionAmt = 0.0;
		if(promo.charAt(0) == 'Y'){
			System.out.print(" Promotion Code : ");
			promotionCode = scanner.nextLine();

			Promotion validatePromo = null;

			validatePromo = promotion.validatePromotion(promotionCode,promotionLst);
			if(validatePromo != null){
				promotionAmt = promotion.applyPromoCode(validatePromo);
			}

			double totalPrice = order.getTotalPrice(order);
			totalPrice = totalPrice * promotionAmt;

			order.displayOrder(order.getOrderID());
			System.out.println("==================================================================");
			System.out.printf(" Total Price: %.2f" , totalPrice);

			System.out.println("\n------------------------ Payment Details ------------------------- ");
			System.out.println(" Card No : ");
			String cardNo = scanner.nextLine();
			System.out.println(" CCV : ");
			String ccv = scanner.nextLine();

		}else{
			double totalPrice = order.getTotalPrice(order);
			totalPrice = totalPrice;

			order.displayOrder(order.getOrderID());
			System.out.println("==================================================================");
			System.out.printf(" Total Price: %.2f" , totalPrice);

			System.out.println("\n------------------------ Payment Details ------------------------- ");
			System.out.println("\n Card No : ");
			String cardNo = scanner.nextLine();
			System.out.println(" CCV : ");
			String ccv = scanner.nextLine();
		}
	}

	public void deleteOrder(String clientID){
		Scanner scanner = new Scanner(System.in);
		//this.order.displayOrder();
		System.out.print(" Choose order to remove(order ID) : ");
		String orderID = scanner.nextLine();

		Integer index = this.order.searchDeleteOrder(orderID);

		if(index == null){
			System.out.println(" No such order");
		}else{
			System.out.printf(" The order you wish to delete is Order %d\n",index+1);
			order.deleteOrder(index);	
		}
	}

	public static void main(String[] args) {
		OrderController c = new OrderController();
		c.maintainOrder();
	}

	public void maintainOrder(){

		Scanner scanner = new Scanner(System.in);
		Order order = new Order();
		order.displayOrder();
		Order index = null;

		String orderID;
		String choice = new String();

		do{
			System.out.println("==================================================================");

			System.out.println("------------------------- Maintain order -------------------------");
			System.out.print(" Enter order ID: ");
			orderID =scanner.nextLine();

			index = order.getOrder(orderID);

			if(index == null){
				System.out.println(" No such order");
			}else{
				System.out.println(" 1. Add item into order");
				System.out.println(" 2. Delete order");
				System.out.print(" ==> ");
				choice = scanner.nextLine();

				switch (choice){
				case "1":
					Item item;
					do {
						String itemId;
						int quantity;
						item = new Item();

						System.out.print(" Enter item ID: ");
						itemId = scanner.nextLine();

						System.out.print(" Enter quantity: ");
						quantity=scanner.nextInt();
						scanner.nextLine();

						item = item.searchForItem(itemId);
						if(item == null){
							System.out.println(" Error item ID");
						}else{
							System.out.printf(" Item added successfully into Order %s\n", orderID);
							order.addItem(index, item,quantity);
						}
					}while(item == null);

					break;

				case "2":

					Integer deleteOrder = order.getOrderIndex(index.getOrderID());
					if(deleteOrder ==null){
						System.out.println(" Invalid Order");
					}else{
						order.deleteOrder(deleteOrder);
					}

					break;

				default:
					break;
				}
			}
		}while(index == null);

	}

	public void viewBillingStatement(String clientID)
	{
		Order clientOrder=new Order();
		clientOrder.readOrderFile();
		clientOrder.viewClientOrder(clientID);
	}


	public void generateSalesReport()
	{
		Scanner scanner=new Scanner(System.in);
		Order adminOrder=new Order();
		System.out.println("------------------------------------------------------------------");
		System.out.println("\t   ~ WELCOME TO BOOKSTORE INVOICING SYSTEM ~");
		System.out.println("------------------------------------------------------------------");
		System.out.println("		         | GENERATE SALES REPORT |");
		System.out.println("------------------------------------------------------------------");
		System.out.println(" Type of sales report ");
		System.out.println(" 1. Daily sales report ");
		System.out.println(" 2. Monthly sales report ");
		System.out.println(" 3. Yearly sales report ");
		System.out.print(" Enter type of sales report: ");
		int salesReportType = scanner.nextInt();
		scanner.nextLine();

		switch(salesReportType) {

		case 1:{

			System.out.println(" Enter date (23/04/2023): ");
			String date=scanner.nextLine();
			adminOrder.generateDailySalesReport(date);
			break;
		}
		case 2:{

			System.out.println(" Enter month and year (04/2023) : ");
			String month=scanner.nextLine();
			adminOrder.generateMonthlySalesReport(month);
			break;
		}
		case 3:{

			System.out.println(" Enter  year (2023) : ");
			String year=scanner.nextLine();
			adminOrder.generateYearlySalesReport(year);
			break;
		}
		}     
	}
}