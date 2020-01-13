import numpy as np
import scipy.optimize as sop
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d.axes3d import Axes3D, get_test_data
from matplotlib import cm

a = 2
b = 3
c = 5
A = 8
B = 13
w1 = 0.21 * 2 * np.pi
w2 = 0.34 * 2 * np.pi



def F1(u1, u2, a=a, b=b, c=c):
    return np.square(u1 - a) + np.square(u2 - b) + c


def obiekt1():

    F_wrap = lambda u: F1(u[0], u[1])

    xopt = sop.fmin(F_wrap, [0, 0])

    U1 = np.arange(-2, 6, 0.1)
    U2 = np.arange(-2, 6, 0.1)
    U1, U2 = np.meshgrid(U1, U2)
    Z = np.array(list(map(F1, U1, U2)))
    
    fig = plt.figure(figsize=plt.figaspect(0.5))

    ax = fig.add_subplot(1, 1, 1, projection='3d')
    
    surf = ax.plot_surface(U1, U2, Z, rstride=1, cstride=1, cmap=cm.coolwarm,
                           linewidth=0, antialiased=False)
    plt.plot([xopt[0]], [xopt[1]], [F_wrap(xopt)], 'ro')
    fig.colorbar(surf, shrink=0.5, aspect=10)

    ax.set_xlabel('U1')
    ax.set_ylabel('U2')
    ax.set_zlabel('Y')
    plt.show()


def F2(u1, u2, a=a, b=b, c=c, t=1):
    return np.square(u1 - (a + A * np.sin(w1 * t))) + np.square(u2 - (b + B *np.sin(w2 * t))) + c


def obiekt2(okres):

    XOPTs = []
    values = []

    timeline = np.arange(0, 1000)
    for t in timeline:
        F_wrap = lambda u: F2(u[0], u[1], t=t)
        opt_u = sop.fmin(F_wrap, [0,0])
        XOPTs.append(opt_u)
        values.append(F_wrap(opt_u))


    XOPTs = np.array(XOPTs)

    xopt = sop.fmin(F_wrap, [0, 0])

    U1 = np.linspace(5, 15, 100)
    U2 = np.linspace(5, 15, 100)
    # U1, U2 = np.meshgrid(U1, U2)
    # Z = np.array(list(map(F2, U1, U2)))

    
    fig = plt.figure(figsize=plt.figaspect(0.5))

    ax = fig.add_subplot(111, projection='3d')
    
    print(U1.shape, U2.shape, XOPTs.shape)
    print(XOPTs[:,0].shape, XOPTs[:,1].shape, timeline.shape)
    surf = ax.plot(XOPTs[:,0], XOPTs[:,1], timeline)
    # plt.plot(timeline, values)
                                #, rstride=1, cstride=1, cmap=cm.coolwarm, linewidth=0, antialiased=False)
    # plt.plot([xopt[0]], [xopt[1]], [F_wrap(xopt)], 'ro')
    # fig.colorbar(surf, shrink=0.5, aspect=10)

    ax.set_xlabel('U1')
    ax.set_ylabel('U2')
    ax.set_zlabel('Y')
    plt.show()


obiekt1()
# obiekt2(10)