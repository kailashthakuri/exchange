package com.demo.exchangeservice.modules.pln.nbpapi.dto;

public class NBP {

    public enum Table {
        A("A"),
        B("B"),
        C("C");

        private String name;

        Table(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
