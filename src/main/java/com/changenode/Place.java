package com.changenode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.gson.Gson;

//import com.google.gson.Gson;

public class Place {
    public String name;
    public int[] locations;
    private ArrayList<String> pre;
    private ArrayList<String> post; 
    public Place(String n, int[] l, ArrayList<String> pr, ArrayList<String> po ) {
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
    public void addarcP2T(arcP2T  a) { post.add(a.name);}
    public void addarcT2P(arcT2P  a) { pre.add(a.name);}
    public void removearcP2T(arcP2T  a) { post.remove(a.name);}
    public void removearcT2P(arcT2P  a) { pre.remove(a.name);}
   
   public String myString(){
       return "name: "+name+"  pre: "+pre+"  post: "+post;
   }
      public String PlacetoJson() {
          Gson g = new Gson();
          String s = g.toJson(this);
          return s;
    } 
    public static  Place getJson() {   
        System.out.println("getJson Starting");
        Place result = null;     
        try  {
            Path path = Paths.get("/Users/dstr/Documents/GitHub/PetriNetBoxCalculus/target/classes/com/changenode/p1.json");
            byte[] encoded = Files.readAllBytes(path);    
            String content = new String(encoded);
           System.out.println(content+"\n***\n");           
             result = new Gson().fromJson(content,Place.class);
            System.out.println(result.myString()); 
            System.out.println("getJson Ending"); 
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public static void main(String[] args) {
        int[] ixs = new int[2];
        ixs[0] = 12;
        Place p1 = new Place("Pl1", ixs);
        String ps = p1.PlacetoJson();
        System.out.println(ps);
    }
}
