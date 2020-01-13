#%%
import numpy as np
from matplotlib import pyplot as plt
from scipy import integrate as intgrt

def dirac(x, dx):
    return 1/dx if abs(x) <= dx else 0

def one(x):
    return 1 if x>=0 else 0

def Euler(A, B, C, D, x0, tarray, u, dt):
    X = [x0]
    Y = []
    for t in tarray:
        x_ = (A @ X[-1] + B * u(t))*dt
        y = C @ X[-1] + D * u(t)
        X.append(X[-1] + x_)
        Y.append(y)
    return X, Y

T = 1
k = 1
A = np.reshape([0, 1, 0, -1/T], newshape=(2, 2))
B = np.reshape([0, k/T], newshape=(2, 1))
C = np.reshape([1, 0], newshape=(1, 2))
D = np.zeros((1,1))
x0 = np.reshape([0, 0], newshape=(2,1))
tarray = np.arange(0, 10, 0.01)
_, Yone = Euler(A, B, C, D, x0, tarray, one, 0.01)
plt.plot(np.reshape(tarray, newshape=(-1,)), np.reshape(Yone, newshape=(-1,)))

_, Ydir = Euler(A, B, C, D, x0, tarray, lambda x: dirac(x, 0.01), 0.01)
plt.plot(np.reshape(tarray, newshape=(-1,)), np.reshape(Ydir, newshape=(-1,)))

# %%
def x_one(x, t):
    u = one
    T = 1
    _, x2 = x
    return [x2, (k/T*u(t)) - x2/T]

def x_dir(x, t):
    u = lambda x: dirac(x, 0.01)
    T = 1
    _, x2 = x
    return [x2, (k/T*u(t)) - x2/T]


R2 = intgrt.odeint(x_dir, (0,0), tarray)
plt.plot(np.reshape(tarray, newshape=(-1,)), np.reshape(R2[:,0], newshape=(-1,)))

R = intgrt.odeint(x_one, (0,0), tarray)
plt.plot(np.reshape(tarray, newshape=(-1,)), np.reshape(R[:,0], newshape=(-1,)))

# %%
