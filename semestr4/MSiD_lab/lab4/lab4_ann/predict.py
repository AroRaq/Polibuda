# --------------------------------------------------------------------------
# ------------  Metody Systemowe i Decyzyjne w Informatyce  ----------------
# --------------------------------------------------------------------------
#  Zadanie 4: Zadanie zaliczeniowe
#  autorzy: A. Gonczarek, J. Kaczmar, S. Zareba, M Zieba
#  2019
# --------------------------------------------------------------------------

import numpy as np
from imgCutter import trim
from network import NeuralNetwork

def predict(x):
    nn = NeuralNetwork.from_file('networks/network_256_128.npz')
    x = trim(x)
    pred = nn.predict(x.T)

    idx = np.nonzero(np.isin(np.reshape(pred, (-1,)), [0,2,4,6]))
    x1 = x[idx]
    nn = NeuralNetwork.from_file('networks/network_256_128_0246.npz')
    pred1 = nn.predict(x1.T)
    pred[idx] = pred1

    return pred.reshape((-1, 1))
