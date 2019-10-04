package com.erp.app;

import java.io.IOException;
import java.sql.ResultSet;

import javafx.application.Application;
import javafx.collections.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.erp.app.model.Resource;
import com.erp.app.model.Staff;
import com.erp.app.model.Product;
import com.erp.app.model.Client;
import com.erp.app.model.Manufacturing;
import com.erp.app.model.Order;
import com.erp.app.view.ClientController;
import com.erp.app.view.ClientDialogController;
import com.erp.app.view.OrderController;
import com.erp.app.view.ProductController;
import com.erp.app.view.ProductDialogController;
import com.erp.app.view.ProductResourceDialogController;
import com.erp.app.view.ResourceController;
import com.erp.app.view.ResourceDialogController;
import com.erp.app.view.RootController;
import com.erp.app.view.SignInController;
import com.erp.app.view.StaffController;
import com.erp.app.view.StaffDialogController;
import com.erp.app.view.DBConnection;
import com.erp.app.view.LoginController;
import com.erp.app.view.ManufactureController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;

public class MainApp extends Application {
	private ObservableList<Resource> resourceData = FXCollections.observableArrayList();
	private ObservableList<Product> productData = FXCollections.observableArrayList();
	private ObservableList<Client> clientData = FXCollections.observableArrayList();
	private ObservableList<Order> orderData = FXCollections.observableArrayList();
	private ObservableList<Staff> staffData = FXCollections.observableArrayList();
	private ObservableList<Manufacturing> manufactData = FXCollections.observableArrayList();
	private Stage primaryStage;
	private BorderPane rootLayout;
	DBConnection connection = new DBConnection();
	ResultSet rs, rs1, rs2;
	ArrayList<Resource> arr = new ArrayList<>();
	ArrayList<Integer> nums = new ArrayList<>();
	ArrayList<Integer> nums2 = new ArrayList<>();
	private boolean login = false;
	Product product;
	Client client;
	int cnt=0;
	private String loggedin;
    
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("ERP");

        initRootLayout();

        showResourceOverview();
        login = showLoginDialog();
        if(login) { //권한에 따라 불러오는 데이터를 다르게 하는 방법으로 scm구현예정
        	getData();
        }
        
        //showProductOverview();
	}
	public void insert(String s) {
		connection.insertQuery(s); 
	}
	public ResultSet search(String s) {
		return connection.selectQuery(s);
	}
	public void getData() {

		//날짜 받아오는 부분 시작
		Calendar cal = Calendar.getInstance();
		StringBuffer sb = new StringBuffer();
		Formatter f = new Formatter(sb);
		f.format("%tY-%tm-%te", cal, cal, cal);
		//날짜 받아오는 부분 종료
		try
		{
			rs = connection.selectQuery("select * from resource;");
			while(rs.next()) {
				String name = rs.getString(1);
				//System.out.println(name);
				int id = rs.getInt(2);
				int price = rs.getInt(3);
				int stack = rs.getInt(4);
				String category = rs.getString(5);
				int standard = rs.getInt(6);
				resourceData.add(new Resource(name, id, price, stack, category, standard));
				
			}
			rs.close();
		} catch(Exception e) {
			System.out.println("resource load failed : " + e.getMessage());
		}
		
		try//join 사용해야 함 수정예정
		{
			rs = connection.selectQuery("select * from product;");
			while(rs.next()) {
				nums.add(rs.getInt(2));
				//System.out.println(nums.iterator());
			}
			//System.out.println(nums.get(0));
			for(int i=0;i<nums.size();i++) {
				rs1 = connection.selectQuery("select * from product_resource where productID = " + nums.get(i) + ";");
				while(rs1.next()) {
					//int pid = rs1.getInt(1);
					int rid = rs1.getInt(2);
					String rName = rs1.getString(3);
					int price1 = rs1.getInt(4);
					int stack1 = rs1.getInt(5);
					//int category = rs1.getString(6);
					arr.add(new Resource(rName, rid, price1, stack1));
				} // while문 속에 rs1이 들어갈 수 없어서 밖으로 빼내어 중첩시행효과를 내었음
				rs=connection.selectQuery("select * from product where productID = " + nums.get(i) +";");
				while(rs.next()) {
					String name = rs.getString(1);
					int id = rs.getInt(2);
					//System.out.println(id);
					//int price = rs.getInt(3);
					//int cost = rs.getInt(4);
					int stack = rs.getInt(5);
					String category = rs.getString(6);
					
					productData.add(new Product(name, id, stack, arr, category));
				}
				arr.clear();
			}
			nums.clear();
		} catch(Exception e) {
			System.out.println("product load failed : "+e.getMessage());
		}
		
		try
		{
			rs = connection.selectQuery("select * from client;");
			while(rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String crn = rs.getString(3);
				String ceoName = rs.getString(4);
				String sn = rs.getString(5);
				int addressCode = rs.getInt(6);
				String address = rs.getString(7);
				String phone = rs.getString(8);
				String account = rs.getString(9);
				String lastOrder = rs.getString(10);
				
				clientData.add(new Client(id, name, crn, ceoName, sn, addressCode, address, phone, account, lastOrder));
			}
			rs.close();
		} catch(Exception e) {
			System.out.println("client load failed : " + e.getMessage());
		}
		//orderid 추가 고려 없으니깐 제거할 때 귀찮음
		try {
			rs = connection.selectQuery("select * from product join orderTable join client where product.productID = orderTable.productID and client.clientID = orderTable.clientID;");
			while(rs.next()) {
				String pName = rs.getString(1);
				int pid = rs.getInt(2);
				int price = rs.getInt(3);
				int cost = rs.getInt(4);
				int stack = rs.getInt(10);
				String orderD = rs.getString(11);
				product = new Product(pName, pid, price, cost, stack);
				int oid = rs.getInt(7);
				int cid = rs.getInt(13);
				String cName = rs.getString(14);
				String crn = rs.getString(15);
				String ceoName = rs.getString(16);
				String sn = rs.getString(17);
				int ac = rs.getInt(18);
				String ad = rs.getString(19);
				String ph = rs.getString(20);
				String account = rs.getString(21);
				String lo = rs.getString(22);
				client = new Client(cid, cName, crn, ceoName, sn, ac, ad, ph, account, lo);
				orderData.add(new Order(oid, crn, pName, stack, stack*price, orderD, rs.getString(10), product, client));
			}
		} catch(Exception e) {
			System.out.println("aa"+e.getMessage());
		}
		
		try {
			rs = connection.selectQuery("select * from staffTable;");
			while(rs.next()) {
				String name = rs.getString(1);
				String birthDay = rs.getString(2);
				int id = rs.getInt(3);
				int age = rs.getInt(4);
				String rank = rs.getString(5);
				String phoneNumber = rs.getString(6);
				String address = rs.getString(7);
				String account = rs.getString(8);
				String socialNumber = rs.getString(9);
				staffData.add(new Staff(id, name, birthDay, age, rank, phoneNumber, address, account, socialNumber));
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		//manu
		try {
			Product prod;
			rs = connection.selectQuery("select * from manufacturing;");
			int k = 0;
			while(rs.next()) {
				nums.add(rs.getInt(2));
			}
			for(int i = 0; i<nums.size();i++) {
				rs = connection.selectQuery("select * from product join manufacturing where product.productID = manufacturing.productID and manufacID = " + nums.get(i)+ ";");
				if(rs.next()) {
					
				}
				String pname = rs.getString(1);
				int pid = rs.getInt(2);
				int mid = rs.getInt(8);
				int mstack = rs.getInt(10);
				String manufactured = rs.getString(9);
				String color = rs.getString(11);
				String sDate = rs.getString(12);
				String eDate = rs.getString(13);
				rs1 = connection.selectQuery("select * from product_resource where productID = " + pid + ";");
				while(rs1.next()) {
					int rid = rs1.getInt(2);
					String rname = rs1.getString(3);
					int rprice = rs1.getInt(4);
					int rstack = rs1.getInt(5);
					arr.add(new Resource(rname, rid, rprice, rstack));
				}
				prod = new Product(pname, pid, arr);
				manufactData.add(new Manufacturing(mid, prod, mstack, manufactured, color, prod.getSellingPrice(), prod.getProductionCost(), sDate, eDate));
				arr.clear();
			}
			nums.clear();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
 
	public MainApp() {
		/*
		//날짜 받아오는 부분 시작
		Calendar cal = Calendar.getInstance();
		StringBuffer sb = new StringBuffer();
		Formatter f = new Formatter(sb);
		f.format("%tY-%tm-%te", cal, cal, cal);
		//날짜 받아오는 부분 종료
		try
		{
			rs = connection.selectQuery("select * from resource;");
			while(rs.next()) {
				String name = rs.getString(1);
				//System.out.println(name);
				int id = rs.getInt(2);
				int price = rs.getInt(3);
				int stack = rs.getInt(4);
				String category = rs.getString(5);
				int standard = rs.getInt(6);
				resourceData.add(new Resource(name, id, price, stack, category, standard));
				
			}
			rs.close();
		} catch(Exception e) {
			System.out.println("resource load failed : " + e.getMessage());
		}
		
		try//join 사용해야 함 수정예정
		{
			rs = connection.selectQuery("select * from product;");
			while(rs.next()) {
				nums.add(rs.getInt(2));
				//System.out.println(nums.iterator());
			}
			//System.out.println(nums.get(0));
			for(int i=0;i<nums.size();i++) {
				rs1 = connection.selectQuery("select * from product_resource where productID = " + nums.get(i) + ";");
				while(rs1.next()) {
					//int pid = rs1.getInt(1);
					int rid = rs1.getInt(2);
					String rName = rs1.getString(3);
					int price1 = rs1.getInt(4);
					int stack1 = rs1.getInt(5);
					//int category = rs1.getString(6);
					arr.add(new Resource(rName, rid, price1, stack1));
				} // while문 속에 rs1이 들어갈 수 없어서 밖으로 빼내어 중첩시행효과를 내었음
				rs=connection.selectQuery("select * from product where productID = " + nums.get(i) +";");
				while(rs.next()) {
					String name = rs.getString(1);
					int id = rs.getInt(2);
					//System.out.println(id);
					//int price = rs.getInt(3);
					//int cost = rs.getInt(4);
					int stack = rs.getInt(5);
					String category = rs.getString(6);
					
					productData.add(new Product(name, id, stack, arr, category));
				}
				arr.clear();
			}
			nums.clear();
		} catch(Exception e) {
			System.out.println("product load failed : "+e.getMessage());
		}
		
		try
		{
			rs = connection.selectQuery("select * from client;");
			while(rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String crn = rs.getString(3);
				String ceoName = rs.getString(4);
				String sn = rs.getString(5);
				int addressCode = rs.getInt(6);
				String address = rs.getString(7);
				String phone = rs.getString(8);
				String account = rs.getString(9);
				String lastOrder = rs.getString(10);
				
				clientData.add(new Client(id, name, crn, ceoName, sn, addressCode, address, phone, account, lastOrder));
			}
			rs.close();
		} catch(Exception e) {
			System.out.println("client load failed : " + e.getMessage());
		}
		//orderid 추가 고려 없으니깐 제거할 때 귀찮음
		try {
			rs = connection.selectQuery("select * from product join orderTable join client where product.productID = orderTable.productID and client.clientID = orderTable.clientID;");
			while(rs.next()) {
				String pName = rs.getString(1);
				int pid = rs.getInt(2);
				int price = rs.getInt(3);
				int cost = rs.getInt(4);
				int stack = rs.getInt(10);
				String orderD = rs.getString(11);
				product = new Product(pName, pid, price, cost, stack);
				int oid = rs.getInt(7);
				int cid = rs.getInt(13);
				String cName = rs.getString(14);
				String crn = rs.getString(15);
				String ceoName = rs.getString(16);
				String sn = rs.getString(17);
				int ac = rs.getInt(18);
				String ad = rs.getString(19);
				String ph = rs.getString(20);
				String account = rs.getString(21);
				String lo = rs.getString(22);
				client = new Client(cid, cName, crn, ceoName, sn, ac, ad, ph, account, lo);
				orderData.add(new Order(oid, crn, pName, stack, stack*price, orderD, rs.getString(10), product, client));
			}
		} catch(Exception e) {
			System.out.println("aa"+e.getMessage());
		}
		
		try {
			rs = connection.selectQuery("select * from staffTable;");
			while(rs.next()) {
				String name = rs.getString(1);
				String birthDay = rs.getString(2);
				int id = rs.getInt(3);
				int age = rs.getInt(4);
				String rank = rs.getString(5);
				String phoneNumber = rs.getString(6);
				String address = rs.getString(7);
				String account = rs.getString(8);
				String socialNumber = rs.getString(9);
				staffData.add(new Staff(id, name, birthDay, age, rank, phoneNumber, address, account, socialNumber));
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		//manu
		try {
			Product prod;
			rs = connection.selectQuery("select * from manufacturing;");
			int k = 0;
			while(rs.next()) {
				nums.add(rs.getInt(2));
			}
			for(int i = 0; i<nums.size();i++) {
				rs = connection.selectQuery("select * from product join manufacturing where product.productID = manufacturing.productID and manufacID = " + nums.get(i)+ ";");
				if(rs.next()) {
					
				}
				String pname = rs.getString(1);
				int pid = rs.getInt(2);
				int mid = rs.getInt(8);
				int mstack = rs.getInt(10);
				String manufactured = rs.getString(9);
				String color = rs.getString(11);
				String sDate = rs.getString(12);
				String eDate = rs.getString(13);
				rs1 = connection.selectQuery("select * from product_resource where productID = " + pid + ";");
				while(rs1.next()) {
					int rid = rs1.getInt(2);
					String rname = rs1.getString(3);
					int rprice = rs1.getInt(4);
					int rstack = rs1.getInt(5);
					arr.add(new Resource(rname, rid, rprice, rstack));
				}
				prod = new Product(pname, pid, arr);
				manufactData.add(new Manufacturing(mid, prod, mstack, manufactured, color, prod.getSellingPrice(), prod.getProductionCost(), sDate, eDate));
				arr.clear();
			}
			nums.clear();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		*/
	}
	
	public ObservableList<Manufacturing> getManufactData() {
		return manufactData;
	}

	public ObservableList<Resource> getResourceData() {
        return resourceData;
    }

	public ObservableList<Product> getProductData() {
        return productData;
    }

	public ObservableList<Client> getClientData() {
        return clientData;
    }

	public ObservableList<Order> getOrderData() {
		return orderData;
	}

	public ObservableList<Staff> getStaffData() {
		return staffData;
	}
	
	public void setLoginDepartment(String department) {
		loggedin=department;
	}
	
	public String getLoggedinDepartment() {
		return loggedin;
	}
	
	public void initRootLayout() {
        try {
            // fxml 占쎈솁占쎌뵬占쎈퓠占쎄퐣 占쎄맒占쎌맄 占쎌쟿占쎌뵠占쎈툡占쎌뜍占쎌뱽 揶쏉옙占쎌죬占쎌궔占쎈뼄.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            RootController controller = loader.getController();
            controller.setMainApp(this);
            // 占쎄맒占쎌맄 占쎌쟿占쎌뵠占쎈툡占쎌뜍占쎌뱽 占쎈７占쎈맙占쎈릭占쎈뮉 scene占쎌뱽 癰귣똻肉т빳占쏙옙�뼄.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void showResourceOverview() {
        try {
            // resource 占쎌뒄占쎈튋占쎌뱽 揶쏉옙占쎌죬占쎌궔占쎈뼄.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ResourceView2.fxml"));
            AnchorPane resourceOverview = (AnchorPane) loader.load();

            // resource 占쎌뒄占쎈튋占쎌뱽 占쎄맒占쎌맄 占쎌쟿占쎌뵠占쎈툡占쎌뜍 揶쏉옙占쎌뒲占쎈쑓嚥∽옙 占쎄퐬占쎌젟占쎈립占쎈뼄.
            rootLayout.setCenter(resourceOverview);
            
            ResourceController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void showClientOverview() {
        try {
            // resource 占쎌뒄占쎈튋占쎌뱽 揶쏉옙占쎌죬占쎌궔占쎈뼄.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ClientView.fxml"));
            AnchorPane resourceOverview = (AnchorPane) loader.load();

            // resource 占쎌뒄占쎈튋占쎌뱽 占쎄맒占쎌맄 占쎌쟿占쎌뵠占쎈툡占쎌뜍 揶쏉옙占쎌뒲占쎈쑓嚥∽옙 占쎄퐬占쎌젟占쎈립占쎈뼄.
            rootLayout.setCenter(resourceOverview);
            
            ClientController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void showProductOverview() {
        try {
            // product 占쎌뒄占쎈튋占쎌뱽 揶쏉옙占쎌죬占쎌궔占쎈뼄.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ProductView.fxml"));
            AnchorPane productOverview = (AnchorPane) loader.load();

            // product 占쎌뒄占쎈튋占쎌뱽 占쎄맒占쎌맄 占쎌쟿占쎌뵠占쎈툡占쎌뜍 揶쏉옙占쎌뒲占쎈쑓嚥∽옙 占쎄퐬占쎌젟占쎈립占쎈뼄.
            rootLayout.setCenter(productOverview);
            
            ProductController controller = loader.getController();  //�뚢뫂�뱜嚥▲끇�쑎 占쎈땾占쎌젟占쎈퉸占쎈튊
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void showOrderOverview() {
        try {
            // product 占쎌뒄占쎈튋占쎌뱽 揶쏉옙占쎌죬占쎌궔占쎈뼄.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/OrderView.fxml"));
            AnchorPane orderOverview = (AnchorPane) loader.load();

            // product 占쎌뒄占쎈튋占쎌뱽 占쎄맒占쎌맄 占쎌쟿占쎌뵠占쎈툡占쎌뜍 揶쏉옙占쎌뒲占쎈쑓嚥∽옙 占쎄퐬占쎌젟占쎈립占쎈뼄.
            rootLayout.setCenter(orderOverview);
            
            OrderController controller = loader.getController();  //�뚢뫂�뱜嚥▲끇�쑎 占쎈땾占쎌젟占쎈퉸占쎈튊
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void showStffOverview() {
        try {
            // resource 占쎌뒄占쎈튋占쎌뱽 揶쏉옙占쎌죬占쎌궔占쎈뼄.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/StaffView.fxml"));
            AnchorPane staffOverview = (AnchorPane) loader.load();

            // resource 占쎌뒄占쎈튋占쎌뱽 占쎄맒占쎌맄 占쎌쟿占쎌뵠占쎈툡占쎌뜍 揶쏉옙占쎌뒲占쎈쑓嚥∽옙 占쎄퐬占쎌젟占쎈립占쎈뼄.
            rootLayout.setCenter(staffOverview);
            
            StaffController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void showManufactOverview() {
        try {
            // resource 占쎌뒄占쎈튋占쎌뱽 揶쏉옙占쎌죬占쎌궔占쎈뼄.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ManufactureView.fxml"));
            AnchorPane resourceOverview = (AnchorPane) loader.load();

            // resource 占쎌뒄占쎈튋占쎌뱽 占쎄맒占쎌맄 占쎌쟿占쎌뵠占쎈툡占쎌뜍 揶쏉옙占쎌뒲占쎈쑓嚥∽옙 占쎄퐬占쎌젟占쎈립占쎈뼄.
            rootLayout.setCenter(resourceOverview);
            
            ManufactureController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public boolean showResourceEditDialog(Resource resource) {
	    try {
	        // fxml 占쎈솁占쎌뵬占쎌뱽 嚥≪뮆諭띰옙釉��⑨옙 占쎄돌占쎄퐣 占쎄퉱嚥≪뮇�뒲 占쎈뮞占쎈�믭옙�뵠筌욑옙�몴占� 筌띾슢諭븝옙�뼄.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/ResourceDialog.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // 占쎈뼄占쎌뵠占쎈섰嚥≪뮄�젃 占쎈뮞占쎈�믭옙�뵠筌욑옙�몴占� 筌띾슢諭븝옙�뼄.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Edit Resource");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // resource �뚢뫂�뱜嚥▲끇�쑎占쎈퓠 占쎄퐬占쎌젟占쎈립占쎈뼄.
	        ResourceDialogController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setResource(resource);

	        // 占쎈뼄占쎌뵠占쎈섰嚥≪뮄�젃�몴占� 癰귣똻肉т틠�눊�� 占쎄텢占쎌뒠占쎌쁽揶쏉옙 占쎈뼍占쎌뱽 占쎈르繹먮슣占� 疫꿸퀡�뼄�뵳怨뺣뼄.
	        dialogStage.showAndWait();

	        return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean showSignInDialog() {
	    try {
	        // fxml 占쎈솁占쎌뵬占쎌뱽 嚥≪뮆諭띰옙釉��⑨옙 占쎄돌占쎄퐣 占쎄퉱嚥≪뮇�뒲 占쎈뮞占쎈�믭옙�뵠筌욑옙�몴占� 筌띾슢諭븝옙�뼄.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/SignInView.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // 占쎈뼄占쎌뵠占쎈섰嚥≪뮄�젃 占쎈뮞占쎈�믭옙�뵠筌욑옙�몴占� 筌띾슢諭븝옙�뼄.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("login");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // resource �뚢뫂�뱜嚥▲끇�쑎占쎈퓠 占쎄퐬占쎌젟占쎈립占쎈뼄.
	        SignInController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setMainApp(this);

	        // 占쎈뼄占쎌뵠占쎈섰嚥≪뮄�젃�몴占� 癰귣똻肉т틠�눊�� 占쎄텢占쎌뒠占쎌쁽揶쏉옙 占쎈뼍占쎌뱽 占쎈르繹먮슣占� 疫꿸퀡�뼄�뵳怨뺣뼄.
	        dialogStage.showAndWait();

	        return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean showLoginDialog() {
	    try {
	        // fxml 占쎈솁占쎌뵬占쎌뱽 嚥≪뮆諭띰옙釉��⑨옙 占쎄돌占쎄퐣 占쎄퉱嚥≪뮇�뒲 占쎈뮞占쎈�믭옙�뵠筌욑옙�몴占� 筌띾슢諭븝옙�뼄.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/LoginView.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // 占쎈뼄占쎌뵠占쎈섰嚥≪뮄�젃 占쎈뮞占쎈�믭옙�뵠筌욑옙�몴占� 筌띾슢諭븝옙�뼄.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("login");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // resource �뚢뫂�뱜嚥▲끇�쑎占쎈퓠 占쎄퐬占쎌젟占쎈립占쎈뼄.
	        LoginController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setMainApp(this);

	        // 占쎈뼄占쎌뵠占쎈섰嚥≪뮄�젃�몴占� 癰귣똻肉т틠�눊�� 占쎄텢占쎌뒠占쎌쁽揶쏉옙 占쎈뼍占쎌뱽 占쎈르繹먮슣占� 疫꿸퀡�뼄�뵳怨뺣뼄.
	        dialogStage.showAndWait();

	        return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean showStaffEditDialog(Staff staff) {
	    try {
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/StaffDialog.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Edit Staff");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        StaffDialogController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setStaff(staff);

	        dialogStage.showAndWait();

	        return controller.isOkClicked();
	        //return true;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean showClientEditDialog(Client client) {
	    try {
	        // fxml 占쎈솁占쎌뵬占쎌뱽 嚥≪뮆諭띰옙釉��⑨옙 占쎄돌占쎄퐣 占쎄퉱嚥≪뮇�뒲 占쎈뮞占쎈�믭옙�뵠筌욑옙�몴占� 筌띾슢諭븝옙�뼄.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/ClientDialog.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // 占쎈뼄占쎌뵠占쎈섰嚥≪뮄�젃 占쎈뮞占쎈�믭옙�뵠筌욑옙�몴占� 筌띾슢諭븝옙�뼄.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Edit Client");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // resource �뚢뫂�뱜嚥▲끇�쑎占쎈퓠 占쎄퐬占쎌젟占쎈립占쎈뼄.
	        ClientDialogController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setClient(client);
	        //controller.setMainApp(this);

	        // 占쎈뼄占쎌뵠占쎈섰嚥≪뮄�젃�몴占� 癰귣똻肉т틠�눊�� 占쎄텢占쎌뒠占쎌쁽揶쏉옙 占쎈뼍占쎌뱽 占쎈르繹먮슣占� 疫꿸퀡�뼄�뵳怨뺣뼄.
	        dialogStage.showAndWait();

	        return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean showProductEditDialog(Product Product) {
	    try {
	        // fxml 占쎈솁占쎌뵬占쎌뱽 嚥≪뮆諭띰옙釉��⑨옙 占쎄돌占쎄퐣 占쎄퉱嚥≪뮇�뒲 占쎈뮞占쎈�믭옙�뵠筌욑옙�몴占� 筌띾슢諭븝옙�뼄.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/ProductDialog.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // 占쎈뼄占쎌뵠占쎈섰嚥≪뮄�젃 占쎈뮞占쎈�믭옙�뵠筌욑옙�몴占� 筌띾슢諭븝옙�뼄.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Edit Product");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // resource �뚢뫂�뱜嚥▲끇�쑎占쎈퓠 占쎄퐬占쎌젟占쎈립占쎈뼄.
	        ProductDialogController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setProduct(Product);
	        if(Product.getMaterial()!=null) {
	        	controller.setResource(Product.getMaterial());
	        }
	        controller.setMainApp(this);

	        // 占쎈뼄占쎌뵠占쎈섰嚥≪뮄�젃�몴占� 癰귣똻肉т틠�눊�� 占쎄텢占쎌뒠占쎌쁽揶쏉옙 占쎈뼍占쎌뱽 占쎈르繹먮슣占� 疫꿸퀡�뼄�뵳怨뺣뼄.
	        dialogStage.showAndWait();

	        return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean showProductResourceDialog(Resource Resource) {
	    try {

	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/ProductResourceDialog.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();


	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Add Resource");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);


	        ProductResourceDialogController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        //controller.setResource(Resource);


	        dialogStage.showAndWait();

	        return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public Stage getPrimaryStage() {
        return primaryStage;
    }

	public static void main(String[] args) {
		launch(args);
	}
}

