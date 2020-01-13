import numpy as np
from matplotlib import pyplot as plt
from scipy.optimize import minimize
from control.matlab import tf
from control.matlab import feedback
from control.matlab import step

time = np.arange(0, 75, 0.01)

def GetPID(k, Ti, Td):
    Kp = k
    Ki = 0 if Ti == None else k/Ti
    Kd = 0 if Td == None else k*Td
    return tf([Kd, Kp, Ki],
              [0,  1,  0 ])

G = tf([0,0,0,2],
       [1,3,2,1])

def calc_pid(k, Ti, Td, target):
    pid = GetPID(k, Ti, Td)
    feed = feedback(G*pid, 1/target)
    return step(feed, time)
    
def err_fun(Y, dt, target):
    err = target - Y
    return np.sum(err*err*dt)

def for_optimize(k, Ti, Td):
    Y, T = calc_pid(k, Ti, Td, 1)
    return err_fun(Y, 0.05, 1)

# print(minimize(lambda x: for_optimize(*x), [1,1,1]))

# for Ti in [1, 2, 53]:
#     Y, T = calc_pid(0.3, Ti, None, 1)
#     plt.plot(T, 1-Y, label="Ti: " + str(Ti))

# plt.legend()
# plt.show()


Y, T = calc_pid(2.5, None, None, 1)
plt.plot(T, Y)
plt.show()
