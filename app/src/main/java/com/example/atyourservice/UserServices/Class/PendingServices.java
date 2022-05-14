package com.example.atyourservice.UserServices.Class;

public class PendingServices {
    public  String id="", Service_Name="", User_id="",Full_Name="", Nationality_Number="", Request_Date="", Replay_Date="", Service_Status="", Payment_Status="", Service_Price="";

    public PendingServices(String id, String service_Name, String user_id, String fullName, String nationality_Number, String request_Date, String replay_Date, String service_Status, String payment_Status, String service_Price) {
        this.id = id;
        Service_Name = service_Name;
        User_id = user_id;
        Full_Name = fullName;
        Nationality_Number = nationality_Number;
        Request_Date = request_Date;
        Replay_Date = replay_Date;
        Service_Status = service_Status;
        Payment_Status = payment_Status;
        Service_Price = service_Price;
    }
    public PendingServices(){

    }


}
