from network import NeuralNetwork
import tester
from imgCutter import trim
import numpy as np

def test_network(network, labels=None):
    (x, y) = tester.get_data_test(2500)
    x = trim(x)
    y = y.reshape((-1, 1))
    print(tester.get_score(network.predict(x.T), y, labels))

def train_network(networkPath, trainDataPath, epochs, nnSizes = [], nnAct = [], gradReps=10, rate=0.1, miniBatch=100):
    import os
    if os.path.isfile(networkPath):
        nn = NeuralNetwork.from_file(networkPath)
    else: 
        nn = NeuralNetwork.create_random(nnSizes, nnAct)
    nn.visualize_neurons('neurons')
    (x, y) = tester.get_data(trainDataPath)
    (x_val, y_val) = (x[-2500:], y[-2500:])
    y_val = y_val.reshape((-1, 1))
    (x, y) = (x[:-2500], y[:-2500])
    y = y.reshape((-1, 1))
    nn.train(trainData=(x.T, y.T), epochs=epochs, gradReps=gradReps, rate=rate, miniBatch=miniBatch, valData=(x_val.T, y_val.T))
    test_network(nn)
    inp = input('Save to file? (y/n): ')
    if (inp == 'y'):
        nn.save_to_file(networkPath)
        print(f'Succesfully saved to {networkPath}')

def train_network_part(labels, networkPath, trainDataPath, epochs, nnSizes = [], nnAct = [], gradReps=10, rate=0.1, miniBatch=100):
    import math
    import os
    (x, y) = tester.get_data(trainDataPath)
    (x, y) = (x[np.nonzero(np.isin(y, labels))], y[np.nonzero(np.isin(y, labels))])
    (x_val, y_val) = (x[-math.floor(0.05*x.shape[0]):], y[-math.floor(0.05*y.shape[0]):])
    y_val = y_val.reshape((-1, 1))
    (x, y) = (x[:-math.floor(0.05*x.shape[0])], y[:-math.floor(0.05*y.shape[0])])
    y = y.reshape((-1, 1))
    networkPath = networkPath + '_' + ''.join(map(str, labels)) + '.npz'
    if os.path.isfile(networkPath):    
        nn = NeuralNetwork.from_file(networkPath)
    else: 
        nn = NeuralNetwork.create_random(nnSizes, nnAct)
    nn.train(trainData=(x.T, y.T), epochs=epochs, gradReps=gradReps, rate=rate, miniBatch=miniBatch, valData=(x_val.T, y_val.T))
    test_network(nn, labels)
    inp = input('Save to file? (y/n): ')
    if (inp == 'y'):
        nn.save_to_file(networkPath)
        print(f'Succesfully saved to {networkPath}')



# for i in range(10):
        # testNetwork(NeuralNetwork.from_file('networks/network_256_128.npz'), [i])
# test_network(NeuralNetwork.from_file('networks/x_network_256_128_0246.npz'), [0,2,4,6])
# train_network('networks/network_256_128.npz', 'dataCut.npz', epochs=5, nnSizes=[28*28, 256, 128, 10], nnAct=['relu', 'relu', 'softmax'], 
#                 gradReps=3, rate=0.01, miniBatch=128)


train_network_part([0,2,4,6], 'networks/network_256_128', 'dataCut.npz', epochs=1, 
                nnSizes=[28*28, 256, 128, 10], nnAct=['relu', 'relu', 'sigmoid'], 
                gradReps=1, rate=0.01, miniBatch=64)
                