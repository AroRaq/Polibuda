import numpy as np
from types import SimpleNamespace
from matplotlib import pyplot as plt
from scipy.optimize import minimize


def F2(u, p, t):
    return (u[0]-(p.a+p.A*np.sin(p.w1*t)))**2 + (u[1]-(p.b+p.B*np.sin(p.w2*t)))**2 + p.c


dt = 0.001
T = 1
U = [[0.5, 0.5]]
Fs = []
p = SimpleNamespace()
p.a = 2
p.b = 3
p.c = 5
p.A = 8
p.B = 13
p.w1 = 0.21 * 2 * np.pi
p.w2 = 0.34 * 2 * np.pi
k = 0.001
try:
    for t in np.arange(0, 10, dt):
        if (t % T == 0):
            res = minimize(lambda x: F2(x, p, t), U[-1])
            U.append(res.x)
        Fs.append(F2(U[-1], p, t))
except OverflowError:
    pass

plt.plot(np.arange(0, 10, dt), Fs)
# plt.plot(*zip(*U))
plt.show()
