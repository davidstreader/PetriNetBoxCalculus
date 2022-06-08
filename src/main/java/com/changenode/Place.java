package com.changenode;
import java.util.ArrayList;

//import com.google.gson.Gson;

public class Place {
    public String name;
    public int[] locations;
    private ArrayList<arcT2P> pre;
    private ArrayList<arcP2T> post; 
    public Place(String n, int[] l, ArrayList<arcT2P> pr, ArrayList<arcP2T> po ) {
        name = n;
        locations = l;
        pre = pr;
        post = po;
    }
    public Place(String n, int[] l) {
        name = n;
        locations = l;
        pre = new ArrayList<>();
        post = new ArrayList<>();
    }
    public void addarcP2T(arcP2T  a) { post.add(a);}
    public void addarcT2P(arcT2P  a) { pre.add(a);}
    public void removearcP2T(arcP2T  a) { post.remove(a);}
    public void removearcT2P(arcT2P  a) { pre.remove(a);}
   
   
    //  public String toJson() {
    //      Gson g = new Gson();
    // } 
}
