import static java.lang.Math.*;

class MojException extends Exception {};

class Figura
{
 
    public enum F1
    {
        KOLO, KWADRAT, SZESCIOKAT, PIECIOKAT;
        
        double ObliczPole(int a) throws MojException
        {
            if (a<=0)
                throw new MojException();
            switch(this)
            {
                case KOLO:
                    return Math.PI*a*a;
                case KWADRAT:
                    return a*a;
                case SZESCIOKAT:
                    return 6*a*a*sqrt(3)/4;
                case PIECIOKAT:
                    return a*a*1/Math.tan(Math.toRadians(36))/4;
                default:
                    return 1;
            }
        }
        double ObliczObwod(int a) throws MojException
        {
            if (a<=0)
                throw new MojException();
            switch(this)
            {
                case KOLO:
                    return Math.PI*a*2;
                case KWADRAT:
                    return a*4;
                case SZESCIOKAT:
                    return 6*a;
                case PIECIOKAT:
                    return a*5;
                default:
                    return 1;
            }
        }
     
    }
    
    public enum F2
    {
        ROMB, PROSTOKAT;
        
        double ObliczPole(int a, int b) throws MojException
        {
            
            switch(this)
            {
                case ROMB:
                {
                    if (a<=0 || b<=0 ||b>180)
                        throw new MojException();
                    return a*a*sin(b);
                }
                case PROSTOKAT:
                {
                    if (a<=0 || b<=0)
                        throw new MojException();
                    return a*b;
                }
                   
                default:
                    return 1;
            }
        }
        double ObliczObwod(int a, int b) throws MojException
        {
            if (a<=0 || b<=0)
                throw new MojException();
            switch(this)
            {

                case ROMB:
                {
                    if (a<=0 || b<=0 ||b>180)
                        throw new MojException();
                    return a*4;
                }
                case PROSTOKAT:
                {
                    if (a<=0 || b<=0)
                        throw new MojException();
                    return 2*a+2*b;
                }   
                    
                default:
                    return 1;
            }
        }
    }
}

public class Test
{
    public static void main(String[] args)
    {
        if (args.length==0)
        {
            System.out.println("brak danych");
            System.exit(0);
        }

         int start = 0;
         int a,b;
         double pole, obwod;
         int end = args[0].length();
         char tab[ ] = new char[end];

         args[0].getChars(start, end, tab, 0);
         System.out.println(tab);

         int j=1;
         int ilf=0;

         int ilosccyfr=0;
         
         for (int i=0;i<end;i++)
         {
             switch (tab[i]) 
             {
                case 'r':
                case 't':
                    ilosccyfr=ilosccyfr+2;
                    break;
                case 's':
                case 'o':
                case 'p':
                case 'k':
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
         
         for (int i=0; i<end; i++)
         {
             if (tab[i]=='r')
             {
                 try
                 {
                    a=Integer.parseInt(args[j]);
                    b=Integer.parseInt(args[j+1]);
                    try
                    {
                        pole=Figura.F2.ROMB.ObliczPole(a,b);
                        obwod=Figura.F2.ROMB.ObliczObwod(a, b);
                        System.out.println("Obwod rombu wynosi: "+obwod + " a pole: " + pole);
                    }
                     catch (MojException e)
                    {
                        System.out.println("bledne dane nie da sie obliczyc pola i obwodu rombu");
                    }
                 }
                 catch (NumberFormatException ex)
                {
                    System.out.println( " nie wszystkie liczby sa calkowita");
                }
                j=j+2;
             }
             
             if (tab[i]=='t')
             {
                 try
                 {
                    a=Integer.parseInt(args[j]);
                    b=Integer.parseInt(args[j+1]);
                    try
                    {
                        pole=Figura.F2.PROSTOKAT.ObliczPole(a,b);
                        obwod=Figura.F2.PROSTOKAT.ObliczObwod(a, b);
                        System.out.println("Obwod prostokata wynosi: "+obwod + " a pole: " + pole);
                    }
                     catch (MojException e)
                    {
                        System.out.println("bledne dane nie da sie obliczyc pola i obwodu prostokata");
                    }
                 }
                 catch (NumberFormatException ex)
                {
                    System.out.println( " nie wszystkie liczby sa calkowita");
                }
                j=j+2;
             }
             
             if (tab[i]=='o')
             {
                 try
                 {
                    a=Integer.parseInt(args[j]);
                    try
                    {
                        pole=Figura.F1.KOLO.ObliczPole(a);
                        obwod=Figura.F1.KOLO.ObliczObwod(a);
                        System.out.println("Obwod kola wynosi: "+obwod + " a pole: " + pole);
                    }
                     catch (MojException e)
                    {
                        System.out.println("bledne dane nie da sie obliczyc pola i obwodu kola");
                    }
                 }
                 catch (NumberFormatException ex)
                {
                    System.out.println( " liczba nie jest ca³kowita");
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
                        pole=Figura.F1.PIECIOKAT.ObliczPole(a);
                        obwod=Figura.F1.PIECIOKAT.ObliczObwod(a);
                        System.out.println("Obwod pieciokata wynosi: "+obwod + " a pole: " + pole);
                    }
                     catch (MojException e)
                    {
                        System.out.println("bledne dane nie da sie obliczyc pola i obwodu pieciokata");
                    }
                 }
                 catch (NumberFormatException ex)
                {
                    System.out.println( " liczba nie jest ca³kowita");
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
                        pole=Figura.F1.SZESCIOKAT.ObliczPole(a);
                        obwod=Figura.F1.SZESCIOKAT.ObliczObwod(a);
                        System.out.println("Obwod szzesciokata wynosi: "+obwod + " a pole: " + pole);
                    }
                     catch (MojException e)
                    {
                        System.out.println("bledne dane nie da sie obliczyc pola i obwodu szesciokata");
                    }
                 }
                 catch (NumberFormatException ex)
                {
                    System.out.println( " liczba nie jest ca³kowita");
                }
                j=j+1;
             }
             if (tab[i]=='k')
             {
                 try
                 {
                    a=Integer.parseInt(args[j]);
                    try
                    {
                        pole=Figura.F1.KWADRAT.ObliczPole(a);
                        obwod=Figura.F1.KWADRAT.ObliczObwod(a);
                        System.out.println("Obwod kwadratu wynosi: "+obwod + " a pole: " + pole);
                    }
                     catch (MojException e)
                    {
                        System.out.println("bledne dane nie da sie obliczyc pola i obwodu kwadratu");
                    }
                 }
                 catch (NumberFormatException ex)
                {
                    System.out.println( " liczba nie jest ca³kowita");
                }
                j=j+1;
             }
         }
         
         
    }
}