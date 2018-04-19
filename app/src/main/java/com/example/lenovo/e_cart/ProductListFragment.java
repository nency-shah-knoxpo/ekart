package com.example.lenovo.e_cart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

public class ProductListFragment extends Fragment {
    private static final String TAG = ProductListFragment.class.getSimpleName();

    private static final String TAG_OUT_OF_STOCK_DIALOG = TAG + ".TAG_OUT_OF_STOCK_DIALOG";
    private static final int REQUEST_OUT_OF_STOCK = 0;

    private static final String ARGS_CATEGORY_ID = TAG + ".ARGS_CATEGORY_ID";

    private ProductAdapter mAdapter;
    private RecyclerView mRecyclerView;
    Boolean OkOrCancel;

    public static ProductListFragment newInstance(UUID categoryId) {
        Bundle args = new Bundle();

        args.putSerializable(ARGS_CATEGORY_ID, categoryId);
        ProductListFragment fragment = new ProductListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product_list, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        CategoryLab categoryLab = CategoryLab.getInstance(getActivity());
        UUID categoryId = (UUID) getArguments().getSerializable(ARGS_CATEGORY_ID);
        List<Product> products = categoryLab.getProducts(categoryId);
        mAdapter = new ProductAdapter(products);
        mRecyclerView.setAdapter(mAdapter);


        updateUI();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public void updateUI() {
        mAdapter.notifyDataSetChanged();
    }


    public class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Product mProduct;
        private TextView mProductNameTV, mProductAmountTV;
        private ImageButton mOutOfStockIB;

        public ProductHolder(View itemView) {
            super(itemView);

            mProductNameTV = (TextView) itemView.findViewById(R.id.txtName);
            mProductAmountTV = (TextView) itemView.findViewById(R.id.txtAmount);
            mOutOfStockIB = (ImageButton) itemView.findViewById(R.id.imgtoggle);

            mOutOfStockIB.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }


        public void bindProduct(Product product) {
            if (product.isOutOfStock()) {
                itemView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.bg_out_of_stock));
            } else {
                itemView.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.transparent));
            }
            mProduct = product;
            mProductNameTV.setText(mProduct.getProductName());
            mProductAmountTV.setText(String.valueOf(mProduct.getProductAmount()));
        }


        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.imgtoggle){
                FragmentManager manager = getFragmentManager();
                AlertDialogFragment dialog = AlertDialogFragment.newInstance(mProduct.getProductID());

                dialog.setTargetFragment(ProductListFragment.this, REQUEST_OUT_OF_STOCK);
                dialog.show(manager, TAG_OUT_OF_STOCK_DIALOG);
            }else{
                Intent intent = DetailProductActivity.getEditIntent(
                        getActivity(),
                        mProduct,
                        (UUID) getArguments().getSerializable(ARGS_CATEGORY_ID)
                );
                startActivity(intent);
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_OUT_OF_STOCK) {

            UUID PRO_ID = (UUID) data.getSerializableExtra("PRO_ID");
            OkOrCancel = data.getBooleanExtra(AlertDialogFragment.OkOrCancel, true);
            Product product = CategoryLab.getInstance(getActivity()).getProduct(
                    (UUID) getArguments().getSerializable(ARGS_CATEGORY_ID),
                    PRO_ID);

            product.setOutOfStock(OkOrCancel);

            updateUI();


        }
    }

    public class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {

        private List<Product> mProducts;

        public ProductAdapter(List<Product> products) {
            mProducts = products;
        }

        @Override
        public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.product_list_layout, parent, false);
            return new ProductHolder(view);
        }

        @Override
        public void onBindViewHolder(ProductHolder holder, int position) {
            Product product = mProducts.get(position);
            holder.bindProduct(product);
        }

        @Override
        public int getItemCount() {

            return mProducts == null ? 0 : mProducts.size();
        }
    }
}

