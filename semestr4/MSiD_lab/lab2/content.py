# --------------------------------------------------------------------------
# ------------  Metody Systemowe i Decyzyjne w Informatyce  ----------------
# --------------------------------------------------------------------------
#  Zadanie 2: k-NN i Naive Bayes
#  autorzy: A. Gonczarek, J. Kaczmar, S. Zareba, P. Dąbrowski
#  2019
# --------------------------------------------------------------------------

import numpy as np

NUM_OF_CLASSES = 4

def hamming_distance(X, X_train):
    """
    Zwróć odległość Hamminga dla obiektów ze zbioru *X* od obiektów z *X_train*.

    :param X: zbiór porównywanych obiektów N1xD
    :param X_train: zbiór obiektów do których porównujemy N2xD
    :return: macierz odległości pomiędzy obiektami z "X" i "X_train" N1xN2
    """
    XX = X.toarray()
    XX_train = X_train.toarray()
    return (~XX).astype(int) @ np.transpose(XX_train) + XX.astype(int) @ ~np.transpose(XX_train)


def sort_train_labels_knn(Dist, y):
    """
    Posortuj etykiety klas danych treningowych *y* względem prawdopodobieństw
    zawartych w macierzy *Dist*.

    :param Dist: macierz odległości pomiędzy obiektami z "X" i "X_train" N1xN2
    :param y: wektor etykiet o długości N2
    :return: macierz etykiet klas posortowana względem wartości podobieństw
        odpowiadającego wiersza macierzy Dist N1xN2

    Do sortowania użyj algorytmu mergesort.
    """
    indices = np.argsort(Dist, kind='mergesort')
    return y[indices]
    

def p_y_x_knn(y, k):
    """
    Wyznacz rozkład prawdopodobieństwa p(y|x) każdej z klas dla obiektów
    ze zbioru testowego wykorzystując klasyfikator KNN wyuczony na danych
    treningowych.

    :param y: macierz posortowanych etykiet dla danych treningowych N1xN2
    :param k: liczba najbliższych sasiadow dla KNN
    :return: macierz prawdopodobieństw p(y|x) dla obiektów z "X" N1xM
    """
    y_short = y[:, :k]
    #y_u = np.unique(y).shape[0]
    occur = [np.bincount(y_short[i], minlength=NUM_OF_CLASSES) for i in range(y.shape[0])]
    return np.array(occur).astype(int) / k                      


def classification_error(p_y_x, y_true):
    """
    Wyznacz błąd klasyfikacji.

    :param p_y_x: macierz przewidywanych prawdopodobieństw - każdy wiersz
    macierzy reprezentuje rozkład p(y|x) NxM
    :param y_true: zbiór rzeczywistych etykiet klas 1xN
    :return: błąd klasyfikacji
    """
    maxInd = p_y_x.shape[1]-np.argmax(np.fliplr(p_y_x), axis=1)-1
    return np.count_nonzero(y_true != maxInd)/y_true.size


def model_selection_knn(X_val, X_train, y_val, y_train, k_values):
    """
    Wylicz bład dla różnych wartości *k*. Dokonaj selekcji modelu KNN
    wyznaczając najlepszą wartość *k*, tj. taką, dla której wartość błędu jest najniższa.

    :param X_val: zbiór danych walidacyjnych N1xD
    :param X_train: zbiór danych treningowych N2xD
    :param y_val: etykiety klas dla danych walidacyjnych 1xN1
    :param y_train: etykiety klas dla danych treningowych 1xN2
    :param k_values: wartości parametru k, które mają zostać sprawdzone
    :return: krotka (best_error, best_k, errors), gdzie "best_error" to
        najniższy osiągnięty błąd, "best_k" to "k" dla którego błąd był
        najniższy, a "errors" - lista wartości błędów dla kolejnych
        "k" z "k_values"
    """
    dist = hamming_distance(X_val, X_train)
    sorted_labels = sort_train_labels_knn(dist, y_train)
    errors = [classification_error(p_y_x_knn(sorted_labels, k), y_val) for k in k_values]
    best_index = np.argmin(errors)
    return (errors[best_index], k_values[best_index], errors)


def estimate_a_priori_nb(y_train):
    """
    Wyznacz rozkład a priori p(y) każdej z klas dla obiektów ze zbioru
    treningowego.

    :param y_train: etykiety dla danych treningowych 1xN
    :return: wektor prawdopodobieństw a priori p(y) 1xM
    """
    return np.bincount(y_train)/y_train.size


def estimate_p_x_y_nb(X_train, y_train, a, b):
    """
    Wyznacz rozkład prawdopodobieństwa p(x|y) zakładając, że *x* przyjmuje
    wartości binarne i że elementy *x* są od siebie niezależne.

    :param X_train: dane treningowe NxD
    :param y_train: etykiety klas dla danych treningowych 1xN
    :param a: parametr "a" rozkładu Beta
    :param b: parametr "b" rozkładu Beta
    :return: macierz prawdopodobieństw p(x|y) dla obiektów z "X_train" MxD.
    """
    mul = lambda d: X_train.toarray()[:, d] * (y_train + 1)
    numerator = [np.bincount(mul(d), minlength=NUM_OF_CLASSES+1)[1:] + a - 1 for d in range(X_train.shape[1])]
    denumerator = np.bincount(y_train, minlength=NUM_OF_CLASSES) + a + b - 2
    return np.transpose(numerator / denumerator)


def p_y_x_nb(p_y, p_x_1_y, X):
    """
    Wyznacz rozkład prawdopodobieństwa p(y|x) dla każdej z klas z wykorzystaniem
    klasyfikatora Naiwnego Bayesa.

    :param p_y: wektor prawdopodobieństw a priori 1xM
    :param p_x_1_y: rozkład prawdopodobieństw p(x=1|y) MxD
    :param X: dane dla których beda wyznaczone prawdopodobieństwa, macierz NxD
    :return: macierz prawdopodobieństw p(y|x) dla obiektów z "X" NxM
    """
    X = X.toarray()
    prob = []
    for i in range(X.shape[0]):                                                 # po D
        mul_s_f = p_x_1_y * X[i] + (1 - p_x_1_y) * (1 - X)[i]                   # Bayes macierz prawdopodobieństw [4xD]
        class_prob = np.prod(mul_s_f, axis=1) * p_y                             # p(x|y') * p(y') [1x4]
        denominator = np.sum(class_prob)                                        # suma p(x|y') * p(y')
        prob.append(class_prob / denominator)
    return np.array(prob)                                                       # macierz prawdopodobieństw p(y|x) dla obiektów z "X" [NxM]


def model_selection_nb(X_train, X_val, y_train, y_val, a_values, b_values):
    """
    Wylicz bład dla różnych wartości *a* i *b*. Dokonaj selekcji modelu Naiwnego
    Byesa, wyznaczając najlepszą parę wartości *a* i *b*, tj. taką, dla której
    wartość błędu jest najniższa.

    :param X_train: zbiór danych treningowych N2xD
    :param X_val: zbiór danych walidacyjnych N1xD
    :param y_train: etykiety klas dla danych treningowych 1xN2
    :param y_val: etykiety klas dla danych walidacyjnych 1xN1
    :param a_values: lista parametrów "a" do sprawdzenia
    :param b_values: lista parametrów "b" do sprawdzenia
    :return: krotka (best_error, best_a, best_b, errors), gdzie "best_error" to
        najniższy osiągnięty błąd, "best_a" i "best_b" to para parametrów
        "a" i "b" dla której błąd był najniższy, a "errors" - lista wartości
        błędów dla wszystkich kombinacji wartości "a" i "b" (w kolejności
        iterowania najpierw po "a_values" [pętla zewnętrzna], a następnie
        "b_values" [pętla wewnętrzna]).
    """

    def errorAB(a, b):
        p_x_y = estimate_p_x_y_nb(X_train, y_train, a, b)
        p_y_x = p_y_x_nb(p_y, p_x_y, X_val)
        return classification_error(p_y_x, y_val)

    p_y = estimate_a_priori_nb(y_train)
    errors = np.array([[errorAB(a, b) for b in b_values] for a in a_values])
    (a_idx, b_idx) = np.unravel_index(np.argmin(errors), errors.shape)
    return (errors[a_idx, b_idx], a_values[a_idx], b_values[b_idx], errors)