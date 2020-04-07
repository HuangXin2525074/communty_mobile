package com.example.login_demo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.login_demo.base.BaseActivity;
import com.example.login_demo.main.home.AddPostFragment;
import com.example.login_demo.main.home.AddPostImageFragment;
import com.example.login_demo.main.home.HomeFragment;
import com.example.login_demo.main.home.view.HomeDetailActivity;
import com.example.login_demo.main.refresh.RefreshFragment;
import com.example.login_demo.main.user.ForgetPasswordFragment;
import com.example.login_demo.main.user.LoginFragment;
import com.example.login_demo.main.user.RegisterFragment;
import com.example.login_demo.main.user.SettingFragment;
import com.example.login_demo.main.user.UserProfileFragment;
import com.example.login_demo.until.ViewInject;

import androidx.core.app.ActivityCompat;
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

    public static final int EXTERNAL_STORAGE_REQ_CODE = 10 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        sectionStatePagerAdaper = new SectionStatePagerAdaper(getSupportFragmentManager());

        mViewPager =findViewById(R.id.fl_main_content);

        setupViewPager(mViewPager,sectionStatePagerAdaper);

        initCheckListener(this);

        int permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // 请求权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},EXTERNAL_STORAGE_REQ_CODE);
        }

    }

    private void setupViewPager(ViewPager viewPager,SectionStatePagerAdaper adaper){
       // SectionStatePagerAdaper adaper = new SectionStatePagerAdaper(getSupportFragmentManager());
        adaper.addFragment(new HomeFragment(),"HomeFragment");//index:0
        adaper.addFragment(new LoginFragment(),"LoginFragment"); // index:1
        adaper.addFragment(new RegisterFragment(),"RegisterFragment"); // index:2
        adaper.addFragment(new SettingFragment(),"SettingFragment");// index:3
        adaper.addFragment(new UserProfileFragment(),"UserProfileFragment");// index:4
        adaper.addFragment(new ForgetPasswordFragment(),"ForgetPasswordFragment");// index 5
        adaper.addFragment(new ForgetPasswordFragment(),"ForgetPasswordFragment");// index 6
        adaper.addFragment(new HomeDetailActivity(),"HomeDetailActivity");// index7
        adaper.addFragment(new AddPostFragment(),"AddPostFragment");// index8
        adaper.addFragment(new AddPostImageFragment(),"AddPostImageFragment");//index9

        viewPager.setAdapter(adaper);
    }

    public void setViewPager(int fragmentNumber){
        mViewPager.setCurrentItem(fragmentNumber);

    }

    public void setDetailItemId(int postId){
      HomeDetailActivity homeDetailActivity =(HomeDetailActivity)sectionStatePagerAdaper.getItem(7);
      homeDetailActivity.setPostId(postId);
    }

    public void setPostId(int postId){
        AddPostImageFragment addPostImageFragment=(AddPostImageFragment)sectionStatePagerAdaper.getItem(9);
        addPostImageFragment.setPostId(postId);
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
