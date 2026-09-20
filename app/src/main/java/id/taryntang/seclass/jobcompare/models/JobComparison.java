package id.taryntang.seclass.jobcompare.models;

public class JobComparison {

    Job job1;
    Job job2;

    public JobComparison(Job job1, Job job2) {
        this.job1 = job1;
        this.job2 = job2;
    }

    public JobComparison() { }

    public int computeJobRank(JobDetails jobDetail, JobSettings jobSetting) {

        // num/den * AYS + num/den * AYB + num/den * (LT * AYS / 260) + num/den * (CSO/2) + num/den * HBP + num/den * WF

        int den = (int) (jobSetting.getSalaryWeight() + jobSetting.getBonusWeight() +
                jobSetting.getVacationWeight() + jobSetting.getSharesWeight() +
                jobSetting.getHomeFundWeight() +jobSetting.getWellnessFundWeight());

        double yearlySalaryWeight = (jobSetting.getSalaryWeight() / den);
        double yearlyBonusWeight = (jobSetting.getBonusWeight() / den);
        double leaveTimeWeight = (jobSetting.getVacationWeight() / den);
        double companySharesWeight = (jobSetting.getSharesWeight() / den);
        double homeWeight =  (jobSetting.getHomeFundWeight() / den);
        double wellnessWeight = (jobSetting.getWellnessFundWeight() / den);


        double jobRank = (yearlySalaryWeight * jobDetail.getSalary()) + (yearlyBonusWeight * jobDetail.getBonus())
                + leaveTimeWeight * (jobDetail.getLeaveTime() * (jobDetail.getSalary() / 260)) +
                companySharesWeight * (jobDetail.getNoOfShares() / 2) + homeWeight * jobDetail.getHomeBuyingFund() +
                wellnessWeight * jobDetail.getWellnessFund();

        return (int) Math.round(jobRank);
    }

    public Job getJob1() {
        return job1;
    }

    public Job getJob2() {
        return job2;
    }

    public void displayTables(Job job1 , Job job2) {

    }

    public void returnToMenu() {

    }

    public void compareJobs() {

    }
}

