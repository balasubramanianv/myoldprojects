package com.example.hotelmange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Order extends Activity implements OnItemSelectedListener {
	

	JSONArray jArray;
	String result = null;
	InputStream is = null;
	StringBuilder sb=null;
	
	
	private Button add, selectOk, selectCancel, loginCancel, loginOk,
			selectRemove;
	private Button minus;

	private String a, selecteditemname, selecteditemprice, selecteditemId,
			selecteditemPricePerUnit;
	private Integer b = 1, inc, value, value2, value3, c, dec;
	private EditText Quantity;
	private EditText Itemname, ItemPrice , Remarks;

	protected ListAdapter arrayadapter;
	protected ListView OrderedItemList;
	protected Cursor cursor;
	protected Cursor cursor1;
	private TextView EditTotal;
	private Spinner spinner2;
	private String selectedTable;
	private String ServerName;
	private String SourceName;
	
	public static  String tid;
	
	Context context = this;

	// Array of strings storing country names
	String[] countries = new String[] { "Pizza with spinach",
			"Pepperoni Pizza", "Italian-Thai Pizza", "Muffuleta Pizza",
			"Seafood", "Sicilian", "Greek Style"

	};

	// Array of integers points to images stored in /res/drawable-ldpi/
	int[] flags = new int[] { R.drawable.p1, R.drawable.p2, R.drawable.p3,
			R.drawable.p5, R.drawable.p6

	};

	// Array of strings to store currencies
	String[] currency = new String[] {
			"Pizza tossed with a quickly cooked sauce of tomatoes, "
					+ "onion, bell pepper, corn, black beans, salsa and taco seasoning.",
			"Tomato, mozzarella, German sausage, oregano, oil",

			"The most delightful pizza with tomato sauce, cheese, tomatos, field mushrooms, onion",
			"Chicken, beef, lamb. The topping up to your choice and the "
					+ "Muffin sauce is added which will taste you in the flavour of muffins",

			"For all the lovers of seafood. The healthiest pizza in house",
			"You will taste the most delicious food in city after choosing this pizza. Best selling pizza at Rafe",
			"Pizza with Greek Style. inside have nuts and topping up to your choice"

	};

	String[] price = new String[] { "12", "11", "12", "11", "13", "12"

	};

	String[] countries1 = new String[] { "Ice Tea", "Orange Juice", "Drinks",
			"Drinks", "Drinks", "Drinks"

	};

	// Array of integers points to images stored in /res/drawable-ldpi/
	int[] flags1 = new int[] { R.drawable.dr1, R.drawable.dr2, R.drawable.dr3,
			R.drawable.dr4, R.drawable.dr5, R.drawable.dr6

	};

	// Array of strings to store currencies
	String[] price1 = new String[] { "12", "11", "12", "11", "13", "12"

	};

	String[] currency1 = new String[] {
			"Pizza tossed with a quickly cooked sauce of tomatoes, "
					+ "onion, bell pepper, corn, black beans, salsa and taco seasoning.",
			"Tomato, mozzarella, German sausage, oregano, oil",

			"The most delightful pizza with tomato sauce, cheese, tomatos, field mushrooms, onion",
			"Chicken, beef, lamb. The topping up to your choice and the "
					+ "Muffin sauce is added which will taste you in the flavour of muffins",

			"For all the lovers of seafood. The healthiest pizza in house",
			"You will taste the most delicious food in city after choosing this pizza. Best selling pizza at Rafe",
			"Pizza with Greek Style. inside have nuts and topping up to your choice"

	};
	String[] countries2 = new String[] { "Capuccino", "Coffee", "Coffee",
			"Coffee", "Coffee", "Coffee"

	};

	// Array of integers points to images stored in /res/drawable-ldpi/
	int[] flags2 = new int[] { R.drawable.d1, R.drawable.d2, R.drawable.d3,
			R.drawable.d4, R.drawable.d5, R.drawable.d6

	};

	// Array of strings to store currencies
	String[] price2 = new String[] { "12", "11", "12", "11", "13", "12"

	};

	private ListView listView;

	/** Called when the activity is first created. */
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_page);

		   StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectDiskReads().detectDiskWrites().detectNetwork()
					.penaltyLog().build()); 
			        
				
		
		addItemsOnSpinner2();
		// addListenerOnButton();

		// addListenerOnSpinnerItemSelection();
		// Toast.makeText(getApplicationContext(), selectedTable,
		// Toast.LENGTH_LONG).show();
		selectedTable = spinner2.getSelectedItem().toString();

		spinner2.setOnItemSelectedListener(this);
  
		final SQLiteDatabase db = (new DataBaseHelper(this))
				.getWritableDatabase();
		// Each row in the list stores country name, currency and flag
		final Cursor cursor_menu = db.rawQuery(
				"SELECT _id,Menu,SubMenu,Price FROM MenuItem WHERE Menu = ?", new String[] {"Pizza"});

		// Keys used in Hashmap
		String[] from = { "SubMenu", "Price" };

		// Ids of views in listview_layout
		int[] to = { R.id.item_name, R.id.item_price };

		
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(
				getApplicationContext(), R.layout.item_details_view,
				cursor_menu, from, to);
		// Getting a reference to listview of main.xml layout file
		ListView listView = (ListView) findViewById(R.id.listView_item);

		// Setting the adapter to the listView
		listView.setAdapter(adapter);
		
		final Cursor cursor_mainmenu = db.rawQuery(
				"SELECT DISTINCT Menu as _id, Menu FROM MenuItem", new String[] {});

		// Keys used in Hashmap
		String[] frommenu = {"Menu"};
     
		// Ids of views in listview_layout
		int[] tomenu = { R.id.item_name};

		
		SimpleCursorAdapter adapter_menu = new SimpleCursorAdapter(
				getApplicationContext(), R.layout.menu,
				cursor_mainmenu, frommenu, tomenu);
		// Getting a reference to listview of main.xml layout file
		ListView listView_menu = (ListView) findViewById(R.id.listView_menu);

		// Setting the adapter to the listView
		listView_menu.setAdapter(adapter_menu);
	
		
		listView_menu.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {

		String selectedMenuName = ((TextView) view
						.findViewById(R.id.item_name)).getText()
						.toString();
					
				final Cursor cursor_menu = db.rawQuery(
						"SELECT _id, Menu , SubMenu,Price FROM MenuItem WHERE Menu = ?", new String[] {""+selectedMenuName});

				// Keys used in Hashmap
				String[] from = { "SubMenu", "Price" };

				// Ids of views in listview_layout
				int[] to = { R.id.item_name, R.id.item_price };

				
				SimpleCursorAdapter adapter = new SimpleCursorAdapter(
						getApplicationContext(), R.layout.item_details_view,
						cursor_menu, from, to);
				// Getting a reference to listview of main.xml layout file
				ListView listView = (ListView) findViewById(R.id.listView_item);

				// Setting the adapter to the listView
				listView.setAdapter(adapter);		
				
				
				
				
				
			}
			
		});
		
		 
		
		Cursor cursor3 = db
				.rawQuery(
						"SELECT _id,ServerName,SourceName FROM Setting ",
						new String[] { });

		if (cursor3.getCount() == 1) {
			cursor3.moveToFirst();

			ServerName = (cursor3.getString(cursor3
					.getColumnIndex("ServerName")));

		
		    SourceName=(cursor3.getString(cursor3
					.getColumnIndex("SourceName")));
			

		}
	 
		
		
		

		Button btn_mainmenu = (Button) findViewById(R.id.btn_order_mainmenu);

		btn_mainmenu.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {

				final Dialog dialog = new Dialog(Order.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

				dialog.setContentView(R.layout.menu_login);

				loginCancel = (Button) dialog
						.findViewById(R.id.btn_login_cancel);
				loginOk = (Button) dialog.findViewById(R.id.btn_login_ok);

				loginCancel.setOnClickListener(new View.OnClickListener() {

					public void onClick(View view) {

						dialog.cancel();

					}
				});
				loginOk.setOnClickListener(new View.OnClickListener() {

					public void onClick(View view) {
						final EditText passwd = (EditText) dialog
								.findViewById(R.id.txt_pwd);
						final String loginPasswd = passwd.getText().toString();

						if (loginPasswd.equals("admin")) {
							Intent myIntent = new Intent(
									getApplicationContext(), MainActivity.class);
							startActivity(myIntent);

						}

						else {

							showNoticeDialogBox("Message",
									"Incorrect  Password");
						}
					}
				});

				dialog.show();

			}
		});

		Button btn_order = (Button) findViewById(R.id.btn_Order);

		btn_order.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);

				// set title
				alertDialogBuilder.setTitle("Alert");

				// set dialog message
				alertDialogBuilder
						.setMessage("Do you want to Confirm the Order?")
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										selectedTable = spinner2
												.getSelectedItem().toString();
										
										db.execSQL("INSERT INTO Ordereditem SELECT * FROM customer WHERE CustTable ='"+selectedTable+"'");						
																		
										db.execSQL("DELETE FROM customer WHERE CustTable ='"+selectedTable+"'");
								         
										        UpdateList();
										

									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// if this button is clicked, just close
										// the dialog box and do nothing
										dialog.cancel();
									}
								});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();

			}
		});

		Button btn_Submit = (Button) findViewById(R.id.btn_submit);

		btn_Submit.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				
				syncfromdbtid();
				
			//	ConnectivityManager connMgr = (ConnectivityManager) this
			//			.getSystemService(Context.CONNECTIVITY_SERVICE);

				
				ConnectivityManager connMgr =(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
						
				android.net.NetworkInfo wifi = connMgr
						.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

				android.net.NetworkInfo mobile = connMgr
						.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				
			if( (wifi.isAvailable()) || (mobile.isAvailable()))	
			{

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);
             
				// set title
				alertDialogBuilder.setTitle("Alert");

				// set dialog message
				alertDialogBuilder
						.setMessage("Do you want to Confirm the Order?")
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										selectedTable = spinner2
												.getSelectedItem().toString();

										// db.execSQL("INSERT INTO  Ordereditem   SELECT * FROM customer WHERE CustTable = "+selectedTable
										// );
										// db.execSQL("DELETE FROM customer WHERE CustTable = "+selectedTable
										// );
										// Intent myIntent = new
										// Intent(getApplicationContext(),Welcome.class);
										// startActivity(myIntent);

									//	db.execSQL("INSERT INTO  Ordereditem   SELECT _id,CustTable FROM customer WHERE ");
									
		db.execSQL("INSERT INTO Ordereditem SELECT * FROM customer WHERE CustTable ='"+selectedTable+"'");						
										
		db.execSQL("DELETE FROM customer WHERE CustTable ='"+selectedTable+"'");
         
		        UpdateList();
		        
		        startSync();
		    //    db.execSQL("DELETE FROM Ordereditem");
		           db.execSQL("DELETE FROM MenuItem");

		        syncfromdbmenu();
		        
				Toast.makeText(getApplicationContext(), "Data has been synched successfully", Toast.LENGTH_SHORT).show();
			      db.execSQL("DELETE FROM Ordereditem");
	
									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// if this button is clicked, just close
										// the dialog box and do nothing
										dialog.cancel();
									}
								});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();

			}
			
			else
			{
		
		  Toast.makeText(getApplicationContext(), "No Internet Connection Available", Toast.LENGTH_LONG).show();		
				
			}
			
			
			
			
			}
			
			
			
			
			
		});

		
		
		
		
		
		
		
		Button btn_Later = (Button) findViewById(R.id.btn_later);

		btn_Later.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);

				// set title
				alertDialogBuilder.setTitle("Alert");

				// set dialog message
				alertDialogBuilder
						.setMessage("Do you want to Confirm the Order Later?")
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {

								//		db.execSQL("INSERT INTO  Ordereditem   SELECT * FROM customer");
										// db.execSQL("DELETE FROM customer");
				Toast.makeText(getApplicationContext(), "Later Can take Order", Toast.LENGTH_SHORT).show();
				
         
									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// if this button is clicked, just close
										// the dialog box and do nothing
										dialog.cancel();
									}
								});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();

			}
		});

		/*
		 * 
		 * 
		 * Button btnpizza=(Button)findViewById(R.id.btn_layout_pizza);
		 * 
		 * btnpizza.setOnClickListener(new View.OnClickListener() {
		 * 
		 * public void onClick(View view) {
		 * 
		 * 
		 * List<HashMap<String,String>> aList1 = new
		 * ArrayList<HashMap<String,String>>();
		 * 
		 * for(int i=0;i<5;i++){ HashMap<String, String> hm = new
		 * HashMap<String,String>(); hm.put("txt", "" + countries[i]);
		 * hm.put("cur","" + currency[i]); hm.put("price", ""+price[i]);
		 * hm.put("flag", Integer.toString(flags[i]) ); aList1.add(hm); }
		 * 
		 * // Keys used in Hashmap String[] from = { "flag","txt","cur","price"
		 * };
		 * 
		 * // Ids of views in listview_layout int[] to = {
		 * R.id.item_photo,R.id.item_name,R.id.item_desc,R.id.item_price};
		 * 
		 * // Instantiating an adapter to store each items //
		 * R.layout.listview_layout defines the layout of each item
		 * SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList1,
		 * R.layout.item_details_view, from, to);
		 * 
		 * // Getting a reference to listview of main.xml layout file ListView
		 * listView = ( ListView ) findViewById(R.id.listView_item);
		 * 
		 * // Setting the adapter to the listView listView.setAdapter(adapter);
		 * 
		 * } });
		 */

		final LinearLayout layoutpizza = (LinearLayout) findViewById(R.id.layout_pizza);

		layoutpizza.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {

				final Cursor cursor_menu = db.rawQuery(
						"SELECT _id,Menu,SubMenu,Price FROM MenuItem WHERE Menu = ?",
						new String[] {"Pizza"});

				// Keys used in Hashmap
				String[] from = { "SubMenu", "Price" };

				// Ids of views in listview_layout
				int[] to = { R.id.item_name, R.id.item_price };

				// Instantiating an adapter to store each items
				// R.layout.listview_layout defines the layout of each item
				// android.support.v4.widget.SimpleCursorAdapter adapter = new
				// android.support.v4.widget.SimpleCursorAdapter(getBaseContext(),
				// cursor_menu, R.layout.item_details_view, from, to);
				SimpleCursorAdapter adapter = new SimpleCursorAdapter(
						getApplicationContext(), R.layout.item_details_view,
						cursor_menu, from, to);
				// Getting a reference to listview of main.xml layout file
				ListView listView = (ListView) findViewById(R.id.listView_item);

				// Setting the adapter to the listView
				listView.setAdapter(adapter);

				/*
				 * while(cursor.moveToNext()) {
				 * db_results.add(String.valueOf(cursor
				 * .getDouble(cursor.getColumnIndex("ItemID")))); }
				 */

			}
		});

		
		
		
		
		
		
		

		/*
		 * Button btndrinks=(Button)findViewById(R.id.btn_layout_drinks);
		 * 
		 * btndrinks.setOnClickListener(new View.OnClickListener() {
		 * 
		 * public void onClick(View view) {
		 * 
		 * 
		 * List<HashMap<String,String>> aList1 = new
		 * ArrayList<HashMap<String,String>>();
		 * 
		 * for(int i=0;i<6;i++){ HashMap<String, String> hm = new
		 * HashMap<String,String>(); hm.put("txt", "" + countries1[i]);
		 * hm.put("cur","" + currency1[i]); hm.put("price", ""+price1[i]);
		 * hm.put("flag", Integer.toString(flags1[i]) ); aList1.add(hm); }
		 * 
		 * // Keys used in Hashmap String[] from = { "flag","txt","cur","price"
		 * };
		 * 
		 * // Ids of views in listview_layout int[] to = {
		 * R.id.item_photo,R.id.item_name,R.id.item_desc,R.id.item_price};
		 * 
		 * // Instantiating an adapter to store each items //
		 * R.layout.listview_layout defines the layout of each item
		 * SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList1,
		 * R.layout.item_details_view, from, to);
		 * 
		 * // Getting a reference to listview of main.xml layout file ListView
		 * listView = ( ListView ) findViewById(R.id.listView_item);
		 * 
		 * // Setting the adapter to the listView listView.setAdapter(adapter);
		 * 
		 * } });
		 */

	
		/*
		 * Button btncoffee=(Button)findViewById(R.id.btn_layout_coffee);
		 * 
		 * btncoffee.setOnClickListener(new View.OnClickListener() {
		 * 
		 * public void onClick(View view) {
		 * 
		 * 
		 * List<HashMap<String,String>> aList1 = new
		 * ArrayList<HashMap<String,String>>();
		 * 
		 * for(int i=0;i<6;i++){ HashMap<String, String> hm = new
		 * HashMap<String,String>(); hm.put("txt", "" + countries2[i]);
		 * hm.put("cur","" + currency1[i]); hm.put("price", ""+price2[i]);
		 * hm.put("flag", Integer.toString(flags2[i]) ); aList1.add(hm); }
		 * 
		 * // Keys used in Hashmap String[] from = { "flag","txt","cur","price"
		 * };
		 * 
		 * // Ids of views in listview_layout int[] to = {
		 * R.id.item_photo,R.id.item_name,R.id.item_desc,R.id.item_price};
		 * 
		 * // Instantiating an adapter to store each items //
		 * R.layout.listview_layout defines the layout of each item
		 * SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList1,
		 * R.layout.item_details_view, from, to);
		 * 
		 * // Getting a reference to listview of main.xml layout file ListView
		 * listView = ( ListView ) findViewById(R.id.listView_item);
		 * 
		 * // Setting the adapter to the listView listView.setAdapter(adapter);
		 * 
		 * } });
		 */

		final Cursor cursor1 = db
				.rawQuery(
						"SELECT _id,ItemID,CustTable,ItemName,Quantity,PricePerUnit,TotalPrice,Status,Remarks,Billed FROM customer WHERE CustTable = ?",
						new String[] { "" + selectedTable });

		/*
		 * while(cursor.moveToNext()) {
		 * db_results.add(String.valueOf(cursor.getDouble
		 * (cursor.getColumnIndex("ItemID")))); }
		 */

		OrderedItemList = (ListView) findViewById(R.id.listView_ordereditem);
		arrayadapter = new SimpleCursorAdapter(this, R.layout.gift_text_image,
				cursor1, new String[] { "ItemName", "Quantity", "TotalPrice", "Remarks",
						"ItemID", "PricePerUnit" }, new int[] {
						R.id.txtview_productname, R.id.txtview_Quantity,
						R.id.txtview_Price, R.id.txtview_ItemRemarks, R.id.txtview_ItemId,
						R.id.txtview_ItemPricePerunit });
      
		OrderedItemList.setAdapter(arrayadapter);

		OrderedItemList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {

				/*
				 * Intent myIntent = new Intent(view.getContext(),
				 * SelectedProduct.class);
				 * myIntent.putExtra("selectedproduct_price",
				 * selectedProductPrice);
				 * myIntent.putExtra("selectedproduct_name", selectedProductName
				 * ); myIntent.putExtra("selectedproduct_link", selectedLink );
				 * startActivity(myIntent);
				 */
				selecteditemId = ((TextView) view
						.findViewById(R.id.txtview_ItemId)).getText()
						.toString();
				selecteditemPricePerUnit = ((TextView) view
						.findViewById(R.id.txtview_ItemPricePerunit)).getText()
						.toString();

				final Dialog dialog = new Dialog(Order.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

				dialog.setContentView(R.layout.alert_update_page);
				Cursor cursor3 = db
						.rawQuery(
								"SELECT _id,ItemID,CustTable,ItemName,Quantity,PricePerUnit,TotalPrice,Status,Remarks,Billed FROM customer WHERE ItemID=?",
								new String[] { "" + selecteditemId });

				if (cursor3.getCount() == 1) {
					cursor3.moveToFirst();

					EditTotal = (TextView) findViewById(R.id.txt_Total);

					Itemname = (EditText) dialog
							.findViewById(R.id.txt_itemname);
					Itemname.setClickable(false);
					Itemname.setFocusable(false);
					Itemname.setText(cursor3.getString(cursor3
							.getColumnIndex("ItemName")));

					ItemPrice = (EditText) dialog.findViewById(R.id.txt_price);
					ItemPrice.setClickable(false);
					ItemPrice.setFocusable(false);
					ItemPrice.setText(cursor3.getString(cursor3
							.getColumnIndex("TotalPrice")));

					Quantity = (EditText) dialog
							.findViewById(R.id.txt_quantity);
					Quantity.setClickable(false);
					Quantity.setFocusable(false);
					Quantity.setText(cursor3.getString(cursor3
							.getColumnIndex("Quantity")));
					
					Remarks = (EditText) dialog
							.findViewById(R.id.txt_remarks);
					Remarks.setText(cursor3.getString(cursor3
							.getColumnIndex("Remarks")));
					
					

				}

				cursor3.requery();

				selectOk = (Button) dialog.findViewById(R.id.btn_select_ok);
				selectCancel = (Button) dialog
						.findViewById(R.id.btn_select_cancel);
				selectRemove = (Button) dialog
						.findViewById(R.id.btn_select_remove);

				Quantity = (EditText) dialog.findViewById(R.id.txt_quantity);

				a = Quantity.getText().toString();

				if (a.equals("")) {
					c = 0;
					Quantity.setText(c.toString());
				}
				add = (Button) dialog.findViewById(R.id.btn_add);
				add.setOnClickListener(new View.OnClickListener() {
					public void onClick(View view) {

						a = Quantity.getText().toString();

						value = Integer.parseInt(a);

						Quantity.setText(value.toString());

						inc = value + b;

						Quantity.setText(inc.toString());

						// SelectedUnitPrice=EditUnitPrice.getText().toString();
						value2 = Integer.parseInt(selecteditemPricePerUnit);
						value3 = inc * value2;
						String resvalue = value3.toString();
						ItemPrice.setText(resvalue);
					}

				});

				minus = (Button) dialog.findViewById(R.id.btn_minus);
				minus.setOnClickListener(new View.OnClickListener() {
					public void onClick(View view) {

						a = Quantity.getText().toString();

						value = Integer.parseInt(a);
						if (value >= 1) {
							dec = value - b;

							Quantity.setText(dec.toString());
							value2 = Integer.parseInt(selecteditemPricePerUnit);
							value3 = dec * value2;
							String resvalue = value3.toString();
							ItemPrice.setText(resvalue);
						} else {
							AlertDialog.Builder altDialog = new AlertDialog.Builder(
									Order.this);
							altDialog.setMessage("Quantity Cannot Be Negative"); // here
																					// add
																					// your
																					// message
							altDialog.setNeutralButton("OK",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											// Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG).show();
										}
									});
							altDialog.show();

						}
					}

				});

				selectRemove.setOnClickListener(new View.OnClickListener() {

					public void onClick(View view) {
						db.execSQL("DELETE FROM customer where ItemID = "
								+ selecteditemId + " ");
					UpdateList();
					dialog.dismiss();
	
					}
				});

				selectCancel.setOnClickListener(new View.OnClickListener() {

					public void onClick(View view) {

						dialog.cancel();

					}
				});
				selectOk.setOnClickListener(new View.OnClickListener() {

					public void onClick(View view) {

						// final SQLiteDatabase db = (new
						// DataBaseHelper(this)).getWritableDatabase();
						String updateitemquantity = Quantity.getText()
								.toString();
						int updateditemQuantity = Integer
								.parseInt(updateitemquantity);
						String Updateprice = ItemPrice.getText().toString();
						int updateditemprice = Integer.parseInt(Updateprice);
                        String UpdateRemarks = Remarks.getText().toString();
						db.execSQL("UPDATE customer SET Quantity ="
								+ updateditemQuantity + ",TotalPrice ="
								+ updateditemprice + " WHERE ItemID = "
								+ selecteditemId + " ");
					UpdateList();
					dialog.dismiss();

					}

				});

				dialog.show();

			}

		});

		selectedTable = spinner2.getSelectedItem().toString();

		// ItemonClick();
		final Cursor cursor2 = db
				.rawQuery(
						"SELECT _id,CustTable,sum(TotalPrice),Status,Billed FROM customer WHERE CustTable = ?",
						new String[] { "" + selectedTable });

		if (cursor2.getCount() == 1) {
			cursor2.moveToFirst();

			EditTotal = (TextView) findViewById(R.id.txt_Total);
			EditTotal.setClickable(false);
			EditTotal.setFocusable(false);
			EditTotal.setText(cursor2.getString(cursor2
					.getColumnIndex("sum(TotalPrice)")));

		}
		cursor2.requery();

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {

				/*
				 * Intent myIntent = new Intent(view.getContext(),
				 * SelectedProduct.class);
				 * myIntent.putExtra("selectedproduct_price",
				 * selectedProductPrice);
				 * myIntent.putExtra("selectedproduct_name", selectedProductName
				 * ); myIntent.putExtra("selectedproduct_link", selectedLink );
				 * startActivity(myIntent);
				 */
				selecteditemname = ((TextView) view
						.findViewById(R.id.item_name)).getText().toString();

				selecteditemprice = ((TextView) view
						.findViewById(R.id.item_price)).getText().toString();

				final Dialog dialog = new Dialog(Order.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

				dialog.setContentView(R.layout.alert_page);

				Itemname = (EditText) dialog.findViewById(R.id.txt_itemname);
				ItemPrice = (EditText) dialog.findViewById(R.id.txt_price);
				
				Itemname.setText(selecteditemname);
                Remarks = (EditText) dialog.findViewById(R.id.txt_remarks);
                
				selectOk = (Button) dialog.findViewById(R.id.btn_select_ok);
				selectCancel = (Button) dialog
						.findViewById(R.id.btn_select_cancel);

				Quantity = (EditText) dialog.findViewById(R.id.txt_quantity);

				a = Quantity.getText().toString();
				c=1;
				value2 = Integer.parseInt(selecteditemprice);
				value3 = c * value2;
				String resvalue = value3.toString();
				ItemPrice.setText(resvalue);
				if (a.equals("")) {
					c = 1;
					Quantity.setText(c.toString());
					
				}
				add = (Button) dialog.findViewById(R.id.btn_add);
				add.setOnClickListener(new View.OnClickListener() {
					public void onClick(View view) {

						a = Quantity.getText().toString();

						value = Integer.parseInt(a);

						Quantity.setText(value.toString());

						inc = value + b;

						Quantity.setText(inc.toString());

						// SelectedUnitPrice=EditUnitPrice.getText().toString();
						value2 = Integer.parseInt(selecteditemprice);
						value3 = inc * value2;
						String resvalue = value3.toString();
						ItemPrice.setText(resvalue);
					}

				});

				minus = (Button) dialog.findViewById(R.id.btn_minus);
				minus.setOnClickListener(new View.OnClickListener() {
					public void onClick(View view) {

						a = Quantity.getText().toString();

						value = Integer.parseInt(a);
						if (value >= 1) {
							dec = value - b;

							Quantity.setText(dec.toString());
							value2 = Integer.parseInt(selecteditemprice);
							value3 = dec * value2;
							String resvalue = value3.toString();
							ItemPrice.setText(resvalue);
						} else {
							AlertDialog.Builder altDialog = new AlertDialog.Builder(
									Order.this);
							altDialog.setMessage("Quantity Cannot Be Negative"); // here
																					// add
																					// your
																					// message
							altDialog.setNeutralButton("OK",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
									
											// TODO Auto-generated method stub
											// Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG).show();
										}
									});
							altDialog.show();

						}
					}

				});

				selectCancel.setOnClickListener(new View.OnClickListener() {

					public void onClick(View view) {

						dialog.cancel();

					}
				});
				selectOk.setOnClickListener(new View.OnClickListener() {

					public void onClick(View view) {

						// final SQLiteDatabase db = (new
						// DataBaseHelper(this)).getWritableDatabase();

						String cust_itemname = Itemname.getText().toString();
						String cust_itemprice = ItemPrice.getText().toString();
						int cust_itemprice1 = Integer.parseInt(cust_itemprice);
						String cust_itemquantity = Quantity.getText()
								.toString();
						int cust_itemquantity1 = Integer
								.parseInt(cust_itemquantity);
						
						String cust_remarks = Remarks.getText().toString();

				EditText Covers =(EditText)findViewById(R.id.txt_covers);
				   
				String cust_covers=Covers.getText().toString();
				
			
						if (cust_itemname.equals(""))

						{
							showNoticeDialogBox("Error",
									"Please enter item name");

						}

						else {
					//		syncfromdbtid();
		//  Toast.makeText(getBaseContext(), Integer.parseInt(tid)+1 ,Toast.LENGTH_LONG).show();

							ContentValues addcustomer = new ContentValues();
							// addcustomer.put("CustomerNo",
							// Welcome.selectedCustNo);
							addcustomer.put("CustTable", selectedTable);
							// addcustomer.put("ItemID", "");
							addcustomer.put("ItemName", cust_itemname);
							addcustomer.put("Quantity", cust_itemquantity1);
							addcustomer.put("PricePerUnit",
									Integer.parseInt(selecteditemprice));
							addcustomer.put("TotalPrice", cust_itemprice1);
							addcustomer.put("Status", "Pending");
							addcustomer.put("Remarks", cust_remarks);
					//		addcustomer.put("tid", "");
					//		addcustomer.put("Covers", cust_covers);
	     
							addcustomer.put("Billed", "Yet to Bill");
       
							db.insert("customer", "ItemName", addcustomer);
             
							UpdateList();
							
							dialog.dismiss();

						 
							/*
							 * while(cursor.moveToNext()) {
							 * db_results.add(String
							 * .valueOf(cursor.getDouble(cursor
							 * .getColumnIndex("ItemID")))); }
							 */

							// cursor1.requery();
							// cursor2.requery();
							// Intent myIntent = new
							// Intent(getApplicationContext(),Order.class);
							// startActivity(myIntent);
							// showNoticeDialogBoxadd("Completed","Order has been added successfully");
							// Intent myIntent = new
							// Intent(view.getContext(),CustomerSelection.class);
							// startActivityForResult(myIntent, 0);
						}
					}

				});

				dialog.show();

				/*
				 * ImageView btn_close=
				 * (ImageView)dialog.findViewById(R.id.btn_close);
				 * 
				 * btn_close.setOnClickListener(new View.OnClickListener() {
				 * 
				 * public void onClick(View view) {
				 * 
				 * dialog.dismiss(); // dialog.cancel();
				 * 
				 * } });
				 */

			}

		});

	}

	public void onItemSelected(AdapterView<?> parent, View arg1, int position,
			long arg3) {
		selectedTable = spinner2.getSelectedItem().toString();

		final SQLiteDatabase db = (new DataBaseHelper(this))
				.getWritableDatabase();
		Cursor cursor1 = db
				.rawQuery(
						"SELECT _id,ItemID,CustTable,ItemName,Quantity,PricePerUnit,TotalPrice,Status,Billed FROM customer WHERE CustTable = ?",
						new String[] { "" + selectedTable });

		/*
		 * while(cursor.moveToNext()) {
		 * db_results.add(String.valueOf(cursor.getDouble
		 * (cursor.getColumnIndex("ItemID")))); }
		 */
		cursor1.requery();
		OrderedItemList = (ListView) findViewById(R.id.listView_ordereditem);
		arrayadapter = new SimpleCursorAdapter(this, R.layout.gift_text_image,
				cursor1, new String[] { "ItemName", "Quantity", "TotalPrice",
						"ItemID", "PricePerUnit" }, new int[] {
						R.id.txtview_productname, R.id.txtview_Quantity,
						R.id.txtview_Price, R.id.txtview_ItemId,
						R.id.txtview_ItemPricePerunit });

		OrderedItemList.setAdapter(arrayadapter);

		Cursor cursor2 = db
				.rawQuery(
						"SELECT _id,CustTable,sum(TotalPrice),Status,Billed FROM customer WHERE CustTable = ?",
						new String[] { "" + selectedTable });

		if (cursor2.getCount() == 1) {
			cursor2.moveToFirst();

			EditTotal = (TextView) findViewById(R.id.txt_Total);
			EditTotal.setClickable(false);
			EditTotal.setFocusable(false);
			EditTotal.setText(cursor2.getString(cursor2
					.getColumnIndex("sum(TotalPrice)")));

		}
		cursor2.requery();

	}

	public void onNothingSelected(AdapterView<?> arg0) {

	}
	
	public void UpdateList()
	
	{
		selectedTable = spinner2.getSelectedItem().toString();

		final SQLiteDatabase db = (new DataBaseHelper(this))
				.getWritableDatabase();
		Cursor cursor1 = db
				.rawQuery(
						"SELECT _id,ItemID,CustTable,ItemName,Quantity,PricePerUnit,TotalPrice,Status,Billed FROM customer WHERE CustTable = ?",
						new String[] {""+selectedTable});
			cursor1.requery();
			OrderedItemList = (ListView) findViewById(R.id.listView_ordereditem);
			arrayadapter = new SimpleCursorAdapter(this, R.layout.gift_text_image,
					cursor1, new String[] { "ItemName", "Quantity", "TotalPrice",
							"ItemID", "PricePerUnit" }, new int[] {
							R.id.txtview_productname, R.id.txtview_Quantity,
							R.id.txtview_Price, R.id.txtview_ItemId,
							R.id.txtview_ItemPricePerunit });

			OrderedItemList.setAdapter(arrayadapter);

			final Cursor cursor2 = db
					.rawQuery(
							"SELECT _id,CustTable,sum(TotalPrice),Status,Billed FROM customer WHERE CustTable = ?",
							new String[] { "" + selectedTable });

			if (cursor2.getCount() == 1) {
				cursor2.moveToFirst();

				EditTotal = (TextView) findViewById(R.id.txt_Total);
				EditTotal.setClickable(false);
				EditTotal.setFocusable(false);
				EditTotal.setText(cursor2.getString(cursor2
						.getColumnIndex("sum(TotalPrice)")));

			}
		
	}
	

	public void addItemsOnSpinner2() {

		spinner2 = (Spinner) findViewById(R.id.spinner_order);
		List<String> list = new ArrayList<String>();
		list.add("Table_1");
		list.add("Table_2");
		list.add("Table_3");
		list.add("Table_4");
		list.add("Table_5");
		list.add("Table_6");
		list.add("Table_7");
		list.add("Table_8");
		list.add("Table_9");
		list.add("Table_10");

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(dataAdapter);
	}

	public void addListenerOnSpinnerItemSelection() {

		spinner2 = (Spinner) findViewById(R.id.spinner_order);
		// spinner2.setOnItemSelectedListener(new
		// CustomOnItemSelectedListener());
	}
 public void syncfromdbtid()
	 
	 {
		

		 ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		//http post
		try{
		     HttpClient httpclient = new DefaultHttpClient();
		     HttpPost httppost = new HttpPost(ServerName+"/"+SourceName+"/tid_get.php");
		     httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		     HttpResponse response = httpclient.execute(httppost);
		     HttpEntity entity = response.getEntity();
		     is = entity.getContent();
		     }catch(Exception e){
		         Log.e("log_tag", "Error in http connection"+e.toString());
		    }
		//convert response to string
		try{
		      BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		       sb = new StringBuilder();
		       sb.append(reader.readLine() + "\n");

		       String line="0";
		       while ((line = reader.readLine()) != null) {
		                      sb.append(line + "\n");
		        }
		        is.close();
		        result=sb.toString();
		        }catch(Exception e){
		              Log.e("log_tag", "Error converting result "+e.toString());
		        }
		//paring data
		
		int c_id;
		String c_tid;
		
    //    Toast.makeText(getBaseContext(), result ,Toast.LENGTH_LONG).show();

		try{
		      jArray = new JSONArray(result);
		      JSONObject json_data=null;
		      for(int i=0;i<jArray.length();i++){
		             json_data = jArray.getJSONObject(i);
		          //   c_id=json_data.getInt("Vi_ID");
		             tid=json_data.getString("tid");
		             
      //  Toast.makeText(getBaseContext(), tid ,Toast.LENGTH_LONG).show();
     
		              
		             
		         }
		      }
		      catch(JSONException e1){
		    	  Toast.makeText(getBaseContext(), "No Id Found" ,Toast.LENGTH_LONG).show();
		      } catch (ParseException e1) {
					e1.printStackTrace();
			}
		}
 public void syncfromdbmenu()
	 
	 {
		

		 ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		//http post
		try{
		     HttpClient httpclient = new DefaultHttpClient();
		     HttpPost httppost = new HttpPost(ServerName+"/"+SourceName+"/get.php");
		     httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		     HttpResponse response = httpclient.execute(httppost);
		     HttpEntity entity = response.getEntity();
		     is = entity.getContent();
		     }catch(Exception e){
		         Log.e("log_tag", "Error in http connection"+e.toString());
		    }
		//convert response to string
		try{
		      BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		       sb = new StringBuilder();
		       sb.append(reader.readLine() + "\n");

		       String line="0";
		       while ((line = reader.readLine()) != null) {
		                      sb.append(line + "\n");
		        }
		        is.close();
		        result=sb.toString();
		        }catch(Exception e){
		              Log.e("log_tag", "Error converting result "+e.toString());
		        }
		//paring data
		
		int c_id;
		String c_Menu;
		String c_Submenu;
		String c_Price;
	

		try{
		      jArray = new JSONArray(result);
		      JSONObject json_data=null;
		      for(int i=0;i<jArray.length();i++){
		             json_data = jArray.getJSONObject(i);
		             c_id=json_data.getInt("Vi_ID");
		             c_Menu=json_data.getString("Vi_MainMenu");
		             c_Submenu=json_data.getString("Vi_SubMenu");
		             c_Price=json_data.getString("Vi_Price");
  
		            System.out.println(+c_id+c_Menu+c_Submenu+c_Price);
		             
   final SQLiteDatabase db = (new DataBaseHelper(this)).getWritableDatabase();
       
   
   
         ContentValues addItem = new ContentValues();
         addItem.put("_id", c_id);
         addItem.put("Menu", c_Menu);
         addItem.put("SubMenu", c_Submenu);
         addItem.put("Price", c_Price);
 	 
      db.insert("MenuItem", "Menu", addItem);    
		             
		         }
		      }
		      catch(JSONException e1){
		    	  Toast.makeText(getBaseContext(), "No Record Found" ,Toast.LENGTH_LONG).show();
		      } catch (ParseException e1) {
					e1.printStackTrace();
			}
		}
	 
	
	void sync(String row, String CustTable, String ItemName, String ItemID, String Quantity , String PricePerUnit , String TotalPrice , String Status,String Remarks,String Covers, String Billed) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(ServerName+"/"+SourceName+"/poster.php");
	//	HttpPost httppost = new HttpPost("http://10.0.2.2/oracleconn/index.php");

		
		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(9);
			nameValuePairs.add(new BasicNameValuePair("id", row));
			nameValuePairs.add(new BasicNameValuePair("CustTable", CustTable));
			nameValuePairs.add(new BasicNameValuePair("ItemName", ItemName));
			nameValuePairs.add(new BasicNameValuePair("ItemID", ItemID));
			nameValuePairs.add(new BasicNameValuePair("Quantity", Quantity));
			nameValuePairs.add(new BasicNameValuePair("PricePerUnit", PricePerUnit));
			nameValuePairs.add(new BasicNameValuePair("TotalPrice", TotalPrice));
			nameValuePairs.add(new BasicNameValuePair("Status", Status));
			nameValuePairs.add(new BasicNameValuePair("Remarks", Remarks));
			
			nameValuePairs.add(new BasicNameValuePair("Covers", Covers));

			nameValuePairs.add(new BasicNameValuePair("Billed", Billed));
	
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
			Log.v("resp", response.toString());

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}

	/*
	 * this method chooses the latest entry from the online database then
	 * selects then selects the later entries that this and posts them to the
	 * remote db
	 */
	public void startSync() {
		// read from Db
		int no_rows = 0;
        
		 
		
		HttpClient httpclienty = new DefaultHttpClient();
		HttpPost httpposty = new HttpPost(
				ServerName+"/"+SourceName+"/rows.php");
		try {
			HttpResponse responsey = httpclienty.execute(httpposty);
			String result = EntityUtils.toString(responsey.getEntity());
			JSONObject jo = new JSONObject(result);
			if(jo.toString().equals("{\"rows\":null}")){
				Log.v("obj", "is null");
			}
			else
			{
				JSONArray ja = jo.getJSONArray("rows");
				Log.v("lenght", ja.toString());

				JSONObject cobj = ja.getJSONObject(0);
				Log.v("c", cobj.toString());
				no_rows = cobj.getInt("row");
			}
			

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}

		String[] columns = new String[] {"_id","CustTable","ItemName","ItemID","Quantity","PricePerUnit","TotalPrice",
				"Status", "Billed" };
		//final Cursor c = ourDb.query("Ordereditem",columns, "_id" + ""
			//	+ no_rows, null, null, null, null);
	    final SQLiteDatabase db = (new DataBaseHelper(this)).getWritableDatabase();

		final Cursor c = db.rawQuery("SELECT * FROM Ordereditem", null);


	final	int iRow = c.getColumnIndex("_id");
	final	int iCustTable = c.getColumnIndex("CustTable");
	final	int iItemName = c.getColumnIndex("ItemName");
	final	int iItemID = c.getColumnIndex("ItemID");
	final	int iQuantity = c.getColumnIndex("Quantity");
	final	int iPricePerUnit = c.getColumnIndex("PricePerUnit");
	final	int iTotalPrice = c.getColumnIndex("TotalPrice");
	final	int iStatus = c.getColumnIndex("Status");
	final	int iRemarks = c.getColumnIndex("Remarks");
	final	int iCovers = c.getColumnIndex("Covers");

	final	int iBilled = c.getColumnIndex("Billed");
		
		class sender extends AsyncTask<Void, Void, Void> {

			@Override
			protected Void doInBackground(Void... params) {
				for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
					sync(c.getString(iRow), c.getString(iCustTable),
							c.getString(iItemName), c.getString(iItemID),
							c.getString(iQuantity),c.getString(iPricePerUnit),c.getString(iTotalPrice),
							c.getString(iStatus),c.getString(iRemarks),c.getString(iCovers),c.getString(iBilled));

				}

				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
		//		close();
				
			}

		}
		sender s = new sender();
		s.execute();
	}

	 
	
	
	
	
	

	private void showNoticeDialogBox(final String title, final String message) {
		Builder setupAlert;
		setupAlert = new AlertDialog.Builder(this).setTitle(title)
				.setMessage(message)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Intent myIntent = new
						// Intent(getApplicationContext(),AddCustomer.this);
						// startActivity(myIntent);
					}
				});
		setupAlert.show();
	}

	private void showNoticeDialogBoxadd(final String title, final String message) {
		Builder setupAlert;
		setupAlert = new AlertDialog.Builder(this).setTitle(title)
				.setMessage(message)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

						Intent myIntent = new Intent(getApplicationContext(),
								Welcome.class);
						startActivity(myIntent);
					}
				});
		setupAlert.show();
	}
	
	

}
