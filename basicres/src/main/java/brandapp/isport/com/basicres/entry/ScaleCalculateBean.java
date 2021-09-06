package brandapp.isport.com.basicres.entry;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Author
 * @Date 2018/10/22
 * @Fuction
 */
@Entity
public class ScaleCalculateBean {

    @Id(autoincrement = true)
    private Long id;//解决数据重叠bug
    private String userId;
    private int deviceType;
    private String mac;
    private float weight;//体重
    private float resistance;//电阻
    private float EXF_extracellularFluid;//获取细胞外液
    private float InF_intracellularFluid;//获取细胞内液
    private float TF_totalWaterWeight;//获取总水重
    private float TFR_percentWaterContent;//获取含水百分比
    private float LBM_loseFatWeight;//获取去脂体重(瘦体重)
    private float SLM_muscleWeight;//获取肌肉重(含水)
    private float PM_protein;//获取蛋白质
    private float FM_fatWeight;//获取脂肪重
    private float BFR_percentageFat;//获取脂肪百份比
    private float EE_edemaTest;//获取水肿测试
    private float OD_obesityDegree;//获取肥胖度
    private float MC_muscleControl;//获取肌肉控制
    private float WC_weightControl;//获取体重控制
    //    private float FC_unknownAttribute;//未知属性
    private float BMR_basalMetabolism;//获取基础代谢
    private float MSW_inorganicSalt;// 获取骨(无机盐)
    private float VFR_visceralFatLevel;//获取内脏脂肪等级
    private float BodyAge;//获取身体年龄
    private float individualScore;//获取个体成分评分
    public float getIndividualScore() {
        return this.individualScore;
    }
    public void setIndividualScore(float individualScore) {
        this.individualScore = individualScore;
    }
    public float getBodyAge() {
        return this.BodyAge;
    }
    public void setBodyAge(float BodyAge) {
        this.BodyAge = BodyAge;
    }
    public float getVFR_visceralFatLevel() {
        return this.VFR_visceralFatLevel;
    }
    public void setVFR_visceralFatLevel(float VFR_visceralFatLevel) {
        this.VFR_visceralFatLevel = VFR_visceralFatLevel;
    }
    public float getMSW_inorganicSalt() {
        return this.MSW_inorganicSalt;
    }
    public void setMSW_inorganicSalt(float MSW_inorganicSalt) {
        this.MSW_inorganicSalt = MSW_inorganicSalt;
    }
    public float getBMR_basalMetabolism() {
        return this.BMR_basalMetabolism;
    }
    public void setBMR_basalMetabolism(float BMR_basalMetabolism) {
        this.BMR_basalMetabolism = BMR_basalMetabolism;
    }
    public float getWC_weightControl() {
        return this.WC_weightControl;
    }
    public void setWC_weightControl(float WC_weightControl) {
        this.WC_weightControl = WC_weightControl;
    }
    public float getMC_muscleControl() {
        return this.MC_muscleControl;
    }
    public void setMC_muscleControl(float MC_muscleControl) {
        this.MC_muscleControl = MC_muscleControl;
    }
    public float getOD_obesityDegree() {
        return this.OD_obesityDegree;
    }
    public void setOD_obesityDegree(float OD_obesityDegree) {
        this.OD_obesityDegree = OD_obesityDegree;
    }
    public float getEE_edemaTest() {
        return this.EE_edemaTest;
    }
    public void setEE_edemaTest(float EE_edemaTest) {
        this.EE_edemaTest = EE_edemaTest;
    }
    public float getBFR_percentageFat() {
        return this.BFR_percentageFat;
    }
    public void setBFR_percentageFat(float BFR_percentageFat) {
        this.BFR_percentageFat = BFR_percentageFat;
    }
    public float getFM_fatWeight() {
        return this.FM_fatWeight;
    }
    public void setFM_fatWeight(float FM_fatWeight) {
        this.FM_fatWeight = FM_fatWeight;
    }
    public float getPM_protein() {
        return this.PM_protein;
    }
    public void setPM_protein(float PM_protein) {
        this.PM_protein = PM_protein;
    }
    public float getSLM_muscleWeight() {
        return this.SLM_muscleWeight;
    }
    public void setSLM_muscleWeight(float SLM_muscleWeight) {
        this.SLM_muscleWeight = SLM_muscleWeight;
    }
    public float getLBM_loseFatWeight() {
        return this.LBM_loseFatWeight;
    }
    public void setLBM_loseFatWeight(float LBM_loseFatWeight) {
        this.LBM_loseFatWeight = LBM_loseFatWeight;
    }
    public float getTFR_percentWaterContent() {
        return this.TFR_percentWaterContent;
    }
    public void setTFR_percentWaterContent(float TFR_percentWaterContent) {
        this.TFR_percentWaterContent = TFR_percentWaterContent;
    }
    public float getTF_totalWaterWeight() {
        return this.TF_totalWaterWeight;
    }
    public void setTF_totalWaterWeight(float TF_totalWaterWeight) {
        this.TF_totalWaterWeight = TF_totalWaterWeight;
    }
    public float getInF_intracellularFluid() {
        return this.InF_intracellularFluid;
    }
    public void setInF_intracellularFluid(float InF_intracellularFluid) {
        this.InF_intracellularFluid = InF_intracellularFluid;
    }
    public float getEXF_extracellularFluid() {
        return this.EXF_extracellularFluid;
    }
    public void setEXF_extracellularFluid(float EXF_extracellularFluid) {
        this.EXF_extracellularFluid = EXF_extracellularFluid;
    }
    public float getResistance() {
        return this.resistance;
    }
    public void setResistance(float resistance) {
        this.resistance = resistance;
    }
    public float getWeight() {
        return this.weight;
    }
    public void setWeight(float weight) {
        this.weight = weight;
    }
    public String getMac() {
        return this.mac;
    }
    public void setMac(String mac) {
        this.mac = mac;
    }
    public int getDeviceType() {
        return this.deviceType;
    }
    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 199135765)
    public ScaleCalculateBean(Long id, String userId, int deviceType, String mac,
            float weight, float resistance, float EXF_extracellularFluid,
            float InF_intracellularFluid, float TF_totalWaterWeight,
            float TFR_percentWaterContent, float LBM_loseFatWeight,
            float SLM_muscleWeight, float PM_protein, float FM_fatWeight,
            float BFR_percentageFat, float EE_edemaTest, float OD_obesityDegree,
            float MC_muscleControl, float WC_weightControl,
            float BMR_basalMetabolism, float MSW_inorganicSalt,
            float VFR_visceralFatLevel, float BodyAge, float individualScore) {
        this.id = id;
        this.userId = userId;
        this.deviceType = deviceType;
        this.mac = mac;
        this.weight = weight;
        this.resistance = resistance;
        this.EXF_extracellularFluid = EXF_extracellularFluid;
        this.InF_intracellularFluid = InF_intracellularFluid;
        this.TF_totalWaterWeight = TF_totalWaterWeight;
        this.TFR_percentWaterContent = TFR_percentWaterContent;
        this.LBM_loseFatWeight = LBM_loseFatWeight;
        this.SLM_muscleWeight = SLM_muscleWeight;
        this.PM_protein = PM_protein;
        this.FM_fatWeight = FM_fatWeight;
        this.BFR_percentageFat = BFR_percentageFat;
        this.EE_edemaTest = EE_edemaTest;
        this.OD_obesityDegree = OD_obesityDegree;
        this.MC_muscleControl = MC_muscleControl;
        this.WC_weightControl = WC_weightControl;
        this.BMR_basalMetabolism = BMR_basalMetabolism;
        this.MSW_inorganicSalt = MSW_inorganicSalt;
        this.VFR_visceralFatLevel = VFR_visceralFatLevel;
        this.BodyAge = BodyAge;
        this.individualScore = individualScore;
    }
    @Generated(hash = 1155313575)
    public ScaleCalculateBean() {
    }


}
