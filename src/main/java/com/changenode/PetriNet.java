package com.changenode;



import java.util.ArrayList;
import java.util.NavigableSet;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



//import javafx.util.Pair;

public class PetriNet {
    private String name;
    private int[] locations;
    private TreeMap<String,String> initVal = new TreeMap<String,String>(); //ArrayList<Pair<String, String>>  initval;
    private ArrayList<String> initMarking = new ArrayList<String>();
    private TreeMap<String,Transition> transitions= new TreeMap<String,Transition>();
    private TreeMap<String,Place> places = new TreeMap<String,Place>();
    private TreeMap<String,arcP2T> arcP2Ts= new TreeMap<String,arcP2T>();
    private TreeMap<String,arcT2P> arcT2Ps= new TreeMap<String,arcT2P>();
    
    public PetriNet(String n, int[] ls, TreeMap<String,String> v) {
        name = n;
        locations = ls;
        initVal = v;
        //initval = ""; //new ArrayList<Pair<String, String>> ();
    }
    public PetriNet(String n, int[] ls, TreeMap<String,String> v,
    TreeMap<String,Transition> t,  TreeMap<String,Place> p ) {
        name = n;
        locations = ls;
        initVal = v;
        //initval = ""; //new ArrayList<Pair<String, String>> ();
        transitions = t;
        places = p;
    }

    public void setarcP2Ts(TreeMap<String,arcP2T> a) { arcP2Ts =a; }
    public void setarcT2Ps(TreeMap<String,arcT2P> a) { arcT2Ps =a; }
    public void setPlaces(TreeMap<String,Place> p) { places =p; }
    public void setTransitions(TreeMap<String,Transition> t) { transitions =t; }
    public void addTransition(Transition t) { transitions.put(t.name,t); }
    public void addPlace(Place p) { places.put(p.name,p); }
    public void markPlace(Place p) {initMarking.add(p.name);}
    public void addarcT2P(arcT2P a) { arcT2Ps.put(a.name,a); }
    public void addarcP2T(arcP2T a) { arcP2Ts.put(a.name,a); }
   
    public void removeTransition(Transition t) { transitions.remove(t.name); }
    public void removePlace(Place p) { places.remove(p.name); }
    public void removearcT2P(arcT2P a) { arcT2Ps.remove(a.name); }
    public void removearcP2T(arcP2T a) { arcP2Ts.remove(a.name); }

    public String myString() {
        NavigableSet<String> pla = places.navigableKeySet();
        NavigableSet<String> trs = transitions.navigableKeySet();
        NavigableSet<String> arcT = arcT2Ps.navigableKeySet();
        NavigableSet<String> arcP = arcP2Ts.navigableKeySet();
         String o = "Petri Net "+name+ " loc "+locations.toString() +"  vars "+ initVal
             +"  "+initVal+  " Places "+pla+ " Transitions "+trs
             + " P2T "+arcP+ " T2P " +arcT;
        return o;
    }
   
    public static PetriNet getJson(String js) {
        java.lang.reflect.Type PNType = new TypeToken<PetriNet>() {}.getType();
        System.out.println("getJson Starting");
        PetriNet result = null;

        result = new Gson().fromJson(js, PNType);
        System.out.println(result.myString());
        System.out.println("getJson Ending");
        return result;
    }
}
