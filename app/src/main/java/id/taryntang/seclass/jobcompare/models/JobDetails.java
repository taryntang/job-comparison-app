package id.taryntang.seclass.jobcompare.models;

import android.os.Parcel;
import android.os.Parcelable;

public class JobDetails implements Parcelable {
    int uniqueId;
    String title;
    String company;
    String city;
    String state;
    double salary;
    double bonus;
    int leaveTime;
    int noOfShares;
    double homeBuyingFund;
    double wellnessFund;


    public JobDetails(int uniqueId, String title, String company, String city, String state,
                      double salary, double bonus,
                      int leaveTime, int noOfShares,
                      double homeBuyingFund, double wellnessFund) {
        this.uniqueId = uniqueId;
        this.title = title;
        this.company = company;
        this.city = city;
        this.state = state;
        this.salary = salary;
        this.bonus = bonus;
        this.leaveTime = leaveTime;
        this.noOfShares = noOfShares;
        this.homeBuyingFund = homeBuyingFund;
        this.wellnessFund = wellnessFund;
    }

    public JobDetails() {

    }

    @Override
    public String toString() {
        return "JobDetailModel{" +
                "uniqueId=" + uniqueId +
                ", title='" + title + '\'' +
                ", company='" + company + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", salary=" + salary +
                ", bonus=" + bonus +
                ", leaveTime=" + leaveTime +
                ", noOfShares=" + noOfShares +
                ", homeBuyingFund=" + homeBuyingFund +
                ", wellnessFund=" + wellnessFund +
                '}';
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public int getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(int leaveTime) {
        this.leaveTime = leaveTime;
    }

    public int getNoOfShares() {
        return noOfShares;
    }

    public void setNoOfShares(int noOfShares) {
        this.noOfShares = noOfShares;
    }

    public double getHomeBuyingFund() {
        return homeBuyingFund;
    }

    public void setHomeBuyingFund(double homeBuyingFund) {
        this.homeBuyingFund = homeBuyingFund;
    }

    public double getWellnessFund() {
        return wellnessFund;
    }

    public void setWellnessFund(double wellnessFund) {
        this.wellnessFund = wellnessFund;
    }

    public boolean validateFields() {
        return this.title != "" &&
                this.company != "" &&
                this.city != "" &&
                this.state != "" &&
            this.salary != 0 &&
            this.bonus != 0 &&
            this.leaveTime != 0 &&
            this.noOfShares != 0 &&
            this.homeBuyingFund != 0 &&
            this.wellnessFund != 0;

    }

    public boolean validateHomeBuyingFund() {
        double homeFundLimit = 0.15 * this.salary;
        int compareFunds = Double.compare(homeFundLimit,this.homeBuyingFund);
        return compareFunds >= 0;
    }

    public boolean validateWellnessFund() {
        double lowerlimit = 0.00;
        double wellnessFundLimit = 5000.00;
        int compareUpperLimit = Double.compare(wellnessFundLimit,this.wellnessFund);
        int compareLowerLimit = Double.compare(lowerlimit,this.wellnessFund);
        return compareUpperLimit >= 0 && compareLowerLimit <= 0 ;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.uniqueId);
        parcel.writeString(this.title);
        parcel.writeString(this.company);
        parcel.writeString(this.city);
        parcel.writeString(this.state);
        parcel.writeDouble(this.salary);
        parcel.writeDouble(this.bonus);
        parcel.writeInt(this.leaveTime);
        parcel.writeInt(this.noOfShares);
        parcel.writeDouble(this.homeBuyingFund);
        parcel.writeDouble(this.wellnessFund);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<JobDetails> CREATOR = new Parcelable.Creator<JobDetails>() {
        public JobDetails createFromParcel(Parcel in) {
            return new JobDetails(in);
        }

        public JobDetails[] newArray(int size) {
            return new JobDetails[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private JobDetails(Parcel in) {
        this.uniqueId = in.readInt();
        this.title = in.readString();
        this.company = in.readString();
        this.city = in.readString();
        this.state = in.readString();
        this.salary = in.readDouble();
        this.bonus = in.readDouble();
        this.leaveTime = in.readInt();
        this.noOfShares = in.readInt();
        this.homeBuyingFund = in.readDouble();
        this.wellnessFund = in.readDouble();
    }
}
