
package bartburg.nl.backbaseweather.provision.remote.controller.weather;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import bartburg.nl.backbaseweather.model.Clouds;
import bartburg.nl.backbaseweather.model.Coordinates;
import bartburg.nl.backbaseweather.model.Main;
import bartburg.nl.backbaseweather.model.Sys;
import bartburg.nl.backbaseweather.model.Weather;
import bartburg.nl.backbaseweather.model.Wind;

public class WeatherResponse implements Parcelable
{

    @SerializedName("coordinates")
    @Expose
    private Coordinates coordinates;
    @SerializedName("weather")
    @Expose
    private List<Weather> weather = new ArrayList<>();
    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("main")
    @Expose
    private Main main;
    @SerializedName("visibility")
    @Expose
    private Integer visibility;
    @SerializedName("wind")
    @Expose
    private Wind wind;
    @SerializedName("clouds")
    @Expose
    private Clouds clouds;
    @SerializedName("dt")
    @Expose
    private Integer dt;
    @SerializedName("sys")
    @Expose
    private Sys sys;
    @SerializedName("id")
    @Expose
    private Integer id; //This is the city id
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    public final static Parcelable.Creator<WeatherResponse> CREATOR = new Creator<WeatherResponse>() {
        @SuppressWarnings({
                "unchecked"
        })
        public WeatherResponse createFromParcel(Parcel in) {
            WeatherResponse instance = new WeatherResponse();
            instance.coordinates = ((Coordinates) in.readValue((Coordinates.class.getClassLoader())));
            in.readList(instance.weather, (bartburg.nl.backbaseweather.model.Weather.class.getClassLoader()));
            instance.base = ((String) in.readValue((String.class.getClassLoader())));
            instance.main = ((Main) in.readValue((Main.class.getClassLoader())));
            instance.visibility = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.wind = ((Wind) in.readValue((Wind.class.getClassLoader())));
            instance.clouds = ((Clouds) in.readValue((Clouds.class.getClassLoader())));
            instance.dt = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.sys = ((Sys) in.readValue((Sys.class.getClassLoader())));
            instance.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.responseCode = ((Integer) in.readValue((Integer.class.getClassLoader())));
            return instance;
        }

        public WeatherResponse[] newArray(int size) {
            return (new WeatherResponse[size]);
        }

    };

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(coordinates);
        dest.writeList(weather);
        dest.writeValue(base);
        dest.writeValue(main);
        dest.writeValue(visibility);
        dest.writeValue(wind);
        dest.writeValue(clouds);
        dest.writeValue(dt);
        dest.writeValue(sys);
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(responseCode);
    }

    public int describeContents() {
        return  0;
    }

    public int getCityId() {
        return id;
    }
}
