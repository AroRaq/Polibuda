import numpy as np
from matplotlib import pyplot as plt
from control.matlab import tf
from scipy.signal import tf2ss


def wave(period, t, A):
    if (t // period) % 2 == 0:
        return 0
    return A

is_active = False
def reg3(x, target, a, hysteresis):
    global is_active
    err = target - x
    if abs(err) > a + hysteresis:
        is_active = True
        return 1 if err > 0 else -1
    if abs(err) < a:
        is_active = False
        return 0
    if is_active:
        return 1 if x < target else -1
    return 0

def reg2(x, target, hysteresis):
    global is_active
    if x > target:
        is_active = False
        return 0
    if x < target - hysteresis:
        is_active = True
        return 1
    return 1 if is_active else 0

def Euler(A, B, C, D, x0, tarray, u, dt):
    X = [x0]
    Y = [0]
    U = []
    for t in tarray:
        u_ = u(t, Y[-1])
        x_ = (A @ X[-1] + B * u_) * dt
        y = C @ X[-1] + D * u_
        X.append(X[-1] + x_)
        Y.append(np.reshape(y, (-1,)))
        U.append(u_)
    return X[1:], Y[1:], U

dt = 0.01
time = np.arange(0, 10, dt)

# T1 = 7
# T2 = 4
# k = 5
# A = np.reshape([0, 1, -1/(T1*T2), -(T1+T2)/(T1*T2)], newshape=(2,2))
# B = np.reshape([0, k/(T1*T2)], newshape=(2,1))
# C = np.reshape([1, 0], newshape=(1,2))
# D = np.zeros((1,1))
# x0 = np.reshape([0,0], newshape=(2,1))

A, B, C, D = tf2ss([0,2],[3,1])
x0 = np.array([0])


# A = np.array([-1/3])
# B = np.array([2/3])
# C = np.array([1])
# D = np.array([0])
# x0 = np.array([0])
hysteresis = 0.1
a = 0
target = 1

# _, Y, U = Euler(A, B, C, D, np.array([0]), time, lambda t, x: wave(1, t, 1), 0.05)
# _, Y, U = Euler(A, B, C, D, x0, time, lambda t, x: reg3(x, target, a, hysteresis), dt)
_, Y, U = Euler(A, B, C, D, x0, time, lambda t, x: reg2(x, target, hysteresis), dt)

plt.plot(time, Y, label="y*")
plt.plot(time, (np.array(U)*0.1)+0.1, label="input")
plt.axhline(target, linestyle='--')
plt.axhline(target-a-hysteresis, linestyle=':')
plt.axhline(target+a+hysteresis, linestyle=':')
plt.axhline(target-a, linestyle='-.')
plt.axhline(target+a, linestyle='-.')
plt.show()