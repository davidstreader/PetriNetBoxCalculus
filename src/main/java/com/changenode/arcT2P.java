package com.changenode;
//import com.google.gson.Gson;
public class arcT2P {
    public String name;
    public Transition pre;
    public String bool;
    public Place post; 
    public arcT2P(String n, Transition  pr , String b, Place  po ) {
        name = n;
        bool = b;
        pre = pr;
        post = po;
    }
    //  public String toJson() {
    //      Gson g = new Gson();
    // } 
}
