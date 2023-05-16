
class Libro
{
    // variabili d'istanza - sostituisci l'esempio che segue con il tuo
    private String tit, editrice;
    private int n_pag;
    private float prezzo;

    /**
     * Costruttore degli oggetti di classe  Libro
     */
    public Libro()
    {
        // inizializza le variabili d'istanza
        tit = "_";
        n_pag = 0;
        prezzo = 0;
        editrice = "_";
    }

    public void setTitolo(String a)
    {
        tit = a;
    }
    public void setPrezzo(float b)
    {
        prezzo = b;
    }
    public void setPagine(int n)
    {
        n_pag = n;
    }
    public void setEditrice(String c)
    {
        editrice = c;
    }
    public float getSconto()
    {
        return( prezzo*0.9f);
    }
    public void getStampa()
    {
      System.out.println("Titolo: " + tit);
      System.out.println("Numero di pagine: " + n_pag);
      System.out.println("Prezzo: " + prezzo);
      System.out.println("Casa editrice: " + editrice);
    }

}
