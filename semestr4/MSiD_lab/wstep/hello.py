

import numpy as np
import matplotlib.pyplot as plt

#x = np.linspace(0, 20, 100)  # Create a list of evenly-spaced numbers over the range
#plt.plot(x, np.sin(x))       # Plot the sine of each x point
#plt.show()                   # Display the plot

#string napisów, krótsze od n
#czy liczba jest 2^n
#lista elementów, zwrócić listę 192837465

#%%
def shorter(str, length): 
    words = str.split(' ')
    ret = ""
    for word in words: 
        if (len(word) < length):
            ret += word + ' '
    return ret.strip()

shorter("4444 666666 333 1 999999999", 6)

#%%
import math
def isPowerOf2(number):
    return math.log2(number) == math.floor(log2(number))
    
#%%
isPowerOf2(15)
#%%
isPowerOf2(1)
#%%
isPowerOf2(128)

#%%
def weirdList(src):
    if len(src) > 0:
        return [src[0]] + weirdList(src[1::][::-1])
    else:
        return []

weirdList([1,2,3,4,5,6,7,8,9])