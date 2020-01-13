
from control import matlab
from control import tf
import matplotlib.pyplot as plt
import numpy as np
from Chart import Chart
from scipy.optimize import minimize

G = tf([0,0,0,2],
       [1,5,4,3])

t = np.arange(0, 200, 0.05)

def GetPID(Kp, Ki, Kd):
    return tf([Kd, Kp, Ki],
              [0,1,0])

def calculate_pid(k, Ti, Td, target):
    Kp = k
    Ki = k/Ti if Ti != None else 0
    Kd = k*Td if Td != None else 0
    PID = GetPID(Kp, Ki, Kd)
    feed = matlab.feedback(G*PID, 1/target)
    Y, T = matlab.step(feed, t)
    return T, Y

def error_function(T, Y, target):
    err = target - Y
    if (abs(err[-1]) > 0.05):
        return 1000 
    err_p = err - err[-1]
    dt = T[1]-T[0]
    return np.sum(err_p*err_p*dt)

k = 10.7596597
Ti = 0.0078477
Td = 156.242113
target = 2
chart = Chart(k, Ti, Td, 2*target, lambda _k,_Ti,_Td: calculate_pid(_k, _Ti, _Td, target))

best_e, best_k, best_ti, best_td = 1000, 0, 0, 0

def for_optimize(k, Ti, Td):
    T, Y = calculate_pid(k, Ti, Td, target)
    return error_function(T, Y, target)

best = minimize(lambda x: for_optimize(x[0], x[1], x[2]), [2,2,2])
print(best)