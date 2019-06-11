import numpy as np

def trim(X):
    X = np.reshape(X, (-1, 36, 36))
    X = np.array([_trim_single(x) for x in X])
    return X.reshape((X.shape[0], -1))


def _diff_score(vec, onlyMiddle):
    if onlyMiddle:
        vec = vec[8:28]
    score = np.count_nonzero(vec>0.6)*5 + np.count_nonzero(vec>0.33) + np.count_nonzero(vec<0.01)*2
    score += np.count_nonzero(abs(vec[:-1] - vec[1:])<0.01)
    return -score

def _trim_single(img):
    (top, diffTop) = (0, _diff_score(img[0], True))
    (bot, diffBot) = (35, _diff_score(img[35], True))
    for i in range(8):
        if (diffTop > diffBot):
            top = top+1
            diffTop = _diff_score(img[top], True)
        else:
            bot = bot-1
            diffBot = _diff_score(img[bot], True)

    (left, diffLeft) = (0, _diff_score(img[:, 0], False))
    (right, diffRight) = (35, _diff_score(img[:, 35], False))
    for i in range(8):
        if (diffLeft > diffRight):
            left+=1
            diffLeft = _diff_score(img[:, left], False)
        else:
            right-=1
            diffRight = _diff_score(img[:, right], False)
            
    return img[top:bot+1, left:right+1]