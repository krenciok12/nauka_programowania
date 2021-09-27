import java.awt.*;
import java.awt.event.*;
import java.io.*;

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
        super ("Uruchom");
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
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        add (p.dane,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 0;
        add (akcja,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 1;
        add (p.wynik,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 2;
        add(koniec,c);
        
        
        pack();
        setResizable(false);
        
                
    }
    
}    
    public class Program
{
    MyFrame frame;
    TextArea wynik;
    TextField dane;
    
    void action()
    {
        try 
        {
            String s;
            s=dane.getText();
            
            Process process = Runtime.getRuntime().exec("WierszTrojkataPascala "+s);
           
 
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            
            String c="";
            String line;
            
            while ((line = reader.readLine()) != null) 
            {
                c=c+line+"\n";
            }
            
            wynik.setText(c);
 
        }       
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        
        
    }
    
    public static void main(String[] args)
    {
        Program p = new Program();
        p.frame= new MyFrame(p);
        p.frame.setVisible(true);
    }
}
