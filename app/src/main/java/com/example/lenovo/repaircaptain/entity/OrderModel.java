package com.example.lenovo.repaircaptain.entity;

/**
 * Created by lenovo on 2018/4/1.
 */

public class OrderModel {
    public int id;
    public int id_user;
    public int id_worker;
    public String address;
    public String name_machine;
    public String description;
    public String time1;
    public String time2;
    public String level;
    public String time_creation;
    public String time_claim;



    public String time_completion;
    public float payment;
    public String name;
    public int sex;
    public String phone1;
    public String phone2;
    public WorkerModel worker;

    public StatusModel status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }


    public int getId_worker() {
        return id_worker;
    }

    public void setId_worker(int id_worker) {
        this.id_worker = id_worker;
    }

    public String getName_machine() {
        return name_machine;
    }

    public void setName_machine(String name_machine) {
        this.name_machine = name_machine;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTime_creation() {
        return time_creation;
    }

    public void setTime_creation(String time_creation) {
        this.time_creation = time_creation;
    }

    public String getTime_claim() {
        return time_claim;
    }

    public void setTime_claim(String time_claim) {
        this.time_claim = time_claim;
    }

    public float getPayment() {
        return payment;
    }

    public void setPayment(float payment) {
        this.payment = payment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public WorkerModel getWorker() {
        return worker;
    }

    public void setWorker(WorkerModel worker) {
        this.worker = worker;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public StatusModel getStatus() {
        return status;
    }

    public void setStatus(StatusModel status) {
        this.status = status;
    }

    public String getTime_completion() {
        return time_completion;
    }

    public void setTime_completion(String time_completion) {
        this.time_completion = time_completion;
    }


}
