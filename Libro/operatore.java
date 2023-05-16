import java.io.*;

public class operatore
{
    public static void main(String args[])throws Exception{
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader tastiera = new BufferedReader(input);
    
        Libro l1;
        l1 = new Libro();

        String s,c;
        int n;
        float x;

        System.out.println("inserisci titolo: ");
            s = tastiera.readLine();
            l1.setTitolo(s);
        System.out.println("inserisci numero di pagine: ");
            n = Integer.valueOf(tastiera.readLine()).intValue();
            l1.setPagine(n);
        System.out.println("inserisci prezzo: ");
            x = Float.valueOf(tastiera.readLine()).floatValue();
            l1.setPrezzo(x);
        System.out.println("inserisci casa editrice: ");
            c = tastiera.readLine();
            l1.setEditrice(c);
     
        l1.getStampa();

        x = l1.getSconto();
        System.out.println("Il prezzo scontato è: "+ x);
      
    }
}