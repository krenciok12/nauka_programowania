/** @author Tomasz Krent **/

#include<iostream>
#include <cstdlib>
#include<sstream>

using namespace std;

class WierszTrojkataPascala
{
    private:
        int  *a;

    public:

        WierszTrojkataPascala(int n) throw(string)
        {

            if (n<0 || n>33)
                throw (string) "bledna liczba";



            a = new int[n+1];

            for (int i=0;i<=n;i++)
            {
                a[i]=0;
            }

            for (int i=0;i<=n;i++)
            {
                a[i]=1;

                int j=n;
                while(a[j]==0)
                {
                    j--;
                }
                j--;
                while (j>0)
                {
                    a[j]=a[j]+a[j-1];
                    j--;
                }
            }
        }

        ~WierszTrojkataPascala()
        {
            delete[] a;
        }

        int wspolczynnik(int m, int n) throw(string)
        {
            if (m<0 || m>n)
                throw (string) "liczba spoza zakresu";
            return a[m];
        }
};
int main(int argc, char *argv[])
{
    if (argc==1)
    {
        cout<<"brak danych";
        exit(0);
    }

    int n,m,i=1;
    //n=atoi(argv[i]);

    istringstream isstream(argv[i]);
    isstream>>n;

    if (isstream.fail())
    {
        cout<<argv[i]<<" - nie jest to liczba calkowita "<<endl;

    }
    else
    {


            try
            {
                WierszTrojkataPascala * wiersz = new WierszTrojkataPascala(n);
                for (int j=2;j<argc;j++)
                {

                    istringstream isstream(argv[j]);
                    isstream>>m;
                    if (isstream.fail())
                    {
                        cout<<argv[j]<<" - nie jest to liczba ca³kowita"<<endl;
                    }
                    else
                    {
                        try
                        {
                            cout<<m<<" - "<<wiersz->wspolczynnik(m,n)<<"\n";
                        }
                        catch (string w)
                        {
                            cout<<m<<" - "<<w<<endl;
                        }
                    }
                }

            }
            catch (string w)
            {
                cout<<n<<" - "<<w<<endl;
            }

    }

    return 0;
}
