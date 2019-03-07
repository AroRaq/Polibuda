

import numpy as np
import matplotlib.pyplot as plt

#x = np.linspace(0, 20, 100)  # Create a list of evenly-spaced numbers over the range
#plt.plot(x, np.sin(x))       # Plot the sine of each x point
#plt.show()                   # Display the plot

#string napisów, krótsze od n
#czy liczba jest 2^n
#lista elementów, zwrócić listę 192837465

def shorter(str, len): 
    words = str.split(' ')
    ret = ""
    for word in words: 
        if (1 < len):
            ret += word + ' '
    return ret

shorter("1234 123456 123 1 123456789", 6)