def model_selection(X_train, X_val, Y_train, Y_val, K_values, Theta_values):
    X_t = toBinaryImage(X_train, BEST_T)
    X_v = toBinaryImage(X_val, BEST_T)
    dist = hamming_distance(X_v, X_t)
    best = 0
    bestK = 0
    for k in K_values:
        closest = closest_classes(dist, Y_t, k)    
        pred = knn_predict(closest)
        score = getScore(pred, Y_v)
        if score > best:
            best = score
            bestK = k
        print (k, score)
    
    best = 0
    bestT = 0
    for t in Theta_values:
        X_t = toBinaryImage(X_train, t)
        X_v = toBinaryImage(X_val, t)
        dist = hamming_distance(X_v, X_t)
        closest = closest_classes(dist, Y_t, bestK)
        pred = knn_predict(closest)
        score = getScore(pred, Y_v)
        if score > best:
            best = score
            bestT = t
        print(t, score)

    return (bestK, bestT, best)



# def displayImage(img, label):
#     print(f'label: {label}')
#     plt.imshow(img, cmap='gray')
