package id.taryntang.seclass.jobcompare.models;

public class CompareJobs {

    Job job1;
    Job job2;
    JobToCompare[] jobsToCompare;


    public CompareJobs(Job job1, Job job2, JobToCompare[] jobsToCompare) {
        this.job1 = job1;
        this.job2 = job2;
        this.jobsToCompare = jobsToCompare;
    }

    public Job getJob1() {
        return job1;
    }

    public Job getJob2() {
        return job2;
    }

    public JobToCompare[] getJobsToCompare() {
        return jobsToCompare;
    }

    public void selectAndCompareJobs(Job job1 ,Job job2) {

    }

    public Job getJobWithID(int jobID) {

        return null;
    }
}