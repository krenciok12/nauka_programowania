import java.awt.*;
import java.awt.event.*;



class MyWindowAdapter extends WindowAdapter
{
    public void windowClosing(WindowEvent e)
    {
        System.exit(0);
    }
}

class EndButtonAdapter implements ActionListener
{
    public void actionPerformed(ActionEvent e)
    {
        System.exit(0);
    }
}

class EndButton extends Button
{
    EndButton()
    {
        super("Koniec");
        addActionListener(new EndButtonAdapter());
    }
}

class MyButtonAdapter implements ActionListener
{
    Program p;
    MyButtonAdapter (Program p)
    {
        this.p=p;
    }
    public void actionPerformed(ActionEvent e)
    {
        p.action();
    }


}

class MyButton extends Button
{
    MyButton (Program p)
    {
        super ("TrojkatPascala");
        addActionListener(new MyButtonAdapter(p));
    }
}

class MyFrame extends Frame
{
    MyFrame(Program p)
    {
        super("Program");
        
        setBounds(100,100,600,600);
        
        EndButton koniec = new EndButton();
        MyButton akcja = new MyButton(p);
        addWindowListener(new MyWindowAdapter());
        
        setFont(new Font("Times New Roman" , Font.PLAIN,20));
        setLayout(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        
        p.wynik = new TextArea();
        p.dane = new TextField(10);
        p.t1 = new Label();
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        add (p.t1,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 0;
        add (p.dane,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 2;
        c.gridy = 0;
        add (akcja,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        add (p.wynik,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 2;
        c.gridy = 3;
        add(koniec,c);
        
        p.t1.setText("Wpisz liczbe:");
        pack();
        setResizable(false);
        
                
    }
}

public class Program
{
    MyFrame frame;
    TextArea wynik;
    TextField dane;
    Label t1;
    
    void action()
    {
        int j,a,i;
        String s="";
        try
        {
            a = Integer.parseInt(dane.getText());
            try
            {
                TrojkatPascala tr = new TrojkatPascala(a);

                for (i=0;i<=a;i++)
                {
                    for (j=0;j<=i;j++)
                    {
                        s=s+Integer.toString(tr.wspolczynnik(j,i))+" ";
                    }
                    s=s+"\n";
                }

                wynik.setText(s);
            }
            catch (MojException e)
            {
                wynik.setText("Liczba spoza zakresu");
            }
            
        }
        catch (NumberFormatException ex)
        {
            wynik.setText("nie jest to liczba ca³kowita");
        }
        
        dane.setText("");
        
        
    }
    
    public static void main(String[] args)
    {
        Program p = new Program();
        p.frame= new MyFrame(p);
        p.frame.setVisible(true);
    }
}
