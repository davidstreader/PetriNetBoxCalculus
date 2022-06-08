package com.changenode;
import java.util.ArrayList;
   //import com.google.gson.Gson;
  enum TrType {  active , passive, send, receive}  
public class Transition {
    //public enum TrType {  active , passive, send, receive}   
        public String name;
        public TrType ty;
        private ArrayList<arcP2T> pre;
        private ArrayList<arcT2P> post; 
    public Transition(String n, TrType t, ArrayList<arcP2T> pr, ArrayList<arcT2P> po ) {
            name = n;
            ty = t;
            pre = pr;
            post = po;
    }
    public Transition(String n, TrType t ) {
        name = n;
        ty = t;
        pre = new ArrayList<>();
        post = new ArrayList<>();
}
    public void addarcP2T(arcP2T  a) { pre.add(a);}
    public void addarcT2P(arcT2P  a) { post.add(a);}
    public void removearcP2T(arcP2T  a) { pre.remove(a);}
    public void removearcT2P(arcT2P  a) { post.remove(a);}

        //  public String toJson() {
        //      Gson g = new Gson();
        // } 
 }

