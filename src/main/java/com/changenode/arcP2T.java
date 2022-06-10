package com.changenode;
//import com.google.gson.Gson;
public class arcP2T {
    public String name;
    public String post;
    public String guard;
    public String pre; 
    public arcP2T(String n,  String pr , String g, String po ) {
        name = n;
        guard = g;
        pre = pr;
        post = po;
    }
    //  public String toJson() {
    //      Gson g = new Gson();
    // } 
}
