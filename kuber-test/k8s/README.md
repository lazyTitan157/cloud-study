# 쿠버네티스 배포: Deployments 만들기
```
[root@node1 ~]# kubectl run nginx-test-latest --image=nginx-test --port=80
kubectl run --generator=deployment/apps.v1 is DEPRECATED and will be removed in a future version. Use kubectl run --generator=run-pod/v1 or kubectl create instead.
deployment.apps/nginx-test-latest created
[root@node1 ~]# kubectl get deployments
NAME                READY   UP-TO-DATE   AVAILABLE   AGE
nginx               1/1     1            1           7h25m
nginx-test          0/1     1            0           25m
nginx-test-latest   0/1     1            0           17s
```
# expose app publicly
```
[root@node1 ~]# kubectl expose deployment/nginx-test-latest --type="NodePort" --port 80
service/nginx-test-latest exposed

[root@node1 ~]# kubectl describe services/nginx-test-latest
Name:                     nginx-test-latest
Namespace:                default
Labels:                   run=nginx-test-latest
Annotations:              <none>
Selector:                 run=nginx-test-latest
Type:                     NodePort
IP:                       10.254.62.107
Port:                     <unset>  80/TCP
TargetPort:               80/TCP
NodePort:                 <unset>  31260/TCP
Endpoints:
Session Affinity:         None
External Traffic Policy:  Cluster
Events:                   <none>
```
# replicas set
```
[root@node1 ~]# kubectl scale deployment nginx-test-latest --replicas=20
deployment.extensions/nginx-test-latest scaled

[root@node1 ~]# kubectl get deployments
NAME                READY   UP-TO-DATE   AVAILABLE   AGE
nginx               1/1     1            1           7h26m
nginx-test          0/1     1            0           27m
nginx-test-latest   0/20    20           0           116s

[root@node1 ~]# kubectl describe deployments/nginx-test-latest
Name:                   nginx-test-latest
Namespace:              default
CreationTimestamp:      Wed, 06 May 2020 17:22:56 +0800
Labels:                 run=nginx-test-latest
Annotations:            deployment.kubernetes.io/revision: 1
Selector:               run=nginx-test-latest
Replicas:               20 desired | 20 updated | 20 total | 0 available | 20 unavailable
StrategyType:           RollingUpdate
MinReadySeconds:        0
RollingUpdateStrategy:  25% max unavailable, 25% max surge
Pod Template:
  Labels:  run=nginx-test-latest
  Containers:
   nginx-test-latest:
    Image:        nginx-test
    Port:         80/TCP
    Host Port:    0/TCP
    Environment:  <none>
    Mounts:       <none>
  Volumes:        <none>
Conditions:
  Type           Status  Reason
  ----           ------  ------
  Available      False   MinimumReplicasUnavailable
  Progressing    True    ReplicaSetUpdated
OldReplicaSets:  <none>
NewReplicaSet:   nginx-test-latest-5bc7b95695 (20/20 replicas created)
Events:
  Type    Reason             Age    From                   Message
  ----    ------             ----   ----                   -------
  Normal  ScalingReplicaSet  2m25s  deployment-controller  Scaled up replica set nginx-test-latest-5bc7b95695 to 1
  Normal  ScalingReplicaSet  45s    deployment-controller  Scaled up replica set nginx-test-latest-5bc7b95695 to 20

```
* kubectl get pods -o wide
