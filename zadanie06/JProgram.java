import javax.swing.*;
import java.awt.* ;
import java.awt.event.* ;
import java.awt.geom.Rectangle2D;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;

/**
 * Klasa sluzaca do losowania
 * @author Michal
 */
class Losowanie
{
    Random r = new Random();
    public int losujint(int k)
    {
        return r.nextInt(k);
    }
}

/**
 * Klasa przeznaczona do zamykania prugramu
 * @author Tomasz
 */
class ProgramWindowAdapter extends WindowAdapter 
{
  public void windowClosing(WindowEvent e) 
  { 
      System.exit(0); 
      
  }
}

/**
 * Klasa sluzaca do pracy w panelu
 * @author Tomasz
 */
class MojPanel extends JPanel
{
    public int szerokosc,wysokosc,liczba;
    private List<Wilk> wilki;
    private List<Zajac> zajace;
    /**
     * Konstruktor MojPanel zapsujacy potrzebne do rysowania dane
     * @param w wysokosc
     * @param sz szerokosc
     * @param wilki tabela wilkow
     * @param zajace tabela zajecy 
     * @param liczba liczba zajecy
     */
    MojPanel(int w, int sz, List<Wilk>  wilki, List<Zajac> zajace, int liczba)
    {
        this.szerokosc=sz;
        this.wysokosc=w;
        this.wilki=wilki;
        this.zajace=zajace;
        this.liczba=liczba;
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        rob(g);
    }
    /**
     * Metoda rysujaca plansze
     * @param g Grafika
     */
    private void rob (Graphics g)
    {
        for (int i=0;i<szerokosc;i++)
        {
            for (int j=0;j<wysokosc;j++)
            {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(0,100,0));
                Rectangle2D rr = new Rectangle2D.Double();
                rr.setFrame(i*30, j*30, 30, 30);
                g2d.fill(rr);
                
            }
        }
        for (int i=0;i<liczba;i++)
        {
            if (zajace.get(i).dzialanie==true)
            {
                int xx=zajace.get(i).x;
                int yy=zajace.get(i).y;
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(250,250,250));
                Rectangle2D rr = new Rectangle2D.Double();
                rr.setFrame(xx*30+1, yy*30+1, 28, 28);
                g2d.fill(rr);
            }
        }
        
        int xx = wilki.get(0).x;
        int yy = wilki.get(0).y;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(0,0,0));
        Rectangle2D rr = new Rectangle2D.Double();
        rr.setFrame(xx*30+1, yy*30+1, 28, 28);
        g2d.fill(rr);
        
    }
}
/**
 * Klasa synchronizujaca watki
 * @author Tomasz
 */
class Wspolny
{
    private int czas;
    public Wspolny(int czas)
    {
        this.czas=czas;
    }
    /**
     * Metoda synchronizujaca dla zajaca
     * @param z objekt zajac
     */
    public synchronized void ruszzajac(Zajac z)       
    {
        int popx=z.x;
        int popy=z.y;
        Losowanie ll = new Losowanie();
        
        Random r = new Random();
        int rx,ry;
        Wilk wil=z.wilki.get(0);

        if (wil.y>z.y)
            z.y--;
        else if (wil.y<z.y)
            z.y++;

        if (wil.x>z.x)
            z.x--;
        else if (wil.x<z.x)
            z.x++;


        while (z.y>z.moj.wysokosc-1 || z.x>z.moj.szerokosc-1 || z.x<0 || z.y<0)
        {
            rx=ll.losujint(3)-1;
            ry=ll.losujint(3)-1;
            z.x=popx+rx;
            z.y=popy+ry;               
        }

        for (int j=0;j<z.zajace.size();j++)
        {
            if (z.zajace.get(j).x==z.x && z.zajace.get(j).y==z.y && j!=z.numer && z.zajace.get(j).dzialanie==true)
            {
                z.y=popy;
                z.x=popx;
            }
        }

        if (wil.x==z.x && wil.y==z.y)
        {
            z.y=popy;
            z.x=popx;
        }

        z.moj.repaint();
        try { wait(czas); } catch(InterruptedException e) {};
    }
    /**
     * Metoda synchronizujaca dla wilka
     * @param w obiekt wilk
     */
    public synchronized void ruszwilk(Wilk w)
    {
        int najm = w.moj.szerokosc*w.moj.szerokosc+w.moj.wysokosc*w.moj.wysokosc+1;
        int najblizszy=w.zajace.size()+1;
        for (int i=0;i<w.zajace.size();i++)
        {
            if (najm>((w.zajace.get(i).x - w.x)*(w.zajace.get(i).x - w.x)+(w.zajace.get(i).y - w.y)*(w.zajace.get(i).y - w.y)) && w.zajace.get(i).dzialanie==true)
            {
                najblizszy=i;
                najm=(w.zajace.get(i).x - w.x)*(w.zajace.get(i).x - w.x)+(w.zajace.get(i).y - w.y)*(w.zajace.get(i).y - w.y);
            }
        }

        Zajac zaj= w.zajace.get(najblizszy);

        if (zaj.y>w.y)
            w.y++;
        else if (zaj.y<w.y)
            w.y--;

        if (zaj.x>w.x)
            w.x++;
        else if (zaj.x<w.x)
            w.x--;

        for (int i=0;i<w.zajace.size();i++)
        {
            if (w.zajace.get(i).x==w.x && w.zajace.get(i).y==w.y && w.zajace.get(i).dzialanie==true)
            {
                w.zajace.get(i).dzialanie=false;
                w.razy=5;
            }
        }

        w.moj.repaint();
        try { wait(czas*w.razy); } catch(InterruptedException e) {};
        w.razy=1;
    }
}
/**
 * Klasa tworzaca watek wilka
 * @author Tomasz
 */
class Wilk extends Thread 
{
    public int x,y;
    public MojPanel moj;
    public List<Zajac> zajace;
    public int razy=1;
    private Wspolny wsp;
   /**
    * Konstruktor zapisujacy wszystkie potrzebne dane dla obiektu Wilk
    * @param x parametr x wilka
    * @param y parametr y wilka
    * @param moj obiekt MojPanel
    * @param zajace tabela zajecy
    * @param wsp obiekt wspolny
    */
    public Wilk( int x, int y, MojPanel moj, List<Zajac> zajace, Wspolny wsp) 
    {
        this.x = x;
        this.y = y;
        this.moj = moj;
        this.zajace=zajace;
        this.wsp=wsp;
    }
    /**
     * Metoda ruszania dla watku wilka
     */
    public synchronized void run() 
    {
        while(true) 
        {  
            wsp.ruszwilk(this);
            //Thread.yield();
            
        }
    } 
}
/**
 * Klasa tworzaca watek zajaca
 * @author Tomasz
 */
class Zajac extends Thread 
{
    public int x,y;
    public MojPanel moj;
    public List<Wilk> wilki;
    public List<Zajac> zajace;
    boolean dzialanie=true;
    int numer;
    private Wspolny wsp;
    /**
     * Konstruktor zapisujacy wszystkie potrzebne dane dla obiektu Zajac
     * @param x parametr x wilka
     * @param y parametr y wilka
     * @param moj obiekt MojPanel
     * @param wilki tabela wilkow
     * @param zajace tabela zajecy
     * @param numer inumer zajaca
     * @param wsp Obiekt wspolny
     */
    public Zajac(int x , int y, MojPanel moj, List<Wilk> wilki, List<Zajac> zajace, int numer, Wspolny wsp) 
    {
        this.x = x;
        this.y = y;
        this.moj = moj;
        this.wilki=wilki;
        this.zajace=zajace;
        this.numer=numer;
        this.wsp=wsp;
    }
    /**
     * Metoda ruszania dla watku zajaca
     */
    public synchronized void run() 
    {
        while(dzialanie==true)    
        { 
            wsp.ruszzajac(this);
            //Thread.yield();
 
        }
    }
}
/**
 * Klasa glowna 
 * @author Tomasz
 */
public class JProgram 
{
    /**
     * Metoda main tworzaca watki, parametry oraz frame 
     * @param args parametr args
     */
    public static void main(String [] args) 
    {
        int x,y;
        int wysokosc=20;
        int szerokosc=20;
        int liczba=3;
        int czas=200;
        Losowanie ll = new Losowanie();
        
        Random r = new Random();
        List<Zajac> Zajace = new ArrayList<Zajac>();
        List<Wilk> Wilki = new ArrayList<Wilk>();
        MojPanel mojp = new MojPanel(wysokosc,szerokosc,Wilki,Zajace,liczba);
        
        x=ll.losujint(szerokosc);
        y=ll.losujint(wysokosc);
        int pczas=czas/2;
        czas=ll.losujint(czas)+pczas;
        Wspolny wsp = new Wspolny(czas);
        
        Wilk w1 = new Wilk(x,y,mojp,Zajace,wsp);
        Wilki.add(w1);
        for (int i=0;i<liczba;i++)
        {
            x=ll.losujint(szerokosc);
            y=ll.losujint(wysokosc);
            Zajac z = new Zajac(x,y,mojp,Wilki,Zajace,i,wsp);
            Zajace.add(z);
        }
         
        for (int i=0;i<liczba;i++)
        {
            Zajace.get(i).start();
        }
        
        w1.start();
        JFrame f =new JFrame();
        f.add(mojp);
        f.setSize(szerokosc*40,wysokosc*40);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
