import numpy as np
import network

class ConvNetwork:

    pooling_types = ['max2', 'max4', 'avg']

    def __init__(self):
        self.layers = []
        self.paddings = []
        self.strides = []
        self.poolings = []
    
    def add_conv_layer(self, kernelCount, kernelShape, padding='valid', stride=1, pooling=None):
        kernels = [np.random.randn(kernelShape[0], kernelShape[1], kernelShape[2]) * 0.01 for i in range(kernelCount)]
        self.layers.append(kernels)
        self.paddings.append(padding)
        self.strides.append(stride)
        self.poolings.append(pooling)

    def add_connected_network(self, network):
        self.network = network

    def eval(self, X):
        A = X
        for l, p, s, poo in zip(self.layers, self.paddings, self.strides, self.poolings):
            A = ConvNetwork.convolution_all(A, l, p, s)
            if poo == 'max2':
                A = ConvNetwork.max_pooling(A, (2,2))
        return A

    @staticmethod
    def max_pooling(A, kernel_shape):
        x_c = A.shape[2] // kernel_shape[1]
        y_c = A.shape[1] // kernel_shape[0]
        return np.asarray(
            [
                [
                    [np.max(A[
                        z,
                        y*kernel_shape[0]:(y+1)*kernel_shape[0], 
                        x*kernel_shape[1]:(x+1)*kernel_shape[1]]) 
                    for x in range(x_c)]
                for y in range(y_c)]
            for z in range(A.shape[0])]
        )
    
    @staticmethod
    def convolution(A, kernel, bias=0, padding='valid', stride=1):
        assert kernel.shape[1] % 2 == 1 and kernel.shape[2] % 2 == 1 and kernel.shape[0] == A.shape[0], "Bad kernel dimensions"
        assert padding in ['same', 'valid']
        if (padding=='same'):
            size = kernel.shape[1] // 2
            ad = np.zeros((A.shape[0], size, A.shape[2]))
            A = np.concatenate([ad, A, ad], axis=1)
            ad = np.zeros((A.shape[0], A.shape[1], size))
            A = np.concatenate([ad, A, ad], axis=2)
        assert (A.shape[2] - kernel.shape[2]) % stride == 0, "Bad stride"
        x_c = (A.shape[2] - kernel.shape[2] + 1) // stride
        y_c = (A.shape[1] - kernel.shape[1] + 1) // stride
        return np.asarray(
            [
                [
                    np.sum(A[ : , 
                            y-kernel.shape[1]//2:y+kernel.shape[1]//2+1, 
                            x-kernel.shape[2]//2:x+kernel.shape[2]//2+1] * kernel)
                for x in range(kernel.shape[2]//2, x_c+1, stride)]
            for y in range(kernel.shape[1]//2, y_c+1, stride)]
        )

    @staticmethod 
    def convolution_all(X, kernels, padding, stride):
        A = []
        for k in kernels:
            A.append(ConvNetwork.convolution(X, k, 0, padding, stride))
        return np.asarray(A)
    
    




# print(ConvNetwork.max_pooling(np.zeros((2, 40, 40)), (2, 2)).shape)

# print(ConvNetwork.convolution(np.ones((2, 41, 41)), np.ones((2, 7, 7)), padding='same').shape)

cnn = ConvNetwork()
cnn.add_conv_layer(32, (1, 3, 3), 'same', pooling='max2')
y = cnn.eval(np.ones((1, 28, 28)))
print(np.shape(y))