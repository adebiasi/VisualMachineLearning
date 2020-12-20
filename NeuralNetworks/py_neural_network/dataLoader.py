# helper to load data from PNG image files
import imageio
# glob helps select multiple files using patterns
import glob
import numpy


class DataLoader:

    @staticmethod
    def load_nmist_train_data():
        data_file = open("../datasets/mnist/mnist_train_100.csv", 'r')
        data_list = data_file.readlines()
        data_file.close()
        return data_list;

    @staticmethod
    def load_mnist_test_data():
        test_data_file = open("../datasets/mnist/mnist_test_10.csv", 'r')
        test_data_list = test_data_file.readlines()
        test_data_file.close()
        return test_data_list;

    @staticmethod
    def load_my_data():
        # our own image test data set
        our_own_dataset = []

        # load the png image data as test data set
        for image_file_name in glob.glob('../datasets/my_own_images/2828_my_own_?.png'):
            # use the filename to set the correct label
            label = int(image_file_name[-5:-4])

            # load image data from png files into an array
            print("loading ... ", image_file_name)
            img_array = imageio.imread(image_file_name, as_gray=True)

            # reshape from 28x28 to list of 784 values, invert values
            img_data = 255.0 - img_array.reshape(784)

            # then scale data to range from 0.01 to 1.0
            img_data = (img_data / 255.0 * 0.99) + 0.01
            print(numpy.min(img_data))
            print(numpy.max(img_data))

            # append label and image data  to test data set
            record = numpy.append(label, img_data)
            our_own_dataset.append(record)

        return our_own_dataset
