package com.changenode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.util.Pair;

public class PetriNet {
    private String name;
    private int[] locations;
    private ArrayList<String> vars;
    private ArrayList<Pair<String, String>>  initval;
    private ArrayList<Transition> transitions;
    private ArrayList<Place> places;
    private ArrayList<arcP2T> arcP2Ts;
    private ArrayList<arcT2P> arcT2Ps;
    
    public PetriNet(String n, int[] ls, ArrayList<String> v) {
        name = n;
        locations = ls;
        vars = v;
    }
    public PetriNet(String n, int[] ls, ArrayList<String> v,
    ArrayList<Transition> t,  ArrayList<Place> p ) {
        name = n;
        locations = ls;
        vars = v;
        transitions = t;
        places = p;
    }

    public void setarcP2Ts(ArrayList<arcP2T> a) { arcP2Ts =a; }
    public void setarcT2Ps(ArrayList<arcT2P> a) { arcT2Ps =a; }
    public void setPlaces(ArrayList<Place> p) { places =p; }
    public void setTransitions(ArrayList<Transition> t) { transitions =t; }
    public void addTransition(Transition t) { transitions.add(t); }
    public void addPlace(Place p) { places.add(p); }
    public void addarcT2P(arcT2P a) { arcT2Ps.add(a); }
    public void addarcP2T(arcP2T a) { arcP2Ts.add(a); }
   
    public void removeTransition(Transition t) { transitions.remove(t); }
    public void removePlace(Place p) { places.remove(p); }
    public void removearcT2P(arcT2P a) { arcT2Ps.remove(a); }
    public void removearcP2T(arcP2T a) { arcP2Ts.remove(a); }

    public String toString() {
        List<String> pla = places.stream().map(x->x.name).collect(Collectors.toList());
        List<String> tra = transitions.stream().map(x->x.name).collect(Collectors.toList());
         String o = "Petri Net "+name+ " loc "+locations +"  vars "+ vars 
             +"  "+initval+  " Places "+pla+ " Transitions "+tra;
        return o;
    }
   
}
