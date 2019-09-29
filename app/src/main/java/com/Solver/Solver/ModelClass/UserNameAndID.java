package com.Solver.Solver.ModelClass;

public class UserNameAndID {

    private String userID;
  private  String userName;

    public UserNameAndID(String userID, String userName) {
        this.userID = userID;
        this.userName = userName;
    }

    public UserNameAndID() {

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
