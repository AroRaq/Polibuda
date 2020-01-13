from mpl_toolkits.mplot3d import Axes3D
from random import random
import matplotlib.pyplot as plt
from matplotlib import cm
from matplotlib.ticker import LinearLocator, FormatStrFormatter
import numpy as np
from scipy.optimize import minimize


def f(x, y):
    return (1-x)**2 + 100*(y-x**2)**2


def stupid_minimize(f, x0):
    size = len(x0)
    print(size)
    best = float("inf")
    best_x = x0
    for n in range(1, 1000):
        x = [random() for i in range(0, size)]
        res = f(x)
        if (res < best):
            best_x = x
            best = res
    return best_x, best


fig = plt.figure()
ax = fig.gca(projection='3d')

# Make data.
X = np.outer(np.linspace(-2, 2, 30), np.ones(30))
Y = X.copy().T  # transpose
R = np.sqrt(X**2 + Y**2)
Z = f(X, Y)

progress = []
res = minimize(lambda x: f(*x), [-1, -1],
               callback=lambda xk: progress.append(xk))
x, y = res.x[0], res.x[1]

# res, _ = stupid_minimize(lambda x: f(*x), [0,0])
# x, y = res[0], res[1]

surf = ax.plot_surface(X, Y, Z, cmap=cm.coolwarm,
                       linewidth=0, antialiased=False, alpha=0.3)

x_ = np.array([p[0] for p in progress])
y_ = np.array([p[1] for p in progress])

print(y_)
ax.scatter(x_, y_, f(x_, y_), marker='o', s=100, color='c')
ax.scatter([x], [y], [f(x, y)], marker='o', s=200, color='b')

ax.zaxis.set_major_locator(LinearLocator(10))
ax.zaxis.set_major_formatter(FormatStrFormatter('%.02f'))

fig.colorbar(surf, shrink=0.5, aspect=5)

plt.show()
