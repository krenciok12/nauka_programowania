#include<iostream>
#include<string>

using namespace std;
template<typename T>
class ElemDrzewa
{

    public:
    T elem;
    ElemDrzewa<T>* lewy;
    ElemDrzewa<T>* prawy;
    ElemDrzewa<T>* rodzic;
    ElemDrzewa(T elem)
    {
        this->elem = elem;
        this->lewy = NULL;
        this->prawy = NULL;
    }


};
template<typename T>
class Drzewo
{
    private:
    ElemDrzewa<T>* korzen;

    public:
    Drzewo()
    {
        korzen = NULL;
    }

    void dodaj(T elem)
    {
        if (czyJest(elem)==false)
            korzen = dodajemy(elem,korzen);
    }
    ElemDrzewa<T>* dodajemy(T elem, ElemDrzewa<T>* w)
    {
        if (w==NULL)
        {
            ElemDrzewa<T> *el = new ElemDrzewa<T>(elem);
            return el;
        }
        if (elem < w->elem)
        {
            w->lewy = dodajemy(elem,w->lewy);
        }
        else if (elem >= w->elem)
        {
            w->prawy = dodajemy(elem,w->prawy);
        }
    }
    void usun(T elem)
    {
        if (czyJest(elem)==true)
            korzen = usuwamy(elem,korzen);
    }
    ElemDrzewa<T>* usuwamy(T elem, ElemDrzewa<T>* w)
    {
        if (elem == w->elem)
        {
            if (w->prawy==NULL)
            {
                w=w->lewy;
            }
            else if (w->prawy->lewy==NULL)
            {
                w->elem=w->prawy->elem;
                w->prawy=w->prawy->prawy;
            }
            else
            {
                ElemDrzewa<T>* el;
                T eleme;
                eleme=usuwamy2(w->prawy);
                w->elem=eleme;
            }
        }
        else
        {
            if (elem < w->elem)
            {
                w->lewy=usuwamy(elem,w->lewy);
            }
            else if (elem > w->elem)
            {
                w->prawy=usuwamy(elem,w->prawy);
            }
        }

    }
    T usuwamy2(ElemDrzewa<T>* w)
    {
        if(w->lewy->lewy==NULL)
        {
            T eleme;
            eleme=w->lewy->elem;
            w->lewy=w->lewy->prawy;
            return eleme;
        }
        else
        {
            return usuwamy2(w->lewy);
        }

    }
    bool czyJest(T elem)
    {
        return czyJestes(elem,korzen);
    }
    bool czyJestes(T elem, ElemDrzewa<T>* w)
    {
        if (w==NULL)
            return false;
        if (elem==w->elem)
            return true;
        if (elem < w->elem)
            return czyJestes(elem,w->lewy);
        else
            return czyJestes(elem,w->prawy);
    }
    void rysuj (Drzewo<T> d)
    {
        toS(korzen);
    }
    void toS(ElemDrzewa<T>* w)
    {
        if (w!=NULL)
        {

            cout<<"("<<w->elem<<":";
            toS(w->lewy);
            cout<<":";
            toS(w->prawy);
            cout<<")";
        }
        else
        {
            cout<<"()";
        }

    }
    void Szukaj(T elem)
    {
        if (czyJestes(elem,korzen))
            cout<<elem <<" znajduje sie\n";
        else
            cout<<elem <<" nie znajduje sie\n";
    }
    void usunDrzewo()
    {
        usunDrzewo2(korzen);
    }
    void usunDrzewo2(ElemDrzewa<T>* w)
    {
        if (w)
        {
            usunDrzewo2(w->lewy);
            usunDrzewo2(w->prawy);
            delete[] w;
        }
    }


};
int main(int argc, char* argv[])
{
    Drzewo<double> a;
    a.dodaj(8);
    a.dodaj(10);
    a.dodaj(14);
    a.dodaj(13);
    a.dodaj(3);
    a.dodaj(1);
    a.dodaj(6);
    a.dodaj(5);
    a.dodaj(7);
    a.dodaj(5.5);

    try
    {
        a.rysuj(a);
    }
    catch(string e)
    {
        cout << e << endl;
    }
    cout<<endl;
    a.usun(3);
    try
    {
        a.rysuj(a);
    }
    catch(string e)
    {
        cout << e << endl;
    }
    cout<<endl;
    a.usun(8);
    try
    {
        a.rysuj(a);
    }
    catch(string e)
    {
        cout << e << endl;
    }
    cout<<endl;
    a.Szukaj(3);
    a.Szukaj(7);
    a.usunDrzewo();

}

