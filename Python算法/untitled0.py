import numpy as np
import os
import random
import sys
def Recommendation(user_ID, K, result=r'D:\Documents\Desktop\666\人生地图\Python算法'):
    
    user_id = user_ID + '.0'
    
    jd_dict_path = os.path.join(result, 'jd_dict')
    user_dict_path = os.path.join(result, 'user_dict')
    user_factor_matrix_path = os.path.join(result, 'user_factor_matrix.npy')
    jd_factor_matrix_path = os.path.join(result, 'jd_factor_matrix.npy')
    
    jd_dict = np.load(jd_dict_path,allow_pickle=True)
    user_dict = np.load(user_dict_path,allow_pickle=True)
    P = np.load(user_factor_matrix_path,allow_pickle=True)
    Q = np.load(jd_factor_matrix_path,allow_pickle=True)

    user = random.randint(0,1000)
    for k, v in user_dict.items():
        if str(k) == user_id:
            user = v
        
    rank = dict()
    for i in range(2541):
        rank[i] = (np.multiply(P[:,user], Q[:,i])).sum()
    a = zip(rank.values(), rank.keys())
    b = list(sorted(a, reverse = True))   
    c = b[0:K]
    rank = list()
    for m in range(K):
        jn = c[m][1]
        for k, v in jd_dict.items():
            if v == jn:
                K = k[:-2]
                rank.append(K)
    return rank

rank = Recommendation(str(sys.argv[1]),int(sys.argv[2]))
print(rank)