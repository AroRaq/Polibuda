import numpy as np
from matplotlib import pyplot as plt
from scipy.optimize import minimize
from control.matlab import tf
from control.matlab import feedback
from control.matlab import step
from control.matlab import pade
from control.matlab import impulse
import math

time = np.arange(0, 50, 0.005)


def GetPID(kp, ki, kd):
    return tf([kd, kp, ki],
              [0,  1,  0])


C = 1250
N = 60
d = 0.22
alpha = (2*N)/(d*d*C)
beta = 1/d
B = (C*C)/(2*N)
q0 = 50


delay = tf(*pade(d, 10))
s = tf('s')
G = ((B)/((s+alpha)*(s+beta)))

kp1 = 7.5546*0.0001
ki1 = 1.4984*0.001
kd1 = 0


def err_fun(Y, target):
    if abs(target - Y[-1]) > 0.05:
        return np.inf
    err = Y - target
    if (np.max(np.abs(err)) > 10):
        return np.inf
    err = [np.abs(e) for e in err]
    return np.sum(err)


target = 1


def for_optimize(x):
    pid = GetPID(x[0], x[1], x[2])
    feed = feedback(delay*G*pid)
    Y, T = step(feed, time)
    return err_fun(Y, q0)


bounds = [(0, 99999), (0.000001, 99999), (0, 99999)]
[kp, ki, kd] = minimize(
    for_optimize, [kp1, ki1, kd1]).x
print(kp)
print(ki)
print(kd)
pid = GetPID(kp, ki, kd)
feed = feedback(delay*G*pid)
Y, T = step(feed, time, input=q0)


plt.plot(time, Y)
plt.show()
