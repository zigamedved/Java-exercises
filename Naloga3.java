import java.io.*;

class LinkedListElement{
    int element;
    LinkedListElement next;

    LinkedListElement(int a){
        element=a;
        next=null;
    }

    LinkedListElement(int a, LinkedListElement nxt){
        element=a;
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
        first = new LinkedListElement(0,null);
        last = null;

    }

    public void addLast(int a){
        LinkedListElement nov = new LinkedListElement(a,null);

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
            System.out.print(el.element + ",");
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

    public void preslikaj(char op,int val){

        LinkedListElement trenutni = first.next;

        if(op=='+'){
            while(trenutni!=null){
                trenutni.element=trenutni.element+val;
                trenutni=trenutni.next;
            }

        }else{
            while(trenutni!=null){
                trenutni.element=trenutni.element*val;
                trenutni=trenutni.next;
            }   

        }

        //izpis
        //System.out.println("preslikaj:");
        //this.write();


    }
    public void ohrani(char op,int val){

        //System.out.println("operator"+op);
        LinkedListElement current = first.next;
        LinkedListElement prejsnji = null;

        //this.write();
        

        if(op=='<'){
           // System.out.println("\nOdstranjujemo vecje od: "+val);
            while(current!=null && current.element>=val){
                first.next=current.next;
                current=first.next;
            }
            while(current!=null){
                while(current!=null && current.element < val){
                    prejsnji=current;
                    current=current.next;
                }
                if(current==null){
                    return;
                }
                prejsnji.next=current.next;
                current=prejsnji.next;
            }
        }else if(op=='>'){
            //System.out.println("\nOhranimo vecje od: "+val);
            while(current!=null && current.element<=val){
                first.next=current.next;
                current=first.next;
            }
            while(current!=null){
                while(current!=null && current.element > val){
                    prejsnji=current;
                    current=current.next;
                }
                if(current==null){
                    return;
                }
                prejsnji.next=current.next;
                current=prejsnji.next;
            }
            
        }else if(op=='='){
            while(current!=null && current.element!=val){
                first.next=current.next;
                current=first.next;
            }
            while(current!=null){
                while(current!=null && current.element == val){
                    prejsnji=current;
                    current=current.next;
                }
                if(current==null){
                    return;
                }

                prejsnji.next=current.next;
                current=prejsnji.next;
            }

        }

       
        //this.write();





    }
    public void zdruzi(char op){
        LinkedListElement el=first.next;

        int data=0;

        if(op=='+'){
            while(el!=null){
                data=data+el.element;
                el=el.next;
            }
        }else{
            while(el!=null){
                data=data*el.element;
                el=el.next;
            }
        }
        
        //el.element=data;
        this.first.element=data;
        

        //izpis
        //System.out.println("zdruzi vrednost je:\n"+data);
        


    }
    int length()
	{
		int counter;
		LinkedListElement el;
		
		counter = 0;
		
		//zacnemo pri elementu za glavo seznama
		el = first.next;
		while (el != null)
		{
			counter++;
			el = el.next;
		}
		
		return counter;
	}



}

public class Naloga3{
    public static FileWriter izhod;
    public static PrintWriter printWriter;

    public static void main(String[]args)throws IOException{
         //cas izvajanja
         long start = System.currentTimeMillis();

        //"C:\\Users\\ZIGA MEDVED\\Desktop\\APS1-Seminarska1\\Naloga3\\I3_1.txt"

         //shranimo vhod,izhod
         String vh = args[0];
         String iz = args[1];

        BufferedReader tok = new BufferedReader(new FileReader(vh));
        izhod = new FileWriter(iz);
        printWriter = new PrintWriter(izhod);

        String vrstica;
        //preberemo...
        vrstica = tok.readLine();

        String[] vrednosti = vrstica.split(",");
        int[] data = new int[vrednosti.length];
        LinkedList seznam = new LinkedList();

        //System.out.println("Stevilo vozlisc je: "+vrednosti.length);
        for(int i = 0; i<vrednosti.length; i++){
            //System.out.print(vrednosti[i]+",");
            data[i]=Integer.parseInt(vrednosti[i]);
            //System.out.print(data[i]+",");
        }
        seznam.addFirst(data[0]);
        for(int i = 1; i<data.length; i++){
            seznam.addLast(data[i]);
        }
        //System.out.println("Moj seznam: ");
        //seznam.write(); direktno zapisi v izhod...

        vrstica=tok.readLine();
        int podatek = Integer.parseInt(vrstica);
        //System.out.println(podatek);
        String[][] ukazi = new String[podatek][];

        for(int i = 0; i<podatek; i++){
            vrstica=tok.readLine();
            ukazi[i]=vrstica.split(",");
            
        }
        
        for(int i = 0; i<podatek; i++){
            //System.out.println(ukazi[i][0]);
            //System.out.println(ukazi[i][1]);
            char ukaz = ukazi[i][0].charAt(0);
            char operator = ukazi[i][1].charAt(0);
            int stevilo;
            if(ukaz=='z'){
                stevilo=0;
            }else{
                stevilo=Integer.parseInt(ukazi[i][2]);
            }
            //System.out.println("ukaz, operator, stevilo: "+ukaz+" "+operator+" "+stevilo);
            LinkedListElement pomik;

            if(ukaz=='o'){

               
               
               
                seznam.ohrani(operator, stevilo);
                
               
               
               
                pomik=seznam.first.next;
                
                //System.out.print("ohrani:\n");
                //seznam.write();
                for(int a = 0; a<seznam.length()-1;a++){
                   /* if(a==seznam.length()-1){
                        printWriter.print(pomik.element);
                        continue;    
                    }*/
                    printWriter.print(pomik.element+",");
                    pomik=pomik.next;
                    
                }
                //pomik=pomik.next;
                printWriter.print(pomik.element);
                printWriter.println();

            }else if(ukaz=='p'){
                seznam.preslikaj(operator, stevilo);
                pomik=seznam.first.next;
                //System.out.print("preslikaj:\n");
                //seznam.write();
                
                for(int a = 0; a<seznam.length()-1;a++){
                    /*if(a==seznam.length()-1){
                        printWriter.print(pomik.element);
                        continue;    
                    }*/
                    printWriter.print(pomik.element+",");
                    
                    pomik=pomik.next;
                }
                //pomik=pomik.next;
                printWriter.print(pomik.element);
                printWriter.println();

            }else if(ukaz=='z'){
                seznam.zdruzi(operator);
                pomik=seznam.first.next;
                int konec=0;
                konec = seznam.first.element;
                
                printWriter.print(konec);

            }

            


        }
        

        //System.out.print("Moj seznam:\n");
        //seznam.write();
        //LinkedListElement eee=seznam.first;
        tok.close();
        printWriter.close();
        //cas izvajanja
        long end = System.currentTimeMillis();
        //System.out.println("\nExecution speed "+ (end - start)+" ms\n");


    }
}

