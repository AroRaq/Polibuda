import numpy as np
from matplotlib import pyplot as plt
from control.matlab import tf
from scipy.signal import tf2ss

# def Euler1(target, time, dt):
#     Y = [0]
#     U = []
#     for t in time:
#         u = 1 if Y[-1] < target else 0
#         y_ = (dt*u+2*Y[-1]-dt*Y[-1])/2
#         Y.append(y_)
#         U.append(u)
#     return Y[1:], time, U

# dt = 0.01
# time = np.arange(0, 5, dt)
# target = 0.5
# Y, T, U = Euler1(target, time, dt)
# plt.plot(T, target-np.array(Y))
# # plt.plot(T, U)
# plt.show()


active = False
def reg3(y, target, a, h):
    global active
    if y > target + a + h:
        active = True
        return -1
    if y < target - a - h:
        active = True
        return 1
    if target - a < y and y < target + a:
        active = False
        return 0
    if active:
        return -1 if y > target else 1
    return 0


def Euler3(A, B, C, D, x0, u, time, dt):
    X = [x0]
    Y = [0]
    U = []
    for t in time:

        u_ = u(Y[-1])
        x_ = (A @ X[-1] + B * u_) * dt
        y_ = C @ X[-1] + D * u_
        X.append(X[-1]+x_)
        Y.append(np.reshape(y_, (-1,)))
        U.append(u_)
    return X[1:], Y[1:], U

dt = 0.01
time = np.arange(0, 30, dt)
A, B, C, D = tf2ss([0,0,1],[2,1,1])
x0 = np.reshape([0,0], newshape=(2,1))
target = 1
a = 0.1
h = 0.01
X, Y, U = Euler3(A, B, C, D, x0, lambda x: reg3(x, target, a, h), time, dt)

plt.plot(time, target-a-np.array(Y))
# plt.plot(time, U)
# plt.axhline(target, linestyle='--')
# plt.axhline(target+a, linestyle='-.')
# plt.axhline(target-a, linestyle='-.')
# plt.axhline(target+a+h, linestyle='--')
# plt.axhline(target-a-h, linestyle='--')
plt.show()

