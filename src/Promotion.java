package Domain;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Promotion {

	private String promotionID;
	private double promotionDiscount;
	private int promotionAmt;


	public Promotion(String promotionID, double promotionDiscount, int promotionAmt)
	{
		this.promotionID = promotionID;
		this.promotionDiscount = promotionDiscount;
		this.promotionAmt = promotionAmt;
	}

	public Promotion() {

	}

	public String getPromoID()
	{
		return promotionID;
	}

	public double getPromoDiscount()
	{
		return promotionDiscount;
	}

	public int getPromoAmt()
	{
		return promotionAmt;
	}

	public void setPromotionCode(String promotionID)
	{
		this.promotionID = promotionID;
	}

	public void setPromoAmt(int promotionAmt)
	{
		this.promotionAmt = promotionAmt;
	}

	public void displayPromoDetails(ArrayList<Promotion> promotionLst)
	{
		//readPromotionFile();
		System.out.println("------------------------------------------------------------------");
		System.out.println("\t   ~ WELCOME TO BOOKSTORE INVOICING SYSTEM ~");
		System.out.println("------------------------------------------------------------------");
		System.out.println("\t\t    | PROMOTION LIST |\t\t");
		System.out.println("------------------------------------------------------------------");
		System.out.println("  No.  |  Promotion ID\t|\tPromotion Disocunt\t| Amount |");
		System.out.println("------------------------------------------------------------------");
		for(int i =0; i < promotionLst.size(); i++) {
			System.out.printf("  %-2d.\t  %-6s\t  %15.2f\t\t   %-53d\n", i+1, promotionLst.get(i).getPromoID(),
					promotionLst.get(i).getPromoDiscount(), promotionLst.get(i).getPromoAmt());
		}
		System.out.println("==================================================================\n");
	}
	
	public void displaySpecificPromoDetails()
	{
		//readPromotionFile();
		System.out.println("------------------------------------------------------------------");
		System.out.println("\t   ~ WELCOME TO BOOKSTORE INVOICING SYSTEM ~");
		System.out.println("------------------------------------------------------------------");
		System.out.println("\t\t    | PROMOTION LIST |\t\t");
		System.out.println("------------------------------------------------------------------");
		System.out.println("  No.  |  Promotion ID\t|\tPromotion Disocunt\t| Amount |");
		System.out.println("------------------------------------------------------------------");
			System.out.printf("  %-2d.\t  %-6s\t  %15.2f\t\t   %-53d\n", 1,promotionID,
					promotionDiscount, promotionAmt);
		System.out.println("==================================================================\n");
	}



	public Promotion validatePromotion(String promotionID,ArrayList<Promotion> promotionLst) {

		Promotion validPromotion = null;

		for(int i = 0 ; i < promotionLst.size() ;i++) {
			if(promotionLst.get(i).getPromoID().contentEquals(promotionID)) {
				validPromotion = promotionLst.get(i);
				break;
			}
		}
		return validPromotion;
	}

	public double applyPromoCode(Promotion validPromotion) {

		return validPromotion.getPromoDiscount();
	}
}