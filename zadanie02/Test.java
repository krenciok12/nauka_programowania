import static java.lang.Math.*;

class MojException extends Exception {};
class MojException2 extends Exception {};
class MojException3 extends Exception {};

abstract class Figura
{
    public abstract double Pole();
    public abstract double Obwod();
}

class Okrag extends Figura
{
    private double obwod;
    private double pole;

    Okrag (int r) throws MojException
    {
        if(r<=0)
            throw new MojException();
        pole=Math.PI*r*r;
        obwod=r*2*Math.PI;
    }

    public double Obwod()
    {
        return obwod;
    }

    public double Pole()
    {
        return pole;
    }
}

class Pieciokat extends Figura
{
    private double obwod;
    private double pole;

    Pieciokat(int a) throws MojException
    {
        if(a<=0)
            throw new MojException();
        obwod=a*5;
        double x=Math.toRadians(36);
        pole=a*a*1/Math.tan(x)/4;
    }

    public double Obwod()
    {
        return obwod;
    }

 
    public double Pole()
    {
        return pole;
    }
}

class Szesciokat extends Figura
{
    private double obwod;
    private double pole;

    Szesciokat(int a) throws MojException
    {
        if(a<=0)
            throw new MojException();
        obwod=a*6;
        pole=6*a*a*sqrt(3)/4;
    }

    public double Obwod()
    {
        return obwod;
    }

    public double Pole()
    {
        return pole;
    }
}

abstract class Czworokat extends Figura
{
    Czworokat(int a, int b, int c, int d, int k) throws MojException
    {
        if (a<=0 || b<=0 || c<=0 || d<=0 || k<=0 || k>=360)
            throw new MojException();
    }

}

class Prostokat extends Czworokat
{
    private double obwod;
    private double pole;

    Prostokat(int a, int b, int c, int d, int k) throws MojException
    {

        super(a,b,c,d,k);

        obwod=a+b+c+d;
        double x=Math.toRadians(k);
        pole=a*b*Math.sin(x);
    }

    public void Czyprostokat(int a, int b, int c, int d, int k) throws MojException3
    {
        if (!(a==c && b==d && k==90))
            throw new MojException3();
    }

    public double Obwod()
    {
        return obwod;
    }

    public double Pole()
    {
        return pole;
    }
};

class Romb extends Czworokat
{
    private double obwod;
    private double pole;

    Romb(int a, int b, int c, int d, int k) throws MojException
    {
        super(a,b,c,d,k);
        obwod=a+b+c+d;
        double x=Math.toRadians(k);
        pole=a*b*Math.sin(x);
    }
    public void Czyromb (int a, int b, int c, int d, int k) throws MojException3
    {
        if (!(a==b && b==c && c==d && k>0 && k<180))
            throw new MojException3();
    }

    public double Obwod ()
    {
        return obwod;
    }

    public double Pole()
    {
        return pole;
    }
};

class Kwadrat extends Czworokat
{
    private double obwod;
    private double pole;

    Kwadrat(int a, int b, int c, int d, int k) throws MojException
    {
        super(a,b,c,d,k);
        obwod=a+b+c+d;
        double x=Math.toRadians(k);
        pole=a*b*Math.sin(x);
    }
    public void Czykwadrat (int a, int b, int c, int d, int k) throws MojException2
    {
        if (!(a==b && b==c && c==d && k==90))
            throw new MojException2();
    }

    public double Obwod ()
    {
        return obwod;
    }

    public double Pole()
    {
        return pole;
    }
};


public class Test
{
    public static void main(String[] args)
    {
        if (args.length==0)
        {
            System.exit(0);
        }

         int start = 0;
         int a,b,c,d,r,k;
         int end = args[0].length();
         char tab[ ] = new char[end];

         args[0].getChars(start, end, tab, 0);
         System.out.println(tab);

         Figura f[]= new Figura[end];

         int j=1;
         int ilf=0;

         int ilosccyfr=0;

         for (int i=0;i<end;i=i+1)
         {
            switch (tab[i]) {
                case 'c':
                    ilosccyfr=ilosccyfr+5;
                    break;
                case 's':
                case 'o':
                case 'p':
                    ilosccyfr=ilosccyfr+1;
                    break;
                default:
                    System.out.println("Wpisano zla litere: "+ tab[i]);
                    System.exit(0);
            }
         }

         if (ilosccyfr!=args.length-1)
         {
             System.out.println("bledna ilosc argumentow");
             System.exit(0);
         }

         for (int i=0;i<end;i=i+1)
         {
            if (tab[i]=='c')
            {
                try
                {
                    a=Integer.parseInt(args[j]);
                    b=Integer.parseInt(args[j+1]);
                    c=Integer.parseInt(args[j+2]);
                    d=Integer.parseInt(args[j+3]);
                    k=Integer.parseInt(args[j+4]);
                    try
                    {

                        Kwadrat kw= new Kwadrat(a,b,c,d,k);
                        try
                        {
                            kw.Czykwadrat(a,b,c,d,k);
                            f[ilf]= new Kwadrat(a,b,c,d,k);
                            System.out.println("Obwod kwadratu wynosi: "+f[ilf].Obwod() + " a pole: " + f[ilf].Pole());
                            ilf=ilf+1;
                        }
                        catch (MojException2 e)
                        {
                            System.out.println("czworokat nie jest kwadratem");
                            Prostokat s= new Prostokat(a,b,c,d,k);
                            try
                            {
                                s.Czyprostokat(a,b,c,d,k);
                                f[ilf]= new Prostokat(a,b,c,d,k);
                                System.out.println("Obwod prostokata wynosi: "+f[ilf].Obwod() + " a pole: " + f[ilf].Pole());
                                ilf=ilf+1;
                            }
                            catch (MojException3 ex)
                            {
                                System.out.println("czworokat nie jest prostokatem");
                            }


                            Romb t= new Romb(a,b,c,d,k);

                            try
                            {
                                t.Czyromb(a,b,c,d,k);
                                f[ilf]= new Romb(a,b,c,d,k);
                                System.out.println("Obwod rombu wynosi: "+f[ilf].Obwod() + " a pole: " + f[ilf].Pole());
                                ilf=ilf+1;
                            }
                            catch (MojException3 ex)
                            {
                                System.out.println("czworokat nie jest rombem");
                            }
                        }
                    }
                    catch (MojException e)
                    {
                        System.out.println("bledne dane nie da sie obliczyc pola i obwodu czworokata");
                    }
                }
                catch (NumberFormatException ex)
                {
                    System.out.println( " nie wszystkie liczby sa calkowita");
                }
                j=j+5;
             }

            if (tab[i]=='o')
            {
                try
                {
                    r=Integer.parseInt(args[j]);
                    try
                    {
                        f[ilf]= new Okrag(r);
                        System.out.println("Obwod kola o promieniu "+r+" wynosi: "+f[ilf].Obwod() + " a pole: " + f[ilf].Pole());
                        ilf=ilf+1;
                    }
                    catch (MojException e)
                    {
                        System.out.println("bledna dana nie da sie obliczyc pola i obwodu okregu"+ r);
                    }
                }
                catch (NumberFormatException ex)
                {
                    System.out.println(args[j] + "r nie jest liczba calkowita");
                }
                j=j+1;
            }

            if (tab[i]=='p')
            {
                try
                {
                    a=Integer.parseInt(args[j]);
                    try
                    {
                        f[ilf]= new Pieciokat(a);
                        System.out.println("Obwod pieciokata foremnego o boku "+a+" wynosi: "+f[ilf].Obwod() + " a pole: " + f[ilf].Pole());
                        ilf=ilf+1;
                    }
                    catch (MojException e)
                    {
                        System.out.println("bledna dana nie da sie obliczyc pola i obwodu pieciokata"+ a);
                    }
                }
                catch (NumberFormatException ex)
                {
                    System.out.println(args[j] + "a nie jest liczba calkowita");
                }
                j=j+1;
            }

            if (tab[i]=='s')
            {
                try
                {
                    a=Integer.parseInt(args[j]);
                    try
                    {
                        f[ilf]= new Szesciokat(a);
                        System.out.println("Obwod szesciokata foremnego o boku "+a+" wynosi: "+f[ilf].Obwod() + " a pole: " + f[ilf].Pole());
                        ilf=ilf+1;
                    }
                    catch (MojException e)
                    {
                        System.out.println("bledna dana nie da sie obliczyc pola i obwodu szesciokata"+ a);
                    }
                }
                catch (NumberFormatException ex)
                {
                    System.out.println(args[j] + "a nie jest liczba calkowita");
                }
                j=j+1;
            }
        }
    }

}
