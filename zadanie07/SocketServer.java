import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Klasa tworzaca Uzytkownika
 * @author Tomasz 
 */
class User
{
    String name;
    Tree<Integer> I;
    Tree<String> S;
    Tree<Double> D;
    User(String s)
    {
        Tree<String> i =new Tree<String>();
        this.I = new Tree<Integer>();
        this.S = i;
        this.D = new Tree<Double>();
        this.name = s;
    }
}
/**
 * Klasa obslugujaca serwer
 * @author Tomasz
 */
class SocketServer 
{
    
    List<User> users;
    ServerSocket server = null;
    Socket client = null;
    BufferedReader in = null;
    PrintWriter out = null;
    String line = "";
    String name = "";
    String butt = "";
    String tre = "";
/**
 * Konstruktor tworzacy serwer
 */
    SocketServer() 
    { 
        this.users = new ArrayList<User>();
        try 
        {
            server = new ServerSocket(4444); 
        } 
        catch (IOException e) 
        {
            System.out.println("Could not listen on port 4444"); System.exit(-1);
        }
    }
/**
 * funkcja obslugujaca klienta
 */
    public void listenSocket() 
    {
        try 
        {
            client = server.accept();
        } 
        catch (IOException e) 
        {
            System.out.println("Accept failed: 4444"); System.exit(-1);
        }
        try 
        {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
        } 
        catch (IOException e) 
        {
            System.out.println("Accept failed: 4444"); System.exit(-1);
        }
        while(line != null) 
        {
            try 
            {
                int i=0;
                line = in.readLine();
                name = in.readLine();
                butt = in.readLine();
                tre = in.readLine();
                for (i=0; i<users.size();i++)
                {
                    if (users.get(i).name.equals(name))
                    {
                        break;
                    }
                }
                if (i==users.size())
                {
                    User u = new User(name);
                    users.add(u);
                }
                
                if (butt.equals("Rysuj"))
                {
                    if (tre.equals("String"))
                        out.println(users.get(i).S.draw(users.get(i).S));
                    if (tre.equals("Integer"))
                        out.println(users.get(i).I.draw(users.get(i).I));
                    if (tre.equals("Double"))
                        out.println(users.get(i).D.draw(users.get(i).D));
                }
                else
                {
                    if (tre.equals("String"))
                    {
                        if (butt.equals("Dopisz"))
                            users.get(i).S.insert(line);
                        if (butt.equals("Usun"))
                            users.get(i).S.delete(line);
                        if (butt.equals("Dopisz") || butt.equals("Usun"))
                            out.println(users.get(i).S.draw(users.get(i).S));
                        if (butt.equals("Szukaj")) 
                        {
                            if (users.get(i).S.search(line))
                                out.println(line+" znajduje sie w drzewie "+tre); 
                            else
                                out.println(line+" nie znajduje sie w drzewie "+tre);
                        }
                    }

                    if (tre.equals("Integer"))
                    {
                        int n;
                        try
                        {
                            n=Integer.parseInt(line);
                            if (butt.equals("Dopisz"))
                                users.get(i).I.insert(n);
                            if (butt.equals("Usun"))
                                users.get(i).I.delete(n);
                            if (butt.equals("Dopisz") || butt.equals("Usun"))
                                out.println(users.get(i).I.draw(users.get(i).I));
                            if (butt.equals("Szukaj")) 
                            {
                                if (users.get(i).I.search(n))
                                    out.println(n+" znajduje sie w drzewie "+tre); 
                                else
                                    out.println(n+" nie znajduje sie w drzewie "+tre);
                            }
                        }
                        catch(NumberFormatException ex)
                        {
                            out.println("nie liczba calkowita");
                        }

                    }

                    if (tre.equals("Double"))
                    {
                        double m;
                        try
                        {
                            m=Double.parseDouble(line);
                            if (butt.equals("Dopisz"))
                                users.get(i).D.insert(m);
                            if (butt.equals("Usun"))
                                users.get(i).D.delete(m);
                            if (butt.equals("Dopisz") || butt.equals("Usun"))
                                out.println(users.get(i).D.draw(users.get(i).D));
                            if (butt.equals("Szukaj")) 
                            {
                                if (users.get(i).D.search(m))
                                    out.println(m+" znajduje sie w drzewie "+tre); 
                                else
                                    out.println(m+" nie znajduje sie w drzewie "+tre);
                            }
                        }
                        catch(NumberFormatException ex)
                        {
                            out.println("nie liczba");
                        }
                    }
                }
                
            } 
            catch (IOException e) 
            {
                System.out.println("Read failed"); System.exit(-1);
            } 
        }
    }
/**
 * funkcja konczaca prace serwera
 */
    protected void finalize() 
    {
        try 
        {
            in.close();
            out.close();
            client.close();
            server.close();
        } 
        catch (IOException e) 
        {
            System.out.println("Could not close."); System.exit(-1);
        }
    }
/**
 * funkcja main inicjujaca serwer
 */
    public static void main(String[] args) 
    {
        SocketServer server = new SocketServer();
        server.listenSocket();
    }
}
