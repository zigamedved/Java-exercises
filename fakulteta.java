import java.util.*;


public class fakulteta{

    public static void main(String[] args){
        Scanner scanner=new Scanner(System.in);

        System.out.println("Vnesite število za izračun faktiorele le tega: ");
        int stevilo=0;
        stevilo=scanner.nextInt();

        System.out.println("Vnesite 1, za izracun na rekurziven nacin ali 2 iterativni nacin: ");
        int odgovor=scanner.nextInt();

        if(odgovor==1){
            System.out.printf("Fakulteta tega stevila (iterativno) je: %d\n",iterativniNacin(stevilo));
        }else{
            System.out.printf("Fakulteta tega stevila (rekurzivno) je: %d\n",rekurzivno(stevilo));
        }

    }

    public static int iterativniNacin(int stevilo){
        int rezultat=1;
        for(int i = stevilo; i>0; i--){
            rezultat*=i;
        }
        return rezultat;
    }

    public static int rekurzivno(int stevilo){
        if(stevilo==0){
            return 1;
        }else{
            return stevilo*rekurzivno(stevilo-1);
        }
    }
    
}