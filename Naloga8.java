import java.io.*;
import java.security.interfaces.DSAKeyPairGenerator;
import java.lang.Math;

class Vozlisce{
    int min;
    int max;
    Vozlisce lGor,dGor,lDol,dDol;
    Vozlisce(int st1, int st2){
        min=st1;
        max=st2;
        lGor=null;
        dGor=null;
        lDol=null;
        dDol=null;
    }
}

public class Naloga8{
    public static FileWriter izhod;
    public static PrintWriter printWriter;
    public static int stObhodov;
    public static double tocke;
    
    public static void main(String[] args)throws IOException{
    
        //cas izvajanja
        long start = System.currentTimeMillis();

        String vh = args[0];
        String iz = args[1];

        //String vh ="C:\\Users\\ZIGA MEDVED\\Desktop\\APS1-Seminarski\\Naloga8\\I_10.txt";
        //String iz ="C:\\Users\\ZIGA MEDVED\\Desktop\\APS1-Seminarski\\Naloga8\\test.txt"; 

        BufferedReader tok = new BufferedReader(new FileReader(vh));
        printWriter = new PrintWriter(new FileWriter(iz));

        String vrstica;

        vrstica = tok.readLine();

        int dimenzija = Integer.parseInt(vrstica);
        int[][] vrednosti = new int[dimenzija][dimenzija];

        for(int i = 0; i<dimenzija; i++){
            vrstica = tok.readLine();
            String[] podatki = vrstica.split(",");
            for(int j = 0; j<dimenzija; j++){
                vrednosti[i][j]=Integer.parseInt(podatki[j]);

            }
        }

        vrstica=tok.readLine();
        int b = Integer.parseInt(vrstica);
        int[] vrednosti2 = new int[b];
        for(int i = 0; i<b; i++){
            vrstica = tok.readLine();
            vrednosti2[i]=Integer.parseInt(vrstica);
        }

        /*
        //////////////////////////
        //IZPIS
        System.out.println("Stevilo A: "+dimenzija);
        for(int i = 0; i<dimenzija; i++){
            for(int j = 0; j<dimenzija; j++){
                System.out.print(vrednosti[i][j]+",");
            }
            System.out.println();
        }
        System.out.println("Stevilo B: "+b);
        for(int i = 0; i<b; i++){
            System.out.println(vrednosti2[i]);
        }

        //printWriter.println("Dela");
        //////////////////////////
        */

        Vozlisce vozlisce = null;
        Vozlisce koren = zgradiDrevo(vrednosti,0,vrednosti.length,0,vrednosti.length,vozlisce,dimenzija);

        //Drevo imam narejen, sledi izpis da preverim...
        int visina = visinaDrevesa(koren);
        int xp = dimenzija*dimenzija;
        //System.out.println("Visina je: "+visina);

        //IMAM DREVO IN VISINO, s katero bom dodeljeval tocke...
        //izpisem drevo level by level...
        //natisniDrevo(koren,visina);
        //izpisi preorder
        //natisniPreorder(koren);
        //preorder pregledujemo in stejemo obiske...

        for(int i = 0; i<b; i++){
            preglej(koren,xp,vrednosti2[i]);
            int a = (int)tocke;
            printWriter.println(a+","+stObhodov);

            stObhodov=0;
            tocke=0;
            
        }
        


        tok.close();
        printWriter.close();

        //cas izvajanja
        long end = System.currentTimeMillis();
        System.out.println("Execution speed "+ (end - start)+" ms\n");

    }


    public static Vozlisce zgradiDrevo(int vrednosti[][],int zacetek1, int konec1,int zacetek2, int konec2, Vozlisce vozlisce,int dimenzija){

        int a = dimenzija/2;
        /*if(zacetek1>konec1 || zacetek2>konec2){
            return null;
        }*/

        int max = vrednosti[zacetek1][zacetek2];
        int min = max;

        for(int i = zacetek1; i<konec1; i++){
            for(int j = zacetek2; j<konec2; j++){
                if(vrednosti[i][j]>max){
                    max=vrednosti[i][j];
                }
                if(vrednosti[i][j]<min){
                    min=vrednosti[i][j];
                }
            }
        }
        //System.out.println("zac in kon x "+(zacetek1)+","+konec1);
        //System.out.println("zac in kon y "+(zacetek2)+","+(konec2));


        //System.out.println("MINIMUM in MAKSIMUM: "+min+","+max);


        if(min==max){
            vozlisce = new Vozlisce(min,max);
            return vozlisce;
        }

        if(zacetek1==konec1){
            vozlisce = new Vozlisce(min,max);
            return vozlisce;
        }

        vozlisce = new Vozlisce(min,max);

        /*
        vozlisce.lGor=zgradiDrevo(vrednosti, zacetek1, (konec1/2), zacetek2, (konec2/2), vozlisce,dimenzija);
        vozlisce.dGor=zgradiDrevo(vrednosti, zacetek1, (konec1/2), (konec2/2), konec2, vozlisce,dimenzija);
        vozlisce.lDol=zgradiDrevo(vrednosti, (konec1/2), konec1, zacetek2, (konec2/2), vozlisce,dimenzija);
        vozlisce.dDol=zgradiDrevo(vrednosti, (konec1/2), konec1, (konec2/2), konec2, vozlisce,dimenzija);
        */

        vozlisce.lGor=zgradiDrevo(vrednosti, zacetek1, (konec1-a), zacetek2, (konec2-a), vozlisce,a);
        vozlisce.dGor=zgradiDrevo(vrednosti, zacetek1, (konec1-a), (zacetek2+a), konec2, vozlisce,a);
        vozlisce.lDol=zgradiDrevo(vrednosti, (zacetek1+a), konec1, zacetek2, (konec2-a), vozlisce,a);
        vozlisce.dDol=zgradiDrevo(vrednosti, (zacetek1+a), konec1, (zacetek2+a), konec2, vozlisce,a);
        

        return vozlisce;
    }

    public static int visinaDrevesa(Vozlisce vozlisce){

        if(vozlisce==null){
            return 0;
        }else{
            int lVisina = visinaDrevesa(vozlisce.lDol);
            int dVisina = visinaDrevesa(vozlisce.dDol);

            if(lVisina>dVisina){
                return (lVisina+1);
            }else
                return (dVisina+1);
        }
    }

    
    //visina = tocke...
    //globalno st obhodov, v vsakem krogu ponastavi...
    //enako za tocke..
    public static void preglej(Vozlisce koren,double xp,int vrednosti){
        //System.out.println(vrednosti);
        double a = xp;


        if(koren==null){
            return;
        }

        //System.out.println("obiskano drevo, min max, iskana vrednost "+koren.min+" "+koren.max+" "+vrednosti);
        //System.out.println("obiskano drevo, min max, xp, vrednosti "+koren.min+" "+koren.max+" "+xp+" "+vrednosti);


        stObhodov++;

        if(koren.min>vrednosti){
            //stObhodov++;
            tocke+=0;
            return;
        }

        //ko najdemo koren, vozlisce katerega maks je manjsi ali enak vrednosti
        if(koren.max<=vrednosti){
            tocke+=xp;
            //stObhodov++;
            return;
        }

        a=a/4;
        
        if(koren.min<=vrednosti && vrednosti<=koren.max){
            preglej(koren.lGor,a,vrednosti);
            preglej(koren.dGor,a,vrednosti);
            preglej(koren.lDol,a,vrednosti);
            preglej(koren.dDol,a,vrednosti);
        }

    }




}