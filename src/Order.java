package Domain;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Order {

	private ArrayList<Order>orders;
	private String orderID;
	private ArrayList<Item> items;
	private String date;
	private ArrayList<Integer>quantity;
	private String clientID;
	public Order(){
		this.orders = new ArrayList<>();
		items = new ArrayList<>();
		quantity = new ArrayList<>();
	}

	public Order(String orderID, Item item, int quantity, String date,String clientID){
		this.orders = new ArrayList<>();
		this.items = new ArrayList<>();
		this.quantity = new ArrayList<>();
		this.date = new String();
		//System.out.println(clientID);
		setClientID(clientID);
		setDate(date);
		setOrderID(orderID);
		setItem(item);
		setQuantity(quantity);
	}

	public void setOrderID(String orderID){
		this.orderID = orderID;
	}
	public void setClientID(String clientID){
		this.clientID = clientID;
	}

	public void setOrders(Order order){
		this.orders.add(order);
	}

	public void setItem(Item item) {
		this.items.add(item);
	}

	public void setDate(String date){
		this.date = date;
	}

	public void setQuantity(int quantity){
		this.quantity.add(quantity);
	}

	public String getOrderID(){
		return this.orderID;
	}

	public void readOrderFile() {
		String source = "Order.txt"; //put file name here
		String line = "";
		try
		{
			orders.clear();
			int count = -1;

			BufferedReader br = new BufferedReader (new FileReader (source)); //read in file--create obj
			while ((line = br.readLine()) != null) //file line not empty--go into end
			{

				String [] it=line.split("#");//split line by comma
				Order order;

				String orderID = it[0];
				String itemID = it[1];
				int quantity = Integer.parseInt(it[2]);
				String date = it[3];
				String clientId=it[4];
				Item item = new Item().searchForItem(itemID);
				if(count == -1) {
					count++;
					order = new Order(orderID, item, quantity,date,clientId);

					this.orders.add(order);

				}else if(count != -1) {
					boolean found = false;

					if(this.orders.get(count).getOrderID().contentEquals(orderID)) {
						this.orders.get(count).setItem(item);
						this.orders.get(count).setQuantity(quantity);
						found = true;
					}else {
						order = new Order(orderID, item, quantity,date,clientId);
						this.orders.add(order);
						count++;
					}
				}
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


	public  void saveToOrderFile(){
		try
		{ //try first
			File itemFile = new File("Order.txt");
			FileWriter fileWriter = new FileWriter(itemFile,false);
			PrintWriter printWriter = new PrintWriter(fileWriter);

			for(int i = 0; i < this.orders.size(); i++)
			{
				for(int k = 0; k < this.orders.get(i).items.size(); k++) {
					printWriter.println(orders.get(i).getOrderID()+"#"+orders.get(i).items.get(k).getItemID()+"#"+orders.get(i).quantity.get(k)+"#"+orders.get(i).date+"#"+orders.get(i).getClientID());
				}
			}
			printWriter.close();
		}
		catch (IOException e)
		{
			System.out.println("Error");
		}
	}

	public void displayOrder(){
		readOrderFile();
		for(int i = 0; i < orders.size(); i++){
			System.out.printf(" Order %d\n", i+1);
			System.out.println("==================================================================");
			System.out.println(" Client User ID: " + this.orders.get(i).getClientID());
			System.out.println(" Order ID: " + this.orders.get(i).getOrderID());
			System.out.println(" Order Date: " + this.orders.get(i).date);
			System.out.println("==================================================================");
			for(int k = 0; k < orders.get(i).items.size(); k++){
				System.out.println(" Item ID: " + orders.get(i).items.get(k).getItemID());
				System.out.println(" Item Description: " + orders.get(i).items.get(k).getItemDescription());
				System.out.println(" Item Price: RM" + orders.get(i).items.get(k).getPrice());
				System.out.println(" Quantity : " + orders.get(i).quantity.get(k) + "\n\n");
			}
		}
	}

	public void displayOrder(String orderID){
		readOrderFile();
		for(int i = 0; i < orders.size(); i++){
			if(orders.get(i).getOrderID().contentEquals(orderID)){
				System.out.println("==================================================================");
				System.out.println(" Client User ID: " + this.orders.get(i).getClientID());
				System.out.println(" Order ID: " + this.orders.get(i).getOrderID());
				System.out.println(" Order Date: " + this.orders.get(i).date);
				System.out.println("==================================================================");
				for(int k = 0; k < orders.get(i).items.size(); k++){
					System.out.println(" Item ID: " + orders.get(i).items.get(k).getItemID());
					System.out.println(" Item Description: " + orders.get(i).items.get(k).getItemDescription());
					System.out.println(" Item Price: RM" + orders.get(i).items.get(k).getPrice());
					System.out.println(" Quantity : " + orders.get(i).quantity.get(k) + "\n");
				}
			}
		}
	}
	public void createOrder(Order order){
		readOrderFile();
		this.setOrders(order);
		saveToOrderFile();
	}


	public Integer searchDeleteOrder(String deleteOrderID) {
		readOrderFile();
		Integer index = null;
		for(int i = 0; i < this.orders.size(); i++) {
			if(this.orders.get(i).getOrderID().contentEquals(deleteOrderID)) {
				index = i;
				break;
			}
		}
		return index;
	}

	public void addItem(Order order, Item item, int quantity){
		readOrderFile();
		for(int i = 0; i < orders.size(); i++){
			if(this.orders.get(i).orderID.contentEquals(order.orderID)){
				for(int m = 0; m < order.items.size(); m++){
					this.orders.get(i).items.add(item);
					this.orders.get(i).quantity.add(quantity);
					break;
				}
			}
		}
		saveToOrderFile();
	}

	public Order getOrder(String orderID){
		Order order = null;
		for(int i = 0; i < this.orders.size(); i++) {
			if(this.orders.get(i).getOrderID().contentEquals(orderID)) {
				order = this.orders.get(i);
				break;
			}
		}
		return order;
	}

	public Integer getOrderIndex(String orderID){
		Integer orderId = null;
		for(int i = 0; i < this.orders.size(); i++) {
			if(this.orders.get(i).getOrderID().contentEquals(orderID)) {
				orderId = i;
				break;
			}
		}
		return orderId;
	}

	public double getTotalPrice(Order order){
		readOrderFile();
		double totalPrice = 0.0;
		for(int i = 0 ; i < orders.size(); i++){
			if(orders.get(i).getOrderID().contentEquals(order.orderID)){
				for(int j =0 ;j < orders.get(i).items.size(); j++){
					totalPrice += this.items.get(j).getPrice() * this.quantity.get(j);
				}
			}
		}
		return totalPrice;
	}

	public void deleteOrder(Integer index){
		readOrderFile();
		this.orders.remove((int)index);
		saveToOrderFile();
		System.out.println(" Order cancel successfully!\n");
		//displayOrder();
	}

	public String getClientID()
	{
		return clientID;
	}


	public void viewClientOrder(String clientID)
	{
		ArrayList<Order> clientOrders=new ArrayList<Order>();

		for(int i=0;i<orders.size();i++)
		{
			Order order=orders.get(i);

			if(order.getClientID().equals(clientID))
			{
				clientOrders.add(order);
			}
		}

		System.out.println("==================================================================");
		System.out.println(" No.  ===== OrderID ======== Order Date ==========================");
		//print client orderID
		for(int i=0;i<clientOrders.size();i++)
		{
			System.out.printf(" %2d.\t   %5s\t%15s\n", (i+1),clientOrders.get(i).getOrderID(),clientOrders.get(i).getOrderDate());
		}
		System.out.println("------------------------------------------------------------------");

		Scanner scanner=new Scanner(System.in);
		System.out.print(" Enter orderID: ");
		String orderID=scanner.nextLine();

		searchCustomerOrder(orderID,clientOrders);
	}

	public void searchCustomerOrder(String orderID,ArrayList<Order> clientOrders) {

		Order orderSelected=new Order();
		for(int i = 0; i < clientOrders.size(); i++) {
			if(clientOrders.get(i).getOrderID().equals(orderID)) {
				orderSelected=clientOrders.get(i);

			}
		}

		displayOrder(orderSelected);
	}


	public void displayOrder(Order orderItem){

		double ttlPrice=0.0;

		System.out.println("==================================================================");
		System.out.println(" Client User ID: " + orderItem.getClientID());
		System.out.println(" Order ID: " + orderItem.getOrderID());
		System.out.println(" Order Date: " + orderItem.date);
		System.out.println("==================================================================");
		for(int k = 0; k < orderItem.items.size(); k++){
			System.out.println(" Item ID: " + orderItem.items.get(k).getItemID());
			System.out.println(" Item Description: " + orderItem.items.get(k).getItemDescription());
			System.out.println(" Item Price: RM" + orderItem.items.get(k).getPrice());
			System.out.println(" Quantity : " + orderItem.quantity.get(k) + "\n");
			ttlPrice+=orderItem.items.get(k).getPrice()*orderItem.quantity.get(k);
		}


		System.out.printf(" Total Price : RM %.2f%n", ttlPrice);

	}

	public String getOrderDate(){
		return date;
	}

	public void generateDailySalesReport(String date)
	{
		readOrderFile();

		ArrayList <Item> dailyOrderItem=new ArrayList<Item>();
		ArrayList<Integer>dailyOrderQuantity=new ArrayList<Integer>();
		for(int i=0;i<orders.size();i++)
		{
			if(orders.get(i).getOrderDate().equals(date))
			{

				for(int k=0;k<orders.get(i).items.size();k++)
				{
					boolean found=false;
					for(int j=0;j<dailyOrderItem.size();j++)
					{
						if(dailyOrderItem.get(j).getItemID().equals(orders.get(i).items.get(k).getItemID()))
						{


							int itemQuantity = dailyOrderQuantity.get(j);


							itemQuantity += orders.get(i).quantity.get(k);
							dailyOrderQuantity.set(j, itemQuantity);
							found=true;
						}


					}
					if(found==false) {
						dailyOrderItem.add(orders.get(i).items.get(k));
						dailyOrderQuantity.add(orders.get(i).quantity.get(k));}
				}

			}
		}
		viewDailySalesReport(date, dailyOrderItem,dailyOrderQuantity);
	}

	public void viewDailySalesReport(String date,ArrayList <Item> dailyOrderItem,ArrayList<Integer>dailyOrderQuantity)
	{
		double totalDailySales=0.0;
		for(int i=0;i<dailyOrderItem.size();i++)
		{
			totalDailySales+=dailyOrderItem.get(i).getPrice()*dailyOrderQuantity.get(i);
		}
		int unitSold=0;
		for(int i:dailyOrderQuantity) {
			unitSold+=i;
		}
		System.out.println(" Daily Sales Report" );
		System.out.println("------------------------------------------------------------------");
		System.out.println(" Date\t: " +date);
		System.out.printf(" Total Sales : RM %.2f%n", totalDailySales);
		System.out.println(" Total unit sold : " + unitSold);
		System.out.println("==================================================================");
		System.out.println(" No.  ItemID   Price     Quantity");
		for(int i=0;i<dailyOrderItem.size();i++)
		{
			System.out.printf(" %2d.  %5s    %6.2f      %3d\n",(i+1),dailyOrderItem.get(i).getItemID(),
					dailyOrderItem.get(i).getPrice(),dailyOrderQuantity.get(i));
		}
	}

	public void generateMonthlySalesReport(String month)
	{
		readOrderFile();

		ArrayList <Item> monthlyOrderItem=new ArrayList<Item>();
		ArrayList<Integer>monthlyOrderQuantity=new ArrayList<Integer>();
		for(int i=0;i<orders.size();i++)
		{
			if(orders.get(i).getOrderDate().substring(3).equals(month))
			{
				for(int k=0;k<orders.get(i).items.size();k++)
				{
					boolean found=false;
					for(int j=0;j<monthlyOrderItem.size();j++)
					{
						if(monthlyOrderItem.get(j).getItemID().equals(orders.get(i).items.get(k).getItemID()))
						{
							int itemQuantity = monthlyOrderQuantity.get(j);

							itemQuantity += orders.get(i).quantity.get(k);
							monthlyOrderQuantity.set(j, itemQuantity);
							found=true;
						}
					}
					if(found==false) {
						monthlyOrderItem.add(orders.get(i).items.get(k));
						monthlyOrderQuantity.add(orders.get(i).quantity.get(k));}
				}
			}
		}
		viewMonthlySalesReport(month, monthlyOrderItem,monthlyOrderQuantity);
	}
	public void viewMonthlySalesReport(String month,ArrayList <Item> monthlyOrderItem,ArrayList<Integer>monthlyOrderQuantity)
	{
		double totalMonthlySales=0.0;
		for(int i=0;i<monthlyOrderItem.size();i++)
		{
			totalMonthlySales+=monthlyOrderItem.get(i).getPrice()*monthlyOrderQuantity.get(i);
		}
		int unitSold=0;
		for(int i:monthlyOrderQuantity) {
			unitSold+=i;
		}
		System.out.println(" Monthly Sales Report" );
		System.out.println("------------------------------------------------------------------");
		System.out.println(" Month : " +month);

		System.out.printf(" Total Sales : RM %.2f%n", totalMonthlySales);
		System.out.println(" Total unit sold: " + unitSold);
		System.out.println("==================================================================");
		System.out.println(" No.  ItemID   Price     Quantity");
		for(int i=0;i<monthlyOrderItem.size();i++)
		{
			System.out.printf(" %2d.  %5s    %6.2f      %3d\n",(i+1),monthlyOrderItem.get(i).getItemID(),
					monthlyOrderItem.get(i).getPrice(),monthlyOrderQuantity.get(i));		}
	}

	public void generateYearlySalesReport(String year)
	{

		readOrderFile();

		ArrayList <Item> yearlyOrderItem=new ArrayList<Item>();
		ArrayList<Integer>yearlyOrderQuantity=new ArrayList<Integer>();
		for(int i=0;i<orders.size();i++)
		{
			if(orders.get(i).getOrderDate().substring(6).equals(year))
			{

				for(int k=0;k<orders.get(i).items.size();k++)
				{
					boolean found=false;
					for(int j=0;j<yearlyOrderItem.size();j++)
					{
						if(yearlyOrderItem.get(j).getItemID().equals(orders.get(i).items.get(k).getItemID()))
						{


							int itemQuantity = yearlyOrderQuantity.get(j);


							itemQuantity += orders.get(i).quantity.get(k);
							yearlyOrderQuantity.set(j, itemQuantity);
							found=true;
						}


					}
					if(found==false) {
						yearlyOrderItem.add(orders.get(i).items.get(k));
						yearlyOrderQuantity.add(orders.get(i).quantity.get(k));}
				}

			}
		}
		viewYearlySalesReport(year, yearlyOrderItem,yearlyOrderQuantity);
	}

	public void viewYearlySalesReport(String year,ArrayList <Item> yearlyOrderItem,ArrayList<Integer>yearlyOrderQuantity)
	{
		double totalYearlySales=0.0;
		for(int i=0;i<yearlyOrderItem.size();i++)
		{
			totalYearlySales+=yearlyOrderItem.get(i).getPrice()*yearlyOrderQuantity.get(i);
		}
		int unitSold=0;
		for(int i:yearlyOrderQuantity) {
			unitSold+=i;
		}
		System.out.println(" Yaerly Sales Report" );
		System.out.println("------------------------------------------------------------------");
		System.out.println(" Year : " +year);

		System.out.printf(" Total Sales : RM %.2f%n", totalYearlySales);
		System.out.println(" Total unit sold: " + unitSold);
		System.out.println("==================================================================");
		System.out.println(" No.  ItemID   Price     Quantity");
		for(int i=0;i<yearlyOrderItem.size();i++)
		{
			System.out.printf(" %2d.  %5s    %6.2f      %3d\n",(i+1),yearlyOrderItem.get(i).getItemID(),
					yearlyOrderItem.get(i).getPrice(),yearlyOrderQuantity.get(i));		}
	}
}