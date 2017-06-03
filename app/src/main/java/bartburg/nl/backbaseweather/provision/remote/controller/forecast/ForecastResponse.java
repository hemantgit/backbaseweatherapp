
package bartburg.nl.backbaseweather.provision.remote.controller.forecast;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import bartburg.nl.backbaseweather.model.City;
import bartburg.nl.backbaseweather.model.Forecast;

public class ForecastResponse implements Parcelable
{

    @SerializedName("responseCode")
    @Expose
    private String responseCode;
    @SerializedName("message")
    @Expose
    private Double message;
    @SerializedName("cnt")
    @Expose
    private Integer cnt;
    @SerializedName("forecast")
    @Expose
    private List<Forecast> forecast = new ArrayList<Forecast>();
    @SerializedName("city")
    @Expose
    private City city;
    public final static Parcelable.Creator<ForecastResponse> CREATOR = new Creator<ForecastResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ForecastResponse createFromParcel(Parcel in) {
            ForecastResponse instance = new ForecastResponse();
            instance.responseCode = ((String) in.readValue((String.class.getClassLoader())));
            instance.message = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.cnt = ((Integer) in.readValue((Integer.class.getClassLoader())));
            in.readList(instance.forecast, (bartburg.nl.backbaseweather.model.Forecast.class.getClassLoader()));
            instance.city = ((City) in.readValue((City.class.getClassLoader())));
            return instance;
        }

        public ForecastResponse[] newArray(int size) {
            return (new ForecastResponse[size]);
        }

    }
    ;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public List<Forecast> getForecast() {
        return forecast;
    }

    public void setForecast(List<Forecast> forecast) {
        this.forecast = forecast;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getCityId(){
        if(city == null)
            return -1;
        return city.getId();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(responseCode);
        dest.writeValue(message);
        dest.writeValue(cnt);
        dest.writeList(forecast);
        dest.writeValue(city);
    }

    public int describeContents() {
        return  0;
    }

}
