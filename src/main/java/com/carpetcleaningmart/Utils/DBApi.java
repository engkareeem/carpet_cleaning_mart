package com.carpetcleaningmart.Utils;

import com.carpetcleaningmart.model.Customer;
import com.carpetcleaningmart.model.Order;
import com.carpetcleaningmart.model.Worker;

import java.security.PublicKey;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

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
        customer.setAddress(resultSet.getString("CustomerAddress"));
        customer.setEmail(resultSet.getString("CustomerEmail"));
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
        order.setCustomerId(resultSet.getString("CustomerId"));
        return order;
    }

    //===    Worker Section    ===\

    public static void addWorker(Worker worker, String workerPassword){

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("insert into Worker(WorkerName, WorkerPhone, WorkerAddress, WorkerEmail, WorkerPassword, WorkerType) values('%s', '%s', '%s', '%s', '%s', '%s')", worker.getName(), worker.getPhone(), worker.getAddress(), worker.getEmail(), workerPassword, worker.getType()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateWorker(String workerId, Worker worker){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("update Worker set WorkerName = '%s', WorkerPhone = '%s', WorkerAddress = '%s', WorkerType = '%s' where WorkerId = '%s'", worker.getName(), worker.getPhone(), worker.getAddress(), worker.getType(), workerId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteWorker(String workerId){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("delete from Worker where WorkerId = '%s'", workerId));
            distributeWaitingOrders();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //===    Customer Section    ===\

    public static void addCustomer(Customer customer, String customerPassword){

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("insert into Customer(CustomerName, CustomerPhone, CustomerAddress, CustomerEmail, CustomerPassword, CustomerTimesServed) values('%s', '%s', '%s', '%s', '%s', 0)", customer.getName(), customer.getPhone(), customer.getAddress(), customer.getEmail(), customerPassword));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Customer getCustomer(String customerId){
        try {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("select * from Customer where CustomerId = '%s'", customerId));
            if(resultSet.next()){
                return getCustomerFromRow(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  new Customer();
    }

    public static void updateCustomer(String customerId ,Customer customer){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("update Customer set CustomerName = '%s', CustomerPhone = '%s', CustomerAddress = '%s' where CustomerId = '%s'", customer.getName(), customer.getPhone(), customer.getAddress(), customerId));
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

    public static void addOrder(Order order){

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("insert into  'Order' (OrderName, OrderDescription, OrderCategory, OrderPrice, CustomerId) values('%s', '%s', '%s', %f, '%s')", order.getName(), order.getDescription(), order.getCategory().toString(), order.getPrice(), order.getCustomerId()));
            distributeWaitingOrders();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static Order getOrder(String orderId){

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet =  statement.executeQuery(String.format("select * from 'Order' where OrderId = '%s'", orderId));
            if(resultSet.next()){
                return getOrderFromRow(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Order();
    }


    public static void updateOrder(String orderId, Order order){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("update 'Order' set OrderName = '%s', OrderDescription = '%s', OrderPrice = %f where OrderId = '%s'", order.getName(), order.getDescription(), order.getPrice(), orderId));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteOrder(String orderId){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("delete from 'Order' where OrderId = '%s'", orderId));
            distributeWaitingOrders();
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
            ResultSet resultSet = statement.executeQuery("select distinct Worker.* from Worker where Worker.WorkerId not in (select distinct 'Order'.WorkerId from 'Order' where 'Order'.OrderStatus = 'IN_TREATMENT' ) and Worker.WorkerType = 'EMPLOYEE'");
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
            Order order = getOrder(orderId);
            ResultSet resultSet = statement.executeQuery(String.format("select Customer.* from Customer where Customer.CustomerId in (select 'Order'.CustomerId from 'Order' where 'Order'.OrderId = '%s')", orderId));
            resultSet.next();
            Customer customer = getCustomerFromRow(resultSet);
            statement.executeUpdate(String.format("update 'Order' set OrderStatus = 'COMPLETE' where OrderId = '%s'", orderId));
            statement.executeUpdate(String.format("update Customer set CustomerTimesServed = CustomerTimesServed + 1 where CustomerId in (select 'Order'.CustomerId from 'Order' where 'Order'.OrderId = '%s')", orderId));
//            Notifier.sendEmail(customer, order);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //===    Orders Utility Section    ===\

    public static Order getWorkerCurrentOrder(String workerId) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("select * from 'Order' where WorkerId = '%s' and OrderStatus = 'IN_TREATMENT'", workerId));
            if (resultSet.next()){
                return getOrderFromRow(resultSet);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Order> getCustomerOrder(String customerId) {
        ArrayList<Order> orders = new ArrayList<>();
        try {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("select * from 'Order' where CustomerId = '%s'", customerId));
            while (resultSet.next()) {
                orders.add(getOrderFromRow(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public static ArrayList<Order> getAllOrders() {
        ArrayList<Order> orders = new ArrayList<>();
        try {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from 'Order'");
            while (resultSet.next()) {
                orders.add(getOrderFromRow(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public static ArrayList<Order> getWaitingOrders() {
        ArrayList<Order> waitingOrders = new ArrayList<>();
        try {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from 'Order' where OrderStatus = 'WAITING'");
            while (resultSet.next()) {
                waitingOrders.add(getOrderFromRow(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return waitingOrders;
    }


    // distribute waiting orders on free workers
    public static void distributeWaitingOrders(){
        ArrayList<Order> waitingOrders = getWaitingOrders();
        ArrayList<Worker> freeWorkers = getFreeWorkers();
        if(freeWorkers.isEmpty()) return;
        int index = 0;
        Random dice = new Random();
        for(Order order : waitingOrders){
            index = dice.nextInt(freeWorkers.size());
            assignWorkerToAnOrder(freeWorkers.get(index).getId(), order.getId());
            freeWorkers.remove(index);
        }
    }

    public static void cancelOrder(String orderId){
        deleteOrder(orderId);
    }


    //===    Auth Utility Section    ===\

    public static Boolean isWorker(String email){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("select * from Worker where WorkerEmail = '%s'", email));
            if(resultSet.next()) {
                return true;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Customer signUp(Customer customer, String password){
        DBApi.addCustomer(customer, password);

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select seq from sqlite_sequence where name = 'Customer'");
            return getCustomer(resultSet.getString("seq"));
        }catch (SQLException e) {
            e.printStackTrace();
        }


        return new Customer();
    }


    public static Worker logInAsWorker(String email, String password){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("select * from Worker where WorkerEmail = '%s'", email));
            if(resultSet.next()) {
                if(resultSet.getString("WorkerPassword").equals(password)){
                    return getWorkerFromRow(resultSet);
                }else{
                    System.out.println("ERROR: Wrong password entered, Please try again.");
                }
            }else{
                System.out.println("ERROR: User was not found, Please check your email.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Worker();
    }

    public static Customer loginAsCustomer(String email, String password){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("select * from Customer where CustomerEmail = '%s'", email));
            if(resultSet.next()) {
                if(resultSet.getString("CustomerPassword").equals(password)){
                    return getCustomerFromRow(resultSet);
                }else{
                    System.out.println("ERROR: Wrong password entered, Please try again.");
                }
            }else{
                System.out.println("ERROR: User was not found, Please check your email.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Customer();
    }





}
