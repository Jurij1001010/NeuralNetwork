public class Network {
    public Layer inputLayer;
    public Layer outputLayer;


    public final int inputNeuronsNumber; //how many neurons are in input layer
    public final int outputNeuronsNumber; //how many neurons are in output layer
    public final int hiddenLayersNumber; //how many hidden layers are
    public final int hiddenNeuronsNumber1 = 64; //how many neurons are in hidden layer
    public final int hiddenNeuronsNumber2 = 256; //how many neurons are in hidden layer
    public final int hiddenNeuronsNumber3 = 128; //how many neurons are in hidden layer

    public Layer[] layers = {};

    public final double learnRate = 0.4;


    public double[] errors = {};
    public double testResult = 0;
    public int[] allNumberGuesses = new int[10];
    public int[] correctNumberGuesses = new int[10];
    public int[] numberCount = new int[10];

    public int number = 1;
    public Network(int inputNeuronsNumber, int hiddenLayersNumber, int outputNeuronsNumber){
        this.inputNeuronsNumber = inputNeuronsNumber;
        this.hiddenLayersNumber = hiddenLayersNumber;
        this.outputNeuronsNumber = outputNeuronsNumber;

        makeLayers();
        /*
        Library.print1DoubleArray(layers[0].neuronValues);
        Library.print2DoubleArray(layers[0].weights);

        System.out.println();

        Library.print1DoubleArray(layers[1].neuronValues);
        Library.print2DoubleArray(layers[1].weights);
        */
        inputLayer = layers[0];
        outputLayer = layers[layers.length-1];

    }



    public void makeLayers(){
        //if there is 0 hidden layers we only need to set up input and output layers
        //else we make n-hidden layers with n-neurons
        if (hiddenLayersNumber == 0){
            layers = Library.addLayer(layers, new Layer(inputNeuronsNumber, outputNeuronsNumber));
        }else{
            if (hiddenLayersNumber == 1) {
                layers = Library.addLayer(layers, new Layer(inputNeuronsNumber, hiddenNeuronsNumber1));
                layers = Library.addLayer(layers, new Layer(hiddenNeuronsNumber1, outputNeuronsNumber));
            }
            if (hiddenLayersNumber == 2){
                layers = Library.addLayer(layers, new Layer(inputNeuronsNumber, hiddenNeuronsNumber1));
                layers = Library.addLayer(layers, new Layer(hiddenNeuronsNumber1, hiddenNeuronsNumber2));
                layers = Library.addLayer(layers, new Layer(hiddenNeuronsNumber2, outputNeuronsNumber));
            }
            if (hiddenLayersNumber == 3){
                layers = Library.addLayer(layers, new Layer(inputNeuronsNumber, hiddenNeuronsNumber1));
                layers = Library.addLayer(layers, new Layer(hiddenNeuronsNumber1, hiddenNeuronsNumber2));
                layers = Library.addLayer(layers, new Layer(hiddenNeuronsNumber2, hiddenNeuronsNumber3));
                layers = Library.addLayer(layers, new Layer(hiddenNeuronsNumber3, outputNeuronsNumber));
            }
            if (hiddenLayersNumber > 3){
                layers = Library.addLayer(layers, new Layer(inputNeuronsNumber, hiddenNeuronsNumber1));
                layers = Library.addLayer(layers, new Layer(hiddenNeuronsNumber1, hiddenNeuronsNumber2));
                for (int i = 0; i < hiddenLayersNumber-2; i ++){
                    layers = Library.addLayer(layers, new Layer(hiddenNeuronsNumber2, hiddenNeuronsNumber2));
                }
                layers = Library.addLayer(layers, new Layer(hiddenNeuronsNumber2, outputNeuronsNumber));
            }
        }
        layers = Library.addLayer(layers, new Layer(outputNeuronsNumber, 0)); //set up output layer --> always has 0 next layers
    }



    public double[] learn(double[] inputNeuronValues, double[] correctNeuronValues){
        if (inputNeuronsNumber != inputNeuronValues.length){
            System.out.println("Invalid input of arguments!");
            return new double[] {0};
        }else{
            //forward-propagation
            inputLayer.setNeurons(inputNeuronValues);

            for (int i = 1; i < layers.length; i++){

                layers[i].setNeurons(layers[i-1].calculateNextNeurons());

            }
            //Library.print1DoubleArray(outputLayer.neuronValues);
            //layers[layers.length-1].setNeurons(layers[layers.length-1].calculateNextNeurons());
            //cost = outputLayer.getCost(correctNeuronValues);
            //costs = Library.addDouble(costs, cost);

            //back-propagation
            double[] returnList = {calculateError(layers[layers.length-1].neuronValues, correctNeuronValues), calculateSumError()};
            //Library.print1DoubleArray(returnNeurons);


            calculateNewWeights(correctNeuronValues);
            return returnList;
        }
    }

    public double[][] test(double[] inputNeuronValues, double[] correctNeuronValues, int correctNumber, int loopCount){
        if (inputNeuronsNumber != inputNeuronValues.length){
            System.out.println("Invalid input of arguments!");
            return new double[][] {{}};
        }else{
            //forward-propagation
            inputLayer.setNeurons(inputNeuronValues);

            for (int i = 1; i < layers.length; i++){

                layers[i].setNeurons(layers[i-1].calculateNextNeurons());

                //System.out.print("Neurons in "+i+" layer: ");
                //Library.print1DoubleArray(layers[i].neuronValues);


            }

            double[] returnNeurons = layers[layers.length-1].neuronValues;

            double correct = 0;
            double cost = 0;
            for (int i = 0; i < layers[layers.length-1].neuronNumber; i++ ){
                double neuronValue = layers[layers.length-1].neuronValues[i];
                double correctNeuronValue = correctNeuronValues[i];
                cost +=  Math.pow(neuronValue-correctNeuronValue, 2);

            }

            double bigest = 0;
            int guessedNum = 0;
            for (int i = 0; i < returnNeurons.length; i++){
                double returnNeuron = returnNeurons[i];
                if (returnNeuron > bigest){
                    bigest = returnNeuron;
                    guessedNum = i;
                }
            }

            allNumberGuesses[guessedNum] += 1;
            numberCount[correctNumber] += 1;
            if (guessedNum == correctNumber){
                testResult ++;
                correctNumberGuesses[correctNumber] += 1;
            }

            if (loopCount == 9999) {
                double[][] returnAnswer = new double[11][];
                System.out.println("|Number|" + "Num guessed corect|" + "Num guesed|" + "Score1|" + "Score2|");
                for (int i = 0; i < 10; i++) {
                    double[] array = {i, numberCount[i], correctNumberGuesses[i], allNumberGuesses[i], ((double) correctNumberGuesses[i] / numberCount[i]) * 100, ((double) correctNumberGuesses[i] / allNumberGuesses[i]) * 100};

                    Library.print1DoubleArray(array);
                    returnAnswer = Library.addArray(returnAnswer, array);
                }
                System.out.println("Total score: "+(testResult / (loopCount + 1))*100);
            }
            //Library.print1DoubleArray(returnNeurons);
            returnNeurons = Library.addDouble(returnNeurons, correctNumber);
            returnNeurons = Library.addDouble(returnNeurons, guessedNum);
            returnNeurons = Library.addDouble(returnNeurons, testResult/(loopCount+1));

            //returnAnswer = Library.addArray(returnAnswer, new double[]{testResult / (loopCount + 1)});
            return new double[2][3];
        }
    }




    public void calculateNewWeights(double[] correctNeuronValues){
        outputLayer.setNeuronDeltas(outputLayer.getCost(correctNeuronValues));

        for(int i = layers.length-2; i >= 0; i--){
            layers[i].setNeuronDeltas(layers[i].getCostHiddenLayer(layers[i+1].neuronDeltas));

            double[] neuronDeltas = layers[i].neuronDeltas;
            double[] neuronNextDeltas = layers[i+1].neuronDeltas;
            double[][] neuronWeights = layers[i].weights;
            double[] neuronValues = layers[i].neuronValues;
            double[] neuronNextValues = layers[i].neuronNextValues;


            //double[][] newWeights = {};

            for(int j = 0; j < neuronValues.length; j++){
                double neuronValue = neuronValues[j];
                //double [] newWeights1 = {};

                for (int k = 0; k < neuronNextValues.length; k++){
                    double neuronNextValue = neuronNextValues[k];
                    double neuronNextDelta = neuronNextDeltas[k];

                    neuronWeights[j][k] -= learnRate*(neuronNextDelta*neuronValue);
                    //newWeights1 = Library.addDouble(newWeights1, -learnRate*(neuronNextValue/neuronValue));


                }
                //newWeights = Library.addArray(newWeights, newWeights1);

            }
            layers[i].setWeights(neuronWeights);

            calculateNewBiases(i);
        }

    }
    public void calculateNewBiases(int i){

        double[] neuronBiases = layers[i].biases;
        double[] neuronNextValues = layers[i].neuronNextValues;
        double[] neuronNextDeltas = layers[i+1].neuronDeltas;

        //double[][] newWeights = {};

        for(int j = 0; j < neuronNextValues.length-1; j++){
            neuronBiases[j] -= learnRate*neuronNextDeltas[j];

        }
        layers[i].setBiases(neuronBiases);
        //Library.print1DoubleArray(layers[i].biases);


    }
    public double calculateError(double[] output, double[] correctOutput){
        double error = 0;
        for (int i = 0; i < output.length; i++){
            error += Math.pow(correctOutput[i]-output[i], 2);

        }
        errors = Library.addDouble(errors, error);
        return error / output.length;
    }
    public double calculateSumError(){
        double errorSum = 0;
        for(double error : errors){
               errorSum += error;
        }
        return  errorSum/errors.length;

    }


}
