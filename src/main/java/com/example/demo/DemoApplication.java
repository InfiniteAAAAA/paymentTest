package com.example.demo;

import com.example.demo.exception.ParameterValidateException;
import com.example.demo.validate.impl.ValidateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.util.*;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static Map<String,Double> paymentInfoMap = new HashMap<>();
    private static Map<String,Double> exchangeRateMap = new HashMap<>();
    private static final String CONFIG_FILENAME = "D:\\paymentTest\\exchange_rate_config.txt";
    private static final String DATA_FILENAME = "D:\\paymentTest\\exchange_rate_data.txt";
    static {
        readFileByLines(CONFIG_FILENAME,"config");
        readFileByLines(DATA_FILENAME,"data");
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    System.exit(0);
                    break;
                }
                System.out.println("Sample output:\n");
                paymentInfoMap.forEach((currency,amount) ->
                        System.out.println(new StringBuffer()
                        .append(currency)
                        .append(" ")
                        .append(amount)
                        .append("(USD ")
                        .append(exchangeRateMap.get(currency)*amount)
                        .append(")")
                        .toString()));
            }
        }).start();

        Scanner scan = new Scanner(System.in);
        System.out.println("Sample input:\n");
        while (true){
            String line = scan.nextLine();
            if (line.equals("quit")) {
                System.out.println("退出程序");
                System.exit(0);
                break;
            }
            String[] strArray = line.split(" ");
            try {
                ValidateUtils.ifTrueThrowException(!line.contains(" "),"输入不正确");
                ValidateUtils.ifTrueThrowException(strArray.length != 2,"输入不正确");
                ValidateUtils.ifTrueThrowException(!paymentInfoMap.containsKey(strArray[0]),"不存在该汇率");
            } catch (ParameterValidateException e) {
                log.error("参数异常",e);
                continue;
            }

            Double amount = 0.0;
            try {
                amount = Double.parseDouble(strArray[1]);
            }catch (Exception e){
                log.error("输入不是数字",e);
                continue;
            }
            appendDataToFile(DATA_FILENAME,"\r\n"+line);
            Double sourceAmount = paymentInfoMap.get(strArray[0]);
            sourceAmount = sourceAmount+amount;
            paymentInfoMap.put(strArray[0],sourceAmount);
        }
    }

    public static void readFileByLines(String fileName,String flag) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                System.out.println(tempString);
                String[] strArray = tempString.split(" ");
                if ("config".equals(flag)) {
                    exchangeRateMap.put(strArray[0],Double.parseDouble(strArray[1]));
                    paymentInfoMap.put(strArray[0],0.0);
                }
                else if("data".equals(flag)) {
                    Double sourceAmount = paymentInfoMap.get(strArray[0]);
                    paymentInfoMap.put(strArray[0],sourceAmount!=null?sourceAmount+Double.parseDouble(strArray[1]):Double.parseDouble(strArray[1]));
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }


    public static void appendDataToFile(String fileName, String content) {
        try {
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
