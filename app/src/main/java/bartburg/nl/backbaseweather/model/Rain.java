
package bartburg.nl.backbaseweather.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rain implements Parcelable
{

    @SerializedName("3h")
    @Expose
    private Double last3Hours;
    public final static Parcelable.Creator<Rain> CREATOR = new Creator<Rain>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Rain createFromParcel(Parcel in) {
            Rain instance = new Rain();
            instance.last3Hours = ((Double) in.readValue((Double.class.getClassLoader())));
            return instance;
        }

        public Rain[] newArray(int size) {
            return (new Rain[size]);
        }

    }
    ;

    public Double getLast3Hours() {
        return last3Hours;
    }

    public void setLast3Hours(Double _3h) {
        this.last3Hours = _3h;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(last3Hours);
    }

    public int describeContents() {
        return  0;
    }

}
