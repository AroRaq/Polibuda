import numpy as np
from types import SimpleNamespace
from matplotlib import pyplot as plt


def F(u, params):
    return (u[0]-params.a)**2+(u[1]-params.b)**2+params.c


def F2(u, p):
    return (u[0]-(p.a+p.A*np.sin(p.w1*p.t)))**2 + (u[1]-(p.a+p.B*np.sin(p.w2*p.t)))**2 + p.c


U = [[0.5, 0.5], [0.6, -0.6], [-0.7, -0.7]]
Fs = []
p = SimpleNamespace()
p.a = 0.5
p.b = 0.5
p.c = -1
k = 0.001
try:
    for i in range(1, 1000):
        num = F(U[-1], p) - F(U[-2], p)
        d0 = num / (U[-1][0] - U[-2][0])
        d1 = num / (U[-1][1] - U[-2][1])
        U.append([U[-1][0]-k*d0, U[-1][1]-k*d1])
        Fs.append(F(U[-1], p))
except OverflowError:
    pass

plt.plot(np.array([k*i for i in range(1, 1000)]), Fs)
# plt.plot(*zip(*U))
plt.show()
