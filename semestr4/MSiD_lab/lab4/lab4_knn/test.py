

import numpy as np
import cProfile
import timeit
from matplotlib import pyplot as plt
from matplotlib import image as mpimg
import pickle as pkl

def getData():
    with open('train.pkl', 'rb') as file:
        (x, y) = pkl.load(file)
    return (x, y)

def displayImage(img, label):
    print(f'label: {label}')
    plt.imshow(img, cmap='gray')

def diffScore(vec, onlyMiddle):
    if onlyMiddle:
        vec = vec[8:28]
    score = np.count_nonzero(vec>0.6)*5 + np.count_nonzero(vec>0.33) + np.count_nonzero(vec<0.01)*2
    score += np.count_nonzero(abs(vec[:-1] - vec[1:])<0.01)
    return -score

def cutNoiseAll(X):
    return np.array([cutNoise(x) for x in X])

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


X,Y = getData()
X = X.reshape((X.shape[0], 36, 36))

# starttime = timeit.default_timer()

X = cutNoiseAll(X)
X = X.reshape((-1, 28*28))
np.savez_compressed('dataCut.npz', x=X, y=Y)

# for i in range(len(X[:200])):
#     mpimg.imsave(f"img/out{i}.png", cutNoise(X[i]))
    #cutNoise(X[i])
    
# print(timeit.default_timer() - starttime)


