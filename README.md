# spring-boot-journal
spring-boot-journal 앱과 kubernetes 배포 구성

※사전작업
Helm을 이용한 Kubernetes에 Jenkins 설치하기

Jenkins -> 새로운 Item -> 
![] (doc/images/jenkins_new_item01.PNG)

배포 결과 확인
```shell
$ kubectl get deploy -n journal
NAME                  DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE
spring-boot-journal   1         1         1            1           8h
```

웹에서 확인하기
```shell
$ export POD_NAME=$(kubectl get pods -n journal -o jsonpath="{.items[0].metadata.name}")
$ kubectl port-forward -n journal $POD_NAME 12000:12000 >> /dev/null &
[1] 3928
```

POD 확인
```shell
    State:          Running
      Started:      Fri, 23 Aug 2019 22:24:35 +0900
    Ready:          True
    Restart Count:  0
    Readiness:      http-get http://:12000/health delay=0s timeout=1s period=10s #success=1 #failure=3
    Environment:    <none>
    Mounts:
      /var/run/secrets/kubernetes.io/serviceaccount from default-token-hwfpk (ro)
Conditions:
  Type              Status
  Initialized       True
  Ready             True
  ContainersReady   True
  PodScheduled      True
Volumes:
  default-token-hwfpk:
    Type:        Secret (a volume populated by a Secret)
    SecretName:  default-token-hwfpk
    Optional:    false
QoS Class:       BestEffort
Node-Selectors:  <none>
Tolerations:     node.kubernetes.io/not-ready:NoExecute for 300s
                 node.kubernetes.io/unreachable:NoExecute for 300s
Events:
  Type     Reason     Age                    From                                                Message
  ----     ------     ----                   ----                                                -------
  Normal   Scheduled  2m43s                  default-scheduler                                   Successfully assigned journal/spring-boot-journal-854cbd9fcf-vkdv9 to gke-jenkins-cd-default-pool-eb065e61-7xs1
  Normal   Pulling    2m41s                  kubelet, gke-jenkins-cd-default-pool-eb065e61-7xs1  pulling image "gcr.io/eunbyul/spring-boot-journal:master.2"
  Normal   Pulled     2m38s                  kubelet, gke-jenkins-cd-default-pool-eb065e61-7xs1  Successfully pulled image "gcr.io/eunbyul/spring-boot-journal:master.2"
  Normal   Created    2m38s                  kubelet, gke-jenkins-cd-default-pool-eb065e61-7xs1  Created container
  Normal   Started    2m38s                  kubelet, gke-jenkins-cd-default-pool-eb065e61-7xs1  Started container
  Warning  Unhealthy  2m20s (x2 over 2m30s)  kubelet, gke-jenkins-cd-default-pool-eb065e61-7xs1  Readiness probe failed: Get http://10.32.1.131:12000/health: dial tcp 10.32.1.131:12000: connect: connection refused
  ```
