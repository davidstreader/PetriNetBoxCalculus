package com.changenode;



import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.NavigableSet;
import java.util.Optional;
import java.util.Scanner;
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

    public static Optional<PetriNet> file2PetriNet(File file, Log log) {
        Optional<PetriNet> pn = Optional.empty();
        try {
            String fileType = Files.probeContentType(file.toPath());
             if (fileType.endsWith("json")) {
                 Scanner myReader = new Scanner(file);
               while (myReader.hasNextLine()) {
                 String data = myReader.nextLine();
                  log.log(data);
                 java.lang.reflect.Type PNType = new TypeToken<PetriNet>() {}.getType();
                  pn = new Gson().fromJson(data, PNType);              
                  log.log("net "+pn.get().myString());
               }
              myReader.close();                         
            } else {
             log.log(file.getAbsolutePath());  
            }
         } catch(Exception ex) {
         System.out.println("FileDrop Error "+ex);
        }       
        return pn;
    }
   
    
    public static PetriNet mkNet() {
        int[] ixs = new int[2];
        ixs[0] = 12;
        Place p1 = new Place("Pl1", ixs);
        Place p2 = new Place("Pl2", ixs);
        Transition t1 = new Transition("Tr1", "ping", TrType.active );
        arcP2T a1 = new arcP2T("arc1",p1.name,"x< 0",t1.name);
        arcT2P a2 = new arcT2P("arc2",t1.name," x = 2",p2.name);
        p1.addarcP2T(a1);
        p2.addarcT2P(a2);
        t1.addarcP2T(a1);
        t1.addarcT2P(a2);
        TreeMap<String,String> eval = new TreeMap<String,String>();
        eval.put("x", "3 +5");
        TreeMap<String,Transition> trs = new TreeMap<String,Transition>(); trs.put(t1.name,t1);
        TreeMap<String,Place> pls = new TreeMap<String,Place>(); pls.put(p1.name,p1);pls.put(p2.name,p2);
        PetriNet pn = new PetriNet("FirstNet",ixs,eval);
        pn.setTransitions(trs);
        pn.setPlaces(pls);
        pn.markPlace(p1);
        pn.addarcP2T(a1); pn.addarcT2P(a2);
        System.out.println(pn.toString()+"\n****");
    
        System.out.println(p1);
        //String ps = p1.PlacetoJson();
        System.out.println("**myString()**");
        System.out.println(pn.myString());
        System.out.println("**JSON**");
        Gson g = new Gson();
        String js =   g.toJson(pn);
        System.out.println(js);
        Place pGot = Place.getJson();
        System.out.println("pGot "+pGot.myString());
        Transition tGot = Transition.getJson();
        System.out.println("tGot "+tGot.myString());
        
        return pn;
    }
   
   
}
