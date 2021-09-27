public class WierszTrojkataPascala
{
    private int n;
    private int[] a;// = new int[n+1];

    WierszTrojkataPascala(int n)
    {
        int[] a = new int[n+1];
        for (int i=0;i<=n;i++)
        {
            a[i]=0;
        }

        for (int i=0;i<=n;i++)
        {
            a[i]=1;

            int j=i-1;
            
            while (j>0)
            {
                a[j]=a[j]+a[j-1];
                j--;
            }
        }
        this.a = a;
    }

    public int wspolczynnik(int m)
    {
        int[] a = new int[n+1];
        a=this.a;
        return a[m];
    }
}

