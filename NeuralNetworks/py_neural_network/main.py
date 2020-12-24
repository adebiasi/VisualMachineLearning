# python notebook for Make Your Own Neural Network
# code for a 3-layer neural network, and code for learning the MNIST dataset
# (c) Tariq Rashid, 2016
# license is GPLv2
import dataLoader
from py_neural_network import NeuralNetwork
import matplotlib.pyplot
import numpy
from dataLoader import DataLoader
from neural_network_module import NeuralNetworkModule

n_n_module = NeuralNetworkModule()
n_n_module.train()
#n_n_module.test_mnist_data();
n_n_module.test_my_own_png()

n_n_module.backquery()




