# zadanie 1
# symulacja układu regulacji z regulatorem P i PD dla obiektu z Ahmeda (wykres uchybu)

# zadaniae 2
# układ regulacji z korektorami

import numpy as np
from matplotlib import pyplot as plt
from scipy.optimize import minimize
from scipy.optimize import fmin
from control.matlab import tf
from control.matlab import feedback
from control.matlab import step
from control.matlab import pade
import math

time = np.arange(0, 20, 0.001)

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


def GetPID(kp, ki, kd):
    return tf([kd, kp, ki],
              [0,  1,  0])


# reg_p = GetPID(0.002, 0, 0)
# feed = feedback(reg_p*delay*G)
# Y, T = step(feed, time)

# # plt.figure()
# plt.plot(T, 1-Y, label='p')
# # plt.show()

# reg_pd = GetPID(0.002, 0, 0.0002)
# feed = feedback(reg_pd*delay*G)
# Y, T = step(feed, time)

# # plt.figure()
# plt.plot(T, 1-Y, label='pd')
# plt.legend()
# plt.show()

reg_pid = GetPID(7.5546*0.0001, 1.4984*0.001, 0)
cor = 0.001/(s*s*s+s*s+s)
feed = feedback(feedback(G*delay, cor)*reg_pid)
Y, T = step(feed, time)

plt.plot(T, 1-Y, label='uklad2')
# plt.legend()
# plt.show()


reg_pid1 = GetPID(7.5546*0.0001, 1.4984*0.001, 0)
cor1 = 0.00001/(s*s*s+s*s+s)
feed1 = feedback(feedback(reg_pid1, cor1)*G*delay)
Y, T = step(feed1, time)

plt.plot(T, 1-Y, label='uklad1')
plt.legend()
plt.show()
