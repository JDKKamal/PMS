package com.jdkgroup.pattern;

import android.os.Bundle;
import com.jdkgroup.baseclasses.BaseActivity;
import com.jdkgroup.pattern.prototype.EmployeeRecord;
import com.jdkgroup.pms.R;
import com.jdkgroup.pms.utils.AppUtils;

import java.util.Arrays;
import java.util.List;

public class BuilderPattern extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*
        * TODO construct a complex object from simple objects using step-by-step approach
        * */

        //http://www.oracle.com/technetwork/articles/java/architect-lambdas-part2-2081439.html
        List<User> alUser = Arrays.asList(
                new User.UserBuilder("Kamlesh", "Lakhani")
                        .age(25)
                        .phone("9586331823")
                        .address("my address").user(),
                new User.UserBuilder("Kamlesh", "Lakhani")
                        .age(25)
                        .phone("9586331823")
                        .address("my address").user(),
                new User.UserBuilder("Kamlesh", "Lakhani")
                        .age(25)
                        .phone("9586331823")
                        .address("my address").user(),
                new User.UserBuilder("Kamlesh", "Lakhani")
                        .age(25)
                        .phone("9586331823")
                        .address("my address").user(),
                new User.UserBuilder("Kamlesh", "Lakhani")
                        .age(26)
                        .phone("9586331823")
                        .address("my address").user()
        );

        for(User user: alUser)
        {
            if(user.getAge() > 25)
            {
                AppUtils.loge("Builder Pattern " + AppUtils.getToJsonClass(user));
            }
        }

        EmployeeRecord e1=new EmployeeRecord(1,"kamal","Android Developer",25000,"Ahm");

        AppUtils.loge("Builder Pattern " + e1.showRecord());
        System.out.println("\n");
        EmployeeRecord e2=(EmployeeRecord) e1.getClone();
        AppUtils.loge("Builder Pattern " + e2.showRecord());
    }
}
