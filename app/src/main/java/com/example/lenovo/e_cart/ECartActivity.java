package com.example.lenovo.e_cart;

import android.app.Activity;
import android.content.Intent;
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

public class ECartActivity extends AppCompatActivity {
    private static final int REQUEST_ADD_PRODUCT = 0;

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FloatingActionButton mAddProductFAB;
    private CategoryAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecart);

        mAddProductFAB = (FloatingActionButton) findViewById(R.id.btnaddProduct);
        mAddProductFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), DetailProductActivity.class);
                startActivityForResult(i, REQUEST_ADD_PRODUCT);

            }
        });


        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mAdapter = new CategoryAdapter(getSupportFragmentManager());
        List<Category> categories = CategoryLab.getInstance(this).getCategories();
        for (int i = 0; i < categories.size(); i++) {
            mAdapter.addCategory(categories.get(i));
        }
        mViewPager.setAdapter(mAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == REQUEST_ADD_PRODUCT && resultCode == Activity.RESULT_OK){
            String name = data.getStringExtra(DetailProductActivity.EXTRA_PRODUCT_NAME);
            String description = data.getStringExtra(DetailProductActivity.EXTRA_PRODUCT_DESCRIPTION);
            int amount = data.getIntExtra(DetailProductActivity.EXTRA_PRODUCT_AMOUNT,0);

            Product newProduct = new Product();
            newProduct.setProductName(name);
            newProduct.setProductDesc(description);
            newProduct.setProductAmount(amount);
            newProduct.setOutOfStock(true);

            int currentTabPosition = mViewPager.getCurrentItem();
            Category category = mAdapter.getCategory(currentTabPosition);

            CategoryLab.getInstance(this).addProduct(newProduct,category);

            ((ProductListFragment)mAdapter.getItem(currentTabPosition)).updateUI();

        }
    }

    private class CategoryAdapter extends FragmentStatePagerAdapter {

        private ArrayList<Category> mCategories;
        private ArrayList<Fragment> mFragments;

        public CategoryAdapter(FragmentManager fm) {
            super(fm);
            mCategories = new ArrayList<>();
            mFragments = new ArrayList<>();
        }

        public void addCategory(Category category) {
            mCategories.add(category);
            mFragments.add(ProductListFragment.newInstance(category.getId()));
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mCategories.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mCategories.get(position).getName();
        }

        public Category getCategory(int index){
            return mCategories.get(index);
        }
    }
}


