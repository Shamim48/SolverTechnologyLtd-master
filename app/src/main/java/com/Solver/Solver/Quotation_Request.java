package com.Solver.Solver;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.Solver.Solver.Adepter.BrandAdapter;
import com.Solver.Solver.Adepter.CategoryAdapter;
import com.Solver.Solver.Adepter.ClientArrayAdapter;
import com.Solver.Solver.Adepter.ProductAdapter;
import com.Solver.Solver.Adepter.ProductArrayAdapter;
import com.Solver.Solver.Adepter.ProductTypeAdapter;
import com.Solver.Solver.Adepter.SelectedProductArrayAdapter;
import com.Solver.Solver.Adepter.SubCategoryAdapter;
import com.Solver.Solver.ModelClass.Brands;
import com.Solver.Solver.ModelClass.Categories;
import com.Solver.Solver.ModelClass.Factories;
import com.Solver.Solver.ModelClass.Product;
import com.Solver.Solver.ModelClass.Product_types;
import com.Solver.Solver.ModelClass.Quotation_masters;
import com.Solver.Solver.ModelClass.Sub_categories;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class Quotation_Request extends AppCompatActivity implements ProductArrayAdapter.CheckedListener,SelectedProductArrayAdapter.RemoveProductListener {

    DatabaseReference productRef;
    List<Product> productList;
    ArrayList<Product_types> product_typesList;
    ArrayList<Categories> categoriesList;
    ArrayList<Sub_categories> sub_categoriesList;
    ArrayList<Brands> brandsArrayList;

    ArrayList<Product> selectedProduct=new ArrayList<>();

    CategoryAdapter categoryAdapter;
    ProductAdapter adepter;
    SubCategoryAdapter subCategoryAdapter;
    BrandAdapter brandAdapter;

    ProductArrayAdapter productArrayAdapter;

    RecyclerView productRv;
    ProductTypeAdapter productTypeAdapter;
    SelectedProductArrayAdapter selectedProductArrayAdapter;
    private SearchView productSv;
    private AutoCompleteTextView clientAt;
    private Spinner productTypeSp,categorySp,subCategorySp,brandSp;
    private ListView productLv;
    private TextView showProductTv;
    private GridView selectedProductGridView;
    private ListView testList;

    private TextView productNameQDTv;
    private TextView desQDTv;
    private EditText quantityEdQD;
    private Button cancelBtnQD;
    private Button addBtnQD;
    private View quantityDialog;

// DataBase Ref
    DatabaseReference productTypeRef,categoryRef,subCategoryRef ,brandRef;
    String productKey,productId;

    String pKey,pId,currentUserName,clientName;
    int quantity;
    SearchView clientSv;
    EditText quantityEt;
    Button sendQuotationBtn;
    ImageButton closeBtn;
    LinearLayout lnlt;
    ListView clientListView;
    FirebaseAuth auth;
    FirebaseUser user;
    String userId;

    DatabaseReference quotationMasterRef;
    DatabaseReference quotationDetailsRef;
    DatabaseReference clientRef;

    ArrayList<Factories> clientArrayList=new ArrayList<>();
    ClientArrayAdapter clientArrayAdapter;

   //Customer Data
    int customerId;
    String attn_person;
    String cc_person;
    String currentDateTime;

    String quot_key,edit_key;
    Quotation_masters quotation_masters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation__request);

        findId();
        dateTime();

        productTypeRef=FirebaseDatabase.getInstance().getReference().child("product_types");
        categoryRef=FirebaseDatabase.getInstance().getReference().child("categories");
        productRef= FirebaseDatabase.getInstance().getReference().child("product");
        subCategoryRef=FirebaseDatabase.getInstance().getReference().child("sub_categories");
        brandRef=FirebaseDatabase.getInstance().getReference().child("brands");
       // quotationMasterRef=FirebaseDatabase.getInstance().getReference().child("quotation_req_masters");
        productList=new ArrayList<>();

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        quotationMasterRef= FirebaseDatabase.getInstance().getReference().child("quotation_req_masters");
        quotationDetailsRef= FirebaseDatabase.getInstance().getReference().child("quotation_req_details");

        clientRef=FirebaseDatabase.getInstance().getReference().child("factories");

        lnlt.setVisibility(View.GONE);
        currentUserName=user.getDisplayName();
        quotation_masters=new Quotation_masters();

        Intent intent=getIntent();
        productKey=intent.getStringExtra("productKey");
        productId=intent.getStringExtra("productId");
        quot_key=intent.getStringExtra("Quot_key");
        edit_key=intent.getStringExtra("Quot_edit");
        try {


        if(!(quot_key.equals(""))){

        quotationMasterRef.child(quot_key).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                quotation_masters=dataSnapshot.getValue(Quotation_masters.class);

                for (Product product:quotation_masters.getProductList()){

                    selectedProduct.add(product);

                }
                selectedProductArrayAdapter=new SelectedProductArrayAdapter(Quotation_Request.this,selectedProduct);
                selectedProductGridView.setAdapter(selectedProductArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });}
        }catch (Exception e){

        }
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lnlt.setVisibility(View.GONE);
            }
        });

        clientSv.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lnlt.setVisibility(View.VISIBLE);
            }
        });

        clientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    Factories client=data.getValue(Factories.class);
                    clientArrayList.add(client);
                }

                clientArrayAdapter=new ClientArrayAdapter(Quotation_Request.this,clientArrayList);
                clientListView.setAdapter(clientArrayAdapter);
                clientArrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        clientListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                clientName=clientArrayList.get(i).getCompany_name();
                customerId=clientArrayList.get(i).getCustomer_id();
                attn_person=clientArrayList.get(i).getAttn_name();
                cc_person=clientArrayList.get(i).getCc_name();
                clientSv.setQuery(clientName,false);
                lnlt.setVisibility(View.GONE);
                clientArrayAdapter.notifyDataSetChanged();
            }
        });

        clientSv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                clientArrayAdapter.getFilter().filter(s);
                return false;
            }
        });

        sendQuotationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(selectedProduct.isEmpty()){

                    toast("Please Select Product..");

                    return;

                }

                clientName=clientSv.getQuery().toString();
               // String  qut=quantityEt.getText().toString();
                //quantity=Integer.parseInt(qut);
                String qut_key= quotationMasterRef.push().getKey();
                String qur_details_key=quotationDetailsRef.push().getKey();
            /* public  Quotation_masters(String qut_key,String qut_no, int customer_id, int company_id, String attn_person, String attn_email, String cc_person, String cc_email, String date,
                        Double total_amount, Double discount, Double vat, Double tax, Double vat_amount, Double tax_amount, Double grand_amount, int user_id,
                String payment_option, String delivery_option, String checked_by, String authorized, String request_user, String request_status, int print, String created_at, String updated_at) {

*/
                Quotation_masters quotation_masters=new Quotation_masters(qut_key,"Q-2019080501", customerId, 101, attn_person, "", cc_person, "", "", 0.00, 0.00, 0.00, 0.00, 0.00, 3000.00, 0.00, 10012, "", "", "", "", currentUserName,"Pending",0, currentDateTime, "",selectedProduct);

               // Quotation_details quotation_details=new Quotation_details(qur_details_key,"Q-2019080502", 101, customerId, productId, 550.00, 4, "", 2200.00, "2019-08-05 09:17:44", "2019-08-05 09:17:44");

                quotationMasterRef.child(qut_key).setValue(quotation_masters).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"Quotation Master upload Success",Toast.LENGTH_SHORT).show();
                            selectedProduct.clear();
                            clientSv.setQuery(null,false);
                            productSv.setQuery(null,false);
                            selectedProductArrayAdapter.notifyDataSetChanged();
                            productArrayAdapter.notifyDataSetChanged();
                           Intent i=new Intent(getApplicationContext(),Quotation_Show.class);
                           startActivity(i);
                        }
                    }
                });

                productSv.setOnCloseListener(new SearchView.OnCloseListener() {
                    @Override
                    public boolean onClose() {
                        productArrayAdapter.notifyDataSetChanged();

                        return true;
                    }
                });
                clientSv.setOnCloseListener(new SearchView.OnCloseListener() {
                    @Override
                    public boolean onClose() {
                        clientArrayAdapter.notifyDataSetChanged();
                        return false;
                    }
                });

               /* quotationDetailsRef.child(qur_details_key).setValue(quotation_details).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Quotation Details upload Success",Toast.LENGTH_SHORT).show();

                        //    quantityEt.setText("");
                            clientSv.setQuery("",true);
                        }
                    }
                });*/

            }
        });


        showProductTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showProductTv.setText("");

                try {
                    for (Product product:selectedProduct){
                        showProductTv.append(product.getProduct_name()+"\n"+product.getQuantity()+"\n\n");

                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Please Chose product",Toast.LENGTH_SHORT).show();

                }
            }
        });

        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productList.clear();
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    Product product=data.getValue(Product.class);
                    productList.add(product);
                }

                Collections.reverse(productList);
                productArrayAdapter=new ProductArrayAdapter(Quotation_Request.this,productList);
               // LinearLayoutManager linearLayoutManager=new LinearLayoutManager(Quotation_Request.this);
               // productRv.setLayoutManager(linearLayoutManager);
               // productRv.setAdapter(productArrayAdapter);
               /* Common_Resouces common_resouces=new Common_Resouces();
                common_resouces.setCommon_clientList(productList);*/

               productLv.setAdapter(productArrayAdapter);
               productArrayAdapter.setCheckedListener((ProductArrayAdapter.CheckedListener) Quotation_Request.this);
                productArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(),"Error:"+databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

       /* productLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                productId=productList.get(i).getProduct_id();
                productKey=productList.get(i).getProduct_key();

                addSelectedProduct(i);

               *//* Intent intent=new Intent(getApplicationContext(),SendQuotation.class);
                intent.putExtra("productKey",productKey);
                intent.putExtra("productId",productId);
                startActivity(intent);
*//*
            }
        });*/

        productLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                productId=productList.get(i).getProduct_id();
                productKey=productList.get(i).getProduct_key();

            //    addSelectedProduct(i);
               // productList.notify();
                productArrayAdapter.notifyDataSetChanged();

            }
        });

        productSv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                productArrayAdapter.getFilter().filter(s);
                productArrayAdapter.notifyDataSetChanged();

                return false;
            }
        });
    }

    public void dateTime(){
        Calendar calForDate = Calendar.getInstance(); //2019-08-05 09:16:53
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
         currentDateTime = currentDateFormat.format(calForDate.getTime());

       // Calendar calForTime = Calendar.getInstance();
       // SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm a");
       // currentTime = currentTimeFormat.format(calForTime.getTime());
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

/*        categoriesList.clear();

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
*/
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

     //   testList=findViewById(R.id.testListId);
       // productLv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        clientSv=findViewById(R.id.clientSvId);
        sendQuotationBtn =findViewById(R.id.sendQuotationId);
        closeBtn=findViewById(R.id.closeBtnSQId);
        lnlt=findViewById(R.id.lltASqLVId);
        clientListView=findViewById(R.id.clientTagLVId);

        product_typesList=new ArrayList<>();
        categoriesList=new ArrayList<>();
        sub_categoriesList=new ArrayList<>();
        brandsArrayList=new ArrayList<>();
        showProductTv=findViewById(R.id.showProductTvId);
        selectedProductGridView=findViewById(R.id.selectedProductGridViewId);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void getCheckListener(final int position) {

      //  addSelectedProduct(position);

        AlertDialog.Builder alert=new AlertDialog.Builder(Quotation_Request.this);
         quantityDialog = getLayoutInflater().inflate(R.layout.quantity_lot, null);
         findIdByDialogViw();
        alert.setView(quantityDialog);
        final AlertDialog alertDialogByQuantity=alert.create();
        alertDialogByQuantity.setCanceledOnTouchOutside(false);
        alertDialogByQuantity.show();

        try {

            productNameQDTv.setText(productList.get(position).getProduct_name());
            desQDTv.setText(Html.fromHtml(productList.get(position).getDescription(),Html.FROM_HTML_MODE_LEGACY));

        }catch (Exception e){

        }

      addBtnQD.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              try {
              String quantity = quantityEdQD.getText().toString().trim();
              int qut=Integer.parseInt(quantity);
              //companyNameEt.setText(bloodGroups);

              if (quantity.isEmpty()) {
                  quantityEdQD.setError("Please Enter Client/Company Name..!");
                  return;
              }

              productList.get(position).setQuantity(qut);
              selectedProduct.add(productList.get(position));
              Toast.makeText(getApplicationContext(),"You Chose "+productList.get(position).getProduct_name(),Toast.LENGTH_SHORT).show();
              Toast.makeText(getApplicationContext(),"Selected Product "+selectedProduct.size(),Toast.LENGTH_SHORT).show();

                  selectedProductArrayAdapter=new SelectedProductArrayAdapter(Quotation_Request.this,selectedProduct);
                  selectedProductGridView.setAdapter(selectedProductArrayAdapter);
                  selectedProductArrayAdapter.setRemoveProduct(Quotation_Request.this);
                  selectedProductArrayAdapter.notifyDataSetChanged();

              }catch (Exception e){

              }

              alertDialogByQuantity.cancel();

          }
      });

        cancelBtnQD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogByQuantity.dismiss();
            }
        });
        //  addSelectedProduct(position);
     // selectedProduct.add(productList.get(position));
      //  Toast.makeText(getApplicationContext(),"You Chose "+productList.get(position).getProduct_name(),Toast.LENGTH_SHORT).show();
      //  Toast.makeText(getApplicationContext(),"Selected Product "+selectedProduct.size(),Toast.LENGTH_SHORT).show();

    }

    @Override
    public void removeProduct(final int position) {

       selectedProduct.remove(productList.get(position));
        Toast.makeText(getApplicationContext(),"You remove "+productList.get(position).getProduct_name(),Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),"Selected Product "+selectedProduct.size(),Toast.LENGTH_SHORT).show();

    }

    @Override
    public void removeGridProduct(int position) {

        selectedProduct.remove(selectedProduct.get(position));
        Toast.makeText(getApplicationContext(),"You remove "+productList.get(position).getProduct_name(),Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),"Selected Product "+selectedProduct.size(),Toast.LENGTH_SHORT).show();
         selectedProductArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void editProductSelectedPdt(int position) {

       // editSelectedProduct(position);

        toast("Coming soon");

    }

    public void editSelectedProduct(final int i){

        AlertDialog.Builder alert=new AlertDialog.Builder(Quotation_Request.this);
        View viewClientForm = getLayoutInflater().inflate(R.layout.quantity_lot, null);
        findIdByDialogViw();
        alert.setView(viewClientForm);
        final AlertDialog alertDialogByQuantity=alert.create();
        alertDialogByQuantity.setCanceledOnTouchOutside(false);
        alertDialogByQuantity.show();
        addBtnQD.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               // quantityEdQD.setText(productList.get(i).getQuantity());
                String quantity = quantityEdQD.getText().toString().trim();
                //companyNameEt.setText(bloodGroups);
                int qut=Integer.parseInt(quantity);

                if (quantity.isEmpty()) {
                    quantityEdQD.setError("Please Enter Client/Company Name..!");
                    return;
                }

                productList.get(i).setQuantity(qut);
                selectedProduct.add(productList.get(i));
               Toast.makeText(getApplicationContext(),"You Chose "+productList.get(i).getProduct_name(),Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),"Selected Product "+selectedProduct.size(),Toast.LENGTH_SHORT).show();

            }
        });

        cancelBtnQD.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                alertDialogByQuantity.dismiss();
            }
        });

    }

    private void toast(String msg) {

        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }

    private void findIdByDialogViw() {

        productNameQDTv=quantityDialog.findViewById(R.id.productNameTvQDId);
        desQDTv=quantityDialog.findViewById(R.id.desTvQDId);
        quantityEdQD=quantityDialog.findViewById(R.id.quantityEtQDId);
        cancelBtnQD=quantityDialog.findViewById(R.id.cancelBtnQDId);
        addBtnQD=quantityDialog.findViewById(R.id.addBtnQDId);

    }
}
