package com.Solver.Solver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.Solver.Solver.Adepter.BrandAdapter;
import com.Solver.Solver.Adepter.CategoryAdapter;
import com.Solver.Solver.Adepter.ProductAdapter;
import com.Solver.Solver.Adepter.ProductArrayAdapter;
import com.Solver.Solver.Adepter.ProductTypeAdapter;
import com.Solver.Solver.Adepter.SubCategoryAdapter;
import com.Solver.Solver.ModelClass.Brands;
import com.Solver.Solver.ModelClass.Categories;
import com.Solver.Solver.ModelClass.Product;
import com.Solver.Solver.ModelClass.Product_types;
import com.Solver.Solver.ModelClass.Sub_categories;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Quotation_Request extends AppCompatActivity {

    DatabaseReference productRef;
    List<Product> productList;
    ArrayList<Product_types> product_typesList;
    ArrayList<Categories> categoriesList;
    ArrayList<Sub_categories> sub_categoriesList;
    ArrayList<Brands> brandsArrayList;

    CategoryAdapter categoryAdapter;
    ProductAdapter adepter;
    SubCategoryAdapter subCategoryAdapter;
    BrandAdapter brandAdapter;

    ProductArrayAdapter productArrayAdapter;

    RecyclerView productRv;
    ProductTypeAdapter productTypeAdapter;

    private SearchView productSv;
    private AutoCompleteTextView clientAt;
    private Spinner productTypeSp,categorySp,subCategorySp,brandSp;
    private ListView productLv;
// DataBase Ref
    DatabaseReference productTypeRef,categoryRef,subCategoryRef ,brandRef;

    String productKey,productId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation__request);

        findId();
        productTypeRef=FirebaseDatabase.getInstance().getReference().child("product_types");
        categoryRef=FirebaseDatabase.getInstance().getReference().child("categories");
        productRef= FirebaseDatabase.getInstance().getReference().child("product");
        subCategoryRef=FirebaseDatabase.getInstance().getReference().child("sub_categories");
        brandRef=FirebaseDatabase.getInstance().getReference().child("brands");
        productList=new ArrayList<>();

        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productList.clear();
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    Product product=data.getValue(Product.class);
                    productList.add(product);
                }

                productArrayAdapter=new ProductArrayAdapter(Quotation_Request.this,productList);
               // LinearLayoutManager linearLayoutManager=new LinearLayoutManager(Quotation_Request.this);
               // productRv.setLayoutManager(linearLayoutManager);
               // productRv.setAdapter(productArrayAdapter);
               /* Common_Resouces common_resouces=new Common_Resouces();
                common_resouces.setCommon_clientList(productList);*/

               productLv.setAdapter(productArrayAdapter);
                productArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(),"Error:"+databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        productLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                productId=productList.get(i).getProduct_id();
                productKey=productList.get(i).getProduct_key();

                Intent intent=new Intent(getApplicationContext(),SendQuotation.class);
                intent.putExtra("productKey",productKey);
                intent.putExtra("productId",productId);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        product_typesList.clear();
        productTypeRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data:dataSnapshot.getChildren()){
                    Product_types product_types=data.getValue(Product_types.class);
                    product_typesList.add(product_types);
                }
                productTypeAdapter=new ProductTypeAdapter(Quotation_Request.this,product_typesList);
                productTypeSp.setAdapter(productTypeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        categoriesList.clear();
        categoryRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data:dataSnapshot.getChildren()){
                    Categories categories=data.getValue(Categories.class);
                    categoriesList.add(categories);
                }
                categoryAdapter=new CategoryAdapter(Quotation_Request.this,categoriesList);
                categorySp.setAdapter(categoryAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
 sub_categoriesList.clear();
        subCategoryRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data:dataSnapshot.getChildren()){
                    Sub_categories sub_categories=data.getValue(Sub_categories.class);
                    sub_categoriesList.add(sub_categories);
                }
                subCategoryAdapter=new SubCategoryAdapter(Quotation_Request.this,sub_categoriesList);
                subCategorySp.setAdapter(subCategoryAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        brandsArrayList.clear();
        brandRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data:dataSnapshot.getChildren()){
                    Brands brands=data.getValue(Brands.class);
                    brandsArrayList.add(brands);
                }
                brandAdapter=new BrandAdapter(Quotation_Request.this,brandsArrayList);
                brandSp.setAdapter(brandAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void findId() {

       // productRv=findViewById(R.id.productRvId);
        productSv=findViewById(R.id.productSvId);
      //  clientAt=findViewById(R.id.clientATId);
        productTypeSp=findViewById(R.id.productTypeSpId);
        categorySp=findViewById(R.id.categorySpId);
        subCategorySp=findViewById(R.id.subCategorySpId);
        brandSp=findViewById(R.id.brandSpId);
        productLv=findViewById(R.id.productLvId);

        product_typesList=new ArrayList<>();
        categoriesList=new ArrayList<>();
        sub_categoriesList=new ArrayList<>();
        brandsArrayList=new ArrayList<>();



    }

}
