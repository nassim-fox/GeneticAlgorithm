
package Part2;
import java.util.ArrayList;
import java.util.Collections;
import Commun.ManipulationFichierDonnees;
public class AlgoGenetique {
    
    private double cr,mr;
    private int population ,maxIter,nbrIterFait,maxAge;
    private static int nbrVar ;
    private static ManipulationFichierDonnees m ;
    private int maxRep ; //repetition 
    private int [] valEs ;
    
    public static void setM(ManipulationFichierDonnees m) {
        AlgoGenetique.m = m;
        Population.setM(m);
        
    }
    
    public AlgoGenetique(double cr, double mr, int population,ManipulationFichierDonnees m,int maxIter,int maxAge) {
        this.cr = cr;
        this.mr = mr;
        this.maxIter=maxIter;
        this.population = population;
        this.m=m;
        this.maxAge=maxAge;
        maxRep=0;
        nbrIterFait=0;
        Population.setM(m);
        if(m!=null){
            nbrVar=m.getNbrVar();
        }
    }

    public int getRep() {
        return maxRep;
    }

    public void setRep(int rep) {
        this.maxRep = rep;
    }
    
    public double getCR() {
        return cr;
    }

    public double getMR() {
        return mr;
    }
    public ArrayList<Population> generateRandSol(int size){
        
        ArrayList <Population> a = new ArrayList <Population> ();
        Population n ;
        while(a.size()<size){
            n=Population.generateRandomSol(nbrVar);
            if(!a.contains(n))a.add(n);
        }
        return a;
    }
    public static Population cross(Population n1,Population n2, int r){
        int []tab=new int[nbrVar];
        int [] Val1=n1.getEnsVal();
        int [] Val2=n2.getEnsVal();
        for(int j =0;j <r;j++){
            tab[j]=Val1[j];
            }
        for(int j =r;j <tab.length;j++){
            tab[j]=Val2[j];
        }
        return new Population(tab);
                
    }
    
    public int getNbrIterFait() {
        return nbrIterFait;
    }
    
    public int[] getValEs( )
    {
    
    
    
        return valEs;
    }
    
    public Population run(){
        // generate random sol -------------------------------------------------
        ArrayList<Population> list =new ArrayList<>();
        Population.setMaxAge(maxAge);
        list=generateRandSol(population);
        int c=(int)(cr*population); // taille de poulation ajoutee
        int m=(int)(mr*population); // taille de population modifiee
        int r ; // indice
        int j,k,l ,lastResult,rep=1;
        Population n;
        Population p;
        Collections.sort(list);
        n=list.get(population-1);
        lastResult=n.getFit();
        //traitement -----------------------------------------------------------
        for (int i = 0; i < maxIter; i++) {
            nbrIterFait++;
        //Cross ------------------------------------------------------------
            j=0;
            while(j<c){
                k = (int)(Math.random()*population);
                do{
                    l = (int)(Math.random()*population);
                }while(l==k);
                r =(int)(Math.random()*(nbrVar-1)+1); // l'indice de div doit etre de 1 a (taille -2)
                // random * tailleEnsemble + debutEnsemble 
                p=cross(list.get(l),list.get(k), r);
                if(!list.contains(p)){
                    j++;list.add(p); }
                p=cross(list.get(k),list.get(l), r);
                if(!list.contains(p)){
                    j++;list.add(p); }
            }
        //mutation ---------------------------------------------------------    
            j=0;
            while(j<m){
                k = (int)(Math.random()*population);
                p=list.get(k).inverserRandomBit();
                if(!list.contains(k)){
                    j++;list.add(p); list.remove(k);  }                
                
            }    
         //Selection ------------------------------------------------------
            Collections.sort(list);
            while(list.size()>population){
                list.remove(0);
            }
            n=list.get(population-1);
            //System.out.println(n+" 1 "+n.getFit()+"     \t"+list.get(0)+" 2 "+list.get(0).getFit()  );
            //fixer la stagnation si la meme valeur de la fonction de fitness se repete maxRep retourner resultat n
            if(maxRep!=0){
                if(lastResult==n.getFit()){rep++;
                    if(rep==maxRep) return n;
                        }
                else{ rep=1;lastResult=n.getFit(); }
            
            
            }//System.out.println(""+i+" "+n.getFit());
            
        }
        
    
    
    return n ;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
