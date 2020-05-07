import java.io.*;
import java.util.*;

class Tocka{
    int ime;
    double x,y;
    int ime2;

    Tocka(int ime,double x,double y,int ime2){
        this.ime=ime;
        this.x=x;
        this.y=y;
        this.ime2=ime2;
    }
}

public class Naloga9{
    public static FileWriter izhod;
    public static PrintWriter printWriter;
    
    public static void main(String[] args)throws IOException{
         //cas izvajanja
         long start = System.currentTimeMillis();

         String vh = args[0];
         String iz = args[1];
 
         //String vh ="C:\\Users\\ZIGA MEDVED\\Desktop\\APS1-Seminarski\\Naloga9\\I_2.txt";
         //String iz ="C:\\Users\\ZIGA MEDVED\\Desktop\\APS1-Seminarski\\Naloga9\\test.txt"; 
 
        BufferedReader tok = new BufferedReader(new FileReader(vh));
        printWriter = new PrintWriter(new FileWriter(iz));
 
        String vrstica;
        vrstica = tok.readLine();
        int stParov = Integer.parseInt(vrstica);
        int clusters=0;
        double[][] tabelaParov = new double[stParov][2];
        String[] podatki;

        //Branje podatkov...
        for(int i = 0; i<stParov; i++){
            vrstica=tok.readLine();
            podatki=vrstica.split(",");

            tabelaParov[i][0]=Double.parseDouble(podatki[0]);
            tabelaParov[i][1]=Double.parseDouble(podatki[1]);
        }
        vrstica=tok.readLine();
        clusters=Integer.parseInt(vrstica);

        /*
        for(int i = 0; i<stParov; i++){
            System.out.println(tabelaParov[i][0]+","+tabelaParov[i][1]);
        }
        System.out.println(clusters);
        */

        //double[] tabelaIndeksov = new double[stParov];
        double[][] tabelaRazdalij = new double[stParov][stParov];
        Tocka[] tocke = new Tocka[stParov];
        
        for(int i = 0; i<stParov; i++){
            tocke[i] = new Tocka(i+1, tabelaParov[i][0], tabelaParov[i][1],i+1);
            //System.out.printf("Ime tocke %d, x: %f ,y: %f \n",tocke[i].ime,tocke[i].x,tocke[i].y);
            
        }
       /* for(int i = 0; i<tocke.length;i++){
            System.out.printf("IME tocke: %d, ID tocke: %d\n",tocke[i].ime,tocke[i].ime2);
        }*/

        Double x1,x2;
        Double y1,y2;
        Double vrednostX,vrednostY;

        for(int i = 0; i<stParov; i++){
            x1=tocke[i].x;
            y1=tocke[i].y;
            
            for(int j = 0; j<i; j++){

                if(i==j){
                    tabelaRazdalij[i][j]=0;
                }else{
                    x2=tocke[j].x;
                    y2=tocke[j].y;
                    vrednostX = Math.pow((x2-x1),2);
                    vrednostY = Math.pow((y2-y1),2);
                    
                    vrednostX+=vrednostY;

                    tabelaRazdalij[i][j]=Math.sqrt(vrednostX);
                    //tabelaRazdalij[i][j]=(vrednostX);
                    
                }
                //System.out.print(tabelaRazdalij[i][j]+",");
            }
            //System.out.println();
        }

        int id1; 
        int id2;


        double minRazdalja=99999;
        int i1=0;
        int j1=0;

        //V TABELO DAJEM OBJEKTE, IME 1 IME 2, NJUNA NAJMANJSA RAZDALJA???...


        /*
        Tocka[][] koncna = new Tocka[stParov][stParov];

        for(int i = 0; i<stParov;i++){
            koncna[i][0]=tocke[i];
        }*/
        
        LinkedList<TreeSet<Integer>> kji = new LinkedList<>();
        for (int i = 0; i<stParov; i++) {
            TreeSet<Integer> enKlaster = new TreeSet<>();
            enKlaster.add(tocke[i].ime);
            kji.add(enKlaster);
        } 

        //izpisi(kji);

        double max=0;
        int k = stParov;
        while(k!=clusters){
            for(int i = 0; i<stParov; i++){
                for(int j = 0; j<i; j++){
                    if(tabelaRazdalij[i][j]>max){
                        max=tabelaRazdalij[i][j];
                    }

                    if(tabelaRazdalij[i][j]<minRazdalja){
                        minRazdalja=tabelaRazdalij[i][j];
                        i1=i+1;
                        j1=j+1;
                        //i1=i;
                        //j1=j;
                        
                    }
                    
                    //System.out.print(tabelaRazdalij[i][j]+",");
                }
                //System.out.println();
            }
            //System.out.println("Trenutna najmanjsa razdalja je: "+minRazdalja+",med tockama: "+(i1)+" in "+(j1));

            int indeks1 = 0;
            int indeks2 = 0;
            int univerzalni = 0;
            TreeSet<Integer> set1=null;
            TreeSet<Integer> set2=null;


            for(TreeSet<Integer> iter : kji) {
                for(int x : iter){
                    if((i1)==x){
                        indeks1=univerzalni;
                        set1=iter;
                    }
                    if((j1)==x){
                        indeks2=univerzalni;
                        set2=iter;
                    }
                    if(set1!=null && set2!=null){
                        break;
                    }
                }
                univerzalni++;
                
            }

            //izpisi(kji);

            
            tabelaRazdalij[i1-1][j1-1]=max;
            minRazdalja=max;


            if(indeks1==indeks2){
                continue;

            }else if(set1.first()>set2.first()){
                set2.addAll(set1);
                kji.remove(indeks1);
                k--;

            }else{
                set1.addAll(set2);
                kji.remove(indeks2);
                k--;
            }

            
            /*
            id1=tocke[i1-1].ime2;
            id2=tocke[j1-1].ime2;

            if(id1==id2){
                tabelaRazdalij[i1-1][j1-1]=max;
                minRazdalja=max;
                continue;
            }
   
            if(id1<id2){
                tocke[j1-1].ime2=tocke[i1-1].ime2;
                k--;
            }else if(id2<id1){
                tocke[i1-1].ime2=tocke[j1-1].ime2;
                k--;
            }
            */
            

            //System.out.println("id prve in druge "+id1+","+id2);
            //System.out.println("stevilo trenutnih clust "+k);

            //

            //

            //k--;
            
            
            //System.out.println("Trenutna najmanjsa razdalja je: "+minRazdalja+",med tockama: "+i1+" in "+j1);
            //System.out.println(max);
            
            
        }


        /*for(int i = 0; i<tocke.length;i++){
            System.out.printf("IME tocke: %d, ID tocke: %d\n",tocke[i].ime,tocke[i].ime2);
        }*/

        int izpis = tocke[0].ime2;
        int trenuten;
        /*
        for(int i = 0; i<tocke.length; i++){
            trenuten=tocke[i].ime2;
            if(izpis!=trenuten){
                printWriter.println();
                izpis=trenuten;
            }
            printWriter.print(tocke[i].ime);

        }*/

        izpisi(kji);



        tok.close();
        printWriter.close();

        //cas izvajanja
        long end = System.currentTimeMillis();
        System.out.println("Execution speed "+ (end - start)+" ms\n");

        
        
        

    }

    public static void izpisi(LinkedList<TreeSet<Integer>> kji){
        int stevec=0;
        for(TreeSet<Integer> iter : kji) {
            for(int x : iter){
                if(stevec>0){
                    //System.out.print(","+x); 
                    printWriter.print(","+x); 
                       
                }else{
                   //System.out.print(x);
                   printWriter.print(x);
                }
                stevec++;
            }
            stevec=0;
            printWriter.println();
            //System.out.println();
        }
    }



}