import javax.swing.*;
import java.awt.* ;
import java.awt.event.* ;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.GeneralPath;

import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Klasa przeznaczona do zamykania prugramu
 * @author Tomasz
 */

class ProgramWindowAdapter extends WindowAdapter {
  public void windowClosing(WindowEvent e) 
  { 
      System.exit(0); 
      
  }
}

/**
 * Klasa sluzaca do pracy w panelu
 * @author Tomasz
 */
class Surface extends JPanel {
    
    private double pos;
    private int numerfigury=-1,numerfigury2=-1;
    static boolean czyPros=false,czyKolo=false,czyWielokat=false,czyZmienic=false,czyZapisz=false,czyWczytaj=false;
    
    boolean zmieniaj=false;
    Point p=null,p2=null,r=null,p3=null,r3=null,p4=null,p_popup=null;
    float wart;
    List<Point> Punkty2 = new ArrayList<Point>();

    List<Obiekt> Ob = new ArrayList<Obiekt>();
    
    public JPopupMenu popup;
    
    /**
     * Klasa oznaczajaca obiekt, ktora posiada w sobie figure, punkty tej figury i tabele koloru figury
     */
    
    public class Obiekt implements Serializable
    {
        private static final long serialVersionUID = 1L;
        private Shape figur;
        private List<Point> Punkty;
        private int tab[];
        
        Obiekt (Shape figur, List<Point> Punkty, int tab[])
        {
            this.figur=figur;
            this.Punkty=Punkty;
            this.tab=tab;
            
        }
        
    }
    
    /**
     * Metoda dzialajaca przez repaint()
     * @param g 
     */
    
    private void rob(Graphics g) 
    {
     
        popup = new JPopupMenu();
        ActionListener menuListener = new ActionListener() 
        {
            /**
             * Metoda sluzaca do zmiany koloru figury
             * @param event 
             */
          public void actionPerformed(ActionEvent event) 
          {
                int numerfigury=-1;
                    
                for (int i = Ob.size()-1;i>=0;i--)
                {
                    Shape fig = Ob.get(i).figur;
                    if (fig.contains(p_popup))
                    {
                        numerfigury=i;
                        break;
                    }
                }
                if (numerfigury!=-1)
                {
                    if (event.getActionCommand().equals("Kolor 1"))
                    {
                        int tab[] = {0,0,200};
                        Ob.get(numerfigury).tab=tab;
                        repaint();
                    }
                    if (event.getActionCommand().equals("Kolor 2"))
                    {
                        int tab[] = {200,0,0};
                        Ob.get(numerfigury).tab=tab;
                        repaint();
                    }
                    if (event.getActionCommand().equals("Kolor 3"))
                    {
                        int tab[] = {0,200,0};
                        Ob.get(numerfigury).tab=tab;
                        repaint();
                    }
                    numerfigury=-1;
                    repaint();
                }
          }
        };
        
        JMenuItem item;
        popup.add(item = new JMenuItem("Kolor 1"));
        item.setHorizontalTextPosition(JMenuItem.RIGHT);
        item.addActionListener(menuListener);
        
        popup.add(item = new JMenuItem("Kolor 2"));
        item.setHorizontalTextPosition(JMenuItem.RIGHT);
        item.addActionListener(menuListener);
        
        popup.add(item = new JMenuItem("Kolor 3"));
        item.setHorizontalTextPosition(JMenuItem.RIGHT);
        item.addActionListener(menuListener);
        

        addMouseListener(new MousePopupListener());
        
        for(Obiekt rrr: Ob) 
        {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(new Color(rrr.tab[0],rrr.tab[1],rrr.tab[2]));
            g2d.fill(rrr.figur);
            
            
        }
        if (czyZapisz==true)
        {
            Zapisywacz();
            czyZapisz=false;
            repaint();
        }
        
        if (czyWczytaj==true)
        {
            Wczytywacz();
            czyWczytaj=false;
            repaint();
        }
        
        
        if (czyZmienic==true && czyPros==false && czyKolo==false && czyWielokat==false)
        {
            Poruszajacy ma3 = new Poruszajacy();
            addMouseMotionListener(ma3);
            addMouseListener(ma3);
            addMouseWheelListener(new ZmRozmiar());
            
            if (p3!=null)
            {
                Ruszaj(g);
            }
            if (p4!=null)
            {
                ZmieniajRozmiar(g);
            }
            
        }
        if (czyZmienic==false)
        {
            MouseListener[] mouseListeners = getMouseListeners();
            for (MouseListener mouseListener : mouseListeners) 
            {
                removeMouseListener(mouseListener);
            }  
            
            MouseMotionListener[] mouseMotionListeners = getMouseMotionListeners();
            for (MouseMotionListener mouseMotionListener : mouseMotionListeners) 
            {
                removeMouseMotionListener(mouseMotionListener);
            } 
            czyZmienic=true;
            
        }
        
        if (czyPros==true)
        {
            
            StworzFigure1 ma = new StworzFigure1();
            addMouseMotionListener(ma);
            addMouseListener(ma);
            
            if (p!=null)
            {
                stworzProstokat(g);
            }
            
        }
        
        if (czyKolo==true)
        {
            StworzFigure1 ma = new StworzFigure1();
            addMouseMotionListener(ma);
            addMouseListener(ma);
            
            if (p!=null)
            {
                stworzOkrag(g);
            }
        }
        
        if (czyWielokat==true)
        {
            StworzFigure2 ma2 = new StworzFigure2();
            addMouseMotionListener(ma2);
            addMouseListener(ma2);
            
            if (p2!=null)
            {
                stworzWielokat(g);
            
            }
        }
        
        
        if(pos==-1)
        {
            czyKolo=false;
            czyPros=false;
            czyWielokat=false;
            czyZmienic=true;
            numerfigury=-1;
            numerfigury2=-1;
            p3=null;
            

            pos=1;
            
            MouseListener[] mouseListeners = getMouseListeners();
            for (MouseListener mouseListener : mouseListeners) 
            {
                removeMouseListener(mouseListener);
            }  
            
            MouseMotionListener[] mouseMotionListeners = getMouseMotionListeners();
            for (MouseMotionListener mouseMotionListener : mouseMotionListeners) 
            {
                removeMouseMotionListener(mouseMotionListener);
            } 
            repaint();
          
        }
    
    }
    /**
     * Metoda rysujaca
     * @param g 
     */
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        rob(g);
    }
    /**
     * Metoda sluzaca do poruszania figury
     * @param g 
     */
    private void Ruszaj(Graphics g)
    {
        
        
        if (numerfigury==-1)
            {
            for(int i = Ob.size(); i>0 ;i--) 
            {
                Shape Obecna=Ob.get(i-1).figur;
                if (Obecna.contains(r3))
                {
                    numerfigury=i-1;
                    break;
                }
            }
        }
        
        if (numerfigury!=-1)
        {
            List<Point> Punktypoprzednie = new ArrayList<Point>(0); 
            
            
            Punktypoprzednie=Ob.get(numerfigury).Punkty;
            
            for (int j=0; j<Punktypoprzednie.size();j++)
            {
                Point pomoc=new Point (0,0);
                
                pomoc.x=r3.x-p3.x;
                pomoc.y=r3.y-p3.y;

                Punktypoprzednie.get(j).x=Punktypoprzednie.get(j).x+pomoc.x;
                Punktypoprzednie.get(j).y=Punktypoprzednie.get(j).y+pomoc.y;

            }
            
            p3=r3;
            
            if (Punktypoprzednie.size()==2)
            {
          
                Ellipse2D rr = new Ellipse2D.Double();

                rr.setFrameFromDiagonal(Punktypoprzednie.get(0), Punktypoprzednie.get(1));
                Obiekt O=Ob.get(numerfigury);
                O.figur=rr;
            }
            if (Punktypoprzednie.size()>2)
            {
 
                GeneralPath wielokat = new GeneralPath();
                wielokat.moveTo(Punktypoprzednie.get(0).getX(), Punktypoprzednie.get(0).getY());
                for (int i = 1; i < Punktypoprzednie.size(); i++)
                {                  
                    wielokat.lineTo(Punktypoprzednie.get(i).getX(), Punktypoprzednie.get(i).getY());
                
                }
                wielokat.closePath();
                Obiekt O=Ob.get(numerfigury);
                O.figur=wielokat;
                
            }
        }
    }
    /**
     * Metoda sluzaca do zmiany rozmiaru figury
     * @param g 
     */
    
    private void ZmieniajRozmiar(Graphics g)
    {
        if (numerfigury2==-1)
        {
            for(int i = Ob.size(); i>0 ;i--) 
            {
                Shape Obecna2=Ob.get(i-1).figur;
                if (Obecna2.contains(p4))
                {
                    numerfigury2=i-1;
                    break;
                }
            }
        }
        if (numerfigury2!=-1 && zmieniaj==true && Ob.get(numerfigury2).figur.contains(p4))
        {
            List<Point> PunktyKolejne = new ArrayList<Point>(0); 
            
            PunktyKolejne=Ob.get(numerfigury2).Punkty;
            
            for (int j=0; j<PunktyKolejne.size();j++)
            {
                Point pomoc2=new Point (0,0);
                
                int w=(int)wart;
                pomoc2.x=p4.x-PunktyKolejne.get(j).x;
                pomoc2.y=p4.y-PunktyKolejne.get(j).y;
                
                PunktyKolejne.get(j).x=PunktyKolejne.get(j).x + pomoc2.x*w/10;
                PunktyKolejne.get(j).y=PunktyKolejne.get(j).y + pomoc2.y*w/10;
                
            }
            if (PunktyKolejne.size()==2)
            {

                Ellipse2D rr = new Ellipse2D.Double();

                rr.setFrameFromDiagonal(PunktyKolejne.get(0), PunktyKolejne.get(1));
                Obiekt O=Ob.get(numerfigury2);
                O.figur=rr;
                
            }
            if (PunktyKolejne.size()>2)
            {

                GeneralPath wielokat = new GeneralPath();
                wielokat.moveTo(PunktyKolejne.get(0).getX(), PunktyKolejne.get(0).getY());
                for (int i = 1; i < PunktyKolejne.size(); i++)
                {                  
                    wielokat.lineTo(PunktyKolejne.get(i).getX(), PunktyKolejne.get(i).getY());

                }
                wielokat.closePath();
                Obiekt O=Ob.get(numerfigury2);
                O.figur=wielokat;
                
            }
            numerfigury=-1;
            zmieniaj=false;  
        }
        
    }
    /**
     * Metoda sluzaca do stworzenia prostokata
     * @param g 
     */
    
    private void stworzProstokat(Graphics g)
    {
        ArrayList<Point> Punkty = new ArrayList<Point>();
        int tab[]= {0,0,0};
        Graphics2D g2d = (Graphics2D) g;
        Rectangle2D rr = new Rectangle2D.Double();
        rr.setFrameFromDiagonal(p, r);
        g2d.setColor(new Color(0,0,0));
        g2d.fill(rr);
        if (pos==-1)
        {
            Point pomoc1= new Point(0,0);
            Point pomoc2= new Point(0,0);
            pomoc1.x=p.x;
            pomoc2.x=r.x;
            pomoc1.y=r.y;
            pomoc2.y=p.y;


            Punkty.add(p);
            Punkty.add(pomoc1);
            Punkty.add(r);
            Punkty.add(pomoc2);

            Obiekt O =new Obiekt(rr,Punkty,tab);
            Ob.add(O);
            
        }
            
    
    }
    /**
     * Metoda sluzaca do stworzenia kola
     * @param g 
     */
    private void stworzOkrag(Graphics g)
    {
        int tab[]= {0,0,0};
        ArrayList<Point> Punkty = new ArrayList<Point>();
        Graphics2D g2d = (Graphics2D) g;
        Ellipse2D rr = new Ellipse2D.Double();
        if (Math.abs(p.x-r.x)>Math.abs(p.y-r.y))      
        {
           r.x=r.x-((p.y-r.y)-(p.x-r.x));
           r.y=r.y;
        }
        else
        {
            r.y=r.y-((p.x-r.x)-(p.y-r.y));
            r.x=r.x;
        }
        rr.setFrameFromDiagonal(p, r);
        g2d.setColor(new Color(0,0,0));
        g2d.fill(rr);
        if (pos==-1)
        {    
            Punkty.add(p);
            Punkty.add(r);


            Obiekt O =new Obiekt(rr,Punkty,tab);
            Ob.add(O);
        }
    
    }
    /**
     * Metoda sluzaca do stworzenia wielokata
     * @param g 
     */
    private void stworzWielokat(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(0,0,0));
        List<Point> Punkty = new ArrayList<Point>();
        int tab[]= {0,0,0};
        Punkty.addAll(Punkty2);
        GeneralPath wielokat = new GeneralPath();
        Punkty.add(p2);
        Punkty2.add(p2);
        Rectangle2D poc = new Rectangle2D.Double(Punkty.get(0).getX() - 16 / 2, Punkty.get(0).getY() - 16 /2, 16, 16);
        g2d.fill(poc);
        wielokat.moveTo(Punkty.get(0).getX(), Punkty.get(0).getY());
        
            
            for (int i = 0; i < Punkty.size(); i++)
            {
                double x = Punkty.get(i).getX() - 8 / 2;
                double y = Punkty.get(i).getY() - 8 / 2;

                Rectangle2D r1 = new Rectangle2D.Double(x, y, 8, 8);
                g2d.fill(r1);
                
                if (i>0 && i<Punkty.size()-1)
                {
                    wielokat.lineTo(Punkty.get(i).getX(), Punkty.get(i).getY());
                }
            
            }
            
            if (poc.contains(Punkty.get(Punkty.size()-1)) && Punkty.size()>3)
            {
                Punkty.remove(Punkty.size()-1);

                wielokat.closePath();

                g2d.dispose();
                Punkty2.clear();
                pos=-1;
                p2=null;

                repaint();
                
                Obiekt O =new Obiekt(wielokat,Punkty,tab);
                Ob.add(O);
            }
            if (Punkty.size()<=3 && Punkty.size()>1 && poc.contains(Punkty.get(Punkty.size()-1)))
            {
                wielokat.closePath();
                g2d.dispose();
                Punkty2.clear();
                pos=-1;
                repaint();
            }
    }
    /**
     * Klasa sluzaca do wyswietlania PopupMenu
     */
    class MousePopupListener extends MouseAdapter 
    {
        public void mousePressed(MouseEvent e) {

          p_popup=e.getPoint();
          checkPopup(e);
        }

        public void mouseClicked(MouseEvent e) {
          checkPopup(e);
        }

        public void mouseReleased(MouseEvent e) {
          checkPopup(e);
        }

        private void checkPopup(MouseEvent e) {
          if (e.isPopupTrigger()) {
            popup.show(Surface.this, e.getX(), e.getY());
          }
        }
    }
    
    /**
     * Klasa sluzaca do operacji myszka, ktora umozliwia stworzenie wielokata
     */
    class StworzFigure2 extends MouseAdapter
    {
        public void mousePressed(MouseEvent e2) {

            p2 = e2.getPoint();
            repaint(); 
        }
        
    }
    /**
     * Klasa sluzaca do operacji myszka, ktora umozliwia stworzenie prostokata i okregu
     */
    class StworzFigure1 extends MouseAdapter 
    {
        
        public void mousePressed(MouseEvent e) {
            
            p = e.getPoint();
            r = e.getPoint();
            
        }
        
        public void mouseDragged(MouseEvent e) {
            
            r = e.getPoint();
            repaint();
            
        }
        
        public void mouseReleased(MouseEvent e) {
            
            pos=-1;
            repaint();
            
        }
    
    }
    /**
     * Klasa sluzaca do operacji myszka, ktora umozliwia poruszanie figury
     */
    class Poruszajacy extends MouseAdapter 
    {
        
        public void mousePressed(MouseEvent e3) {
            
            p3 = e3.getPoint();
            r3 = e3.getPoint();
            repaint();
            
        }
        
        public void mouseDragged(MouseEvent e3) {
            
            r3 = e3.getPoint();
            repaint();
            
        }
        
        public void mouseReleased(MouseEvent e3) {
            
            r3 = e3.getPoint();
            pos=-1;
            repaint();
            
        }
    
    }
    /**
     * Klasa sluzaca do operacji myszka, ktora umozliwia zmiane rozmiaru figury
     */
    class ZmRozmiar implements MouseWheelListener
    {
        @Override
        public void mouseWheelMoved(MouseWheelEvent e4) {
            p4=e4.getPoint();
            if (e4.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL)
            {
                
                zmieniaj=true;
                wart=e4.getWheelRotation() * 1f;
                pos=-1;
                repaint();
                
            }
            
        }
    }
    /**
     * Metoda sluzaca do zapisywania figur do pliku
     */
    void Zapisywacz ()
    {
        try
        {
            FileOutputStream f = new FileOutputStream(new File("myObjects.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);
            for (Obiekt O: Ob)
            {
                o.writeObject(O);
            }
            
            o.close();
            f.close();
        }
        catch(FileNotFoundException e)
        {
            
        }
        catch(IOException e)
        {
            
        }
    }
    /**
     * Metoda sluzaca do wczytywania figur do pliku
     */
    void Wczytywacz()
    {
        try
        {
            FileInputStream fi = new FileInputStream(new File("myObjects.txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);
            
            Ob.clear();
            
            while(true)
            {       
                Obiekt O = (Obiekt) oi.readObject();
                Ob.add(O);
            }
            
            
            
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found");
        }
        catch(IOException e)
        {
            System.out.println("Error initializing stream");
        }
        catch (ClassNotFoundException e)
        {
        }
    }

}

/**
 * Klasa zawierajaca wszystkie dzialania, ktore beda mialy miejsce w Aplecie
 * @author Tomasz
 */

class JWszystko extends JPanel implements ActionListener
{
    JMenuBar myMenu;
    JMenu menu1, submenu;
    JMenuItem i1, i2, i3,wczytaj,zapisz;
    
  
    public JWszystko(JProgram p) 
    {
    
        myMenu = new JMenuBar();
        menu1 = new JMenu("Opcje");
        myMenu.add( menu1 );
        submenu = new JMenu("Rysuj");
        menu1.add( submenu );
        menu1.addSeparator();


        i1 = new JMenuItem("Rysuj kolo");
        i1.addActionListener(this);
        i2 = new JMenuItem("Rysuj prostokat");
        i2.addActionListener(this);
        i3 = new JMenuItem("Rysuj wielokat");
        i3.addActionListener(this);

        wczytaj = new JMenuItem("Wczytaj");
        wczytaj.addActionListener(this);

        zapisz = new JMenuItem("Zapisz");
        zapisz.addActionListener(this);


        submenu.add( i1 );
        submenu.add( i2 );
        submenu.add( i3 );

        menu1.add(wczytaj);
        menu1.add(zapisz);
        


        add(new Surface());
        setLayout(new GridLayout(1,1));
        p.setJMenuBar(myMenu);
        setSize(800,800);
    }
    /**
     * Metoda, ktora motywuje dzialnie od nacisniecia odpowiedniego hasla w menu
     * @param e 
     */
    public void actionPerformed(ActionEvent e) {
        
        if(e.getActionCommand().equals("Rysuj prostokat") )
        {
            Surface.czyPros=true;
            Surface.czyKolo=false;
            Surface.czyWielokat=false;
            Surface.czyZmienic=false;

        }
        if(e.getActionCommand().equals("Rysuj kolo") )
        {
            Surface.czyPros=false;
            Surface.czyKolo=true;
            Surface.czyWielokat=false;
            Surface.czyZmienic=false;

        }
        if(e.getActionCommand().equals("Rysuj wielokat") )
        {
            Surface.czyPros=false;
            Surface.czyKolo=false;
            Surface.czyWielokat=true;
            Surface.czyZmienic=false;
        }
        if(e.getActionCommand().equals("Wczytaj") )
        {
            Surface.czyWczytaj=true;
        }
        if(e.getActionCommand().equals("Zapisz") )
        {
            Surface.czyZapisz=true;
        }

    }
};

/**
 * Klasa, ktora ma dzialac jako applet
 * @author Tomasz
 */

public class JProgram extends JApplet
{
    
  /**
   * Metoda inicjujaca applet
   */
  public void init()
  {
      setLayout( new BorderLayout() );
      add( "Center", new JWszystko(this));
      
  }

  /**
   * Metoda, ktora pokazuje dzialanie programu w pliku jar
   * @param args 
   */
  public static void main(String[] args) {
    

        JFrame f = new JFrame();
        f.setSize(800,800);
        f.addWindowListener(new ProgramWindowAdapter());
        JProgram w = new JProgram();
        f.add(w);
        w.init();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        f.setLocationRelativeTo(null); 

        
        
        f.setVisible(true);  
  
  }
}

