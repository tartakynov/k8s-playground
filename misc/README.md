`nginx.conf` contains nginx configuration file built by ingress-nginx.

To extract the file I used the following commands
```console
foo@bar:~$ kubectl get pods -n ingress-nginx
NAME                                       READY   STATUS      RESTARTS   AGE
ingress-nginx-admission-create-v9rw7       0/1     Completed   0          25d
ingress-nginx-admission-patch-f7nkt        0/1     Completed   1          25d
ingress-nginx-controller-c4f944d4d-klwmw   1/1     Running     2          25d
foo@bar:~$ kubectl exec ingress-nginx-controller-c4f944d4d-klwmw -n ingress-nginx -- cat /etc/nginx/nginx.conf
```
