import java.net.ProtocolFamily;
import java.util.Random;

public class Layer {
    public Integer position; // 0 = input and hidden layer, 1 = output layer
    public Integer neuronNumber; //how many neurons are in this layer
    public Integer neuronNextNumber; //how many neurons are in next layer if there are 0 it means it is output layer

    public double[] neuronValues = {}; //values of neurons in this layer
    public double[] neuronDeltas = {};
    public double[] neuronNextValues = {}; //calculated values of neurons for next layer

    public double[][] weights = {};
    public double[] biases = {};

    private final Random rand = new Random();



    public Layer(int neuronNumber, int neuronNextNumber){
        this.neuronNumber = neuronNumber;
        this.neuronNextNumber = neuronNextNumber;

        if (neuronNextNumber == 0){
            this.position = 1;
        }else {
            this.position = 0;
            setNewWeights();
            setNewBiases();
        }
    }



    public double ActivationFunction(double input){
        return 1/(1+Math.exp(-input));
    }

    public double[] calculateNextNeurons(){
        //System.out.println(position);
        for (int i = 0; i < neuronNextNumber; i++){
            double nextNeuron = 0;

            for (int j = 0; j < neuronNumber; j++){
                //System.out.println(j);
                nextNeuron = nextNeuron + neuronValues[j]*weights[j][i];
                //System.out.println(nextNeuron);
            }
            nextNeuron = nextNeuron + biases[i];


            if (neuronNextNumber != neuronNextValues.length){
                neuronNextValues = Library.addDouble(neuronNextValues, ActivationFunction(nextNeuron));
            }
            else{
                neuronNextValues[i] = ActivationFunction(nextNeuron);
            }

        }
        return neuronNextValues;
    }



    public void setNeurons(double[] neuronArray) {
        this.neuronValues = neuronArray;
    }
    public void setNewWeights(){

        for (int i = 0; i < neuronNumber; i++) {
            double[] list1 = {};
            for (int j = 0; j < neuronNextNumber; j++){
                //double randomNum = rand.nextDouble();
                //System.out.println(randomNum);
                double randomNum = -0.05 + (0.05 + 0.05) * rand.nextDouble();
                list1 = Library.addDouble(list1, randomNum);
            }
            weights = Library.addArray(weights, list1);
        }
    }
    public void setNewBiases(){
        for (int i = 0; i < neuronNextNumber; i++) {
            double randomNum = -1 + (1 + 1) * rand.nextDouble();
            biases = Library.addDouble(biases, randomNum);
        }
    }
    public void setWeights(double[][] weights){
        this.weights = weights;
    }
    public void setBiases(double[] biases){
        this.biases = biases;
    }
    public void setNeuronDeltas(double[] neuronDeltas){
        this.neuronDeltas = neuronDeltas;
    }


    public double[] getCost(double[] correctNeuronValues){
        double[] cost = {};

        //System.out.println(neuronValues.length);

        //Library.print1DoubleArray(neuronValues);
        //Library.print1DoubleArray(correctNeuronValues);

        for (int i = 0; i < neuronNumber; i++){
            double neuronValue = neuronValues[i];
            double correctNeuronValue = correctNeuronValues[i];

            cost = Library.addDouble(cost, -(correctNeuronValue-neuronValue)*(neuronValue*(1-neuronValue)));//Math.pow(correctNeuronValue-neuronValue, 2)/(neuronValues.length-1));
        }
        //Library.print1DoubleArray(cost);
        return cost;
    }
    public double[] getCostHiddenLayer(double[] previousDeltas){
        double[] costs = {};


        for(int i = 0; i < neuronNumber; i++){

            double a = 0;
            for(int j = 0; j < neuronNextNumber; j++){
                a += weights[i][j]*previousDeltas[j];
            }
            costs = Library.addDouble(costs, a*(neuronValues[i] *(1-neuronValues[i])));
        }
        //Library.print1DoubleArray(costs);
        return costs;
    }
    public Integer getPosition() {
        return position;
    }
    public double[][] getWeights() {
        return weights;
    }
    public double[] getBiases() {
        return biases;
    }
}
