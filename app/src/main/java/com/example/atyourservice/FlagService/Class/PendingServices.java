package com.example.atyourservice.FlagService.Class;

public class PendingServices {
    public  String Service_Name="", User_id="", Nationality_Number="", Request_Date="", Replay_Date="", Service_Status="", Payment_Status="", Service_Price="";

    public PendingServices(String service_Name, String user_id, String nationality_Number, String request_Date, String replay_Date, String service_Status, String payment_Status, String service_Price) {
        Service_Name = service_Name;
        User_id = user_id;
        Nationality_Number = nationality_Number;
        Request_Date = request_Date;
        Replay_Date = replay_Date;
        Service_Status = service_Status;
        Payment_Status = payment_Status;
        Service_Price = service_Price;
    }
}
