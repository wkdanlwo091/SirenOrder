package com.example.sirenorder.frame;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.example.common.Pagination;
import com.example.sirenorder.vo.ChainVO;
import com.example.sirenorder.vo.PaginationOwner;
import com.example.sirenorder.vo.PointVO;
import com.example.sirenorder.vo.Point_storeJoinStoreVO;
import com.example.sirenorder.vo.ProductVO;
import com.example.sirenorder.vo.StoreVO;
import com.example.sirenorder.vo.Store_nameAndDate;
import com.example.sirenorder.vo.Store_productJoinProductVO;
import com.example.sirenorder.vo.Store_productVO;
import com.example.sirenorder.vo.SumAndOrders_date;
import com.example.sirenorder.vo.UserVO;

public interface Biz<Id, Model> {
	@Transactional 
	default public void register(Model m) throws Exception{
	}
	@Transactional 
	default public void update(Model id) throws Exception{
	}
	
	@Transactional 
	default public String getLastId() {
		return null;
	}
	
	@Transactional
	default public ArrayList<Model> getByChainName(String chain_name) throws Exception {
		return null;
	}

	@Transactional 
	default public void updateRole(Model m) {
	}
	@Transactional
	default public void registerMultiple(List<Model> m) throws Exception  {
	}
	@Transactional
	default public void deleteMultiple(List<String> lists) throws Exception {
	}

	@Transactional 
	default public void updatePoint_rate(Model m) throws Exception {
	}
	@Transactional
	default public void updateAllPoint_rate(StoreVO storeVO) {
		
	}
	@Transactional
	default public String getToken(String orders_id) {
		return null;
	}
	@Transactional
	default public void updateStore_name(UserVO m) throws Exception {
	}

	@Transactional 
	default public void deleteStore_productByProduct_name(String product_name) {
	}
	@Transactional
	default public void updateToken(Model m) throws Exception {
	}
	@Transactional 
	default public void delete(Id id) throws Exception{
	}
	@Transactional 
	default public ArrayList<Model> getChain(Id id) throws Exception{
		return null;
	}
	@Transactional
	default public ArrayList<Model> getByStore_name(String store_name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional 
	default public ArrayList<Model> getProduct(String name, int num) throws Exception{
		return null;
	}
	
	@Transactional 
	default public Model get(String name, String name2) throws Exception{
		return null;
	}
	
	@Transactional 
	default public Model getByChain_name(String chain_name) throws Exception{
		return null;
	}
	
	@Transactional 
	default public void deleteProduct(Model m) throws Exception{
	}
	
	@Transactional
	default public Model getByChain_nameWithusers_id(String chain_name, String users_id) {
		return null;
 	}

	@Transactional
	default public int getOrders_seq() throws Exception{
		return (Integer) null;
	}
	
	@Transactional
	default public String getProduct_id(String product_name) throws Exception{
		return  null;
	}
	@Transactional
	default public int getListCnt(String chain_name) {
		return (Integer) null;
	}
	@Transactional
	default public ArrayList<Model> getProductList(String chain_name, int startList, int listSize) {
		return  null;
	}
	
	@Transactional
	default public ArrayList<Model> getOrdersStatus(Pagination pagination){
		return null;
	}
	
	@Transactional
	default public int getOrders_detailCnt( ){
		return (Integer) null;
	}
	
	@Transactional
	default public ArrayList<Model> getByDateFromTo(String users_id, Date firstDate, Date secondDate){
		return null;
	}
	@Transactional
	default public ArrayList<Model> getOrders_detailByOrdersId(String orders_id ){
		return null;
	}
	@Transactional
	default public ArrayList<Point_storeJoinStoreVO> getByDateFromToJoin(String users_id, Date firstDate, Date secondDate)  {
		return null;
	}
	@Transactional
	default public ArrayList<Store_productJoinProductVO> getProductListJoin(String store_name, int startList, int listSize)  {
		return null;
	}
	
	@Transactional
	default public ArrayList<Model> getOrders_detailByStore_name(String store_name ) throws Exception {
		return null;
	}
	@Transactional
	default public ArrayList<Point_storeJoinStoreVO> getByUsers_id(String users_id ) throws Exception {
		return null;
	}
	@Transactional
	default public int getOrders_detailCntByStore_name(String store_name) { 
		return (Integer)null;
	}
	@Transactional
	default public ArrayList<Model> getOrders_detailJoinProductByStore_name(PaginationOwner pagination){
		
		return null;
	}
	
	@Transactional
	default public ArrayList<Integer> getIncomeByStore_nameDay(Store_nameAndDate a) { 
		return null;
	}
	@Transactional
	default public ArrayList<SumAndOrders_date> getIncomeBystore_nameDayRange(Store_nameAndDate a) { 
		return null;
	}
	@Transactional
	default public String getStore_id(String store_name) {
		return null;
	}



	public Model get(Id id);
	public ArrayList<Model> get();
}
