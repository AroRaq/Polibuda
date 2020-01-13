import numpy as np
import scipy as scp
import matplotlib.pyplot as plt

def euler_step(A, B, C, D, x0, dt, tmax):
    T = np.arange(0, tmax, dt)
    X = x0 * np.ones(T.shape)
    Y = np.zeros(T.shape)

    for i, t in enumerate(T, 1):
        dx = A @ X[-1] * dt + B
        y = C @ X[-1] * dt + D 
        X[i] = X[-1] + dx
        Y[i] = y
    return (X, Y)


one = np.ones((1, 1))
euler_step(one, one, one, one, 1, 0.1, 10)