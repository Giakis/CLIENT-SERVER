public class Message {
    public boolean isRead;
    public String sender;
    public String receiver;
    public String body;
    public  Integer idMessage= 1;
    public  static Integer id= 1;



    public Message (boolean isRead,String sender,String receiver,String body){
        this.isRead=isRead;
        this.sender=sender;
        this.receiver=receiver;
        this.body=body;
        this.idMessage=id;
        id++;
    }
    public Integer getIdMessage(){return idMessage;}
    public boolean getIsRead(){ return isRead; }

}
