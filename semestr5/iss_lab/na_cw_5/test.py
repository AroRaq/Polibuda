#%%
import scipy.signal as sgn
import numpy as np
import matplotlib.pyplot as plt
from ipywidgets import interact, IntSlider
%matplotlib inline

b = 2
a0, a1, a2 = 3, 4, 5

@interact(
    K = IntSlider(value=0, min = 0, max=50, step=1),
    Ti = IntSlider(value=1, min = 1, max=50, step=1),
    Td = IntSlider(value=0, min = 0, max=50, step=1))
def pid_plot(K, Ti, Td):
    Kp = K
    Ki = K / Ti
    Kd = K * Td

    licz = [b*Kd, b*Kp, b*Ki]
    mian = [1, a2, a1 + b*Kd, a0 + b*Kp, b*Ki]

    dt = 0.01
    t = np.arange(0, 200, dt)

    _, y = sgn.lti(licz, mian).step(T = t)

    plt.plot(t, y)
    plt.show()

# pid_plot(1, 1, 1)

# %%
