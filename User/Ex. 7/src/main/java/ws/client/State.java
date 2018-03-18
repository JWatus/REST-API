package ws.client;

import org.codehaus.jackson.annotate.JsonProperty;
public class State {
    int id;
    String country;
    String name;
    String abbr;
    String area;
    // adnotacja ta konieczna jest w momencie kiedy w pliku json uzyte sa znaki " _" lub z wielkich liter
    // musza byc wszystkie pola stworzone i dodatkowo stworzone musza byc settery i gettery
    @JsonProperty("largest_city")
    String largestCity;
    String capital;
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLargestCity() {
        return largestCity;
    }
    public void setLargestCity(String largestCity) {
        this.largestCity = largestCity;
    }
    public String getCapital() {
        return capital;
    }
    public void setCapital(String capital) {
        this.capital = capital;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getAbbr() {
        return abbr;
    }
    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }
}