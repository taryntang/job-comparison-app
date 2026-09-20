package id.taryntang.seclass.jobcompare.models;

public class JobSettings {
    /*private float yearlySalary;
    private float yearlyBonus;
    private int leaveDays;
    private int sharesOffered;
    private float homeBuyingFund;
    private float wellnessFund;*/
    private int salaryWeight;
    private int bonusWeight;
    private int vacationWeight;
    private int sharesWeight;
    private int homeFundWeight;
    private int wellnessFundWeight;

    public JobSettings() {
    }

/*    public JobSettings(float yearlySalary, float yearlyBonus, int leaveDays, int sharesOffered, float homeBuyingFund,
                       float wellnessFund) {
        this.yearlySalary = yearlySalary;
        this.yearlyBonus = yearlyBonus;
        this.leaveDays = leaveDays;
        this.sharesOffered = sharesOffered;
        this.homeBuyingFund = homeBuyingFund;
    }*/

/*    public JobSettings(float yearlySalary, float yearlyBonus, int leaveDays, int sharesOffered, float homeBuyingFund,
                       float wellnessFund, int salaryWeight, int bonusWeight, int vacationWeight, int sharesWeight,
                       int homeFundWeight, int wellnessFundWeight) {
        this.yearlySalary = yearlySalary;
        this.yearlyBonus = yearlyBonus;
        this.leaveDays = leaveDays;
        this.sharesOffered = sharesOffered;
        this.homeBuyingFund = homeBuyingFund;
        this.wellnessFund = wellnessFund;
        this.salaryWeight = salaryWeight;
        this.bonusWeight = bonusWeight;
        this.vacationWeight = vacationWeight;
        this.sharesWeight = sharesWeight;
        this.homeFundWeight = homeFundWeight;
        this.wellnessFundWeight = wellnessFundWeight;
    }*/

    public JobSettings(int salaryWeight, int bonusWeight, int vacationWeight, int sharesWeight,
                       int homeFundWeight, int wellnessFundWeight) {

        this.salaryWeight = salaryWeight;
        this.bonusWeight = bonusWeight;
        this.vacationWeight = vacationWeight;
        this.sharesWeight = sharesWeight;
        this.homeFundWeight = homeFundWeight;
        this.wellnessFundWeight = wellnessFundWeight;
    }

/*    public float getYearlySalary() {
        return yearlySalary;
    }

    public void setYearlySalary(float yearlySalary) {
        this.yearlySalary = yearlySalary;
    }

    public float getYearlyBonus() {
        return yearlyBonus;
    }

    public void setYearlyBonus(float yearlyBonus) {
        this.yearlyBonus = yearlyBonus;
    }

    public int getLeaveDays() {
        return leaveDays;
    }

    public void setLeaveDays(int leaveDays) {
        this.leaveDays = leaveDays;
    }

    public int getSharesOffered() {
        return sharesOffered;
    }

    public void setSharesOffered(int sharesOffered) {
        this.sharesOffered = sharesOffered;
    }

    public float getHomeBuyingFund() {
        return homeBuyingFund;
    }

    public void setHomeBuyingFund(float homeBuyingFund) {
        this.homeBuyingFund = homeBuyingFund;
    }

    public float getWellnessFund() {
        return wellnessFund;
    }

    public void setWellnessFund(float wellnessFund) {
        this.wellnessFund = wellnessFund;
    }*/

    public void setSalaryWeight(int salaryWeight) {
        this.salaryWeight = salaryWeight;
    }

    public void setBonusWeight(int bonusWeight) {
        this.bonusWeight = bonusWeight;
    }

    public void setVacationWeight(int vacationWeight) {
        this.vacationWeight = vacationWeight;
    }

    public void setSharesWeight(int sharesWeight) {
        this.sharesWeight = sharesWeight;
    }

    public void setHomeFundWeight(int homeFundWeight) {
        this.homeFundWeight = homeFundWeight;
    }

    public void setWellnessFundWeight(int wellnessFundWeight) {
        this.wellnessFundWeight = wellnessFundWeight;
    }

    public float getSalaryWeight() {
        return salaryWeight;
    }

    public float getBonusWeight() {
        return bonusWeight;
    }

    public float getVacationWeight() {
        return vacationWeight;
    }

    public float getSharesWeight() {
        return sharesWeight;
    }

    public float getHomeFundWeight() {
        return homeFundWeight;
    }

    public float getWellnessFundWeight() {
        return wellnessFundWeight;
    }

//    @Override
//    public String toString() {
//        return "JobSettings{" +
//                "yearlySalary=" + yearlySalary +
//                ", yearlyBonus=" + yearlyBonus +
//                ", leaveDays=" + leaveDays +
//                ", sharesOffered=" + sharesOffered +
//                ", homeBuyingFund=" + homeBuyingFund +
//                ", wellnessFund=" + wellnessFund +
//                '}';
//    }

    public boolean validateFields() {
        return this.salaryWeight != 0 &&
                this.bonusWeight != 0 &&
                this.vacationWeight != 0 &&
                this.sharesWeight != 0 &&
                this.homeFundWeight != 0 &&
                this.wellnessFundWeight != 0;
    }

    public boolean validateInteger(){
        return  !Integer.toString(this.salaryWeight).matches("[-+]?\\d*\\.?\\d+")
                && !Integer.toString(this.bonusWeight).matches("-?\\d+(\\.\\d+)?")
                &&!Integer.toString(this.vacationWeight).matches("[-+]?\\d*\\.?\\d+")
                && !Integer.toString(this.sharesWeight).matches("[-+]?\\d*\\.?\\d+")
                && !Integer.toString(this.homeFundWeight).matches("[-+]?\\d*\\.?\\d+")
                && !Integer.toString(this.wellnessFundWeight).matches("[-+]?\\d*\\.?\\d+");
    }

}
