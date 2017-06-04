
package bartburg.nl.backbaseweather.model;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Forecast implements Parcelable
{

    @SerializedName("dt")
    @Expose
    private Integer dt;
    @SerializedName("temp")
    @Expose
    private Temp temp;
    @SerializedName("pressure")
    @Expose
    private Double pressure;
    @SerializedName("humidity")
    @Expose
    private Integer humidity;
    @SerializedName("weather")
    @Expose
    private List<Weather> weather = null;
    @SerializedName("speed")
    @Expose
    private Double speed;
    @SerializedName("deg")
    @Expose
    private Integer deg;
    @SerializedName("clouds")
    @Expose
    private Integer clouds;
    public final static Parcelable.Creator<Forecast> CREATOR = new Creator<Forecast>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Forecast createFromParcel(Parcel in) {
            Forecast instance = new Forecast();
            instance.dt = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.temp = ((Temp) in.readValue((Temp.class.getClassLoader())));
            instance.pressure = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.humidity = ((Integer) in.readValue((Integer.class.getClassLoader())));
            in.readList(instance.weather, (bartburg.nl.backbaseweather.model.Weather.class.getClassLoader()));
            instance.speed = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.deg = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.clouds = ((Integer) in.readValue((Integer.class.getClassLoader())));
            return instance;
        }

        public Forecast[] newArray(int size) {
            return (new Forecast[size]);
        }

    }
    ;

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Integer getDeg() {
        return deg;
    }

    public void setDeg(Integer deg) {
        this.deg = deg;
    }

    public Integer getClouds() {
        return clouds;
    }

    public void setClouds(Integer clouds) {
        this.clouds = clouds;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(dt);
        dest.writeValue(temp);
        dest.writeValue(pressure);
        dest.writeValue(humidity);
        dest.writeList(weather);
        dest.writeValue(speed);
        dest.writeValue(deg);
        dest.writeValue(clouds);
    }

    public int describeContents() {
        return  0;
    }

}
