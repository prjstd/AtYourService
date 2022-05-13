package com.example.atyourservice;

import java.io.Serializable;

public class Users implements Serializable {
     public String Uid="", Full_Name="", Email="", Password="", Ballance="";

    public Users(String Uid, String Full_Name, String Email, String Password, String Balance){
        this.Uid = Uid;
        this.Full_Name = Full_Name;
        this.Email = Email;
        this.Password = Password;
        this.Ballance = Balance;
    }

}
