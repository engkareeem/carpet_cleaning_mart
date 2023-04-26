package com.carpetcleaningmart.Utils;

import com.carpetcleaningmart.model.Customer;
import com.carpetcleaningmart.model.Order;
import com.carpetcleaningmart.model.Worker;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBApi {
    public static Connection connection = DBConnection.getConnection();

    //===    Helper Functions Section    ===\
    private static Worker getWorkerFromRow(ResultSet resultSet) throws SQLException {
        Worker worker = new Worker();

        worker.setId(resultSet.getString("WorkerId"));
        worker.setName(resultSet.getString("WorkerName"));
        worker.setPhone(resultSet.getString("WorkerPhone"));
        worker.setAddress(resultSet.getString("WorkerAddress"));
        worker.setEmail(resultSet.getString("WorkerEmail"));
        worker.setType(resultSet.getString("WorkerType"));

        return worker;
    }

    private static Customer getCustomerFromRow(ResultSet resultSet) throws SQLException {
        Customer customer = new Customer();

        customer.setId(resultSet.getString("CustomerId"));
        customer.setName(resultSet.getString("CustomerName"));
        customer.setPhone(resultSet.getString("CustomerPhone"));
        customer.setAddress(resultSet.getString("CustomerEmail"));
        customer.setEmail(resultSet.getString("WorkerEmail"));
        customer.setTimesServed(resultSet.getInt("CustomerTimesServed"));

        return customer;
    }

    private static Order getOrderFromRow(ResultSet resultSet) throws SQLException {
        Order order = new Order();

        order.setId(resultSet.getString("OrderId"));
        order.setName(resultSet.getString("OrderName"));
        order.setDescription(resultSet.getString("OrderDescription"));
        order.setStatus(resultSet.getString("OrderStatus"));
        order.setCategory(resultSet.getString("OrderCategory"));
        order.setPrice(resultSet.getDouble("OrderPrice"));

        return order;
    }

    //===    Worker Section    ===\

    public static void addWorker(String workerName, String workerPhone, String workerAddress, String workerEmail, String workerType){

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("insert into Worker(WorkerName, WorkerPhone, WorkerAddress, WorkerEmail, WorkerType) values('%s', '%s', '%s', '%s', '%s')", workerName, workerPhone, workerAddress, workerEmail, workerType));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateWorker(String workerId, String workerName, String workerPhone, String workerAddress, String workerEmail, String workerType){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("update Worker set WorkerName = '%s', WorkerPhone = '%s', WorkerAddress = '%s', WorkerEmail = '%s', WorkerType = '%s' where WorkerId = '%s'", workerName, workerPhone, workerAddress, workerEmail, workerType, workerId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteWorker(String workerId){
        try {
            Statement statement = connection.createStatement();
            connection.setAutoCommit(false);
            statement.executeUpdate(String.format("delete from Worker where WorkerId = '%s'", workerId));
            statement.executeUpdate(String.format("update WorksOnOrder set Status = 'WAITING' where WorkerId = '%s' and Status = 'IN_TREATMENT'", workerId));
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //===    Customer Section    ===\

    public static void addCustomer(String customerName, String customerPhone, String customerAddress, String customerEmail){

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("insert into Customer(CustomerName, CustomerPhone, CustomerAddress, CustomerEmail, CustomerTimesServed) values('%s', '%s', '%s', '%s', 0)", customerName, customerPhone, customerAddress, customerEmail));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateCustomer(String customerId, String customerName, String customerPhone, String customerAddress, String customerEmail,Integer CustomerTimesServed ){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("update Customer set CustomerName = '%s', CustomerPhone = '%s', CustomerAddress = '%s', CustomerEmail = '%s', CustomerTimesServed = %d where CustomerId = '%s'", customerName, customerPhone, customerAddress, customerEmail, CustomerTimesServed, customerId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void incrementCustomerTimesServed(String customerId){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("update Customer set CustomerTimesServed = CustomerTimesServed + 1 where CustomerId = '%s'", customerId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCustomer(String customerId){
        try {
            Statement statement = connection.createStatement();
            connection.setAutoCommit(false);
            statement.executeUpdate(String.format("delete from Customer where CustomerId = '%s'", customerId));
            statement.executeUpdate(String.format("delete from WorksOnOrder where CustomerId = '%s'", customerId));
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //===    Order Section    ===\

    public static void addOrder(String orderName, String orderDescription, String orderCategory, Double orderPrice, String customerId){

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("insert into 'Order'(OrderName, OrderDescription, OrderCategory, OrderPrice, CustomerId) values('%s', '%s', '%s', %f, '%s')", orderName, orderDescription, orderCategory, orderPrice, customerId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateOrder(String orderId, String orderName, String orderDescription, String orderCategory, Double orderPrice, String customerId ){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("update 'Order' set OrderName = '%s', OrderDescription = '%s', OrderCategory = '%s', OrderPrice = %f, CustomerId = '%s' where OrderId = '%s'", orderName, orderDescription, orderCategory, orderPrice, customerId, orderId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteOrder(String orderId){
        try {
            Statement statement = connection.createStatement();
            connection.setAutoCommit(false);
            statement.executeUpdate(String.format("delete from 'Order' where OrderId = '%s'", orderId));
            statement.executeUpdate(String.format("delete from WorksOnOrder where OrderId = '%s'", orderId));
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //===    Workers Utility Section    ===\
    public static ArrayList<Worker> getAllWorkers() {
        ArrayList<Worker> workers = new ArrayList<>();
        try {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Worker where WorkerType = 'EMPLOYEE'");
            while (resultSet.next()) {
                workers.add(getWorkerFromRow(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return workers;
    }

    public static ArrayList<Worker> getFreeWorkers() {
        ArrayList<Worker> freeWorkers = new ArrayList<>();
        try {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select distinct Worker.* from Worker left join 'Order' on Worker Worker.WorkerId = WorksOnOrder.WorkerId where (WorksOnOrder.WorkerId is null or WorksOnOrder.Status = 'COMPLETE') and Worker.WorkerType = 'EMPLOYEE'");
            while (resultSet.next()) {
                freeWorkers.add(getWorkerFromRow(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return freeWorkers;
    }

    public static void assignWorkerToAnOrder(String workerId, String orderId){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("update 'Order' set OrderStatus = 'IN_TREATMENT', WorkerId = '%s' where OrderId = '%s'", workerId, orderId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void finishWorkOnAnOrder(String orderId){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("update 'Order' set OrderStatus = 'COMPLETE' where OrderId = '%s'", orderId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
