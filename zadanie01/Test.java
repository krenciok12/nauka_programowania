/** @author Tomasz Krent **/
public class Test
{
    public static void main(String[] args)
    {
        int i=0;
        int n,m;
        try
        {
            n=Integer.parseInt(args[i]);
            if (n>=0 && n<34)
            {
                WierszTrojkataPascala wiersz = new WierszTrojkataPascala(n);
                for (int j=1;j<args.length;j=j+1)
                {
                    try
                    {
                        m=Integer.parseInt(args[j]);
                        if (m>n || m<0)
                        {
                            System.out.println(args[j] + " liczba spoza zakresu");
                        }
                        else
                            System.out.println(args[j] + " - " + wiersz.wspolczynnik(m));
                    }

                    catch (NumberFormatException ex)
                    {
                        System.out.println(args[j] + " nie jest liczba calkowita");
                    }
                }
            }
            else
            {
                System.out.println("Nieprawid³owy numer wiersza");
            }
        }
        catch (NumberFormatException ex)
        {
            System.out.println(args[i] + " nie jest liczba calkowita");
        }
    }
}

