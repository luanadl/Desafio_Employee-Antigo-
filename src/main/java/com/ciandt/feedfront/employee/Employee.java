package com.ciandt.feedfront.employee;

import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;
import com.ciandt.feedfront.excecoes.EmailInvalidoException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;

import java.util.*;

import java.io.*;
import java.util.stream.Collectors;

public class Employee implements Serializable{
    private String id;
    private String nome;
    private String sobrenome;
    private String email;

    public Employee() {
    }

    public Employee(String nome, String sobrenome, String email) throws ComprimentoInvalidoException {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
    }

    private static final List<Employee> listOfEmployee = new ArrayList<Employee>();

    private static void addNewEmployee(Employee employee)
    {
        listOfEmployee.add(employee);
    }

    public static List<Employee> getList()
    {
        return Collections.unmodifiableList(listOfEmployee);
    }

    public static boolean salvarEmployee(Employee employee) throws Exception, EmailInvalidoException {

        addNewEmployee(employee);

        List<Employee> listOfEmployee = getList();

        String path = "file.txt";

        File file = new File(path);

        if(! file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        try{
            FileOutputStream fileOutput = new FileOutputStream(file);
            ObjectOutputStream objOutput = new ObjectOutputStream(fileOutput);

            objOutput.writeObject(listOfEmployee);

            objOutput.flush();
            fileOutput.flush();

            objOutput.close();
            fileOutput.close();

            return true;

        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static Employee atualizarEmployee(Employee employee) throws ArquivoException, EmailInvalidoException {
        return null;
    }

    public static List<Employee> listarEmployees() throws IOException, ClassNotFoundException {

        String path = "file.txt";

        File file = new File(path);

        try {
            FileInputStream fileInput = new FileInputStream(file);
            ObjectInputStream objInput = new ObjectInputStream(fileInput);

            Object object = objInput.readObject();
            List<Employee> list = (List<Employee>) object;

            objInput.close();
            fileInput.close();

            return list;

        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }

    }

    public static Employee buscarEmployee(String id) throws IOException, EmployeeNaoEncontradoException, ClassNotFoundException, ComprimentoInvalidoException {

        List<Employee> listOfEmployee = listarEmployees();

        for (Employee employee : listOfEmployee) {
            if (id.equals(employee.getId().toString())) {
                return employee;
            } else {
                throw new EmployeeNaoEncontradoException("not found");
            }
        }
        return null;

    }

    public static void apagarEmployee(String id) throws IOException, EmployeeNaoEncontradoException, ClassNotFoundException {

        List<Employee> listOfEmployee = listarEmployees();

        for (Employee employee : listOfEmployee) {
            if (id.equals(employee.getId().toString())) {
                listOfEmployee.remove(employee);
            } else {
                throw new EmployeeNaoEncontradoException("not found");
            }
        }


    }

    public String getNome() {
        return null;
    }

    public void setNome(String nome) throws ComprimentoInvalidoException {
    }

    public String getSobrenome() {
        return null;
    }

    public void setSobrenome(String sobrenome) throws ComprimentoInvalidoException {
    }

    public String getEmail() {
        return null;
    }

    public void setEmail(String email) {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && Objects.equals(nome, employee.nome) && Objects.equals(sobrenome, employee.sobrenome) && Objects.equals(email, employee.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, sobrenome, email);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
