# --------------------------------------------------------------------------
# ------------  Metody Systemowe i Decyzyjne w Informatyce  ----------------
# --------------------------------------------------------------------------
#  Zadanie 4: Zadanie zaliczeniowe
#  autorzy: A. Gonczarek, J. Kaczmar, S. Zareba, M Zieba
#  2019
# --------------------------------------------------------------------------

import numpy as np
# import cProfile
# import timeit
# from matplotlib import pyplot as plt

T_DATA_COUNT = 20000
V_DATA_COUNT = 1000

NUM_OF_CLASSES = 10

BEST_T = 0.31
BEST_K = 7

def predict(x):
    (X_t, Y_t) = getDataCompressed()
    #(X_t, Y_t) = (X_t[ :10000], Y_t[ :10000])
    x = cutNoiseAll(x)
    #x = toBinaryImage(x, BEST_T)
    dist = hamming_distance(x, X_t)
    closest = closest_classes(dist, Y_t, BEST_K)
    pred = knn_predict(closest)
    return pred.reshape((-1, 1))






def getData():
    import pickle as pkl
    with open('train.pkl', 'rb') as file:
        (x, y) = pkl.load(file)
    return (x, y)

def getDataCompressed():
    with open('data.npz', 'rb') as file:
        npz = np.load(file)
        return np.unpackbits(npz['x'], axis=-1).astype(bool), npz['y']

def toBinaryImage(img, threshold):
    return img > threshold
    #return np.greater(img, threshold)

def divideData(X, Y):
    X_train = X[:T_DATA_COUNT]
    X_val = X[T_DATA_COUNT:T_DATA_COUNT+V_DATA_COUNT]
    Y_train = Y[:T_DATA_COUNT]
    Y_val = Y[T_DATA_COUNT:T_DATA_COUNT+V_DATA_COUNT]
    return (X_train, X_val, Y_train, Y_val)
        
def hamming_distance(X, X_train):
    trans = np.transpose(X_train)
    return np.dot((1-X), trans) + np.dot(X, 1-trans)
    #return (1-X)@trans + X@(1-trans)

def closest_classes(Dist, y, k):
    ind = np.argpartition(Dist, k)[:, :k]
    return y[ind]

def knn_predict(closest):
    occur = [np.bincount(closest[i], minlength=NUM_OF_CLASSES) for i in range(closest.shape[0])]
    return np.argmax(occur, axis=-1)

# def saveData():
#     #(x, y) = getData()
#     (x, y) = load_mnist()
#     #x = cutNoiseAll(x)
#     x = toBinaryImage(x, BEST_T)
#     np.savez_compressed('dataMnist', x=np.packbits(x, axis=-1), y=y)

# def getScore(pred, Y_val):
#     return np.count_nonzero(pred == Y_val) / Y_val.shape[0]    

# def test():
#     (x, y) = getData()
#     x, y = x[-2500:], np.reshape(y[-2500:], (-1, 1))
#     print(getScore(predict(x), y))  







# def model_selection(X_train, X_val, Y_train, Y_val, K_values, Theta_values):
#     X_t = toBinaryImage(X_train, BEST_T)
#     X_v = toBinaryImage(X_val, BEST_T)
#     dist = hamming_distance(X_v, X_t)
#     best = 0
#     bestK = 0
#     for k in K_values:
#         closest = closest_classes(dist, Y_train, k)    
#         pred = knn_predict(closest)
#         score = getScore(pred, Y_val)
#         if score > best:
#             best = score
#             bestK = k
#         print (k, score)
    
#     best = 0
#     bestT = 0
#     for t in Theta_values:
#         X_t = toBinaryImage(X_train, t)
#         X_v = toBinaryImage(X_val, t)
#         dist = hamming_distance(X_v, X_t)
#         closest = closest_classes(dist, Y_train, bestK)
#         pred = knn_predict(closest)
#         score = getScore(pred, Y_val)
#         if score > best:
#             best = score
#             bestT = t
#         print(t, score)

#     return (bestK, bestT, best)





def diffScore(vec, onlyMiddle):
    if onlyMiddle:
        vec = vec[8:28]
    c = np.count_nonzero(vec>0.5)
    if (c > 0):
        return c * -10
    diff = abs(vec[:-1] - vec[1:])
    score = np.count_nonzero(diff>0.05) - 3 * np.count_nonzero(diff<0.01)
    return score

def cutNoiseAll(X):
    X = np.reshape(X, (X.shape[0], 36, 36))
    X = np.array([cutNoise(x) for x in X])
    return X.reshape((X.shape[0], -1))

def cutNoise(img):
    top = 0
    bot = 35
    top, diffTop = 0, diffScore(img[0], True)
    bot, diffBot = 35, diffScore(img[35], True)
    for i in range(8):
        if (diffTop > diffBot):
            top = top+1
            diffTop = diffScore(img[top], True)
        else:
            bot = bot-1
            diffBot = diffScore(img[bot], True)
    left, diffLeft = 0, diffScore(img[:, 0], False)
    right, diffRight = 35, diffScore(img[:, 35], False)
    for i in range(8):
        if (diffLeft > diffRight):
            left+=1
            diffLeft = diffScore(img[:, left], False)
        else:
            right-=1
            diffRight = diffScore(img[:, right], False)
    return img[top:bot+1, left:right+1]




def load_mnist():
    import os
    import gzip
    import numpy as np

    """Load MNIST data from `path`"""
    labels_path = os.path.join('train-labels-idx1-ubyte.gz')
    images_path = os.path.join('train-images-idx3-ubyte.gz')

    with gzip.open(labels_path, 'rb') as lbpath:
        labels = np.frombuffer(lbpath.read(), dtype=np.uint8,
                               offset=8)

    with gzip.open(images_path, 'rb') as imgpath:
        images = np.frombuffer(imgpath.read(), dtype=np.uint8,
                               offset=16).reshape(len(labels), 784)

    return images, labels


(x, y) = load_mnist()
x = x/255
print(np.max(x))
np.savez_compressed('dataMnist', x=x, y=y)


# test()
# cProfile.run('test()')

# starttime = timeit.default_timer()
# cProfile.run('test()')
# print(timeit.default_timer() - starttime)

# (x,y) = getData()
# x = np.reshape(x, (x.shape[0], 36, 36))
# x = cutNoiseAll(x)
# x = np.reshape(x, (x.shape[0], -1))
# (X_t, X_v, Y_t, Y_v) = divideData(x, y)
#print(model_selection(X_t, X_v, Y_t, Y_v, [10,11,12,13,14,15,16,17], [.32,.33,.34,.35,.36,.37]))
# print(model_selection(X_t, X_v, Y_t, Y_v, [3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13], [.28, .29, .3, .31, .32, .33, .34, .35, .36]))
#saveData()

# starttime = timeit.default_timer()
# (x, y) = getData()
# pred = predict(x[-1000:])
# print (getScore(pred, np.reshape(y[-1000:], (1000, 1))))
# print(timeit.default_timer() - starttime)

