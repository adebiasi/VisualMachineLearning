class DataLoader:

    @staticmethod
    def loadData():
        data_file = open("../datasets/mnist/mnist_train_100.csv", 'r')
        data_list = data_file.readlines()
        data_file.close()
        return data_list;
