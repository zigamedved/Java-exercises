import java.io.*;
import java.util.*;
import java.util.HashMap;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;


class Graf<G>{
    private Map<G,List<G>>kljucVrednost = new HashMap<>();
    
    public void dodajVozlisce(G i){
        kljucVrednost.put(i, new java.util.LinkedList<G>());
    }

    public void dodajPovezavo(G izvor, G cilj){
        if(kljucVrednost.containsKey(izvor)){
            
        }else{
            dodajVozlisce(izvor);
        }
        if(kljucVrednost.containsKey(cilj)){
            
        }else{
            dodajVozlisce(cilj);
        }
        kljucVrednost.get(izvor).add(cilj);
        kljucVrednost.get(cilj).add(izvor);
        
    }

    public boolean preveriPovezavo(G i,G c) 
    { 
        return (!(kljucVrednost.get(i).contains(c))) ? false : true;

    } 

}


public class Naloga6{
    public static FileWriter izhod;
    public static PrintWriter printWriter;
    public static String[] modri;
    public static String[] vList;
    public static int[][] jamal;
    public static int steviloElementov=0;
    public static int[] koncno;
    public static int rdeci=0;
    public static int plavi=0;
   // public static LinkedList<Integer> vrsta;
    //public static int v;
    //public static int[]tabelaBarv;
   
    
    public static void main(String[] args)throws IOException{
        //cas izvajanja
        long start = System.currentTimeMillis();

        //String vh = args[0];
        //String iz = args[1];

        String vh ="C:\\Users\\ZIGA MEDVED\\Desktop\\APS1-Seminarski\\Naloga6\\I_1.txt";
        String iz ="C:\\Users\\ZIGA MEDVED\\Desktop\\APS1-Seminarski\\Naloga6\\output.txt"; 

        BufferedReader tok = new BufferedReader(new FileReader(vh));
        printWriter = new PrintWriter(new FileWriter(iz));

        String vrstica;

        vrstica = tok.readLine();
        
        int stBranj = Integer.parseInt(vrstica);

        //System.out.print("Stevilo branj je : "+stBranj+"\n");

        String[] vozlisca = new String[stBranj];

        for(int i = 0; i<stBranj; i++){
            vrstica = tok.readLine();
            vozlisca[i] = vrstica;
            //System.out.println(vozlisca[i]);
        }

        //urediti moram povezave...
        Arrays.sort(vozlisca);

        //IZPIS
        /*
        for(int i = 0; i<stBranj; i++){
            System.out.println(vozlisca[i]);
        }
        */

        String[] prvi = new String[stBranj];
        String[] drugi = new String[stBranj];
    

        for(int i = 0; i<stBranj; i++){
            String[] trenuten = vozlisca[i].split("-");
            prvi[i]=trenuten[0];
            drugi[i]=trenuten[1];

         
        }

        //naredim seznam vozlisc s pomocjo hashseta(ne podvaja elementov)
        HashSet<String> elementi = new HashSet<String>();
        String[][] eList = new String[stBranj][2];


        //IZPIS
        for(int i = 0; i<stBranj; i++){
            //System.out.println(prvi[i]+","+drugi[i]);
            elementi.add(prvi[i]);
            elementi.add(drugi[i]);
            eList[i][0]=prvi[i];
            eList[i][1]=drugi[i];
        }

        //izpis elementov...
        //System.out.println(elementi);
        int elSize = elementi.size();
        //System.out.println(elSize);
        vList = new String[elSize];

        int stevec=0;
        for(String a : elementi){
            vList[stevec]=a;
            stevec++;
            //System.out.println("TLE SM: "+a);
        }
        //System.out.print("stevilo "+stevec);
        
        
        //NAREDIM SI VERTEX LIST IN EDGE LIST...
        
    
        /*
        //System.out.println("Izpis V lista: ");

        for(int a = 0; a<vList.length; a++){
           // System.out.println(vList[a]);
           
        }

        //System.out.println("Izpis E lista: ");

        for(int b = 0; b<stBranj; b++){
            //System.out.println(eList[b][0]+"--->"+eList[b][1]);
        }
        */
        

        Graf<String> g = new Graf<String>(); 
  
        for(int a = 0; a<stBranj; a++){
            g.dodajPovezavo(eList[a][0], eList[a][1]);
        }
        
        
        jamal = new int[elSize][elSize];
        //vList

        //MATRIKA NAREJENA, INDEKSI SO UREJENI PO VRSTI, VLIST??
        for(int a = 0; a<elSize; a++){
            for(int b = 0; b<elSize; b++){
                if(a==b){
                    jamal[a][b]=0;
                }
                String alfa = vList[a];
                String beta = vList[b];

                //ce imam to povezavo v hashmapu, napisem v matriko 1...
                if(g.preveriPovezavo(alfa,beta)){
                    jamal[a][b]=1;
                }else{
                    jamal[a][b]=0;
                }
               // System.out.print(jamal[a][b]+",");
            
            }
           // System.out.println();
        }

        modri = new String[elSize];
        //MATRIKA DONE, pobarvam graf in locim po barvah v dve skupini... izpisem vecjo, ce sta enaki izpisem -1?...
        //boolean lmao = isBipartite(jamal,0,elSize);

        steviloElementov=elSize;
        int rafCamora = preveri(jamal);
      
        if(rafCamora==0){
            printWriter.println(-1);
        }

        tok.close();
        printWriter.close();

        //cas izvajanja
        long end = System.currentTimeMillis();
        System.out.println("Execution speed "+ (end - start)+" ms\n");


    }

    //POMOC IZ "https://www.geeksforgeeks.org/bipartite-graph/", 2.01.2020//
	
    public static int barvaj(int stevec,int matrika[][],int tabelaBarv[]){
    	LinkedList<Integer> vrsta = new LinkedList<Integer>();
        tabelaBarv[stevec]=1;
        vrsta.add(stevec);
        int v;
        int preveri = 1;
        
        while(vrsta.size()!=0){
            int u = vrsta.getFirst();

            if(matrika[u][u]==1){
                preveri=0;
            }
            v=0;
			vrsta.pop();
            while(v<steviloElementov){
                if(matrika[u][v]==1 && tabelaBarv[v]==-1){
                	tabelaBarv[v]=1-tabelaBarv[u];
                    vrsta.push(v);
                    //poglej(v,u);
                    
                }
                if(matrika[u][v]==1 && tabelaBarv[v]==tabelaBarv[u]){
                    preveri=0;
                }
                v++;
            }
        }
        return preveri;
    }
    

    public static int preveri(int[][] matrika){
    	int[]tabelaBarv =new int[steviloElementov];
        tabelaBarv[0]=0;
        int stevec=0;
        while(stevec<steviloElementov){
            tabelaBarv[stevec]=-1;
            stevec++;
        }
        stevec=0;

        while(stevec<steviloElementov){
            if(tabelaBarv[stevec]==-1){
                int poglej=barvaj(stevec,matrika,tabelaBarv);
                if(poglej<1){
                    return 0;
                }
            }
            stevec++;
        }
        
        koncno=tabelaBarv;
    
        for(int a = 0; a<steviloElementov; a++){
            if(koncno[a]==0){
                rdeci++;
            }else if(koncno[a]==1){
                plavi++;
            }
            //System.out.print(koncno[a]+",");
        }
        
        //System.out.printf("stevilo rdecih je: %d, modrih pa: %d\n",crveni,plavi);

        if(rdeci==plavi){
            printWriter.println(-1);
        }else if(rdeci>plavi){
            for(int a = 0; a<steviloElementov; a++){
                if(koncno[a]==0){
                    printWriter.println(vList[a]);
                }
            }
        }else if(plavi>rdeci){
            for(int a = 0; a<steviloElementov; a++){
                if(koncno[a]==1){
                    printWriter.println(vList[a]);
                }
            }
        }

        return 1;
    

    }
   // public static void poglej(int a,int b){
    //    tabelaBarv[a]=1-tabelaBarv[b];
      //  vrsta.push(a);
    //}

}