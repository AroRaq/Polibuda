from mpl_toolkits.mplot3d import Axes3D
from random import random
import matplotlib.pyplot as plt
import numpy as np
from scipy.optimize import minimize


min_ = -2
max_ = 2


def minimize1(f, x0):
    size = len(x0)
    print(size)
    best = float("inf")
    best_x = x0
    for n in range(1, 100000):
        x = [random()*(max_-min_)+min_ for i in range(size)]
        res = f(x)
        if (res < best):
            best_x = x
            best = res
    return best_x


def F(x, y):
    return (x**2+y**2-1)**2+(x**2-1)**2


def F2(x, y):
    return (x+y)/(x**2+y**2+1)


a = 2
b = 1


def F3(x):
    return 4*x*b*np.sqrt(1-(x/a)**2)


fig = plt.figure()
ax = fig.gca(projection='3d')

X = np.outer(np.linspace(-5, 5, 30), np.ones(30))
Y = X.copy().T
Z = F2(X, Y)

surf = ax.plot_surface(X, Y, Z, linewidth=0, antialiased=False, alpha=0.3)

best = minimize1(lambda x: F2(*x), [-1, -1])
# best = minimize(lambda x: F2(*x), [-1, -1]).x
print(best)

ax.scatter([best[0]], [best[1]], [F2(*best)], marker='o', s=200, color='b')

plt.show()
