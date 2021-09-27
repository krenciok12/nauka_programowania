class MojException extends Exception{};

public class TrojkatPascala
{
    private int n;
    private int[][] a;// = new int[n+1];

    TrojkatPascala(int n) throws MojException
    {
        if (n<0 || n>33)
            throw new MojException();
        
        int[][] a = new int[n+1][n+1];
        for (int ii=n;ii>=0;ii--)
        {
            for (int i=0;i<=ii;i++)
            {
                a[i][ii]=0;
            }

            for (int i=0;i<=ii;i++)
            {
                a[i][ii]=1;

                int j=i-1;

                while (j>0)
                {
                    a[j][ii]=a[j][ii]+a[j-1][ii];
                    j--;
                }
            }
        }
        this.a = a;
    }

    public int wspolczynnik(int m,int k)
    {
        int[][] a = new int[n+1][n+1];
        a=this.a;
        return a[m][k];
    }
}

