
package Part2;

import Commun.ManipulationFichierDonnees;
import Commun.Vect;

public class Population extends Vect implements Comparable {
    private static ManipulationFichierDonnees m ;
    private int fit ;
    private int age ; 
    private static int  maxAge =-1;
    
    public Population(int[] vals) {
        super(vals);
        setFit();
    
    }

    public int getAge() {
        return age;
    }
    public void incAge() {
        age++;
    }
    public static void setMaxAge(int maxAge){
        Population.maxAge=maxAge;
    }
    public boolean estMort(){
        return age==maxAge;
    }
    public static void setM(ManipulationFichierDonnees m) {
        Population.m = m;
    }
    
    @Override
    public int compareTo(Object o) {
        if(o== null ) return -1;
        if(!( o instanceof Population)) return -1;
        Population n = (Population )o ;
        if(this.getFit()>n.getFit())return  1 ;
        if(this.getFit()<n.getFit())return -1 ;
        return 0 ;
    }
    public void setEnsVal(int [] vals){
        super.setEnsVal(vals);
        setFit();
    }
    public int getFit(){
        return fit;
    }
    private void setFit(){
        fit=m.nbrClausesVerifiee(getEnsVal());
    }
    public static Population generateRandomSol(int nbrVar ){
        int[]tab =new int[nbrVar];
        for(int i=0;i<nbrVar;i++){
        tab[i]=(int)(Math.random()*2);//  pour chaque col 1 ou 0 (v/f)
        }
        return new Population(tab);
    } 
       
    public Population inverserRandomBit(){
        if(ensVal==null) return null;
        if(ensVal.length==0) return null;
        int i =(int)((Math.random())*ensVal.length);
        int [] copie = new int[ensVal.length];
        for (int j = 0; j < ensVal.length; j++) {
            copie[j]=ensVal[j];
        }
        copie[i]=1-copie[i];
        return (new Population(copie));
    }
    
    
}
