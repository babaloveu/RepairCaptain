package com.example.lenovo.repaircaptain.entity;

public class StatusModel {
    public int id;
    public int id_order;
    public String order_state;
    public String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public String getOrder_statue() {
        return order_state;
    }

    public void setOrder_statue(String order_statue) {
        this.order_state = order_statue;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
