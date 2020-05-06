# 쿠버네티스 배포: Deployments 만들기
- kubectl run nginx --image=nginx --port=80
```
[root@node1 ~]# kubectl run nginx-test --image=docker.io/1azytitan157/nginx-test:2 --port=80
kubectl run --generator=deployment/apps.v1 is DEPRECATED and will be removed in a future version. Use kubectl run --generator=run-pod/v1 or kubectl create instead.
deployment.apps/nginx-test created

kubectl get deployments
```
# expose app publicly
```
kubectl expose deployment/nginx --type="NodePort" --port 80

kubectl describe services/nginx
```
# replicas set
```
kubectl scale deployment nginx --replicas=20

kubectl get deployments
kubectl get pods -o wide
kubectl describe deployments/nginx
```
