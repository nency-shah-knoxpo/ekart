package com.example.lenovo.e_cart;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ECartActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    FloatingActionButton addProduct;
    UUID Category_ID;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecart);



        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        addProduct = (FloatingActionButton)findViewById(R.id.btnaddProduct);

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(view.getContext(),DetailProductActivity.class);
                startActivity(i);

            }
        });

        CategoryAdapter adapter = new CategoryAdapter(getSupportFragmentManager());

        List<Category> categories = CategoryLab.getInstance(this).getCategories();

        for(int i=0;i<categories.size();i++){
            adapter.addCategory(categories.get(i));
        }




        mViewPager.setAdapter(adapter);

        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        mTabLayout.setupWithViewPager(mViewPager);

    }


    private class CategoryAdapter extends FragmentStatePagerAdapter{

        private ArrayList<Category> mCategories;

        public CategoryAdapter(FragmentManager fm) {
            super(fm);
            mCategories = new ArrayList<>();
        }

        public void addCategory(Category category){
            mCategories.add(category);
        }

        @Override
        public Fragment getItem(int position) {
            Category category = mCategories.get(position);
            //TODO: replace this with a fragment having list of products
            Category_ID=(UUID)category.getId();
            return CategoryFragment.newInstance((UUID)category.getId());
        }

        @Override
        public int getCount() {
            return mCategories.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mCategories.get(position).getName();
        }
    }
}


