import java.io.*;

class Vozlisce{
    int kljuc;
    Vozlisce levo,desno;
    Vozlisce(int vrednost){
        kljuc=vrednost;
        levo=null;
        desno=null;
    }

}


public class Naloga10{

    public static FileWriter izhod;
    public static PrintWriter printWriter;
    public static Vozlisce vozl;
    public static int stevilo;
    public static int stevec;

    public static void main(String[] args) throws IOException{

    //cas izvajanja
    long start = System.currentTimeMillis();

    String vh = args[0];
    String iz = args[1];
    

    //String vh ="C:\\Users\\ZIGA MEDVED\\Desktop\\APS1-Seminarski\\Naloga10\\I_10.txt";
    //String iz ="C:\\Users\\ZIGA MEDVED\\Desktop\\APS1-Seminarski\\Naloga10\\test.txt"; 


    BufferedReader tok = new BufferedReader(new FileReader(vh));
    printWriter = new PrintWriter(new FileWriter(iz));

    String vrstica = tok.readLine();

    String[] podatki = vrstica.split(",");
    int[] inorder = new int[podatki.length];

    for(int i = 0; i<podatki.length; i++){
        inorder[i]=Integer.parseInt(podatki[i]);

    }
    /*
    for(int i = 0; i<podatki.length; i++){
        //System.out.print(podatki[i]+",");
        //printWriter.print(podatki[i]+",");
        System.out.print(inorder[i]+",");
        printWriter.print(inorder[i]+",");
    }
    */

    Vozlisce vozlisce = null;
    Vozlisce koren = zgradiDrevo(inorder,0,inorder.length-1,vozlisce);
    //System.out.println("dolzina "+inorder.length);
    stevilo = inorder.length;
    int visina = visinaDrevesa(koren);
    natisniDrevo(koren,visina);
    //System.out.println("visina "+visina);

    

    tok.close();
    printWriter.close();

    //cas izvajanja
    long end = System.currentTimeMillis();
    System.out.println("Execution speed "+ (end - start)+" ms\n");


    }
    //zac 0, kon len-1, 
    public static Vozlisce zgradiDrevo(int inorder[],int zacetek, int konec,Vozlisce vozlisce){

        if(zacetek>konec){
            return null;
        }

        int max=-1;
        int indeks=-1;
        for(int i = zacetek; i<=konec;i++){
            if(inorder[i]>max){
                max=inorder[i];
                indeks=i;
            }
        }
    
        //System.out.println("tukaj sem: "+vozlisce.kljuc);

        if(zacetek==konec){
            vozlisce = new Vozlisce(max);
            //System.out.print("eeeee"+vozlisce.kljuc);
            return vozlisce;
        }
        //System.out.println("indekkkks "+indeks);
        vozlisce = new Vozlisce(max);
        vozlisce.levo=zgradiDrevo(inorder, zacetek, indeks-1, vozlisce.levo);
        vozlisce.desno=zgradiDrevo(inorder, indeks+1, konec, vozlisce.desno);
        
        //System.out.print(max+"asdasd");
        return vozlisce;
    }

    //POMOC ZA IZPIS IZ https://www.geeksforgeeks.org/level-order-tree-traversal///

    public static void natisniDrevo(Vozlisce koren,int visina){
        int i = 0; 

        while(i<visina){
            sprintejDrevo(koren,i);
            i++;
        }
    }
    
    public static void sprintejDrevo(Vozlisce koren, int nivo){

        if(koren==null)
            return;
        
        if(nivo==0){
            //System.out.print(koren.kljuc+" ");
            if(stevec==(stevilo-1)){
                //printWriter.println("BOI");
                printWriter.print(koren.kljuc);
                return;
            }
            printWriter.print(koren.kljuc+",");
            stevec++;
          
        }
        else if(nivo>0){
            sprintejDrevo(koren.levo, nivo-1);
            sprintejDrevo(koren.desno,nivo-1);
        }

    }

    public static int visinaDrevesa(Vozlisce vozlisce){

        if(vozlisce==null){
            return 0;
        }else{
            int lVisina = visinaDrevesa(vozlisce.levo);
            int dVisina = visinaDrevesa(vozlisce.desno);

            if(lVisina>dVisina){
                return (lVisina+1);
            }else
                return (dVisina+1);
        }
    }

}