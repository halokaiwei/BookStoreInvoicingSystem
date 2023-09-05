package Domain;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Item {
    private String itemID;
    private String itemDescription;
    private double price;
    private ArrayList<Item> items;

    private File file = new File("Item.txt");

    public Item(String itemID, String itemDesc, double price){
        this.itemID = itemID;
        this.itemDescription = itemDesc;
        this.price = price;
        this.items = new ArrayList<>();

    }

    public Item() {
        this.items = new ArrayList<>();
    }


    public String getItemID() {
        return itemID;
    }
    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemDescription() {
        return itemDescription;
    }
    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public void readItemFile() {
        items.clear();
        String source = "Item.txt"; //put file name here
        String line = "";
        try
        {
            BufferedReader br = new BufferedReader (new FileReader (source)); //read in file--create obj
            while ((line = br.readLine()) != null) //file line not empty--go into end
            {
                String [] it=line.split("#");//split line by comma
                Item item = null;
                String itemID = it[0];
                String itemDescp = it[1];
                double itemPrice = Double.parseDouble(it[2]);
                item = new Item(itemID, itemDescp, itemPrice);
                items.add(item);
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

    public void saveToItemFile(){
        try { //try first
            File itemFile = new File("Item.txt");
            FileWriter fileWriter = new FileWriter(itemFile,false);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            for(int i = 0; i < items.size(); i++)
            {
                printWriter.println(items.get(i).getItemID()+"#"+items.get(i).getItemDescription() +"#"+items.get(i).getPrice());
            }
            printWriter.close();
        }
        catch (IOException e)
        {
            System.out.println("Error");

        }
    }

    public void displayItemList() {
    	readItemFile();
    	System.out.println("------------------------------------------------------------------");
    	System.out.println("\t   ~ WELCOME TO BOOKSTORE INVOICING SYSTEM ~");
    	System.out.println("------------------------------------------------------------------");
    	System.out.println("\t\t    | ITEM LIST |\t\tTotal: " + items.size());
    	System.out.println("------------------------------------------------------------------");
    	System.out.println("  No.  |\tItem ID\t\t|\tItem Price(RM)\t\t|");
    	System.out.println("------------------------------------------------------------------");        
    	for(int i=0; i < this.items.size(); i++) 
    	{
    		System.out.printf(" %2d.   |\t%5s\t\t|\t    %.2f \t\t|\n",(i+1),this.items.get(i).getItemID(),this.items.get(i).getPrice());
    	}
    	System.out.println("------------------------------------------------------------------");        
    }

    public Item searchForItem(String itemID) {
        readItemFile();
        Item targetItem = null;
        targetItem = null;
        for(int i=0; i < this.items.size(); i++) {
            if(this.items.get(i).getItemID().contentEquals(itemID)) {
                targetItem = this.items.get(i);
                break;
            }
        }
        return targetItem;
    }

    public void addItem(Item item){
        readItemFile();
        items.add(item);
    }

    public void displayItemDetails(Item item){
        System.out.println(" Item ID: " + item.getItemID());
        System.out.println(" Item Description: " + item.getItemDescription());
        System.out.println(" Item Price: " + item.price);
    }

    public void deleteSelectedItem(Item item){
        readItemFile();
        int index = 0;
        for(int i = 0 ; i < items.size(); i++){
            if(items.get(i).getItemID().contentEquals(item.itemID)){
                index = i;
                break;
            }
        }
        items.remove(index);
    }

    public void modifyItem(Item item){
        readItemFile();

        int index = 0;
        for(int i=0; i < this.items.size(); i++) {
            if(this.items.get(i).getItemID().equals(item.itemID)) {
                index = i;
                break;
            }
        }

        System.out.println(index);

        String modifyContent;
        Scanner scanner = new Scanner(System.in);
        System.out.println(" You want to modify: ");
        System.out.println(" 1. Item ID");
        System.out.println(" 2. Item Description");
        System.out.println(" 3. Item Price");
        String choice = scanner.nextLine();

        switch (choice){
            case "1":
                System.out.println(" Enter New Item ID: " );
                modifyContent = scanner.nextLine();
                items.get(index).setItemID(modifyContent);
                break;

            case "2":
                System.out.println(" Enter New Description: " );
                modifyContent = scanner.nextLine();
                items.get(index).setItemDescription(modifyContent);
                break;

            case "3":
                System.out.println(" Enter New Item Price: " );
                modifyContent = scanner.nextLine();
                items.get(index).setPrice(Double.parseDouble(modifyContent));
                break;

            default:
                System.out.println(" Invalid Choice");
                break;
        }

    }

}