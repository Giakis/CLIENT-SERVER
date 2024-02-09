import java.util.ArrayList;

public class Account {
    public String userName;
    public ArrayList<Message> messageBox;
    public Integer authToken;
    public static Integer temp= 1000;

    public Account(String userName){
        this.userName=userName;
        this.messageBox=new ArrayList<>();
        this.authToken=temp;
        temp+=1;
    }

    public String getUserName(){return userName;}

    public Integer getAuthToken(){return authToken;}

}
