import numpy as np

class PID: 
    def __init__(self, a0, a1, a2, b, time, dt):
        self.A = np.reshape([0,1,0,0,0,1,-a0,-a1,-a2], (3,3))
        self.B = np.reshape([0,0,b], (3,1))
        self.C = np.reshape([1,0,0], (1,3))
        self.D = np.reshape([0], (1,1)) 

        self.time = time
        self.dt = dt

    def calculate(self, k, Ti, Td, x0, target):
        X = [x0]
        Y = [0]
        eprev, i_ = 0, 0
        for t in self.time:

            e = target - Y[-1]

            p = k * e #* self.dt
            i = 0 if Ti == None else i_ + (k/Ti) * e
            i_ = i_ if Ti == None else i_ + (k/Ti) * e * self.dt
            d = 0 if Td == None else k * Td * (e-eprev) #* self.dt 

            eprev = e

            u = p + i + d
            x_ = (self.A @ X[-1] + self.B * u) * self.dt
            x = X[-1] + x_
            y = self.C @ x + self.D * u
            X.append(x)
            Y.append(y)
        return (self.time, np.reshape(Y[1:], newshape=(-1,)))

    def get_error(self, Y, target):
        err_sum = 0
        err_inf = Y[-1]
        for y in Y:
            err = y - target
            err_sum = err_sum + self.dt*(err-err_inf)**2
        return err_sum

