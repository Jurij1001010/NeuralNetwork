

public class Library {
    public static double[] addDouble(double[] arr, double x)
    {
        int i;
        int n = arr.length;
        // create a new array of size n+1
        double[] newarr = new double[n + 1];

        // insert the elements from
        // the old array into the new array
        // insert all elements till n
        // then insert x at n+1
        if (n != 0) {
            for (i = 0; i < n; i++)
                newarr[i] = arr[i];
        }

        newarr[n] = x;

        return newarr;
    }

    public static double[][] addArray(double[][] arr, double[] x)
    {
        int i;
        int n = arr.length;
        // create a new array of size n+1
        double[][] newarr = new double[n + 1][];

        // insert the elements from
        // the old array into the new array
        // insert all elements till n
        // then insert x at n+1
        if (n != 0) {
            for (i = 0; i < n; i++)
                newarr[i] = arr[i];
        }

        newarr[n] = x;

        return newarr;
    }

    public static Layer[] addLayer(Layer[] arr, Layer x)
    {
        int i;
        int n = arr.length;
        // create a new array of size n+1
        Layer[] newarr = new Layer[n + 1];

        // insert the elements from
        // the old array into the new array
        // insert all elements till n
        // then insert x at n+1
        if (n != 0) {
            for (i = 0; i < n; i++)
                newarr[i] = arr[i];
        }

        newarr[n] = x;

        return newarr;
    }

    public static void print2DoubleArray(double[][] array){

        for (double [] array1 : array){
            for (double variable : array1){
                System.out.print(variable+", ");

            }
            System.out.println();
        }
    }

    public static void print1DoubleArray(double[] array){

        System.out.print("[");
        for (double a: array){
            System.out.print(a+", ");
        }
        System.out.println("]");
    }
}
