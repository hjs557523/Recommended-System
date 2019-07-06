#coding = utf-8
import pandas as pd
import numpy as np
import math 
import random
import pickle
import os
import csv


#coding = utf-8
import pandas as pd
import numpy as np
import math 
import random
import pickle
import os
import csv

def ItemUserMatrix(root,result):
    user_dict = dict()              # user_id 的数据字典
    jd_dict = dict()                #jd_poi 的数据字典
    jd_users_dict = dict()          #每个jd 对应的 去旅游过并进行评论过的用户的id
    user_jds_dict = dict()          #每个user去过并评论的jd的poi
    n = 0                           # user_id 的数据字典中的数字排序
    m = -1                          #jd_poi 的数据字典中的数字排序
    mat = np.full((30000,3000),0.65)   #每一行代表用户对几个景点的评分数据
    rpath = os.path.join(root, 'new1.csv')
    with open(rpath, 'rb') as cf:                                                                    
        df = pd.DataFrame(pd.read_csv(cf, encoding="unicode_escape", lineterminator= '\n'))
    M = df.as_matrix()      #将Dataframe数据转换为矩阵形式
    for x in range(len(M)):
        jd = str(M[x][2])     #将每个景点的jd_poi提取出来并转换成字符串       
        if jd not in jd_dict.keys():
            m += 1
            jd_dict[jd] = m
        #创建jd_users_dict字典
        if m not in jd_users_dict.keys():                                                          
            jd_users_dict[m] = set()
                #读取user_id并传换成字符串，同时向jd_users_dict中添加用户
            I = str(M[x][0])
            if I not in user_jds_dict.keys():
                user_jds_dict[n] = set()
            #user数据字典赋值，并且对mat赋值
            if I not in user_dict.keys():                                                           
                user_dict[I] = n
                jd_users_dict[m].add(n)
                user_jds_dict[n].add(m)
                a = 0
                a = M[x][3]
                b = a/5         #用最大值对评分做个正则化
                mat[n][m] = b
                n += 1
                x += 1
            else:
                c = user_dict[I]
                jd_users_dict[m].add(c)
                user_jds_dict[c].add(m)
                a = 0
                a = M[x][3]
                b = a/5         #用最大值对评分做个正则化
                mat[c][m] = b
                x += 1 
        else:
            I = str(M[x][0])
                
            if I not in user_jds_dict.keys():
                user_jds_dict[n] = set()
            #user数据字典赋值，并且对mat赋值
            if I not in user_dict.keys():
                user_dict[I] = n
                jd_users_dict[m].add(n)
                user_jds_dict[n].add(m)
                a = 0
                a = M[x][3]
                b = a/5         #用最大值对评分做个正则化
                mat[n][m] = b
                n += 1
                x += 1
            else:
                c = user_dict[I]
                jd_users_dict[m].add(c)
                user_jds_dict[c].add(m)
                a = 0
                a = M[x][3]
                b = a/5         #用最大值对评分做个正则化
                mat[c][m] = b
                x += 1
    #将得到的数据写入文件
    print(n)
    print(m)
    user_dict_path = os.path.join(result, 'user_dict')
    jd_dict_path = os.path.join(result, 'jd_dict')
    jd_users_dict_path = os.path.join(result, 'jd_users_dict')
    user_jds_dict_path = os.path.join(result, 'user_jds_dict')
    ItemUserMatrix_path = os.path.join(result, 'ItemUserMatrix')
   
    with open(user_dict_path, 'wb') as uf:
        pickle.dump(user_dict, uf)
    with open(jd_dict_path, 'wb') as jf:
        pickle.dump(jd_dict, jf)
    with open(jd_users_dict_path, 'wb') as juf:
        pickle.dump(jd_users_dict, juf)
    with open(user_jds_dict_path, 'wb') as ujf:
        pickle.dump(user_jds_dict, ujf)
    np.save(ItemUserMatrix_path,mat)

def SelectNagativeSample(user, items,ItemUserMatrix): #挑选正负样本
    ret = dict()
    n = 0
    for i in items:
        ret[i] = 1
        n += 1
    I = ItemUserMatrix[user].argsort()
    for j in I:
        if j in items:
            continue
        ret[j] = 0
        n -= 1
        if n == 0:
            break
    return ret

def InitModel(result, F):
    user_factor_matrix_path = os.path.join(result, 'user_factor_matrix.npy')
    jd_factor_matrix_path = os.path.join(result, 'jd_factor_matrix.npy')
    ItemUserMatrix_path = os.path.join(result, 'ItemUserMatrix.npy')
    
    ItemUserMatrix = np.load(ItemUserMatrix_path)
    P = np.random.randn(F,len(ItemUserMatrix))  #* 0.01
    Q = np.random.randn(F,len(ItemUserMatrix[0])) #* 0.01
    
    np.save(user_factor_matrix_path, P)
    np.save(jd_factor_matrix_path, Q)


def predict(user, item, P, Q):
    P = np.transpose(P)
    r = (P[user] * Q[:,item]).sum() * 0.01
    return r

def LantentFactorModel(result, N, alpha, l):
    
    user_jds_dict_path = os.path.join(result, 'user_jds_dict')
    ItemUserMatrix_path = os.path.join(result, 'ItemUserMatrix.npy')
    user_factor_matrix_path = os.path.join(result, 'user_factor_matrix.npy')
    jd_factor_matrix_path = os.path.join(result, 'jd_factor_matrix.npy')
    
    user_items_dict = np.load(user_jds_dict_path)
    ItemUserMatrix = np.load(ItemUserMatrix_path)
    P = np.load(user_factor_matrix_path)
    Q = np.load(jd_factor_matrix_path)
    
    for step in range(0,N):
        for user,items in user_items_dict.items():
            samples = SelectNagativeSample(user, items, ItemUserMatrix)
            for item, rui in samples.items():
                eui = rui - predict(user, item, P, Q)
                for f in range(len(P)):
                    P[f][user] += alpha * (eui * Q[f][item] - (l * P[f][user]))
                    Q[f][item] += alpha * (eui * P[f][user] - (l * Q[f][item]))
        alpha *= 0.9
        print(str(step) + 'Done')
    
    np.save(user_factor_matrix_path, P)
    np.save(jd_factor_matrix_path, Q)


ItemUserMatrix(root,result)
InitModel(result, 100)

LantentFactorModel(result, 10, 0.02, 0.01)