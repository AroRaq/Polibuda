#%%

from control import matlab
from control import tf
import matplotlib.pyplot as plt
import numpy as np

G = tf([0,0,0,2],
       [1,5,4,3])

t = np.arange(0, 200, 0.05)

def GetPID(Kp, Ki, Kd):
    return tf([Kd, Kp, Ki],
              [0,1,0])

def TestP(values):
    _, ax = plt.subplots()
    for p in values:
        PID = GetPID(p, 0, 0)
        feed = matlab.feedback(PID * G, 1)
        response, T = matlab.step(feed, t)
        ax.plot(T, response, label = str(p))
    ax.legend()
    
def TestPI(valuesP, valuesI):
    _, ax = plt.subplots()
    for p in valuesP:
        for i in valuesI:
            PID = GetPID(p, i, 0)
            feed = matlab.feedback(PID * G, 1)
            response, T = matlab.step(feed, t)
            ax.plot(T, response, label = str(p) + "P " + str(i) +"I")
    ax.legend()
    
def TestPID(valuesP, valuesI, valuesD):
    _, ax = plt.subplots()
    for p in valuesP:
        for i in valuesI:
            for d in valuesD:
                PID = GetPID(p, i, d)
                feed = matlab.feedback(PID * G, 1)
                response, T = matlab.step(feed, t)
                ax.plot(T, response, label = str(p) + "P " + str(i) +"I "+ str(d) + "D")
    ax.legend()

TestP([0,1,5])
TestPI([1, 2], [0, 1, 5, 10])
TestPID([1, 2], [1, 5], [1, 2, 5])

# %%
