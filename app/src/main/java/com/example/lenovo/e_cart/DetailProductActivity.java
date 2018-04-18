package com.example.lenovo.e_cart;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import java.util.UUID;

public class DetailProductActivity extends SingleFragmentActivity implements DetailProductFragment.Callback {

    private static final String TAG = DetailProductActivity.class.getSimpleName();
    private static final String EXTRA_CATEGORY_ID = TAG + ".EXTRA_CATEGORY_ID";
    private static final String EXTRA_PRODUCT = TAG + ".EXTRA_PRODUCT";

    public static final String EXTRA_PRODUCT_NAME = TAG + ".EXTRA_PRODUCT_NAME";
    public static final String EXTRA_PRODUCT_DESCRIPTION = TAG + ".EXTRA_PRODUCT_DESCRIPTION";
    public static final String EXTRA_PRODUCT_AMOUNT = TAG + ".EXTRA_PRODUCT_AMOUNT";


    public static Intent getEditIntent(Context context, Product product, UUID categoryId) {
        Intent intent = new Intent(context, DetailProductActivity.class);
        intent.putExtra(EXTRA_PRODUCT, product);
        intent.putExtra(EXTRA_CATEGORY_ID, categoryId);
        return intent;
    }

    @Override
    Fragment getFragment() {
        Product product = getIntent().getParcelableExtra(EXTRA_PRODUCT);
        UUID categoryId = (UUID) getIntent().getSerializableExtra(EXTRA_CATEGORY_ID);
        return DetailProductFragment.newInstance(product, categoryId);
    }

    @Override
    public void onProductCreated(String name, String description, int amount) {
        Intent data = new Intent();
        data.putExtra(EXTRA_PRODUCT_NAME, name);
        data.putExtra(EXTRA_PRODUCT_DESCRIPTION, description);
        data.putExtra(EXTRA_PRODUCT_AMOUNT, amount);
        setResult(Activity.RESULT_OK, data);
        finish();
    }

    @Override
    public void onProductUpdated() {
        finish();
    }
}
