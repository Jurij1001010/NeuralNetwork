import java.io.IOException;
import java.util.Random;

public class Main {

    private static final Network network = new Network(784, 1, 10);
    private static final MnistDataReader reader = new MnistDataReader();



    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");



        //<----------------------------LEARN---------------------------->
        MnistMatrix[] matrix_for_learning = readImages("/home/juris/Documents/CODING/AI/MNIST_LIST/data/train-images.idx3-ubyte", "/home/juris/Documents/CODING/AI/MNIST_LIST/data/train-labels.idx1-ubyte");

        learn(1, matrix_for_learning);



        //<----------------------------TEST---------------------------->

        MnistMatrix[] matrix_for_testing = readImages("/home/juris/Documents/CODING/AI/MNIST_LIST/data/t10k-images.idx3-ubyte", "/home/juris/Documents/CODING/AI/MNIST_LIST/data/t10k-labels.idx1-ubyte");
        test(matrix_for_testing);





    }

    public static void learn(int loops, MnistMatrix[] matrix){
        for (int j = 0; j < loops; j++){
            for (int i = 0;i < matrix.length; i++){
                //System.out.println(i);
                //System.out.println(matrix[i].getLabel());

                double[] input = getImageValues(matrix[i]);
                //double[] input = {0.05, 0.1};
                //double[] correctOutput = {0.01, 0.99};
                //double[] a = {};


                double[] a = network.learn(input, GetCorrectOutput(matrix[i].getLabel()));

                if (i == 0){
                    System.out.println("Learning...");
                } else if (i % 1000 == 0) {
                    System.out.println("Score of this "+i+" learning number: "+a[0]*100+", total score: "+a[1]*100);
                    //Library.print1DoubleArray(a);

                }
            }
        }
    }
    public static void test(MnistMatrix[] matrix){
        double[][] end = {{}};
        int correctGuesses = 0;
        int wrongGuesses = 0;
        for (int i = 0;i < matrix.length; i++){

            double[] input = getImageValues(matrix[i]);


            double[][] a = network.test(input, GetCorrectOutput(matrix[i].getLabel()), matrix[i].getLabel(), i);

            /*
            System.out.print("Inputing: ");
            //Library.print1DoubleArray(input);
            System.out.print("Correct output: ");
            Library.print1DoubleArray(GetCorrectOutput(matrix[i].getLabel()));
            System.out.print("Output: ");
            Library.print1DoubleArray(a);*/
            /*
            if (a[10] != a[11]){
                //Library.print1DoubleArray(a);
                wrongGuesses ++;
            }else{
                correctGuesses ++;
            }*/

            end = a;

        }
        //Library.print2DoubleArray(end);
        /*System.out.println("Correct guesses: "+correctGuesses);
        System.out.println("Wrong guesses: "+wrongGuesses);
        System.out.println("Total score: "+end[end.length-1]*100);*/

    }

    public static double ActivationFunction(double input){
        return 1/(1+Math.exp(-input));
    }
    public static double[] GetCorrectOutput(int correctNumber){

        double[] output = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};

        output[correctNumber] = 1.0;
        return output;
    }
    public static double[] getImageValues(MnistMatrix m){
        double[] input = {};
        for (int j = 0; j < 28; j++) {
            for (int k = 0; k < 28; k++) {
                int value = m.getValue(j, k);
                if (value != 0) {
                    input = Library.addDouble(input, ActivationFunction(((double) value / 255)+0.25));
                } else {
                    input = Library.addDouble(input, (value));
                }
            }
        }
        return input;
    }
    public static MnistMatrix[] readImages(String dataFilePath, String labelFilePath) throws IOException {
        MnistMatrix[] matrix;
        matrix = reader.readData(dataFilePath, labelFilePath);
        return matrix;
    }
}