package org.example.myjava.myclassloader;

import java.sql.*;

public class SPIClassLoaderTest {
    static final String USER = "root";
    static final String PASS = "123456";
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        //注册JDBC驱动程序
        // mysql8.0以上版本
        Class.forName("com.mysql.cj.jdbc.Driver");
//  Database credentials
        System.out.println("Connecting to database...");

        //在jdk1.8和jdk11环境下加载jdbc的加载器有所不同，
        //8时为启动类加载器，11为平台类加载器
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/xxl_job",USER,PASS);


        PreparedStatement preparedStatement = connection.prepareStatement("select * from xxl_job_log");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String executor_address = resultSet.getString("executor_address");
            String executor_handler = resultSet.getString("executor_handler");

            System.out.println(id+executor_address+executor_handler);
        }
        connection.close();
    }
}