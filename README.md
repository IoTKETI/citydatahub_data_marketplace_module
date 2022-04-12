# citydatahub_data_marketplace
Data Marketplace Module of City Data Hub

This is an open API of Data Service Module. 
    The data service module API is a module suitable for the service that commercializes and distributes data from the data hub. You can find out more about City Data Hub at
    [http://citydatahub.kr/](http://citydatahub.kr/)


##### **1. 전체 프로젝트 구성**
``` 
* frontend (egovframework)
	1. 이용 및 제공자 전용 포탈
		=> datahub-dataservice-userportal
	2. 운영 및 관리 전용 포탈
		=> datahub-dataservice-adminportal

* backend (spring boot)
	1. Portal API
		=> datahub-dataservice-portal
	2. Notifier 
		=> datahub-dataservice-notifier
	3. Dataset Provider
		=> datahub-dataservice-dataset-provider
```

##### **2. 기본 설정 포트 (Spring boot)**
```
datahub-dataservice-portal (::7910)
datahub-dataservice-notifier (::7940)
datahub-dataservice-dataset-provider (::7930)
```


##### **3. 사용 버전**
```
JAVA          -> 1.8
PostgreSQL    -> 10.10
Spring Boot   -> 2.0.5.RELEASE
Egovframework -> 3.8.0
Apache Maven  -> 3.6.3
```

