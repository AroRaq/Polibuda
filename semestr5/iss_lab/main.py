import random
import matplotlib.pyplot as plt
import math
import numpy as np

def find_zero(loss_function, left, right):
    e = 0.01
    no_progress_iters = 0
    stop_after = 100
    best = 10000
    while no_progress_iters < stop_after:
        x = random.randint(left, right)
        if loss_function(x) < best:
            if best - loss_function(x) > e:
                no_progress_iters = 0
            best = loss_function(x)
        no_progress_iters = no_progress_iters + 1

def plot_result(loss_function, left, right, best, hits):
    t1 = np.arange(left, right, (right-left)/100)
    plt.plot(t1, loss_function(t1))


def f(x):
    return np.sin(x)



plot_result(f, -10, 10, 1, 1)
    


