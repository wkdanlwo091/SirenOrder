package com.example.sirenorder.frame;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.example.common.Pagination;
import com.example.sirenorder.vo.PaginationOwner;
import com.example.sirenorder.vo.Point_storeJoinStoreVO;
import com.example.sirenorder.vo.StoreVO;
import com.example.sirenorder.vo.Store_nameAndDate;
import com.example.sirenorder.vo.Store_productJoinProductVO;
import com.example.sirenorder.vo.SumAndOrders_date;
import com.example.sirenorder.vo.UserVO;

public interface Dao<Id, Model> {
	default public void insert(Model m) throws Exception {
	}

	default public void update(Model m) throws Exception {
	}
	default public void deleteStore_productByProduct_name(String product_name) {
	}
	default public void updateToken(Model m) throws Exception {
	}
	default public void updateRole(Model m) {
		
	}
	default public String selectLastId() {
		return null;
	}
	default public void deleteMultiple(List<String> lists) throws Exception {
	}

	default public void insertMultiple(List<Model> m)  {
	}

	default public String selectToken(String orders_id) {
		return null;
	}
	default public void updateAllPoint_rate(StoreVO storeVO) {
		
	}

	default public void delete(String id) throws Exception {
	}

	default public ArrayList<Model> selectChain(String name) throws Exception {
		return null;
	}
	default public String selectStore_id(String store_name) {
		return null;
	}
	default public ArrayList<Model> selectByStore_name(String store_name) {
		// TODO Auto-generated method stub
		return null;
	}
	default public ArrayList<Model> selectProduct(String  name, int num) throws Exception {
		return null;
	}
	default public Model select(String  name, String name2) throws Exception {
		return null;
	}
	default public void deleteProduct(Model m) throws Exception {
	}
	default public Model selectByChain_name(Id id) throws Exception {
		return null;
	}
	default public Model selectByChain_nameWithusers_id(String chain_name, String users_id) {
		return null;
	}
	default public int selectOrders_seq() throws Exception {
		return (Integer) null;
	}
	default public void updatePoint_rate(Model m) throws Exception {
		// TODO Auto-generated method stub
	}
	default public String selectProduct_id(String product_name) {
		return null;
	}
	default public int selectListCnt(String chain_name) {
		return (Integer) null;
	}
	default public ArrayList<Model> selectProductList(String chain_name, int startList, int listSize) {
		return null;
	}
	default public ArrayList<Model> selectOrdersStatus(Pagination pagination){
		
		return null;
	}
	default public ArrayList<Model> selectOrders_detailJoinProductByStore_name(PaginationOwner pagination){
		
		return null;
	}
	
	
	default public int selectOrders_detailCnt( ){
		return (Integer) null;
	}
	default public ArrayList<Model> selectByDateFromTo(String users_id, Date firstDate, Date secondDate){
		return null;
	}
	default public ArrayList<Model> selectOrders_detailByOrdersId(String orders_id ){
		return null;
	}
	default public ArrayList<Point_storeJoinStoreVO> selectByUsers_id(String users_id ) throws Exception {
		return null;
	}
	default public ArrayList<Point_storeJoinStoreVO> selectByDateFromToJoin(String users_id, Date firstDate, Date secondDate)  {
		return null;
	}
	default public ArrayList<Store_productJoinProductVO> selectProductListJoin(String store_name, int startList, int listSize) {
		// TODO Auto-generated method stub
		return null;
	}
	default public ArrayList<Model> selectOrders_detailByStore_name(String store_name ) throws Exception {
		return null;
	}
	default public int selectOrders_detailCntByStore_name(String store_name) { 
		return (Integer) null;
	}
	
	default public ArrayList<Integer> selectIncomeByStore_nameDay(Store_nameAndDate a) { 
		return null;
	}
	
	default public ArrayList<SumAndOrders_date> selectIncomeBystore_nameDayRange(Store_nameAndDate a) { 
		return null;
	}
	
	default public void updateStore_name(UserVO m) throws Exception {
	}

	
	public Model select(Id id);

	public ArrayList<Model> selectall();


}
