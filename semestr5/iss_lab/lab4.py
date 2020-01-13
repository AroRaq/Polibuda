#%%
import numpy as np
from matplotlib import pyplot as plt
import scipy as scp

def heaviside(x):
    return 1 if x >= 0 else 0

def dirac(x, dt):
    return 1/dt if x == 0 else 0

def euler(A, B, C, D, x0, u, tmax, dt):
    T = np.arange(0, tmax, dt)
    X = [x0]
    Y = []
    for t in T:
        x_ = (A @ X[-1] + B * u(t)) * dt
        x = X[-1] + x_
        y = C @ x + D * u(t)
        X.append(x)
        Y.append(y)
    return (X[1:], Y)

#%%
# człon inercyjny I-rzędu
T = 1
tmax = 10
k = 1
A = -1/T * np.ones((1,1))
B = k/T * np.ones((1,1))
C = 1 * np.ones((1,1))
D = 0 * np.ones((1,1))
x0 = 0 * np.ones((1,1))
dt = 0.01
time = np.arange(0, tmax, dt)
plt.grid(True, alpha=0.5)
#skok jednostkowy
_, Yhea = euler(A, B, C, D, x0, heaviside, tmax, dt)
plt.plot(time, np.reshape(Yhea, newshape=(-1,)))
#impuls Diraca
_, Ydir = euler(A, B, C, D, x0, lambda x: dirac(x, dt), tmax, dt)
plt.plot(time, np.reshape(Ydir, newshape=(-1,)))

print(dt/(Yhea[1]-Yhea[0])*k)

#%%
# człon inercyjny II-rzędu
T1 = 1
T2 = 1
k = 0.1
tmax = 10
A = np.reshape([0, 1, -1/(T1*T2), -(T1+T2)/(T1*T2)], newshape=(2,2))
B = np.reshape([0, k/(T1*T2)], newshape=(2,1))
C = np.reshape([1, 0], newshape=(1,2))
D = np.zeros((1,1))
x0 = np.reshape([0,0], newshape=(2,1))
dt = 0.1
time = np.arange(0, tmax, dt)
#skok jednostkowy
X, Y = euler(A, B, C, D, x0, heaviside, tmax, dt)
plt.plot(time, np.reshape(Y, newshape=(-1,)))
#impuls Diraca
X, Y = euler(A, B, C, D, x0, lambda x: dirac(x, dt), tmax, dt)
plt.plot(time, np.reshape(Y, newshape=(-1,)))

#%%
# człon całkujący
k = 1
tmax = 10
A = np.zeros((1,1))
B = np.reshape([k], (1,1))
C = np.reshape([1], (1,1))
D = np.zeros((1,1))
dt = 0.01
x0 = np.zeros((1,1))

#skok jednostkowy
X, Y = euler(A, B, C, D, x0, heaviside, tmax, dt)
plt.plot(np.arange(0, tmax, dt), np.reshape(Y, newshape=(-1,)))
#impuls Diraca
X, Y = euler(A, B, C, D, x0, lambda x: dirac(x, dt), tmax, dt)
plt.plot(np.arange(0, tmax, dt), np.reshape(Y, newshape=(-1,)))

#%%
# człon oscylacyjny
w = 2
e = 0.25
k = 3
A = np.reshape([0, 1, -w*w, -2*e*w], newshape=(2,2))
B = np.reshape([0, k*w*w], newshape=(2,1))
C = np.reshape([1, 0], newshape=(1,2))
D = np.zeros((1,1))
x0 = np.reshape([0,0], newshape=(2,1))
dt = 0.1
tmax = 10
time = np.arange(0, tmax, dt)
#skok jednostkowy
_, Yhea = euler(A, B, C, D, x0, heaviside, tmax, dt)
plt.plot(time, np.reshape(Yhea, newshape=(-1,)))
#impuls Diraca
_, Ydir = euler(A, B, C, D, x0, lambda x: dirac(x, dt), tmax, dt)
plt.plot(time, np.reshape(Ydir, newshape=(-1,)))

#%%