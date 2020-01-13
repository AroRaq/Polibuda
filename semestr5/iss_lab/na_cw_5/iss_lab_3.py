from scipy.signal import tf2ss
import numpy as np
from matplotlib import pyplot as plt
from matplotlib.widgets import Slider
from Chart import Chart
from PID import PID

x0 = np.reshape([0,0,0], (3,1))

tmax = 200
dt = 0.05
time = np.arange(0, tmax, dt)
k = 5
Ti = 100
Td = 2
target = 2

pid = PID(3,4,5,2, time, dt)
chart = Chart(k, Ti, Td, 2*target, lambda _k,_Ti,_Td: pid.calculate(_k, _Ti, _Td, x0, target))

def error_function(target, Y, time, dt):
    err_sum = 0
    err_inf = Y[-1]
    for y in Y:
        err = y-target
        err_sum = err_sum + dt*(err-err_inf)**2
    return err_sum

def is_divergent_period(Y, time):
    local_max_t = []
    local_max_y = []
    for y0, y1, y2, t in zip(Y[:-2], Y[1:-1], Y[2:], time[1:-1]):
        if y0 < y1 and y1 > y2:
            local_max_t.append(t)
            local_max_y.append(y1)
    local_max_t = local_max_t[len(local_max_t)//2:]
    local_max_y = local_max_y[len(local_max_y)//2:]
    per = sum(np.array(local_max_t[1:]) - np.array(local_max_t[:-1])) / (len(local_max_t)-1)
    is_d = True
    for y0, y1 in zip(local_max_y[:-1], local_max_y[1:]):
        if y0 > y1:
            is_d = False
    return is_d, per

# zadanie 3
Ti, Td = None, None
ku = 1
pu = 0
is_div = False
while not is_div:
    (_, Y) = pid.calculate(ku, Ti, Td, x0, target)
    is_div, pu = is_divergent_period(Y, time)
    ku = ku+1

chart = Chart(0.5*ku, None, None, 2*target, lambda _k,_Ti,_Td: pid.calculate(_k, _Ti, _Td, x0, target))
chart.display()