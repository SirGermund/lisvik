package com.example.lisvikproject.Models;

public class UserInfo {

    public String nameStr, ageStr, emailStr;

    public UserInfo(String name, String age, String email)
    {
        this.nameStr=name;
        this.ageStr=age;
        this.emailStr=email;
    }

    public UserInfo(){}

    public String getName(){return nameStr;}
    public void setName(String name){this.nameStr=name;}

    public String getAge(){return ageStr;}
    public void setAge(String age){this.ageStr=age;}

    public String getEmail(){return emailStr;}
    public void setEmail(String email){this.emailStr=email;}
}
