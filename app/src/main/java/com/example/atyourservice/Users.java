package com.example.atyourservice;

import java.io.Serializable;

public class Users implements Serializable {
     public String Id="", FullName="", Email="", Password="", Ballance="";

    public Users(String Id, String FullName, String Email, String Password, String Balance){
        this.Id = Id;
        this.FullName = FullName;
        this.Email = Email;
        this.Password = Password;
        this.Ballance = Balance;
    }

}
