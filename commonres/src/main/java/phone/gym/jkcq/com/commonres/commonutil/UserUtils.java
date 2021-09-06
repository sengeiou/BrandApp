package phone.gym.jkcq.com.commonres.commonutil;

import android.text.TextUtils;

import java.util.Calendar;

public class UserUtils {

    public static int getAge(String birthday) {
        int age = 18;
        try {
            if (!TextUtils.isEmpty(birthday)) {
                String[] birthdays = birthday.split("-");
                Calendar calendar = Calendar.getInstance();
                int ageYear = Integer.parseInt(birthdays[0]);
                int currentYear = calendar.get(Calendar.YEAR);
                int ageMonth = Integer.parseInt(birthdays[1]);
                int ageDay = Integer.parseInt(birthdays[2]);
                int currentMonth = calendar.get(Calendar.MONTH) + 1;
                int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
                age = currentYear - ageYear;
                if (currentMonth < ageMonth) {
                    age--;
                } else if (currentMonth == ageMonth && currentDay < ageDay) {
                    age--;
                }
            } else {
                age = 18;
            }
        } catch (Exception e) {
            age = 18;
        } finally {
            return age;
        }


    }
}
