import numpy as np
import pickle as pkl
import predict
from network import NeuralNetwork
import imgCutter

def test_submit(count, labels=None):
    (x, y) = get_data_test(count)
    if (labels != None):
        (x, y) = (x[np.nonzero(np.isin(y, labels))], y[np.nonzero(np.isin(y, labels))])
    y = np.reshape(y, (-1, 1))
    pred = predict.predict(x)
    print(get_confusion_matrix(pred, y))
    print(get_score(pred, y))


def get_data_test(count):
    with open('..//data//train.pkl', 'rb') as file:
        (x, y) = pkl.load(file)
    return (x[-count:], y[-count:])

def get_data(filename):
    with open(filename, 'rb') as file:
        npz = np.load(file)
        return (npz['x'], npz['y'])

def get_score(pred, Y_val, labels=None):
    assert pred.shape == Y_val.shape, f"Pred: {pred.shape}, Y_val: {Y_val.shape}"
    if (labels==None):
        return np.count_nonzero(pred == Y_val) / Y_val.shape[0]
    idx = np.nonzero(np.isin(Y_val, labels))
    return np.count_nonzero(pred[idx] == Y_val[idx]) / Y_val[idx].shape[0]

def get_confusion_matrix(pred, Y_val):
    res = np.zeros((10, 10))
    for p, y in zip (pred, Y_val):
        if (p != y):
            res[y, p] += 1
    return res


test_submit(2500)