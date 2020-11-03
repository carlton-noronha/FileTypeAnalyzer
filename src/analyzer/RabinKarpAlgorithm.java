package analyzer;

public class RabinKarpAlgorithm implements StringMatcher{

    public final static int d = 256;
    public final static int q = 101;

    @Override
    public boolean isPatternFound(String txt, String pat) {

        /*
        This code is a copy from GeeksForGeeks all credits goes to them.
         */

        int M = pat.length();
        int N = txt.length();
        int i, j;
        int p = 0;
        int t = 0;
        int h = 1;

        for (i = 0; i < M-1; i++)
            h = (h*d)%q;


        for (i = 0; i < M; i++)
        {
            p = (d*p + pat.charAt(i))%q;
            t = (d*t + txt.charAt(i))%q;
        }

        for (i = 0; i <= N - M; i++)
        {


            if ( p == t )
            {
                for (j = 0; j < M; j++)
                {
                    if (txt.charAt(i+j) != pat.charAt(j))
                        break;
                }

                if (j == M)
                    return true;
            }


            if ( i < N-M )
            {
                t = (d*(t - txt.charAt(i)*h) + txt.charAt(i+M))%q;

                if (t < 0)
                    t = (t + q);
            }
        }

        return false;
    }
}
