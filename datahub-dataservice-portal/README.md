# datahub-dataservice-portal

City Hub 서비스 포탈의 전체 구성중 Back-end 서버에 해당 되며,
front (사용자, 관리자) 포탈에서 사용 되는 포탈 API 전용 서버입니다.

아래 전체프로젝트 구성이 모두 설치 및 구동이 되어야 정상적으로 동작됩니다.


##### **1. Profiles 구성**
```
local (로컬계)
dev 	(개발계)
test 	(품질계)

active profile : dev (구동 시 기본)
```

##### **2. Maven Build**
```
mvn clean package 
```
