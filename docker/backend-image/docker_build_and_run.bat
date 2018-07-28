DEL "%cd%\toBeDeployed\echo.war"
MOVE "%cd%\..\..\target\echo.war" "%cd%\toBeDeployed\echo.war"
docker build -t teamplaya/tomcatbitnami .
docker run -p 8080:8080 --env-file ./env.list teamplaya/tomcatbitnami