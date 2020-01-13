from mpl_toolkits.mplot3d import Axes3D
from random import random
import matplotlib.pyplot as plt
import numpy as np
from scipy.optimize import minimize


def f(x, y):
    return (1-x)**2 + 100*(y-x**2)**2


fig = plt.figure()
ax = fig.gca(projection='3d')

# Make data.
X = np.outer(np.linspace(-2, 2, 30), np.ones(30))
Y = X.copy().T  # transpose
Z = f(X, Y)

# plot function surface
surf = ax.plot_surface(X, Y, Z, linewidth=0, antialiased=False, alpha=0.3)

# calculate minimize route
progress = []
best = minimize(lambda x: f(*x), [-1, -1],
                callback=lambda xk: progress.append(xk)).x

x_, y_ = zip(*progress)

ax.scatter(x_, y_, f(np.array(x_), np.array(y_)), marker='o', s=100, color='c')
ax.scatter([best[0]], [best[1]], [f(*best)], marker='o', s=200, color='b')

plt.show()
