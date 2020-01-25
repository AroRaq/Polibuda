import numpy as np
from matplotlib import pyplot as plt
from scipy.optimize import minimize
from scipy.optimize import fmin
from control.matlab import tf
from control.matlab import feedback
from control.matlab import step
from control.matlab import pade
import math

time = np.arange(0, 20, 0.01)

C = 1250
N = 60
d = 0.246
q0 = 50

alpha = (2 * N) / (d * d * C)
beta = 1 / d
B = (C * C) / (2 * N)

s = tf('s')
sys = B / ((s + alpha) * (s + beta))
pa = tf(*pade(d, 10))
G = sys * pa


def fb(Y):
    sum = np.sum(np.abs(Y-1))
    # print(sum)
    return sum


def f(G, kp, ki, kd):
    if math.isnan(kp) or math.isnan(ki) or math.isnan(kd):
        return np.inf
    s = tf('s')
    pid = kp + ki / s + kd * s
    print(kp, kd)
    feed = feedback(G * pid)
    Y, T = step(feed, time)
    return fb(Y)


# kp = 0.0001
# ki = 0.00006


# best = minimize(lambda x: f(G, x[0], x[1], 0), [kp, ki]).x
# print(best)

# pi = best[0] + best[1]/s
# feed = feedback(pi * G)
# Y, T = step(feed, time)

# plt.plot(time, Y, color='b', label='pi  ' + str(best))
kp = 0.1
kd = 0.1

bounds = [(0.000001, 10), (0.000001, 10)]
best_result = minimize(lambda x: f(G, x[0], 0, x[1]), [
    kp, kd])

print(best_result)

best = best_result.x

pi = best[0] + best[1]*s
feed = feedback(pi * G)
Y, T = step(feed, time)

plt.plot(time, Y, color='r', label='pd  ' + str(best))


plt.legend()
plt.show()
