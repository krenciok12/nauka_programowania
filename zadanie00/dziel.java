public class dziel
{
    public static int div(int n)
    {
        if (n==0 || n==1)
            return 0;
        int i=n-1;
        while(n%i!=0)
        {
            i=i-1;
        }
        return i;
    }
    public static void main(String[] args)
    {
        for (int i=0;i<args.length;i=i+1)
        {
            int n;
            ;
            try
            {
                n=Integer.parseInt(args[i]);
                int wynik;
                wynik=div(n);
                System.out.println(wynik);
            }
            catch (NumberFormatException ex)
            {
                System.out.println(args[i] + " nie jest liczba calkowita");
            }


        }

    }
}
