package id.taryntang.seclass.jobcompare.models;

public class JobToCompare {
    String title;
    String company;
    int jobId;
    int rank;

    Job job;

    // hold state of boolean
    private boolean isCheckedForComparison;


    public JobToCompare(String title, String company, int jobId, int rank, Job job) {
        this.title = title;
        this.company = company;
        this.jobId = jobId;
        this.rank = rank;
        this.job = job;
    }

    public Job getJob() { return job; }



    public void setJob(Job job) {
        this.job = job;
    }

    public boolean getChecked() {
        return isCheckedForComparison;
    }

    public void setChecked(boolean checked) {
        isCheckedForComparison = checked;
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

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
