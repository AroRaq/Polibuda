import numpy as np
import math

class NeuralNetwork:

    def __init__(self, sizes, W, b, activations, inputDropout, hiddenDropout):
        self.sizes = sizes
        self.W = W
        self.b = b
        self.activations=activations
        self.inputDropout = inputDropout
        self.hiddenDropout = hiddenDropout

    @staticmethod
    def from_file(path):
        with open(path, 'rb') as file:
            npz = np.load(file)
            return NeuralNetwork(npz['sizes'], npz['W'], npz['b'], npz['activations'], npz['ido'], npz['hdo'])

    @staticmethod
    def create_random(sizes, activations):
        W = [np.random.randn(sizes[i+1], sizes[i]) * 0.01 for i in range(len(sizes) - 1)]
        b = [np.zeros(shape = (sizes[i+1], 1)) for i in range(len(sizes) - 1)]
        return NeuralNetwork(sizes, W, b, activations, 0.9, 0.9)

    def predict(self, X):
        (_, A) = self.__feed_forward_test(X)
        return np.reshape(np.argmax(A[-1], axis=0), (-1,1))

    def __feed_forward_train(self, X):
        Z = []
        A = [zero_out_random(X, 1-self.inputDropout)]
        for w, b, act in list(zip(self.W, self.b, self.activations))[:-1]:
            Z.append(zero_out_random(np.dot(w, A[-1]) + b, 1-self.hiddenDropout))
            A.append(Activation.get(act)[0](Z[-1]))
        Z.append(np.dot(self.W[-1], A[-1]) + self.b[-1])
        A.append(Activation.get(self.activations[-1])[0](Z[-1]))
        return (np.asarray(Z), A)

    def __feed_forward_test(self, X):
        Z = []
        A = [X * self.inputDropout]
        for w, b, act in list(zip(self.W, self.b, self.activations)):
            Z.append(self.hiddenDropout * (np.dot(w, A[-1]) + b))
            act = Activation.get(act)[0]
            A.append(act(Z[-1]))
        return (np.asarray(Z), A)

    def __activation_error(self, Y, Z, A):
        dA = [None] * len(self.W)
        y_mat = np.transpose([np.bincount([y], minlength=np.shape(A[-1])[0]) for y in Y[0]])

        dA[-1] = (y_mat-A[-1]) * Activation.get(self.activations[-1])[1](Z[-1])
        for i, act in reversed(list(zip(range(len(dA)-1), self.activations))):
            dA[i] = self.W[i+1].T.dot(dA[i+1])*Activation.get(act)[1](Z[i])
        return dA

    def __backpropagation(self, y, Z, A):
        dA = self.__activation_error(y, Z, A)
        batch_size = y.shape[1]
        db = [np.sum(d) / batch_size for d in dA]
        dW = [np.dot(d, a.T) / batch_size for d, a in zip(dA, A)]
        return (dW, db)

    def __update_parameters(self, dW, db, eta):
        self.W += eta*np.array(dW)
        self.b += eta*np.array(db)
        for (w, b) in zip(self.W, self.b):
            b = b / (np.abs(w).max())
            w = w / (np.abs(w).max())

    def __gradient_descent(self, reps, X, Y, eta):
        for r in range(reps):
            (Z, A) = self.__feed_forward_train(X)
            (dW, db) = self.__backpropagation(Y, Z, A)
            self.__update_parameters(dW, db, eta)

    def train(self, trainData, epochs=10, rate=0.1, gradReps=10, miniBatch=100, valData=None):
        import sys
        (X_train, Y_train) = trainData
        miniBatch = min(X_train.shape[1], miniBatch)
        M = X_train.shape[1] // miniBatch
        for e in range(epochs):
            acc = 0
            for b in range(M):
                sub = np.random.randint(X_train.shape[1], size=miniBatch)
                X_s = X_train[:, sub]
                Y_s = Y_train[:, sub]
                
                (_, A) = self.__feed_forward_test(X_s)
                y_mat = np.transpose([np.bincount([y], minlength=np.shape(A[-1])[0]) for y in Y_s[0]])
                acc = (b/(b+1)) * acc + np.count_nonzero(np.argmax(A[-1], axis=0) == Y_s) / ((b+1) * np.shape(Y_s)[1])
                sys.stdout.write(f"\rEpoch: {e+1}, Progress: {(b/M):9.2f}, Loss: {np.linalg.norm(A[-1] - y_mat):9.2f}, TrainAcc: {acc:9.3f}     ")

                self.__gradient_descent(gradReps, X_s, Y_s, rate)

            if (valData != None):
                (X_val, Y_val) = valData
                (_, A) = self.__feed_forward_test(X_val)
                acc = np.count_nonzero(np.argmax(A[-1], axis=0) == Y_val) / np.shape(Y_val)[1]
                sys.stdout.write(f"ValAcc: {acc:9.3f}")
            sys.stdout.write("\n")

    def save_to_file(self, path):
        self.W = [w.astype('float16') for w in self.W]
        self.b = [b.astype('float16') for b in self.b]
        np.savez_compressed(path, 
            sizes = self.sizes,
            W = self.W, b = self.b, activations=self.activations,
            ido = self.inputDropout, hdo = self.hiddenDropout)
    
    def visualize_neurons(self, folder):
        import matplotlib.image as mpimg
        for i in range(self.W[0].shape[0]):
            img = np.reshape(self.W[0][i,:], (28, 28))
            mpimg.imsave(f"{folder}\\img{i}.png", img)


def zero_out_random(X, rate):
    count = X.size
    idx = np.random.choice(count, size=int(rate*count), replace=False)
    multiplier = np.ones(shape=(count,))
    multiplier[idx] = 0
    multiplier = np.reshape(multiplier, X.shape)
    return multiplier * X




class Activation:

    @staticmethod
    def get(name):
        if (name=='sigmoid'):
            return (lambda x: Activation.__sigmoid(x), lambda x: Activation.__sigmoid_prime(x))
        elif (name=='swish'):
            return (lambda x: Activation.__swish(x), lambda x: Activation.__swish_prime(x))
        elif (name=='softmax'):
            return (lambda x: Activation.__softmax(x), lambda x: Activation.__softmax_prime(x))
        elif (name=='relu'):
            return (lambda x: Activation.__relu(x), lambda x: Activation.__relu_prime(x))
        elif (name=='lrelu'):
            return (lambda x: Activation.__lrelu(x), lambda x: Activation.__lrelu_prime(x))
        raise Exception(f"No activation function named {name}")

    @staticmethod
    def __sigmoid(x):
        return 1./(1.+np.exp(-x))

    @staticmethod
    def __sigmoid_prime(x):
        return Activation.__sigmoid(x)*(1.-Activation.__sigmoid(x))

    @staticmethod
    def __swish(x):
        return x/(1+np.exp(-x))

    @staticmethod
    def __swish_prime(x):
        return (np.exp(x)/(np.exp(x)+1)) * ((x + np.exp(x) + 1)/(np.exp(x) + 1))

    @staticmethod
    def __relu(x):
        return np.maximum(x, 0)

    @staticmethod
    def __relu_prime(x):
        return (x > 0).astype(int)

    @staticmethod
    def __lrelu(x):
        return np.maximum(x, 0.1*x)

    @staticmethod
    def __lrelu_prime(x):
        res = (x > 0).astype(int)
        res[x == 0] = 0.1
        return res

    @staticmethod
    def __softmax(x):
        exps = np.exp(x - np.max(x))
        return exps/np.sum(exps, axis=0)
    
    @staticmethod
    def __softmax_prime(x):
        return Activation.__softmax(x)*(1-Activation.__softmax(x))
