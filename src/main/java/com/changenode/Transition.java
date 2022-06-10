package com.changenode;
   import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
//import com.google.gson.Gson;
//import java.util.TreeMap;

import com.google.gson.Gson;

public class Transition {
    //public enum TrType {  active , passive, send, receive}   
        public String name;
        public String eventName;
        public TrType ty;
        private ArrayList<String> pre = new ArrayList<String> ();
        private ArrayList<String> post  = new ArrayList<String> (); 
    public Transition(String n, String e,TrType t, ArrayList<String> pr, ArrayList<String> po ) {
            name = n;
            ty = t;
            eventName = e;
            pre = pr;
            post = po;
    }
    public Transition(String n, String e,TrType t ) {
        name = n;
        ty = t;
        eventName = e;
        pre = new ArrayList<String> ();
        post = new ArrayList<String> ();
}
    public void addarcP2T(arcP2T  a) { pre.add(a.name);}
    public void addarcT2P(arcT2P  a) { post.add(a.name);}
    public void removearcP2T(arcP2T  a) { pre.remove(a.name);}
    public void removearcT2P(arcT2P  a) { post.remove(a.name);}
    public String myString(){
        return "  pre: "+pre+ " name: "+name+" evnt "+eventName+" type; "+ ty+"  post: "+post;
    }
    public static  Transition getJson() {   
        System.out.println("getJson Starting");
        Transition result = null;     
        try  {
            Path path = Paths.get("/Users/dstr/Documents/GitHub/PetriNetBoxCalculus/target/classes/com/changenode/t1.json");
            byte[] encoded = Files.readAllBytes(path);    
            String content = new String(encoded);
           System.out.println(content+"\n***\n");           
             result = new Gson().fromJson(content,Transition.class);
            System.out.println(result.myString()); 
            System.out.println("getJson Ending"); 
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }    //  public String toJson() {
        //      Gson g = new Gson();
        // } 
 }

