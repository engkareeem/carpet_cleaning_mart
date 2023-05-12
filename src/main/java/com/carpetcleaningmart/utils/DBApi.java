package com.carpetcleaningmart.utils;

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

    public static String addWorker(Worker worker, String workerPassword) {

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("insert into Worker(WorkerName, WorkerPhone, WorkerAddress, WorkerEmail, WorkerPassword, WorkerType) values('%s', '%s', '%s', '%s', '%s', '%s')", worker.getName(), worker.getPhone(), worker.getAddress(), worker.getEmail(), workerPassword, worker.getType()));
            ResultSet resultSet = statement.executeQuery("select seq from sqlite_sequence where name = 'Worker'");
            distributeWaitingOrders();
            if (resultSet.next()) {
                return resultSet.getString("seq");
            }
        } catch (SQLException e) {
           Printer.printError(e.getMessage());
        }
        return null;
    }

    public static void updateWorker(String workerId, Worker worker) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("update Worker set WorkerName = '%s', WorkerPhone = '%s', WorkerAddress = '%s', WorkerType = '%s' where WorkerId = '%s'", worker.getName(), worker.getPhone(), worker.getAddress(), worker.getType(), workerId));
        } catch (SQLException e) {
           Printer.printError(e.getMessage());
        }
    }

    public static void deleteWorker(String workerId) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("delete from Worker where WorkerId = '%s'", workerId));
            distributeWaitingOrders();
        } catch (SQLException e) {
           Printer.printError(e.getMessage());
        }
    }

    //===    Customer Section    ===\

    public static String addCustomer(Customer customer, String customerPassword){

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("insert into Customer(CustomerName, CustomerPhone, CustomerAddress, CustomerEmail, CustomerPassword, CustomerTimesServed) values('%s', '%s', '%s', '%s', '%s', 0)", customer.getName(), customer.getPhone(), customer.getAddress(), customer.getEmail(), customerPassword));
            ResultSet resultSet = statement.executeQuery("select seq from sqlite_sequence where name = 'Customer'");
            if (resultSet.next()) {
                return resultSet.getString("seq");
            }
        } catch (SQLException e) {
           Printer.printError(e.getMessage());
        }
        return null;
    }

    public static Customer getCustomer(String customerId) {
        try {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("select * from Customer where CustomerId = '%s'", customerId));
            if (resultSet.next()) {
                return getCustomerFromRow(resultSet);
            }
        } catch (SQLException e) {
           Printer.printError(e.getMessage());
        }
        return new Customer();
    }

    public static void updateCustomer(String customerId, Customer customer) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("update Customer set CustomerName = '%s', CustomerPhone = '%s', CustomerAddress = '%s' where CustomerId = '%s'", customer.getName(), customer.getPhone(), customer.getAddress(), customerId));
        } catch (SQLException e) {
           Printer.printError(e.getMessage());
        }
    }

    public static void incrementCustomerTimesServed(String customerId) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("update Customer set CustomerTimesServed = CustomerTimesServed + 1 where CustomerId = '%s'", customerId));
        } catch (SQLException e) {
           Printer.printError(e.getMessage());
        }
    }

    public static void deleteCustomer(String customerId) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("delete from Customer where CustomerId = '%s'", customerId));
        } catch (SQLException e) {
           Printer.printError(e.getMessage());
        }
    }

    public static void deleteTestCustomer() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Customer where CustomerEmail = 'customer@gmail.com'");
            resultSet.next();
            String customerId = resultSet.getString("CustomerId");
            statement.executeUpdate(String.format("delete from Customer where CustomerId = '%s'", customerId));
        } catch (SQLException e) {
           Printer.printError(e.getMessage());
        }
    }

    //===    Order Section    ===\


    public static String addOrder(Order order, boolean distributeOrder) {

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("insert into  'Order' (OrderName, OrderDescription, OrderCategory, OrderPrice, CustomerId) values('%s', '%s', '%s', %f, '%s')", order.getName(), order.getDescription(), order.getCategory().toString(), order.getPrice(), order.getCustomerId()));
            ResultSet resultSet = statement.executeQuery("select seq from sqlite_sequence where name = 'Order'");
            if(distributeOrder) distributeWaitingOrders();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
           Printer.printError(e.getMessage());
        }
        return null;
    }


    public static Order getOrder(String orderId) {

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("select * from 'Order' where OrderId = '%s'", orderId));
            if (resultSet.next()) {
                return getOrderFromRow(resultSet);
            }

        } catch (SQLException e) {
           Printer.printError(e.getMessage());
        }
        return new Order();
    }


    public static void updateOrder(String orderId, Order order) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("update 'Order' set OrderName = '%s', OrderDescription = '%s', OrderPrice = %f where OrderId = '%s'", order.getName(), order.getDescription(), order.getPrice(), orderId));

        } catch (SQLException e) {
           Printer.printError(e.getMessage());
        }
    }

    public static void deleteOrder(String orderId) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("delete from 'Order' where OrderId = '%s'", orderId));
            distributeWaitingOrders();
        } catch (SQLException e) {
           Printer.printError(e.getMessage());
        }
    }

    //===    Customer Utility Section    ===\

    public static Integer getCustomersServedCount() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select sum(CustomerTimesServed) from Customer");
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
           Printer.printError(e.getMessage());
        }
        return 0;
    }


    //===    Workers Utility Section    ===\

    public static Worker getWorkerById(String id) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("select * from Worker where WorkerId = '%s'", id));
            if (resultSet.next()) {
                return getWorkerFromRow(resultSet);
            }
        } catch (SQLException e) {
           Printer.printError(e.getMessage());
        }
        return null;
    }

    public static void deleteTestWorker() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("delete from Worker where WorkerEmail = 'worker@gmail.com'");
        } catch (SQLException e) {
           Printer.printError(e.getMessage());
        }
    }


    public static Worker getTestWorker() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Worker where WorkerType = 'EMPLOYEE' and WorkerEmail = 'worker@gmail.com'");
            if (resultSet.next()) {
                return getWorkerFromRow(resultSet);
            }
        } catch (SQLException e) {
           Printer.printError(e.getMessage());
        }
        return null;
    }

    public static Worker getLastWorker() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Worker where WorkerType = 'EMPLOYEE' order by WorkerId desc limit 1");
            if (resultSet.next()) {
                return getWorkerFromRow(resultSet);
            }
        } catch (SQLException e) {
           Printer.printError(e.getMessage());
        }
        return null;
    }

    public static Order getWorkerCurrentOrder(String workerId) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("select * from 'Order' where WorkerId = '%s' and OrderStatus = 'IN_TREATMENT'", workerId));
            if (resultSet.next()) {
                return getOrderFromRow(resultSet);
            }
        } catch (SQLException e) {
           Printer.printError(e.getMessage());
        }
        return null;
    }
    public static ArrayList<Order> getWorkerPreviousOrders(String workerId) {
        ArrayList<Order> orders = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("select * from 'Order' where WorkerId = '%s' and OrderStatus <> 'IN_TREATMENT'", workerId));
            while (resultSet.next()) {
                orders.add(getOrderFromRow(resultSet));
            }
        }catch (SQLException e) {
           Printer.printError(e.getMessage());
        }
        return orders;
    }

    public static ArrayList<Worker> getAllWorkers() {
        ArrayList<Worker> workers = new ArrayList<>();
        try {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Worker where WorkerType = 'EMPLOYEE'");
            while (resultSet.next()) {
                workers.add(getWorkerFromRow(resultSet));
            }
        } catch (SQLException e) {
           Printer.printError(e.getMessage());
        }
        return workers;
    }

    public static Integer getWorkersCount() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select count(*) from Worker where WorkerType = 'EMPLOYEE'");
            return resultSet.getInt(1);
        } catch (SQLException e) {
           Printer.printError(e.getMessage());
        }
        return 0;
    }

    public static ArrayList<Worker> searchForWorkers(String workerName) {
        ArrayList<Worker> workers = new ArrayList<>();
        try {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Worker where WorkerType = 'EMPLOYEE'");
            while (resultSet.next()) {
                workers.add(getWorkerFromRow(resultSet));
            }
        } catch (SQLException e) {
           Printer.printError(e.getMessage());
        }
        return workers;
    }

    public static ArrayList<Worker> getFreeWorkers() {
        ArrayList<Worker> freeWorkers = new ArrayList<>();
        try {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select distinct Worker.* from Worker where Worker.WorkerId not in (select 'Order'.WorkerId from 'Order' where 'Order'.OrderStatus = 'IN_TREATMENT' ) and Worker.WorkerType = 'EMPLOYEE'");
            while (resultSet.next()) {
                freeWorkers.add(getWorkerFromRow(resultSet));
            }
        } catch (SQLException e) {
           Printer.printError(e.getMessage());
        }
        return freeWorkers;
    }

    public static void assignWorkerToAnOrder(String workerId, String orderId) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("update 'Order' set OrderStatus = 'IN_TREATMENT', WorkerId = '%s' where OrderId = '%s'", workerId, orderId));
        } catch (SQLException e) {
           Printer.printError(e.getMessage());
        }
    }

    public static void finishWorkOnAnOrder(String orderId) {
        try {
            Statement statement = connection.createStatement();
            Order order = getOrder(orderId);
            ResultSet resultSet = statement.executeQuery(String.format("select Customer.* from Customer where Customer.CustomerId in (select 'Order'.CustomerId from 'Order' where 'Order'.OrderId = '%s')", orderId));
            resultSet.next();
            Customer customer = getCustomerFromRow(resultSet);
            statement.executeUpdate(String.format("update 'Order' set OrderStatus = 'COMPLETE' where OrderId = '%s'", orderId));
            statement.executeUpdate(String.format("update Customer set CustomerTimesServed = CustomerTimesServed + 1 where CustomerId in (select 'Order'.CustomerId from 'Order' where 'Order'.OrderId = '%s')", orderId));
            distributeWaitingOrders();
            Notifier.sendEmail(customer, order);
        } catch (SQLException e) {
           Printer.printError(e.getMessage());
        }
    }

    //===    Orders Utility Section    ===\


    public static Integer getOrderStatusCount(Order.Status orderStatus) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("select count(*) from 'Order' where OrderStatus = '%s'", orderStatus.toString().toUpperCase()));
            return resultSet.getInt(1);
        } catch (SQLException e) {
           Printer.printError(e.getMessage());
        }
        return 0;
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
           Printer.printError(e.getMessage());
        }
        return orders;
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
           Printer.printError(e.getMessage());
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
           Printer.printError(e.getMessage());
        }
        return waitingOrders;
    }


    // distribute waiting orders on free workers
    public static void distributeWaitingOrders() {
        ArrayList<Order> waitingOrders = getWaitingOrders();
        ArrayList<Worker> freeWorkers = getFreeWorkers();
        if (freeWorkers.isEmpty()) return;
        int index = 0;

        for (Order order : waitingOrders) {
            if(index >= freeWorkers.size()) break;
            assignWorkerToAnOrder(freeWorkers.get(index).getId(), order.getId());
            freeWorkers.remove(index);
            index++;
        }
    }

    public static void cancelOrder(String orderId) {
        deleteOrder(orderId);
    }


    //===    Auth Utility Section    ===\

    public static Boolean isWorker(String email) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("select * from Worker where WorkerEmail = '%s'", email));
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
           Printer.printError(e.getMessage());
        }
        return false;
    }

    public static boolean checkIfEmailUsed(String email) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("select * from Customer where CustomerEmail = '%s'", email));
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
           Printer.printError(e.getMessage());
        }
        return false;
    }

    public static Customer signUp(Customer customer, String password) {
        DBApi.addCustomer(customer, password);

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select seq from sqlite_sequence where name = 'Customer'");
            return getCustomer(resultSet.getString("seq"));
        } catch (SQLException e) {
           Printer.printError(e.getMessage());
        }


        return null;
    }


    public static Worker logInAsWorker(String email, String password) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("select * from Worker where WorkerEmail = '%s'", email));
            if (resultSet.next()) {
                if (resultSet.getString("WorkerPassword").equals(password)) {
                    return getWorkerFromRow(resultSet);
                } else {
                    Interrupt.printError("Wrong password entered, Please try again.");
                }
            } else {
                Interrupt.printError("User was not found, Please check your email.");
            }
        } catch (SQLException e) {
           Printer.printError(e.getMessage());
        }
        return null;
    }

    public static Customer loginAsCustomer(String email, String password) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("select * from Customer where CustomerEmail = '%s'", email));
            if (resultSet.next()) {
                if (resultSet.getString("CustomerPassword").equals(password)) {
                    return getCustomerFromRow(resultSet);
                } else {
                    Interrupt.printError("Wrong password entered, Please try again.");
                }
            } else {
                Interrupt.printError("User was not found, Please check your email.");
            }
        } catch (SQLException e) {
           Printer.printError(e.getMessage());
        }
        return null;
    }


}
