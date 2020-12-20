# python notebook for Make Your Own Neural Network
# code for a 3-layer neural network, and code for learning the MNIST dataset
# (c) Tariq Rashid, 2016
# license is GPLv2
import numpy
# library for plotting arrays
import matplotlib.pyplot
from dataLoader import DataLoader
from py_neural_network import NeuralNetwork



class NeuralNetworkModule:
    # number of input, hidden and output nodes
    # number of input, hidden and output nodes
    input_nodes = 784
    hidden_nodes = 400
    output_nodes = 10

    # learning rate
    learning_rate = 0.5

    def __init__(self):
        # create instance of neural network
        self.n = NeuralNetwork(self.input_nodes, self.hidden_nodes, self.output_nodes, self.learning_rate)

    def train(self):
        training_data = DataLoader.load_nmist_train_data()
        self.__train(self.n, training_data)

    def test_mnist_data(self):
        # load the mnist test data CSV file into a list
        test_data_list = DataLoader.load_mnist_test_data()
        self.__test(self.n, test_data_list)

    def test_my_own_png(self):
        # load the mnist test data CSV file into a list
        test_data_list = DataLoader.load_my_data()
        self.__test_png(self.n, test_data_list)

    @staticmethod
    def __test_png(neural_network, our_own_dataset):
        # test the neural network with our own images

        for item in range(0, len(our_own_dataset)):
            # record to test
            #item = 2
            print(item)
            # plot image
            matplotlib.pyplot.imshow(our_own_dataset[item][1:].reshape(28, 28), cmap='Greys', interpolation='None')

            # correct answer is first value
            correct_label = our_own_dataset[item][0]
            # data is remaining values
            inputs = our_own_dataset[item][1:]

            # query the network
            outputs = neural_network.query(inputs)
            print(outputs)

            # the index of the highest value corresponds to the label
            label = numpy.argmax(outputs)
            print("network says ", label)
            # append correct or incorrect to list
            if label == correct_label:
                print("match!")
            else:
                print("no match!")
                pass

    @staticmethod
    def __train(neural_network, training_data_list):
        # train the neural network
        # epochs is the number of times the training data set is used for training
        epochs = 20
        for e in range(epochs):
            # go through all records in the training data set
            for record in training_data_list:
                # split the record by the ',' commas
                all_values = record.split(',')
                # scale and shift the inputs
                inputs = (numpy.asfarray(all_values[1:]) / 255.0 * 0.99) + 0.01
                # create the target output values (all 0.01, except the desired label which is 0.99)
                targets = numpy.zeros(neural_network.o_nodes) + 0.01
                # all_values[0] is the target label for this record
                targets[int(all_values[0])] = 0.99
                neural_network.train(inputs, targets)
                pass

    @staticmethod
    def __test(neural_network, test_data_list):
        # test the neural network

        # scorecard for how well the network performs, initially empty
        scorecard = []
        # go through all the records in the test data set
        for record in test_data_list:
            # split the record by the ',' commas
            all_values = record.split(',')
            # correct answer is first value
            correct_label = int(all_values[0])
            # scale and shift the inputs
            inputs = (numpy.asfarray(all_values[1:]) / 255.0 * 0.99) + 0.01
            # query the network
            outputs = neural_network.query(inputs)
            # the index of the highest value corresponds to the label
            label = numpy.argmax(outputs)
            # append correct or incorrect to list
            if label == correct_label:
                # network's answer matches correct answer, add 1 to scorecard
                scorecard.append(1)
            else:
                print("guessed: " + str(label) + " but correct label is :" + str(correct_label))
                # network's answer doesn't match correct answer, add 0 to scorecard
                scorecard.append(0)
                pass
            pass

        # calculate the performance score, the fraction of correct answers
        scorecard_array = numpy.asarray(scorecard)
        print("performance = ", scorecard_array.sum() / scorecard_array.size)
