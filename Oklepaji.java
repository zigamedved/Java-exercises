import java.util.*;
public class Oklepaji {
	
	//funkcija ima na voljo "ukl" uklepajev in "zak" zaklepajev za 
	//nadaljevanje znakovnega zaporedja "izraz".
	//izraz nadaljujemo tako, da stevilo zaklepajev nikoli ne presega stevila oklepajev (gledano z leve proti desni)
	public static void nadaljujIzraz(int ukl, int zak, char[] izraz, int n)
	{
		
		// robni pogoj - oklepajev ni vec na voljo
		if(ukl==0 && zak ==0){
			System.out.println(izraz);
			return;
		}
		
		if(ukl>0){
			izraz[n]='(';
			nadaljujIzraz(ukl-1,zak,izraz,n+1);
		}
		if(ukl<zak){
			izraz[n]=')';
			nadaljujIzraz(ukl,zak-1,izraz,n+1);
		}
		
		
		
		// gradnjo izraza nadaljujemo bodisi z '(' bodisi z ')', ce so se na voljo 
	}
		
	public static void sestaviIzraz(int N)
	{
		char[] izraz = new char[2*N];
		nadaljujIzraz(N, N, izraz, 0);
	}
		
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Vnesite stevilo oklepajev: ");
		int n=sc.nextInt();
		sestaviIzraz(n);
		System.out.println();
	}

}
