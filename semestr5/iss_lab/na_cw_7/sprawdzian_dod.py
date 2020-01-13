from mpl_toolkits.mplot3d import Axes3D
from random import random
import matplotlib.pyplot as plt
import numpy as np
from scipy.optimize import minimize
from matplotlib.patches import Ellipse
from matplotlib.patches import Rectangle
from scipy.optimize import Bounds
from math import sqrt

a = 10
b = 1


def F(x):
    return 4*x*b*sqrt(1-(float(x)/a)**2)


fig = plt.figure()
ax = fig.gca()

ellipse = Ellipse(xy=(0, 0), width=2*a, height=2*b, fill=None)
ax.set_xlim(-a, a)
ax.set_ylim(-b, b)
ax.add_patch(ellipse)

# b = Bounds([0], [a])
best = minimize(lambda x: -F(*x), [0.3], bounds=[(0.0001, a-0.0001)]).x[0]
print(best)
y = b*sqrt(1-(best/a)**2)
rect = Rectangle(xy=(-best, -y), width=best*2, height=2*y, fill=None)
ax.add_patch(rect)
# best = minimize(lambda x: F2(*x), [-1, -1]).x
# print(best)

# ax.scatter([best[0]], [best[1]], [F2(*best)], marker='o', s=200, color='b')

plt.show()
