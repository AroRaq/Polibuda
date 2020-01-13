import numpy as np
from matplotlib import pyplot as plt
from matplotlib.widgets import Slider

k_x_max = 50
Ti_x_max = 10
td_x_max = 200


def error_function(target, Y, time, dt):
    err = target - Y
    err_p = err - err[-1]
    return np.sum(err_p*err_p*dt)

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


class Chart:


    def __init__(self, K_init, Ti_init, Td_init, max_y, sim_function):
        self.K = K_init
        self.Ti = Ti_init
        self.Td = Td_init
        self.sim_function = sim_function
        
        self.chart = None

        self.slider_k_ax = plt.axes([0.05, 0.05, 0.9, 0.05])
        self.slider_Ti_ax = plt.axes([0.05, 0.1, 0.9, 0.05])
        self.slider_Td_ax = plt.axes([0.05, 0.15, 0.9, 0.05])
        self.slider_k = Slider(self.slider_k_ax, 'K', 0, k_x_max, valinit = 0 if self.K == None else self.K)
        self.slider_Ti = Slider(self.slider_Ti_ax, 'Ti', 0, Ti_x_max, valinit = Ti_x_max if self.Ti == None else self.Ti)
        self.slider_Td = Slider(self.slider_Td_ax, 'Td', 0, td_x_max, valinit = 0 if self.Td == None else self.Td)

        self.slider_k.on_changed(lambda v: self.on_slider_change(v))
        self.slider_Ti.on_changed(lambda v: self.on_slider_change(v))
        self.slider_Td.on_changed(lambda v: self.on_slider_change(v))

        self.fun_ax = plt.axes([0.05, 0.25, 0.9, 0.7])
        plt.ylim(top=max_y)

        (X, Y) = self.sim_function(self.K, self.Ti, self.Td)
        self.plot(X, Y)
        plt.show()


    def on_slider_change(self, val):
        self.K = self.slider_k.val
        self.Ti = self.slider_Ti.val
        self.Td = self.slider_Td.val
        if self.Ti == Ti_x_max: self.Ti = None
        if self.Td == 0: self.Td = None
        (X, Y) = self.sim_function(self.K, self.Ti, self.Td)
        self.plot(X, Y)

    def plot(self, X, Y):
        if self.chart == None:
            self.chart, = plt.plot(X, np.reshape(Y, newshape=(-1,)))
        
        else:
            self.chart.set_ydata(Y)

        plt.title(error_function(2, Y, X, 0.05))

    def set_params(self, k, Ti, Td):
        self.K = k
        self.Ti = Ti
        self.Td = Td
        self.slider_k.val = k
        self.slider_Ti.val = Ti
        self.slider_Td.val = Td
        (X, Y) = self.sim_function(self.K, self.Ti, self.Td)
        self.plot(X, Y)

    def display(self):
        plt.show()
