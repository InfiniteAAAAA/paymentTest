环境: jdk1.8

测试用例:

在把paymentTest放到D盘根目录下，编译打包：mvn clean package

运行：java -Xms128m -Xmx512m -jar paymentTest-0.0.1-SNAPSHOT.jar 2>&1 &

功能：
1、通过修改exchange_rate_config.txt可以修改各货币汇率。
2、修改exchange_rate_data.txt初始化数据，将会在项目启动时加载文件。
3、通过键盘键入货币三字码和金额并进行计算，同时键入的货币和金额将会持久化在exchange_rate_data.txt文件中。
4、每分钟输出一次计算结果
5、键入quit结束程序