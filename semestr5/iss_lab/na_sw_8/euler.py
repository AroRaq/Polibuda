import numpy as np
from matplotlib import pyplot as plt
from scipy.optimize import minimize
from control.matlab import tf
from control.matlab import tf2ss
from control.matlab import feedback
from control.matlab import step
from control.matlab import pade
import math


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


time = np.arange(0, 20, 0.005)


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

kp = 7.5546*0.0001
ki = 1.4984*0.001
kd = 0

delay = tf(*pade(d))
s = tf('s')
G = ((B)/((s+alpha)*(s+beta)))


pid = GetPID(kp, ki, kd)
pid_ss = tf2ss(pid)
delay_ss = tf2ss(delay)
G_ss = tf2ss(delay)

T = 20
dt = 0.005

X, Y = euler(G_ss.A, G_ss.B, G_ss.C,
             G_ss.D, [0], lambda x: 1, T, dt)

plt.plot(time, np.reshape(Y, (-1,)))
plt.show()
