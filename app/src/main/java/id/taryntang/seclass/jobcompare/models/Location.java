package id.taryntang.seclass.jobcompare.models;

public class Location {
    private String city;
    private String state;

    public Location(){
    }

    public void setLocation(String city, String state) {
        this.city = city;
        this.state = state;
    }

    @Override
    public String toString() {
        return "Location{" +
                "city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}