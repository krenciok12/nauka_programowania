#include<iostream>
#include<math.h>
#include<sstream>
#include<cstdlib>
#include<string.h>

using namespace std;

class Figura
{
public:
    virtual double Pole()=0;
    virtual double Obwod()=0;

};

class Okrag: public Figura
{
private:
    double pole;
    double obwod;

public:
    Okrag(int r) throw(string)
    {
        if (r<=0)
        {
            throw(string) "bledna dana";
        }
        pole=M_PI*r*r;
        obwod=M_PI*2*r;
    }

    ~Okrag()
    {

    }

    double Obwod()
    {
        return obwod;
    }

    double Pole()
    {
        return pole;
    }
};

class Pieciokat: public Figura
{
private:
    double pole;
    double obwod;

public:
    Pieciokat(int a) throw(string)
    {
        if (a<=0)
        {
            throw(string) "bledna dana";
        }
        pole=a*a*1/tan(36*M_PI/180)/4;
        obwod=5*a;
    }

    ~Pieciokat()
    {

    }

    double Obwod()
    {
        return obwod;
    }

    double Pole()
    {
        return pole;
    }
};

class Szesciokat: public Figura
{
private:
    double pole;
    double obwod;

public:
    Szesciokat(int a) throw(string)
    {
        if (a<=0)
        {
            throw(string) "bledna dana";
        }
        pole=6*a*a*sqrt(3)/4;
        obwod=6*a;
    }

    ~Szesciokat()
    {

    }

    double Obwod()
    {
        return obwod;
    }

    double Pole()
    {
        return pole;
    }
};

class Czworokat: public Figura
{
public:
    virtual void CzyCzworokat (int a,int b,int c,int d,int k) throw(string)
    {
        if (a<=0 || b<=0 || c<=0 || d<=0 || k<=0 || k>=360)
        {
            throw(string) "bledne dane";
        }
    }

};

class Prostokat: public Czworokat
{
private:
    double pole;
    double obwod;

public:
    Prostokat(int a,int b,int c,int d,int k) throw(string)
    {
        Czworokat::CzyCzworokat(a,b,c,d,k);
        double x=k*M_PI/180 ;
        pole=a*b*sin(x);
        obwod=a+b+c+d;
    }

    ~Prostokat()
    {

    }

    void CzyProstokat(int a,int b,int c,int d,int k) throw(string)
    {
        if (!(a==c && b==d && k==90))
        {
            throw (string) "To nie prostokat";
        }
    }

    double Obwod()
    {
        return obwod;
    }

    double Pole()
    {
        return pole;
    }
};

class Romb: public Czworokat
{
private:
    double pole;
    double obwod;

public:
    Romb(int a,int b,int c,int d,int k) throw(string)
    {
        Czworokat::CzyCzworokat(a,b,c,d,k);
        double x=k*M_PI/180;
        pole=a*b*sin(x);
        obwod=a+b+c+d;
    }

    ~Romb()
    {

    }

    void CzyRomb(int a,int b,int c,int d,int k) throw(string)
    {
        if (!(a==b && b==c && c==d && k>0 && k<180))
        {
            throw (string) "To nie romb";
        }
    }

    double Obwod()
    {
        return obwod;
    }

    double Pole()
    {
        return pole;
    }
};

class Kwadrat: public Czworokat
{
private:
    double pole;
    double obwod;

public:
    Kwadrat(int a,int b,int c,int d,int k) throw(string)
    {
        Czworokat::CzyCzworokat(a,b,c,d,k);
        double x=k*M_PI/180;
        pole=a*b*sin(x);
        obwod=a+b+c+d;
    }

    ~Kwadrat()
    {

    }

    void Czykwadrat(int a,int b,int c,int d,int k) throw(string)
    {
        if (!(a==b && b==c && c==d && k==90))
        {
            throw (string) "To nie kwadrat";
        }
    }

    double Obwod()
    {
        return obwod;
    }

    double Pole()
    {
        return pole;
    }
};

int main(int argc, char *argv[])
{
    if (argc==1)
    {
        cout<<"brak danych";
        exit(0);
    }


    char * tab1=argv[1];
    int j=2,r,a,b,c,d,k;


    int ilosccyfr=0;

    int iloscznakow=strlen(tab1);

    for (int i=0;i<iloscznakow;i++)
    {

        if (tab1[i]=='c')
        {
            ilosccyfr=ilosccyfr+5;

        }
        else if (tab1[i]=='s' || tab1[i]=='o' || tab1[i]=='p')
        {
            ilosccyfr=ilosccyfr+1;
        }
        else
        {
            cout<<"Wpisano zla litere ";
            exit(0);

        }
    }
    if (ilosccyfr!=argc-2)
    {
        cout<<"bledna ilosc cyfr";
        exit(0);

    }


    int ilf=0;
    Figura ** tabF = new Figura *[iloscznakow];

    for (int i=0;i<iloscznakow;i++)
    {
        if (tab1[i]=='c')
        {
            istringstream isstream(argv[j]);
            isstream>>a;
            istringstream isstream2(argv[j+1]);
            isstream2>>b;
            istringstream isstream3(argv[j+2]);
            isstream3>>c;
            istringstream isstream4(argv[j+3]);
            isstream4>>d;
            istringstream isstream5(argv[j+4]);
            isstream5>>k;
            if (isstream.fail() || isstream2.fail() || isstream3.fail() || isstream4.fail() || isstream5.fail())
            {
                cout<<" nie wszystkie sa calkowita \n";
            }
            else
            {
                try
                {
                    tabF[ilf] = new Kwadrat(a,b,c,d,k);
                    try
                    {
                        Kwadrat * kwad = new Kwadrat(a,b,c,d,k);
                        kwad->Czykwadrat(a,b,c,d,k);
                        cout<<"Kwadrat o obwodzie: "<<tabF[ilf]->Obwod()<<" i polu: "<<tabF[ilf]->Pole()<<"\n";
                        ilf++;
                    }
                    catch(string w)
                    {
                        cout<<w<<"\n";


                        tabF[ilf] = new Prostokat(a,b,c,d,k);
                        try
                        {
                            Prostokat * pros = new Prostokat(a,b,c,d,k);
                            pros->CzyProstokat(a,b,c,d,k);
                            cout<<"Prostokat o obwodzie: "<<tabF[ilf]->Obwod()<<" i polu: "<<tabF[ilf]->Pole()<<"\n";
                            ilf++;
                        }
                        catch(string w)
                        {
                            cout<<w<<"\n";
                        }

                        tabF[ilf] = new Romb(a,b,c,d,k);
                        try
                        {
                            Romb * rombek = new Romb(a,b,c,d,k);
                            rombek->CzyRomb(a,b,c,d,k);

                            cout<<"Romb o obwodzie: "<<tabF[ilf]->Obwod()<<" i polu: "<<tabF[ilf]->Pole()<<"\n";
                            ilf++;
                        }
                        catch(string w)
                        {
                            cout<<w<<"\n";
                        }

                    }

                }
                catch(string w)
                {
                    cout<<" - "<<w<<"\n";
                }
            }
            j=j+5;
        }
        if (tab1[i]=='o')
        {
            istringstream isstream(argv[j]);
            isstream>>r;
            if (isstream.fail())
            {
                cout<<argv[j]<<" nie jest calkowita \n";
            }
            else
            {
                try
                {

                    tabF[ilf] = new Okrag(r);
                    cout<<"Okrag o obwodzie: "<<tabF[ilf]->Obwod()<<" i polu: "<<tabF[ilf]->Pole()<<"\n";
                    ilf++;
                }
                catch(string w)
                {
                    cout<<r<<" - "<<w<<"\n";
                }
            }
            j++;
        }

        if (tab1[i]=='p')
        {
            istringstream isstream(argv[j]);
            isstream>>a;
            if (isstream.fail())
            {
                cout<<argv[j]<<" nie jest calkowita \n";
            }
            else
            {
                try
                {

                    tabF[ilf] = new Pieciokat(a);
                    cout<<"Pieciokat o obwodzie: "<<tabF[ilf]->Obwod()<<" i polu: "<<tabF[ilf]->Pole()<<"\n";
                    ilf++;
                }
                catch(string w)
                {
                    cout<<a<<" - "<<w<<"\n";
                }
            }
            j++;
        }

        if (tab1[i]=='s')
        {
            istringstream isstream(argv[j]);
            isstream>>a;
            if (isstream.fail())
            {
                cout<<argv[j]<<" nie jest calkowita \n";
            }
            else
            {
                try
                {

                    tabF[ilf] = new Szesciokat(a);
                    cout<<"Szesciokat o obwodzie: "<<tabF[ilf]->Obwod()<<" i polu: "<<tabF[ilf]->Pole()<<"\n";
                    ilf++;
                }
                catch(string w)
                {
                    cout<<a<<" - "<<w<<"\n";
                }
            }
            j++;
        }
    }
    for (int i=0; i<ilf;i++)
    {
        delete tabF[i];
    }


    return 0;
}

