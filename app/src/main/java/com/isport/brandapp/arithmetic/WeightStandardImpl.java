package com.isport.brandapp.arithmetic;

import com.isport.brandapp.R;

import java.util.ArrayList;

import brandapp.isport.com.basicres.commonutil.UIUtils;

public class WeightStandardImpl implements WeightStandard {
    @Override
    public ArrayList<String> compositeScoreStandardWithValue(String valueStr) {


        float value = Float.parseFloat(valueStr);
        String standardStr = UIUtils.getString(R.string.standard);
        String colorStr = "#50E3C2";

        if (value < 60) {
            standardStr = UIUtils.getString(R.string.level_low);
            colorStr = "#4BC4FF";
        } else if (value >= 60 && value <= 75) {
            standardStr = UIUtils.getString(R.string.standard);
            colorStr = "#84D5FD";
        } else if (value > 75 && value <= 85) {
            standardStr = UIUtils.getString(R.string.standard);
            colorStr = "#50E3C2";
        } else {
            standardStr = UIUtils.getString(R.string.level_good);
            colorStr = "#50E386";
        }

        ArrayList<String> resultArr = new ArrayList<>();
        resultArr.add(standardStr);
        resultArr.add(colorStr);
        return resultArr;

    }

    @Override
    public ArrayList<String> bmiStandardWithValue(double value) {
        //float value = Float.parseFloat(valueStr);
        String standardStr = UIUtils.getString(R.string.standard);
        String colorStr = "#50E3C2";

        if (value < 18.5) {
            standardStr = UIUtils.getString(R.string.thin);
            colorStr = "#4BC4FF";
        } else if (value >= 18.5 && value < 24.0) {
            standardStr = UIUtils.getString(R.string.standard);
            colorStr = "#50E3C2";
        } else if (value >= 24.0 && value < 28.0) {
            standardStr = UIUtils.getString(R.string.chubby);
            colorStr = "#FFD100";
        } else if (value >= 28.0 && value < 30.0) {
            standardStr = UIUtils.getString(R.string.obesity);
            colorStr = "#FD944A";
        } else {
            standardStr = UIUtils.getString(R.string.severe_obesity);
            colorStr = "#FA5F5F";
        }


        ArrayList<String> resultArr = new ArrayList<>();
        resultArr.add(standardStr);
        resultArr.add(colorStr);
        return resultArr;
    }

    // 体脂率
    @Override
    public ArrayList<String> bfpStandardWithValue(double value, String gender, int age) {
        String standardStr = UIUtils.getString(R.string.standard);
        String colorStr = "#50E3C2";


        if (gender.equals("Female")) {
            //m女性
            //年龄39岁以下，数值20以下||年龄40-59，数值21以下||年龄60以上，数值22以下
            if ((age <= 39 && value < 20) || ((age >= 40 && age <= 59) && value < 21) || (age >= 60 && value < 22)) {
                standardStr = UIUtils.getString(R.string.thin);
                colorStr = "#4BC4FF";
            } else if ((age <= 39 && (value <= 34 && value >= 20)) || ((age >= 40 && age <= 59) && (value >= 21 && value <= 35)) || (age >= 60 && (value >= 22 && value <= 36))) {
                standardStr = UIUtils.getString(R.string.standard);
                colorStr = "#50E3C2";
            } else if ((age <= 39 && (value <= 39 && value > 34)) || ((age >= 40 && age <= 59) && (value > 35 && value <= 40)) || (age >= 60 && (value > 36 && value <= 41))) {
                standardStr = UIUtils.getString(R.string.chubby);
                colorStr = "#FFD100";
            } else if ((age <= 39 && value > 39) || ((age >= 40 && age <= 59) && value > 40) || (age >= 60 && value > 41)) {
                standardStr = UIUtils.getString(R.string.obesity);
                colorStr = "#FD944A";
            }

        } else {
            //男性
            //年龄39岁以下，数值10以下||年龄40-59，数值11以下||年龄60以上，数值13以下
            if ((age <= 39 && value < 10) || ((age >= 40 && age <= 59) && value < 11) || (age >= 60 && value < 13)) {
                standardStr = UIUtils.getString(R.string.thin);
                colorStr = "#4BC4FF";
            } else if ((age <= 39 && (value <= 21 && value >= 10)) || ((age >= 40 && age <= 59) && (value >= 11 && value <= 22)) || (age >= 60 && (value >= 13 && value <= 24))) {
                standardStr = UIUtils.getString(R.string.standard);
                colorStr = "#50E3C2";
            } else if ((age <= 39 && (value <= 26 && value > 21)) || ((age >= 40 && age <= 59) && (value > 22 && value <= 27)) || (age >= 60 && (value > 24 && value <= 29))) {
                standardStr = UIUtils.getString(R.string.chubby);
                colorStr = "#FFD100";
            } else if ((age <= 39 && value > 26) || ((age >= 40 && age <= 59) && value > 27) || (age >= 60 && value > 29)) {
                standardStr = UIUtils.getString(R.string.obesity);
                colorStr = "#FD944A";
            }
        }


        ArrayList<String> resultArr = new ArrayList<>();
        resultArr.add(standardStr);
        resultArr.add(colorStr);
        return resultArr;
    }

    @Override
    public ArrayList<String> muscle_massStandardWithValue(double value, String gender, int height) {
        String standardStr = UIUtils.getString(R.string.standard);
        String colorStr = "#50E3C2";

        if (gender.equals("Female")) {
            //女性
            if (height < 150) {
                if (value <= 21.9)//偏低
                {
                    standardStr = UIUtils.getString(R.string.level_low);
                    colorStr = "#4BC4FF";
                } else if (value > 21.9 && value <= 34.7)//标准
                {
                    standardStr = UIUtils.getString(R.string.standard);
                    colorStr = "#50E3C2";
                } else {
                    standardStr = UIUtils.getString(R.string.level_high);
                    colorStr = "#FFD100";
                }
            } else if (height >= 150 && height < 160) {
                if (value <= 32.9)//偏低
                {
                    standardStr = UIUtils.getString(R.string.level_low);
                    colorStr = "#4BC4FF";
                } else if (value > 32.9 && value <= 37.5)//标准
                {
                    standardStr = UIUtils.getString(R.string.standard);
                    colorStr = "#50E3C2";
                } else {
                    standardStr = UIUtils.getString(R.string.level_high);
                    colorStr = "#FFD100";
                }
            } else {
                //身高160以上
                if (value <= 36.5)//偏低
                {
                    standardStr = UIUtils.getString(R.string.level_low);
                    colorStr = "#4BC4FF";
                } else if (value > 36.5 && value <= 42.5)//标准
                {
                    standardStr = UIUtils.getString(R.string.standard);
                    colorStr = "#50E3C2";
                } else {
                    standardStr = UIUtils.getString(R.string.level_high);
                    colorStr = "#FFD100";
                }
            }
        } else {
            //男性
            if (height < 160) {
                if (value <= 38.5)//偏低
                {
                    standardStr = UIUtils.getString(R.string.level_low);
                    colorStr = "#4BC4FF";
                } else if (value > 38.5 && value <= 46.5)//标准
                {
                    standardStr = UIUtils.getString(R.string.standard);
                    colorStr = "#50E3C2";
                } else {
                    standardStr = UIUtils.getString(R.string.level_high);
                    colorStr = "#FFD100";
                }
            } else if (height >= 160 && height < 170) {
                if (value <= 44)//偏低
                {
                    standardStr = UIUtils.getString(R.string.level_low);
                    colorStr = "#4BC4FF";
                } else if (value > 44 && value <= 52.4)//标准
                {
                    standardStr = UIUtils.getString(R.string.standard);
                    colorStr = "#50E3C2";
                } else {
                    standardStr = UIUtils.getString(R.string.level_high);
                    colorStr = "#FFD100";
                }
            } else {
                //身高170以上
                if (value <= 49.4)//偏低
                {
                    standardStr = UIUtils.getString(R.string.level_low);
                    colorStr = "#4BC4FF";
                } else if (value > 49.4 && value <= 59.4)//标准
                {
                    standardStr = UIUtils.getString(R.string.standard);
                    colorStr = "#50E3C2";
                } else {
                    standardStr = UIUtils.getString(R.string.level_high);
                    colorStr = "#FFD100";
                }
            }
        }


        ArrayList<String> resultArr = new ArrayList<>();
        resultArr.add(standardStr);
        resultArr.add(colorStr);
        return resultArr;
    }

    //水分率
    @Override
    public ArrayList<String> bwpStandardWithValue(double value, String gender) {
        String standardStr = UIUtils.getString(R.string.standard);
        String colorStr = "#50E3C2";

        if (gender.equals("Female")) {
            //女性
            if (value < 45) {
                standardStr = UIUtils.getString(R.string.level_low);
                colorStr = "#4BC4FF";
            } else if (value >= 45 && value < 60) {
                standardStr = UIUtils.getString(R.string.standard);
                colorStr = "#50E3C2";
            } else {
                standardStr = UIUtils.getString(R.string.level_high);
                colorStr = "#FFD100";
            }
        } else {
            //男性
            if (value < 55) {
                standardStr = UIUtils.getString(R.string.level_low);
                colorStr = "#4BC4FF";
            } else if (value >= 55 && value < 65) {
                standardStr = UIUtils.getString(R.string.standard);
                colorStr = "#50E3C2";
            } else {
                standardStr = UIUtils.getString(R.string.level_high);
                colorStr = "#FFD100";
            }
        }

        ArrayList<String> resultArr = new ArrayList<>();
        resultArr.add(standardStr);
        resultArr.add(colorStr);
        return resultArr;
    }

    //骨量标准的算法
    @Override
    public ArrayList<String> bone_massStandardWithValue(double value, String gender, int weight) {
        String standardStr = UIUtils.getString(R.string.standard);
        String colorStr = "#50E3C2";

        if (gender.equals("Female")) {
            //女性
            if (weight < 45) {
                if (value < 1.8) {
                    standardStr = UIUtils.getString(R.string.level_low);
                    colorStr = "#4BC4FF";
                } else if (value == 1.8) {
                    standardStr = UIUtils.getString(R.string.standard);
                    colorStr = "#50E3C2";
                } else {
                    standardStr = UIUtils.getString(R.string.level_good);
                    colorStr = "#50E386";
                }
            } else if (weight >= 45 && weight <= 60) {
                if (value < 2.2) {
                    standardStr = UIUtils.getString(R.string.level_low);
                    colorStr = "#4BC4FF";
                } else if (value == 2.2) {
                    standardStr = UIUtils.getString(R.string.standard);
                    colorStr = "#50E3C2";
                } else {
                    standardStr = UIUtils.getString(R.string.level_good);
                    colorStr = "#50E386";
                }
            } else {

                if (value < 2.5) {
                    standardStr = UIUtils.getString(R.string.level_low);
                    colorStr = "#4BC4FF";
                } else if (value == 2.5) {
                    standardStr = UIUtils.getString(R.string.standard);
                    colorStr = "#50E3C2";
                } else {
                    standardStr = UIUtils.getString(R.string.level_good);
                    colorStr = "#50E386";
                }
            }
        } else { //男性
            if (weight < 60) {
                if (value < 2.5) {
                    standardStr = UIUtils.getString(R.string.level_low);
                    colorStr = "#4BC4FF";
                } else if (value == 2.5) {
                    standardStr = UIUtils.getString(R.string.standard);
                    colorStr = "#50E3C2";
                } else {
                    standardStr = UIUtils.getString(R.string.level_good);
                    colorStr = "#50E386";
                }
            } else if (weight >= 60 && weight <= 75) {
                if (value < 2.9) {
                    standardStr = UIUtils.getString(R.string.level_low);
                    colorStr = "#4BC4FF";
                } else if (value == 2.9) {
                    standardStr = UIUtils.getString(R.string.standard);
                    colorStr = "#50E3C2";
                } else {
                    standardStr = UIUtils.getString(R.string.level_good);
                    colorStr = "#50E386";
                }
            } else {
                if (value < 3.2) {
                    standardStr = UIUtils.getString(R.string.level_low);
                    colorStr = "#4BC4FF";
                } else if (value == 3.2) {
                    standardStr = UIUtils.getString(R.string.standard);
                    colorStr = "#50E3C2";
                } else {
                    standardStr = UIUtils.getString(R.string.level_good);
                    colorStr = "#50E386";
                }
            }
        }
        ArrayList<String> resultArr = new ArrayList<>();
        resultArr.add(standardStr);
        resultArr.add(colorStr);
        return resultArr;
    }

    //蛋白质标准的算法
    @Override
    public ArrayList<String> proteinStandardWithValue(double value) {
        String standardStr = UIUtils.getString(R.string.standard);
        String colorStr = "#50E3C2";

        if (value < 16) {
            standardStr = UIUtils.getString(R.string.level_low);
            colorStr = "#4BC4FF";
        } else if (value >= 16 && value <= 20) {
            standardStr = UIUtils.getString(R.string.standard);
            colorStr = "#50E3C2";
        } else {
            standardStr = UIUtils.getString(R.string.level_high);
            colorStr = "#FFD100";
        }

        ArrayList<String> resultArr = new ArrayList<>();
        resultArr.add(standardStr);
        resultArr.add(colorStr);
        return resultArr;
    }

    @Override
    public ArrayList<String> basal_metabolismStandardWithValue(double value, String gender, int age) {
        String standardStr = UIUtils.getString(R.string.standard);
        String colorStr = "#50E3C2";

        if (gender.equals("Female")) {
            //女性
            if (age <= 29) {
                if (value < 1210) {
                    standardStr = UIUtils.getString(R.string.level_low);
                    colorStr = "#FA5F5F";
                } else {
                    standardStr = UIUtils.getString(R.string.standard);
                    colorStr = "#50E3C2";
                }
            } else if (age >= 30 && age <= 49) {
                if (value < 1170) {
                    standardStr = UIUtils.getString(R.string.level_low);
                    colorStr = "#FA5F5F";
                } else {
                    standardStr = UIUtils.getString(R.string.standard);
                    colorStr = "#50E3C2";
                }
            } else if (age >= 50 && age <= 69) {
                if (value < 1110) {
                    standardStr = UIUtils.getString(R.string.level_low);
                    colorStr = "#FA5F5F";
                } else {
                    standardStr = UIUtils.getString(R.string.standard);
                    colorStr = "#50E3C2";
                }
            } else {
                if (value < 1010) {
                    standardStr = UIUtils.getString(R.string.level_low);
                    colorStr = "#FA5F5F";
                } else {
                    standardStr = UIUtils.getString(R.string.standard);
                    colorStr = "#50E3C2";
                }
            }
        } else {
            //男性

            if (age <= 29) {
                if (value < 1550) {
                    standardStr = UIUtils.getString(R.string.level_low);
                    colorStr = "#FA5F5F";
                } else {
                    standardStr = UIUtils.getString(R.string.standard);
                    colorStr = "#50E3C2";
                }
            } else if (age >= 30 && age <= 49) {
                if (value < 1500) {
                    standardStr = UIUtils.getString(R.string.level_low);
                    colorStr = "#FA5F5F";
                } else {
                    standardStr = UIUtils.getString(R.string.standard);
                    colorStr = "#50E3C2";
                }
            } else if (age >= 50 && age <= 69) {
                if (value < 1350) {
                    standardStr = UIUtils.getString(R.string.level_low);
                    colorStr = "#FA5F5F";
                } else {
                    standardStr = UIUtils.getString(R.string.standard);
                    colorStr = "#50E3C2";
                }
            } else {
                if (value < 1220) {
                    standardStr = UIUtils.getString(R.string.level_low);
                    colorStr = "#FA5F5F";
                } else {
                    standardStr = UIUtils.getString(R.string.standard);
                    colorStr = "#50E3C2";
                }
            }
        }

        ArrayList<String> resultArr = new ArrayList<>();
        resultArr.add(standardStr);
        resultArr.add(colorStr);
        return resultArr;
    }

    //内脏脂肪等级
    @Override
    public ArrayList<String> visceral_fatStandardWithValue(double value) {
        String standardStr = UIUtils.getString(R.string.standard);
        String colorStr = "#50E3C2";

        //内脏脂肪依照的是OKOK
        if (value <= 9) {
            standardStr = UIUtils.getString(R.string.standard);
            colorStr = "#50E3C2";
        } else if (value >= 10 && value < 14) {
            standardStr = UIUtils.getString(R.string.level_high);
            colorStr = "#FFD100";
        } else {
            standardStr = UIUtils.getString(R.string.level_overtop);
            colorStr = "#FA5F5F";
        }

        ArrayList<String> resultArr = new ArrayList<>();
        resultArr.add(standardStr);
        resultArr.add(colorStr);
        return resultArr;
    }

    @Override
    public ArrayList<String> skeleton_muscle_massStandardWithValue(double value, String gender, int height) {
        String standardStr = UIUtils.getString(R.string.standard);
        String colorStr = "#50E3C2";

        if (gender.equals("Female")) {
            //女性
            if (height < 150) {
                if (value < 16) {
                    standardStr = UIUtils.getString(R.string.level_low);
                    colorStr = "#FA5F5F";
                } else if (value >= 16 && value <= 20.6) {
                    standardStr = UIUtils.getString(R.string.standard);
                    colorStr = "#50E3C2";
                } else {
                    standardStr = UIUtils.getString(R.string.level_good);
                    colorStr = "#50E386";
                }
            } else if (height <= 160 && height >= 150) {
                if (value < 18.9) {
                    standardStr = UIUtils.getString(R.string.level_low);
                    colorStr = "#FA5F5F";
                } else if (value >= 18.9 && value <= 23.7) {
                    standardStr = UIUtils.getString(R.string.standard);
                    colorStr = "#50E3C2";
                } else {
                    standardStr = UIUtils.getString(R.string.level_good);
                    colorStr = "#50E386";
                }
            } else {
                if (value < 22.1) {
                    standardStr = UIUtils.getString(R.string.level_low);
                    colorStr = "#FA5F5F";
                } else if (value >= 22.1 && value <= 30.3) {
                    standardStr = UIUtils.getString(R.string.standard);
                    colorStr = "#50E3C2";
                } else {
                    standardStr = UIUtils.getString(R.string.level_good);
                    colorStr = "#50E386";
                }
            }
        } else {
            //男性
            if (height < 160) {
                if (value < 21.2) {
                    standardStr = UIUtils.getString(R.string.level_low);
                    colorStr = "#FA5F5F";
                } else if (value >= 21.2 && value <= 26.6) {
                    standardStr = UIUtils.getString(R.string.standard);
                    colorStr = "#50E3C2";
                } else {
                    standardStr = UIUtils.getString(R.string.level_good);
                    colorStr = "#50E386";
                }
            } else if (height <= 170 && height >= 160) {
                if (value < 24.8) {
                    standardStr = UIUtils.getString(R.string.level_low);
                    colorStr = "#FA5F5F";
                } else if (value >= 24.8 && value <= 34.6) {
                    standardStr = UIUtils.getString(R.string.standard);
                    colorStr = "#50E3C2";
                } else {
                    standardStr = UIUtils.getString(R.string.level_good);
                    colorStr = "#50E386";
                }
            } else {
                if (value < 29.6) {
                    standardStr = UIUtils.getString(R.string.level_low);
                    colorStr = "#FA5F5F";
                } else if (value >= 29.6 && value <= 43.2) {
                    standardStr = UIUtils.getString(R.string.standard);
                    colorStr = "#50E3C2";
                } else {
                    standardStr = UIUtils.getString(R.string.level_good);
                    colorStr = "#50E386";
                }
            }
        }

        ArrayList<String> resultArr = new ArrayList<>();
        resultArr.add(standardStr);
        resultArr.add(colorStr);
        return resultArr;
    }
}
