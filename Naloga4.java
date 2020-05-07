import java.io.*;

import javax.lang.model.element.Element;

class LinkedListElement{
    int element;
    int stPonovitev;
    
    LinkedListElement next;

    LinkedListElement(int a,int st){
        element=a;
        stPonovitev=st;
        next=null;
    }

    LinkedListElement(int a,int st,LinkedListElement nxt){
        element=a;
        stPonovitev=st;
        next=nxt;

    }
}

class LinkedList{
    protected LinkedListElement first;
    protected LinkedListElement last;

    LinkedList(){
        makenull();
    }

    public void makenull(){
        first = new LinkedListElement(0,0,null);
        last = null;

    }

    public void addLast(int a,int st){
        LinkedListElement nov = new LinkedListElement(a,st,null);

        if(last==null){
            first.next=nov;
            last=first;
        }else{
            last.next.next=nov;
            last=last.next;

        }


    }

    public void write(){
        LinkedListElement el;
        
        //zacnemo pri elementu za glavo seznama
        el = first.next;
        while (el != null)
        {
            System.out.print(el.element + "," + el.stPonovitev + "-");
           
            el = el.next;
        }
        
        System.out.println();
    
    }
    void addFirst(int obj){

        LinkedListElement newEl = new LinkedListElement(obj);
        
        //novi element postavimo za glavo seznama
        newEl.next = first.next;
        first.next = newEl;
        
        if (last == null)//preverimo, ali je to edini element v seznamu
            last = first;
        else if (last == first)//preverimo, ali je seznam vseboval en sam element
            last = newEl;
    }
}

public class Naloga4{
    //public static FileWriter izhod;
    public static PrintWriter printWriter;

    LinkedList tabela[] = new LinkedList[10000];

    public static void main(String[] args)throws IOException{
        long start = System.currentTimeMillis();
    
        //String vh = args[0];
        //String iz = args[1];
        String vh="C:\\Users\\ZIGA MEDVED\\Desktop\\APS1-Seminarska1\\Naloga4\\I4_1.txt";
        String iz="C:\\Users\\ZIGA MEDVED\\Desktop\\APS1-Seminarska1\\Naloga4\\test.txt";

        BufferedReader tok = new BufferedReader(new FileReader(vh));
        printWriter = new PrintWriter(new FileWriter(iz));

        String vrstica;
        vrstica = tok.readLine();

        int steviloUkazov = Integer.parseInt(vrstica);
        System.out.println(steviloUkazov);
        
        String ukazi[][] = new String[steviloUkazov][10000];

        for(int i = 0; i<steviloUkazov; i++){
            vrstica=tok.readLine();
            ukazi[i]=vrstica.split(",");
            //System.out.println(ukazi[i][0]);

        }

        /*
        for(int i = 0; i<ukazi[1].length;i++){
            System.out.println(ukazi[1][i]);

        }*/

        for(int i = 0; i<steviloUkazov; i++){
            char action = ukazi[i][0];

            if(action=='U'){

                int imeVrece=ukazi[i][1];
                tabela[imeVrece]=new LinkedList();

            }else if(action == 'Z'){
                int rezultatZdruzevanja = ukazi[i][1];
                int drugaVreca = ukazi[i][2];


            }else if(action == 'R'){
                int rezultatRazlike = ukazi[i][1];
                int drugaVreca = ukazi[i][2];

                
            }else if(action == 'S'){
                int rezultatSkupno = ukazi[i][1];
                int drugaVreca = ukazi[i][2];
                
            }else if(action == 'P'){
                int rezultatPorezi = ukazi[i][1];
                int konstatna = ukazi[i][2];
                
            }else if(action == 'O'){
                int rezultatObdrzi =ukazi [i][1];
                int drugaVreca = ukazi[i][2];
                
            }else if(action == 'I'){
                int rezultatIzpisi = ukazi [i][1];
                int drugaVreca = ukazi[i][2];
                
            }


            //System.out.print(ukazi[i][j]+",");
            
            //System.out.println();
        }


        long end = System.currentTimeMillis();
        System.out.println("\nExecution speed "+ (end - start)+" ms\n");

    }



}