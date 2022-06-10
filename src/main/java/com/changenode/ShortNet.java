package com.changenode;

import java.util.ArrayList;
//import java.util.ArrayList;
//import java.util.TreeMap;
import java.util.TreeMap;

public class ShortNet {
    private String name;
    private int[] locations;
    private TreeMap<String,String> initVal = new TreeMap<String,String>();
    private ArrayList<String> initMarking = new ArrayList<String>();
    private TreeMap<String,Transition> transitions= new TreeMap<String,Transition>();
   /*   private TreeMap<String,Place> places = new TreeMap<String,Place>();
    private TreeMap<String,arcP2T> arcP2Ts= new TreeMap<String,arcP2T>();
    private TreeMap<String,arcT2P> arcT2Ps= new TreeMap<String,arcT2P>(); */
    public ShortNet(String n, int[] ls, TreeMap<String,String>v) {
        name = n;
        locations = ls;
        initVal = v;
        //initval = ""; //new ArrayList<Pair<String, String>> ();
    }
    public String myString() {
       /*  NavigableSet<String> pla = places.navigableKeySet();
        NavigableSet<String> trs = transitions.navigableKeySet();
        NavigableSet<String> arcT = arcT2Ps.navigableKeySet();
        NavigableSet<String> arcP = arcP2Ts.navigableKeySet(); */
         String o = "Petri Net "+name+ " loc "+locations.toString() +"  initVal "+ initVal 
            +"  Marking "+initMarking+ " Transitions "+transitions ;
       //   +  " Places "+pla   + " P2T "+arcP+ " T2P " +arcT;
        return o;
    }
}
