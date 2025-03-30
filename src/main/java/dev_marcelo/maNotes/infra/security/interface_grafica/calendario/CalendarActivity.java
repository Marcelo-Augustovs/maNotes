package dev_marcelo.maNotes.infra.security.interface_grafica;

import java.time.ZonedDateTime;

public class CalendarActivity {
    private ZonedDateTime date;
    private String clientName;
    private Integer serviceNo;

    // Construtor original
    public CalendarActivity(ZonedDateTime date, String clientName, Integer serviceNo) {
        this.date = date;
        this.clientName = clientName;
        this.serviceNo = serviceNo;
    }

    // Novo construtor para aceitar apenas o nome do evento
    public CalendarActivity(String clientName) {
        this.clientName = clientName;
    }

    // Getters e Setters
    public ZonedDateTime getDate() { return date; }
    public void setDate(ZonedDateTime date) { this.date = date; }
    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }
    public Integer getServiceNo() { return serviceNo; }
    public void setServiceNo(Integer serviceNo) { this.serviceNo = serviceNo; }

    @Override
    public String toString() {
        return "CalendarActivity{" +
                "date=" + date +
                ", clientName='" + clientName + '\'' +
                ", serviceNo=" + serviceNo +
                '}';
    }
}