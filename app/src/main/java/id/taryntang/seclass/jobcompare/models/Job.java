package id.taryntang.seclass.jobcompare.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Job implements Parcelable{
    int uniqueID;
    JobDetails jobDetails;
    boolean isCurrentJob;

    public Job(int uniqueID, boolean isCurrentJob) {
        this.uniqueID = uniqueID;
        this.isCurrentJob = isCurrentJob;
    }

    public Job(int uniqueID, JobDetails jobDetails, boolean isCurrentJob) {
        this.uniqueID = uniqueID;
        this.jobDetails = jobDetails;
        this.isCurrentJob = isCurrentJob;
    }

    public int getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(int uniqueID) {
        this.uniqueID = uniqueID;
    }

    public JobDetails getJobDetails() {
        return jobDetails;
    }

    public void setJobDetails(JobDetails jobDetails) {
        this.jobDetails = jobDetails;
    }

    public boolean isCurrentJob() {
        return isCurrentJob;
    }

    public void setCurrentJob(boolean currentJob) {
        isCurrentJob = currentJob;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.uniqueID);
        parcel.writeParcelable(this.jobDetails, i);
        parcel.writeBoolean(this.isCurrentJob);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Job> CREATOR = new Parcelable.Creator<Job>() {
        public Job createFromParcel(Parcel in) {
            return new Job(in);
        }
        public Job[] newArray(int size) {
            return new Job[size];
        }
    };

    private Job(Parcel in) {
        this.uniqueID = in.readInt();
        this.jobDetails = in.readParcelable(getClass().getClassLoader());
        this.isCurrentJob = in.readBoolean();
    }
}
