import numpy as np
from matplotlib import pyplot as plt
from scipy.optimize import minimize
from control.matlab import tf
from control.matlab import feedback
from control.matlab import step

G = tf([0, 0, 2],
        [0, 3, 1])

time = np.arange(0, 100, 0.05)

def PID(k, Ti, Td):
    Kp = k
    Ki = 0 if Ti == None else k/Ti
    Kd = 0 if Td == None else k*Td
    return tf([Kd, Kp, Ki],
                [0,1,0])

def calc_pid(k, Ti, Td):
    pid = PID(k, Ti, Td)
    feed = feedback(G*pid, 1)
    Y, T = step(feed, time)
    return T, Y

# for Td in [0.25, 5, 10, 20]:
#     T, Y = calc_pid(4, 0.1, Td)
#     plt.plot(T, 1-Y, label=f"Td: {Td}")

def error_fun(Y, target):
    if abs(target - Y[-1]) > 0.05:
        return 1000
    err = Y - target
    return np.sum(0.05*err*err)

def for_optimize(k, Ti, Td):
    T, Y = calc_pid(k, Ti, Td)
    return error_fun(Y, 1)


print(minimize(lambda x: for_optimize(*x), [33.33611222,  0.13144108, 23.64579223]))

T, Y = calc_pid(33.33611222,  0.13144108, 23.64579223)
plt.plot(T, 1-Y)
# plt.legend()
plt.show()