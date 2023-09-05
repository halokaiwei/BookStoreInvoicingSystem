package Domain;

public class User
{
    private String username;
    private String userID;
    private String password;

    //Setter
    public void setName(String username)
    {
        this.username = username;
    }

    public void setID(String ID)
    {
        boolean validID = false;
        //L-DDD
        if(ID.length() == 5)
        {
            char IDCase = ID.charAt(0);
            if(IDCase == 'A' || IDCase == 'C')
            {
                String IDDigit = ID.substring(2); //should be substring (1)
                boolean allDigit = IDDigit.matches("[0-9]+");
                if (allDigit == true)
                {
                    validID = true;
                    this.userID = ID;
                }
            }
        }
        if(validID == false)
        {
            this.userID = "INVALID USER ID";
        }
    }

    //Getter
    public String getName()
    {
        return username;
    }

    public String getUserID()
    {
        return userID;
    }

    public String getPassword()
    {
        return password;
    }

    //Constructor
    public User(String name,String ID,String password)
    {
        this.username = name;
        this.password = password;
        setID(ID);
    }

	public User() {
		// TODO Auto-generated constructor stub
	}
}
