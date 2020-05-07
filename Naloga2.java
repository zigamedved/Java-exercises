import java.util.Scanner;
import java.io.*;

//rabim se za brisat ter dolzina???..
class LinkedListElement{

    String element;
    LinkedListElement next;

    LinkedListElement(String podatek){
        element = podatek;
        next = null;
    }

    //dodamo naslednika...
    LinkedListElement(String podatek,LinkedListElement nxt){
        element = podatek;
        next = nxt;
    }
}

class LinkedList{

    protected LinkedListElement first;
    protected LinkedListElement last;

    LinkedList(){
        makenull();
    }

    //naredimo nov seznam
    public void makenull(){
        //na zacetku seznama header
        first = new LinkedListElement(null,null);
        last = null;

    }

    //funkcija write, za izpisovanje
    public void write(){

        //System.out.println("Izpis:\n");

        LinkedListElement el;
        //zacnemo pri elementu za glavo
        el = first.next;
        while(el!=null){
            System.out.print(el.element+",");
            el=el.next;
        }
        
        System.out.println();
    }

    public void vstavi(String data){

        //najprej naredimo nov element
        LinkedListElement nov = new LinkedListElement(data,null);

        //ali je seznam prazen?
        if(last==null){
            first.next=nov;
            last=first;
        }else{
            //last next je zadnji, next next je pa novi element...
            last.next.next=nov;
            //nou zadnji je zdaj predzadnji
            last=last.next;
        }
    }

    //takoj za glavo seznama vstavimo...
    public void vstaviPrvega(String data){
        //naredimo nov element
        LinkedListElement nov = new LinkedListElement(data);
        //nov element postavimo za glavo, najpre pa se recemo da naslednji, glave je next od novega
        //in glava.next je zdaj nas nov...

        nov.next=first.next;
        first.next=nov;
        //ali je to edini v seznamu 
        if(last==null){
            last=first;
        }else if(last==first){
            last=nov;
        }
    }

    int dolzina(){
        int stevec=0;
        LinkedListElement el;

        el=first.next;

        while(el!=null){
            stevec++;
            el=el.next;
        }
        return stevec;
    }

    

   
}

public class Naloga2{
    //staticna da sta dosegljiva...
    public static FileWriter izhod;
    public static PrintWriter printWriter;

    public static void main(String[] args) throws IOException{

        //cas izvajanja
        long start = System.currentTimeMillis();

        //shranimo vhod,izhod

        String vh = args[0];
        String iz = args[1];
        //String vh = "C:\\Users\\ZIGA MEDVED\\Desktop\\APS1-Seminarska1\\Naloga2\\I2_1.txt";
        //test: "C:\\Users\\ZIGA MEDVED\\Desktop\\APS1-Seminarska1\\Naloga2\\I2_1.txt"

        BufferedReader tok = new BufferedReader(new FileReader(vh));
        izhod = new FileWriter(iz);
        printWriter = new PrintWriter(izhod);

        String vrstica;
        //preberemo vrstico;
        vrstica=tok.readLine();

        String[] podatki=vrstica.split(",");
        
        int steviloKartZacetek = Integer.parseInt(podatki[0]);
        int steviloMesanj = Integer.parseInt(podatki[1]);

        //System.out.print(steviloKartZacetek+"+"+steviloMesanj);

        //preberem vse karte...
        vrstica=tok.readLine();
        String[] karte=vrstica.split(",");


        //zacetek uporabe classa linked list
        LinkedList seznam = new LinkedList();

        //vstavimo prvi element
        seznam.vstaviPrvega(karte[0]);
        //dodam vse elemente v seznam...
        for(int i = 1; i<steviloKartZacetek; i++){
            String trenuten = karte[i];
            //implementiraj dodajanje linkedlistelementa v list
            seznam.vstavi(trenuten);
           
        }

        //seznam.write();


        //System.out.println("first.next kaze na: "+seznam.first.next.element+"last.next pa kaze na:"+seznam.last.next.element);
        //System.out.println("first.next kaze na: "+seznam.first.next.element+"last pa kaze na:"+seznam.last.element);

        String[] D = new String[steviloMesanj];
        String[] V = new String[steviloMesanj];
        int[] S = new int[steviloMesanj];
        
        //System.out.println("Stevilo kart je: "+steviloKartZacetek);
        //System.out.println("Stevilo mesanje je: "+steviloMesanj);

        //preberem navodila, vsako navodilo je v svojem arrayu
        for(int i = 0; i<steviloMesanj; i++){
            vrstica = tok.readLine();
            String[] data = vrstica.split(",");

            D[i]=data[0];
            V[i]=data[1];
            S[i]=Integer.parseInt(data[2]);

            //System.out.println(D[i]+","+V[i]+","+S[i]);
        }
        //for ponovim steviloMesanj krat
        
        for(int m = 0; m<steviloMesanj; m++){

            //preberemo navodila i-tega mesanja
            String razpoloviTle=D[m]; //i
            String vstaviZa=V[m]; //i
            int vstaviJih=S[m]; //[i]

            //seznam je nas linked list ki smo ga sproti naredili ko smo brali podatke, nase izhodisce... na koncu bomo morali seznam 2 posodobiti, seznam = prviKup...
            LinkedListElement el = seznam.first.next;
            LinkedList kup2 = new LinkedList();
            boolean najdel = false;
            LinkedList prviKup = new LinkedList();

            //v vsakem primeru moramo vstaviti vsaj enega, prviKup ima sedaj 1 element
            prviKup.vstaviPrvega(el.element);

            //sprehodimo se po seznamu in najdemo mesto, kjer razpolovimo...

            for(int i = 0; i<seznam.dolzina(); i++){

                //ce je true, vstavimo prvega in dokler el(pomikamo do konca seznama) ni null vstavljamo notri elemente...
                if(najdel){
                    //ce najde, vstavimo prvega
                    kup2.vstaviPrvega(el.element);
                    el=el.next;
                    //pomaknemo enega naprej, napolnimo prviKup do konca
                    while(el!=null){
                        kup2.vstavi(el.element);
                        el=el.next;
                    }
                    break;
                }

                //ko najdemo, karto nastavimo flag
                if(el.element.equals(razpoloviTle)){
                    //System.out.println("nasli smo element");
                    najdel=true;
                    el=el.next;
                    continue;
                }

                //ce ne najdemo elementa;
                //KUP 2 JE POLN, KUP 1 PA PRAZEN...
                if((i == seznam.dolzina()-1) && (!najdel)){
                    //kup 2 je enak seznamu, prviKup in seznam pa "resetiramo"
                    kup2=seznam;
                    seznam = new LinkedList();
                    prviKup = new LinkedList();
                    break;
                }
                
                //ce pridemo do tukaj, premaknemo za eno naprej in vstavimo, premaknemo zato za eno naprej, ker smo prej ze vstavili prvi element;
                el=el.next;
                prviKup.vstavi(el.element);
           }
            //kupa sta razdeljena
           // System.out.println("razdeljena kupcka: ");
            //prviKup.write();
            //kup2.write();

            //od zdej naprej delamo s prviKup in kup2
            //System.out.println("prvi k2 zadnji k2: "+kup2.first.next.element+" "+kup2.last.next.element);
            //System.out.println("prvi 1kup konec 1 kupa: "+prviKup.first.next.element+" "+prviKup.last.next.element);

            LinkedListElement isci = prviKup.first.next;
            LinkedListElement vstaviTukaj = prviKup.first;

            //System.out.println("zacetek 1 kupa: "+prviKup.first.next.element);
            //sprehodim cez prviKup s kazalcem isci, ko najdem iskani element GA prepisem v pointer vstaviTukaj, ce ne najdemo
            //vstavljamo na zacetek vstaviTukaj,ki je ze nastavljen

            for(int j = 0; j<prviKup.dolzina();j++){
                if(isci.element.equals(vstaviZa)){
                    vstaviTukaj=isci;
                    break;
                }
              isci=isci.next;
            }
            
            //System.out.println("Prvi kup");
            //prviKup.write();
            //System.out.println("Drugi kup");
            //kup2.write();
            if(prviKup.dolzina()==0){
                vstaviTukaj=prviKup.first;
            }

            while(kup2.dolzina()!=0){
                LinkedListElement del2Kupa1;
                //razbijem kup, vedno na tem mestu vstavljam
                LinkedListElement vstaviZaTem=vstaviTukaj;
            
                //System.out.println(vstaviZaTem.element);
                //2 del razbitega prvega kupa
                    
                if(vstaviTukaj.next==null){
                    //del2Kupa1 = new LinkedListElement("prazen");
                    del2Kupa1 = null;
                }else{
                    del2Kupa1 = vstaviTukaj.next;
                }
                //System.out.println(del2Kupa1.element);
                for(int k = 0; k<vstaviJih; k++){
                    try{
                        vstaviZaTem.next=kup2.first.next;
                        vstaviZaTem=vstaviZaTem.next;
                        if(kup2.first.next.next==null){
                            vstaviZaTem.next=del2Kupa1;
                            kup2=new LinkedList();
                            break;
                        }
                        kup2.first.next=kup2.first.next.next;
                    }catch(NullPointerException e){
                        break;
                    }
                }
                vstaviZaTem.next=del2Kupa1;
            }
            //System.out.println("povezi s tem"+vstaviTukaj.next.element);
            //prviKup.write();
            //kup2.write();
            //System.out.print(kup2.dolzina());
            seznam=prviKup;
           // System.out.println("vsak obhod: ");
            //seznam.write();
        }

        LinkedListElement izpis = seznam.first.next;
        
       for(int i = 0; i<seznam.dolzina()-1; i++){
        printWriter.print(izpis.element+",");
        izpis=izpis.next;
       }
       printWriter.print(izpis.element);

        //seznam.write();
        tok.close();
        printWriter.close();

        //cas izvajanja
        long end = System.currentTimeMillis();
        //System.out.println("Execution speed "+ (end - start)+" ms\n");
    }
}