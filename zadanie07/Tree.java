/**
 * Klasa sluzaca do tworzenia galezi
 * @author Tomasz
 * @param <T> rodzaj drzewa
 */
class TreeElem<T extends Comparable<T>> 
{
    T elem;
    TreeElem<T> left;
    TreeElem<T> right;
    /**
     * Konstruktor tworzacy galaz z elementem elem
     * @param elem element nalezacy do liscia
     */
    TreeElem(T elem)
    {
        this.elem = elem;
        left = null;
        right = null;
    }
    /**
     * funkcja zwracajaca elem w postaci stringa
     * @return zwracajcy string
     */
    public String toString() 
    { 
        return elem.toString(); 
    }
}
/**
 * Klasa sluzaca do tworzenia drzewa i pracy nad nim
 * @author Tomasz
 * @param <T> rodzaj drzewa
 */
class Tree<T extends Comparable<T>> 
{
    private TreeElem<T> root;
    /**
     * Konstruktor tworzacy drzewo
     */
    public Tree() 
    { 
        root = null; 
    }
    /**
     * Funkcja ktora dodaje liscie do drzewa o elemencie elem
     * @param elem dodawany element
     */
    public void insert(T elem) 
    {
        if (!isElement(elem))
            root = ins(elem, root); 
    }
    /**
     * Funkcja zwracajaca wyglad calego drzewa wraz z jego nowym elementem
     * @param elem dodawany element
     * @param w lgalaz drzewa
     * @return zwracajacy wwyglad
     */
    private TreeElem<T> ins(T elem, TreeElem<T> w) 
    {
        if( w==null ) 
            return new TreeElem<T>(elem);
        if( elem.compareTo(w.elem)<0 ) 
            w.left = ins(elem, w.left);
        else if( elem.compareTo(w.elem)>0)
            w.right = ins(elem, w.right);
        return w;
    }
    /**
     * Funkcja ktora usuwa lisc z drzewa o elemencie elem
     * @param elem  usuwany element
     */
    public void delete(T elem)
    {
        if (isElement(elem))
            root = del(elem,root);
    }
    /**
     * Funkcja zwracajaca wyglad calego drzewa bez usunietego liscia o 
     * @param elem usuwany element
     * @param w galaz drzewa idaca od korzenia
     * @return zwracajacy wyglad
     */
    private TreeElem<T> del(T elem, TreeElem<T> w) 
    {
        if( elem.compareTo(w.elem)==0 )
        {
            System.out.println(w.elem);
            if (w.right==null)
            {            
                    w=w.left;
            }
            else if (w.right.left==null)
            {
                w.elem=w.right.elem;
                w.right=w.right.right;
            }
            else
            {
                TreeElem<T> k;
                k=delelem(w.right);
                w.elem=k.left.elem;
                k.left=k.left.right;

            }
        }
        else
        {
            if( elem.compareTo(w.elem)>0)
                w.right=del(elem, w.right);
            if( elem.compareTo(w.elem)<0) 
                w.left=del(elem, w.left);
        }
        return w;
    }
    /**
     * Dodatkowa funkcja znajdujaca nastepnik
     * @param w dalsza galaz drzewa
     * @return Zwraca nastepnik
     */
    private TreeElem<T> delelem(TreeElem<T> w) 
    {
        if( w.left.left==null ) 
        {
            return w;
        }
        else 
            return delelem(w.left);
    }
    /**
     * funkcja szukajaca liscia o elemencie elem
     * @param elem element do wyszukania
     * @return Czy jest element
     */
    public boolean search (T elem)
    {
        if (isElement(elem))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    /**
     * funkcja sprawdzajaca czy element elem znajduje sie w drzewie
     * @param elem element do wyszukania
     * @return Czy jest element
     */
    public boolean isElement(T elem) 
    { 
        return isElem(elem,root); 
    }
    /**
     * funkcja zwracajca prawde lub falsz, w razie pojawienia sie elementu elem w drzewie , czy tez nie
     * @param elem element do wyszukania
     * @param w galaz drzewa
     * @return Czy aby na pewno jest element
     */
    private boolean isElem(T elem, TreeElem<T> w) 
    {
        if( w==null ) 
            return false;
        if( elem.compareTo(w.elem)==0 ) 
            return true;
        if( elem.compareTo(w.elem)<0) 
            return isElem(elem, w.left);
        else
            return isElem(elem, w.right);
    }
    /**
     * funkcja zwracajaca drzewo w postaci stringa
     * @return wartosc drzewa w stringu
     */
    public String toString() 
    { 
        return toS(root); 
    }
    /**
     * funkcja zwracajaca wyglad drzewa
     * @param w galezie drzewa
     * @return wartosc drzewa w stringu
     */
    private String toS(TreeElem<T> w) 
    { 
        if( w!=null )
            return "("+w.elem+":"+toS(w.left)+":"+toS(w.right)+")";
        return "()";
    }
    /**
     * funkcja rysujaca drzewo
     * @param tr jakie drzewo
     * @return rysunek drzewa
     */
    public String draw(Tree<T> tr)
    {
        return tr.toString();
    }
}

