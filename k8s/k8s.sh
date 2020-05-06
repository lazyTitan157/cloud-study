kubectl run nginx-test-latest --image=nginx-test --port=80

kubectl get deployments

kubectl expose deployment/nginx-test-latest --type="NodePort" --port 80

kubectl scale deployment nginx-test-latest --replicas=20
kubectl get deployments
kubectl get pods -o wide
kubectl describe deployments/nginx-test-latest
