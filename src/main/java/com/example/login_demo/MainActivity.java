package com.example.login_demo;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.login_demo.base.BaseActivity;
import com.example.login_demo.main.home.AddPostFragment;
import com.example.login_demo.main.home.HomeFragment;
import com.example.login_demo.main.home.view.HomeDetailActivity;
import com.example.login_demo.main.refresh.RefreshFragment;
import com.example.login_demo.main.user.ForgetPasswordFragment;
import com.example.login_demo.main.user.LoginFragment;
import com.example.login_demo.main.user.RegisterFragment;
import com.example.login_demo.main.user.SettingFragment;
import com.example.login_demo.main.user.UserProfileFragment;
import com.example.login_demo.until.ViewInject;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;


@ViewInject(mainlayoutid = R.layout.activity_main)
public class MainActivity extends BaseActivity {


    @BindView(R.id.rb_main_home)
    RadioButton rbMainHome;
    @BindView(R.id.rb_main_me)
    RadioButton rbMainMe;
    @BindView(R.id.rg_main_top)
    RadioGroup rgMainTop;
    @BindView(R.id.fl_main_bottom)
    FrameLayout flMainBottom;

    private SectionStatePagerAdaper sectionStatePagerAdaper;

    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        sectionStatePagerAdaper = new SectionStatePagerAdaper(getSupportFragmentManager());

        mViewPager =findViewById(R.id.fl_main_content);

        setupViewPager(mViewPager);

        initCheckListener(this);

    }

    private void setupViewPager(ViewPager viewPager){
        SectionStatePagerAdaper adaper = new SectionStatePagerAdaper(getSupportFragmentManager());
        adaper.addFragment(new HomeFragment(),"HomeFragment");//index:0
        adaper.addFragment(new LoginFragment(),"LoginFragment"); // index:1
        adaper.addFragment(new RegisterFragment(),"RegisterFragment"); // index:2
        adaper.addFragment(new SettingFragment(),"SettingFragment");// index:3
        adaper.addFragment(new UserProfileFragment(),"UserProfileFragment");// index:4
        adaper.addFragment(new ForgetPasswordFragment(),"ForgetPasswordFragment");// index 5
        adaper.addFragment(new ForgetPasswordFragment(),"ForgetPasswordFragment");// index 6
        adaper.addFragment(new HomeDetailActivity(),"HomeDetailActivity");// index7
        adaper.addFragment(new AddPostFragment(),"AddPostFragment");// index8
        viewPager.setAdapter(adaper);
    }

    public void setViewPager(int fragmentNumber){
        mViewPager.setCurrentItem(fragmentNumber);

    }

    @Override
    public void afterBindView() {

    }

    private void initCheckListener(Context context) {


        rbMainHome.setChecked(true);

        rgMainTop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_main_home:
                        setViewPager(0);
                        break;
                    case R.id.rb_main_me:
                        setViewPager(4);
                        break;
                }
            }
        });



    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
