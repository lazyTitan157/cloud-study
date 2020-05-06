kubectl run nginx --image=nginx-test --port=80

kubectl get deployments

kubectl expose deployment/nginx --type="NodePort" --port 80

kubectl scale deployment nginx --replicas=20
kubectl get deployments
kubectl get pods -o wide
kubectl describe deployments/nginx
