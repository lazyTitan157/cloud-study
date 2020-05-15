## CPU 3분 평균 사용률이 90%넘으면 자신의 Slack의 xalert이라는 체널에 표시되도록 하시오.
* 참고할 PromQL expr: 100 - (avg by (instance) (irate(node_cpu_seconds_total{job='node_exporter',mode='idle'}[5m])) * 100) > 95
* prometheus.yml,alertmanager.yml,alert.rules 파일과 캡쳐 화면을 ./sol4 폴더에 저장하시오.
  - AlertManager는 s2에 설치, 기존 Prometheus의 셋팅은 삭제할 것.
  - https://github.com/lazyTitan157/prometheusTest/tree/master/sol4 
* Alert 실행 결과(Slack UI)
 ![6 4](https://user-images.githubusercontent.com/8167433/82030296-38986a00-96d3-11ea-920a-2f809a3479b1.png)
