package Domain;

import java.util.ArrayList;
import java.util.Scanner;

public class ClientUser extends User{

    private String email;
    private int phoneNo;
    private String homeAddress;
    private ArrayList <ClientUser> clientUser = new ArrayList<ClientUser> ();

    public ClientUser(String clientUsername,String clientUserID,String clientUserPassword)
    {
        super(clientUsername, clientUserID, clientUserPassword);
        clientUser = new ArrayList<ClientUser> ();
    }

    public ClientUser(String clientUserID,String clientUsername,String clientUserPassword, int phoneNo,String email,String homeAddress)
    {
        super(clientUsername,clientUserID,clientUserPassword);
        this.email = email;
        this.homeAddress = homeAddress;
        this.phoneNo = phoneNo;

    }

    public ClientUser(ClientUser cu)
    {
        super(cu.getName(),cu.getUserID(),cu.getPassword());
        this.phoneNo = cu.phoneNo;
        this.email = cu.email;
        this.homeAddress = cu.homeAddress;

    }

    //getter
    public String getEmail()
    {
        return email;
    }

    public int getPhoneNo()
    {
        return phoneNo;
    }

    public String getHomeAddress()
    {
        return homeAddress;
    }

    //setter
    public void setEmail (String email)
    {
        this.email = email;
    }

    public void setTelNo (int phoneNo)
    {
        this.phoneNo = phoneNo;
    }

    public void setAddress (String homeAddress)
    {
        this.homeAddress = homeAddress;
    }

    public void clientUserMenu()
    {
        System.out.println("------------------------------------------------------------------");
        System.out.println("\t   ~ WELCOME TO BOOKSTORE INVOICING SYSTEM ~");
        System.out.println("------------------------------------------------------------------");
        System.out.println("\t\t       | CLIENT USER MENU |");
        System.out.println("------------------------------------------------------------------\n");
        System.out.println("\t\t   1. View Personal Details\n");
        System.out.println("\t\t   2. Create orders\n");
        System.out.println("\t\t   3. Cancel orders\n");
        System.out.println("\t\t   4. View Billing Statement\n");
        System.out.println("\t\t   5. Check Promotion\n");
        System.out.println("\t\t   6. Search For Items\n");
        System.out.println("==================================================================");
        System.out.println(" Enter 0 to quit this interface. ");
        System.out.print(" Option: ");
    }

    public  void displayClientUserDetails()
    {
        System.out.println("------------------------------------------------------------------");
        System.out.println("\t   ~ WELCOME TO BOOKSTORE INVOICING SYSTEM ~");
        System.out.println("------------------------------------------------------------------");
        System.out.println("\t\t    | CLIENT USER DETAILS |\t\t" );
        System.out.println("------------------------------------------------------------------");
        System.out.printf(" Client user ID: %-10s\t\tPassword : %-10s\n" , super.getUserID() , super.getPassword());
        System.out.printf("\n Name          : %-20s	\n" , super.getName());
        System.out.println("\n PhoneNo       : " + phoneNo);
        System.out.println("\n Email\t       : " + email);
        System.out.println("\n Address       : \n\n " + homeAddress);
        System.out.println("==================================================================\n");
    }

    public void createOrders(String clientID,ArrayList<Promotion> promotionLst) {
        OrderController orderController = new OrderController();
        orderController.createOrder(clientID,promotionLst);
    }

    public void cancelOrder(String clientID) {
    	Order clientOrder=new Order();
    	clientOrder.readOrderFile();
    	clientOrder.viewClientOrder(clientID);
    	OrderController orderController = new OrderController();
    	orderController.deleteOrder(clientID);
    }



}
