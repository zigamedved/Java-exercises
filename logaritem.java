import java.io.*;
import java.util.Scanner;

public class logaritem{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        System.out.println("Vnesite stevilo in bazo logaritma: ");

        int a;
        int b;
        a=sc.nextInt();
        b=sc.nextInt();

        if(a==0 || b == 0){
            System.out.println("Napaka pri vnosu!");
            System.exit(0);
        }
        
        System.out.print("Za rekurziven izracun vnesite 1, za iterativen izracun pa 2");

        int nacin = sc.nextInt();

        switch(nacin){
            case 1 :{
                rekurzivno(a,b,0);
                break;

            }
            
            
            case 2 :{
                int stevec=0;
                while(a>b){
                    a=a/b;
                    stevec++;
                }
                System.out.println("Rezultat je: "+stevec);
                break;
            }
            
       }
    }

    static void rekurzivno(int a, int b,int stevec){
        if(a<b){
            System.out.println("Rezultat je: "+stevec); //stevec==rezultat
            return;
        }else{
            rekurzivno(a/b, b, stevec+1);
        }
        
        

    }
}