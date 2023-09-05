package Domain;

import java.util.Scanner;

public class Administrator extends User{

    private Item items;
    private String jobTitle;

    public Administrator(){
        super();
        items = new Item();
    }

	public Administrator(String adminName, String adminID, String adminPassword, String jobTitle) {
		super(adminName, adminID, adminPassword);
		this.jobTitle = jobTitle;
	}
	
    public void manageBookStoreItem(){
        boolean cont = false;
        //Before this might be the MAIN System MENU
        do {
            cont=false;
            System.out.println(" ----------- Manage BookStore Items ------------- ");
            System.out.println(" Please select your operation choice: ");
            System.out.println(" 1. Add item");
            System.out.println(" 2. Delete item");
            System.out.println(" 3. Modify an item");
            System.out.println(" 4. Exit");
            System.out.println(" ------------------------------------------------ ");
            System.out.print(" Option: ");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            if (choice == 1) {
                scanner.nextLine();

                System.out.println();
                System.out.println(" Please enter the new item id: ");
                String itemID = scanner.nextLine();

                System.out.println(" Please enter the new item description: ");
                String itemDescription = scanner.nextLine();

                System.out.println(" Please enter the new item price: ");
                double price = scanner.nextDouble();

                Item item = new Item(itemID, itemDescription,price);
                items.addItem(item);
            }
            else if (choice == 2) {
                scanner.nextLine();

                System.out.println(" Please enter the item ID that you wish to delete: ");
                String itemID = scanner.nextLine();

                Item selectedDeleteItem = items.searchForItem(itemID);

                if (selectedDeleteItem == null) {	//not found
                    System.out.println(" Selected item ID for delete not found.");
                    System.out.println(" Return to menu...\n");
                    cont=true;
                    break;
                }
                items.displayItemDetails(selectedDeleteItem);
                items.deleteSelectedItem(selectedDeleteItem);
            }
            else if (choice == 3) {
                scanner.nextLine();

                System.out.println(" Please enter the item ID that you wish to modify: ");
                String searchID = scanner.nextLine();
                Item selectedModifyItem = items.searchForItem(searchID);
                if (selectedModifyItem == null ) {	//not found
                    System.out.println(" Selected item ID for modify not found.");
                    System.out.println(" Return to menu...\n");
                    cont=true;
                    break;
                }
                items.displayItemDetails(selectedModifyItem);
                items.modifyItem(selectedModifyItem);

            } else if (choice == 4) {
                System.out.println(" Exiting manage bookstore item function...");
                cont=false;
                break;
            }

            items.saveToItemFile();

            System.out.println(" Return to menu...\n");
            cont=true;
        } while(cont);
    }
    
    public void generateSalesReport()
	{
		OrderController adminOrderCtrl=new OrderController();
		adminOrderCtrl.generateSalesReport();
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

}