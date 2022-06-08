package com.changenode;
//import com.google.gson.Gson;
public class arcP2T {
    public String name;
    public Transition post;
    public String guard;
    public Place pre; 
    public arcP2T(String n,  Place pr , String g, Transition po ) {
        name = n;
        guard = g;
        pre = pr;
        post = po;
    }
    //  public String toJson() {
    //      Gson g = new Gson();
    // } 
}
