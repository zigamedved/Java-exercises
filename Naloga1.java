import java.util.Scanner;
import java.io.*;

public class Naloga1{
    
    public static char[][] crke;
    public static char[][] rezerva;
    public static String[] rezervaBesed;
    public static FileWriter izhod;
    public static PrintWriter printWriter;

    //mozni pomiki, za iterativni nacin...
    //desnoGor
    public static int xdG=-1;
    public static int ydG=1;
    //levoGor
    public static int xlG=-1;
    public static int ylG=-1;
    //gor

    public static int gorx=-1;
    public static int gory=0;
    //desno
    public static int desnox=0;
    public static int desnoy=1;
    //levo
    public static int levox=0;
    public static int levoy=-1;
    //dol
    public static int dolx=1;
    public static int doly=0;
    //int dolD
    public static int dolDx=1;
    public static int dolDy=1;
    //int dolL
    public static int dolLx=1;
    public static int dolLy=-1;


    static void uredi(String[] s,String[] s2){
        int n=s.length;
         for(int i = 1; i<n;i++){
             String temp = s[i];
             int j = i-1;
             while(j>=0 && temp.length() > s[j].length()){
                 s[j+1]=s[j];
                 s2[j+1]=s2[j];
                 j--;
             }
             s[j+1]=temp;
             s2[j+1]=temp;
         }
    }
    public static void main(String[] args) throws IOException{
        long start = System.currentTimeMillis();

        String vh = "C:\\Users\\ZIGA MEDVED\\Desktop\\APS1-Seminarska1\\Naloga1\\I1_9.txt";
        //String vh=args[0];
        String iz = "C:\\Users\\ZIGA MEDVED\\Desktop\\APS1-Seminarska1\\Naloga1\\test.txt";
        //String iz=args[1];
        
        //za testiranje je odprt samo ta vhod
        //BufferedReader tok = new BufferedReader(new FileReader("C:\\Users\\ZIGA MEDVED\\Desktop\\APS1-Seminarska1\\Naloga1\\I1_2.txt"));
        BufferedReader tok = new BufferedReader(new FileReader(vh));
        //izhod = new FileWriter("C:\\Users\\ZIGA MEDVED\\Desktop\\APS1-Seminarska1\\Naloga1\\izhod_2.txt");
        izhod = new FileWriter(iz);
        printWriter = new PrintWriter(izhod);

        String vrstica;
        
        
        vrstica=tok.readLine();
        String[] dimenzije=vrstica.split(",");
        
        //shranim si dimenzije tabele
        //System.out.print(dimenzije[0]+dimenzije[1]);
        int visina = Integer.parseInt(dimenzije[0]);
        int sirina = Integer.parseInt(dimenzije[1]);

        //globalna mreza crk za popravljanje rekurzije...
        rezerva=new char[visina][sirina];
        //tabela crk, ki jih iscem in jo tudi manipuliram
        crke=new char[visina][sirina];
        
        //napolnim tabelo crk
        for(int i = 0; i<visina;i++){
            vrstica=tok.readLine();
            String[] trenutnaVrstica=vrstica.split(",");
            for(int j = 0;j<sirina;j++){
                crke[i][j]=trenutnaVrstica[j].charAt(0);
                rezerva[i][j]=trenutnaVrstica[j].charAt(0);
            }

        }

        
        //preberem stevilo besed, koliko jih moram najti
        vrstica=tok.readLine();
        int steviloBesed=Integer.parseInt(vrstica);
        //System.out.print(steviloBesed);

        //crko po crko grem s charAt();...
        String[] besede = new String[steviloBesed];
        rezervaBesed = new String[steviloBesed];

        for(int i = 0;i<steviloBesed;i++){
            besede[i]=tok.readLine();
            rezervaBesed[i]=besede[i];
        }

     
        //posortira besede...
        uredi(besede,rezervaBesed);
        

        //KLIC REKURZIJE...
        //zbrisou sm pousod crke

        rekurzija(0,besede);
      

        tok.close();
        printWriter.close();

        long end = System.currentTimeMillis();

        //System.out.println("Execution speed "+ (end - start)+" ms\n");
    }

    //rekurzija za iskanje po tabeli besed...
    public static boolean rekurzija(int i,String[] besede){
        //ce smo prisli do konca tabele iskanih besed, vrni pozitiven rezultat
        if(i>=besede.length){
            return true;
        }

        //Ce napisano v besedo "imam", pomeni da sem jo ze nasel
        //rekurzivno klicem za naslednjo besedo
        if(besede[i]=="imam"){
            return rekurzija(i+1, besede);
        }

        /*KLICANJE REKURZIJE, KI ISCE MOZNE KOMBINACIJE, PODATI MORAM TRENUTNO
        BESEDO IN MREZO...
        */
            //nevem tocno
            for(int k = 0; k<crke.length;k++){
                for(int j = 0; j<crke[k].length;j++){
                    if(crke[k][j]==besede[i].charAt(0)){
                        if(poisci(besede[i],k,j,0)){//0 ali 1
                            //damo nek flag, da imamo resitev
                            besede[i]="imam";
                            if(rekurzija(i+1,besede)){
                                return true;
                            }
                            //besede[i]=rezervaBesed[i];

                        }
                        
                    }
                    //continue;
                }
            }
        //ce ni pravilne resitve razveljavim 'imam' in vrnem false, 
        //prepisem z originalno vrednostjo...
        //besede[i]=rezervaBesed[i];
        besede[i]=rezervaBesed[i];

        return false;
    }
   
    
    //v rekurziji grem zdaj crko po crko PO BESEDI, v rekurzijo dobim celo besedo...
    //prvi klic: koordinati, kjer najdemo prvo crko,znakBesede = 0;

    public static boolean poisci(String beseda,int x,int y,int znakBesede){

        //pomik desno gor
        //int x1=x, int y1=y; sprot neses dva inta na konc sprintas...

        if(rekurzijaDesnoGor(beseda,x,y,znakBesede,x,y)){
            //System.out.println("prva koordinata besede: "+beseda+" je: "+x+","+y);
            
            return true;
        }
        //pomik levo gor
        if(rekurzijaLevoGor(beseda,x,y,znakBesede,x,y)){
            //System.out.println("prva koordinata besede: "+beseda+" je: "+x+","+y);
            return true;
        }
        //pomik gor
        if(rekurzijaGor(beseda,x,y,znakBesede,x,y)){
            //System.out.println("prva koordinata besede: "+beseda+" je: "+x+","+y);
            return true;
        }
        //pomik dol
        if(rekurzijaDol(beseda,x,y,znakBesede,x,y)){
            //System.out.println("prva koordinata besede: "+beseda+" je: "+x+","+y);
            return true;
        }
        //pomik levo 
        if(rekurzijaLevo(beseda,x,y,znakBesede,x,y)){
            //System.out.println("prva koordinata besede: "+beseda+" je: "+x+","+y);
            return true;
        }
        //desno 
        if(rekurzijaDesno(beseda,x,y,znakBesede,x,y)){
            //System.out.println("prva koordinata besede: "+beseda+" je: "+x+","+y);
            return true;
        }
        //levo dol
        if(rekurzijaLevoDol(beseda,x,y,znakBesede,x,y)){
            //System.out.println("prva koordinata besede: "+beseda+" je: "+x+","+y);
            return true;
        }
        //desno dol
        if(rekurzijaDesnoDol(beseda,x,y,znakBesede,x,y)){
            //System.out.println("prva koordinata besede: "+beseda+" je: "+x+","+y);
            return true;
        }
        

        return false;

    }

    //prvi klic..... x,y koordinate kjer sm dobil crko in znakBesede=0, ter beseda...
    public static boolean rekurzijaDesnoGor(String beseda, int x, int y, int znakBesede,int x1,int y1){
        //preveri ali je X-koordinata veljavna,manj od 0 ali pa daljsa od meje
        if(x<0 || x>=crke[0].length){
            return false;

        }
        //preveri ali je Y-koordinata veljavna
        if(y<0 || y>=crke.length){
            return false;
        }

        if(crke[x][y]=='.'){
            return false;
        }

        //izhodni pogoj
        if(beseda.charAt(znakBesede)==crke[x][y]  && ((beseda.length()-1)==znakBesede)){
        	//na tem mestu smo, je veljavno, torej oznacim
            crke[x][y]='.';

            //System.out.println(" najdu besedo: "+beseda);
            //System.out.print("porabljena crka:"+crke[x][y]+" nastavljena vrednost nazaj:"+crke[3][0]);

            //System.out.println("najdu sm besedo: "+beseda+" prva koordinata je:"+x1+","+y1+" ; zadna koordinata je: "+x+","+y);
            //System.out.println("najdu sm besedo: "+beseda);
            //print writter u usako vrsto pa je...
            printWriter.printf("%s,%d,%d,%d,%d\n",beseda,x1,y1,x,y);

           /*
            if(beseda.length()>1){
                printWriter.printf("\n");

            }*/
            
            return true;
        }
        
        //besede dolzina 1
        if((beseda.length()==1) && (beseda.charAt(0)==crke[x][y])){
        	//na tem mestu smo, je veljavno, torej oznacim
            crke[x][y]='.';
            //System.out.print("najdu znak: "+beseda);
            printWriter.printf("%s,%d,%d,%d,%d\n",beseda,x1,y1,x,y);
            return true;
        }
        
        //ujemanje crke, moramo se naprej...
        //System.out.print("crke[x][y]: "+crke[x][y]+"znak besede pa je: "+beseda.charAt(znakBesede));
        
        if(crke[x][y]==beseda.charAt(znakBesede)){
        	//na tem mestu smo, je veljavno, torej oznacim
            crke[x][y]='.';
            
            if(rekurzijaDesnoGor(beseda, x+xdG, y+ydG, znakBesede+1,x1,y1)) {
            	return true;
            }else {
            	 //ce nismo prisli do konca vse za nazaj razveljavimo in returnamo false;
            	crke[x][y]=rezerva[x][y];
            	return false;
            	
            }
            	
        }


        crke[x][y]=rezerva[x][y];
        
        
        return false;

    }
    
    public static boolean rekurzijaGor(String beseda, int x, int y, int znakBesede,int x1, int y1){
        //preveri ali je X-koordinata veljavna,manj od 0 ali pa daljsa od meje
        
        if(x<0 || x>=crke[0].length){
            return false;

        }
        //preveri ali je Y-koordinata veljavna
        if(y<0 || y>=crke.length){
            return false;
        }

        if(crke[x][y]=='.'){
            return false;
        }


        //izhodni pogoj
        if(beseda.charAt(znakBesede)==crke[x][y]  && ((beseda.length()-1)==znakBesede)){
            //System.out.print("najdu besedo: "+beseda);
          //na tem mestu smo, je veljavno, torej oznacim
            crke[x][y]='.';

            //System.out.println("najdu sm besedo: "+beseda+" prva koordinata je: nevem ; zadna koordinata je: "+x+","+y);
            printWriter.printf("%s,%d,%d,%d,%d\n",beseda,x1,y1,x,y);

            return true;
        }
        
        //besede dolzina 1
        if((beseda.length()==1) && (beseda.charAt(0)==crke[x][y])){
        	//na tem mestu smo, je veljavno, torej oznacim
            crke[x][y]='.';
            //System.out.print("najdu znak: "+beseda);
            printWriter.printf("%s,%d,%d,%d,%d\n",beseda,x1,y1,x,y);
            return true;
        }
        
        //ujemanje crke, moramo se naprej...
        if(crke[x][y]==beseda.charAt(znakBesede)){
        	//na tem mestu smo, je veljavno, torej oznacim
            crke[x][y]='.';

            if(rekurzijaGor(beseda, x+gorx, y+gory, znakBesede+1,x1,y1)){
                return true;
            }else{
                crke[x][y]=rezerva[x][y];
                return false;
            }
        }

        //ce nismo prisli do konca vse za nazaj razveljavimo in returnamo false;
        

        crke[x][y]=rezerva[x][y];
        
        
        return false;

        
    }

    public static boolean rekurzijaLevoGor(String beseda, int x, int y, int znakBesede,int x1,int y1){
        //preveri ali je X-koordinata veljavna,manj od 0 ali pa daljsa od meje
        if(x<0 || x>=crke[0].length){
            return false;

        }
        //preveri ali je Y-koordinata veljavna
        if(y<0 || y>=crke.length){
            return false;
        }

        if(crke[x][y]=='.'){
            return false;
        }

        //izhodni pogoj
        if(beseda.charAt(znakBesede)==crke[x][y]  && ((beseda.length()-1)==znakBesede)){
            //System.out.print("najdu besedo: "+beseda);
          //na tem mestu smo, je veljavno, torej oznacim
            crke[x][y]='.';
            printWriter.printf("%s,%d,%d,%d,%d\n",beseda,x1,y1,x,y);

            //System.out.println("najdu sm besedo: "+beseda+" prva koordinata je: nevem ; zadna koordinata je: "+x+","+y);

            return true;
        }
        
        //besede dolzina 1
        if((beseda.length()==1) && (beseda.charAt(0)==crke[x][y])){
        	//na tem mestu smo, je veljavno, torej oznacim
            crke[x][y]='.';
            //System.out.print("najdu znak: "+beseda);
            printWriter.printf("%s,%d,%d,%d,%d\n",beseda,x1,y1,x,y);
            return true;
        }
        
        //ujemanje crke, moramo se naprej...
        if(crke[x][y]==beseda.charAt(znakBesede)){
        	//na tem mestu smo, je veljavno, torej oznacim
            crke[x][y]='.';

            if(rekurzijaLevoGor(beseda, x+xlG, y+ylG, znakBesede+1,x1,y1)){
                return true;
            }else{
                crke[x][y]=rezerva[x][y];
                return false;
            }

        }

        //ce nismo prisli do konca vse za nazaj razveljavimo in returnamo false;
        

        crke[x][y]=rezerva[x][y];
        
        
        return false;
    }

    
    public static boolean rekurzijaDesno(String beseda, int x, int y, int znakBesede,int x1, int y1){
        //preveri ali je X-koordinata veljavna,manj od 0 ali pa daljsa od meje
        if(x<0 || x>=crke[0].length){
            return false;

        }
        //preveri ali je Y-koordinata veljavna
        if(y<0 || y>=crke.length){
            return false;
        }

        if(crke[x][y]=='.'){
            return false;
        }

        

        //izhodni pogoj
        if(beseda.charAt(znakBesede)==crke[x][y]  && ((beseda.length()-1)==znakBesede)){
            //System.out.print("najdu besedo: "+beseda);
          //na tem mestu smo, je veljavno, torej oznacim
            crke[x][y]='.';
            printWriter.printf("%s,%d,%d,%d,%d\n",beseda,x1,y1,x,y);

            //System.out.println("najdu sm besedo: "+beseda+" prva koordinata je: nevem ; zadna koordinata je: "+x+","+y);

            return true;
        }
        
        //besede dolzina 1
        if((beseda.length()==1) && (beseda.charAt(0)==crke[x][y])){
        	//na tem mestu smo, je veljavno, torej oznacim
            crke[x][y]='.';
            //System.out.print("najdu znak: "+beseda);
            printWriter.printf("%s,%d,%d,%d,%d\n",beseda,x1,y1,x,y);
            return true;
        }
        
        //ujemanje crke, moramo se naprej...
        if(crke[x][y]==beseda.charAt(znakBesede)){
        	//na tem mestu smo, je veljavno, torej oznacim
            crke[x][y]='.';

            if(rekurzijaDesno(beseda, x+desnox, y+desnoy, znakBesede+1,x1,y1)){
                return true;
            }else{
                crke[x][y]=rezerva[x][y];
                return false;
            }

        }

        //ce ni true ze prej, razveljavimo in returnamo false;
      

        crke[x][y]=rezerva[x][y];
        
        
        return false;
    }
    
    public static boolean rekurzijaLevo(String beseda, int x, int y, int znakBesede,int x1, int y1){
        //preveri ali je X-koordinata veljavna,manj od 0 ali pa daljsa od meje
        if(x<0 || x>=crke[0].length){
            return false;

        }
        //preveri ali je Y-koordinata veljavna
        if(y<0 || y>=crke.length){
            return false;
        }

        if(crke[x][y]=='.'){
            return false;
        }

        

        //izhodni pogoj
        if(beseda.charAt(znakBesede)==crke[x][y]  && ((beseda.length()-1)==znakBesede)){
            //System.out.print("najdu besedo: "+beseda);
          //na tem mestu smo, je veljavno, torej oznacim
            crke[x][y]='.';
            printWriter.printf("%s,%d,%d,%d,%d\n",beseda,x1,y1,x,y);

            //System.out.println("najdu sm besedo: "+beseda+" prva koordinata je: nevem ; zadna koordinata je: "+x+","+y);

            return true;
        }
        
        //besede dolzina 1
        if((beseda.length()==1) && (beseda.charAt(0)==crke[x][y])){
        	//na tem mestu smo, je veljavno, torej oznacim
            crke[x][y]='.';
            //System.out.print("najdu znak: "+beseda);
            printWriter.printf("%s,%d,%d,%d,%d\n",beseda,x1,y1,x,y);
            return true;
        }
        
        //ujemanje crke, moramo se naprej...
        if(crke[x][y]==beseda.charAt(znakBesede)){
        	//na tem mestu smo, je veljavno, torej oznacim
            crke[x][y]='.';

            if(rekurzijaLevo(beseda, x+levox, y+levoy, znakBesede+1,x1,y1)){
                return true;
            }else{
                crke[x][y]=rezerva[x][y];
                return false;
            }

        }

        //ce ni true ze prej, razveljavimo in returnamo false;
        

        crke[x][y]=rezerva[x][y];
        
        
        return false;
    }
    
    public static boolean rekurzijaLevoDol(String beseda, int x, int y, int znakBesede, int x1, int y1){
        //preveri ali je X-koordinata veljavna,manj od 0 ali pa daljsa od meje
        if(x<0 || x>=crke[0].length){
            return false;

        }
        //preveri ali je Y-koordinata veljavna
        if(y<0 || y>=crke.length){
            return false;
        }

        if(crke[x][y]=='.'){
            return false;
        }


        //izhodni pogoj
        if(beseda.charAt(znakBesede)==crke[x][y]  && ((beseda.length()-1)==znakBesede)){
            //System.out.print("najdu besedo: "+beseda);
          //na tem mestu smo, je veljavno, torej oznacim
            crke[x][y]='.';
            printWriter.printf("%s,%d,%d,%d,%d\n",beseda,x1,y1,x,y);

            //System.out.println("najdu sm besedo: "+beseda+" prva koordinata je: nevem ; zadna koordinata je: "+x+","+y);

            return true;
        }
        
        //besede dolzina 1
        if((beseda.length()==1) && (beseda.charAt(0)==crke[x][y])){
        	//na tem mestu smo, je veljavno, torej oznacim
            crke[x][y]='.';
            //System.out.print("najdu znak: "+beseda);
            printWriter.printf("%s,%d,%d,%d,%d\n",beseda,x1,y1,x,y);
            return true;
        }
        
        //ujemanje crke, moramo se naprej...
        if(crke[x][y]==beseda.charAt(znakBesede)){
        	//na tem mestu smo, je veljavno, torej oznacim
            crke[x][y]='.';

            if(rekurzijaLevoDol(beseda, x+dolLx, y+dolLy, znakBesede+1,x1,y1)){
                return true;
            }else{
                crke[x][y]=rezerva[x][y];
                return false;
            }

          
        }

        //ce ni true ze prej, razveljavimo in returnamo false;
        

        crke[x][y]=rezerva[x][y];
        
        
        return false;
    }
    
    public static boolean rekurzijaDesnoDol(String beseda, int x, int y, int znakBesede, int x1, int y1){
        //preveri ali je X-koordinata veljavna,manj od 0 ali pa daljsa od meje
        if(x<0 || x>=crke[0].length){
            return false;
        }
        //preveri ali je Y-koordinata veljavna
        if(y<0 || y>=crke.length){
            return false;
        }

        if(crke[x][y]=='.'){
            return false;
        }

        

        //izhodni pogoj
        if(beseda.charAt(znakBesede)==crke[x][y]  && ((beseda.length()-1)==znakBesede)){
            //System.out.print("najdu besedo: "+beseda);
          //na tem mestu smo, je veljavno, torej oznacim
            crke[x][y]='.';
            printWriter.printf("%s,%d,%d,%d,%d\n",beseda,x1,y1,x,y);

            //System.out.println("najdu sm besedo: "+beseda+" prva koordinata je: nevem ; zadna koordinata je: "+x+","+y);

            return true;
        }
        
        //besede dolzina 1
        if((beseda.length()==1) && (beseda.charAt(0)==crke[x][y])){
        	//na tem mestu smo, je veljavno, torej oznacim
            crke[x][y]='.';
            //System.out.print("najdu znak: "+beseda);
            printWriter.printf("%s,%d,%d,%d,%d\n",beseda,x1,y1,x,y);
            return true;
        }
        
        //ujemanje crke, moramo se naprej...
        if(crke[x][y]==beseda.charAt(znakBesede)){
        	//na tem mestu smo, je veljavno, torej oznacim
            crke[x][y]='.';

            if(rekurzijaDesnoDol(beseda, x+dolDx, y+dolDy, znakBesede+1,x1,y1)){
                return true;
            }else{
                crke[x][y]=rezerva[x][y];
                return false;
            }
            
        }

        //ce niso crke enake, razveljavimo in returnamo false;
        
        crke[x][y]=rezerva[x][y];
        
        
        return false;
    }
    
    public static boolean rekurzijaDol(String beseda, int x, int y, int znakBesede, int x1, int y1){
        //preveri ali je X-koordinata veljavna,manj od 0 ali pa daljsa od meje
        if(x<0 || x>=crke[0].length){
            return false;

        }
        //preveri ali je Y-koordinata veljavna
        if(y<0 || y>=crke.length){
            return false;
        }

        if(crke[x][y]=='.'){
            return false;
        }



        //izhodni pogoj
        if(beseda.charAt(znakBesede)==crke[x][y]  && ((beseda.length()-1)==znakBesede)){
            //System.out.print("najdu besedo: "+beseda);
          //na tem mestu smo, je veljavno, torej oznacim
            crke[x][y]='.';
            printWriter.printf("%s,%d,%d,%d,%d\n",beseda,x1,y1,x,y);

            //System.out.println("najdu sm besedo: "+beseda+" prva koordinata je: nevem ; zadna koordinata je: "+x+","+y);

            return true;
        }
        
        //besede dolzina 1
        if((beseda.length()==1) && (beseda.charAt(0)==crke[x][y])){
        	//na tem mestu smo, je veljavno, torej oznacim
            crke[x][y]='.';
            //System.out.print("najdu znak: "+beseda);
            printWriter.printf("%s,%d,%d,%d,%d\n",beseda,x1,y1,x,y);
            return true;
        }
        
        //ujemanje crke, moramo se naprej...
        if(crke[x][y]==beseda.charAt(znakBesede)){
        	//na tem mestu smo, je veljavno, torej oznacim
            crke[x][y]='.';

            if(rekurzijaDol(beseda, x+dolx, y+doly, znakBesede+1,x1,y1)){
                return true;
            }else{
                crke[x][y]=rezerva[x][y];
                return false;
            }

            
        }

        //ce niso crke enake, razveljavimo in returnamo false;
        

        crke[x][y]=rezerva[x][y];
        
        
        return false;
    }
}