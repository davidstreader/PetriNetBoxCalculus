package com.changenode;

import com.google.gson.Gson;

public class  UniTest {
       public static void main(String[] args) {


        
        Gson g = new Gson();
         String sin = "x = 9"; String son = "x \u003d 9";
         System.out.println("sin "+sin+"  son "+son);
         
        String frog = g.toJson(sin);  
        String srog = g.toJson(son);  
        System.out.println("toJson son "+son+ " returns "+srog);
        
        System.out.println("toJson sin "+sin+ " returns "+frog);
          
        String oo = g.fromJson(frog, String.class);
        System.out.println("fromJson on "+frog+" returns "+oo);
         try {
         String  frig = "\"x \u003d 9\"";
         String fail = "x \u003d 9";
         System.out.println("frog.toString() = " +frog.toString());

         System.out.println("frog "+frog+"  frig "+frig+" fail "+fail+"\n")   ;
         String oops = g.fromJson(frig, String.class);  
         System.out.println("fromJson frig "+frig+"  output"+oops);

         String frag = "\""+frog+"\"";
      System.out.println("frog "+frog+"  frig "+frig+"  frag "+frag)   ;
       
       } catch (Exception e)
       {
           System.out.println("Oops frog - frig\n");
           System.out.println(e);
       }
     }

    }
