package Cafe_s.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JComboBox;

import Cafe_s.Frame.CafeServer;
import Cafe_s.Frame.ChatPanel;
import Cafe_s.Frame.MainPanel;
import Cafe_s.model.CafeDao;
import Cafe_s.model.service.EmployeeVO;
import Cafe_s.model.service.StockVO;
import service.CoffeeVO;
import service.MemberVO;
import service.MySbVO;
import service.OrderVO;

public class Controller {
	
	public ArrayList<CoffeeVO> c_select(){
		return new CafeDao().c_select();
	}
	public ArrayList<OrderVO> order_select(MemberVO vo){
		return new CafeDao().order_select(vo);
	}
	public ArrayList<MemberVO> m_select(){
		return new CafeDao().m_select();
	}
	public MemberVO idfind(MemberVO vo) {
		return new CafeDao().idfind(vo);
	}
	public MemberVO pwfind (MemberVO vo){
		return new CafeDao().pwfind(vo);	
	}
	public EmployeeVO pwfind (EmployeeVO vo){
		return new CafeDao().pwfind(vo);	
	}
	public ArrayList<EmployeeVO> e_select(){
		return new CafeDao().e_select();
	}
	public ArrayList<MemberVO> block_select(){
		return new CafeDao().block_select();
	}
	public ArrayList<MySbVO> MySb_select(MemberVO vo){
		return new CafeDao().MySb_select(vo);
	}	
	public ArrayList<MySbVO> OurSb_select(){
		return new CafeDao().OurSb_select();
	}
	public MySbVO orderSb_select(MySbVO vo){
		return new CafeDao().orderSb_select(vo);
	}	
	public ArrayList<OrderVO> newOrder(){
		return new CafeDao().newOrder();
	}
	public ArrayList<OrderVO> s_select(Date start, Date end, int sch){
		return new CafeDao().s_select(start, end, sch);
	}
	public ArrayList<StockVO> stock_select(Date start, Date end, int sort){
		ArrayList<StockVO> list = new CafeDao().stock_select(start, end, sort);
		System.out.println("c: "+list.size());
		return list;
	}
	public void m_insert(MemberVO vo) {
		new CafeDao().m_insert(vo);
	}
	public void e_insert(EmployeeVO vo) {
		new CafeDao().e_insert(vo);
	}
	
	public void c_insert(CoffeeVO cvo){
		new CafeDao().c_insert(cvo);
	}
	public void sb_insert(MySbVO svo){
		new CafeDao().sb_insert(svo);
	}	
	public void stock_insert(StockVO vo){
		new CafeDao().stock_insert(vo);
	}
	public void o_insert(OrderVO ovo){
		new CafeDao().o_insert(ovo);
	}
	
	public void m_delete(MemberVO vo){
		new CafeDao().m_delete(vo);
	}
	public void c_delete(CoffeeVO cvo){
		new CafeDao().c_delete(cvo);
	}
	public void e_delete(EmployeeVO evo){
		new CafeDao().e_delete(evo);
	}
	public void sb_delete(MySbVO vo){
		new CafeDao().sb_delete(vo);
	}
	public void m_update(MemberVO vo){
		new CafeDao().m_update(vo);
	}
	public void order_delete(OrderVO vo){
		new CafeDao().order_delete(vo);
	}
	public void m_pointUpdate(MemberVO vo){
		new CafeDao().m_pointUpdate(vo);
	}
	public void grade_Update(MemberVO vo){
		new CafeDao().grade_update(vo);
	}
	public void block_update(MemberVO vo){
		new CafeDao().block_update(vo);
	}
	
	public void e_update(EmployeeVO evo){
		new CafeDao().e_update(evo);
	}
	public void c_update(CoffeeVO cvo){
		new CafeDao().c_update(cvo);
	}
	public void share_update(MySbVO sbvo){
		new CafeDao().share_update(sbvo);
	}
	public void like_update(MySbVO sbvo){
		new CafeDao().like_update(sbvo);
	}
	public void order_update(OrderVO svo, int flag){
		new CafeDao().order_update(svo, flag);
	}
	
	public void pn_main(CafeServer cs){
		new MainPanel(cs);
	}
	
	public void logout(CafeServer cs){
		cs.dispose();
		new CafeServer();
	}
	
	// 관리자 계정 매칭.
	public EmployeeVO loginChk(String id, char[] pass) { 
		EmployeeVO evo = null;

		ArrayList<EmployeeVO> list = new CafeDao().e_select();

		for (EmployeeVO ee : list) {
			System.out.println(ee.getId());
			if (id.equals(ee.getId())) { // 입력한 계정이 DB에 있을 경우.
				String passStr = "";
				for (int i = 0; i < pass.length; i++)
					passStr += pass[i];
				if (passStr.equals(ee.getE_pass())) { // 그 계정의 비밀번호가 같을 경우.
					evo = ee;
				}
			}
		}
		return evo;
	}
	
	// 회원 로그인 체크
	public MemberVO m_logChk(MemberVO vo) {
		MemberVO buf = null;
		ArrayList<MemberVO> list = new CafeDao().m_select();
		
		for (MemberVO mm : list) {
			if (vo.getId().equals(mm.getId())) {
				if (vo.getPass().equals(mm.getPass())) {
					buf = mm;
				}
			}
		}
		return buf;
	}
	
	// 회원 아이디 중복 검사
	public boolean idChk(String id){
		ArrayList<MemberVO> list = new CafeDao().m_select();
		
		for (MemberVO mm : list) {
			if(id.equals(mm.getId()))
				return true;
		}
		return false;
	}

	// 직원 아이디 중복 검사
	public boolean e_idChk(String id) {
		ArrayList<EmployeeVO> list = new CafeDao().e_select();

		for (EmployeeVO em : list) {
			if (id.equals(em.getId()))
				return true;
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
}
