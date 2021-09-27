import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
/**
 * klasa tworzaca i obslugujaca czesc stworzona dla klienta
 * @author Tomasz
 */
class SocketClient extends Frame implements ActionListener 
{
    JLabel name;
    JTextField namein;
    JTextArea output;
    JRadioButton r1,r2,r3;
    ButtonGroup bg;
    JButton button,button2,button3,button4;
    JTextField input;
    Socket socket = null;
    PrintWriter out = null;
    BufferedReader in = null;
    Font f;
/**
 * Konstruktor tworzacy miejsce pracy kienta
 */
    SocketClient() 
    {
        Font font = new Font("TimesRoman", Font.BOLD, 18);
        
        name = new JLabel();
        name.setText("Uzytkownik");
        name.setFont(font);
        
        namein = new JTextField();
        namein.setFont(font);
        
        input = new JTextField();
        input.setFont(font);
        
        output = new JTextArea();
        output.setFont(font);
        output.setBackground(Color.white);
        
        button = new JButton("Dopisz");
        button.addActionListener(this);
        button.setFont(font);
       
        button2 = new JButton("Usun");
        button2.addActionListener(this);
        button2.setFont(font);
        
        button3 = new JButton("Rysuj");
        button3.addActionListener(this);
        button3.setFont(font);
        
        button4 = new JButton("Szukaj");
        button4.addActionListener(this);
        button4.setFont(font);
        
        bg = new ButtonGroup();
        
        r1 = new JRadioButton("Integer");
        bg.add(r1);
        r1.setSelected(true);
        r1.addActionListener(this);
        r1.setFont(font);
        
        r2 = new JRadioButton("Double");
        bg.add(r2);
        r2.addActionListener(this);
        r2.setFont(font);
        
        r3 = new JRadioButton("String");
        bg.add(r3);
        r3.addActionListener(this);
        r3.setFont(font);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 2;
        c.gridx = 0;
        c.gridy = 0;
        add(name,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        add(namein,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 1;
        add(r1,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 2;
        c.gridy = 1;
        add(r2,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 3;
        c.gridy = 1;
        add(r3,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;
        add(input,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 2;
        add(button,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 2;
        c.gridy = 2;
        add(button2,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 3;
        c.gridy = 2;
        add(button3,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 4;
        c.gridy = 2;
        add(button4,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 3;
        c.gridheight = 4;
        c.gridwidth = 4;
        c.gridx = 0;
        c.gridy = 3;
        add(output,c);
        
    }
/**
 * funkcja do obslugi dla klienta ktora laczy klienta z serwerem 
 * @param event obsluga przyciskow
 */
    public void actionPerformed(ActionEvent event) 
    {
        if(event.getSource() == button) 
        {
            if (r1.isSelected())
                out.println(input.getText()+"\n"+namein.getText()+"\n"+"Dopisz"+"\n"+"Integer");
            if (r2.isSelected())
                out.println(input.getText()+"\n"+namein.getText()+"\n"+"Dopisz"+"\n"+"Double");
            if (r3.isSelected())
                out.println(input.getText()+"\n"+namein.getText()+"\n"+"Dopisz"+"\n"+"String");
            try 
            {
                output.setText(in.readLine());
            }
            catch (IOException e) 
            {
                System.out.println("Read failed"); System.exit(1);
            }
            input.setText("");
            input.requestFocus();
        }
        if(event.getSource() == button2) 
        {
            if (r1.isSelected())
                out.println(input.getText()+"\n"+namein.getText()+"\n"+"Usun"+"\n"+"Integer");
            if (r2.isSelected())
                out.println(input.getText()+"\n"+namein.getText()+"\n"+"Usun"+"\n"+"Double");
            if (r3.isSelected())
                out.println(input.getText()+"\n"+namein.getText()+"\n"+"Usun"+"\n"+"String");
            try 
            {
                output.setText(in.readLine());
            }
            catch (IOException e) 
            {
                System.out.println("Read failed"); System.exit(1);
            }
            input.setText("");
            input.requestFocus();
        }
        if(event.getSource() == button3) 
        {
            if (r1.isSelected())
                out.println(input.getText()+"\n"+namein.getText()+"\n"+"Rysuj"+"\n"+"Integer");
            if (r2.isSelected())
                out.println(input.getText()+"\n"+namein.getText()+"\n"+"Rysuj"+"\n"+"Double");
            if (r3.isSelected())
                out.println(input.getText()+"\n"+namein.getText()+"\n"+"Rysuj"+"\n"+"String");
            try 
            {
                output.setText(in.readLine());
            }
            catch (IOException e) 
            {
                System.out.println("Read failed"); System.exit(1);
            }
            input.setText("");
            input.requestFocus();
        }
        if(event.getSource() == button4) 
        {
            if (r1.isSelected())
                out.println(input.getText()+"\n"+namein.getText()+"\n"+"Szukaj"+"\n"+"Integer");
            if (r2.isSelected())
                out.println(input.getText()+"\n"+namein.getText()+"\n"+"Szukaj"+"\n"+"Double");
            if (r3.isSelected())
                out.println(input.getText()+"\n"+namein.getText()+"\n"+"Szukaj"+"\n"+"String");
            try 
            {
                output.setText(in.readLine());
            }
            catch (IOException e) 
            {
                System.out.println("Read failed"); System.exit(1);
            }
            input.setText("");
            input.requestFocus();
        }
    }
/**
 * funcja sprawdzajaca lacznosc z serwerem
 */
    public void listenSocket()
    {
        try 
        {
            socket = new Socket("localhost", 4444);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (UnknownHostException e) 
        {
             System.out.println("Unknown host: localhost"); System.exit(1);
        }
        catch    (IOException e) 
        {
            System.out.println("No I/O"); System.exit(1);
        }
    }
/**
 * funkcja main incjujaca miejsce pracy klienta
 */
    public static void main(String[] args){
        SocketClient frame = new SocketClient();
        frame.addWindowListener( new WindowAdapter() 
        {
            public void windowClosing(WindowEvent e) 
            {
                System.exit(0);
            }
        } );
        frame.pack();
        frame.setVisible(true);
        frame.listenSocket();
    }
}
