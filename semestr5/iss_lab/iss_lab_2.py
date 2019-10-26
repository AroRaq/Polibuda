#%%
import numpy as np
import matplotlib.pyplot as plt

#Simulation parameters
T = 5
h = 0.1

#Model parameters
alpha = 2
x_0 = 3

t = np.arange(0, T, h)
x = np.zeros(t.shape)
x[0] = x_0

for i in range(t.shape[0] - 1):
    x[i+1] = x[i] + h*alpha*x[i]

plt.plot(t, x)

#%%
import numpy as np
import matplotlib.pyplot as plt
import scipy.integrate as intgrt

def F(point, t):
    (x, y) = point
    (dx, dy) = (y, -x-0.1*y)
    return (dx, dy)

t_min = 0
t_max = 20
h = 0.01
t = np.arange(t_min, t_max, h)
point_0 = (1, 0)

points = intgrt.odeint(F, point_0, t)

(X, Y) = zip(*points)

plt.plot(X, Y)

#%%
