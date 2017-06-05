package bartburg.nl.backbaseweather.provision.remote.controller.weather;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Bart on 6/5/2017.
 */


public class MultipleCitiesWeatherResponse implements Parcelable {

    @SerializedName("cnt")
    @Expose
    private Integer cnt;
    @SerializedName("list")
    @Expose
    private List<WeatherResponse> weatherResponses = null;
    public final static Parcelable.Creator<MultipleCitiesWeatherResponse> CREATOR = new Creator<MultipleCitiesWeatherResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public MultipleCitiesWeatherResponse createFromParcel(Parcel in) {
            MultipleCitiesWeatherResponse instance = new MultipleCitiesWeatherResponse();
            instance.cnt = ((Integer) in.readValue((Integer.class.getClassLoader())));
            in.readList(instance.weatherResponses, (bartburg.nl.backbaseweather.provision.remote.controller.weather.WeatherResponse.class.getClassLoader()));
            return instance;
        }

        public MultipleCitiesWeatherResponse[] newArray(int size) {
            return (new MultipleCitiesWeatherResponse[size]);
        }

    };

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public List<WeatherResponse> getWeatherResponses() {
        return weatherResponses;
    }

    public void setWeatherResponses(List<WeatherResponse> weatherResponses) {
        this.weatherResponses = weatherResponses;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(cnt);
        dest.writeList(weatherResponses);
    }

    public int describeContents() {
        return 0;
    }

}
