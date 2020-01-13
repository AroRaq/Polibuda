import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D

epsilon = 0.01
a = 2
b = 3
c = 5
u1 = -1
u2 = -1
k = 0.1415

ys = []
u1s = []
u2s = []


def F(u1, u2):
    return (u1 - a)**2 + (u2 - b)**2 + c


prevF = 0
currF = F(u1, u2)
diff = prevF - currF

u1s.append(u1)
u2s.append(u2)
ys.append(-currF)

prevu1 = 0
prevu2 = 0

while abs(diff) > epsilon:

    d1 = diff / (prevu1 - u1)
    d2 = diff / (prevu2 - u2)

    u1 = u1 - k*d1
    u2 = u2 - k*d2

    prevF = currF
    currF = F(u1, u2)

    u1s.append(u1)
    u2s.append(u2)
    ys.append(currF)

    print(diff)

    diff = prevF - currF


fig = plt.figure()
ax = fig.add_subplot(111, projection='3d')
ax.plot(u1s, u2s, ys)
plt.show()
